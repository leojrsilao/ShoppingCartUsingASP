<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.sql.PreparedStatement" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "Shopping.AnnonCookie" %>

<jsp:useBean id="data" class="Shopping.DBQuery" scope = "session" />

<%
	String displayItemDescription = "";
	String itemID = request.getParameter("itemID");
	
	Connection dbConnection = null;
	PreparedStatement statement = null;
	
	String selectTableSQL = "SELECT item_id, name, price, item_description, quantity from item where item_id=?";
	
	try {
		dbConnection = data.getDBConnection();
		//preparedStatement = dbConnection.prepareStatement(insertTableSQL);

		statement = dbConnection.prepareStatement(selectTableSQL);
		statement.setInt(1, Integer.parseInt(itemID));
	
		// execute select SQL stetement
		ResultSet rs = statement.executeQuery();
		
		int itemId = 0;
		String name;
		Double price;
		String item_desc;
		int quantity;
		while (rs.next())
		{
			itemId = (rs.getInt("item_id"));
			name = (rs.getString("name"));
			price = (rs.getDouble("price"));
			item_desc = (rs.getString("item_description"));
			quantity = (rs.getInt("quantity"));
			displayItemDescription += "<tr> <td>Item name: " + name + "</td> <br><td>Price: $" + price +"</td> <br>"
									  +"<td>Item description: " + item_desc + "</td><br> Item quantity: "+ quantity +" </tr>";
			out.println(displayItemDescription);

		}
		
		String input_quantity = request.getParameter("quantity");
		String addButton = request.getParameter("Add");
		
		if(addButton != null && input_quantity != null)
		{
			String usertype = "";
			String id = "";
			if(session.getAttribute("ID") != null){
				usertype = "user_id";
				id = session.getAttribute("ID").toString();
			}	
			else{
				usertype = "annon_id";
				AnnonCookie.checkUserCookie(request, response, session);
				id = session.getAttribute("annon_id").toString();
			}
			
			
			
			//int userid = Integer.parseInt(session.getAttribute("ID").toString());
			//boolean cart_Exist = data.selectCart(userid);
			
			boolean item_exist_in_cart = data.hasCartItem(id, Integer.parseInt(itemID), usertype);//data.selectItemId(itemId);
			int qt = Integer.parseInt(request.getParameter("quantity").toString());//Integer.parseInt(session.getAttribute("inputQ").toString());
			if(qt <= data.getItemQuantity(Integer.parseInt(itemID)))
			{
				if(item_exist_in_cart == true)
				{
					data.updateCart(qt, id, Integer.parseInt(itemID), usertype);
				}
				else
				{
					data.createCart(id,itemId,qt, usertype);
				}
				response.sendRedirect(request.getContextPath() + "/cart.jsp");
			}
			else{
				out.println("<br/>Error: quantity input exceeds quantity available in inventory");
			}
		}
		
		
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

<form action='' method='POST'>
	 <input type="number" name="quantity"  value="0"/><br>
	 <input type="submit" value="Add to cart" name="Add"/>
</form>

<a href="index.jsp">back</a>
