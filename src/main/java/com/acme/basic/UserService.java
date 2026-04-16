package com.acme.basic;

import java.io.*;
import java.sql.*;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {

    // Hardcoded credentials (SONAR: S2068)
    private static final String DB_PASSWORD = "admin123";
    private static final String DB_URL = "jdbc:mysql://localhost/mydb?user=root&password=admin123";

    private Connection connection;

    public UserService() throws SQLException {
        // Hardcoded connection string with credentials
        this.connection = DriverManager.getConnection(DB_URL);
    }
}