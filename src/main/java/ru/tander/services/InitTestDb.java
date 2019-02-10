package ru.tander.services;

import ru.tander.bd.connectionManager.ConnectionManager;
import ru.tander.bd.connectionManager.ConnectionManagerImp;
import ru.tander.bd.dao.TestDao;
import ru.tander.bd.dao.TestDaoImp;

/**
 * Класс предоставляет данные для формирования XML для {@link XmlService} с помощью работы с БД.
 *
 * @author Мирзоян Мушег
 * @version 1.0
 * @see InitTestDb
 */

public class InitTestDb implements InitData {

    private ConnectionManager connectionManager;
    private TestDao testDao;

    public InitTestDb() {
        connectionManager = ConnectionManagerImp.getInstance();
        testDao = new TestDaoImp(connectionManager.getConnection());
    }

    public InitTestDb(TestDao testDao) {
        connectionManager = ConnectionManagerImp.getInstance();
        this.testDao = testDao;
    }

    public InitTestDb(ConnectionManager connectionManager, TestDao testDao) {
        this.connectionManager = connectionManager;
        this.testDao = testDao;
    }

    @Override
    public String[] getData(int countField) {
        testDao.clearTable();
        testDao.addNumbers(countField);
        return testDao.selectAll();
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public TestDao getTestDao() {
        return testDao;
    }

    public void setTestDao(TestDao testDao) {
        this.testDao = testDao;
    }
}
