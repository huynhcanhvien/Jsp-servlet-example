package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Admin;
import model.bo.LoginBO;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LoginBO loginBO;

    public LoginController() {
        super();
        this.loginBO = new LoginBO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set encoding for Vietnamese characters
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");

        if (action == null) {
            // Show login form
            showLoginForm(request, response);
        } else if (action.equals("login")) {
            // Process login
            processLogin(request, response);
        } else if (action.equals("logout")) {
            // Process logout
            processLogout(request, response);
        } else {
            // Default to login form
            showLoginForm(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Show login form
     */
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            // Already logged in, redirect to main page
            response.sendRedirect("index.jsp");
            return;
        }

        // Forward to login page
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Process login request
     */
    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Validate and process login
            String errorMessage = loginBO.processLogin(username, password);

            if (errorMessage != null) {
                // Login failed
                request.setAttribute("error", errorMessage);
                request.setAttribute("username", username); // Keep username for user convenience

                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            } else {
                // Login successful
                Admin admin = loginBO.login(username, password);

                if (admin != null) {
                    // Create session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("admin", admin);
                    session.setAttribute("username", admin.getUsername());

                    // Set session timeout (30 minutes)
                    session.setMaxInactiveInterval(30 * 60);

                    // Redirect to main page
                    response.sendRedirect("index.jsp");
                } else {
                    // This shouldn't happen, but handle it
                    request.setAttribute("error", "Có lỗi xảy ra trong quá trình đăng nhập!");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                    dispatcher.forward(request, response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Process logout request
     */
    private void processLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get current session
            HttpSession session = request.getSession(false);

            if (session != null) {
                // Invalidate session
                session.invalidate();
            }

            // Redirect to login page with logout message
            request.setAttribute("message", "Bạn đã đăng xuất thành công!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // Even if there's an error, redirect to login
            response.sendRedirect("LoginController");
        }
    }
}