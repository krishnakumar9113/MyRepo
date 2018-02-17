<html>
    <head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Wireless Health Care System</title>
<link href="Css/style.css" rel="stylesheet" type="text/css" media="screen" />
<script language="javascript">
function validate()
{
var flag =0;
	if(document.myform.HospitalID.value=="")
	{
		alert("HospitalID is not Empty");
		document.myform.HospitalID.focus();
		//break;
		return false;
	}
	else if(document.myform.NameoftheHospital.value=="")
	{
		alert("Hospital Name is not Empty");
		document.myform.NameoftheHospital.focus();
		return false;
	}
        else if(document.myform.Address.value=="")
	{
		alert("Address is not Empty");
		document.myform.Address.focus();
		return false;
	}
        else if(document.myform.Spltreatment.value=="")
	{
		alert("Spl Treatment is not Empty");
		document.myform.Spltreatment.focus();
		return false;
	}
        else if(document.myform.Hospcategoryperson.value=="")
	{
		alert("Hospital Category is not Empty");
		document.myform.Hospcategoryperson.focus();
		return false;
	}
         else if(document.myform.FounderName.value=="")
	{
		alert("Founder Name is not Empty");
		document.myform.FounderName.focus();
		return false;
	}
        else if(document.myform.Ageofhospital.value=="")
	{
		alert("Hospital Age is not Empty");
		document.myform.Ageofhospital.focus();
		return false;
	}
  	else if(document.myform.Persontocontact.value=="")
	{
		alert("Contact Person is not Empty" + document.myform.Mailid.value );
		document.myform.Persontocontact.focus();
		return false;
	}
        	else if(document.myform.mailID.value=="")
	{
		alert("Mail ID is not Empty");
		document.myform.mailID.focus();
		return false;
	}
	else if(document.myform.contact.value=="")
	{
		alert("Contact No is not Valid");
		document.myform.contact.focus();
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
<form id="myform" name="myform" method="post" action="HospitalInput.jsp" onSubmit="return validate();" >
   <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
<br><br><br><br>
<font size="6" face="Times New Roman"><center><b>Hospital Entry</b></center><br></font>
    <center>        
        <table>
            <tr><td><b>Hospital ID</b></td><td><input type="text" name="HospitalID" class="text" onkeydown="numvalidate(this.value);"></td></tr>
            <tr><td><b>Hospital Name</b></td><td><input type="text" name="NameoftheHospital" class="text"></td></tr>
             <tr><td><b>Address</b></td><td>
                 <textarea name="Address" cols="15" rows="2" class="textarea"></textarea>                
             </td></tr>
    
                   <tr><td><b>Spl Treatment</b></td><td><input type="text" name="Spltreatment" class="text"></td></tr>
            <tr><td><b>Hospital Category</b></td><td><input type="text" name="Hospcategoryperson" class="text"></td></tr>
            <tr><td><b>Founder Name</b></td><td><input type="text" name="FounderName" class="text"></td></tr>
    
             <tr><td><b> Hospital Age</b></td><td><input type="text" name="Ageofhospital" class="text" onkeydown="numvalidate(this.value);"></td></tr>
            <tr><td><b>Contact Person</b></td><td><input type="text" name="Persontocontact" class="text"></td></tr>
            <tr><td><b>Contact</b></td><td><input type="text" name="contact" class="text" onkeydown="numvalidate(this.value);"></td></tr>
            <tr><td><b>Mail Id</b></td><td><input type="text" name="mailID" class="text"></td></tr>
             
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
