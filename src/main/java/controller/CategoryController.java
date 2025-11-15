package controller;

import dao.CategoryDAO;
import model.Category;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryController", urlPatterns = {"/CategoryController"})
public class CategoryController extends HttpServlet {

    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        categoryDAO = new CategoryDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "get":
                    getCategory(request, response);
                    break;
                default:
                    getCategory(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "❌ Lỗi xảy ra: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/AddItemView.jsp");
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
    
    private void showAddItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/AddItemView.jsp");
        dispatcher.forward(request, response);
    }
    
    private void getCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Category> dsCategory = categoryDAO.getCategory();

        if (dsCategory != null && !dsCategory.isEmpty()) {
            request.getSession().setAttribute("dsCategory", dsCategory);
        } else {
            request.setAttribute("message", "danh sách Category trống");
        }
        
        showAddItem(request, response);
    }
}