/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pustaka.models;

import com.pustaka.config.DBConnection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Buku {
    private int id;
    private String judul, penulis, kategori;
    private int tahun, halaman;
    private double harga;
    DBConnection db = new DBConnection();

    // 1. Constructor Kosong (Penting untuk inisialisasi tanpa data)
    public Buku() {}

    // 2. Constructor Lengkap (Penting untuk insert/update)
    public Buku(int id, String judul, String penulis, String kategori, int tahun, int halaman, double harga) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.kategori = kategori;
        this.tahun = tahun;
        this.halaman = halaman;
        this.harga = harga;
    }

    // --- LOGIKA DATABASE (CRUD) ---

    // Ambil Semua Buku
    public ArrayList<Buku> getAll() {
        ArrayList<Buku> list = new ArrayList<>();
        try {
            ResultSet rs = db.getData("SELECT * FROM buku");
            while (rs.next()) {
                list.add(new Buku(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getString("kategori"),
                    rs.getInt("tahun"),
                    rs.getInt("halaman"),
                    rs.getDouble("harga")
                ));
            }
        } catch (Exception e) {
            System.out.println("Get Buku Error: " + e.getMessage());
        }
        return list;
    }
    
    // Cari Buku
    public ArrayList<Buku> search(String keyword) {
        ArrayList<Buku> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM buku WHERE judul LIKE '%" + keyword + "%' OR kategori LIKE '%" + keyword + "%'";
            ResultSet rs = db.getData(sql);
            while (rs.next()) {
                list.add(new Buku(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getString("kategori"),
                    rs.getInt("tahun"),
                    rs.getInt("halaman"),
                    rs.getDouble("harga")
                ));
            }
        } catch (Exception e) {
            System.out.println("Search Error: " + e.getMessage());
        }
        return list;
    }

    // Ambil satu buku berdasarkan ID (Untuk Edit)
    public Buku findById(int id) {
        try {
            String sql = "SELECT * FROM buku WHERE id=" + id;
            ResultSet rs = db.getData(sql);
            if (rs.next()) {
                return new Buku(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getString("kategori"),
                    rs.getInt("tahun"),
                    rs.getInt("halaman"),
                    rs.getDouble("harga")
                );
            }
        } catch (Exception e) {
            System.out.println("FindById Error: " + e.getMessage());
        }
        return null;
    }

    // Simpan Buku Baru
    public void insert() {
        String sql = "INSERT INTO buku (judul, penulis, kategori, tahun, halaman, harga) VALUES ('" 
                   + judul + "', '" + penulis + "', '" + kategori + "', " 
                   + tahun + ", " + halaman + ", " + harga + ")";
        db.runQuery(sql);
    }

    // Update Buku
    public void update() {
        String sql = "UPDATE buku SET judul='" + judul + "', penulis='" + penulis 
                   + "', kategori='" + kategori + "', tahun=" + tahun 
                   + ", halaman=" + halaman + ", harga=" + harga 
                   + " WHERE id=" + id;
        db.runQuery(sql);
    }

    // Hapus Buku
    public void delete(int id) {
        String sql = "DELETE FROM buku WHERE id=" + id;
        db.runQuery(sql);
    }

    // --- GETTER & SETTER (WAJIB LENGKAP) ---
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; } // Ini yang tadi dicari Transaksi.java

    public String getPenulis() { return penulis; }
    public void setPenulis(String penulis) { this.penulis = penulis; } // Ini juga

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public int getTahun() { return tahun; }
    public void setTahun(int tahun) { this.tahun = tahun; }

    public int getHalaman() { return halaman; }
    public void setHalaman(int halaman) { this.halaman = halaman; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
}