/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pustaka.models;

import com.pustaka.config.DBConnection;
import java.sql.ResultSet;

public class Admin extends Pengguna {
    DBConnection db = new DBConnection();

    public Admin() {}

    @Override
    public boolean login(String username, String password) {
        try {
            // Cek ke tabel 'admin'
            String sql = "SELECT * FROM admin WHERE username='" + username + "' AND password='" + password + "'";
            ResultSet rs = db.getData(sql);
            if (rs != null && rs.next()) {
                this.id = rs.getInt("id");
                this.nama = rs.getString("nama");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Admin Login Error: " + e.getMessage());
        }
        return false;
    }
}