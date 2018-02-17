<%@ page import="java.sql.*"%>
<%@ page import="java.util.Calendar"%>



<%
Connection con,con1;
Statement stmt;
ResultSet rs;
%>
<html>
    <body>
        <a href ="Treatment.jsp">Back </a>
    </body>
</html>

<%
	try     
	{
            int count=0,docid=0;
                                
		 int DoctorId=Integer.parseInt(request.getParameter("DoctorId"));
        	 String DoctorName=request.getParameter("DoctorName");
        	 String Qualification=request.getParameter("Qualification");
        	 String PatientId=request.getParameter("PatientId");
        	 String PatientName=request.getParameter("PatientName");
        	 String Dtype=request.getParameter("Dtype");
        	 String Prescription=request.getParameter("Prescription");

              Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                      
               stmt=con.createStatement();
               stmt.executeUpdate("insert into Treatment( DoctorId ,DoctorName,Qualification,PatientId,PatientName,Dtype,Prescription)values ('"+
                DoctorId+"','"+DoctorName+" ','"+Qualification+"','"+PatientId+"','"+PatientName+"','"+Dtype+"','"+Prescription +"')");
 
               stmt=con.createStatement();
               stmt.executeUpdate("Update Pattable set Status='Old' , Prescription ='" + Prescription +"' where PatientId='" + PatientId +"'");
                                 
               stmt=con.createStatement();
               stmt.executeUpdate("insert into Pharmacy(  PatientId ,Patientname,DoctorId,DoctorName,DType,Prescription ,Amount)values ('"+
                       PatientId+"','"+PatientName+" ','"+DoctorId+"','"+DoctorName+"','"+Dtype+"','"+Prescription +"','0')");
                   Calendar calendar2 = Calendar.getInstance();

out.println("your value is inserted!!!");
   out.print("<br>");

 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
   stmt=con.createStatement();
               stmt.executeUpdate("insert into Treatment( DoctorId ,DoctorName,Qualification,PatientId,PatientName,Dtype,Prescription)values ('"+
                DoctorId+"','"+DoctorName+" ','"+Qualification+"','"+PatientId+"','"+PatientName+"','"+Dtype+"','"+Prescription +"')");

               stmt=con.createStatement();
               stmt.executeUpdate("Update Pattable set Status='Old' , Prescription ='" + Prescription +"' where PatientId='" + PatientId +"'");

               stmt=con.createStatement();
               stmt.executeUpdate("insert into Pharmacy(  PatientId ,Patientname,DoctorId,DoctorName,DType,Prescription ,Amount)values ('"+
                       PatientId+"','"+PatientName+" ','"+DoctorId+"','"+DoctorName+"','"+Dtype+"','"+Prescription +"','0')");
                }
                
                  catch(Exception e)
		{
		out.println(e);                
                 }
                
                %>



        
           

