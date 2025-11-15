<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ManagementStaff, model.Supplier, model.Product, model.ImportProduct" %>

<%
    ManagementStaff currentUser = (ManagementStaff) session.getAttribute("user");
    Supplier supplier = (Supplier) session.getAttribute("supplier");
    List<Product> dsProduct = (List<Product>) session.getAttribute("dsProduct");
    List<ImportProduct> dsImportProduct = (List<ImportProduct>) session.getAttribute("dsImportProduct");
    
    if (currentUser == null) {
        request.setAttribute("message", "⚠️ Không tìm thấy user hiện tại, vui lòng đăng nhập lại!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../user/LoginView.jsp");
        dispatcher.forward(request, response);
        return;
    }

    double total = 0;
    if (dsImportProduct != null) {
        for (ImportProduct ip : dsImportProduct) {
            total += ip.getImportPrice();
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Search Item</title>
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
    <h1>Search Item</h1>
    
    <div class="selected-supplier">Supplier: <%= supplier.getName() %></div>
    <!-- Search section -->
    <div class="search-section">
        <form action="${pageContext.request.contextPath}/ProductController" method="get" onsubmit="saveScrollPos()" class="search-form">
            <input type="hidden" name="action" value="search">
            <input type="text" name="searchName" class="search-input" placeholder="Enter item name here..."
                   value="<%= request.getParameter("searchName") != null ? request.getParameter("searchName") : "" %>">
            <button type="submit" class="btn-search">Search</button>
            <a href="${pageContext.request.contextPath}/view/managementStaff/AddItemView.jsp" class="btn-add">Add</a>
        </form>
    </div>

    <!-- Item list -->
    <div class="table-section">
        <h2>Item list:</h2>
        <div class="table-wrapper">
            <table class="supplier-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Description</th>
                    <th>Unit price</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (dsProduct != null && !dsProduct.isEmpty()) {
                        for (Product p : dsProduct) {
                %>
                <tr class="clickable-row"
                    onclick="saveScrollPos();submitSelectForm(<%= p.getId() %>);" style="cursor:pointer;">
                    <form id="form-select-<%= p.getId() %>" 
                                    action="${pageContext.request.contextPath}/ProductController" 
                                    method="post">
                           <input type="hidden" name="action" value="select">
                           <input type="hidden" name="idProduct" value="<%= p.getId() %>">
                    </form>
                    <td><%= p.getId() %></td>
                    <td><%= p.getName() %></td>
                    <td><%= p.getCategory().getTitle() %></td>
                    <td><%= p.getDescription()%></td>
                    <td><%= String.format("%,.0f", p.getUnitPrice()) %></td>
                </tr>
                <%
                        }
                    } else if (request.getParameter("searchName") != null) {
                %>
                <tr><td colspan="5" style="text-align:center; padding:15px;">No items found</td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Selected items -->
    <div class="table-section">
        <h2>
            Selected item list: (<%= dsImportProduct != null ? dsImportProduct.size() : 0 %> items)
            <form id="removeForm" action="${pageContext.request.contextPath}/ProductController" method="post" onsubmit="saveScrollPos()" style="display:inline;">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="idProduct" id="removeId">
                <button type="submit" id="btnRemove" class="btn-remove" disabled>
                    🗑️ Remove
                </button>
            </form>
        </h2>
        <div class="table-wrapper">
            <table class="supplier-table" id="selectedTable">
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
                    if (dsImportProduct != null && !dsImportProduct.isEmpty()) {
                        for (ImportProduct ip : dsImportProduct) {
                %>
                <tr class="selectable-row" data-id="<%= ip.getProduct().getId() %>">
                    <form action="${pageContext.request.contextPath}/ProductController" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="idProduct" value="<%= ip.getProduct().getId() %>">
                        <td><%= ip.getProduct().getId() %></td>
                        <td><%= ip.getProduct().getName() %></td>
                        <td><%= ip.getProduct().getCategory().getTitle()%></td>
                        <td>
                            <% String formattedValue = String.format(java.util.Locale.US, "%.0f", ip.getImportUnitPrice()); %>
                            <input type="number" step="1000"
                                   name="importUnitPrice"
                                   value="<%= formattedValue %>"
                                   min="0" max="1000000000"
                                   onchange="validateAndSubmit(this.form); saveScrollPos();" class="input-inline">
                        </td>
                        <td>
                            <input type="number" name="quantity" value="<%= ip.getQuantity() %>"
                                   min="1" max="1000"
                                   onchange="validateAndSubmit(this.form); saveScrollPos();" class="input-inline">
                        </td>
                        <td>
                            <input type="text" name="note" value="<%= ip.getNote()%>"
                                   onchange="validateAndSubmit(this.form); saveScrollPos();" class="input-inline">
                        </td>
                        <td><%= String.format("%,.0f", ip.getImportPrice()) %></td>
                    </form>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="7" style="text-align:center;">No selected items</td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Total -->
    <div class="total-section" style="text-align:right; margin-top:20px;">
        <h2>Total: <%= String.format("%,.0f", total) %> VND</h2>
    </div>

    <!-- Buttons -->
    <div class="action-section" style="text-align:center; margin-top:30px;">
        <a href="${pageContext.request.contextPath}/view/managementStaff/SearchSupplierView.jsp" class="btn-back">Back</a>
        <a href="${pageContext.request.contextPath}/view/managementStaff/PrintImportInvoiceView.jsp" class="btn-submit">Submit</a>
    </div>
</div>

</body>
</html>
