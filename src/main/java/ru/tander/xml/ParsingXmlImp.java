package ru.tander.xml;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Этот класс создаёт XML файл с помощью {@link XMLStreamWriter},
 * а так же преобразовывает XML с помощью XSLT {@link Transformer#transform}.
 * Считывает данные из XML и расчитывает сумму fields.
 *
 * @author Мирзоян Мушег
 * @version 1.0
 * @see ParsingXml
 * @since 1.8
 */

public class ParsingXmlImp implements ParsingXml {

    private static final Logger LOGGER = Logger.getLogger(ParsingXmlImp.class);
    private long sum;

    @Override
    public int createXmlFile(String[] fields, String xmlPath) {
        int counter = 0;
        int countEntry = 0;
        if (fields != null) {
            countEntry = fields.length;
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            try (FileWriter fileWriter = new FileWriter(xmlPath)) {
                XMLStreamWriter writer = output.createXMLStreamWriter(fileWriter);
                writer.writeStartDocument("UTF-8", "1.0");
                writer.writeDTD("\n");
                writer.writeStartElement("entries");
                for (int i = 0; i < countEntry; i++) {
                    addEntry(writer, fields[i]);
                    counter++;
                }
                newRow(0, writer);
                writer.writeEndElement();
                newRow(0, writer);
                writer.writeEndDocument();
                writer.flush();
            } catch (IOException | XMLStreamException | NullPointerException e) {
                LOGGER.error(counter + " of " + countEntry + " records are recorded. ", e);
                delFile(xmlPath);
            }
        } else {
            LOGGER.error("Null fields");
            delFile(xmlPath);
        }
        LOGGER.info(counter + " of " + countEntry + " records are recorded.");
        return counter;
    }

    private void addEntry(XMLStreamWriter writer, String field) throws XMLStreamException {
        newRow(1, writer);
        writer.writeStartElement("entry");
        newRow(2, writer);
        writer.writeStartElement("field");
        writer.writeCharacters(field);
        writer.writeEndElement();
        newRow(1, writer);
        writer.writeEndElement();
    }

    private void newRow(int countTab, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeDTD("\n");
        for (int i = 0; i < countTab; i++) {
            writer.writeDTD("\t");
        }
    }

    @Override
    public void transformXml(String xmlPathOriginal, String xslPath, String xmlPathTransformed) {
        try (InputStream xml = new FileInputStream(xmlPathOriginal);
             InputStream xsl = new FileInputStream(xslPath)) {
            StreamSource xmlSource = new StreamSource(xml);
            StreamSource styleSource = new StreamSource(xsl);
            Transformer transformer = TransformerFactory.newInstance().newTransformer(styleSource);
            transformer.transform(xmlSource, new StreamResult(new File(xmlPathTransformed)));
        } catch (IOException | TransformerException e) {
            LOGGER.error(e);
            delFile(xmlPathOriginal);
        }
    }

    @Override
    public long sumFields(String pathFile) throws IOException, ParserConfigurationException, SAXException {

        DefaultHandler handler = new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                String valueField = attributes.getValue("field");
                if (valueField != null) {
                    try {
                        sum += Integer.parseInt(valueField);
                    } catch (NumberFormatException e) {
                        LOGGER.error("Incorrect attribute value : '" + valueField + "'");
                    }
                }
            }
        };

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(new File(pathFile), handler);
        return sum;
    }

    private void delFile(String pathFile) {
        if (pathFile != null) {
            try {
                Files.deleteIfExists(Paths.get(pathFile));
                LOGGER.info("File " + pathFile + " deleted");
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }

}
