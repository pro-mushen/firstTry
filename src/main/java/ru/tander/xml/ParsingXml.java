package ru.tander.xml;

public interface ParsingXml {
    int createXmlFile(String[] fields, String xmlPath);

    void transformXml(String xmlPathOriginal, String xslPath, String xmlPathTransformed);

    void delFile(String pathFile);
}
