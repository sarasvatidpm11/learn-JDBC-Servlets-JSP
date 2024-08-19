/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.servlet.http.HttpSession;

/**
 *
 * @author saras
 */
@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //work on dopost method
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        //accept username and password from index.html
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        //database
        try {
            //open connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "");
            
            //get data from login table using query
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM login WHERE username = '" + username + "' AND password = '" + password + "'");
            if(rs.next()) {
                //save session login
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                //if username and password true than go to Home.html file
                response.sendRedirect("/FirstWebApplication/HomeServlet");
            } else {
                //wrong username and password
                out.println("wrong username and password...");
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
