package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.PhongBan;
import model.bo.PhongBanBO;

/**
 * Servlet implementation class PhongBanController
 */
@WebServlet("/PhongBanController")
public class PhongBanController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PhongBanBO phongBanBO;

    public PhongBanController() {
        super();
        this.phongBanBO = new PhongBanBO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set encoding for Vietnamese characters
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            // Display list of departments
            listPhongBan(request, response);
        } else if (action.equals("manageList")) {
            // Show management list (requires login)
            if (!checkLogin(request, response))
                return;
            showManageList(request, response);
        } else if (action.equals("showUpdate")) {
            // Show update form (requires login)
            if (!checkLogin(request, response))
                return;
            showUpdateForm(request, response);
        } else if (action.equals("update")) {
            // Process update (requires login)
            if (!checkLogin(request, response))
                return;
            updatePhongBan(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Display list of all departments
     */
    private void listPhongBan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get all departments from BO
            List<PhongBan> listPhongBan = phongBanBO.getAllPhongBan();

            // Set attribute to pass to JSP
            request.setAttribute("listPhongBan", listPhongBan);
            request.setAttribute("totalPhongBan", phongBanBO.getTotalPhongBan());

            // Forward to JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("phongban-list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi lấy danh sách phòng ban: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Check if user is logged in
     */
    private boolean checkLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            response.sendRedirect("LoginController");
            return false;
        }
        return true;
    }

    /**
     * Show management list
     */
    private void showManageList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get all departments from BO
            List<PhongBan> listPhongBan = phongBanBO.getAllPhongBan();

            // Set attribute to pass to JSP
            request.setAttribute("listPhongBan", listPhongBan);
            request.setAttribute("totalPhongBan", phongBanBO.getTotalPhongBan());
            request.setAttribute("isManagePage", true);

            // Forward to JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("phongban-manage.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi lấy danh sách phòng ban: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Show update form
     */
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idPB = request.getParameter("idPB");

            if (idPB == null || idPB.trim().isEmpty()) {
                request.setAttribute("error", "Mã phòng ban không hợp lệ!");
                showManageList(request, response);
                return;
            }

            // Get department by ID
            PhongBan phongBan = phongBanBO.getPhongBanById(idPB);

            if (phongBan == null) {
                request.setAttribute("error", "Không tìm thấy phòng ban!");
                showManageList(request, response);
                return;
            }

            // Set attribute and forward to update form
            request.setAttribute("phongBan", phongBan);
            RequestDispatcher dispatcher = request.getRequestDispatcher("phongban-update.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải form cập nhật: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Update department
     */
    private void updatePhongBan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idPB = request.getParameter("idPB");
            String tenPB = request.getParameter("tenPB");
            String moTa = request.getParameter("moTa");

            // Create PhongBan object
            PhongBan phongBan = new PhongBan(idPB, tenPB, moTa);

            // Update department
            String errorMessage = phongBanBO.updatePhongBan(phongBan);

            if (errorMessage != null) {
                // Update failed
                request.setAttribute("error", errorMessage);
                request.setAttribute("phongBan", phongBan);

                RequestDispatcher dispatcher = request.getRequestDispatcher("phongban-update.jsp");
                dispatcher.forward(request, response);
            } else {
                // Update successful
                request.setAttribute("success", "Cập nhật phòng ban thành công!");
                showManageList(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi cập nhật phòng ban: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }
}