package com.pustaka.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
    private Connection con;
    private Statement stmt;
    public String message;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pustaka_db", "root", "");
            stmt = con.createStatement();
            message = "Koneksi Berhasil";
        } catch (Exception e) {
            message = "Koneksi Gagal: " + e.getMessage();
            System.out.println(message);
        }
    }

    public ResultSet getData(String query) {
        try {
            connect();
            return stmt.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Error getData: " + e.getMessage());
            return null;
        }
    }

    public void runQuery(String query) {
        try {
            connect();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error runQuery: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public void disconnect() {
        try {
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("Disconnect Error: " + e.getMessage());
        }
    }
}