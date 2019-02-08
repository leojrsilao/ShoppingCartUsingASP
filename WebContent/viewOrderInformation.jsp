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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
		if(session.getAttribute("username") == null)
			response.sendRedirect(request.getContextPath() + "/login.jsp");
	
		int order_id = Integer.parseInt(request.getParameter("order_id"));
		
		Connection dbConnection = null;
		PreparedStatement statement = null;
		
		String selectTableSQL = "SELECT item.name, order_detail.sales_price, order_detail.quantity from order_detail INNER JOIN item USING (item_id) where order_id=?";
		
		String output = "";
		double subtotal = 0;
		try {
			dbConnection = data.getDBConnection();
			//preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			statement = dbConnection.prepareStatement(selectTableSQL);
			statement.setInt(1, order_id);
		
			
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				String name = rs.getString("name");
				double sales_price = rs.getDouble("sales_price");
				int quantity = rs.getInt("quantity");
				output += "<tr><td> "+ name +" </td> <td> " + sales_price +" </td> <td> " + quantity + " </td></tr>";
				subtotal += quantity * sales_price;
			}
			output += "<tr><td>Subtotal: "+subtotal+"$</td></tr>";
			
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
	<h1>Order# <%= order_id %></h1>
	<form method="post">
		<table border="1">
			<tr>
				<th>Item name</th>
				<th>Price</th>
				<th>Quantity</th>
			</tr>
			<%= output %>
			<tr>
				<td>
				<%
					if(data.isAdmin(session.getAttribute("username").toString()))
					{
						System.out.println("username : "  + session.getAttribute("username"));
				%>
						<a href="adminIndex.jsp">Back</a>
					
				<%
					}
					else
					{
				%>
						<a href="userOrders.jsp">Back</a>
				<%		
					}
				%>
	</td>
			</tr>
		</table>
	</form>
</body>
</html>