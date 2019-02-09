package ru.tander.bd.connectionManager;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();

    void close();
}
