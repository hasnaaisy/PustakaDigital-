<%@page import="java.util.ArrayList"%>
<%@page import="com.pustaka.models.Buku"%>
<%@page import="com.pustaka.models.Wishlist"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if(session.getAttribute("user") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    int userId = (int) session.getAttribute("userId");
    Wishlist wsModel = new Wishlist();
    ArrayList<Buku> wishItems = wsModel.getItems(userId);
    
    double totalHarga = 0;
    for(Buku b : wishItems) {
        totalHarga += b.getHarga();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Wishlist Saya</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        .cart-container { padding: 40px; width: 800px; margin: 0 auto; }
        .cart-table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        .cart-table th { background: #ffebeb; padding: 15px; text-align: left; color: #d32f2f; }
        .cart-table td { padding: 15px; border-bottom: 1px solid #ddd; }
        .total-section { text-align: right; margin-top: 30px; font-size: 18px; font-weight: bold; }
        .btn-checkout { background-color: #d32f2f; color: white; padding: 12px 30px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; margin-top: 10px; }
        .btn-checkout:hover { background-color: #b71c1c; }
        .btn-remove { background: none; border: 1px solid #ccc; padding: 5px 10px; border-radius: 4px; cursor: pointer; color: #555; }
        .btn-remove:hover { background: #eee; }
    </style>
</head>
<body>
    <div class="header">
        <h2>Wishlist Saya ❤️</h2>
        <div class="profile">
            <a href="BukuController" style="text-decoration: none; color: #333;">← Kembali ke Katalog</a>
        </div>
    </div>

    <div class="cart-container">
        <% if (wishItems.isEmpty()) { %>
            <div style="text-align: center; margin-top: 50px;">
                <h1 style="font-size: 50px;">💔</h1>
                <h3>Wishlist kamu masih kosong</h3>
                <p>Simpan buku favoritmu di sini sebelum membelinya.</p>
                <a href="BukuController" class="input-btn" style="text-decoration: none; background-color: #d32f2f;">Cari Buku Favorit</a>
            </div>
        <% } else { %>
            <div style="margin-bottom: 20px;">
                <p>Berikut adalah buku-buku yang kamu tandai:</p>
            </div>

            <table class="cart-table">
                <thead>
                    <tr>
                        <th>Buku</th>
                        <th>Penulis</th>
                        <th>Harga</th>
                        <th>Aksi</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Buku b : wishItems) { %>
                    <tr>
                        <td style="font-weight: bold;"><%= b.getJudul() %></td>
                        <td><%= b.getPenulis() %></td>
                        <td style="color: green;">Rp <%= (int)b.getHarga() %></td>
                        <td>
                            <form action="WishlistController" method="POST" style="display:inline;">
                                <input type="hidden" name="bukuId" value="<%= b.getId() %>">
                                <input type="hidden" name="type" value="remove">
                                <button type="submit" class="btn-remove" title="Hapus dari Wishlist">Hapus ❌</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <div class="total-section">
                <p>Total Estimasi: <span style="color: #d32f2f;">Rp <%= (int)totalHarga %></span></p>
                
                <form id="form-checkout-all" action="TransaksiController" method="POST">
                    <input type="hidden" name="action" value="checkout_all">
                    <button type="button" class="btn-checkout" onclick="konfirmasiCheckout()">Beli Semua Sekarang</button>
                </form>
            </div>
        <% } %>
    </div>

    <script>
        function konfirmasiCheckout() {
            Swal.fire({
                title: 'Checkout Wishlist?',
                text: "Semua buku di wishlist akan dibeli sekaligus!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d32f2f',
                cancelButtonColor: '#aaa',
                confirmButtonText: 'Ya, Bayar!',
                cancelButtonText: 'Batal'
            }).then((result) => {
                if (result.isConfirmed) {
                    document.getElementById('form-checkout-all').submit();
                    Swal.fire('Berhasil!', 'Pembelian sedang diproses.', 'success');
                }
            })
        }
    </script>
</body>
</html>