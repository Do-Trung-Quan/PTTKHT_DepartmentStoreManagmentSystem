<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>
<%
    // Redirect to CategoryController to load categories if not exists
    List<Category> dsCategory = (List<Category>) session.getAttribute("dsCategory");
    if (dsCategory == null) {
        response.sendRedirect(request.getContextPath() + "/CategoryController?action=get");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Item</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-style.css">
    <script src="${pageContext.request.contextPath}/js/main.js"S></script>
</head>
<body>
<div class="form-container">
    <div class="form-box">
        <h1>Add Item</h1>
        <div id="messageBox" ><%= request.getAttribute("message") == null ? "" : request.getAttribute("message") %></div>
        
        <form action="${pageContext.request.contextPath}/ProductController" method="post" id="addForm">
            <input type="hidden" name="action" value="add">
            
            <div class="form-row">
                <label>Name:</label>
                <input type="text" name="name" required>
            </div>
            
            <div class="form-row">
                <label>Category:</label>
                <select name="CategoryId" required class="form-select">
                    <option value="" disabled selected>Select category</option>
                    <%
                        if (dsCategory != null && !dsCategory.isEmpty()) {
                            for (Category c : dsCategory) {
                    %>
                    <option value="<%= c.getId() %>"><%= c.getTitle() %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            
            <div class="form-row">
                <label>Unit price:</label>
                <input type="number" name="unitPrice" min="0" max="1000000000" id ="price" required>
            </div>
            
            <div class="form-group-textarea">
                <label>Description: (optional)</label>
                <textarea name="note" rows="4" class="form-textarea"></textarea>
            </div>
            
            <div class="button-group">
                <a href="${pageContext.request.contextPath}/view/managementStaff/SearchItemView.jsp" class="btn btn-back">Back</a>
                <button type="submit" class="btn btn-submit">Add</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>