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
		 int Patientid=Integer.parseInt(request.getParameter("Patientid"));
        	 String Patientname=request.getParameter("Patientname");
        	 String Contact=request.getParameter("Contact");
        	 String Emailid=request.getParameter("Emailid");
        	 String Suggestion=request.getParameter("Suggestion");
        	
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                 stmt=con.createStatement();
               stmt.executeUpdate("insert into feedtab( Patientid,Patientname,Contact,Emailid,Suggestion)values ('"+
                       Patientid+"','"+Patientname+" ','"+Contact+"','"+Emailid+"','"+Suggestion+"')");
              response.sendRedirect("Feedback.jsp"); 
}
         catch(Exception e)
		{
		out.println(e); 
               
                 }
%>