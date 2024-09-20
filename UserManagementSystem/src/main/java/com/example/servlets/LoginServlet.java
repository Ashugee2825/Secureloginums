package com.example.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")

public class LoginServlet extends HttpServlet {
    private Object email;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement", "user", "123456")) {
            String sql = "SELECT password, is_verified FROM users WHERE username = ? OR email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();
            
            
            
            
            if (isValidUser(email, password)) {
                // Check if user is locked
                String sql1 = "SELECT locked FROM users WHERE email = ?";
                try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                    stmt1.setNString(1, (String) email);
                    ResultSet rs1 = stmt1.executeQuery();
                    if (rs1.next()) {
                        boolean isLocked = rs1.getBoolean("locked");
                        if (isLocked) {
                            // Inform user that account is locked
                            request.setAttribute("errorMessage", "Your account is locked. Please contact admin.");
                            request.getRequestDispatcher("login.jsp").forward(request, response);
                            return;
                        }
                    }
                }
                // Proceed with normal login process
            }

            

            
            
            
            
            // login checked 
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                boolean isVerified = rs.getBoolean("is_verified");

                if (!isVerified) {
                    response.sendRedirect("verify.jsp");
                    return;
                }

                if (checkPassword(password, storedPassword)) {
                    // Create a session and redirect to the dashboard
                    HttpSession session = request.getSession();
                    session.setAttribute("user", username);
                    response.sendRedirect("dashboard.jsp");
                } else {
                    response.sendRedirect("login.jsp?error=invalid");
                }
            } else {
                response.sendRedirect("login.jsp?error=usernotfound");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidUser(Object email2, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean checkPassword(String password, String storedPassword) {
        // Implement password checking logic (bcrypt or MD5/SHA)
        return password.equals(storedPassword); // Example
    }
}
