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
 		if (session.getAttribute("username") != null && !data.isAdmin(session.getAttribute("username").toString()))
 		{
 			response.sendRedirect(request.getContextPath() + "/login.jsp");
 			return;
 		}


 		if (request.getParameter("item_id") == null || request.getParameter("item_id").equals("")) {
 			response.sendRedirect(request.getContextPath() + "/adminIndex.jsp");
 			return;
 		}

 		//delete
 		if (request.getParameter("delete") != null) {
 			int item_id = Integer.parseInt(request.getParameter("item_id")); //get the itemid after clicking delete
 			data.deleteItem(item_id);
 			response.sendRedirect(request.getContextPath() + "/adminIndex.jsp?delete=" + item_id);
 			return;
 		}

 		//update
 		if (request.getParameter("update") != null) {
 			Item updateItem = new Item();
 			updateItem.setName(request.getParameter("name"));
 			updateItem.setDescription(request.getParameter("description"));
 			updateItem.setPrice(Float.parseFloat(request.getParameter("price")));
 			updateItem.setQuantity(Integer.parseInt(request.getParameter("quantity")));

 			int item_id = Integer.parseInt(request.getParameter("item_id"));

 			data.updateItem(item_id, updateItem.getName(), updateItem.getPrice(),
 			updateItem.getDescription(), updateItem.getQuantity());
 		}

 		int item_id = Integer.parseInt(request.getParameter("item_id"));
 		System.out.println(item_id);
 		Item item = data.getItem(item_id);
 		//System.out.println(item);
 	%> 
	
	<form method="post">
		<table>
			<input type="hidden" name="item_id" value="<%= item_id %>"/>
			<tr>
				<td>Name : </td>
				<td><input type="text" name="name" value="<%= item.getName() %>"/></td>
			</tr>
			
			<tr>
				<td>Price : </td>
				<td><input type="text" name="price" value="<%= item.getPrice() %>"/></td>
			</tr>
			
			<tr>
				<td>Description : </td>
				<td><input type="text" name="description" value="<%= item.getDescription() %>"/></td>
			</tr>
			
			<tr>
				<td>Quantity : </td>
				<td><input type="number" name="quantity" value="<%= item.getQuantity() %>"/></td>
			</tr>
			
			<tr>
				<td><input type="submit" name="update" value="Update"/></td>
				<td><input type="submit" name="delete" value="Delete"/></td>
			</tr>
			<tr>
				<td><a href="adminIndex.jsp">Return to items</a></td>
			</tr>
		</table>
	</form>
</body>
</html>