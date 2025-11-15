<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="model.ManagementStaff, model.Supplier, model.ImportProduct" %>

<%
    ManagementStaff currentUser = (ManagementStaff) session.getAttribute("user");
    Supplier supplier = (Supplier) session.getAttribute("supplier");
    List<ImportProduct> dsImportProduct = (List<ImportProduct>) session.getAttribute("dsImportProduct");

    if (currentUser == null) {
        request.setAttribute("message", "⚠️ Không tìm thấy user hiện tại, vui lòng đăng nhập lại!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../user/LoginView.jsp");
        dispatcher.forward(request, response);
        return;
    }
    
    if (dsImportProduct == null || dsImportProduct.isEmpty()) {
        request.setAttribute("message", "⚠️ Vui lòng chọn tối thiểu 1 sản phẩm trước khi submit!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("SearchItemView.jsp");
        dispatcher.forward(request, response);
        return;
    }

    // Calculate total
    double total = 0;
    for (ImportProduct ip : dsImportProduct) {
        total += ip.getImportPrice();
    }

    // Format current date
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String formattedDate = currentDate.format(formatter);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Import Invoice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-style.css">
    <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/management-style.css">-->
</head>
<body>
<div id="messageBox" class = "invisible"><%= request.getAttribute("message") == null ? "" : request.getAttribute("message") %></div>
<div class="form-container invoice-container">
    <div class="form-box invoice-box">
        <h1>Import Invoice</h1>
        
        <div class="invoice-info">
            <p><strong>Import date:</strong> <%= formattedDate %></p>
            <p><strong>Management staff:</strong> <%= currentUser.getFullName() %></p>
            <p><strong>Supplier:</strong> <%= supplier.getName() %></p>
            <p><strong>Address:</strong> <%= supplier.getAddress() %></p>
            <p><strong>Telephone:</strong> <%= supplier.getTelephone() %></p>
        </div>

        <div class="table-section">
            <h2>Selected item list: (<%= dsImportProduct.size() %> items)</h2>
            <div class="table-wrapper">
                <table class="supplier-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Category</th>
                            <th>Import unit price</th>
                            <th>Quantity</th>
                            <th>Note</th>
                            <th>Import price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (ImportProduct ip : dsImportProduct) {
                        %>
                        <tr class="clickable-row">
                            <td><%= ip.getProduct().getId() %></td>
                            <td><%= ip.getProduct().getName() %></td>
                            <td><%= ip.getProduct().getCategory().getTitle() %></td>
                            <td><%= String.format("%,.0f", ip.getImportUnitPrice()) %></td>
                            <td><%= ip.getQuantity() %></td>
                            <td><%= ip.getNote()%></td>
                            <td><%= String.format("%,.0f", ip.getImportPrice()) %></td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="total-section" style="text-align:right; margin-top:20px;">
            <h2>Total: <%= String.format("%,.0f", total) %> VND</h2>
        </div>

        <div class="button-group">
            <a href="${pageContext.request.contextPath}/view/managementStaff/SearchItemView.jsp" 
               class="btn btn-back">Cancel</a>
            <form action="${pageContext.request.contextPath}/ImportInvoiceController" method="post" style="display:inline;">
                <input type="hidden" name="action" value="save">
                <input type="hidden" name="date" value="<%= currentDate %>">
                <input type="hidden" name="total" value="<%= total %>">
                <button type="submit" class="btn btn-submit">Print</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>