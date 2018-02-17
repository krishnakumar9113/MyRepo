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
		 int PatientId=Integer.parseInt(request.getParameter("PatientId"));
        	 String Patientname=request.getParameter("Patientname");
        	 String Patientcontact=request.getParameter("Patientcontact");
        	 int DoctorId=Integer.parseInt(request.getParameter("DoctorId"));
        	 String DoctorName=request.getParameter("DoctorName");
        	 String Doclocation=request.getParameter("Doclocation");
        	 String Doccontactno=request.getParameter("Doccontactno");
        	 String Visitstatus=request.getParameter("Visitstatus");
        	 String Appomentregistered=request.getParameter("Appomentregistered");
        	 String appomenttiming=request.getParameter("appomenttiming");
        	
		String Paymentmode=request.getParameter("Paymentmode");
        	 String Accountno=request.getParameter("Accountno");
        	 int Servicecharge=Integer.parseInt(request.getParameter("Servicecharge"));
        	 String Amountstatus=request.getParameter("Amountstatus");
        	
               Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                     
                 stmt=con.createStatement();
               stmt.executeUpdate("insert into apptab(PatientId ,Patientname,Patientcontact,DoctorId,DoctorName,Doclocation," +
               "Doccontactno,Visitstatus,Appointmentregistered, appointmenttiming)values ('"+PatientId+"','"+Patientname+" ','"+Patientcontact+ 
               "','"+DoctorId+"','"+DoctorName+"','"+Doclocation+"','"+Doccontactno+"','"+Visitstatus+" ','"+Appomentregistered+"','"+
                       appomenttiming+"')");
            
                 stmt=con.createStatement();
               stmt.executeUpdate("insert into Acctab(PatientId ,Paymentmode, Accountno, Servicecharge,Amountstatus)values ('"+
                       PatientId+"','"+Paymentmode+" ','"+Accountno+"','"+Servicecharge+"','"+Amountstatus+"')");
            
               response.sendRedirect("PatRegister.jsp"); 
	       
}

         catch(Exception e)
		{
		out.println(e); 
               
                 }
%>



