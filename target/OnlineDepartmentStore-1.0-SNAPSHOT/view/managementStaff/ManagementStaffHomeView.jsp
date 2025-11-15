<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.ManagementStaff" %>
<%
    ManagementStaff currentUser = (ManagementStaff) session.getAttribute("user");
    if (currentUser == null) {
        request.setAttribute("message", "⚠️ Không tìm thấy user hiện tại, vui lòng đăng nhập lại!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../user/LoginView.jsp");
        dispatcher.forward(request, response);
        return;
    }
    
    session.removeAttribute("dsImportProduct");
    session.removeAttribute("supplier");
    session.removeAttribute("dsProduct");
    session.removeAttribute("suppliers");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Management Staff Homepage</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/management-style.css">
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
    <div id="messageBox" class = "invisible"><%= request.getAttribute("message") == null ? "" : request.getAttribute("message") %></div>
    <div class="header">
        <div class="user-info">
            <div class="avatar"></div>
            <div class="user-details">
                <div class="user-name"><%= currentUser.getFullName() %></div>
                <div class="user-role">Role: Management staff</div>
            </div>
        </div>
    </div>
    
    <div class="main-content">
        <h1>Management Staff Homepage</h1>
        <div class="welcome-text">
            <p>Welcome back <%= currentUser.getFullName() %>!</p>
            <p>What are you planning to do today?</p>
        </div>
        
        <div class="button-container">
            <a href="${pageContext.request.contextPath}/view/managementStaff/SearchSupplierView.jsp" class="btn-action">Import goods</a>
            
            <form action="${pageContext.request.contextPath}/UserController" method="post">
                <input type="hidden" name="action" value="logout">
                <button type="submit" class="btn-action">Logout</button>
            </form>
        </div>
    </div>
</body>
</html>