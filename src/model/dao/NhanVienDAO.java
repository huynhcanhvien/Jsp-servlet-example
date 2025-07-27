package model.dao;

import model.bean.NhanVien;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT IDNV, HoTen, IDPB, DiaChi FROM nhanvien ORDER BY IDNV";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setIdNV(rs.getString("IDNV"));
                nhanVien.setHoTen(rs.getString("HoTen"));
                nhanVien.setIdPB(rs.getString("IDPB"));
                nhanVien.setDiaChi(rs.getString("DiaChi"));
                nhanVienList.add(nhanVien);
            }
        } catch (SQLException e) {
            System.err.println("Error in getAllNhanVien: " + e.getMessage());
            e.printStackTrace();
        }
        return nhanVienList;
    }

    public NhanVien getNhanVienById(String idNV) {
        String sql = "SELECT IDNV, HoTen, IDPB, DiaChi FROM nhanvien WHERE IDNV = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idNV);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setIdNV(rs.getString("IDNV"));
                nhanVien.setHoTen(rs.getString("HoTen"));
                nhanVien.setIdPB(rs.getString("IDPB"));
                nhanVien.setDiaChi(rs.getString("DiaChi"));
                return nhanVien;
            }
        } catch (SQLException e) {
            System.err.println("Error in getNhanVienById: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<NhanVien> getNhanVienByPhongBan(String idPB) {
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT IDNV, HoTen, IDPB, DiaChi FROM nhanvien WHERE IDPB = ? ORDER BY HoTen";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idPB);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setIdNV(rs.getString("IDNV"));
                nhanVien.setHoTen(rs.getString("HoTen"));
                nhanVien.setIdPB(rs.getString("IDPB"));
                nhanVien.setDiaChi(rs.getString("DiaChi"));
                nhanVienList.add(nhanVien);
            }
        } catch (SQLException e) {
            System.err.println("Error in getNhanVienByPhongBan: " + e.getMessage());
            e.printStackTrace();
        }
        return nhanVienList;
    }

    public boolean insertNhanVien(NhanVien nhanVien) {
        String sql = "INSERT INTO nhanvien (IDNV, HoTen, IDPB, DiaChi) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nhanVien.getIdNV());
            stmt.setString(2, nhanVien.getHoTen());
            stmt.setString(3, nhanVien.getIdPB());
            stmt.setString(4, nhanVien.getDiaChi());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in insertNhanVien: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateNhanVien(NhanVien nhanVien) {
        String sql = "UPDATE nhanvien SET HoTen = ?, IDPB = ?, DiaChi = ? WHERE IDNV = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nhanVien.getHoTen());
            stmt.setString(2, nhanVien.getIdPB());
            stmt.setString(3, nhanVien.getDiaChi());
            stmt.setString(4, nhanVien.getIdNV());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in updateNhanVien: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteNhanVien(String idNV) {
        String sql = "DELETE FROM nhanvien WHERE IDNV = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idNV);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in deleteNhanVien: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isNhanVienExists(String idNV) {
        String sql = "SELECT COUNT(*) FROM nhanvien WHERE IDNV = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idNV);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error in isNhanVienExists: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<NhanVien> searchNhanVienByName(String hoTen) {
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT IDNV, HoTen, IDPB, DiaChi FROM nhanvien WHERE HoTen LIKE ? ORDER BY HoTen";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + hoTen + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setIdNV(rs.getString("IDNV"));
                nhanVien.setHoTen(rs.getString("HoTen"));
                nhanVien.setIdPB(rs.getString("IDPB"));
                nhanVien.setDiaChi(rs.getString("DiaChi"));
                nhanVienList.add(nhanVien);
            }
        } catch (SQLException e) {
            System.err.println("Error in searchNhanVienByName: " + e.getMessage());
            e.printStackTrace();
        }
        return nhanVienList;
    }

    public List<NhanVien> getNhanVienWithPhongBan() {
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT n.IDNV, n.HoTen, n.IDPB, n.DiaChi, p.TenPB " +
                "FROM nhanvien n LEFT JOIN phongban p ON n.IDPB = p.IDPB " +
                "ORDER BY n.HoTen";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setIdNV(rs.getString("IDNV"));
                nhanVien.setHoTen(rs.getString("HoTen"));
                nhanVien.setIdPB(rs.getString("IDPB"));
                nhanVien.setDiaChi(rs.getString("DiaChi"));
                nhanVienList.add(nhanVien);
            }
        } catch (SQLException e) {
            System.err.println("Error in getNhanVienWithPhongBan: " + e.getMessage());
            e.printStackTrace();
        }
        return nhanVienList;
    }

    public int countNhanVienByPhongBan(String idPB) {
        String sql = "SELECT COUNT(*) FROM nhanvien WHERE IDPB = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idPB);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error in countNhanVienByPhongBan: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public int countNhanVien() {
        String sql = "SELECT COUNT(*) FROM nhanvien";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error in countNhanVien: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}