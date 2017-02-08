<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%> 

<%@include file="settings.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="content" viewport="width=device-width, initial-scale=1.0"/> 
	
	<title>Home | E-TRAN</title>
	
	<link rel="stylesheet" href="CSS/base.css">
</head>
<body id="body">

	<%@include file="header.jsp" %>

	<%@include file="main.jsp" %>
	
	<%@include file="registerForm.html" %>	
	
	<%@include file="messenger.jsp" %>

	<script src="JS/jquery-1.12.1.min.js"></script>
	<script src="JS/js.js"></script>
	<script src="JS/behaviour.js"></script>
</body>
</html>