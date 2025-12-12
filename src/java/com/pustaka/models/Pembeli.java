/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pustaka.models;

import com.pustaka.config.DBConnection;
import java.sql.ResultSet;

public class Pembeli extends Pengguna {
    private String email;
    DBConnection db = new DBConnection();

    public Pembeli() {}

    public Pembeli(int id, String nama, String email, String password) {
        super(id, nama, password);
        this.email = email;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean login(String email, String password) {
        try {
            String sql = "SELECT * FROM pembeli WHERE email='" + email + "' AND password='" + password + "'";
            ResultSet rs = db.getData(sql);
            if (rs != null && rs.next()) {
                this.id = rs.getInt("id");
                this.nama = rs.getString("nama");
                this.email = rs.getString("email");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
        }
        return false;
    }

    public void register() {
        String sql = "INSERT INTO pembeli (nama, email, password) VALUES ('" + nama + "', '" + email + "', '" + password + "')";
        db.runQuery(sql);
    }
}
