<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.web.enums.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Banking Application</title>
</head>
<body>
<h2>Login</h2>
<form action="FrontController" method="post">
	<p>Username: <input type="text" name="username" style="margin-left: 5px;"></p>
	<p>Password: <input type="text" name="password" style="margin-left: 5px;"></p>
	<input type="hidden" name="purpose" value="login">
	<p><input type="submit">
</form>
</body>
</html>