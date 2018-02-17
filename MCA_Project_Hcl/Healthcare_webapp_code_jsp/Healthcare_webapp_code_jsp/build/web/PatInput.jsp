

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
		 int PatientId=Integer.parseInt(request.getParameter("PatientId"));
        	 String Patientname=request.getParameter("Patientname");
        	 String PatientAddress=request.getParameter("PatientAddress");
                 String Patientcontactno=request.getParameter("Patientcontactno");
        	 String Patientmailid=request.getParameter("Patientmailid");
        	 String Patientpassword=request.getParameter("Patientpassword");
        	
		  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
            
	             
               stmt=con.createStatement();
                 	 

               stmt.executeUpdate("insert into Pattable( PatientId,Patientname,PatientAddress,Patientcontactno ,Patientmailid,Patientpassword,Status,Prescription)" +
                       "values ('"+ PatientId+"','"+Patientname+" ','"+PatientAddress+"','"+Patientcontactno+"','"+Patientmailid+"','"+
                Patientpassword + "','New','Nil')");
            
                   stmt=con.createStatement();
               stmt.executeUpdate("insert into Login(UserName,password,UserType)values ('"+Patientname+"','"+Patientpassword+"','Patient')");
           
                response.sendRedirect("PatRegister.jsp"); 
	       
}
         catch(Exception e)
		{
		out.println(e); 
               
                 }
%>



