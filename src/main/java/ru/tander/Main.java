package ru.tander;

import ru.tander.bd.connectionManager.ConnectionManagerImp;
import ru.tander.bd.dao.DaoTest;
import ru.tander.bd.dao.DaoTestImpl;
import ru.tander.logic.services.ParsingXml;
import ru.tander.logic.services.ParsingXmlImp;


public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:postgresql://localhost:5500/test";

    public static void main(String[] args) {
        DaoTest daoTest = new DaoTestImpl(ConnectionManagerImp.getInstance().getConnection(USER, PASSWORD,URL));
        ((DaoTestImpl) daoTest).clearTable();
        ((DaoTestImpl) daoTest).clearTable();
        long startTime = System.currentTimeMillis();
        ((DaoTestImpl) daoTest).addNumbersBatch(100);
        System.out.println("Добавление: " + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        String[] rows = ((DaoTestImpl) daoTest).selectAll();
        System.out.println(rows.length);
        System.out.println("Продолжительность работы: " + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        ParsingXml parsingXml = new ParsingXmlImp();
        parsingXml.createXmlFile(rows);
        try {
            ((ParsingXmlImp) parsingXml).xmlToString("1.xml", "test.xsl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Продолжительность работы парсинг: " + (System.currentTimeMillis() - startTime));
        System.out.println("ВСЁ");
    }
}
