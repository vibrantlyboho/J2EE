<%-- 
    Document   : login
    Created on : Oct 12, 2020, 10:36:32 AM
    Author     : Jo
--%>
<%@ page import ="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
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
                String _mailid = request.getParameter("mailid");
                String _pword = request.getParameter("password"); 
                String mailid = null;
                String fname = null;
                String pword= null;
                Boolean found= false;
                Statement stmnt= connection.createStatement();
                ResultSet rs= stmnt.executeQuery("select * from public.\"maildb\"");
                while(rs.next())
                {
                    mailid= rs.getString("mailid");
                    pword = rs.getString("password");
                    fname = rs.getString("fname");
                    if (mailid.equals(_mailid)&&pword.equals(_pword))
                    {
                       found =true;
                       out.println("Welcome " + fname);
                    }
                }
                if (found == false)
                {
                    out.println("Login Failed");
                }
                            
                connection.close();            
            }
            catch( SQLException sqlex)
            {
                out.println(sqlex.getMessage());
            }
                       
            
        %>
    </body>
</html>
