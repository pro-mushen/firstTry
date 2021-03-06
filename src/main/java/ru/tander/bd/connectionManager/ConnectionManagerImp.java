package ru.tander.bd.connectionManager;

import org.apache.log4j.Logger;
import ru.tander.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerImp implements ConnectionManager {
    private static ConnectionManager connectionManager;
    private static final Logger LOGGER = Logger.getLogger(ConnectionManagerImp.class);
    private Connection connection;

    public static ConnectionManager getInstance() {
        if (connectionManager==null){
            connectionManager = new ConnectionManagerImp();
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
            connection = DriverManager.getConnection(
                    Config.getProperty(Config.URL_DB),
                    Config.getProperty(Config.LOGIN_DB),
                    Config.getProperty(Config.PASSWORD_DB));
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


}
