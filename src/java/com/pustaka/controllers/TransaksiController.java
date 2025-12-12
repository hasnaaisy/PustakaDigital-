/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.pustaka.controllers;

import com.pustaka.models.Buku;
import com.pustaka.models.Transaksi;
import com.pustaka.models.Wishlist;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "TransaksiController", urlPatterns = {"/TransaksiController"})
public class TransaksiController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Cek Session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action"); // Cek aksi (checkout_all atau beli satuan)
        Transaksi trx = new Transaksi();

        // --- SKENARIO 1: BELI SEMUA DARI WISHLIST ---
        if ("checkout_all".equals(action)) {
            Wishlist ws = new Wishlist();
            
            // Ambil semua item di wishlist user
            ArrayList<Buku> wishItems = ws.getItems(userId);
            
            // Looping beli satu per satu
            for (Buku b : wishItems) {
                // Cek lagi biar tidak duplikat
                if (!trx.isPurchased(b.getId(), userId)) {
                    trx.buy(b.getId(), userId, b.getHarga());
                }
            }
            
            // Hapus semua dari wishlist setelah dibeli
            ws.clearCart(userId);
            
            // Redirect ke halaman riwayat
            response.sendRedirect("TransaksiController"); // Panggil doGet untuk lihat history
        } 
        
        // --- SKENARIO 2: BELI SATUAN (LANGSUNG) ---
        else {
            // Ambil parameter HANYA jika bukan checkout_all
            // Mencegah Error NullPointerException
            String idParam = request.getParameter("bukuId");
            String hargaParam = request.getParameter("harga");

            if (idParam != null && hargaParam != null) {
                int bukuId = Integer.parseInt(idParam);
                double harga = Double.parseDouble(hargaParam);
                
                if (!trx.isPurchased(bukuId, userId)) {
                    trx.buy(bukuId, userId, harga);
                }
            }
            // Redirect kembali ke katalog
            response.sendRedirect("BukuController");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        Transaksi trxModel = new Transaksi();
        
        ArrayList<Transaksi> historyList = trxModel.getHistory(userId);
        
        request.setAttribute("historyList", historyList);
        request.getRequestDispatcher("transaction.jsp").forward(request, response);
    }
}