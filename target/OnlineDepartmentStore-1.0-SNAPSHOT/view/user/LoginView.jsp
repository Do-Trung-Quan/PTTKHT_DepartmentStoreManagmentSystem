<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<div class="container">
    <div class="image"> 
        <img src="${pageContext.request.contextPath}/images/SachTheThao.jpg" alt="Login Image">
    </div>
    <div class="content">
        <h1>Login</h1>
        <div id="messageBox"><%= request.getAttribute("message") == null ? "" : request.getAttribute("message") %></div>
        <form action="${pageContext.request.contextPath}/UserController" method="post">
            <input type="hidden" name="action" value="login">
            
            <div class="form-row">
                <label>Email:</label>
                <input type="text" name="email" required>
            </div>
            
            <div class="form-row">
                <label>Password:</label>
                <input type="password" name="password" required>
            </div>
            
            <button class="btn">Login</button>
        </form>
        <div class="links">
            <p>Do not have an account? 
               <a href="${pageContext.request.contextPath}/view/customer/RegisterView.jsp">Register now!</a>
            <p><a href="${pageContext.request.contextPath}/view/user/GreetingView.jsp">Back to Greeting</a></p>
        </div>
    </div>
</div>
</body>
</html>