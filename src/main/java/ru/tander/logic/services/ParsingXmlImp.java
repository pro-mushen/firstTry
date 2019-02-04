package ru.tander.logic.services;

import ru.tander.bd.dao.DaoTest;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class ParsingXmlImp implements ParsingXml {

    DaoTest daoTest;

    public ParsingXmlImp() {
    }

    public ParsingXmlImp(DaoTest daoTest) {
        this.daoTest = daoTest;
    }

    @Override
    public int createXmlFile(String[] fields) {
        int counter = 0;
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        try (FileWriter fileWriter = new FileWriter("1.xml")) {
            XMLStreamWriter writer = output.createXMLStreamWriter(fileWriter);
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeDTD("\n");
            writer.writeStartElement("entries");
            for (int i = 1; i < fields.length; i++) {
                newRow(1, writer);
                writer.writeStartElement("entry");
                newRow(2, writer);
                writer.writeStartElement("field");
                writer.writeCharacters(fields[i]);
                writer.writeEndElement();
                newRow(1, writer);
                writer.writeEndElement();
                counter++;
            }
            newRow(0, writer);
            writer.writeEndElement();
            newRow(0, writer);
            writer.writeEndDocument();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public void xmlToString(String xmlFile, String xslFile) throws Exception {
        // Открыть файлы в виде потоков
        InputStream xml = new FileInputStream(xmlFile);
        InputStream xsl = new FileInputStream(xslFile);
        // Сщоздать источник для транформации из потоков
        StreamSource xmlSource = new StreamSource(xml);
        StreamSource stylesource = new StreamSource(xsl);
        // Создать трансформатор и выполнить трансформацию
        Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
        transformer.transform(xmlSource, new StreamResult(new File("2.xml")));
        // вернуть результат в виде строки
    }

    private void newRow(int countTab, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeDTD("\n");
        for (int i = 0; i < countTab; i++) {
            writer.writeDTD("\t");
        }
    }

    public DaoTest getDaoTest() {
        return daoTest;
    }

    public void setDaoTest(DaoTest daoTest) {
        this.daoTest = daoTest;
    }
}
