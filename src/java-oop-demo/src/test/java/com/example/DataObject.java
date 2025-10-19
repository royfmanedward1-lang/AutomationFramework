package com.example;

public class DataObject {
    private String username;
    private String password;

    public DataObject(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Example data provider method for Selenium
    public static String[] dataProvider() {
        return new String[] {"user1", "user2", "user3"};
    }
}
