/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.pustaka.controllers;

import com.pustaka.models.Admin;
import com.pustaka.models.Buku;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {

    // Handle Data (Simpan/Update/Login)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("login".equals(action)) {
            Admin admin = new Admin();
            if (admin.login(request.getParameter("username"), request.getParameter("password"))) {
                request.getSession().setAttribute("admin", admin);
                response.sendRedirect("AdminController?action=dashboard");
            } else {
                response.sendRedirect("admin_login.jsp?err=failed");
            }
            
        } else if ("save".equals(action)) {
            // Logika Simpan/Update Buku
            Buku b = new Buku(
                request.getParameter("id").isEmpty() ? 0 : Integer.parseInt(request.getParameter("id")),
                request.getParameter("judul"),
                request.getParameter("penulis"),
                request.getParameter("kategori"),
                Integer.parseInt(request.getParameter("tahun")),
                Integer.parseInt(request.getParameter("halaman")),
                Double.parseDouble(request.getParameter("harga"))
            );
            
            if (b.getId() == 0) b.insert(); // ID 0 berarti baru -> Insert
            else b.update(); // ID ada -> Update
            
            response.sendRedirect("AdminController?action=dashboard");
        }
    }

    // Handle Navigasi (Lihat Dashboard, Form Edit, Hapus)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        // Security Check (Kecuali login page)
        if ((session == null || session.getAttribute("admin") == null) && !"logout".equals(action)) {
            response.sendRedirect("admin_login.jsp");
            return;
        }

        if ("dashboard".equals(action)) {
            Buku bukuModel = new Buku();
            ArrayList<Buku> list = bukuModel.getAll();
            request.setAttribute("listBuku", list);
            request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
            
        } else if ("form".equals(action)) {
            // Jika ada ID, berarti Edit mode. Ambil datanya.
            String idStr = request.getParameter("id");
            if (idStr != null) {
                Buku b = new Buku().findById(Integer.parseInt(idStr));
                request.setAttribute("buku", b);
            }
            request.getRequestDispatcher("admin_form.jsp").forward(request, response);
            
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            new Buku().delete(id);
            response.sendRedirect("AdminController?action=dashboard");
            
        } else if ("logout".equals(action)) {
            if(session != null) session.invalidate();
            response.sendRedirect("admin_login.jsp");
        }
    }
}