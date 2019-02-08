<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<jsp:useBean id="data" class="Shopping.DBQuery" scope="session" />

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1 align="center">Accounts</h1>
	
	<table border="1">
			<tr align="center">
				<td>Name</td>
				<td>Info</td>
			</tr>
			<tr><%= data.listOfAccounts() %></tr>
	
	</table>
	<a href="adminIndex.jsp">Back</a>
	
</body>
</html>