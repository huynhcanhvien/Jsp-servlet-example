package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.NhanVien;
import model.bean.PhongBan;
import model.bo.NhanVienBO;
import model.bo.PhongBanBO;

/**
 * Servlet implementation class NhanVienController
 */
@WebServlet("/NhanVienController")
public class NhanVienController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private NhanVienBO nhanVienBO;
    private PhongBanBO phongBanBO;

    public NhanVienController() {
        super();
        this.nhanVienBO = new NhanVienBO();
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
            // Display list of all employees
            listAllNhanVien(request, response);
        } else if (action.equals("listByPhongBan")) {
            // Display list of employees by department
            listNhanVienByPhongBan(request, response);
        } else if (action.equals("showAdd")) {
            // Show add employee form (requires login)
            if (!checkLogin(request, response))
                return;
            showAddForm(request, response);
        } else if (action.equals("add")) {
            // Process add employee (requires login)
            if (!checkLogin(request, response))
                return;
            addNhanVien(request, response);
        } else if (action.equals("delete")) {
            // Delete employee (requires login)
            if (!checkLogin(request, response))
                return;
            deleteNhanVien(request, response);
        } else if (action.equals("manageList")) {
            // Show management list (requires login)
            if (!checkLogin(request, response))
                return;
            showManageList(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Display list of all employees
     */
    private void listAllNhanVien(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get all employees with department information
            List<NhanVien> listNhanVien = nhanVienBO.getNhanVienWithPhongBan();

            // Set attributes to pass to JSP
            request.setAttribute("listNhanVien", listNhanVien);
            request.setAttribute("totalNhanVien", nhanVienBO.getTotalNhanVien());
            request.setAttribute("pageTitle", "Danh sách tất cả nhân viên");

            // Forward to JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("nhanvien-list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi lấy danh sách nhân viên: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Display list of employees by department
     */
    private void listNhanVienByPhongBan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idPB = request.getParameter("idPB");

            if (idPB == null || idPB.trim().isEmpty()) {
                // If no department ID, redirect to all employees
                listAllNhanVien(request, response);
                return;
            }

            // Get department information
            PhongBan phongBan = phongBanBO.getPhongBanById(idPB);
            if (phongBan == null) {
                request.setAttribute("error", "Không tìm thấy phòng ban với mã: " + idPB);
                RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Get employees by department
            List<NhanVien> listNhanVien = nhanVienBO.getNhanVienByPhongBan(idPB);

            // Set attributes to pass to JSP
            request.setAttribute("listNhanVien", listNhanVien);
            request.setAttribute("phongBan", phongBan);
            request.setAttribute("totalNhanVien", listNhanVien.size());
            request.setAttribute("pageTitle", "Danh sách nhân viên - " + phongBan.getTenPB());

            // Forward to JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("nhanvien-list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error",
                    "Có lỗi xảy ra khi lấy danh sách nhân viên theo phòng ban: " + e.getMessage());
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
     * Show add employee form
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get all departments for dropdown
            List<PhongBan> listPhongBan = phongBanBO.getAllPhongBan();
            request.setAttribute("listPhongBan", listPhongBan);

            // Forward to add form
            RequestDispatcher dispatcher = request.getRequestDispatcher("nhanvien-add.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải form thêm nhân viên: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Add new employee
     */
    private void addNhanVien(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idNV = request.getParameter("idNV");
            String hoTen = request.getParameter("hoTen");
            String idPB = request.getParameter("idPB");
            String diaChi = request.getParameter("diaChi");

            // Create NhanVien object
            NhanVien nhanVien = new NhanVien(idNV, hoTen, idPB, diaChi);

            // Create employee
            String errorMessage = nhanVienBO.createNhanVien(nhanVien);

            if (errorMessage != null) {
                // Add failed
                request.setAttribute("error", errorMessage);
                request.setAttribute("nhanVien", nhanVien);

                // Get departments again for dropdown
                List<PhongBan> listPhongBan = phongBanBO.getAllPhongBan();
                request.setAttribute("listPhongBan", listPhongBan);

                RequestDispatcher dispatcher = request.getRequestDispatcher("nhanvien-add.jsp");
                dispatcher.forward(request, response);
            } else {
                // Add successful
                request.setAttribute("success", "Thêm nhân viên thành công!");
                showManageList(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi thêm nhân viên: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Delete employee
     */
    private void deleteNhanVien(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idNV = request.getParameter("idNV");

            // Delete employee
            String errorMessage = nhanVienBO.deleteNhanVien(idNV);

            if (errorMessage != null) {
                request.setAttribute("error", errorMessage);
            } else {
                request.setAttribute("success", "Xóa nhân viên thành công!");
            }

            showManageList(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi xóa nhân viên: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Show management list with add/delete functions
     */
    private void showManageList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get all employees with department information
            List<NhanVien> listNhanVien = nhanVienBO.getNhanVienWithPhongBan();

            // Set attributes to pass to JSP
            request.setAttribute("listNhanVien", listNhanVien);
            request.setAttribute("totalNhanVien", nhanVienBO.getTotalNhanVien());
            request.setAttribute("pageTitle", "Quản lý nhân viên");
            request.setAttribute("isManagePage", true);

            // Forward to JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("nhanvien-manage.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi lấy danh sách nhân viên: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }
}