
<%@ page import="java.sql.*"%>

<%
Connection con;
Statement stmt;
ResultSet rs;         
%>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Wireless Health Care System</title>
<link href="Css/style.css" rel="stylesheet" type="text/css" media="screen" />
</head>

<body background="images/BG0.jpg">
<form id="myform" name="myform" method="post" action="Appointment.jsp" >
<marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
<br><br><br>
<font size="6" face="Times New Roman"><center><b></b></center><br></font>
<%
String strresult = "";
strresult= "Welcome !!! "+ session.getAttribute("sessionId") ;%>
    <font size=6><%
        out.println(strresult);
        %>
    </font>
<center>
    <table>
        <tr>
            <td>
                <table  border="2" >
           <tr><th>Patient Prev Prescription</th></tr>
          
                    <tr>
            <%
                                     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		      con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                               
	       stmt=con.createStatement();
              
	     rs=stmt.executeQuery("select Patientname,Status,Prescription from Pattable where Patientname='" +
                     session.getAttribute("sessionId")  + "' and Status='Old' " );
              
	      while(rs.next())
		{
                 %>
                                        <tr>
                                            <td><%=rs.getString("Prescription")%></td>
                                        </tr>
	        <%
		}		
      %> 
                    </tr>
                </table>
            </td>
            <td>
                  <table border="2">              
           <tr><th>Doctor Id</th><th>Dieseas</th><th>Specialized</th><th>appomenttiming</th></tr>
               <%
                                     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		      con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                               
	       stmt=con.createStatement();
              
	    // rs=stmt.executeQuery("select D.DoctorId,D.DoctorName,D.Specialized,A.appomenttiming from Doctab D Inner  Join apptab A On D.DoctorId = A.DoctorId ");
                                
             rs=stmt.executeQuery("select DoctorId,DoctorName,Specialized,AvailableTime from Doctab");
               int i=0;
	      while(rs.next())
		{                   
                 %>
                                        <tr>
                                            <td><%=rs.getString("DoctorId")%></td>
                                            <td><%=rs.getString("DoctorName")%></td>
                                            <td><a  href="Appointment.jsp">
                                                <%=rs.getString("Specialized")%>
                                            </a></td>
                                           <td><%=rs.getString("AvailableTime")%></td>
                                        </tr>
	        <%
		}		
      %> 
    <tr><td colspan="4" align="center">  <font size="6"><a href="Login.jsp">Logout</a></font>
    </table>

            </td>
        </tr>
    </table>
  </center>
</form>

</body>
<center><div id="footer">
	<p>&copy; All Rights Reserved &bull;</p>
</div></center>

</html>
