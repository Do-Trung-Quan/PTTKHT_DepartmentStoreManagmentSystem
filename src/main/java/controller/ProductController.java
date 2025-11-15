package controller;

import dao.ProductDAO;
import model.Product;
import model.Category;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.ImportProduct;

@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "search":
                    searchItem(request, response);
                    break;
                case "add":
                    addItem(request, response);
                    break;
                case "select":
                    selectItem(request, response);
                    break;
                case "update":
                    updateSelectedItem(request, response);
                    break;
                case "remove":
                    removeSelectedItem(request, response);
                    break;
                default:
                    showSearchItem(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "❌ Lỗi xảy ra: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/SearchItemView.jsp");
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

    private void showSearchItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/SearchItemView.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showAddItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/AddItemView.jsp");
        dispatcher.forward(request, response);
    }
    
    private void searchItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("searchName").toLowerCase();
        List<Product> products = productDAO.searchItemByName(name);
        
        HttpSession session = request.getSession();
        List<ImportProduct> dsImportProduct = (List<ImportProduct>) session.getAttribute("dsImportProduct");

        if (products.isEmpty()) {
            request.getSession().setAttribute("dsProduct", products);
        } else {
            if (dsImportProduct != null && !dsImportProduct.isEmpty()) {
                products.removeIf(p ->
                    dsImportProduct.stream().anyMatch(ip -> ip.getProduct().getId() == p.getId())
                );
            }
            request.getSession().setAttribute("dsProduct", products);
        }
        
        showSearchItem(request, response);
    }
    
    private void selectItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idProduct"));
        
        List<Product> products = (List<Product>) request.getSession().getAttribute("dsProduct");
        Product selectedItem = null;
        if (products != null) {
            for (Product p : products) {
                if (p.getId() == id) {
                    selectedItem = p;
                    break;
                }
            }
        }
        if (selectedItem != null) {
            // ✅ Lưu supplier đang chọn vào session
            HttpSession session = request.getSession();
            List<ImportProduct> ip = (List<ImportProduct>) session.getAttribute("dsImportProduct");
//            List<Product> p = (List<Product>) session.getAttribute("dsProduct");
            if(ip==null) ip = new ArrayList<>();
            ip.add(new ImportProduct(0, 1, 0, "", selectedItem));
//            p.remove(selectedItem);
            session.setAttribute("dsImportProduct", ip);
            session.setAttribute("dsProduct", null);

            showSearchItem(request, response);
        } else {
            request.setAttribute("message", "⚠️ Không tìm thấy sản phẩm trong danh sách hiện tại.");
            showSearchItem(request, response);
        }
    }
    
    private void updateSelectedItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idProduct = Integer.parseInt(request.getParameter("idProduct"));
            float importUnitPrice = Float.parseFloat(request.getParameter("importUnitPrice"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String note = request.getParameter("note");

            HttpSession session = request.getSession();
            List<ImportProduct> dsImportProduct = (List<ImportProduct>) session.getAttribute("dsImportProduct");

            if (dsImportProduct != null) {
                for (ImportProduct ip : dsImportProduct) {
                    if (ip.getProduct() != null && ip.getProduct().getId() == idProduct) {
                        ip.setImportUnitPrice(importUnitPrice);
                        ip.setQuantity(quantity);
                        ip.setImportPrice();
                        ip.setNote(note);
                        break;
                    }
                }

                // Lưu lại danh sách vào session
                session.setAttribute("dsImportProduct", dsImportProduct);
            } else {
                request.setAttribute("message", "⚠️ Danh sách sản phẩm rỗng!");
            }

        } catch (Exception e) {
            request.setAttribute("message", "❌ Lỗi khi cập nhật: " + e.getMessage());
        }
        
        showSearchItem(request, response);
    }

    private void removeSelectedItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idProduct = Integer.parseInt(request.getParameter("idProduct"));
            HttpSession session = request.getSession();

            List<ImportProduct> dsImportProduct = (List<ImportProduct>) session.getAttribute("dsImportProduct");

            if (dsImportProduct != null && !dsImportProduct.isEmpty()) {
                ImportProduct removed = null;
                for (ImportProduct ip : dsImportProduct) {
                    if (ip.getProduct() != null && ip.getProduct().getId() == idProduct) {
                        removed = ip;
                        break;
                    }
                }
                if (removed != null) {
                    dsImportProduct.remove(removed);
                }
                session.setAttribute("dsImportProduct", dsImportProduct);
            }
        } catch (Exception e) {
            request.setAttribute("message", "❌ Lỗi khi xóa: " + e.getMessage());
        }

        showSearchItem(request, response);
    }
    
    private void addItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        float unitPrice = Float.parseFloat(request.getParameter("unitPrice"));
        String note = request.getParameter("note");
        int id = Integer.parseInt(request.getParameter("CategoryId"));
        
        List<Category> categories = (List<Category>) request.getSession().getAttribute("dsCategory");
        Category selectedCategory = null;
        if (categories != null) {
            for (Category c : categories) {
                if (c.getId() == id) {
                    selectedCategory = c;
                    break;
                }
            }
        }
        if (selectedCategory != null) {
            Product product = new Product(0, name, 0, unitPrice, note, selectedCategory);

            boolean success = productDAO.addItem(product);
            if (success) {
                request.setAttribute("message", "✅ Thêm sản phẩm thành công!");
                showSearchItem(request, response);
            } else {
                request.setAttribute("message", "❌ Thêm thất bại, sản phẩm đã tồn tại!");
                showAddItem(request, response);
            }
        }else {
            request.setAttribute("message", "❌ Thêm thất bại, category null!");
            showAddItem(request, response);
        }
        
    }
    
}