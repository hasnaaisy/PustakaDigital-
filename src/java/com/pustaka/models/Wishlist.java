package com.pustaka.models;

import com.pustaka.config.DBConnection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Wishlist {
    DBConnection db = new DBConnection();

    public boolean isWished(int bukuId, int pembeliId) {
        boolean result = false;
        try {
            String sql = "SELECT * FROM wishlist WHERE buku_id=" + bukuId + " AND pembeli_id=" + pembeliId;
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

    public void add(int bukuId, int pembeliId) {
        if (!isWished(bukuId, pembeliId)) {
            String sql = "INSERT INTO wishlist (buku_id, pembeli_id) VALUES (" + bukuId + ", " + pembeliId + ")";
            db.runQuery(sql); 
        }
    }

    public void remove(int bukuId, int pembeliId) {
        String sql = "DELETE FROM wishlist WHERE buku_id=" + bukuId + " AND pembeli_id=" + pembeliId;
        db.runQuery(sql);
    }
    
    public ArrayList<Buku> getItems(int userId) {
        ArrayList<Buku> list = new ArrayList<>();
        try {
            String sql = "SELECT b.* FROM wishlist w JOIN buku b ON w.buku_id = b.id WHERE w.pembeli_id = " + userId;
            ResultSet rs = db.getData(sql);
            
            while (rs != null && rs.next()) {
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
            System.out.println("Get Wishlist Items Error: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return list;
    }

    public void clearCart(int userId) {
        String sql = "DELETE FROM wishlist WHERE pembeli_id=" + userId;
        db.runQuery(sql);
    }
}