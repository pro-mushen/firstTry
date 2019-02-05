package ru.tander.bd.connectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerImp implements ConnectionManager {
    private static ConnectionManager connectionManager;
    private static String user;
    private static String password;
    private static String url;
    private Connection connection;

    public static ConnectionManager getInstance(String urlBd, String userBd, String passwordBd) {
        if (connectionManager==null){
            connectionManager = new ConnectionManagerImp();
            user = userBd;
            password = passwordBd;
            url = urlBd;
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
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void close() {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "ConnectionManagerImp{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url +
                '}';
    }

}
