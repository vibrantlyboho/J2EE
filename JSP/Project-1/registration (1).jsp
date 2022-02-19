<%-- 
    Document   : registration
    Created on : Oct 12, 2020, 10:36:52 AM
    Author     : Jo
--%>
<%@ page import ="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration JSP Page</title>
    </head>
    <body>
        <%
            Connection connection;
            String url = "jdbc:postgresql://localhost:5432/lab";
            String user = "postgres";
            String password = "password";
          
            try
            {
                Class.forName("org.postgresql.Driver");
            }
            catch(ClassNotFoundException e)
            {
                out.println(e.getMessage());
            }

            try
            {
                connection = DriverManager.getConnection(url,user,password);
                String _fname = request.getParameter("fname");
                String _lname = request.getParameter("lname");
                String _mailid = request.getParameter("mailid");
                String _pword = request.getParameter("password"); 

                Statement stmnt = connection.createStatement();
                int i = stmnt.executeUpdate("INSERT INTO \"maildb\"(fname, lname, mailid, password) VALUES ('"+_fname+"','"+ _lname+"','"+ _mailid+"','" + _pword +"');");
                out.println("Welcome!!" + _fname + "!! You have created your new account. Please go on to Login page to access your account.");
        %>    
                <button><a href="login.html">Login</a></button>
        <%    
                connection.close();            
            }
            catch( SQLException sqlex)
            {
                out.println(sqlex.getMessage());
            }
            
            
        %>
    </body>
</html>
