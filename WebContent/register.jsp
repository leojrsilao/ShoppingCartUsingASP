<%@ page import = "Shopping.*" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.sql.Statement" %>
<jsp:useBean id="data" class="Shopping.DBQuery" scope = "session" />
<jsp:useBean id="beans" class="Shopping.Person" scope = "session" />

<HTML> 
	<HEAD>
			<TITLE>Registration</TITLE>
	</HEAD>
		<BODY>
			<form action='' method='POST'>
			Username: 
			<input type="text" name="username" /> <br>
			Password:
			<input type="password" name="password" /><br>
			<input type="submit" value="Create account" /></form>
			<a href="login.jsp">Login</a>
			<%
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				
				if ((username == "")  && (password == "")) 
				{
					out.println("Please enter a username and password");
					
				}
				else if(username == "")
				{
					out.println("Please enter a username");
					
				}
				else if (password == "") 
				{
					out.println("Please enter a password");
				}
				else if((username != null) && (password != null))
				{
					data.insertRecordIntoTable(username, password);
					
					String id = data.get_user_id(username);
							
					if(AnnonCookie.getCookie("annon_id", request) != null){
						Cookie cookie = AnnonCookie.getCookie("annon_id", request);
						data.transferCart(cookie.getValue(), id);
					}
					
					session.setAttribute("ID", id);
					session.setAttribute("username", username);
					
					if(request.getParameter("checkout") == null)
						response.sendRedirect(request.getContextPath() + "/login.jsp");
					else
						response.sendRedirect(request.getContextPath() + "/checkout.jsp");
					
				}	
			%>
			
		</BODY>
</HTML>