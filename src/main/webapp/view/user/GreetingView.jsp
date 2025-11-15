<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Greeting</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<div class="container">
    <div id="messageBox" class = "invisible"><%= request.getAttribute("message") == null ? "" : request.getAttribute("message") %></div>
    <div class="image">
        <img src="${pageContext.request.contextPath}/images/SachTheThao.jpg" alt="Greeting Image">
    </div>
    <div class="content" style="text-align:center;">
        <h1>GREETING</h1>
        <p>Welcome to the online department store management system</p>

        <a href="${pageContext.request.contextPath}/view/user/LoginView.jsp" class="btn">Login</a>
        <a href="${pageContext.request.contextPath}/view/customer/RegisterView.jsp" class="btn">Register as a member</a>

    </div>
</div>
</body>
</html>
