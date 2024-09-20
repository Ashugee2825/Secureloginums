package com.example.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class ResetPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password")) {
            // Verify the token
            String sql = "SELECT user_id FROM password_reset_requests WHERE token = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, token);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("user_id");

                    // Hash the new password
                    String hashedPassword = hashPassword(newPassword);

                    // Update the user's password
                    String updateSql = "UPDATE users SET password = ? WHERE id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, hashedPassword);
                        updateStmt.setInt(2, userId);
                        updateStmt.executeUpdate();
                    }

                    // Delete the reset request
                    String deleteSql = "DELETE FROM password_reset_requests WHERE token = ?";
                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                        deleteStmt.setString(1, token);
                        deleteStmt.executeUpdate();
                    }

                    // Redirect to success page
                    response.sendRedirect("reset-success.jsp");

                } else {
                    response.sendRedirect("reset-failure.jsp");  // Invalid token
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String hashPassword(String ashu) {
        // Replace this with a secure hash function like bcrypt
        return BCrypt.hashpw(ashu, BCrypt.gensalt());
    }
}
