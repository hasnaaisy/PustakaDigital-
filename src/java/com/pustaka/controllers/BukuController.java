/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.pustaka.controllers;

import com.pustaka.models.Buku;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "BukuController", urlPatterns = {"/BukuController"})
public class BukuController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Cek Session dulu (Security)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String search = request.getParameter("search");
        Buku bukuModel = new Buku();
        ArrayList<Buku> listBuku;

        if (search != null && !search.trim().isEmpty()) {
            listBuku = bukuModel.search(search);
        } else {
            listBuku = bukuModel.getAll();
        }

        request.setAttribute("listBuku", listBuku);
        // Kita pakai nama dashboard.jsp biar beda dari referensi 'book.jsp'
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}