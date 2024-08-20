/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author saras
 */
@WebServlet(urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //work on dopost method
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        //accept username and password from register.html
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        
        //database
        try {
            //open connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "");
            
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM login WHERE email = '" + email + "' OR username = '" + username + "'");

            if(rs.next()) {
                // If the account is already registered
                out.println("The account has already been registered with this email or username.");
                return;
            } else {
                //If not registered, then insert data
                String insertQuery = "INSERT INTO login (email, username, password) VALUES ('" + email + "', '" + username + "', '" + password + "')";
                int result = stm.executeUpdate(insertQuery);
                
                if (result > 0) {
                    // If insert is successful, redirect to login page
                    response.sendRedirect("/FirstWebApplication/index.html");
                } else {
                    out.println("An error occurred while registering the account. Please try again.");
                }
            }
            //close connection
            conn.close();
        }catch(Exception e) {
            System.out.println(e.getMessage());
        } 
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
}
