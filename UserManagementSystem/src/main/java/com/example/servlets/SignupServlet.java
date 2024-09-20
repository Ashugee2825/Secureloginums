package com.example.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.security.MessageDigest;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

@WebServlet("/signup")




public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Hash the password (you can also use bcrypt)
        String hashedPassword = hashPassword(password);

        // Insert the user details in the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement", "user", "123456")) {
            String sql = "INSERT INTO users (username, email, password, is_verified) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword);
            stmt.setBoolean(4, false); // User is not verified initially
            stmt.executeUpdate();
            
            // Generate verification token and send email (using JavaMail API)
            String token = generateVerificationToken();
            sendVerificationEmail(email, token);
            
            response.sendRedirect("success.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateVerificationToken() {
		// TODO Auto-generated method stub
		return null;
	}

	private String hashPassword(String password) {
        // Implement hashing (bcrypt or MD5/SHA)
        return password; // Example (use a real hash function here)
    }

    private void sendVerificationEmail(String email, String token) {
        // Send email with verification link (use JavaMail API)
        // e.g., http://yourdomain.com/verify?token=TOKEN
    }
}
