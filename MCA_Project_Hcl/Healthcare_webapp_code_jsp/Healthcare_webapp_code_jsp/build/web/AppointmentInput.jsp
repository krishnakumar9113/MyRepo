<%@ page import="java.sql.*"%>

<%
Connection con;
Statement stmt;
ResultSet rs;
%>

<%
	try     
	{
                 
             	 int count=0,docid=0;
		 int Patientid=Integer.parseInt(request.getParameter("PatientId"));
        	 String Patientname=request.getParameter("Patientname");
        	 String Patientcontact=request.getParameter("Patientcontact");
        	 int DoctorId=Integer.parseInt(request.getParameter("DoctorId"));
        	 String DoctorName=request.getParameter("DoctorName");
        	 String Doclocation=request.getParameter("Doclocation");
        	 String Doccontactno=request.getParameter("Doccontactno");
        	 String Visitstatus=request.getParameter("Visitstatus");
        	 String Appomentregistered=request.getParameter("Appointmentregistered");
                 String AppDate=request.getParameter("AppDate");
        	 String appomenttiming=request.getParameter("appointmenttiming");
        	
                 String Paymentmode=request.getParameter("Paymentmode");
        	 String Accountno=request.getParameter("Accountno");
        	 String Servicecharge=request.getParameter("Servicecharge");
        	 String Amountstatus=request.getParameter("Amountstatus");
                 
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
 
	                      stmt=con.createStatement();
                 stmt.executeUpdate("insert into apptab(PatientId,Patientname,Patientcontact,DoctorId,DoctorName,Doclocation,Doccontactno" +
                 ",Visitstatus,Appomentregistered,AppDate,appomenttiming)values ('"+Patientid+"','"+Patientname+"','"+Patientcontact+"','"+
                 DoctorId+"','"+DoctorName+"','"+Doclocation+"','"+Doccontactno+"','"+Visitstatus+"','"+
                 Appomentregistered+"','"+AppDate +"','"+appomenttiming+"')");

              stmt=con.createStatement();
              stmt.executeUpdate("insert into Acctab( Patientid,Paymentmode,Accountno,AppDate,Servicecharge,Amountstatus)values ('"+
              Patientid+"','"+Paymentmode+" ','"+Accountno+" ','"+AppDate+"','"+Servicecharge+"','"+Amountstatus+"')");
         
              response.sendRedirect("Appointment.jsp"); 
}
         catch(Exception e)
		{
		out.println(e);                
                 }
%>
