package ru.tander.bd.pojo;

import java.util.Objects;

public class ConnectionData {
    private String user;
    private String password;
    private String url;

    public ConnectionData(String url, String user, String password) {
        this.user = user;
        this.password = password;
        this.url = url;
    }

    public ConnectionData() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ConnectionData{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionData that = (ConnectionData) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(password, that.password) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, password, url);
    }
}
