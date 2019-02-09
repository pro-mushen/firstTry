package ru.tander.xml;

import org.apache.log4j.Logger;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class ParsingXmlImp implements ParsingXml {

    private static final Logger LOGGER = Logger.getLogger(ParsingXmlImp.class);

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
    public void delFile(String pathFile) {
        if (pathFile != null) {
            File file = new File(pathFile);
            if (file.delete()) {
                LOGGER.info("File " + pathFile + " deleted");
            }
        }
    }

}
