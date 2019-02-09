package ru.tander.services;

import ru.tander.bd.connectionManager.ConnectionManager;
import ru.tander.bd.connectionManager.ConnectionManagerImp;
import ru.tander.bd.dao.TestDao;
import ru.tander.bd.dao.TestDaoImp;

public class InitDataImp implements InitData {
    @Override
    public String[] getData(int countField) {
        ConnectionManager connectionManager = ConnectionManagerImp.getInstance();
        TestDao testDao = new TestDaoImp(connectionManager.getConnection());
        testDao.clearTable();
        testDao.addNumbers(countField);
        return testDao.selectAll();
    }
}
