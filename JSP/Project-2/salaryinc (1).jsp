<%-- 
    Document   : salary
    Created on : Oct 26, 2020, 10:58:07 AM
    Author     : Jo
--%>
<%@ page import ="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Salary Incremented</title>
    </head>
    <body>
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
                String _dept = request.getParameter("dept");
                int _new_salary = Integer.parseInt(request.getParameter("new_salary"));
                int salary;
                int eid;
                String dept;
                int affectedrows = 0;
                Statement stmnt= connection.createStatement();
                String SQL = "UPDATE employee "
                        + "SET emp_salary = ? WHERE emp_dept = ?";
                PreparedStatement pstmt = connection.prepareStatement(SQL);
                pstmt.setInt(1, _new_salary);
                pstmt.setString(2, _dept);
                affectedrows = pstmt.executeUpdate();
                //out.println(affectedrows);
                
                if (affectedrows == 0)
                {
                    %>
                            <h1>There is no such department!</h1>
                    <%
                    out.println("Please select another department!");
                    %>    
                    <br>
                    <br>
                    <button><a href="salaryinc.html">Salary Increment</a></button>
                    <% 
                }
                else
                {
                    out.println("The salary is updated.");
                }
                %>
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
