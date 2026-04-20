package com.acme.basic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class HelloWorld {

  void sayHello() {
    System.out.println("Hello World!");
  }

  void notCovered() {
    System.out.println("This method is not covered by unit tests");
  }

    // --- VULNERABILITY: Hardcoded Sensitive Data (CWE-798) ---
    // Storing a secret key directly in the source code.
    private static final String SECRET_API_KEY = "DEV-API-KEY-4567-VERY-SECRET-1234";

    public static void main(String[] args) {
        System.out.println("--- Demo 1: Hardcoded Credential Usage (CWE-798) ---");
        useHardcodedKey();

        System.out.println("\n--- Demo 2: Security Hotspot (Weak MD5 Hash) ---");
        // Simulating hashing a piece of important data
        useWeakHashing("SensitiveDataToHash123");
    }

    /**
     * Demonstrates the use of a hardcoded sensitive key.
     * The key is directly available in the compiled code.
     */
    private static void useHardcodedKey() {
        System.out.println("Accessing service using hardcoded key...");
        System.out.println("Key used: " + SECRET_API_KEY.substring(0, 15) + "...");
        // This is where sensitive operations would occur in a real application
    }

    /**
     * HOTSPOT: Uses a weak or broken cryptographic hash (MD5).
     * MD5 is no longer secure for cryptographic purposes due to collision attacks.
     * @param data The string to be hashed.
     */
    private static void useWeakHashing(String data) {
        try {
            // Hotspot: Using MD5, which is a known weak algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(data.getBytes(StandardCharsets.UTF_8));

            // Convert byte array to hex string for display
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            System.out.println("Data: '" + data + "'");
            System.out.println("MD5 Hash: " + hexString.toString());

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: MD5 algorithm not available.");
        }
    }
  
}
