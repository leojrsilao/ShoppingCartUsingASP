<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="data" class="Shopping.DBQuery" scope = "session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	data.addOrder(session.getAttribute("ID").toString(), Integer.parseInt(request.getParameter("payment").toString()));
%>
<h1>Order is Complete! Returning to main page.</h1>
<script>
	setTimeout(function(){
		window.location.href = "index.jsp";
	}, 5000);
</script>
</body>
</html>