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
		 int Sno=Integer.parseInt(request.getParameter("Sno"));
        	 String PatientId=request.getParameter("PatientId");
        	 String Patientname=request.getParameter("Patientname");
        	 String Patientcontact=request.getParameter("Patientcontact");
        	 String DoctorId=request.getParameter("DoctorId");
        	 String DoctorName=request.getParameter("DoctorName");
        	 String DType=request.getParameter("DType");
                 String Prescription=request.getParameter("Prescription");
        	  String Amount=request.getParameter("Amount");
        	 
		 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                   
                 
               stmt=con.createStatement();
               stmt.executeUpdate("insert into Pharmacy(Sno,PatientId,Patientname,Patientcontact,DoctorId,DoctorName,DType,Prescription,Amount 
              )values ('"+ Sno+"','"+PatientId+" ','"+Patientname+"','"+Patientcontact+"','"+DoctorId+"','"+DoctorName+"','"+DType +"','"+ Prescription +"','"+ Amount +"')");
 
               stmt=con.createStatement(); 	       
}
         catch(Exception e)
		{
		out.println(e);                
                 }
%>