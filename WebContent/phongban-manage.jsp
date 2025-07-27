<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.PhongBan" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Phòng ban</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .header-info {
            background-color: #e3f2fd;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #28a745;
            color: white;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #e8f5e8;
        }
        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin-right: 5px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn-warning {
            background-color: #ffc107;
            color: #212529;
        }
        .no-data {
            text-align: center;
            padding: 40px;
            color: #666;
            font-style: italic;
        }
        .navigation {
            margin-bottom: 20px;
        }
        .success-message {
            background-color: #d4edda;
            color: #155724;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Navigation -->
        <div class="navigation">
            <a href="index.jsp" class="btn btn-secondary">← Trang chủ</a>
            <a href="PhongBanController?action=list" class="btn btn-secondary">Xem danh sách công khai</a>
        </div>

        <h1>Quản lý Phòng ban</h1>
        
        <!-- Display messages -->
        <%
            String success = (String) request.getAttribute("success");
            String error = (String) request.getAttribute("error");
            if (success != null) {
        %>
        <div class="success-message">
            <%= success %>
        </div>
        <%
            }
            if (error != null) {
        %>
        <div class="error-message">
            <%= error %>
        </div>
        <%
            }
        %>
        
        <!-- Header Information -->
        <div class="header-info">
            <strong>Tổng số phòng ban: </strong>
            <%= request.getAttribute("totalPhongBan") != null ? request.getAttribute("totalPhongBan") : 0 %>
        </div>

        <!-- Department Table -->
        <%
            @SuppressWarnings("unchecked")
            List<PhongBan> listPhongBan = (List<PhongBan>) request.getAttribute("listPhongBan");
            
            if (listPhongBan != null && !listPhongBan.isEmpty()) {
        %>
        <table>
            <thead>
                <tr>
                    <th style="width: 15%;">Mã phòng ban</th>
                    <th style="width: 25%;">Tên phòng ban</th>
                    <th style="width: 40%;">Mô tả</th>
                    <th style="width: 20%;">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (PhongBan phongBan : listPhongBan) {
                %>
                <tr>
                    <td><strong><%= phongBan.getIdPB() %></strong></td>
                    <td><%= phongBan.getTenPB() %></td>
                    <td>
                        <%= phongBan.getMoTa() != null && !phongBan.getMoTa().trim().isEmpty() 
                            ? phongBan.getMoTa() : "<em>Chưa có mô tả</em>" %>
                    </td>
                    <td>
                        <a href="PhongBanController?action=showUpdate&idPB=<%= phongBan.getIdPB() %>" 
                           class="btn btn-warning">
                            Cập nhật
                        </a>
                        <a href="NhanVienController?action=listByPhongBan&idPB=<%= phongBan.getIdPB() %>" 
                           class="btn btn-primary">
                            Xem nhân viên
                        </a>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <%
            } else {
        %>
        <div class="no-data">
            <h3>Không có dữ liệu phòng ban</h3>
            <p>Hiện tại chưa có phòng ban nào trong hệ thống.</p>
        </div>
        <%
            }
        %>
    </div>
</body>
</html>