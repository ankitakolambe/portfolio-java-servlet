package com.contact;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submitContact")
public class ContactServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private String dbURL = "jdbc:mysql://localhost:3306/portfolio_db";
    private String dbUser = "root";
    private String dbPass = "MOHINIanki18@"; 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            // Insert query
            String sql = "INSERT INTO contacts (name, email, message) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, message);
            stmt.executeUpdate();

            // Close resources
            stmt.close();
            conn.close();

            // Redirect to index with success flag
            response.sendRedirect("index.html?success=true");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html?success=false");
        }
    }
}
