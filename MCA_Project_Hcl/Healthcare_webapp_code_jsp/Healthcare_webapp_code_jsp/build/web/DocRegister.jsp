<html>
    <head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Wireless Health Care System</title>

<link href="Css/style.css" rel="stylesheet" type="text/css" media="screen" />
<script language="javascript">
function validate()
{
var flag =0;
var len = document.myform.gen.length;
	for (i = 0; i <len; i++)
	{
		if (document.myform.gen[i].checked)
		{
			flag = 1;
			document.myform.gender.value=document.myform.gen[i].value;
		}
	}
	
	if(document.myform.DoctorId.value=="")
	{
		alert("Doctor Id is not Empty");
		document.myform.DoctorId.focus();
		//break;
		return false;
	}
	else if(document.myform.DoctorName.value=="")
	{
		alert("Doctor Name is not Empty");
		document.myform.DoctorName.focus();
		return false;
	}
        else if(document.myform.Qualification.value=="")
	{
		alert("Qualification is not Empty");
		document.myform.contactno.focus();
		return false;
	}
        else if(flag<=0)
	{
		alert("Select Gender");
		document.myform.gen[1].focus();
		return false;
	}
        else if(document.myform.ClinicAddress.value=="")
	{
		alert("Clinic Address is not Empty");
		document.myform.ClinicAddress.focus();
		return false;
	}
         else if(document.myform.AvailableTime.value=="")
	{
		alert("Available Time is not Empty");
		document.myform.AvailableTime.focus();
		return false;
	}
        else if(document.myform.Location.value=="")
	{
		alert("Location is not Empty");
		document.myform.Location.focus();
		return false;
	}
        
	else if(document.myform.Mailid.value=="")
	{
		alert("Patient's Mailid is not Empty" + document.myform.Mailid.value );
		document.myform.Mailid.focus();
		return false;
	}
        	else if(document.myform.docpassword.value=="")
	{
		alert("Doctor password is not Empty");
		document.myform.docpassword.focus();
		return false;
	}
	else if(document.myform.contactno.value=="")
	{
		alert("Patient's Contact No is not Valid");
		document.myform.contactno.focus();
		return false;
	}         
	else
	{
        	return true;
	
        }
}

function validate()
{
var flag =0;
var len = document.myform.gen.length;
	for (i = 0; i <len; i++)
	{
		if (document.myform.gen[i].checked)
		{
			flag = 1;
			document.myform.gender.value=document.myform.gen[i].value;
		}
	}
	
	if(document.myform.DoctorId.value=="")
	{
		alert("Doctor Id is not Empty");
		document.myform.DoctorId.focus();
		//break;
		return false;
	}
	else if(document.myform.DoctorName.value=="")
	{
		alert("Doctor Name is not Empty");
		document.myform.DoctorName.focus();
		return false;
	}
        else if(document.myform.Qualification.value=="")
	{
		alert("Qualification is not Empty");
		document.myform.contactno.focus();
		return false;
	}
        else if(flag<=0)
	{
		alert("Select Gender");
		document.myform.gen[1].focus();
		return false;
	}
        else if(document.myform.ClinicAddress.value=="")
	{
		alert("Clinic Address is not Empty");
		document.myform.ClinicAddress.focus();
		return false;
	}
         else if(document.myform.AvailableTime.value=="")
	{
		alert("Available Time is not Empty");
		document.myform.AvailableTime.focus();
		return false;
	}
        else if(document.myform.Location.value=="")
	{
		alert("Location is not Empty");
		document.myform.Location.focus();
		return false;
	}
        
	else if(document.myform.Mailid.value=="")
	{
		alert("Patient's Mailid is not Empty" + document.myform.Mailid.value );
		document.myform.Mailid.focus();
		return false;
	}
        	else if(document.myform.docpassword.value=="")
	{
		alert("Doctor password is not Empty");
		document.myform.docpassword.focus();
		return false;
	}
	else if(document.myform.contactno.value=="")
	{
		alert("Patient's Contact No is not Valid");
		document.myform.contactno.focus();
		return false;
	}         
	else
	{
        	return true;
	
        }
}

function SelectOption(s)
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
<form id="myform" name="myform" method="post" action="DoctorInput.jsp" onSubmit="return validate();" >
   <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
<br><br><br><br>
<font size="6" face="Times New Roman"><center><b>Doctor's Registration</b></center><br></font>
    <center>
        <table>
            <tr><td><b>Doctor Id</b></td><td><input type="text" name="DoctorId" class="text" onkeydown="numvalidate(this.value);"></td></tr>
            <tr><td><b>Doctor Name</b></td><td><input type="text" name="DoctorName" class="text"></td></tr>
            <tr><td><b>Qualification</b></td><td><input type="text" name="Qualification" class="text"></td></tr>
            <tr>
		    <th><div align="left">Gender:</div></th>
                     <td><input name="gen" type="radio" value="Male" />Male
			 <input name="gen" type="radio" value="Female" />Female<input type="hidden" name="gender" value="" /></td>
	    </tr>
            <tr><td><b>Specialized</b></td><td>
                    <select class="lists" name="Specialized"   >
                        <option>DEMENTIA</option>
                        <option>ECZEMA</option>
                        <option>INFLUENZA</option>
                        <option>LYMPHOMA</option>
                        <option>INSOMNIA</option>
                        <option>OBESITY</option>
                        <option>THRUS</option>
                        <option>OSTEOPOROSIS</option>
                    </select><input type="hidden" name="hidspl" value="" />
            </td></tr>
             <tr><td><b>ClinicAddress</b></td><td>
                 <textarea name="ClinicAddress" cols="15" rows="2" class="textarea"></textarea>                
             </td></tr>
        <tr><td><b>Available Time</b></td><td><input type="text" name="AvailableTime" class="text"></td></tr>
       <tr><td><b>Location</b></td><td><input type="text" name="Location" class="text"></td></tr>
       <tr><td><b>Contact No</b></td><td><input type="text" name="ContactNo" class="text" onkeydown="numvalidate(this.value);"></td></tr>
          <tr><td><b>Mail Id</b></td><td><input type="text" name="Mailid" class="text"></td></tr>
     <tr><td><b>doctor Password</b></td><td><input type="password" name="docpassword" class="text"></td></tr>
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
