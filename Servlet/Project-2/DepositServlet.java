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
@WebServlet(urlPatterns = {"/DepositServlet"})
public class DepositServlet extends HttpServlet {

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
            int _amt = Integer.parseInt(request.getParameter("amount"));
            //out.println(_amt);
            String accno = null;
            int amount;
            Boolean found= false;
            Statement stmnt= connection.createStatement();
            ResultSet rs= stmnt.executeQuery("select * from public.\"bankaccount\"");
            while(rs.next()){
                accno= rs.getString("accno");
                amount = rs.getInt("amount");
                if (accno.equals(_accno))
                    {
                        found = true;
                        amount= amount + _amt;
                        //String sql= "UPDATE bankaccount SET amount="+amount+"WHERE accno="+_accno+"";
                        //int i= stmnt.executeUpdate(sql);
                        String sql = "UPDATE bankaccount set amount = ? where accno= ?";
                        PreparedStatement prepstmt = connection.prepareStatement(sql);
                        prepstmt .setInt(1, amount);
                        prepstmt .setString(2, _accno);
                        int i= prepstmt .executeUpdate();
                        if(i!=0){
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Balance</title>");            
                            out.println("</head>");
                            out.println("<body>");
                            out.println("Your balance is: "+amount);
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
            out.println("<title>Servlet DepositServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DepositServlet at " + request.getContextPath() + "</h1>");
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
