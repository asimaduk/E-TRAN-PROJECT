<%@page import="com.asimadu.data.DataAccess"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="content" viewport="width=device-width, initial-scale=1.0"/> 

	<title>Password Recovery | E-TRAN</title>
	
	<style>
		h4 {
			margin-top: 0;
		}
		
		#container {
			width: 100%;
			max-width: 300px;
			height: auto;
			margin: 30px auto;
			background-color: #f8f8f8;
			border: 2px solid #34495e;
			padding: 20px;
			text-align: center;
			-webkit-border-radius: 5px;
			border-radius: 5px;
		}
		
		input {
			float: right;
		}
		
		#send, #homelink {
			margin-top: 20px;
			border: 2px solid #28b463;
			background-color: #34495e;
			padding: 10px 12px 10px;
			color: #fff;
			-webkit-border-radius: 5px;
			border-radius: 5px;
			cursor: pointer;
			font-size: 90%;
		}
		
		#homelink {
			text-decoration: none;
		}
		
		#message {
			color: green;
		}
	</style>
</head>
<body>
	<% 
		out.print("<span id='domain' style='display:none'>"+DataAccess.baseUrl+"</span>");
	%>

	<div id="container">
		<h4>E-TRAN</h4>
		<span id='message'></span>
		<p>Provide reset code: <input type="text" name="recovery-code" id="recovery-code"> </p>
		<p>New password: <input type="text" name="new-password" id="new-password"> </p>
		<p>Confirm password: <input type="text" name="confirm-password" id="confirm-password"> </p>
		<button id="send">Reset</button>
		
		<% out.print("<br><br><a id='homelink' href='"+DataAccess.baseUrl+"' style='display: none'>Visit HomePage</a>"); %>
	</div>

	<script src="../JS/jquery-1.12.1.min.js"></script>	
	<script>
		$(document).ready(function() {
			$("#send").click(function(){
				var code = $("#recovery-code").val();
				var pswd = $("#new-password").val();
				var c_pswd = $("#confirm-password").val();
				
				var count = 4;
				
				for(var i=0; i<4; i++){
					if(code[i] == " "){
						count--;
					}
				}
				
				if(count==4 && code.length==4){
					var xhttp;
					xhttp = new XMLHttpRequest();
					xhttp.onreadystatechange = function(xhr) {
				  	    if (this.readyState == 4 && this.status == 200) {
				  	    	$("p,input,button").css("display","none");
				  	    	$("#message").html(this.responseText);
				  	    	$("#homelink").css("display","block");
				  	    } else if (this.readyState == 4 && this.status == 201) {
				  	    	alert(this.responseText);
				  	    } else {
				  	    	alert("Error occured... please try a little while");
				  	    }
			  	  	};
			  	    xhttp.open("POST",$("#domain").html()+"RecoverPassword?code="+code+"&password="+pswd+"&c_password="+c_pswd, true);
			  	    xhttp.send();
				}
			});
		});
	</script>
	
</body>
</html>