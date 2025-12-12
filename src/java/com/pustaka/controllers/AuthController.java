/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.pustaka.controllers;

import com.pustaka.models.Pembeli;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AuthController", urlPatterns = {"/AuthController"})
public class AuthController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("login".equals(action)) {
            String email = request.getParameter("email");
            String pass = request.getParameter("password");
            
            Pembeli p = new Pembeli();
            if (p.login(email, pass)) {
                // Login Sukses -> Buat Session
                HttpSession session = request.getSession();
                session.setAttribute("user", p); // Simpan objek user
                session.setAttribute("userId", p.getId());
                response.sendRedirect("BukuController"); // Arahkan ke katalog
            } else {
                response.sendRedirect("index.jsp?err=login_failed");
            }
            
        } else if ("register".equals(action)) {
            String nama = request.getParameter("nama");
            String email = request.getParameter("email");
            String pass = request.getParameter("password");
            
            Pembeli p = new Pembeli(0, nama, email, pass);
            p.register();
            response.sendRedirect("index.jsp?msg=registered");
        } else if ("logout".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect("index.jsp");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}