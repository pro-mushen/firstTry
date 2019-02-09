package ru.tander;

import ru.tander.services.InitData;
import ru.tander.services.InitDataImp;
import ru.tander.services.XmlService;
import ru.tander.services.XmlServiceImp;
import ru.tander.xml.ParsingXml;
import ru.tander.xml.ParsingXmlImp;




public class Main {

    private static final int N = 1000000;

    public static void main(String[] args) {
        InitData initData = new InitDataImp();
        ParsingXml parsingXml = new ParsingXmlImp();
        XmlService xmlService = new XmlServiceImp(parsingXml, initData);
        System.out.println(xmlService.getSumFields(N));
    }

}
