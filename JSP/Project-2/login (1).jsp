<%-- 
    Document   : login
    Created on : Oct 25, 2020, 7:31:53 PM
    Author     : Jo
--%>
<%@ page import ="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logged in</title>
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
                String _emp_name = request.getParameter("emp_name");
                int _emp_id = Integer.parseInt(request.getParameter("emp_id"));
                String ename = null;
                int eid;
                int salary;
                String dept;
                Boolean found= false;
                
                Statement stmnt= connection.createStatement();
                ResultSet rs= stmnt.executeQuery("select * from public.\"employee\"");
                while(rs.next())
                {
                    ename = rs.getString("emp_name");
                    eid = Integer.parseInt(rs.getString("emp_id"));
                    if (ename.equals(_emp_name))
                    {
                        if(eid == _emp_id)
                        {
                            salary = Integer.parseInt(rs.getString("emp_salary"));
                            dept = rs.getString("emp_dept");
                            %>
                            <h1>You have logged in!</h1>
                            <%
                            found =true;
                            out.println("Welcome " + ename);
                            out.println(". Your department is " + dept);
                            out.println(" and your salary is " + salary);
                            
                            
                            
                        }
                    }
                }
                if (found == false)
                {
                    %>
                            <h1>Login has failed!!</h1>
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
