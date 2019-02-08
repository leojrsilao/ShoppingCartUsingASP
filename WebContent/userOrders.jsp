<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="data" class="Shopping.DBQuery" scope = "session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<table border="1">
	<tr align="center">
		<td>Order#</td>
		<td>Info</td>
	</tr> 
	
	<%= data.listOfOrders(session.getAttribute("ID").toString()) %>
</table>

<a href="index.jsp">back</a>
</body>
</html>