<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Login - Pustaka Digital</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body style="background:#333; display:flex; justify-content:center; align-items:center; height:100vh;">
    <div class="form-card" style="width:300px;">
        <h3 style="text-align:center;">ADMINISTRATOR</h3>
        <form action="AdminController" method="POST">
            <input type="hidden" name="action" value="login">
            <input class="input-box" type="text" name="username" placeholder="Username" required>
            <input class="input-box" type="password" name="password" placeholder="Password" required>
            <button class="input-btn" style="background-color:#d32f2f;">Masuk Sistem</button>
        </form>
        <br>
        <center><a href="index.jsp" style="font-size:12px;">Kembali ke Halaman User</a></center>
    </div>
</body>
</html>