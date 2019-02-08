<%@ page import = "Shopping.*" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.sql.Statement" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>

<jsp:useBean id="data" class="Shopping.DBQuery" scope = "session" />


<HTML> 
	<HEAD>
			<TITLE>Index</TITLE>
	</HEAD>
		<BODY>
			<%
				//if user is logged
				if(session.getAttribute("ID") != null){
			%>
		    		<a href="login.jsp?logout">Logout</a>
		    		<a href="userOrders.jsp">Orders</a>
		    <%
				}
				else{
			%>
					<a href="login.jsp">login</a>
			<%	
				}
		    %>
		    
			<a href="cart.jsp">Cart</a>
			<h1>Welcome <%-- ${sessionScope.username } --%></h1>
			
			
	
			 <h1> List of items</h1>
			 
			 <table border = "1" width='150px'>
			 	<th><h2>Item names</h2></th>
				<% out.println(data.getItemName()); %>
       		 </table>
			
		</BODY>
</HTML>