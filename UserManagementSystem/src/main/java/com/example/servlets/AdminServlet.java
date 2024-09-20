package com.example.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.example.utils.EmailUtil;

import java.io.IOException;
import java.sql.*;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;






public class AdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = Integer.parseInt(request.getParameter("userId"));

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement", "root", "123456")) {
            String sql = "";
            PreparedStatement stmt = null;

           
         // Reset Password Action
            if ("reset_password".equals(action)) {
                String newPassword = "newpassword123"; // Generate a secure password
                String hashedPassword = hashPassword(newPassword);

                sql = "UPDATE users SET password = ? WHERE id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, hashedPassword);
                stmt.setInt(2, userId);
                stmt.executeUpdate();

                // Send reset email
                String userEmail = getUserEmailById(conn, userId);
                String emailSubject = "Your password has been reset";
                String emailBody = "Your new password is: " + newPassword;
                EmailUtil.sendEmail(userEmail, emailSubject, emailBody);

                logAction(conn, userId, "Password reset by admin");
                
             // Log the action
                logAction(conn, userId, "Password reset by admin");

            // Lock/Unlock User Action
            } else if ("lock".equals(action)) {
                sql = "UPDATE users SET is_locked = TRUE WHERE id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userId);
                stmt.executeUpdate();

                logAction(conn, userId, "Account locked by admin");

            } else if ("unlock".equals(action)) {
                sql = "UPDATE users SET is_locked = FALSE WHERE id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userId);
                stmt.executeUpdate();

                logAction(conn, userId, "Account unlocked by admin");
            }

            if (stmt != null) stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("admin-dashboard.jsp");
            }

            // Helper method to fetch user email
            private String getUserEmailById(Connection conn, int userId) throws SQLException {
                String email = null;
                String sql = "SELECT email FROM users WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, userId);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        email = rs.getString("email");
                    }
                }
                
                return email;
                
                
            }
            
  
            // Reset Password Action
			/*
			 * if ("reset_password".equals(action)) { String newPassword = "newpassword123";
			 * // Generate a secure password String hashedPassword =
			 * hashPassword(newPassword);
			 * 
			 * sql = "UPDATE users SET password = ? WHERE id = ?"; stmt =
			 * conn.prepareStatement(sql); stmt.setString(1, hashedPassword); stmt.setInt(2,
			 * userId); stmt.executeUpdate();
			 */

      

    private String hashPassword(String password) {
        // Use a proper password hashing algorithm like bcrypt or PBKDF2
        // Here, we'll just return the plain password for simplicity (Not secure)
        return password;  // Replace this with proper password hashing
    }

    private void logAction(Connection conn, int userId, String action) throws SQLException {
        String logSql = "INSERT INTO logs (user_id, action) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(logSql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, action);
            stmt.executeUpdate();
        }
    }
    
}


