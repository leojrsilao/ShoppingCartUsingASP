<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="Shopping.Item" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:useBean id="data" class="Shopping.DBQuery" scope = "session" />

<title>Insert title here</title>
</head>
<body>

	<% 
		if(request.getParameter("addItem") != null)
		{
			//Item addItem = new Item();		
		}
	
		if(session.getAttribute("username") == null){
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		if (request.getParameter("addItem") != null) {
			Item addItem = new Item();
			addItem.setName(request.getParameter("name"));
			addItem.setPrice(Float.parseFloat(request.getParameter("price")));
			addItem.setDescription(request.getParameter("description"));
			addItem.setQuantity(Integer.parseInt(request.getParameter("quantity")));

		
			data.addItem(addItem.getName(),addItem.getPrice(),addItem.getDescription(),addItem.getQuantity());
		}
	%>
	<h1>Add Items</h1>
	<form method="post">
	<table>
		<tr>
			<td>Name:	</td>
			<td><input type="text" name="name"></input></td>
		</tr>
		
		<tr>
			<td>Price:	</td>
			<td><input type="text" name="price"></input></td>
		</tr>	
		
		<tr>
			<td>Description:	</td>
			<td><input type="text" name="description"></input></td>
		</tr>
		<tr>
			<td>Quantity:	</td>
			<td><input type="text" name="quantity"></input></td>
		</tr>	
	
		<tr>
			<td><input type="submit" value="add" name="addItem"></input></td>
		</tr>
	</table>
	<a href="adminIndex.jsp">back</a>
	</form>
</body>
</html>