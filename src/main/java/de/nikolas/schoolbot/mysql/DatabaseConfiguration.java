package de.nikolas.schoolbot.mysql;

public class DatabaseConfiguration {

    private String hostName;
    private String database;
    private String user;
    private String password;
    private int port;

    public DatabaseConfiguration(String hostName, String database, String user, String password, int port) {
        this.hostName = hostName;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}