<%@page import="java.util.ArrayList"%>
<%@page import="com.pustaka.models.Buku"%>
<%@page import="com.pustaka.models.Wishlist"%>
<%@page import="com.pustaka.models.Transaksi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if(session.getAttribute("user") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    
    ArrayList<Buku> list = (ArrayList<Buku>) request.getAttribute("listBuku");
    int userId = (int) session.getAttribute("userId");
    
    Wishlist wsHelper = new Wishlist(); 
    Transaksi trxHelper = new Transaksi();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Katalog Pustaka</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    
    <style>
        .header { background: #fff; padding: 15px 40px; display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #ddd; }
        .card-grid { display: flex; flex-wrap: wrap; gap: 20px; padding: 40px; justify-content: center; }
        .book-card { border: 1px solid #eee; border-radius: 8px; width: 220px; padding: 15px; background: white; transition: 0.3s; display: flex; flex-direction: column; justify-content: space-between; }
        .book-card:hover { box-shadow: 0 5px 15px rgba(0,0,0,0.1); transform: translateY(-3px); }
        .badge { font-size: 10px; background: #e0e0e0; padding: 3px 8px; border-radius: 12px; color: #555; display: inline-block; margin-bottom: 5px;}
        .btn-love { border: 1px solid #ddd; background: white; cursor: pointer; padding: 8px; border-radius: 5px; }
        .btn-love.active { background: #ffe0e0; border-color: #ffcccc; color: red; }
        .btn-buy-action { padding: 6px 15px; margin:0; font-size: 12px; width: 100%; cursor: pointer;}
        .btn-disabled { background-color: #4CAF50; color: white; border: none; cursor: default; opacity: 0.8; }
    </style>
</head>
<body>
    <div class="header">
        <h2>Pustaka Digital 📚</h2>
        <div class="profile">
            <span>Hi, <b><%= ((com.pustaka.models.Pembeli)session.getAttribute("user")).getNama() %></b></span>
            <a href="wishlist.jsp" style="margin-left: 15px; text-decoration:none; font-size:20px;" title="Lihat Wishlist Saya">❤️</a>
            <a href="TransaksiController" style="margin-left: 10px; text-decoration:none; font-size:20px;" title="Riwayat Belanja">📜</a>
            <a href="AuthController?action=logout" style="margin-left: 15px;" title="Logout"><img src="img/exit.png" width="20"></a>
        </div>
    </div>

    <div style="text-align: center; margin-top: 30px;">
        <form action="BukuController" method="GET">
            <input type="text" name="search" placeholder="Cari judul buku atau kategori..." class="search-box" style="width: 400px; padding: 10px;">
            <button type="submit" class="btn-search" style="padding: 10px 20px; cursor: pointer;">Cari</button>
        </form>
    </div>

    <div class="card-grid">
        <% 
        if (list != null) { 
            for(Buku b : list) { 
                boolean isWished = wsHelper.isWished(b.getId(), userId);
                boolean isPurchased = trxHelper.isPurchased(b.getId(), userId);
        %>
        <div class="book-card">
            <div>
                <span class="badge"><%= b.getKategori() %></span>
                <h3 style="margin: 10px 0; font-size: 16px;"><%= b.getJudul() %></h3>
                <p style="font-size: 12px; color: #777;"><%= b.getPenulis() %></p>
                <p style="font-size: 12px; margin-bottom: 10px;"><%= b.getTahun() %> | <%= b.getHalaman() %> Hal</p>
            </div>
            
            <div>
                <div style="border-top: 1px solid #eee; margin: 10px 0;"></div>
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
                    <span style="font-weight: bold; font-size: 14px;">Rp <%= (int)b.getHarga() %></span>
                    
                    <form action="WishlistController" method="POST" style="margin:0;">
                        <input type="hidden" name="bukuId" value="<%= b.getId() %>">
                        <% if(isWished) { %>
                            <input type="hidden" name="type" value="remove">
                            <button type="submit" class="btn-love active" title="Hapus dari Wishlist">❤️</button>
                        <% } else { %>
                            <input type="hidden" name="type" value="add">
                            <button type="submit" class="btn-love" title="Tambah ke Wishlist">🤍</button>
                        <% } %>
                    </form>
                </div>

                <% if (isPurchased) { %>
                    <button class="btn-buy-action btn-disabled" disabled>Sudah Dibeli ✅</button>
                <% } else { %>
                    <form id="form-beli-<%= b.getId() %>" action="TransaksiController" method="POST" style="margin:0;">
                        <input type="hidden" name="bukuId" value="<%= b.getId() %>">
                        <input type="hidden" name="harga" value="<%= b.getHarga() %>">
                        
                        <button type="button" class="input-btn btn-buy-action" 
                                onclick="konfirmasiBeli('<%= b.getId() %>', '<%= b.getJudul() %>', '<%= (int)b.getHarga() %>')">
                            Beli Sekarang
                        </button>
                    </form>
                <% } %>
            </div>
        </div>
        <% 
            }
        } else {
        %>
            <p style="text-align: center; color: #777;">Tidak ada buku yang ditemukan.</p>
        <% } %>
    </div>

    <script>
        function konfirmasiBeli(id, judul, harga) {
            Swal.fire({
                title: 'Konfirmasi Pembelian',
                html: "Apakah kamu yakin ingin membeli buku <b>" + judul + "</b> seharga <b style='color:green'>Rp " + harga + "</b>?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Ya, Beli!',
                cancelButtonText: 'Batal'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Submit form secara manual via ID
                    document.getElementById('form-beli-' + id).submit();
                    
                    // Pemanis: Tampilkan loading sebentar
                    Swal.fire({
                        title: 'Memproses...',
                        timer: 2000,
                        didOpen: () => { Swal.showLoading() }
                    })
                }
            })
        }
    </script>
</body>
</html>