<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Supplier</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-style.css">
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<div class="form-container">
    <div class="form-box">
        <h1>Add Supplier</h1>
        <div id="messageBox" class = "invisible"><%= request.getAttribute("message") == null ? "" : request.getAttribute("message") %></div>
        <form id="registerForm" action="${pageContext.request.contextPath}/SupplierController" method="post">
            <input type="hidden" name="action" value="add">
            
            <div class="form-row">
                <label>Name:</label>
                <input type="text" name="name" required>
            </div>
            
            <div class="form-row">
                <label>Telephone:</label>
                <input type="text" name="telephone" id="telephone" required>
            </div>
            
            <div class="form-row">
                <label>Email:</label>
                <input type="text" name="email" id="email" required>
            </div>
            
            <div class="form-row">
                <label>Address:</label>
                <input type="text" name="address" required>
            </div>
            
            <div class="button-group">
                <a href="${pageContext.request.contextPath}/view/managementStaff/SearchSupplierView.jsp" class="btn btn-back">Back</a>
                <button type="submit" class="btn btn-submit">Add</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>