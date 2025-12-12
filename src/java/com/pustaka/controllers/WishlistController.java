/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.pustaka.controllers;

import com.pustaka.models.Wishlist;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "WishlistController", urlPatterns = {"/WishlistController"})
public class WishlistController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        int bukuId = Integer.parseInt(request.getParameter("bukuId"));
        String type = request.getParameter("type"); // add atau remove

        Wishlist ws = new Wishlist();
        
        if ("add".equals(type)) {
            ws.add(bukuId, userId);
        } else if ("remove".equals(type)) {
            ws.remove(bukuId, userId);
        }
        
        // Kembali ke halaman sebelumnya
        response.sendRedirect("BukuController");
    }
}