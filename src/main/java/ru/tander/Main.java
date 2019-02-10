package ru.tander;

import ru.tander.services.InitData;
import ru.tander.services.InitTestDb;
import ru.tander.services.XmlService;
import ru.tander.services.XmlServiceImp;
import ru.tander.xml.ParsingXml;
import ru.tander.xml.ParsingXmlImp;

public class Main {

    private static final int N = 100000;

    public static void main(String[] args) {
        InitData initData = new InitTestDb();
        ParsingXml parsingXml = new ParsingXmlImp();
        XmlService xmlService = new XmlServiceImp(parsingXml, initData);
        System.out.println("The sum of the fields is " + xmlService.getSumFields(N));
    }

}
