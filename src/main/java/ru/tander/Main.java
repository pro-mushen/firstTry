package ru.tander;

import ru.tander.bd.connectionManager.ConnectionManager;
import ru.tander.bd.connectionManager.ConnectionManagerImp;
import ru.tander.bd.dao.DaoTest;
import ru.tander.bd.dao.DaoTestImpl;

public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:postgresql://localhost:5500/test";

    public static void main(String[] args) {
        DaoTest daoTest = new DaoTestImpl(ConnectionManagerImp.getInstance().getConnection(USER, PASSWORD,URL));
        ((DaoTestImpl) daoTest).clearTable();
        ((DaoTestImpl) daoTest).clearTable();
        System.out.println(((DaoTestImpl) daoTest).countRows());
        daoTest.addNumbers(1000);
        System.out.println(((DaoTestImpl) daoTest).countRows());
    }
}
