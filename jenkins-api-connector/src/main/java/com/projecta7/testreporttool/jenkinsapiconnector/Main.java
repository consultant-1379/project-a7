package com.projecta7.testreporttool.jenkinsapiconnector;


import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {
        Thread.sleep(1000 * 30); // Sleep in an attempt to wait long enough for the server to be live

        DataHandler handler = new DataHandler();

        handler.readDatabase();
    }
}
