<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.ManagementStaff" %>
<%@ page import="model.Supplier" %>
<%@ page import="java.util.List" %>
<%
    ManagementStaff currentUser = (ManagementStaff) session.getAttribute("user");
    if (currentUser == null) {
        request.setAttribute("message", "⚠️ Không tìm thấy user hiện tại, vui lòng đăng nhập lại!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../user/LoginView.jsp");
        dispatcher.forward(request, response);
        return;
    }
    List<Supplier> dsSupplier = (List<Supplier>) session.getAttribute("suppliers");
    
    session.removeAttribute("dsImportProduct");
    session.removeAttribute("supplier");
    session.removeAttribute("dsProduct");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Search Supplier</title>
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
        <div class="current-function">Import goods</div>
    </div>
    
    <div class="main-content">
        <h1>Search Supplier</h1>
        
        <div class="search-section">
            <form action="${pageContext.request.contextPath}/SupplierController" method="get" class="search-form">
                <input type="hidden" name="action" value="search">
                <input type="text" name="searchName" class="search-input" placeholder="Enter supplier name here..." 
                       value="<%= request.getParameter("searchName") != null ? request.getParameter("searchName") : "" %>">
                <button type="submit" class="btn-search">Search</button>
                <a href="${pageContext.request.contextPath}/view/managementStaff/AddSupplierView.jsp" class="btn-add">Add</a>
            </form>
        </div>
        
        <div class="table-section">
            <h2>Supplier list:</h2>
            <div class="table-wrapper">
                <table class="supplier-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Telephone</th>
                            <th>Email</th>
                            <th>Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        if (dsSupplier != null && !dsSupplier.isEmpty()) {
                            for (Supplier supplier : dsSupplier) {
                        %>
                        <tr class="clickable-row" onclick="submitSelectForm(<%= supplier.getId() %>)" style="cursor:pointer;">
                            <form id="form-select-<%= supplier.getId() %>" 
                                    action="${pageContext.request.contextPath}/SupplierController" 
                                    method="post">
                                  <input type="hidden" name="action" value="select">
                                  <input type="hidden" name="id" value="<%= supplier.getId() %>">
                            </form>
                            <td><%= supplier.getId() %></td>
                            <td><%= supplier.getName() %></td>
                            <td><%= supplier.getTelephone() %></td>
                            <td><%= supplier.getEmail() %></td>
                            <td><%= supplier.getAddress() %></td>
                        </tr>
                        <%
                            }
                        } else if (request.getParameter("searchName") != null) {
                        %>
                        <tr>
                            <td colspan="5" style="text-align: center; padding: 20px;">No suppliers found</td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="back-section">
            <a href="${pageContext.request.contextPath}/view/managementStaff/ManagementStaffHomeView.jsp" class="btn-back">Back</a>
        </div>
    </div>
</body>
</html>