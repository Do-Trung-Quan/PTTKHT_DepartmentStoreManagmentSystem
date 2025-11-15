package controller;

import dao.CustomerDAO;
import model.Customer;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "CustomerController", urlPatterns = {"/CustomerController"})
public class CustomerController extends HttpServlet {

    private CustomerDAO customerDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "register":
                    registerCustomer(request, response);
                    break;
                default:
                    showRegisterForm(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "❌ Lỗi xảy ra: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("view/customer/RegisterView.jsp");
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

    // Hiển thị form đăng ký (GET)
    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/customer/RegisterView.jsp");
        dispatcher.forward(request, response);
    }

    // Đăng ký tài khoản mới (POST)
    private void registerCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String dateOfBirthStr = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");

        LocalDate dateOfBirth = null;
        try {
            if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
                dateOfBirth = LocalDate.parse(dateOfBirthStr);
            }
        } catch (Exception e) {
            request.setAttribute("message", "⚠️ Ngày sinh không hợp lệ!");
            showRegisterForm(request, response);
            return;
        }

        Customer c = new Customer(0, fullName, dateOfBirth, gender, telephone, email, password, address);

        if (customerDAO.addCustomer(c)) {
            request.setAttribute("message", "✅ Đăng ký thành công!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("view/user/GreetingView.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("message", "❌ Email hoặc số điện thoại đã tồn tại.");
            showRegisterForm(request, response);
        }
    }
}
