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
    // SQL Injection (SONAR: S3649)
    public ResultSet findUser(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    // Weak hashing algorithm for passwords (SONAR: S4790, S2070)
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(password.getBytes());
        return new String(hash);
    }

    // Command injection (SONAR: S4721)
    public void runReport(String reportName) throws IOException {
        Runtime.getRuntime().exec("generateReport.sh " + reportName);
    }

        // Path traversal (SONAR: S2083)
    public String readFile(String filename) throws IOException {
        File file = new File("/app/reports/" + filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        // Resource leak: reader is never closed (SONAR: S2095)
        return sb.toString();
    }
}
