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
		 int DoctorId=Integer.parseInt(request.getParameter("DoctorId"));
        	 String DoctorName=request.getParameter("DoctorName");
        	 String Qualification=request.getParameter("Qualification");
        	 String Gender=request.getParameter("gender");
        	 String Specialized=request.getParameter("Specialized");
        	 String ClinicAddress=request.getParameter("ClinicAddress");
        	 String AvailableTime=request.getParameter("AvailableTime");
        	 String Location=request.getParameter("Location");
        	 String ContactNo=request.getParameter("ContactNo");
        	 String Mailid=request.getParameter("Mailid");
        	 String docpassword=request.getParameter("docpassword");
        	
		 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
 
          
	             
               stmt=con.createStatement();
               stmt.executeUpdate("insert into Doctab( DoctorId ,DoctorName,Qualification,Gender,Specialized,ClinicAddress," +
              "AvailableTime,Location,ContactNo,Mailid,docpassword)values ('"+DoctorId+"','"+DoctorName+" ','"+Qualification+"','"+Gender+"','"+Specialized+"','"+
                ClinicAddress+"','"+AvailableTime+"','"+Location+" ','"+ContactNo+"','"+Mailid+"','"+docpassword+"')");
               
              stmt=con.createStatement();
              stmt.executeUpdate("insert into Login(UserName,password,UserType)values ('"+DoctorName+"','"+docpassword+"','Doctor')");
                       
                      response.sendRedirect("DocRegister.jsp"); 
                     
}
         catch(Exception e)
		{
		out.println(e);                
                 }
%>



