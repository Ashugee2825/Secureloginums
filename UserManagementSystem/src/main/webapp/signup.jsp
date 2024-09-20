<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Signup</title>
</head>

<script src="https://www.google.com/recaptcha/api.js" async defer></script>

<body>
    <h2>Signup</h2>
    <!-- <form action="signup" method="post">
        Email: <input type="email" name="email" required>
        Password: <input type="password" name="password" required>
        <input type="submit" value="Signup">
        
        
    </form>
     -->
    
    <form action="SignupServlet" method="POST">
    <input type="text" name="username" required placeholder="Username">
    <input type="email" name="email" required placeholder="Email">
    <input type="password" name="password" required placeholder="Password">
    <!-- Integrate Captcha -->
    <div class="g-recaptcha" data-sitekey="your-site-key"></div>
    <button type="submit">Sign Up</button>
    </form>
    
    <a href="login.jsp">Login</a>
</body>
</html>
