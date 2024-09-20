<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
</head>
<body>
    <h1>Admin Dashboard</h1>
    
    <!-- Users Table -->
    <h2>Manage Users</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Username</th>
                <th>Email</th>
                <th>Verified</th>
                <th>Locked</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "root", "password");

                    String sql = "SELECT * FROM users";
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int userId = rs.getInt("id");
                        String username = rs.getString("username");
                        String email = rs.getString("email");
                        boolean isVerified = rs.getBoolean("is_verified");
                        boolean isLocked = rs.getBoolean("is_locked");
            %>
            <tr>
                <td><%= username %></td>
                <td><%= email %></td>
                <td><%= isVerified ? "Yes" : "No" %></td>
                <td><%= isLocked ? "Yes" : "No" %></td>
                <td>
                    <!-- Reset Password -->
                    <form action="AdminServlet" method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="reset_password">
                        <input type="hidden" name="userId" value="<%= userId %>">
                        <button type="submit">Reset Password</button>
                    </form>
                    
                    <!-- Lock/Unlock User -->
                    <form action="AdminServlet" method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="<%= isLocked ? "unlock" : "lock" %>">
                        <input type="hidden" name="userId" value="<%= userId %>">
                        <button type="submit"><%= isLocked ? "Unlock" : "Lock" %></button>
                    </form>
                </td>
            </tr>
            <%
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                }
            %>
        </tbody>
    </table>
    
    <!-- Logs Table -->
    <h2>View Logs</h2>
    <table border="1">
        <thead>
            <tr>
                <th>User ID</th>
                <th>Action</th>
                <th>Timestamp</th>
            </tr>
        </thead>
        <tbody>
            <%
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "root", "password");
                    String sql = "SELECT * FROM logs ORDER BY timestamp DESC";
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int userId = rs.getInt("user_id");
                        String action = rs.getString("action");
                        Timestamp timestamp = rs.getTimestamp("timestamp");
            %>
            <tr>
                <td><%= userId %></td>
                <td><%= action %></td>
                <td><%= timestamp %></td>
            </tr>
            <%
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                }
            %>
        </tbody>
    </table>
</body>
</html>
