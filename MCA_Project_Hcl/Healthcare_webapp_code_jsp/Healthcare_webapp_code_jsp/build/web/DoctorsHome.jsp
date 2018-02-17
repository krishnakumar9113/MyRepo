<html>
<title>Wireless Health Care System</title>

    <body background="images/BG0.jpg">
<form id="form1" name="form1" method="post" action="ValidateLogin.jsp" >
   <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
<br><br><br><br>
<font size="6" face="Times New Roman"><center><b>Doctors Home</b></center><br></font>
<%
String strresult = "";
strresult= "Welcome, "+ session.getAttribute("sessionId") ;%>
    <font size="6"><%
        out.println(strresult);
        %>
    </font>
    <center>
        <table>
            <tr><td ><font size="6"><a href="Treatment.jsp">Treatment</a></font></td></tr>
            <tr><td ><font size="6"><a href="NewAppointment.jsp">New Appointment</a></font></td></tr>
            <tr><td><font size="6"><a href="OldAppointment.jsp">Previous Appointment</a></font></td></tr>
            <tr><td><font size="6"><a href="ViewHospital.jsp">Hospital Recommentation</a></font></td></tr>
            <tr><td><font size="6"><a href="Login.jsp">Logout</a></font></td></tr>
        </table>
    </center>
</body>
<center><div id="footer">
	<p>&copy; All Rights Reserved &bull;</p>
</div></center>
</html>