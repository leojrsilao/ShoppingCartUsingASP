<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "Shopping.*" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.sql.PreparedStatement" %>
<%@ page import = "java.util.*" %>
<%@ page import = "Shopping.AnnonCookie" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:useBean id="data" class="Shopping.DBQuery" scope = "session" />

<title>Cart</title>
</head>
<body>
    <h1>Cart</h1>
    
</body>
</html>
<form action='' method='POST'>
<table border = "1">
<tr>
			<th><h4>Items</h4> </th>
			<th><h4>Price</h4> </th>
			<th><h4>Quantity</h4></th>
</tr>
<%
	String updateButton = request.getParameter("update");
	String input_quantity = request.getParameter("quantity");
	String itemID = request.getParameter("itemID");

	//
	String usertype;
	String id = "";
	if(updateButton != null)
	{
		if(input_quantity != null)	
		{
			int input_q = Integer.parseInt(input_quantity);
			data.updateCartQuantity(input_q);
		}
		
	}
	if(session.getAttribute("ID") != null){
		usertype = "user_id";
		id = session.getAttribute("ID").toString();
	}	
	else{
		usertype = "annon_id";
		AnnonCookie.checkUserCookie(request, response, session);
		id = session.getAttribute("annon_id").toString();
		System.out.println("annon_id : " + id);
	}
	
	if(request.getParameter("delete") != null){
		int delete = Integer.parseInt(request.getParameter("delete").toString());
		data.deleteCartItem(id, delete, usertype);
	}

	String list_of_items = request.getParameter("List");
	if(list_of_items != null)
	{
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	String checkout = request.getParameter("cart");
	if(checkout!= null)
	{
		response.sendRedirect(request.getContextPath() + "/checkout.jsp");
	}

		Connection dbConnection = null;
		PreparedStatement statement = null;
		String displayCart = "";

		String selectTableSQL = "SELECT item.name, item.price, cart.quantity, item_id from cart inner join item using (item_id) where " + usertype +"=?";
		
		try {
			
			dbConnection = data.getDBConnection();
			statement = dbConnection.prepareStatement(selectTableSQL);
			
			
			statement.setString(1, id);
			// execute select SQL stetement
			
			ResultSet rs = statement.executeQuery();
			//int inputQ = Integer.parseInt(session.getAttribute("inputQ").toString());
			double subtotal = 0;
			while (rs.next())
			{
				String itemname = (rs.getString("name"));
				String price = (rs.getString("price"));
				int quantity = rs.getInt("quantity");
				
				int item_id = rs.getInt("item_id");
				displayCart += "<tr> <td>" + itemname + "</td> <td>" + price +
						 	   "</td> <td> <input type='number' name='quantity'  value=" + quantity + ""+ "><br></td><td><button type='submit' name='delete' value='" + item_id +"'>Delete</button></td></tr>";	
				subtotal += quantity * Double.parseDouble(price);
				String cart_quantity = String.valueOf(quantity);
				session.setAttribute("cart_quantity", cart_quantity);
			}
			out.println(displayCart + "<tr><td>Sub total: "+ subtotal + "</td></tr><br>");

		} catch (SQLException e) {
		
			System.out.println(e.getMessage());
		
		} finally {
		
			if (statement != null) {
				statement.close();
			}
		
			if (dbConnection != null) {
				dbConnection.close();
			}
		
		}
%>	


</table>

<input type="submit" value="Proceed to checkout" name="cart" />
<!-- <input type="submit" value="Return to list of items" name="List" /> -->
<input type="submit" value="Update quantity of items" name="update" />
</form>
<a href="index.jsp">back</a>