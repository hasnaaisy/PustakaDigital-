<%@page import="java.util.ArrayList"%>
<%@page import="com.pustaka.models.Transaksi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Riwayat Transaksi</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .table-history { width: 100%; border-collapse: collapse; margin-top: 20px; }
        .table-history th, .table-history td { padding: 15px; text-align: left; border-bottom: 1px solid #ddd; }
        .btn-back { padding: 10px 20px; background: #333; color: white; text-decoration: none; border-radius: 4px; font-size: 14px;}
    </style>
</head>
<body>
    <div class="header">
        <h2>Pustaka Digital 📚</h2>
        <div class="profile">
            <span>Riwayat Belanja</span>
            <a href="AuthController?action=logout" style="margin-left: 15px;"><img src="img/exit.png" width="20"></a>
        </div>
    </div>

    <div class="content">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px;">
            <div>
                <h1>Riwayat Transaksi</h1>
                <p>Berikut adalah daftar buku yang sudah kamu miliki.</p>
            </div>
            <a href="BukuController" class="btn-back">← Kembali ke Katalog</a>
        </div>

        <table class="table-history">
            <thead>
                <tr style="background: #f9f9f9;">
                    <th>ID Trx</th>
                    <th>Judul Buku</th>
                    <th>Penulis</th>
                    <th>Tanggal</th>
                    <th>Harga</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Transaksi> list = (ArrayList<Transaksi>) request.getAttribute("historyList");
                    if(list != null && !list.isEmpty()) {
                        for (Transaksi t : list) {
                %>
                <tr>
                    <td>#<%= t.getId() %></td>
                    <td style="font-weight: bold;"><%= t.getBuku().getJudul() %></td>
                    <td><%= t.getBuku().getPenulis() %></td>
                    <td><%= t.getTanggal() %></td>
                    <td style="color: green;">Rp <%= (int)t.getTotalHarga() %></td>
                </tr>
                <%      }
                    } else {
                %>
                <tr>
                    <td colspan="5" style="text-align: center; padding: 30px;">Belum ada riwayat transaksi. Yuk beli buku!</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>