<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Success!</title>
</head>
<body>
<h2><%= request.getAttribute("message").toString()%></h2>
<a href="FrontController">Main Menu</a>
</body>
</html>