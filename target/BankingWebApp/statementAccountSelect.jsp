<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.ArrayList" %>
 <%@ page import="com.bank.beans.Account" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Select Account</title>
</head>
<body>
<h2>Please select an account below.</h2>
<form action="FrontController" method="post">
<% 
	ArrayList<Account> accountList = (ArrayList<Account>)request.getAttribute("accountList");
for(int i = 0; i < accountList.size(); i+=1) { 
		if(i == 0){%>
            <input type="radio" name="account" value="<%=Integer.toString(i)%>" checked="checked"> <%= accountList.get(i).getType() %>
     <% } 
     else {%>
           <input type="radio" name="account" value="<%=Integer.toString(i)%>" > <%= accountList.get(i).getType() %>
         <% }%>
    <br>
    <% } %>
    
    <input type="hidden" name="purpose" value="viewStatement">
    <br>
    <input type="submit" value="submit">

</form>
</body>
</html>