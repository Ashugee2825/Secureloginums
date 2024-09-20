<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>

<script src="https://www.google.com/recaptcha/api.js" async defer></script>


<body>
    <h2>Login</h2>
<!--     <form action="login" method="post">
        Email: <input type="email" name="email" required>
        Password: <input type="password" name="password" required>
        <input type="submit" value="Login">
    </form> -->
    
    <form action="LoginServlet" method="POST">
    <input type="text" name="username" required placeholder="Username or Email">
    <input type="password" name="password" required placeholder="Password">
    <button type="submit">Login</button>
</form>
    
    <a href="signup.jsp">Signup</a>
</body>
</html>
