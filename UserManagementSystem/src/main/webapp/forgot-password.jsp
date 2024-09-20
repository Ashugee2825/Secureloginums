<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.sql.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <title>Reset Password</title>
</head>
<body>

<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*, java.util.*" %>
<html>
<head>
    <title>Reset Password</title>
</head>
<body>
    <h2>Reset Your Password</h2>

    <%
        String token = request.getParameter("token");
        if (token == null || token.isEmpty()) {
            out.println("Invalid or missing token.");
        } else {
            // Check if the token is valid and not expired
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password");
                String sql = "SELECT user_id, expiration_time FROM password_reset_requests WHERE token = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, token);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Timestamp expirationTime = rs.getTimestamp("expiration_time");
                    if (expirationTime.before(new Timestamp(System.currentTimeMillis()))) {
                        out.println("Token has expired. Please request a new password reset.");
                    } else {
                        // Display the password reset form
    %>
                        <form method="post" action="ResetPasswordServlet">
                            <input type="hidden" name="token" value="<%= token %>" />
                            <label for="newPassword">New Password:</label>
                            <input type="password" id="newPassword" name="newPassword" required />
                            <button type="submit">Reset Password</button>
                        </form>
    <%
                    }
                } else {
                    out.println("Invalid token.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    %>
</body>
</html>



