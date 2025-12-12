<%@page import="com.pustaka.models.Buku"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if(session.getAttribute("admin") == null) { response.sendRedirect("admin_login.jsp"); return; }
    
    Buku b = (Buku) request.getAttribute("buku");
    boolean isEdit = (b != null);

    String currentKategori = isEdit ? b.getKategori() : "";
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= isEdit ? "Edit" : "Tambah" %> Buku</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        select.input-box {
            height: 45px;
            background-color: white;
        }
    </style>
</head>
<body>
    <div class="container" style="height:auto;">
        <h2 style="text-align:center; margin-bottom:20px;"><%= isEdit ? "Edit Buku" : "Tambah Buku Baru" %></h2>
        
        <form action="AdminController" method="POST">
            <input type="hidden" name="action" value="save">
            <input type="hidden" name="id" value="<%= isEdit ? b.getId() : "" %>">

            <label>Judul Buku</label>
            <input class="input-box" type="text" name="judul" value="<%= isEdit ? b.getJudul() : "" %>" required>

            <label>Penulis</label>
            <input class="input-box" type="text" name="penulis" value="<%= isEdit ? b.getPenulis() : "" %>" required>

            <div style="display:flex; gap:10px;">
                <div style="flex:1;">
                    <label>Kategori</label>
                    <select class="input-box" name="kategori" required>
                        <option value="" disabled <%= !isEdit ? "selected" : "" %>>-- Pilih Kategori --</option>
                        
                        <optgroup label="Fiksi">
                            <option value="Romance" <%= "Romance".equals(currentKategori) ? "selected" : "" %>>Romance</option>
                            <option value="Fantasy" <%= "Fantasy".equals(currentKategori) ? "selected" : "" %>>Fantasy</option>
                            <option value="Sci-Fi" <%= "Sci-Fi".equals(currentKategori) ? "selected" : "" %>>Science Fiction</option>
                            <option value="Horor" <%= "Horor".equals(currentKategori) ? "selected" : "" %>>Horor & Thriller</option>
                            <option value="Misteri" <%= "Misteri".equals(currentKategori) ? "selected" : "" %>>Misteri</option>
                            <option value="Komedi" <%= "Komedi".equals(currentKategori) ? "selected" : "" %>>Komedi</option>
                            <option value="Slice of Life" <%= "Slice of Life".equals(currentKategori) ? "selected" : "" %>>Slice of Life</option>
                            <option value="Puisi" <%= "Puisi".equals(currentKategori) ? "selected" : "" %>>Puisi & Sastra</option>
                        </optgroup>

                        <optgroup label="Non-Fiksi">
                            <option value="Edukasi" <%= "Edukasi".equals(currentKategori) ? "selected" : "" %>>Edukasi & Pelajaran</option>
                            <option value="Biografi" <%= "Biografi".equals(currentKategori) ? "selected" : "" %>>Biografi</option>
                            <option value="Sejarah" <%= "Sejarah".equals(currentKategori) ? "selected" : "" %>>Sejarah</option>
                            <option value="Bisnis" <%= "Bisnis".equals(currentKategori) ? "selected" : "" %>>Bisnis & Ekonomi</option>
                            <option value="Self-Improvement" <%= "Self-Improvement".equals(currentKategori) ? "selected" : "" %>>Self-Improvement</option>
                            <option value="Teknologi" <%= "Teknologi".equals(currentKategori) ? "selected" : "" %>>Teknologi & Komputer</option>
                            <option value="Agama" <%= "Agama".equals(currentKategori) ? "selected" : "" %>>Agama & Spiritual</option>
                            <option value="Seni" <%= "Seni".equals(currentKategori) ? "selected" : "" %>>Seni & Desain</option>
                            <option value="Masakan" <%= "Masakan".equals(currentKategori) ? "selected" : "" %>>Masakan & Kuliner</option>
                        </optgroup>

                        <option value="Komik" <%= "Komik".equals(currentKategori) ? "selected" : "" %>>Komik / Manga</option>
                        <option value="Anak" <%= "Anak".equals(currentKategori) ? "selected" : "" %>>Buku Anak</option>
                        <option value="Lainnya" <%= "Lainnya".equals(currentKategori) ? "selected" : "" %>>Lainnya</option>
                    </select>
                </div>
                <div style="flex:1;">
                    <label>Tahun Terbit</label>
                    <input class="input-box" type="number" name="tahun" value="<%= isEdit ? b.getTahun() : "" %>" required>
                </div>
            </div>

            <div style="display:flex; gap:10px;">
                <div style="flex:1;">
                    <label>Jumlah Halaman</label>
                    <input class="input-box" type="number" name="halaman" value="<%= isEdit ? b.getHalaman() : "" %>" required>
                </div>
                <div style="flex:1;">
                    <label>Harga (Rp)</label>
                    <input class="input-box" type="number" name="harga" value="<%= isEdit ? (int)b.getHarga() : "" %>" required>
                </div>
            </div>

            <button type="submit" class="input-btn">Simpan Data</button>
            <a href="AdminController?action=dashboard" class="btn-input-cancel" style="display:block; text-decoration:none; margin-top:10px;">Batal</a>
        </form>
    </div>
</body>
</html>