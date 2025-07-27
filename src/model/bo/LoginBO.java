package model.bo;

import model.bean.Admin;
import model.dao.LoginDAO;

public class LoginBO {
    private LoginDAO loginDAO;

    public LoginBO() {
        this.loginDAO = new LoginDAO();
    }

    /**
     * Validate and process login
     * 
     * @param username Username
     * @param password Password
     * @return null if successful login, error message if failed
     */
    public String processLogin(String username, String password) {
        // Validate input
        String validationError = validateLoginInput(username, password);
        if (validationError != null) {
            return validationError;
        }

        // Trim whitespace
        username = username.trim();
        password = password.trim();

        // Check login credentials
        Admin admin = loginDAO.checkLogin(username, password);
        if (admin == null) {
            return "Tên đăng nhập hoặc mật khẩu không đúng!";
        }

        return null; // Login successful
    }

    /**
     * Get admin information after successful login
     * 
     * @param username Username
     * @param password Password
     * @return Admin object if login successful, null if failed
     */
    public Admin login(String username, String password) {
        // Validate input
        if (validateLoginInput(username, password) != null) {
            return null;
        }

        // Trim whitespace
        username = username.trim();
        password = password.trim();

        // Check login credentials
        return loginDAO.checkLogin(username, password);
    }

    /**
     * Check if admin exists
     * 
     * @param username Username to check
     * @return true if exists, false otherwise
     */
    public boolean isAdminExists(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return loginDAO.isAdminExists(username.trim());
    }

    /**
     * Change password with validation
     * 
     * @param username        Username
     * @param currentPassword Current password
     * @param newPassword     New password
     * @return null if successful, error message if failed
     */
    public String changePassword(String username, String currentPassword, String newPassword) {
        // Validate input
        String validationError = validatePasswordChange(username, currentPassword, newPassword);
        if (validationError != null) {
            return validationError;
        }

        // Trim whitespace
        username = username.trim();
        currentPassword = currentPassword.trim();
        newPassword = newPassword.trim();

        // Verify current password
        Admin admin = loginDAO.checkLogin(username, currentPassword);
        if (admin == null) {
            return "Mật khẩu hiện tại không đúng!";
        }

        // Change password
        boolean success = loginDAO.changePassword(username, newPassword);
        return success ? null : "Không thể thay đổi mật khẩu!";
    }

    /**
     * Validate login input
     * 
     * @param username Username to validate
     * @param password Password to validate
     * @return null if valid, error message if invalid
     */
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

        // Check for invalid characters in username
        if (!username.trim().matches("^[a-zA-Z0-9_]+$")) {
            return "Tên đăng nhập chỉ được chứa chữ cái, số và dấu gạch dưới!";
        }

        return null;
    }

    /**
     * Validate password change input
     * 
     * @param username        Username
     * @param currentPassword Current password
     * @param newPassword     New password
     * @return null if valid, error message if invalid
     */
    private String validatePasswordChange(String username, String currentPassword, String newPassword) {
        // Validate basic input
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