package model.bo;

import model.bean.PhongBan;
import model.dao.PhongBanDAO;
import model.dao.NhanVienDAO;
import java.util.List;

/**
 * Business Object for PhongBan operations
 */
public class PhongBanBO {
    private PhongBanDAO phongBanDAO;
    private NhanVienDAO nhanVienDAO;
    
    public PhongBanBO() {
        this.phongBanDAO = new PhongBanDAO();
        this.nhanVienDAO = new NhanVienDAO();
    }
    
    /**
     * Get all departments
     * @return List of PhongBan objects
     */
    public List<PhongBan> getAllPhongBan() {
        return phongBanDAO.getAllPhongBan();
    }
    
    /**
     * Get department by ID
     * @param idPB Department ID
     * @return PhongBan object or null if not found
     */
    public PhongBan getPhongBanById(String idPB) {
        if (idPB == null || idPB.trim().isEmpty()) {
            return null;
        }
        return phongBanDAO.getPhongBanById(idPB.trim());
    }
    
    /**
     * Create new department with validation
     * @param phongBan PhongBan object to create
     * @return null if successful, error message if failed
     */
    public String createPhongBan(PhongBan phongBan) {
        // Validate input
        String validationError = validatePhongBan(phongBan);
        if (validationError != null) {
            return validationError;
        }
        
        // Check if department ID already exists
        if (phongBanDAO.isPhongBanExists(phongBan.getIdPB())) {
            return "Mã phòng ban đã tồn tại!";
        }
        
        // Trim whitespace
        phongBan.setIdPB(phongBan.getIdPB().trim().toUpperCase());
        phongBan.setTenPB(phongBan.getTenPB().trim());
        if (phongBan.getMoTa() != null) {
            phongBan.setMoTa(phongBan.getMoTa().trim());
        }
        
        // Insert department
        boolean success = phongBanDAO.insertPhongBan(phongBan);
        return success ? null : "Không thể tạo phòng ban!";
    }
    
    /**
     * Update existing department
     * @param phongBan PhongBan object with updated information
     * @return null if successful, error message if failed
     */
    public String updatePhongBan(PhongBan phongBan) {
        if (phongBan.getIdPB() == null || phongBan.getIdPB().trim().isEmpty()) {
            return "Mã phòng ban không hợp lệ!";
        }
        
        // Check if department exists
        PhongBan existingPhongBan = phongBanDAO.getPhongBanById(phongBan.getIdPB());
        if (existingPhongBan == null) {
            return "Phòng ban không tồn tại!";
        }
        
        // Validate updated data
        String validationError = validatePhongBanForUpdate(phongBan);
        if (validationError != null) {
            return validationError;
        }
        
        // Trim whitespace
        phongBan.setTenPB(phongBan.getTenPB().trim());
        if (phongBan.getMoTa() != null) {
            phongBan.setMoTa(phongBan.getMoTa().trim());
        }
        
        // Update department
        boolean success = phongBanDAO.updatePhongBan(phongBan);
        return success ? null : "Không thể cập nhật phòng ban!";
    }
    
    /**
     * Delete department with business logic validation
     * @param idPB Department ID to delete
     * @return null if successful, error message if failed
     */
    public String deletePhongBan(String idPB) {
        if (idPB == null || idPB.trim().isEmpty()) {
            return "Mã phòng ban không hợp lệ!";
        }
        
        // Check if department exists
        PhongBan existingPhongBan = phongBanDAO.getPhongBanById(idPB);
        if (existingPhongBan == null) {
            return "Phòng ban không tồn tại!";
        }
        
        // Check if department has employees
        int employeeCount = nhanVienDAO.countNhanVienByPhongBan(idPB);
        if (employeeCount > 0) {
            return "Không thể xóa phòng ban vì còn có " + employeeCount + " nhân viên!";
        }
        
        // Delete department
        boolean success = phongBanDAO.deletePhongBan(idPB);
        return success ? null : "Không thể xóa phòng ban!";
    }
    
    /**
     * Search departments by name
     * @param keyword Search keyword
     * @return List of matching PhongBan objects
     */
    public List<PhongBan> searchPhongBan(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllPhongBan();
        }
        return phongBanDAO.searchPhongBanByName(keyword.trim());
    }
    
    /**
     * Get department statistics
     * @param idPB Department ID
     * @return String containing department statistics
     */
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
    
    /**
     * Validate PhongBan data for creation
     * @param phongBan PhongBan object to validate
     * @return null if valid, error message if invalid
     */
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
        
        // Validate ID format (only alphanumeric and underscore)
        if (!phongBan.getIdPB().trim().matches("^[A-Za-z0-9_]+$")) {
            return "Mã phòng ban chỉ được chứa chữ cái, số và dấu gạch dưới!";
        }
        
        return null;
    }
    
    /**
     * Validate PhongBan data for update
     * @param phongBan PhongBan object to validate
     * @return null if valid, error message if invalid
     */
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
    
    /**
     * Get total number of departments
     * @return Total count of departments
     */
    public int getTotalPhongBan() {
        return phongBanDAO.countPhongBan();
    }
}