<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hệ thống Quản lý Nhân viên - Phòng ban</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 50px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            max-width: 700px;
            margin: 0 auto;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            border-bottom: 1px solid #eee;
            padding-bottom: 15px;
        }
        h1 {
            color: #333;
            margin: 0;
        }
        .user-info {
            text-align: right;
        }
        .welcome-msg {
            color: #666;
            margin-bottom: 5px;
        }
        .btn {
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }
        .btn-logout {
            background-color: #dc3545;
            color: white;
        }
        .btn-login {
            background-color: #28a745;
            color: white;
        }
        .menu-section {
            margin-bottom: 25px;
        }
        .menu-section h3 {
            color: #333;
            margin-bottom: 15px;
        }
        .menu {
            list-style: none;
            padding: 0;
        }
        .menu li {
            margin: 10px 0;
        }
        .menu a {
            display: block;
            padding: 12px 20px;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            text-align: center;
        }
        .menu-public a {
            background-color: #007bff;
        }
        .menu-public a:hover {
            background-color: #0056b3;
        }
        .menu-admin a {
            background-color: #28a745;
        }
        .menu-admin a:hover {
            background-color: #218838;
        }
        .disabled a {
            background-color: #6c757d !important;
            cursor: not-allowed;
        }
        .login-note {
            background-color: #fff3cd;
            padding: 15px;
            border-radius: 5px;
            margin-top: 15px;
            color: #856404;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1>Hệ thống Quản lý</h1>
            <div class="user-info">
                <%
                    String username = (String) session.getAttribute("username");
                    if (username != null) {
                %>
                <div class="welcome-msg">Xin chào, <strong><%= username %></strong></div>
                <a href="LoginController?action=logout" class="btn btn-logout">Đăng xuất</a>
                <%
                    } else {
                %>
                <a href="LoginController" class="btn btn-login">Đăng nhập</a>
                <%
                    }
                %>
            </div>
        </div>
        
        <!-- Public Functions -->
        <div class="menu-section">
            <h3>Chức năng xem thông tin</h3>
            <ul class="menu menu-public">
                <li>
                    <a href="PhongBanController?action=list">Xem danh sách Phòng ban</a>
                </li>
                <li>
                    <a href="NhanVienController?action=list">Xem danh sách Nhân viên</a>
                </li>
            </ul>
        </div>
        
        <!-- Admin Functions -->
        <div class="menu-section">
            <h3>Chức năng quản trị</h3>
            <%
                if (username != null) {
            %>
            <ul class="menu menu-admin">
                <li>
                    <a href="NhanVienController?action=showAdd">Thêm Nhân viên</a>
                </li>
                <li>
                    <a href="NhanVienController?action=manageList">Quản lý Nhân viên</a>
                </li>
                <li>
                    <a href="PhongBanController?action=manageList">Quản lý Phòng ban</a>
                </li>
            </ul>
            <%
                } else {
            %>
            <ul class="menu disabled">
                <li>
                    <a href="#">Thêm Nhân viên (Cần đăng nhập)</a>
                </li>
                <li>
                    <a href="#">Quản lý Nhân viên (Cần đăng nhập)</a>
                </li>
                <li>
                    <a href="#">Quản lý Phòng ban (Cần đăng nhập)</a>
                </li>
            </ul>
            <div class="login-note">
                <strong>Lưu ý:</strong> Bạn cần đăng nhập để sử dụng các chức năng quản trị.
            </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>