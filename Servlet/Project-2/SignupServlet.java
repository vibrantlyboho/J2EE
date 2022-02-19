/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author Jo
 */
@WebServlet(urlPatterns = {"/SignupServlet"})
public class SignupServlet extends HttpServlet {

    Connection connection;
    String url = "jdbc:postgresql://localhost:5432/bank";
    String user = "postgres";
    String pass = "password";
    
    public void dbConnection(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        //out.println("hello");
        
        try{
            Class.forName("org.postgresql.Driver");
        } 
        catch(ClassNotFoundException e){
            out.println(e.getMessage());
        }
        
        try{
            connection = DriverManager.getConnection(url,user,pass);
            //out.println("Connected");
            String _fname = request.getParameter("fname");
            String _lname = request.getParameter("lname");
            String _accno = request.getParameter("accno");
            String _pword = request.getParameter("password"); 
            
            Statement stmnt = connection.createStatement();
            int i = stmnt.executeUpdate("INSERT INTO \"bankaccount\"(fname, lname, accno, password) VALUES ('"+_fname+"','"+ _lname+"','"+ _accno+"','" + _pword +"');");
            //out.println("Welcome!!" + _fname + "!! You have created your new account. Please go on to Login page to access your account.");
            response.sendRedirect("index.html");
            //out.println("INSERT INTO account(fname, lname, accno, password) VALUES ('"+_fname+"','"+ _lname+"','"+ _accno+"','" + _pword +"');");
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SignupServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignupServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
