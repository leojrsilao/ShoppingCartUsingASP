<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="Shopping.Person" %>
<jsp:useBean id="data" class="Shopping.DBQuery" scope="session" />    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
	<%
		if(session.getAttribute("username") == null)
			response.sendRedirect(request.getContextPath() + "/login.jsp");
	
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		Person info = data.getPerson(user_id);
	%>
	
	
	<form method="post">
		<table>
			<input type="hidden" name="user_id" value="<%= user_id %>"/>
			<%
				if(info != null){
			%>
					<tr>
						<td>First Name : </td>
						<td><%= info.getFirstName()  %></td>
					</tr>
					
					<tr>
						<td>Last Name : </td>
						<td><%= info.getLastName() %></td>
					</tr>
					
					<tr>
						<td>Postal Code : </td>
						<td><%= info.getPostcode() %></td>
					</tr>
					
					<tr>
						<td>Province : </td>
						<td><%= info.getProvince() %></td>
					</tr>
					
					<tr>
						<td>Street : </td>
						<td><%= info.getStreet() %></td>
					</tr>
			<%
				}
				else{
			%>
					<h2>User currently has not information</h2>
			
			
			<%
				}
			%>
			<tr>
				<td><input type="submit" name="update" value="Return"/></td>
			</tr>
			<tr>
				<td><a href="adminIndex.jsp">Return to items</a></td>
			</tr>
		</table>
	</form>
</body>
</html>