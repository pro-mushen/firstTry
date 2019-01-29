package ru.tander.logic.services;

import ru.tander.bd.dao.DaoTest;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.IOException;

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
        try (FileWriter fileWriter = new FileWriter("C:/test/1.xml")) {
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
