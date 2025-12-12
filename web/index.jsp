<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Pustaka Digital - Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h1 style="text-align: center; margin-bottom: 20px;">Pustaka Digital 📚</h1>
        
        <div class="nav" style="justify-content: center;">
            <a href="index.jsp?mode=login" class="nav-item">Login</a> | 
            <a href="index.jsp?mode=register" class="nav-item">Register</a>
        </div>

        <% 
            String mode = request.getParameter("mode");
            if(mode == null || mode.equals("login")) { 
        %>
            <div class="form-card">
                <h3>Masuk Akun</h3>
                <form action="AuthController" method="POST">
                    <input type="hidden" name="action" value="login">
                    <input class="input-box" type="email" name="email" placeholder="Email" required>
                    <input class="input-box" type="password" name="password" placeholder="Password" required>
                    <button type="submit" class="input-btn">Login Masuk</button>
                </form>
            </div>
        <% } else { %>
            <div class="form-card">
                <h3>Daftar Baru</h3>
                <form action="AuthController" method="POST">
                    <input type="hidden" name="action" value="register">
                    <input class="input-box" type="text" name="nama" placeholder="Nama Lengkap" required>
                    <input class="input-box" type="email" name="email" placeholder="Email" required>
                    <input class="input-box" type="password" name="password" placeholder="Password" required>
                    <button type="submit" class="input-btn">Daftar Sekarang</button>
                </form>
            </div>
        <% } %>
    </div>
</body>
</html>