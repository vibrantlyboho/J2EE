/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
@WebServlet(urlPatterns = {"/Login1Servlet"})
public class Login1Servlet extends HttpServlet {
    
    //creating an obj of connection so that you can establish a connection and then create a statement obj to implement queries
    Connection connection;
    //With JDBC, a database is represented by a URL (Uniform Resource Locator). 
    //With PostgreSQL™, this takes any one of 3 forms. The one we use here is jdbc:postgresql://host:port/database
    String url = "jdbc:postgresql://localhost:5432/lab";
    String user = "postgres";
    String pass = "password";
    
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
            String uname = request.getParameter("uname");
            String pword = request.getParameter("password"); 
            //initialising the var that'll store values that you get from db
            String username = null;
            String password= null;
            Boolean found= false;
            //establish a connection so that you can create a statement obj; The method DriverManager.getConnection establishes a database connection.
            connection = DriverManager.getConnection(url,user,pass);
            out.println("Connected");
            //create an object of statement so that you can implement simple sql statements
            Statement stmnt = connection.createStatement();
            //when you execute the query you will get a single Result set object
            ResultSet rs= stmnt.executeQuery("select * from public.\"users\"");
            while(rs.next()){
                //rs.next, the method outputs the data in the row where the cursor is currently positioned
                //now we retrieve data values from the db
                username= rs.getString("uname");
                password = rs.getString("password");
                //comparing the values the user entered in login page is present in the db
                if (username.equals(uname)&&password.equals(pword))
                    {
                        //you have found the values you entered in the db
                        found = true;
                        break;
                    }
            }
            if (true == found)
                {                    
                   out.println("Login success");                   
                }
                else
                {
                    out.println("Login Failed");
                }
                connection.close();            
        }
        catch( SQLException sqlex){
            out.println(sqlex.getMessage());
        }
    }
         
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String uname = request.getParameter("uname");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Welcome!</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Welcome " + uname + "!! This is your Login-1 homepage</h1>");
            out.println("Your password has to remain a secret, so not displaying it! :D");
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
