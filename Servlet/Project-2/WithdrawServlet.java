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
@WebServlet(urlPatterns = {"/WithdrawServlet"})
public class WithdrawServlet extends HttpServlet {

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
            String _accno= request.getParameter("accno");
            String _pword= request.getParameter("password");
            int _amt = Integer.parseInt(request.getParameter("amount"));
            //out.println(_amt);
            String accno = null;
            String password = null;
            int amount;
            Boolean found= false;
            Statement stmnt= connection.createStatement();
            ResultSet rs= stmnt.executeQuery("select * from public.\"bankaccount\"");
            while(rs.next()){
                accno= rs.getString("accno");
                password=rs.getString("password");
                amount = rs.getInt("amount");
                if (accno.equals(_accno)&& password.equals(_pword))
                    {
                        found = true;
                        if(amount>0){
                            amount= amount - _amt;
                            //int i= stmnt.executeUpdate("UPDATE \"bankaccount\" SET \"amount\"="+amount+"");
                            String sql = "UPDATE bankaccount set amount = ? where accno= ? and password= ?";
                            PreparedStatement prepstmt = connection.prepareStatement(sql);
                            prepstmt .setInt(1, amount);
                            prepstmt .setString(2, _accno);
                            prepstmt .setString(3, _pword);
                            int i= prepstmt .executeUpdate();
                            if(i!=0){
                                out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Balance</title>");            
                                out.println("</head>");
                                out.println("<body>");
                                out.println("You are withdrawing "+_amt+".");
                                out.println(" Your balance is: "+amount);
                                out.println("<button><a href=\"index.html\">Go back to Index Page</a></button>");
                                out.println("</body>");
                                out.println("</html>");
                            }                            
                        }
                        else{
                            out.println("<!DOCTYPE html>");
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>Balance</title>");            
                                out.println("</head>");
                                out.println("<body>");
                                out.println("You don't have sufficient balance to withdraw. Your balance is: "+amount);
                                out.println("<button><a href=\"index.html\">Go back to Index Page</a></button>");
                                out.println("</body>");
                                out.println("</html>");
                        }
                        break;
                    }
            }
            if (false == found)
                {
                    out.println("Login Failed");
                }
                connection.close();               
        }
        catch(SQLException sq){
            out.println(sq.getMessage());
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
            out.println("<title>Servlet WithdrawServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WithdrawServlet at " + request.getContextPath() + "</h1>");
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
