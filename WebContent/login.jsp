<%@ page import="Shopping.*"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Statement"%>


<jsp:useBean id="data" class="Shopping.DBQuery" scope="session" />


<jsp:useBean id="beans" class="Shopping.Person" scope="session" />
<jsp:setProperty property="*" name="beans" />

<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>
<BODY>

	<%
		//logout user
		if (request.getParameter("logout") != null){
				session.removeAttribute("ID");
				session.removeAttribute("username");
		}
		if (session.getAttribute("ID") != null)
			response.sendRedirect(request.getContextPath() + "/index.jsp");
	%>

	<form action='' method='POST'>
		Username: <input type="text" name="username" /> <br> Password: <input
			type="password" name="password" /><br> <a href="register.jsp">Register
			now!</a><br> <input type="submit" value="Login" />
	</form>
	<a href="index.jsp">View items</a>
	<%
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String userpwAdmin = "admin";
		String id = "";

		if ((username == "") && (password == "")) {
			out.println("Please enter a username and password");

		} else if (username == "") {
			out.println("Please enter a username");

		} else if (password == "") {
			out.println("Please enter a password");
		} else if (username != null && password != null) {

			if (data.exist(username, password)) {
				session.setAttribute("username", username);
				if (data.isAdmin(username))
					response.sendRedirect(request.getContextPath() + "/adminIndex.jsp");
				else{
					System.out.print("getting the user id: "+data.get_user_id(username));
				
					id = data.get_user_id(username);
					System.out.println("user id is : " + id);
					
					if(AnnonCookie.getCookie("annon_id", request) != null){
						Cookie cookie = AnnonCookie.getCookie("annon_id", request);
						//System.out.println("cookie: " + cookie.getName());
						data.transferCart(cookie.getValue(), id);
						
						//Delete Cookie
						//cookie = new Cookie("annon_id","");
						//cookie.setMaxAge(0);
						//response.addCookie(cookie);
						
					}
					session.setAttribute("ID", id);
					
					System.out.println("getting the session: "+session.getAttribute("username"));
					
					
					
					//out.print("in jsp : " +id);
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
			} else {
				out.println("Username and password doesn't exist");
			}
		}
			
		
	%>

</BODY>
</HTML>