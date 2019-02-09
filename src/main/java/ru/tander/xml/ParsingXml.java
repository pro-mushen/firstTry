package ru.tander.xml;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface ParsingXml {
    int createXmlFile(String[] fields, String xmlPath);

    void transformXml(String xmlPathOriginal, String xslPath, String xmlPathTransformed);

    long sumFields(String pathFile) throws IOException, ParserConfigurationException, SAXException;

    void delFile(String pathFile);
}
