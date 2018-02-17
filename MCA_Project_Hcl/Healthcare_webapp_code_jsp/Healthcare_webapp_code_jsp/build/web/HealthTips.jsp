<%@ page import="java.sql.*"%>

<%
Connection con;
Statement stmt;
ResultSet rs;
%>
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
	
	if(document.myform.sno.value=="")
	{
		alert("Serial No is not Empty");
		document.myform.sno.focus();
		return false;
	}  
       else if(document.myform.Tip.value=="")
	{
		alert("Tip is not Empty");
		document.myform.Tip.focus();
		//break;
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
<form id="myform" name="myform" method="post" action="HealthTipsInput.jsp" onSubmit="return validate();" >
   <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
<br><br><br><br>
<font size="6" face="Times New Roman"><center><b>HealthTips Entry</b></center><br></font>
    <center>        
        <table>
            <tr><td><b>Serial No</b></td><td><input type="text" name="sno" class="text" onkeydown="numvalidate(this.value);"></td></tr>
            <tr><td><b>Health Tips</b></td><td><input type="text" name="Tip" class="text"></td></tr>
            <tr><td colspan="2"></td></tr>
            <tr><td colspan="2" align="center"><b><input type="submit" value="Submit" onFocus="validate();"></b>
            <font size="6"><a href="Login.jsp">Logout</a></font></td>
            <td></td></tr>
            <tr>
                <td colspan="=2"er align="cent">
                    <font size="6"> <a href="ViewHealthTip.jsp">View Health Tips</a></font>
                </td>
            </tr>
        </table>
    </center>
</body>
<center><div id="footer">
	<p>&copy; All Rights Reserved &bull;</p>
</div></center>

</html>
