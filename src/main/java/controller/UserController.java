package controller;

import dao.UserDAO;
import model.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "login":
                    login(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
                default:
                    showLoginForm(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "❌ Lỗi đăng nhập: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("view/user/LoginView.jsp");
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

    // Hiển thị form login
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/user/LoginView.jsp");
        dispatcher.forward(request, response);
    }

    // Xử lý đăng nhập
    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAO.login(email, password);
        if (user == null) {
            request.setAttribute("message", "❌ Sai email hoặc mật khẩu!");
            showLoginForm(request, response);
            return;
        }

        HttpSession session = request.getSession();
        
        
        // Điều hướng theo vai trò
        if (user instanceof ManagementStaff) {
            session.setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher("view/managementStaff/ManagementStaffHomeView.jsp");
            dispatcher.forward(request, response);
//      } else if (user instanceof SaleStaff) {
//           RequestDispatcher dispatcher = request.getRequestDispatcher("view/saleStaff/SaleHomeView.jsp");
//           dispatcher.forward(request, response);
//        } else if (user instanceof Customer) {
//            RequestDispatcher dispatcher = request.getRequestDispatcher("view/customer/CustomerHomeView.jsp");
//            dispatcher.forward(request, response);
        } else {
            request.setAttribute("message", "❌ Vui lòng chỉ đăng nhập bằng tài khoản của managementStaff, chức năng cho các người dùng khác sẽ được cập nhật sau!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("view/user/GreetingView.jsp");
            dispatcher.forward(request, response);
        }
    }

    // Xử lý đăng xuất
    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        request.setAttribute("message", "✅ Bạn đã đăng xuất!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/user/GreetingView.jsp");
        dispatcher.forward(request, response);
    }
}
