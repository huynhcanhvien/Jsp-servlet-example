<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.PhongBan" %>
<%@ page import="model.bean.NhanVien" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Nhân viên</title>
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
        .form-group input, .form-group select, .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
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
    </style>
</head>
<body>
    <div class="container">
        <div class="navigation">
            <a href="index.jsp" class="btn btn-secondary">← Trang chủ</a>
            <a href="NhanVienController?action=manageList" class="btn btn-secondary">← Quản lý nhân viên</a>
        </div>

        <h1>Thêm Nhân viên mới</h1>
        
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
        
        <!-- Add Form -->
        <%
            NhanVien nhanVien = (NhanVien) request.getAttribute("nhanVien");
            @SuppressWarnings("unchecked")
            List<PhongBan> listPhongBan = (List<PhongBan>) request.getAttribute("listPhongBan");
        %>
        
        <form action="NhanVienController" method="post">
            <input type="hidden" name="action" value="add">
            
            <div class="form-group">
                <label for="idNV">Mã nhân viên: *</label>
                <input type="text" 
                       id="idNV" 
                       name="idNV" 
                       value="<%= nhanVien != null ? nhanVien.getIdNV() : "" %>"
                       required 
                       placeholder="Nhập mã nhân viên">
            </div>
            
            <div class="form-group">
                <label for="hoTen">Họ tên: *</label>
                <input type="text" 
                       id="hoTen" 
                       name="hoTen" 
                       value="<%= nhanVien != null ? nhanVien.getHoTen() : "" %>"
                       required 
                       placeholder="Nhập họ tên">
            </div>
            
            <div class="form-group">
                <label for="idPB">Phòng ban: *</label>
                <select id="idPB" name="idPB" required>
                    <option value="">-- Chọn phòng ban --</option>
                    <%
                        if (listPhongBan != null) {
                            for (PhongBan pb : listPhongBan) {
                                String selected = "";
                                if (nhanVien != null && pb.getIdPB().equals(nhanVien.getIdPB())) {
                                    selected = "selected";
                                }
                    %>
                    <option value="<%= pb.getIdPB() %>" <%= selected %>>
                        <%= pb.getIdPB() %> - <%= pb.getTenPB() %>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            
            <div class="form-group">
                <label for="diaChi">Địa chỉ:</label>
                <textarea id="diaChi" 
                          name="diaChi" 
                          rows="3" 
                          placeholder="Nhập địa chỉ"><%= nhanVien != null && nhanVien.getDiaChi() != null ? nhanVien.getDiaChi() : "" %></textarea>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Thêm nhân viên</button>
                <a href="NhanVienController?action=manageList" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
    </div>
</body>
</html>