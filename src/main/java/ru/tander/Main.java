package ru.tander;

import ru.tander.bd.connectionManager.ConnectionManager;
import ru.tander.bd.connectionManager.ConnectionManagerImp;
import ru.tander.bd.dao.TestDao;
import ru.tander.bd.dao.TestDaoImp;
import ru.tander.bd.pojo.ConnectionData;
import ru.tander.xml.ParsingXml;
import ru.tander.xml.ParsingXmlImp;

import java.sql.Connection;


public class Main {

    private static final int N = 20;
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:postgresql://localhost:5500/test";
    private static final String XML_ORIGINAL = "1.xml";
    private static final String XML_TRANSFORMED = "2.xml";
    private static final String XSL = "test.xsl";
    private static ConnectionManager connectionManager;

    public static void main(String[] args) throws Exception {
        ConnectionData connectionData = new ConnectionData(URL, USER, PASSWORD);
        connectionManager = ConnectionManagerImp.getInstance(connectionData);
        String[] records = clearingAndCreatingRecords();
        ParsingXml parsingXml = new ParsingXmlImp();
        parsingXml.createXmlFile(records, XML_ORIGINAL);
        parsingXml.transformXml(XML_ORIGINAL, XSL, XML_TRANSFORMED);
        connectionManager.close();
    }

    private static String[] clearingAndCreatingRecords() {
        Connection connection = connectionManager.getConnection();
        TestDao testDao = new TestDaoImp(connection);
        testDao.clearTable();
        testDao.addNumbers(N);
        return testDao.selectAll();
    }

}
