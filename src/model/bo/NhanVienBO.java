package model.bo;

import model.bean.NhanVien;
import model.dao.NhanVienDAO;
import model.dao.PhongBanDAO;
import java.util.List;

public class NhanVienBO {
    private NhanVienDAO nhanVienDAO;
    private PhongBanDAO phongBanDAO;

    public NhanVienBO() {
        this.nhanVienDAO = new NhanVienDAO();
        this.phongBanDAO = new PhongBanDAO();
    }

    public List<NhanVien> getAllNhanVien() {
        return nhanVienDAO.getAllNhanVien();
    }

    public NhanVien getNhanVienById(String idNV) {
        if (idNV == null || idNV.trim().isEmpty()) {
            return null;
        }
        return nhanVienDAO.getNhanVienById(idNV.trim());
    }

    public List<NhanVien> getNhanVienByPhongBan(String idPB) {
        if (idPB == null || idPB.trim().isEmpty()) {
            return getAllNhanVien();
        }
        return nhanVienDAO.getNhanVienByPhongBan(idPB.trim());
    }

    public String createNhanVien(NhanVien nhanVien) {
        String validationError = validateNhanVien(nhanVien);
        if (validationError != null) {
            return validationError;
        }

        if (nhanVienDAO.isNhanVienExists(nhanVien.getIdNV())) {
            return "Mã nhân viên đã tồn tại!";
        }

        if (!phongBanDAO.isPhongBanExists(nhanVien.getIdPB())) {
            return "Phòng ban không tồn tại!";
        }

        nhanVien.setIdNV(nhanVien.getIdNV().trim().toUpperCase());
        nhanVien.setHoTen(formatName(nhanVien.getHoTen().trim()));
        nhanVien.setIdPB(nhanVien.getIdPB().trim().toUpperCase());
        if (nhanVien.getDiaChi() != null) {
            nhanVien.setDiaChi(nhanVien.getDiaChi().trim());
        }

        boolean success = nhanVienDAO.insertNhanVien(nhanVien);
        return success ? null : "Không thể tạo nhân viên!";
    }

    public String updateNhanVien(NhanVien nhanVien) {
        if (nhanVien.getIdNV() == null || nhanVien.getIdNV().trim().isEmpty()) {
            return "Mã nhân viên không hợp lệ!";
        }

        NhanVien existingNhanVien = nhanVienDAO.getNhanVienById(nhanVien.getIdNV());
        if (existingNhanVien == null) {
            return "Nhân viên không tồn tại!";
        }

        String validationError = validateNhanVienForUpdate(nhanVien);
        if (validationError != null) {
            return validationError;
        }

        if (!phongBanDAO.isPhongBanExists(nhanVien.getIdPB())) {
            return "Phòng ban không tồn tại!";
        }

        nhanVien.setHoTen(formatName(nhanVien.getHoTen().trim()));
        nhanVien.setIdPB(nhanVien.getIdPB().trim().toUpperCase());
        if (nhanVien.getDiaChi() != null) {
            nhanVien.setDiaChi(nhanVien.getDiaChi().trim());
        }

        boolean success = nhanVienDAO.updateNhanVien(nhanVien);
        return success ? null : "Không thể cập nhật nhân viên!";
    }

    public String deleteNhanVien(String idNV) {
        if (idNV == null || idNV.trim().isEmpty()) {
            return "Mã nhân viên không hợp lệ!";
        }

        NhanVien existingNhanVien = nhanVienDAO.getNhanVienById(idNV);
        if (existingNhanVien == null) {
            return "Nhân viên không tồn tại!";
        }

        boolean success = nhanVienDAO.deleteNhanVien(idNV);
        return success ? null : "Không thể xóa nhân viên!";
    }

    public List<NhanVien> searchNhanVien(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllNhanVien();
        }
        return nhanVienDAO.searchNhanVienByName(keyword.trim());
    }

    public List<NhanVien> getNhanVienWithPhongBan() {
        return nhanVienDAO.getNhanVienWithPhongBan();
    }

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

        if (!nhanVien.getIdNV().trim().matches("^[A-Za-z0-9_]+$")) {
            return "Mã nhân viên chỉ được chứa chữ cái, số và dấu gạch dưới!";
        }

        if (!nhanVien.getHoTen().trim().matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
            return "Họ tên chỉ được chứa chữ cái và khoảng trắng!";
        }

        return null;
    }

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

        if (!nhanVien.getHoTen().trim().matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
            return "Họ tên chỉ được chứa chữ cái và khoảng trắng!";
        }

        return null;
    }

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

    public int getTotalNhanVien() {
        return nhanVienDAO.countNhanVien();
    }

    public int getNhanVienCountByPhongBan(String idPB) {
        if (idPB == null || idPB.trim().isEmpty()) {
            return 0;
        }
        return nhanVienDAO.countNhanVienByPhongBan(idPB);
    }
}