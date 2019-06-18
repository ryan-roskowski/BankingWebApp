<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.ArrayList" %>
 <%@ page import="com.bank.beans.Transaction" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transaction List</title>
</head>
<body>
<h2>Transactions for <%= request.getAttribute("name") %>'s account <%= request.getAttribute("accountNumber") %></h2>
<table style="border-collapse: collapse; border-spacing: 5px; width: 600px;">
	<% 
	ArrayList<Transaction> transactionList = (ArrayList<Transaction>)request.getAttribute("transactionList");
for(int i = 0; i < transactionList.size(); i+=1) { %>
        <tr style="border: 1px solid black;">      
            <td style="text-align: center;"><%=transactionList.get(i).getDate()%></td>
            <td style="text-align: center;"><%=transactionList.get(i).getType()%></td>
            <td style="text-align: center;"><%=transactionList.get(i).getAmount()%></td>
        </tr>
    <% } %>

</table>
</body>
</html>