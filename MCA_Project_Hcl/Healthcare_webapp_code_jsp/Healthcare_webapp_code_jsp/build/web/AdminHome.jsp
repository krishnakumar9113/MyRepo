<html>
<body background="images/BG0.jpg">
<title>Wireless Health Care System</title>

    <form id="form1" name="form1" method="post" action="ValidateLogin.jsp" >
    <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
   <br><br><br><br>
<font size="6" face="Times New Roman"><center><b>Admin Home</b></center><br></font>
<%
String strresult = "";
strresult= "Welcome !!! "+ session.getAttribute("sessionId") ;%>
    <font size=6><%
        out.println(strresult);
        %>
    </font>
    <center>
        <table>
            <tr><td ><font size="6"><a href="DocRegister.jsp">Register Doctor</a></font></td></tr>
            <tr><td><font size="6"><a href="HosRegister.jsp">Register Hospital</a></font></td></tr>
           <tr><td><font size="6"><a href="Timing.jsp">Insert Timings</a></font></td></tr>
          
            <tr><td><font size="6"><a href="ViewAccounts.jsp">View Accounts</a></font></td></tr>
           <tr><td><font size="6"><a href="ViewFeedback.jsp">View Feedback</a></font></td></tr>
           <tr><td><font size="6"><a href="HealthTips.jsp">Health Tips Update</a></font></td></tr>
           <tr><td><font size="6"><a href="Login.jsp">Logout</a></font></td></tr>
           </table>
    </center>
</body>
<center><div id="footer">
	<p>&copy; All Rights Reserved &bull;</p>
</div></center>

</html>
