package com.pustaka.models;

import com.pustaka.config.DBConnection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class Transaksi {
    DBConnection db = new DBConnection();
    
    private int id;
    private Buku buku;
    private String tanggal;
    private double totalHarga;
    
    public Transaksi(){}
    
    public Transaksi(int id, Buku buku, String tanggal, double totalHarga) {
        this.id = id;
        this.buku = buku;
        this.tanggal = tanggal;
        this.totalHarga = totalHarga;
    }

    public boolean isPurchased(int bukuId, int pembeliId) {
        boolean result = false;
        try {
            String sql = "SELECT * FROM transaksi WHERE buku_id=" + bukuId + " AND pembeli_id=" + pembeliId;
            ResultSet rs = db.getData(sql);
            if (rs != null && rs.next()) {
                result = true;
            }
        } catch (Exception e) {
            result = false;
        } finally {
            db.disconnect();
        }
        return result;
    }

    public void buy(int bukuId, int pembeliId, double harga) {
        String tanggal = LocalDate.now().toString();
        String sql = "INSERT INTO transaksi (buku_id, pembeli_id, tanggal, total_harga) VALUES (" 
                   + bukuId + ", " + pembeliId + ", '" + tanggal + "', " + harga + ")";
        db.runQuery(sql);
    }

    public ArrayList<Transaksi> getHistory(int userId) {
        ArrayList<Transaksi> list = new ArrayList<>();
        try {
            String sql = "SELECT t.id, t.tanggal, t.total_harga, b.judul, b.penulis " +
                         "FROM transaksi t JOIN buku b ON t.buku_id = b.id " +
                         "WHERE t.pembeli_id = " + userId + " ORDER BY t.tanggal DESC";
            
            ResultSet rs = db.getData(sql);
            while (rs != null && rs.next()) {
                Buku b = new Buku();
                b.setJudul(rs.getString("judul"));
                b.setPenulis(rs.getString("penulis"));
                
                list.add(new Transaksi(
                    rs.getInt("id"),
                    b,
                    rs.getString("tanggal"),
                    rs.getDouble("total_harga")
                ));
            }
        } catch (Exception e) {
            System.out.println("Get History Error: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return list;
    }

    public int getId() { return id; }
    public Buku getBuku() { return buku; }
    public String getTanggal() { return tanggal; }
    public double getTotalHarga() { return totalHarga; }
}