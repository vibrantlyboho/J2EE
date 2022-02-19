/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
//Any source that uses JDBC needs to import the java.sql package, using: import java.sql.*;

/**
 *
 * @author Jo
 */
@WebServlet(urlPatterns = {"/Registration1Servlet"})
public class Registration1Servlet extends HttpServlet {

    //creating an obj of connection so that you can establish a connection and then create a statement obj to implement queries
    Connection connection;
    //With JDBC, a database is represented by a URL (Uniform Resource Locator). 
    //With PostgreSQL™, this takes any one of 3 forms. The one we use here is jdbc:postgresql://host:port/database
    String url = "jdbc:postgresql://localhost:5432/lab";
    String user = "postgres";
    String pass = "password";
        
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    public void dbConnection(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        out.println("hello");
        try{
            //Your code implicitly loads the driver using the Class.forName() method. 
            //For PostgreSQL™, you would use: Class.forName("org.postgresql.Driver");
            //This will load the driver, and while loading, the driver will automatically register itself with JDBC.
            //The forName() method can throw a ClassNotFoundException if the driver is not available.
            Class.forName("org.postgresql.Driver");
        } 
        //The forName() method can throw a ClassNotFoundException if the driver is not available.
        catch(ClassNotFoundException e){
            out.println(e.getMessage());
        }
        
        try{
            //getting values from request obj
            String _fname = request.getParameter("fname");
            String _lname = request.getParameter("lname");
            String _uname = request.getParameter("uname");
            String _pword = request.getParameter("password"); 
            
            //establish a connection so that you can create a statement obj; The method DriverManager.getConnection establishes a database connection.
            connection = DriverManager.getConnection(url,user,pass);
            out.println("Connected");
            //create an object of statement so that you can implement simple sql statements
            Statement stmnt = connection.createStatement();
            //inserting values into the db users
            int i = stmnt.executeUpdate("INSERT INTO \"users\"(fname, lname, uname, password) VALUES ('"+_fname+"','"+ _lname+"','"+ _uname+"','" + _pword +"');");
            out.println(i);
            out.println("INSERT INTO users(fname, lname, uname, password) VALUES ('"+_fname+"','"+ _lname+"','"+ _uname+"','" + _pword +"');");
            connection.close();            
        }
        catch( SQLException sqlex){
            out.println(sqlex.getMessage());
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String uname = request.getParameter("uname");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegistrationServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Thank You " + fname + " " + lname + " for registering at Registration-1!</h1>");
            out.println("Your user id is: "+ uname + " and your password is protected.");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
     
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        dbConnection(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
