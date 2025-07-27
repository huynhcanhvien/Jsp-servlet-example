package model.bo;

import model.bean.NhanVien;
import model.dao.NhanVienDAO;
import model.dao.PhongBanDAO;
import java.util.List;

/**
 * Business Object for NhanVien operations
 */
public class NhanVienBO {
    private NhanVienDAO nhanVienDAO;
    private PhongBanDAO phongBanDAO;
    
    public NhanVienBO() {
        this.nhanVienDAO = new NhanVienDAO();
        this.phongBanDAO = new PhongBanDAO();
    }
    
    /**
     * Get all employees
     * @return List of NhanVien objects
     */
    public List<NhanVien> getAllNhanVien() {
        return nhanVienDAO.getAllNhanVien();
    }
    
    /**
     * Get employee by ID
     * @param idNV Employee ID
     * @return NhanVien object or null if not found
     */
    public NhanVien getNhanVienById(String idNV) {
        if (idNV == null || idNV.trim().isEmpty()) {
            return null;
        }
        return nhanVienDAO.getNhanVienById(idNV.trim());
    }
    
    /**
     * Get employees by department
     * @param idPB Department ID
     * @return List of NhanVien objects in the department
     */
    public List<NhanVien> getNhanVienByPhongBan(String idPB) {
        if (idPB == null || idPB.trim().isEmpty()) {
            return getAllNhanVien();
        }
        return nhanVienDAO.getNhanVienByPhongBan(idPB.trim());
    }
    
    /**
     * Create new employee with validation
     * @param nhanVien NhanVien object to create
     * @return null if successful, error message if failed
     */
    public String createNhanVien(NhanVien nhanVien) {
        // Validate input
        String validationError = validateNhanVien(nhanVien);
        if (validationError != null) {
            return validationError;
        }
        
        // Check if employee ID already exists
        if (nhanVienDAO.isNhanVienExists(nhanVien.getIdNV())) {
            return "Mã nhân viên đã tồn tại!";
        }
        
        // Validate department exists
        if (!phongBanDAO.isPhongBanExists(nhanVien.getIdPB())) {
            return "Phòng ban không tồn tại!";
        }
        
        // Trim whitespace and format data
        nhanVien.setIdNV(nhanVien.getIdNV().trim().toUpperCase());
        nhanVien.setHoTen(formatName(nhanVien.getHoTen().trim()));
        nhanVien.setIdPB(nhanVien.getIdPB().trim().toUpperCase());
        if (nhanVien.getDiaChi() != null) {
            nhanVien.setDiaChi(nhanVien.getDiaChi().trim());
        }
        
        // Insert employee
        boolean success = nhanVienDAO.insertNhanVien(nhanVien);
        return success ? null : "Không thể tạo nhân viên!";
    }
    
    /**
     * Update existing employee
     * @param nhanVien NhanVien object with updated information
     * @return null if successful, error message if failed
     */
    public String updateNhanVien(NhanVien nhanVien) {
        if (nhanVien.getIdNV() == null || nhanVien.getIdNV().trim().isEmpty()) {
            return "Mã nhân viên không hợp lệ!";
        }
        
        // Check if employee exists
        NhanVien existingNhanVien = nhanVienDAO.getNhanVienById(nhanVien.getIdNV());
        if (existingNhanVien == null) {
            return "Nhân viên không tồn tại!";
        }
        
        // Validate updated data
        String validationError = validateNhanVienForUpdate(nhanVien);
        if (validationError != null) {
            return validationError;
        }
        
        // Validate department exists
        if (!phongBanDAO.isPhongBanExists(nhanVien.getIdPB())) {
            return "Phòng ban không tồn tại!";
        }
        
        // Trim whitespace and format data
        nhanVien.setHoTen(formatName(nhanVien.getHoTen().trim()));
        nhanVien.setIdPB(nhanVien.getIdPB().trim().toUpperCase());
        if (nhanVien.getDiaChi() != null) {
            nhanVien.setDiaChi(nhanVien.getDiaChi().trim());
        }
        
        // Update employee
        boolean success = nhanVienDAO.updateNhanVien(nhanVien);
        return success ? null : "Không thể cập nhật nhân viên!";
    }
    
    /**
     * Delete employee
     * @param idNV Employee ID to delete
     * @return null if successful, error message if failed
     */
    public String deleteNhanVien(String idNV) {
        if (idNV == null || idNV.trim().isEmpty()) {
            return "Mã nhân viên không hợp lệ!";
        }
        
        // Check if employee exists
        NhanVien existingNhanVien = nhanVienDAO.getNhanVienById(idNV);
        if (existingNhanVien == null) {
            return "Nhân viên không tồn tại!";
        }
        
        // Delete employee
        boolean success = nhanVienDAO.deleteNhanVien(idNV);
        return success ? null : "Không thể xóa nhân viên!";
    }
    
    /**
     * Search employees by name
     * @param keyword Search keyword
     * @return List of matching NhanVien objects
     */
    public List<NhanVien> searchNhanVien(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllNhanVien();
        }
        return nhanVienDAO.searchNhanVienByName(keyword.trim());
    }
    
    /**
     * Get employees with department information
     * @return List of NhanVien objects with department names
     */
    public List<NhanVien> getNhanVienWithPhongBan() {
        return nhanVienDAO.getNhanVienWithPhongBan();
    }
    
    /**
     * Validate NhanVien data for creation
     * @param nhanVien NhanVien object to validate
     * @return null if valid, error message if invalid
     */
    private String validateNhanVien(NhanVien nhanVien) {
        if (nhanVien == null) {
            return "Dữ liệu nhân viên không hợp lệ!";
        }
        
        if (nhanVien.getIdNV() == null || nhanVien.getIdNV().trim().isEmpty()) {
            return "Mã nhân viên không được để trống!";
        }
        
        if (nhanVien.getIdNV().trim().length() > 255) {
            return "Mã nhân viên không được vượt quá 255 ký tự!";
        }
        
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().trim().isEmpty()) {
            return "Họ tên không được để trống!";
        }
        
        if (nhanVien.getHoTen().trim().length() > 255) {
            return "Họ tên không được vượt quá 255 ký tự!";
        }
        
        if (nhanVien.getIdPB() == null || nhanVien.getIdPB().trim().isEmpty()) {
            return "Mã phòng ban không được để trống!";
        }
        
        if (nhanVien.getIdPB().trim().length() > 255) {
            return "Mã phòng ban không được vượt quá 255 ký tự!";
        }
        
        if (nhanVien.getDiaChi() != null && nhanVien.getDiaChi().trim().length() > 1000) {
            return "Địa chỉ không được vượt quá 1000 ký tự!";
        }
        
        // Validate ID format (only alphanumeric and underscore)
        if (!nhanVien.getIdNV().trim().matches("^[A-Za-z0-9_]+$")) {
            return "Mã nhân viên chỉ được chứa chữ cái, số và dấu gạch dưới!";
        }
        
        // Validate name format (only letters and spaces)
        if (!nhanVien.getHoTen().trim().matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
            return "Họ tên chỉ được chứa chữ cái và khoảng trắng!";
        }
        
        return null;
    }
    
    /**
     * Validate NhanVien data for update
     * @param nhanVien NhanVien object to validate
     * @return null if valid, error message if invalid
     */
    private String validateNhanVienForUpdate(NhanVien nhanVien) {
        if (nhanVien == null) {
            return "Dữ liệu nhân viên không hợp lệ!";
        }
        
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().trim().isEmpty()) {
            return "Họ tên không được để trống!";
        }
        
        if (nhanVien.getHoTen().trim().length() > 255) {
            return "Họ tên không được vượt quá 255 ký tự!";
        }
        
        if (nhanVien.getIdPB() == null || nhanVien.getIdPB().trim().isEmpty()) {
            return "Mã phòng ban không được để trống!";
        }
        
        if (nhanVien.getIdPB().trim().length() > 255) {
            return "Mã phòng ban không được vượt quá 255 ký tự!";
        }
        
        if (nhanVien.getDiaChi() != null && nhanVien.getDiaChi().trim().length() > 1000) {
            return "Địa chỉ không được vượt quá 1000 ký tự!";
        }
        
        // Validate name format (only letters and spaces)
        if (!nhanVien.getHoTen().trim().matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
            return "Họ tên chỉ được chứa chữ cái và khoảng trắng!";
        }
        
        return null;
    }
    
    /**
     * Format name with proper capitalization
     * @param name Original name
     * @return Formatted name
     */
    private String formatName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }
        
        String[] words = name.trim().split("\\s+");
        StringBuilder formattedName = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                formattedName.append(" ");
            }
            String word = words[i].toLowerCase();
            if (word.length() > 0) {
                formattedName.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    formattedName.append(word.substring(1));
                }
            }
        }
        
        return formattedName.toString();
    }
    
    /**
     * Get total number of employees
     * @return Total count of employees
     */
    public int getTotalNhanVien() {
        return nhanVienDAO.countNhanVien();
    }
    
    /**
     * Get employee count by department
     * @param idPB Department ID
     * @return Number of employees in department
     */
    public int getNhanVienCountByPhongBan(String idPB) {
        if (idPB == null || idPB.trim().isEmpty()) {
            return 0;
        }
        return nhanVienDAO.countNhanVienByPhongBan(idPB);
    }
}