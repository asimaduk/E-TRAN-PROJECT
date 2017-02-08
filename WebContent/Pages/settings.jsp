<%@page import="com.asimadu.data.UserDetails"%>
<%@page import="com.asimadu.data.DataAccess"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% 
	UserDetails user = (UserDetails) request.getSession().getAttribute("userDetails");

	String supportedBanks = DataAccess.supportedBanks; 
	String[] banks = supportedBanks.split(":");

	String authenticated = (null == session.getAttribute("auth")) ? "false" : (String) session.getAttribute("auth"); 
	String transacting = (null == session.getAttribute("transacting")) ? "false" : (String) session.getAttribute("transacting");  
	int isAdmin = (null == session.getAttribute("isAdmin")) ? 0 : (int) session.getAttribute("isAdmin"); 	
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 	
</head>
<body>
	<% 
		out.print("<span id='domain' style='display:none'>"+DataAccess.baseUrl+"</span>");
		out.print("<span id='supportedBanks' style='display:none'>"+DataAccess.supportedBanks+"</span>");
	%>
</body>
</html>