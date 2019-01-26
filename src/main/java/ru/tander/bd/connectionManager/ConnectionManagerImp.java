package ru.tander.bd.connectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerImp implements ConnectionManager {
    private static ConnectionManager connectionManager;
    private String user;
    private String password;
    private String url;
    private Connection connection;

    public static synchronized ConnectionManager getInstance() {
        if (connectionManager==null){
            connectionManager = new ConnectionManagerImp();
        }
        return connectionManager;
    }

    @Override
    public synchronized Connection getConnection(String user, String password, String url) {
        if (connection==null){
            connection = getNewConnection(user,password,url);
        }
        return connection;
    }

//Double Checked Locking & volatile
    private Connection getNewConnection(String user, String password, String url){
        try {
            connection = DriverManager.getConnection(url ,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    void close(){
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
