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
<title>Insert title here</title>
</head>
<body>
<%
	String id = session.getAttribute("ID").toString();	

	if(request.getParameter("add") != null)
	{
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		String ctype = request.getParameter("ctype");
		String cnumber = request.getParameter("cnumber");
		String cname = request.getParameter("cname");
		String scode = request.getParameter("scode");
		String date = request.getParameter("date"); 
		int address = Integer.parseInt(request.getParameter("address"));
		data.addPayment(ctype, cnumber, cname, scode, date, address, id);
	}
	

%>
<h1> Add a credit card</h1>

	<h1> List of Payment options</h1>
	<table border = "1">
		<th><h2>Select Payment method</h2></th>
		<th><h2>Credit type</h2></th>
		<th><h2>Credit card number</h2></th>
		<th><h2>Security code</h2></th>
		<th><h2>Expiration date</h2></th>
		<% out.println(data.displayPayments(id)); %>
	</table>
	<form action='' method='POST'>
	
			Credit type:<br> <input type="text" name="ctype" /><br>
			Credit card number:<br> <input type="text" name="cnumber" /><br>
			Card name: <br>
			<input type="text" name="cname" /> <br>
			Security code:<br>
			<input type="text" name="scode" /><br>
			Expiration date: (ex: 01/01/2020)<br>
			<input type="text" name="date" /><br><br>
			<input type="submit" value="Add" name="add" />
	
	</form>
	
</body>
</html>




