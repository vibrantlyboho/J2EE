<%-- 
    Document   : registration
    Created on : Oct 20, 2020, 3:24:13 PM
    Author     : Jo
--%>
<%@ page import ="java.sql.*" %>
<%@page import="java.io.PrintWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registered</title>
    </head>
    <body>
        <h1>You have completed your registration</h1>
        <%
            
            //defining the url
            Connection connection;
            String url = "jdbc:postgresql://localhost:5432/lab";
            String user = "postgres";
            String password = "password";

            try
            {
                //to load driver into memory
                Class.forName("org.postgresql.Driver");
            }
            catch(ClassNotFoundException e)
            {
                out.println(e.getMessage());
            }
            
            try
            {
                //establishing connection with db & this throws the SQL error
                connection = DriverManager.getConnection(url,user,password);
                String _emp_name = request.getParameter("emp_name");
                int _emp_id = Integer.parseInt(request.getParameter("emp_id"));
                String _emp_dept = request.getParameter("emp_dept");
                int _emp_salary = Integer.parseInt(request.getParameter("emp_salary"));
                
                Statement stmnt = connection.createStatement();
                int i = stmnt.executeUpdate("INSERT INTO \"employee\"(emp_name, emp_id, emp_dept, emp_salary) VALUES ('"+_emp_name+"','"+ _emp_id+"','"+ _emp_dept+"','" + _emp_salary +"');");
                out.println("Welcome!! " + _emp_name + "!! You have created your new account. Please go on to Login page to access your account.");
        %>    
                <br>
                <br>
                <button><a href="login.html">Login</a></button>
                <br>
                <br>
                <button><a href="index.html">Go back to Home Page</a></button>
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
