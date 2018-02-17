<html>
    <head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Wireless Health Care System</title>

<link href="Css/style.css" rel="stylesheet" type="text/css" media="screen" />
<script language="javascript">
function validate()
{
var flag =0;              
if(document.myform.Date.value=="")
	{
		alert("Date is not Empty");
		document.myform.Date.focus();
		return false;
	}
	else if(document.myform.Doctorid.value=="")
	{
		alert("Doctor Id is not Empty");
		document.myform.Doctorid.focus();
		return false;
	}
        else if(document.myform.DoctorName.value=="")
	{
		alert("Doctor Name is not Empty");
		document.myform.DoctorName.focus();
		return false;
	}
        else if(document.myform.Timings.value=="")
	{
		alert("Timings No is not Empty");
		document.myform.Timings.focus();
		return false;
	}
        else if(document.myform.Status.value=="")
	{
		alert("Status Id is not Empty");
		document.myform.Status.focus();
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
    <form id="myform" name="myform" method="post" action="TimingInput.jsp" onSubmit="return validate();" >
        <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
        <br><br><br><br>
        <font size="6" face="Times New Roman"><center><b>Timing Entry</b></center><br></font>
        <center>        
               <table>
                <tr><td><b>Date</b></td><td><input type="text" name="Date" class="text"></td></tr>
                <tr><td><b>Doctorid</b></td><td><input type="text" name="Doctorid" class="text"></td></tr>
                <tr><td><b>DoctorName</b></td><td><input type="text" name="DoctorName" class="text"></td></tr>
                <tr><td><b>Timings</b></td><td><input type="text" name="Timings" class="text"></td></tr>
                <tr><td><b>status</b></td><td><input type="text" name="Status" class="text"></td></tr>
               
                <tr><td colspan="2"></td></tr>
                <tr><td colspan="2" align="center"><b><input type="submit" value="Submit"  onFocus="validate();"></b>
                    <font size="6"><a href="Login.jsp">Logout</a></font></td>
                    <td></td>
                </tr>
                  <tr><td colspan="2" align="center">
                           <font size="6">
                        <a href="ViewAppointment.jsp">Appointment</a></font>
                  </td>
                  <td></td>
                </tr>
            </table>
        </center>
    </form>
</body>
<center><div id="footer">
	<p>&copy; All Rights Reserved &bull;</p>
</div></center>


</html>
