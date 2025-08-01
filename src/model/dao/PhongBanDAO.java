package model.dao;

import model.bean.PhongBan;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongBanDAO {

    public List<PhongBan> getAllPhongBan() {
        List<PhongBan> phongBanList = new ArrayList<>();
        String sql = "SELECT IDPB, TenPB, MoTa FROM phongban ORDER BY IDPB";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PhongBan phongBan = new PhongBan();
                phongBan.setIdPB(rs.getString("IDPB"));
                phongBan.setTenPB(rs.getString("TenPB"));
                phongBan.setMoTa(rs.getString("MoTa"));
                phongBanList.add(phongBan);
            }
        } catch (SQLException e) {
            System.err.println("Error in getAllPhongBan: " + e.getMessage());
            e.printStackTrace();
        }
        return phongBanList;
    }

    public PhongBan getPhongBanById(String idPB) {
        String sql = "SELECT IDPB, TenPB, MoTa FROM phongban WHERE IDPB = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idPB);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                PhongBan phongBan = new PhongBan();
                phongBan.setIdPB(rs.getString("IDPB"));
                phongBan.setTenPB(rs.getString("TenPB"));
                phongBan.setMoTa(rs.getString("MoTa"));
                return phongBan;
            }
        } catch (SQLException e) {
            System.err.println("Error in getPhongBanById: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertPhongBan(PhongBan phongBan) {
        String sql = "INSERT INTO phongban (IDPB, TenPB, MoTa) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phongBan.getIdPB());
            stmt.setString(2, phongBan.getTenPB());
            stmt.setString(3, phongBan.getMoTa());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in insertPhongBan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePhongBan(PhongBan phongBan) {
        String sql = "UPDATE phongban SET TenPB = ?, MoTa = ? WHERE IDPB = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phongBan.getTenPB());
            stmt.setString(2, phongBan.getMoTa());
            stmt.setString(3, phongBan.getIdPB());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in updatePhongBan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePhongBan(String idPB) {
        String sql = "DELETE FROM phongban WHERE IDPB = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idPB);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in deletePhongBan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPhongBanExists(String idPB) {
        String sql = "SELECT COUNT(*) FROM phongban WHERE IDPB = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idPB);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error in isPhongBanExists: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<PhongBan> searchPhongBanByName(String tenPB) {
        List<PhongBan> phongBanList = new ArrayList<>();
        String sql = "SELECT IDPB, TenPB, MoTa FROM phongban WHERE TenPB LIKE ? ORDER BY TenPB";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + tenPB + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PhongBan phongBan = new PhongBan();
                phongBan.setIdPB(rs.getString("IDPB"));
                phongBan.setTenPB(rs.getString("TenPB"));
                phongBan.setMoTa(rs.getString("MoTa"));
                phongBanList.add(phongBan);
            }
        } catch (SQLException e) {
            System.err.println("Error in searchPhongBanByName: " + e.getMessage());
            e.printStackTrace();
        }
        return phongBanList;
    }

    public int countPhongBan() {
        String sql = "SELECT COUNT(*) FROM phongban";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error in countPhongBan: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}