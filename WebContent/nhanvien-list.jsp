<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.NhanVien" %>
<%@ page import="model.bean.PhongBan" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <%= request.getAttribute("pageTitle") != null ? 
            request.getAttribute("pageTitle") : "Danh sách Nhân viên" %>
    </title>
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
        .department-info {
            background-color: #fff3cd;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border-left: 4px solid #ffc107;
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
            background-color: #17a2b8;
            color: white;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #e8f4f8;
        }
        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #545b62;
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
        .navigation a {
            margin-right: 15px;
        }
        .back-link {
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>
            <%= request.getAttribute("pageTitle") != null ? 
                request.getAttribute("pageTitle") : "Danh sách Nhân viên" %>
        </h1>
        
        <!-- Navigation Menu -->
        <div class="navigation">
            <a href="PhongBanController?action=list" class="btn btn-primary">Danh sách Phòng ban</a>
            <a href="NhanVienController?action=list" class="btn btn-primary">Tất cả Nhân viên</a>
        </div>
        
        <!-- Department Information (if viewing by department) -->
        <%
            PhongBan phongBan = (PhongBan) request.getAttribute("phongBan");
            if (phongBan != null) {
        %>
        <div class="back-link">
            <a href="PhongBanController?action=list" class="btn btn-secondary">← Quay lại danh sách phòng ban</a>
        </div>
        
        <div class="department-info">
            <h3>Thông tin phòng ban</h3>
            <p><strong>Mã phòng ban:</strong> <%= phongBan.getIdPB() %></p>
            <p><strong>Tên phòng ban:</strong> <%= phongBan.getTenPB() %></p>
            <% if (phongBan.getMoTa() != null && !phongBan.getMoTa().trim().isEmpty()) { %>
            <p><strong>Mô tả:</strong> <%= phongBan.getMoTa() %></p>
            <% } %>
        </div>
        <%
            }
        %>
        
        <!-- Header Information -->
        <div class="header-info">
            <strong>Tổng số nhân viên: </strong>
            <%= request.getAttribute("totalNhanVien") != null ? request.getAttribute("totalNhanVien") : 0 %>
            <% if (phongBan != null) { %>
                <span> trong phòng ban <strong><%= phongBan.getTenPB() %></strong></span>
            <% } %>
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
                    <th style="width: 45%;">Địa chỉ</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (NhanVien nhanVien : listNhanVien) {
                %>
                <tr>
                    <td><strong><%= nhanVien.getIdNV() %></strong></td>
                    <td><%= nhanVien.getHoTen() %></td>
                    <td>
                        <% if (phongBan == null) { %>
                            <a href="NhanVienController?action=listByPhongBan&idPB=<%= nhanVien.getIdPB() %>" 
                               style="color: #007bff; text-decoration: none;">
                                <%= nhanVien.getIdPB() %>
                            </a>
                        <% } else { %>
                            <%= nhanVien.getIdPB() %>
                        <% } %>
                    </td>
                    <td>
                        <%= nhanVien.getDiaChi() != null && !nhanVien.getDiaChi().trim().isEmpty() 
                            ? nhanVien.getDiaChi() : "<em>Chưa cập nhật</em>" %>
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
            <% if (phongBan != null) { %>
                <p>Phòng ban <strong><%= phongBan.getTenPB() %></strong> hiện tại chưa có nhân viên nào.</p>
            <% } else { %>
                <p>Hiện tại chưa có nhân viên nào trong hệ thống.</p>
            <% } %>
        </div>
        <%
            }
        %>
    </div>
    <div style="text-align: center; margin-top: 30px;">
        <a href="index.jsp" class="btn btn-primary">← Quay về Trang chủ</a>
    </div>
</body>
</html>