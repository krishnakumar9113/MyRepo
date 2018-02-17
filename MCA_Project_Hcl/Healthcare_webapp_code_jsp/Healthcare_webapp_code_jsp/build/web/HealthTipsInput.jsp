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
		 int sno=Integer.parseInt(request.getParameter("sno"));
        	 String Tip=request.getParameter("Tip");
        	
		 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
               stmt=con.createStatement();
               stmt.executeUpdate("insert into HealthTips(sno,Tip)values ('"+
                       sno+"','"+Tip+"')");
              response.sendRedirect("HealthTips.jsp"); 
}
         catch(Exception e)
		{
		out.println(e); 
               
                 }
%>