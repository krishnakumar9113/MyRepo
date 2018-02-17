<script language="javascript">
function validate()
{
var flag =0;                              
	if(document.myform.username.value=="")
	{
		alert("user Name  is not Empty");
		document.myform.username.focus();
		return false;
	}  
       else if(document.myform.password.value=="")
	{
		alert("Password is not Empty");
		document.myform.password.focus();
		//break;
		return false;
	}
	else
	{
		return true;
	}
}

</script>
<html>
<title>Wireless Health Care System</title>

    <body background="images/BG0.jpg">
     
            <form id="form1" name="myform" method="post" action="ValidateLogin.jsp" >
            <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
            <br><br><br><br>
            <font size="6" face="Times New Roman"><center><b>Login Page</b></center><br></font>
            <center>
                <table>
                    <tr>
                        <td>
                            <table>
                                <tr><td><font size="6"><a href="PatRegister.jsp">Patient Registration</a></font></td></tr>
                                <tr><td><font size="6"><a href="ViewHealthTip.jsp">Health Tips</a></font></td></tr>
                                <tr><td><font size="6"><a href="ViewFeedback.jsp">Feedbacks</a></font></td></tr>
                                  <tr><td><font size="6"><a href="Appointment.jsp">Get Appointment</a></font></td></tr>
                              
                            </table>
                        </td>
                        <td>
                            <table>
                                <tr><td><b>User Name</b></td><td><input type="text" name="username"></td></tr>
                                <tr><td><b>Password</b></td><td><input type="password" name="password"></td></tr>
                                <tr><td><b>User Type</b></td><td><select name="UserType"  >
                                            <option>- Select -</option>
                                            <option>Admin</option>
                                            <option>Doctor</option>
                                            <option>Patient</option>
                                </select></td></tr>
                                <tr><td colspan="2"></td></tr>
                                <tr><td></td><td><b><input type="submit" value="Submit"  onFocus="validate();"></b></td></tr>
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
