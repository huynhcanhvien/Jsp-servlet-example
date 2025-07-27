<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.NhanVien" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Nhân viên</title>
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
        .actions {
            margin-bottom: 20px;
            text-align: right;
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
        .btn-danger {
            background-color: #dc3545;
            color: white;
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
    <script>
        function confirmDelete(id, name) {
            if (confirm('Bạn có chắc chắn muốn xóa nhân viên "' + name + '" không?')) {
                window.location.href = 'NhanVienController?action=delete&idNV=' + id;
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <!-- Navigation -->
        <div class="navigation">
            <a href="index.jsp" class="btn btn-secondary">← Trang chủ</a>
            <a href="NhanVienController?action=list" class="btn btn-secondary">Xem danh sách công khai</a>
        </div>

        <h1>Quản lý Nhân viên</h1>
        
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
            <strong>Tổng số nhân viên: </strong>
            <%= request.getAttribute("totalNhanVien") != null ? request.getAttribute("totalNhanVien") : 0 %>
        </div>
        
        <!-- Actions -->
        <div class="actions">
            <a href="NhanVienController?action=showAdd" class="btn btn-primary">+ Thêm nhân viên</a>
        </div>

        <!-- Employee Table -->
        <%
            @SuppressWarnings("unchecked")
            List<NhanVien> listNhanVien = (List<NhanVien>) request.getAttribute("listNhanVien");
            
            if (listNhanVien != null && !listNhanVien.isEmpty()) {
        %>
        <table>
            <thead>
                <tr>
                    <th style="width: 15%;">Mã nhân viên</th>
                    <th style="width: 25%;">Họ tên</th>
                    <th style="width: 15%;">Mã phòng ban</th>
                    <th style="width: 30%;">Địa chỉ</th>
                    <th style="width: 15%;">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (NhanVien nhanVien : listNhanVien) {
                %>
                <tr>
                    <td><strong><%= nhanVien.getIdNV() %></strong></td>
                    <td><%= nhanVien.getHoTen() %></td>
                    <td><%= nhanVien.getIdPB() %></td>
                    <td>
                        <%= nhanVien.getDiaChi() != null && !nhanVien.getDiaChi().trim().isEmpty() 
                            ? nhanVien.getDiaChi() : "<em>Chưa cập nhật</em>" %>
                    </td>
                    <td>
                        <button onclick="confirmDelete('<%= nhanVien.getIdNV() %>', '<%= nhanVien.getHoTen() %>')" 
                                class="btn btn-danger">
                            Xóa
                        </button>
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
            <h3>Không có dữ liệu nhân viên</h3>
            <p>Hiện tại chưa có nhân viên nào trong hệ thống.</p>
            <a href="NhanVienController?action=showAdd" class="btn btn-primary">Thêm nhân viên đầu tiên</a>
        </div>
        <%
            }
        %>
    </div>
</body>
</html>