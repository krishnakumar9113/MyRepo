

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
		 String Date=request.getParameter("Date");
        	 String Doctorid=request.getParameter("Doctorid");
        	 String DoctorName=request.getParameter("DoctorName");
                 String Timings=request.getParameter("Timings");
        	 String Status=request.getParameter("Status");
        	
		 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                     
               stmt=con.createStatement();
               stmt.executeUpdate("insert into Timing(Date,Doctorid,DoctorName,Timings ,Status)" +
                       "values ('"+ Date+"','"+Doctorid+" ','"+DoctorName+"','"+Timings+"','"+Status+"')");
            
                 response.sendRedirect("Appointment.jsp"); 
	       
}
         catch(Exception e)
		{
		out.println(e); 
               
                 }
%>



