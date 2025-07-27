package model.bo;

import model.bean.Admin;
import model.dao.LoginDAO;

public class LoginBO {
    private LoginDAO loginDAO;

    public LoginBO() {
        this.loginDAO = new LoginDAO();
    }

    public String processLogin(String username, String password) {
        String validationError = validateLoginInput(username, password);
        if (validationError != null) {
            return validationError;
        }

        username = username.trim();
        password = password.trim();

        Admin admin = loginDAO.checkLogin(username, password);
        if (admin == null) {
            return "Tên đăng nhập hoặc mật khẩu không đúng!";
        }

        return null;
    }

    public Admin login(String username, String password) {
        if (validateLoginInput(username, password) != null) {
            return null;
        }

        username = username.trim();
        password = password.trim();

        return loginDAO.checkLogin(username, password);
    }

    public boolean isAdminExists(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return loginDAO.isAdminExists(username.trim());
    }

    public String changePassword(String username, String currentPassword, String newPassword) {
        String validationError = validatePasswordChange(username, currentPassword, newPassword);
        if (validationError != null) {
            return validationError;
        }

        username = username.trim();
        currentPassword = currentPassword.trim();
        newPassword = newPassword.trim();

        Admin admin = loginDAO.checkLogin(username, currentPassword);
        if (admin == null) {
            return "Mật khẩu hiện tại không đúng!";
        }

        boolean success = loginDAO.changePassword(username, newPassword);
        return success ? null : "Không thể thay đổi mật khẩu!";
    }

    private String validateLoginInput(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "Tên đăng nhập không được để trống!";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Mật khẩu không được để trống!";
        }

        if (username.trim().length() > 50) {
            return "Tên đăng nhập không được vượt quá 50 ký tự!";
        }

        if (password.trim().length() > 100) {
            return "Mật khẩu không được vượt quá 100 ký tự!";
        }

        if (!username.trim().matches("^[a-zA-Z0-9_]+$")) {
            return "Tên đăng nhập chỉ được chứa chữ cái, số và dấu gạch dưới!";
        }

        return null;
    }

    private String validatePasswordChange(String username, String currentPassword, String newPassword) {
        String basicValidation = validateLoginInput(username, currentPassword);
        if (basicValidation != null) {
            return basicValidation;
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            return "Mật khẩu mới không được để trống!";
        }

        if (newPassword.trim().length() < 6) {
            return "Mật khẩu mới phải có ít nhất 6 ký tự!";
        }

        if (newPassword.trim().length() > 100) {
            return "Mật khẩu mới không được vượt quá 100 ký tự!";
        }

        if (currentPassword.trim().equals(newPassword.trim())) {
            return "Mật khẩu mới phải khác mật khẩu hiện tại!";
        }

        return null;
    }
}