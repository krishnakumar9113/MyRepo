<%@ page import="java.sql.*"%>


<%
Connection con;
Statement stmt;
ResultSet rs;
%>
<%
	try    
   	{
             	 int count=0;
		 String user=request.getParameter("username");
        	 String pass=request.getParameter("password");
                  String UserType=request.getParameter("UserType");
		   Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                stmt=con.createStatement();
               rs=stmt.executeQuery("select username,password,UserType from Login where username='"+user+"' and password='"+pass+"' and UserType='"+ UserType +"'");
	   
                
                while(rs.next())
		{
		count++;
		String name=rs.getString("username");
		String pwd=rs.getString("password");
                String User=rs.getString("UserType");
		
		if(name.equals(user) && pwd.equals(pass) && User.equals("Doctor"))
			{
                        response.sendRedirect("DoctorsHome.jsp");
			session.setAttribute("sessionId",user);

			}	
                        else if(name.equals(user) && pwd.equals(pass) && User.equals("Patient"))
			{
                        response.sendRedirect("PatientHome.jsp");
			session.setAttribute("sessionId",user);
			}
		       else if(name.equals(user) && pwd.equals(pass) && User.equals("Admin"))
			{
                        response.sendRedirect("AdminHome.jsp");
			session.setAttribute("sessionId",user);
			}
                        else
                            {
                            out.println("Invalid User Name and Password and User Type!!!!");
                            }
           	}
}

         catch(Exception e)
		{
		out.println(e); 
               
                 }
%>



