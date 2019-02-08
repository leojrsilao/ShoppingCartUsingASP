<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "Shopping.*" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.sql.Statement" %>
<%@ page import = "java.util.ArrayList" %>
<jsp:useBean id="data" class="Shopping.DBQuery" scope = "session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Checkout</title>
</head>
<body>
	<%
		if(session.getAttribute("ID") == null){
			response.sendRedirect(request.getContextPath() + "/register.jsp?checkout");
			return;
		}
	
		String id = session.getAttribute("ID").toString();
	%>
	<h1> List of addresses</h1>
	<table border = "1">
	<th><h2>Select address</h2></th>
	<th><h2>City</h2></th>
	<th><h2>Postal Code</h2></th>
	<th><h2>Province</h2></th>
	<th><h2>Street</h2></th>
	<th><h2>Country</h2></th>
	<% out.println(data.displayAddress(id)); %>
	</table>
	<h1> Add a new address</h1>
	<form action='' method='POST'>
	
			First name:<br> <input type="text" name="fname" /><br>
			Last name:<br> <input type="text" name="lname" /><br>
			City: <br>
			<input type="text" name="city" /> <br>
			Postal code:<br>
			<input type="text" name="pcode" /><br>
			Province: <br>
			<input type="text" name="province" /> <br>
			Street address:<br>
			<input type="text" name="saddress" /><br>
			Country:<br>
			<input type="text" name="country" /><br><br>
			<input type="submit" value="add" name="ship" />
	
	</form>
	<a href="index.jsp">back</a>
</body>
</html>

<%
	String shipButton = request.getParameter("ship");
	String fname = request.getParameter("fname");
	String lname = request.getParameter("lname");
	String city = request.getParameter("city");
	String pcode = request.getParameter("pcode");
	String province = request.getParameter("province");
	String saddress = request.getParameter("saddress");
	String country = request.getParameter("country");
	int userid = Integer.parseInt(session.getAttribute("ID").toString());

	if(shipButton != null)
	{
		
		data.insertIntoAddress(fname,lname,city,pcode,province,saddress,country,userid);
		response.sendRedirect(request.getContextPath() + "/checkout.jsp");
	}
	
%>
