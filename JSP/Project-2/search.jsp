<%-- 
    Document   : search
    Created on : Oct 25, 2020, 7:49:57 PM
    Author     : Jo
--%>
<%@ page import ="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Result</title>
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
                int _emp_id = Integer.parseInt(request.getParameter("emp_id"));
                String ename = null;
                //int salary;
                int eid;
                String dept;
                Boolean found= false;
                
                Statement stmnt= connection.createStatement();
                ResultSet rs= stmnt.executeQuery("select * from public.\"employee\"");
                while(rs.next())
                {
                    eid = Integer.parseInt(rs.getString("emp_id"));
                    if (eid == _emp_id)
                    {
                        %>
                            <h1>The employee is found!</h1>
                            <br>
                        <%
                        ename = rs.getString("emp_name");
                        //salary = Integer.parseInt(rs.getString("emp_salary"));
                        dept = rs.getString("emp_dept");
                        found =true;
                        out.println("The name of the employee is " + ename); 
                        out.println(". The department the employee is " + dept);
                        //out.println(" and the salary given to the employee is " + salary); 
                    }
                }
                if (found == false)
                {
                    %>
                            <h1>There is no such employee!</h1>
                    <%
                    out.println("Please login again or create a new account!");
                    %>    
                    <br>
                    <br>
                    <button><a href="login.html">Login</a></button>
                    <br>
                    <br>
                    <button><a href="registration.html">Registration</a></button>
                    <% 
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
