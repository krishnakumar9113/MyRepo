
<%@ page import="java.sql.*"%>
<script language="javascript">
function validate()
{
var flag =0; 
	if(document.myform.Sno.value=="")
	{
		alert("Sno is not Empty");
		document.myform.Sno.focus();
		//break;
		return false;
	}
	else if(document.myform.PatientId.value=="")
	{
		alert("PatientId Name is not Empty");
		document.myform.PatientId.focus();
		return false;
	}
        else if(document.myform.Patientname.value=="")
	{
		alert("Patientname is not Empty");
		document.myform.Patientname.focus();
		return false;
	}      
        else if(document.myform.Patientcontact.value=="")
	{
		alert("Patient Contact is not Empty");
		document.myform.Patientcontact.focus();
		return false;
	}
        else if(document.myform.DoctorId.value=="")
	{
		alert("Doctor Id is not Empty");
		document.myform.DoctorId.focus();
		return false;
	}       
         else if(document.myform.DoctorName.value=="")
	{
		alert("Doctor Name is not Empty");
		document.myform.DoctorName.focus();
		return false;
	}
        else if(document.myform.DType.value=="")
	{
		alert("Doctor Type Age is not Empty");
		document.myform.DType.focus();
		return false;
	}
  	else if(document.myform.Prescription.value=="")
	{
		alert("Prescription is not Empty" );
		document.myform.Prescription.focus();
		return false;
	}
        	else if(document.Amount.mailID.value=="")
	{
		alert("Amount is not Empty");
		document.myform.Amount.focus();
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

<html>
    <head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Wireless Health Care System</title>

<link href="Css/style.css" rel="stylesheet" type="text/css" media="screen" />
</head>

<body background="images/BG0.jpg">
    <form id="myform" name="myform" method="post" action="TreatmentInput.jsp" onSubmit="return validate();" >
        <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
        <br><br><br><br>
        <font size="6" face="Times New Roman"><center><b>Pharmacy Details</b></center><br></font>
        <center>
                <table>    
                <tr><td><b>Serial No</b></td><td><input type="text" name="Sno" class="text"></td></tr>
                <tr><td><b>Patient Id</b></td><td><input type="text" name="PatientId" class="text"></td></tr>
                <tr><td><b>Patient Name</b></td><td><input type="text" name="Patientname" class="text"></td></tr>
                <tr><td><b>Patient contact</b></td><td><input type="text" name="Patientcontact" class="text"></td></tr>
                <tr><td><b>DoctorId</b></td><td><input type="text" name="DoctorId" class="text"></td></tr>
                <tr><td><b>DoctorName</b></td><td><input type="text" name="DoctorName" class="text"></td></tr>
                <tr><td><b>DType</b></td><td><input type="text" name="DType" class="text"></td></tr>                
                <tr><td valign="top"><b>Prescription</b></td><td>
                      <textarea name="Prescription" cols="15" rows="2" class="textarea"></textarea>                
                </td></tr>
               <tr><td><b>Amount</b></td><td><input type="text" name="Amount" class="text"></td></tr>                
             
                <tr><td colspan="2"></td></tr>
                <tr><td colspan="2" align="center"><b><input type="submit" value="Submit" onFocus="validate();"></b>
                    <font size="6"><a href="Login.jsp">Logout</a></font></td>
                <td></td></tr>          
            </table>
        </center>
    </form>
</body>
<center><div id="footer">
	<p>&copy; All Rights Reserved &bull;</p>
</div></center>
</html>
