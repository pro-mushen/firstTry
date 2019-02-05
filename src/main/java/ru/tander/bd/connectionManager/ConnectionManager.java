package ru.tander.bd.connectionManager;

import java.sql.Connection;

public interface ConnectionManager extends AutoCloseable {
    Connection getConnection();
}
