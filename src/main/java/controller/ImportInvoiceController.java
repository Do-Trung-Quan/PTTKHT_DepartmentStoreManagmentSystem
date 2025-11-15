package controller;

import dao.ImportInvoiceDAO;
import model.Product;
import model.Category;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.ImportInvoice;
import model.ImportProduct;
import model.ManagementStaff;
import model.Supplier;

@WebServlet(name = "ImportInvoiceController", urlPatterns = {"/ImportInvoiceController"})
public class ImportInvoiceController extends HttpServlet {

    private ImportInvoiceDAO importInvoiceDAO;

    @Override
    public void init() {
        importInvoiceDAO = new ImportInvoiceDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "save":
                    saveImportInvoice(request, response);
                    break;
                default:
                    showPrintImportInvoice(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "❌ Lỗi xảy ra: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/PrintImportInvoiceView.jsp");
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
    private void showPrintImportInvoice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/PrintImportInvoiceView.jsp");
        dispatcher.forward(request, response);
    }
    
   private void saveImportInvoice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Date importDate = Date.valueOf(LocalDate.parse(request.getParameter("date")));
        System.out.println(importDate);
        float total = Float.parseFloat(request.getParameter("total"));
        
        ManagementStaff mnStaff = (ManagementStaff) request.getSession().getAttribute("user");
        Supplier supplier = (Supplier) request.getSession().getAttribute("supplier");        
        List<ImportProduct> dsImportProduct = (List<ImportProduct>) request.getSession().getAttribute("dsImportProduct");
        
        ImportInvoice importInvoice = new ImportInvoice(0, importDate, total, supplier, mnStaff, dsImportProduct);
        boolean success = importInvoiceDAO.addImportInvoice(importInvoice);
        
        if (success) {
                request.setAttribute("message", "✅ Lưu hóa đơn nhập thành công!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/ManagementStaffHomeView.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("message", "❌ Thêm thất bại, we are cooked!");
                showPrintImportInvoice(request, response);
            }
    }
}