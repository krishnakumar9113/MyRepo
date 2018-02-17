<%@ page import="java.sql.*"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Wireless Health Care System</title>
<link href="Css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body background="images/BG0.jpg">
			<br/><p> <br/> <br/><form name="myform" id="myform" method="post" action="">
                             <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>

			<br/><p> <br/> <br/>
				  <table align="center" cellpadding="3" cellspacing="0" border="2" >
                                      <tr><th>Hospital Name</th><th>Address</th><th>Spltreatment</th><th>Persontocontact</th><th>contact</th><th>contact</th></tr>
                                    <%
                                    Connection con;
                                    Statement stmt;
                                    ResultSet rs;
                       Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		      con=DriverManager.getConnection("jdbc:odbc:HC","sa","star"); 
                                        
	       stmt=con.createStatement();
               rs=stmt.executeQuery("select * from Hospitaltab");
	      while(rs.next())
		{
                 %>
                                                    <tr>
                                            <td><%=rs.getString("NameoftheHospital")%></td>
                                            <td><%=rs.getString("Address")%></td>
                                            <td><%=rs.getString("Spltreatment")%></td>
                                            <td><%=rs.getString("Persontocontact")%></td>
                                            <td><%=rs.getString("contact")%></td>
                                            <td><%=rs.getString("mailID")%></td>
                                        </tr>
					
	        <%
		}		
      %>
      <tr><td colspan="6" align="center" ><b><a href="DoctorsHome.jsp" >Back</a></b></td></tr>
</table>
</form>
</body>
<center><div id="footer">
	<p>&copy; All Rights Reserved &bull;</p>
</div></center>
</html>
