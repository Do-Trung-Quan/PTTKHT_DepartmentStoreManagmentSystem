package controller;

import dao.SupplierDAO;
import model.Supplier;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SupplierController", urlPatterns = {"/SupplierController"})
public class SupplierController extends HttpServlet {

    private SupplierDAO supplierDAO;

    @Override
    public void init() {
        supplierDAO = new SupplierDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "search":
                    searchSupplier(request, response);
                    break;
                case "add":
                    addSupplier(request, response);
                    break;
                case "select":
                    selectSupplier(request, response);
                    break;
                default:
                    showSearchSupplier(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "❌ Lỗi xảy ra: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/SearchSupplierView.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Mở form quản lý nhà cung cấp (chưa hiển thị danh sách)
    private void showSearchSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/SearchSupplierView.jsp");
        dispatcher.forward(request, response);
    }
    
    // Mở form quản lý nhà cung cấp (chưa hiển thị danh sách)
    private void showAddSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/AddSupplierView.jsp");
        dispatcher.forward(request, response);
    }

    // Tìm kiếm nhà cung cấp theo tên
    private void searchSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("searchName").toLowerCase();
        List<Supplier> suppliers = supplierDAO.searchSupplierByName(name);

        if (suppliers.isEmpty()) {
//            request.setAttribute("message", "⚠️ Không tìm thấy nhà cung cấp nào.");
            request.getSession().setAttribute("suppliers", suppliers);
        } else {
            // 🟢 Tạm lưu list vào session để chọn supplier từ list đó
            request.getSession().setAttribute("suppliers", suppliers);
        }

        showSearchSupplier(request, response);
    }
    
    private void selectSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        // 🟢 Lấy danh sách suppliers từ session (nếu trước đó bạn đã set)
        List<Supplier> suppliers = (List<Supplier>) request.getSession().getAttribute("suppliers");
        Supplier selectedSupplier = null;
        if (suppliers != null) {
            for (Supplier s : suppliers) {
                if (s.getId() == id) {
                    selectedSupplier = s;
                    break;
                }
            }
        }
        if (selectedSupplier != null) {
            // ✅ Lưu supplier đang chọn vào session
            HttpSession session = request.getSession();
            session.setAttribute("supplier", selectedSupplier);

            // Chuyển sang SearchItemView
            RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/SearchItemView.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("message", "⚠️ Không tìm thấy nhà cung cấp trong danh sách hiện tại.");
            showSearchSupplier(request, response);
        }
    }

    // Thêm nhà cung cấp mới
    private void addSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        Supplier supplier = new Supplier(0, name, telephone, email, address);

        boolean success = supplierDAO.addSupplier(supplier);
        if (success) {
            request.setAttribute("message", "✅ Thêm nhà cung cấp thành công!");
            showSearchSupplier(request, response);
        } else {
            request.setAttribute("message", "❌ Thêm thất bại, nhà cung cấp đã tồn tại!");
            showAddSupplier(request, response);
        }
    }
}
