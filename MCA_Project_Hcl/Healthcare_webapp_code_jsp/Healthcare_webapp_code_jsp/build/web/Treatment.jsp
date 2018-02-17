
<%@ page import="java.sql.*"%>

<script language="javascript">      
function validate()
{
var flag =0;
	if(document.myform.DoctorName.value=="")
	{
		alert("Doctor Name is not Empty");
		document.myform.DoctorName.focus();
		return false;
	}
	else if(document.myform.Qualification.value=="")
	{
		alert("Qualification is not Empty");
		document.myform.Qualification.focus();
		return false;
	}              
        else if(document.myform.PatientId.value=="")
	{
		alert("Patient Id is not Empty");
		document.myform.PatientId.focus();
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

function hide()
{
document.getElementById("PatientId").style.visibility="hidden"; 
}



</script>
   
<html>
    <head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Wireless Health Care System</title>

<link href="Css/style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<%Connection con;
Statement stmt;
ResultSet rs;  
%>
<body background="images/BG0.jpg">
    <form id="myform" name="myform" method="post" action="TreatmentInput.jsp" onSubmit="return validate();" >
        <marquee width="100%" behavior="alternate"> <font size="10" face="Times New Roman"><b>Wireless Health Care System</b></font></marquee>
        <br><br><br><br>
        <font size="6" face="Times New Roman"><center><b>Treatment Details</b></center><br></font>
        </font>
       <%
String strresult = "";
strresult= "Welcome, "+ session.getAttribute("sessionId") ;%>
        <font size="6"><%
            out.println(strresult);
            %>
        </font>
        <center>
                 <table border="5" >
                     <tr  ><td><b>Enter Patient Id and Select </b></td>
                
                 <td><input type="text" name="PatientId1" class="text"   onselect="location.href='Treatment.jsp?'+this.value;" 
                 onblur="request.getQueryString();"  ></td></tr>
                     <tr><td></td></tr>
                 </table>            
            
            <table>
                <tr><td><b>Doctor Id</b></td><td>
                <select class="lists" name="DoctorId"  >
<%
       
	              try
                      {	                                
                      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		      con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                      stmt=con.createStatement();
                      String strDName = "";
                strDName=session.getAttribute("sessionId").toString();             
                
                    rs=stmt.executeQuery("select DoctorId from Doctab where DoctorName='"+ strDName +"'"); 
                   
	          
	              while(rs.next())
			{ 
			%>
			                <option >
                                        <%=rs.getString("DoctorId")%>
                                        </option>
                        <%				
			}//while
		}//try
		catch(Exception e)
		{
			e.printStackTrace();
		}
                %>
</select>
                </td></tr>
                  
             <%  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                stmt=con.createStatement();
                
                String strDName = "";
                strDName=session.getAttribute("sessionId").toString();             
                rs=stmt.executeQuery("select DoctorName,Qualification,Specialized from Doctab where DoctorName='"+ strDName +"'");
	        while(rs.next())
		{
                   %>
                       <tr><td><b>Doctor Name</b></td><td><input type="text" name="DoctorName" value="<%=rs.getString("DoctorName").toString()%>" class="text"></td></tr>
           
                   <tr>
                   <td><b>Qualification</b></td>
                  <td> <input type="text" name="Qualification" value="<%=rs.getString("Qualification").toString()%>" class="text"></td>
                  </tr>
 <tr>
                   <td><b>Specialized</b></td>
                  <td> <input type="text" name="Specialized" value="<%=rs.getString("Specialized").toString()%>" class="text"></td>
                  </tr>
                  <%
                }
                
                try    
   	{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con=DriverManager.getConnection("jdbc:odbc:HC","sa","star");
                stmt=con.createStatement();
             
                int PatId=Integer.parseInt(request.getQueryString());
                rs=stmt.executeQuery("select Patientid,Patientname,Prescription from Pattable where Status='Old' and PatientId='"+ PatId + "'");
                
                while(rs.next())
                {
                %>
                  <tr><td><b>Patient Id</b></td>
                
                 <td><input type="text" name="PatientId" class="text" value="<%=rs.getString("Patientid")%>"    ></td></tr>

                   <tr><td><b>Patient Name</b></td><td><input type="text" name="PatientName"  class="text" value="<%=rs.getString("Patientname")%>"  ></td></tr>
                 <tr><td valign="top"><b>Prescription</b></td><td>
                <textarea name="Prescription" cols="15" rows="2" class="textarea" >
                    <%=rs.getString("Prescription")%>
                    
                    </textarea>                
                </td></tr>
             
                <%
                }	
                 }

         catch(Exception e)
		{
		out.println(e); 
               
                 }
                %> 
                 
                <tr><td><b>Disease Type</b></td><td><input type="text" name="Dtype" class="text"  ></td></tr>
                <tr><td colspan="2"></td></tr>
                <tr><td colspan="2" align="center"><b><input type="submit" value="Submit" onFocus="validate();"></b>
                    <font size="6"><a href="Login.jsp">Logout</a></font></td>
                <td></td></tr>
                <tr>
                    <td colspan="2">
                        <table cellspacing="25">
                            <tr>
                                <td>
                                      <font size="6">
                        <a href="ViewHospital.jsp">Hospital List</a></font>
                                </td>
                                <td>
                                     <font size="6">
                        <a href="Pharmacy.jsp">Pharmacy</a></font>
                                </td>
                                <td>
                                   <font size="6">     <a href="Feedback.jsp">Feedback</a></font>
                       
                                </td>
                            </tr>
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
