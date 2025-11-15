<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<div class="container">
    <div class="image">
        <img src="${pageContext.request.contextPath}/images/SachTheThao.jpg" alt="Register Image">
    </div>
    <div class="content">
        <h1>Register</h1>
        <div id="messageBox"><%= request.getAttribute("message") == null ? "" : request.getAttribute("message") %></div>
        <form id="registerForm" action="${pageContext.request.contextPath}/CustomerController" method="post">
        <input type="hidden" name="action" value="register">

        <div class="form-row">
            <label>Full name:</label>
            <input type="text" name="fullName" required>
        </div>

        <div class="form-row">
            <label>Date of birth:</label>
            <input type="date" name="dateOfBirth" required>
        </div>

        <div class="form-row">
            <label>Address:</label>
            <input type="text" name="address" required>
        </div>

        <div class="form-row">
            <label>Email:</label>
            <input type="text" name="email" id="email" required>
        </div>

        <div class="form-row">
            <label>Telephone:</label>
            <input type="text" name="telephone" id="telephone" required>
        </div>

        <div class="form-row">
            <label>Password:</label>
            <input type="password" name="password" required>
        </div>

        <div class="form-row">
            <label>Gender:</label>
            <select name="gender">
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>
        </div>

        <button class="btn" type="submit">Add</button>
    </form>
        <div class="links">
            <p><a href="${pageContext.request.contextPath}/view/user/GreetingView.jsp">Back to Greeting</a></p>
        </div>
    </div>
</div>
</body>
</html>