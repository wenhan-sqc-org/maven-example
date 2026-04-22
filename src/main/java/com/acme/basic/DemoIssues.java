package com.acme.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Demo file intentionally containing SonarQube issues for Sonarsource demonstration.
 * Categories: Security, Reliability, Maintainability
 */
public class DemoIssues {

    // =========================================================================
    // SECURITY ISSUES
    // =========================================================================

    // [Security] Hard-coded credentials (sonar: java:S2068)
    private static final String DB_PASSWORD = "admin123";
    private static final String DB_URL = "jdbc:mysql://localhost/mydb";

    // [Security] SQL Injection - user input concatenated directly into query (sonar: java:S2077)
    public List<String> findUsersByName(String name) throws Exception {
        List<String> results = new ArrayList<>();
        Connection conn = DriverManager.getConnection(DB_URL, "root", DB_PASSWORD);
        Statement stmt = conn.createStatement();
        // Vulnerable: attacker can inject SQL via 'name'
        ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE name = '" + name + "'");
        while (rs.next()) {
            results.add(rs.getString("name"));
        }
        return results;
    }

    // [Security] Weak cryptography - using MD5 for password hashing (sonar: java:S4790)
    public String hashPassword(String password) throws Exception {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // [Security] Use of java.util.Random for security-sensitive context (sonar: java:S2245)
    public String generateToken() {
        Random random = new Random();
        return String.valueOf(random.nextLong());
    }

    // =========================================================================
    // RELIABILITY ISSUES
    // =========================================================================

    // [Reliability] NullPointerException risk - no null check before use (sonar: java:S2259)
    public int getUserNameLength(String username) {
        // If username is null, this throws NullPointerException
        return username.length();
    }

    // [Reliability] Resource leak - Connection/Statement never closed (sonar: java:S2095)
    public void leakyDatabaseCall() throws Exception {
        Connection conn = DriverManager.getConnection(DB_URL, "root", DB_PASSWORD);
        Statement stmt = conn.createStatement();
        stmt.executeQuery("SELECT 1");
        // conn and stmt are never closed
    }

    // [Reliability] Empty catch block silently swallows exception (sonar: java:S108)
    public String readConfig(String key) {
        try {
            return System.getProperty(key).trim();
        } catch (Exception e) {
            // exception ignored — caller gets null silently
        }
        return null;
    }

    // [Reliability] Comparison of Integer with == instead of .equals() (sonar: java:S4973)
    public boolean isSameId(Integer a, Integer b) {
        return a == b;  // works only within cached range [-128, 127]
    }

    // =========================================================================
    // MAINTAINABILITY ISSUES
    // =========================================================================

    // [Maintainability] Cognitive complexity too high - deeply nested logic (sonar: java:S3776)
    public String classify(int score, boolean isActive, boolean isPremium, String region) {
        if (isActive) {
            if (isPremium) {
                if (score > 90) {
                    if (region != null) {
                        if (region.equals("US")) {
                            if (score > 95) {
                                return "platinum-us";
                            } else {
                                return "gold-us";
                            }
                        } else if (region.equals("EU")) {
                            if (score > 95) {
                                return "platinum-eu";
                            } else {
                                return "gold-eu";
                            }
                        } else {
                            return "gold-other";
                        }
                    }
                } else if (score > 70) {
                    return "silver";
                } else {
                    return "bronze";
                }
            } else {
                if (score > 50) {
                    return "standard";
                } else {
                    return "basic";
                }
            }
        }
        return "inactive";
    }

    // [Maintainability] Duplicated code block (sonar: java:S4144)
    public double calculateDiscountForVip(double price) {
        double discount = 0;
        if (price > 100) {
            discount = price * 0.2;
        } else {
            discount = price * 0.1;
        }
        return price - discount;
    }

    public double calculateDiscountForMember(double price) {
        double discount = 0;
        if (price > 100) {
            discount = price * 0.2;
        } else {
            discount = price * 0.1;
        }
        return price - discount;
    }

    // [Maintainability] Magic numbers without named constants (sonar: java:S109)
    public double convertTemperature(double celsius) {
        return celsius * 9 / 5 + 32;
    }

    // [Maintainability] Too many parameters in method (sonar: java:S107)
    public void createOrder(String userId, String productId, int quantity,
                            double price, String coupon, String address,
                            String city, String country) {
        // method with 8 parameters — hard to read and maintain
        System.out.println("Order: " + userId + " " + productId + " " + quantity
                + " " + price + " " + coupon + " " + address + " " + city + " " + country);
    }
}
