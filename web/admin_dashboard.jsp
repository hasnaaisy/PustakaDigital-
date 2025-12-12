<%@page import="com.pustaka.models.Buku, java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if(session.getAttribute("admin") == null) { response.sendRedirect("admin_login.jsp"); return; }
    ArrayList<Buku> list = (ArrayList<Buku>) request.getAttribute("listBuku");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Admin</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .table-admin { width: 100%; border-collapse: collapse; margin-top: 20px; }
        .table-admin th, .table-admin td { border: 1px solid #ddd; padding: 10px; text-align: left; font-size: 14px; }
        .table-admin th { background-color: #f2f2f2; }
        .btn-small { padding: 5px 10px; font-size: 12px; text-decoration: none; border-radius: 4px; color: white; margin-right: 5px; }
        .btn-edit { background-color: #4CAF50; }
        .btn-del { background-color: #f44336; }
    </style>
</head>
<body>
    <div class="header">
        <h2>Panel Admin 🛠️</h2>
        <div class="profile">
            <span>Login sebagai: <b><%= ((com.pustaka.models.Admin)session.getAttribute("admin")).getNama() %></b></span>
            <a href="AdminController?action=logout" style="margin-left:15px; color:red;">Logout</a>
        </div>
    </div>

    <div class="content">
        <div style="display:flex; justify-content:space-between; align-items:center;">
            <h3>Daftar Buku Pustaka</h3>
            <a href="AdminController?action=form" class="input-btn" style="text-decoration:none; width:auto;">+ Tambah Buku Baru</a>
        </div>

        <table class="table-admin">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Judul</th>
                    <th>Penulis</th>
                    <th>Kategori</th>
                    <th>Harga</th>
                    <th width="150">Aksi</th>
                </tr>
            </thead>
            <tbody>
                <% if(list != null) { for(Buku b : list) { %>
                <tr>
                    <td><%= b.getId() %></td>
                    <td><%= b.getJudul() %></td>
                    <td><%= b.getPenulis() %></td>
                    <td><%= b.getKategori() %></td>
                    <td>Rp <%= (int)b.getHarga() %></td>
                    <td>
                        <a href="AdminController?action=form&id=<%= b.getId() %>" class="btn-small btn-edit">Edit</a>
                        <a href="AdminController?action=delete&id=<%= b.getId() %>" class="btn-small btn-del" onclick="return confirm('Hapus buku ini?')">Hapus</a>
                    </td>
                </tr>
                <% }} %>
            </tbody>
        </table>
    </div>
</body>
</html>