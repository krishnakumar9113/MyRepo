
<%@ page import="java.sql.*"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Wireless Health Care System</title>
<link href="Css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body background="images/BG0.jpg">
  <form name="myform" id="myform" method="post" action="Feed">
       <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>

			<br/><p> <br/> <br/>
				  <table align="center" cellpadding="3" cellspacing="0" border="2" >
                                      <tr><th>Patient Id</th><th>Patient Name</th><th>Contact</th><th>Email Id</th><th>Suggestion</th></tr>
                                    <%
                                    Connection con;
                                    Statement stmt;
                                    ResultSet rs;
                                     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		      con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                               
	       stmt=con.createStatement();
               rs=stmt.executeQuery("select * from feedtab");
              
	      while(rs.next())
		{
                 %>
                                        <tr>
                                            <td><%=rs.getString("Patientid")%></td>
                                            <td><%=rs.getString("Patientname")%></td>
                                            <td><%=rs.getString("Contact")%></td>
                                            <td><%=rs.getString("Emailid")%></td>
                                           <td><%=rs.getString("Suggestion")%></td>
                                        </tr>
					
	        <%
		}		
      %> 
      <tr><td colspan="5" align="center" ><b><a href="Login.jsp" >Back</a></b></td></tr>
</table>
</form>
</body>
<center><div id="footer">
	<p>&copy; All Rights Reserved &bull;</p>
</div></center>


</html>
