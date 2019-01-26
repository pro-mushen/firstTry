package ru.tander.bd.connectionManager;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection(String user, String password, String url);
}
