package com.example.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.utils.EmailUtil;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


/**
 * Servlet implementation class ForgotPasswordServlet
 */
@WebServlet("/ForgotPasswordServlet")


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */



public class ForgotPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        String email = request.getParameter("email");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement", "user", "123456")) {
            String token = generateResetToken();
            String sql = "UPDATE users SET reset_token = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, token);
            stmt.setString(2, email);
            stmt.executeUpdate();

            // Send email with reset token
            sendResetEmail(email, token);
            response.sendRedirect("resetlinksent.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    private String generateResetToken() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32); // Generates a secure random token
    }


	private void logAction(Object conn, int userId, String string) {
		// TODO Auto-generated method stub
		
	}


	private String getUserEmailById(Object conn, int userId) {
		// TODO Auto-generated method stub
		return null;
	}


	private void sendResetEmail(String email, String token) {
        // Send email with reset link
    }
}
