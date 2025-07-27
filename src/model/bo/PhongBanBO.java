package model.bo;

import model.bean.PhongBan;
import model.dao.PhongBanDAO;
import model.dao.NhanVienDAO;
import java.util.List;

public class PhongBanBO {
    private PhongBanDAO phongBanDAO;
    private NhanVienDAO nhanVienDAO;

    public PhongBanBO() {
        this.phongBanDAO = new PhongBanDAO();
        this.nhanVienDAO = new NhanVienDAO();
    }

    public List<PhongBan> getAllPhongBan() {
        return phongBanDAO.getAllPhongBan();
    }

    public PhongBan getPhongBanById(String idPB) {
        if (idPB == null || idPB.trim().isEmpty()) {
            return null;
        }
        return phongBanDAO.getPhongBanById(idPB.trim());
    }

    public String createPhongBan(PhongBan phongBan) {
        String validationError = validatePhongBan(phongBan);
        if (validationError != null) {
            return validationError;
        }

        if (phongBanDAO.isPhongBanExists(phongBan.getIdPB())) {
            return "Mã phòng ban đã tồn tại!";
        }

        phongBan.setIdPB(phongBan.getIdPB().trim().toUpperCase());
        phongBan.setTenPB(phongBan.getTenPB().trim());
        if (phongBan.getMoTa() != null) {
            phongBan.setMoTa(phongBan.getMoTa().trim());
        }

        boolean success = phongBanDAO.insertPhongBan(phongBan);
        return success ? null : "Không thể tạo phòng ban!";
    }

    public String updatePhongBan(PhongBan phongBan) {
        if (phongBan.getIdPB() == null || phongBan.getIdPB().trim().isEmpty()) {
            return "Mã phòng ban không hợp lệ!";
        }

        PhongBan existingPhongBan = phongBanDAO.getPhongBanById(phongBan.getIdPB());
        if (existingPhongBan == null) {
            return "Phòng ban không tồn tại!";
        }

        String validationError = validatePhongBanForUpdate(phongBan);
        if (validationError != null) {
            return validationError;
        }

        phongBan.setTenPB(phongBan.getTenPB().trim());
        if (phongBan.getMoTa() != null) {
            phongBan.setMoTa(phongBan.getMoTa().trim());
        }

        boolean success = phongBanDAO.updatePhongBan(phongBan);
        return success ? null : "Không thể cập nhật phòng ban!";
    }

    public String deletePhongBan(String idPB) {
        if (idPB == null || idPB.trim().isEmpty()) {
            return "Mã phòng ban không hợp lệ!";
        }

        PhongBan existingPhongBan = phongBanDAO.getPhongBanById(idPB);
        if (existingPhongBan == null) {
            return "Phòng ban không tồn tại!";
        }

        int employeeCount = nhanVienDAO.countNhanVienByPhongBan(idPB);
        if (employeeCount > 0) {
            return "Không thể xóa phòng ban vì còn có " + employeeCount + " nhân viên!";
        }

        boolean success = phongBanDAO.deletePhongBan(idPB);
        return success ? null : "Không thể xóa phòng ban!";
    }

    public List<PhongBan> searchPhongBan(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllPhongBan();
        }
        return phongBanDAO.searchPhongBanByName(keyword.trim());
    }

    public String getPhongBanStatistics(String idPB) {
        if (idPB == null || idPB.trim().isEmpty()) {
            return "Mã phòng ban không hợp lệ!";
        }

        PhongBan phongBan = phongBanDAO.getPhongBanById(idPB);
        if (phongBan == null) {
            return "Phòng ban không tồn tại!";
        }

        int employeeCount = nhanVienDAO.countNhanVienByPhongBan(idPB);

        StringBuilder stats = new StringBuilder();
        stats.append("Thông tin phòng ban: ").append(phongBan.getTenPB()).append("\n");
        stats.append("Mã phòng ban: ").append(phongBan.getIdPB()).append("\n");
        stats.append("Số nhân viên: ").append(employeeCount).append("\n");
        stats.append("Mô tả: ").append(phongBan.getMoTa() != null ? phongBan.getMoTa() : "Không có");

        return stats.toString();
    }

    private String validatePhongBan(PhongBan phongBan) {
        if (phongBan == null) {
            return "Dữ liệu phòng ban không hợp lệ!";
        }

        if (phongBan.getIdPB() == null || phongBan.getIdPB().trim().isEmpty()) {
            return "Mã phòng ban không được để trống!";
        }

        if (phongBan.getIdPB().trim().length() > 255) {
            return "Mã phòng ban không được vượt quá 255 ký tự!";
        }

        if (phongBan.getTenPB() == null || phongBan.getTenPB().trim().isEmpty()) {
            return "Tên phòng ban không được để trống!";
        }

        if (phongBan.getTenPB().trim().length() > 255) {
            return "Tên phòng ban không được vượt quá 255 ký tự!";
        }

        if (!phongBan.getIdPB().trim().matches("^[A-Za-z0-9_]+$")) {
            return "Mã phòng ban chỉ được chứa chữ cái, số và dấu gạch dưới!";
        }

        return null;
    }

    private String validatePhongBanForUpdate(PhongBan phongBan) {
        if (phongBan == null) {
            return "Dữ liệu phòng ban không hợp lệ!";
        }

        if (phongBan.getTenPB() == null || phongBan.getTenPB().trim().isEmpty()) {
            return "Tên phòng ban không được để trống!";
        }

        if (phongBan.getTenPB().trim().length() > 255) {
            return "Tên phòng ban không được vượt quá 255 ký tự!";
        }

        return null;
    }

    public int getTotalPhongBan() {
        return phongBanDAO.countPhongBan();
    }
}