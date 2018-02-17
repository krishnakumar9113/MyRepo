<%@ page import="java.sql.*"%>
<script language="javascript">
function validate()
{
var flag =0;
	if(document.myform.PatientId.value=="")
	{
		alert("Patient Id is not Empty");
		document.myform.PatientId.focus();
		return false;
	}
	else if(document.myform.Patientname.value=="")
	{
		alert("Patient Name is not Empty");
		document.myform.Patientname.focus();
		return false;
	}                                
	else if(document.myform.Patientcontact.value=="")
	{
		alert("Patient contact is not Empty");
		document.myform.Patientcontact.focus();
		return false;
	}                                

        else if(document.myform.DoctorId.value=="")
	{
		alert("Doctor Id  is not Empty");
		document.myform.DoctorId.focus();
		return false;
	}
        else if(document.DoctorName.Specialized.value=="")
	{
		alert("Specialized is not Empty");
		document.myform.Specialized.focus();
		return false;
	}
        else if(document.myform.Doclocation.value=="")
	{
		alert("Doctor Location is not Empty");
		document.myform.Doclocation.focus();
		return false;
	}
         else if(document.myform.Doccontactno.value=="")
	{
		alert("Doctor contactno Time is not Empty");
		document.myform.Doccontactno.focus();
		return false;
	}
        else if(document.myform.Visitstatus.value=="")
	{
		alert("Visit status is not Empty");
		document.myform.Visitstatus.focus();
		return false;
	}
        
	else if(document.myform.Appointmentregistered.value=="")
	{
		alert("Appointmentregistered is not Empty" );
		document.myform.Appointmentregistered.focus();
		return false;
	}
        	else if(document.myform.AppDate.value=="")
	{
		alert("Appointment Date is not Empty");
		document.myform.AppDate.focus();
		return false;
	}
                          
	else if(document.myform.appointmenttiming.value=="")
	{
		alert("Appointment Timing No is not Valid");
		document.myform.appointmenttiming.focus();
		return false;
	}
        else if(document.myform.Paymentmode.value=="")
	{
		alert("Paymentmode Timing No is not Valid");
		document.myform.Paymentmode.focus();
		return false;
	}
        else if(document.myform.Accountno.value=="")
	{
		alert("Accountno Timing No is not Valid");
		document.myform.Accountno.focus();
		return false;
	}                  
	else if(document.myform.Servicecharge.value=="")
	{
		alert("Servicecharge Timing No is not Valid");
		document.myform.Servicecharge.focus();
		return false;
	}
        else if(document.myform.Amountstatus.value=="")
	{
		alert("Amountstatus Timing No is not Valid");
		document.myform.Amountstatus.focus();
		return false;
	}
        else
	{
        alert("Record Saved");
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
<%
Connection con;
Statement stmt;
ResultSet rs;         
%>
<body background="images/BG0.jpg">
    <form id="myform" name="myform" method="post" action="AppointmentInput.jsp" onSubmit="return validate();" >
        <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
        <br><br><br><br>
        <font size="6" face="Times New Roman"><center><b>Appointment Details</b></center><br></font>
   <%
String strresult = "";
strresult= "Welcome !!! "+ session.getAttribute("sessionId") ;%>
    <font size=6><%
        out.println(strresult);
        %>
    </font>
        <center>
                <table> 
                    <tr>
                        <td>
                            <table>

                                            <%  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                stmt=con.createStatement();
                
                String strDName = "";
                strDName=session.getAttribute("sessionId").toString();             
                rs=stmt.executeQuery("select PatientId,Patientname,Patientcontactno,Status from Pattable where Patientname='"+ strDName +"'");
	        while(rs.next())
		{
                   %>                    
                                    <tr><td><b>Patient Id</b></td><td><input type="text"  name="PatientId" value="<%=rs.getString("Patientid")%>" class="text"></td></tr>
                              
                                    <tr><td><b>Patient Name</b></td><td><input type="text" name="Patientname" value="<%=rs.getString("Patientname")%>"  class="text"></td></tr>
                                    <tr><td><b>Patient contact</b></td><td><input type="text" name="Patientcontact" value="<%=rs.getString("Patientcontactno")%>"  class="text"></td></tr>
                                   <tr><td><b>Visit Status</b></td><td><input type="text" name="Visitstatus" value="<%=rs.getString("Status")%>" class="text"></td></tr>
                                   
                                    <%
                } %>
                                    <tr bgcolor="yellow"><td><b>Enter and Select Doctor Id</b></td><td><input type="text" name="DoctorId1" 
                                    onselect="location.href='Appointment.jsp?'+this.value;" class="text"></td></tr>
                       <%
                              try    
   	{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                stmt=con.createStatement();
             
                int DocId=Integer.parseInt(request.getQueryString());
                rs=stmt.executeQuery("select DoctorId,DoctorName,Location,ContactNo from Doctab where  DoctorId='"+ DocId + "'");
                
                while(rs.next())
                {
                %>
                                         <tr><td><b>Doctor Id</b></td><td><input type="text" name="DoctorId" 
                                     value="<%=rs.getString("DoctorId")%>" class="text"></td></tr>
                                        <tr><td><b>Doctor Name</b></td><td><input type="text" name="DoctorName"
                                          value="<%=rs.getString("DoctorName")%>" class="text"></td></tr>
                                    <tr><td><b>Doctor Location</b></td><td><input type="text" name="Doclocation" 
                                     value="<%=rs.getString("Location")%>" class="text"></td></tr>
                                    <tr><td><b>Contact No</b></td><td><input type="text" name="Doccontactno" 
                                     value="<%=rs.getString("ContactNo")%>" class="text"></td></tr>
                                 
                                <%
                }	
                 }

         catch(Exception e)
		{
		out.println(e); 
               
                 }
                %> 
                                    <tr><td><b>Appointment Registered</b></td><td><input type="text" name="Appointmentregistered" class="text"></td></tr>
                                    <tr><td><b>Appointment Date</b></td><td><input type="text" name="AppDate" class="text"></td></tr>
                                    <tr><td><b>Appointment Timing</b></td><td><input type="text" name="appointmenttiming" class="text"></td></tr>

                            </table>
                        </td>
                        <td valign="top">
                            <table>
                                    <tr><td><b>Payment Mode</b></td><td><input type="text" name="Paymentmode" class="text"></td></tr>
                                    <tr><td><b>Account No</b></td><td><input type="text" name="Accountno" class="text"></td></tr>
                                    <tr><td><b>Service Charge</b></td><td><input type="text" name="Servicecharge" class="text"></td></tr>
                                    <tr><td><b>Amount Status</b></td><td><input type="text" name="Amountstatus" class="text"></td></tr>
                            </table>
                        </td>                        
                    </tr>
                    <tr><td colspan="2"></td></tr>
                <tr><td  align="center"><b><input type="submit" value="Submit" onFocus="validate();"></b>
                    <font size="6"><a href="Login.jsp">Logout</a></font></td>
                    <td><font size="6"><a href="PatientHome.jsp">Patient Home</a></font></td>
                <td></td></tr>
                </table>
              </center>
    </form>
</body>
<center><div id="footer">
	<p>&copy; All Rights Reserved &bull;</p>
</div></center>
</html>
