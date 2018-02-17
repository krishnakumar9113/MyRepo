<html>
<title>Wireless Health Care System</title>

    <head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title></title>
<link href="Css/style.css" rel="stylesheet" type="text/css" media="screen" />
<script language="javascript">
function validate()
{
var flag =0;
           
	if(document.myform.Patientid.value=="")
	{
		alert("Patient ID is not Empty");
		document.myform.Patientid.focus();
		//break;
		return false;
	}
	else if(document.myform.Patientname.value=="")
	{
		alert("Patient Name is not Empty");
		document.myform.Patientname.focus();
		return false;
	}
        else if(document.myform.Contact.value=="")
	{
		alert("Contact is not Empty");
		document.myform.Contact.focus();
		return false;
	}
        else if(document.myform.Emailid.value=="")
	{
		alert("Email Id is not Empty");
		document.myform.Emailid.focus();
		return false;
	}
        else if(document.myform.Suggestion.value=="")
	{
		alert("Suggestion is not Empty");
		document.myform.Suggestion.focus();
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
<form id="myform" name="myform" method="post" action="FeedbackInput.jsp" onSubmit="return validate();" >
   <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
<br><br><br><br>
<font size="6" face="Times New Roman"><center><b>Feedback Entry</b></center><br></font>
    <center>        
        <table>             
            <tr><td><b>Patient ID</b></td><td><input type="text" name="Patientid" class="text" onkeydown="numvalidate(this.value);"></td></tr>
            <tr><td><b>Patient Name</b></td><td><input type="text" name="Patientname" class="text"></td></tr>
             <tr><td><b>Contact</b></td><td><input type="text" name="Contact" class="text"></td></tr>
            <tr><td><b>Email Id</b></td><td><input type="text" name="Emailid" class="text"></td></tr>
            <tr><td><b>Suggestion</b></td><td>
                    <textarea name="Suggestion" cols="15" rows="2" class="textarea">
                    </textarea>
            </td></tr>
            <tr><td colspan="2"></td></tr>
            <tr><td colspan="2" align="center"><b><input type="submit" value="Submit" onFocus="validate();"></b>
            <font size="6"><a href="Login.jsp">Logout</a></font></td>
            <td></td></tr>
        </table>
    </center>
</body>
<center><div id="footer">
	<p>&copy; All Rights Reserved &bull;</p>
</div></center>
</html>
