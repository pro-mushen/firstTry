package ru.tander.bd.connectionManager;

import org.apache.log4j.Logger;
import ru.tander.bd.pojo.ConnectionData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerImp implements ConnectionManager {
    private static ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(ConnectionManagerImp.class);
    private Connection connection;
    private static ConnectionData connectionData;

    public static ConnectionManager getInstance(ConnectionData connectionInfo) {
        if (connectionManager==null){
            connectionManager = new ConnectionManagerImp();
            connectionData = connectionInfo;
        }
        return connectionManager;
    }

    @Override
    public Connection getConnection() {
        if (connection==null){
            connection = getNewConnection();
        }
        return connection;
    }

    private Connection getNewConnection() {
        try {
            connection = DriverManager.getConnection(connectionData.getUrl(), connectionData.getUser(), connectionData.getPassword());
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return connection;
    }


    @Override
    public void close() {
        if (connection != null){
            try {
                connection.close();
                LOGGER.info("Connection is closed");
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
    }

    public ConnectionData getConnectionData() {
        return connectionData;
    }

    @Override
    public String toString() {
        return connectionData.toString();
    }

}
