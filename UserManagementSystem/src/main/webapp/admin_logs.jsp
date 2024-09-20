<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Admin Logs</title>
</head>
<body>
    <h2>Admin Action Logs</h2>
     <table border="1">
        <tr>
            <th>Admin ID</th>
            <th>Action</th>
            <th>Timestamp</th>
        </tr>
        <%
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement", "username", "password")) {
                String sql = "SELECT admin_id, action, timestamp FROM admin_logs ORDER BY timestamp DESC";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int adminId = rs.getInt("admin_id");
                    String action = rs.getString("action");
                    Timestamp timestamp = rs.getTimestamp("timestamp");
        %>
        <tr>
            <td><%= adminId %></td>
            <td><%= action %></td>
            <td><%= timestamp %></td>
        </tr>
        <%
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        %>
    </table>
</body>
</html>
