<html>
    <head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Wireless Health Care System</title>
<link href="Css/style.css" rel="stylesheet" type="text/css" media="screen" />
<script language="javascript">
function validate()
{
var flag =0;
	if(document.myform.PatientId.value=="")
	{
		alert("PatientId is not Empty");
		document.myform.PatientId.focus();
		return false;
	}
	else if(document.myform.Patientname.value=="")
	{
		alert("Patientname is not Empty");
		document.myform.Patientname.focus();
		return false;
	}
        else if(document.myform.PatientAddress.value=="")
	{
		alert("PatientAddress is not Empty");
		document.myform.PatientAddress.focus();
		return false;
	}
        else if(document.myform.Patientcontactno.value=="")
	{
		alert("Contact No is not Empty");
		document.myform.Patientcontactno.focus();
		return false;
	}
        else if(document.myform.Patientmailid.value=="")
	{
		alert("Mail Id is not Empty");
		document.myform.Patientmailid.focus();
		return false;
	}
         else if(document.myform.Patientpassword.value=="")
	{
		alert("Password is not Empty");
		document.myform.Patientpassword.focus();
		return false;
	}         
	else
	{
		return true;
	}
}
function numvalidate(s)
{
	 var i;
	s = s.toString();
      for (i = 0; i < s.length; i++)
      {
         var c = s.charAt(i);
         if (isNaN(c)) 
	   {
		alert("Only numbers");
		return false;
	   }
      }
      return true;
}
</script>
</head>
<body background="images/BG0.jpg">
    <form id="myform" name="myform" method="post" action="PatInput.jsp" onSubmit="return validate();" >
        <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
        <br><br><br><br>
        <font size="6" face="Times New Roman"><center><b>Patient's Registration</b></center><br></font>
        <center>        
               <table>
                <tr><td><b>Patient Id</b></td><td><input type="text" name="PatientId" class="text" onkeydown="numvalidate(this.value);"></td></tr>
                <tr><td><b>Patient Name</b></td><td><input type="text" name="Patientname" class="text"></td></tr>
                <tr><td><b>Patient Address</b></td><td>
                        <textarea name="PatientAddress" cols="15" rows="2" class="textarea" >
                            
                        </textarea>
                </td></tr>
                <tr><td><b>Patient Contactno</b></td><td><input type="text" name="Patientcontactno" class="text" onkeydown="numvalidate(this.value);"></td></tr>
                <tr><td><b>Patient MailId</b></td><td><input type="text" name="Patientmailid" class="text"></td></tr>
                <tr><td><b>Patient Password</b></td><td><input type="password" name="Patientpassword" class="text"></td></tr>
                <tr><td colspan="2"></td></tr>
                <tr><td colspan="2" align="center"><b><input type="submit" value="Submit"  onFocus="validate();"></b>
                    <font size="6"><a href="Login.jsp">Logout</a></font></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><font size="6"><a href="Appointment.jsp">Get Appointment</a></font>
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
