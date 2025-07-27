<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.bean.PhongBan" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Phòng ban</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        .form-group input, .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .form-group input[readonly] {
            background-color: #f8f9fa;
            color: #6c757d;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin-right: 10px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .form-actions {
            text-align: center;
            margin-top: 30px;
        }
        .navigation {
            margin-bottom: 20px;
        }
        .info-note {
            background-color: #d1ecf1;
            color: #0c5460;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="navigation">
            <a href="index.jsp" class="btn btn-secondary">← Trang chủ</a>
            <a href="PhongBanController?action=manageList" class="btn btn-secondary">← Quản lý phòng ban</a>
        </div>

        <h1>Cập nhật Phòng ban</h1>
        
        <div class="info-note">
            <strong>Lưu ý:</strong> Mã phòng ban không thể thay đổi. Chỉ có thể cập nhật tên phòng ban và mô tả.
        </div>
        
        <!-- Display error message -->
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
        <div class="error-message">
            <%= error %>
        </div>
        <%
            }
        %>
        
        <!-- Update Form -->
        <%
            PhongBan phongBan = (PhongBan) request.getAttribute("phongBan");
            if (phongBan != null) {
        %>
        
        <form action="PhongBanController" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="idPB" value="<%= phongBan.getIdPB() %>">
            
            <div class="form-group">
                <label for="idPB">Mã phòng ban:</label>
                <input type="text" 
                       id="idPB" 
                       value="<%= phongBan.getIdPB() %>"
                       readonly>
            </div>
            
            <div class="form-group">
                <label for="tenPB">Tên phòng ban: *</label>
                <input type="text" 
                       id="tenPB" 
                       name="tenPB" 
                       value="<%= phongBan.getTenPB() %>"
                       required 
                       placeholder="Nhập tên phòng ban">
            </div>
            
            <div class="form-group">
                <label for="moTa">Mô tả:</label>
                <textarea id="moTa" 
                          name="moTa" 
                          rows="4" 
                          placeholder="Nhập mô tả phòng ban"><%= phongBan.getMoTa() != null ? phongBan.getMoTa() : "" %></textarea>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Cập nhật</button>
                <a href="PhongBanController?action=manageList" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
        
        <%
            } else {
        %>
        <div class="error-message">
            Không tìm thấy thông tin phòng ban cần cập nhật.
        </div>
        <div class="form-actions">
            <a href="PhongBanController?action=manageList" class="btn btn-secondary">← Quay lại</a>
        </div>
        <%
            }
        %>
    </div>
</body>
</html>