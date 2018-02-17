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
		 int HospitalID=Integer.parseInt(request.getParameter("HospitalID"));
        	 String NameoftheHospital=request.getParameter("NameoftheHospital");
        	 String Address=request.getParameter("Address");
        	 String Spltreatment=request.getParameter("Spltreatment");
        	 String Hospcategoryperson=request.getParameter("Hospcategoryperson");
        	 String FounderName=request.getParameter("FounderName");
        	 String Ageofhospital=request.getParameter("Ageofhospital");
        	 String Persontocontact=request.getParameter("Persontocontact");
        	 String contact=request.getParameter("contact");
        	 String mailID=request.getParameter("mailID");
        	
		 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                        stmt=con.createStatement();
               stmt.executeUpdate("insert into Hospitaltab( HospitalID,NameoftheHospital,Address,Spltreatment,Hospcategoryperson, FounderName," +
                "Ageofhospital,Persontocontact,contact,mailID)values ('"+HospitalID+"','"+NameoftheHospital+" ','"+Address+"','"+Spltreatment+"','"+
              Hospcategoryperson+"','"+FounderName+"','"+Ageofhospital+"','"+Persontocontact+" ','"+contact+"','"+mailID+"')");
              response.sendRedirect("HosRegister.jsp"); 
}
         catch(Exception e)
		{
		out.println(e); 
               
                 }
%>