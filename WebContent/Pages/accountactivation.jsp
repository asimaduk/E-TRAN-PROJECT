<%@page import="com.asimadu.data.DataAccess"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Account activation | E-TRAN</title>
	
	<style>
		div {
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
		
		p {
			margin-bottom: 40px;
		}
		
		#sender, #homelink {
			margin-top: 50px;
			border: 2px solid #28b463;
			background-color: #34495e;
			padding: 12px 12px 10px;
			color: #fff;
			-webkit-border-radius: 5px;
			border-radius: 5px;
			cursor: pointer;
		}
		
		#homelink {
			text-decoration: none;
		}
		
		#code {
			margin-top: 20px;
		}
		
		#message-success {
			color: green;
		}
		
		#message-failure {
			color: red;
		}
	</style>
</head>
<body>
	<div>
		<% 
			out.print("<span id='domain' style='display:none'>"+DataAccess.baseUrl+"</span>");
		%>
		<p>
			<% if(request.getAttribute("show") != null){
				boolean show = (boolean) request.getAttribute("show");	
				String message = (String) request.getAttribute("message");
				
				if(show){
					out.print("<h4>E-TRAN</h4><span id='message-success'></span>");
					out.print("<span id='label'>Provide activation code:</span> <input id='code' type='text' />"
							+"<br><button id='sender' onclick='send()'>Activate</button>"
							+"<a id='homelink' href='"+DataAccess.baseUrl+"' style='display: none'>Visit HomePage</a>"
							);
				}
				else
					out.print("<span id='message-failure'>"+message+"</span>");
			} else 
				out.print("<h4>E-TRAN</h4>Ooops, an error occured. Please try a little while or contact webmaster");
			%> 
		</p>
		
	</div>
	<script>
		function send(){
			var code = document.getElementById("code").value;
			var domain = document.getElementById("domain").innerHTML;
			if((code.length == 4)){
				var xhttp;
			  	xhttp = new XMLHttpRequest();
			  	xhttp.onreadystatechange = function(xhr) {
				    if (this.readyState == 4 && this.status == 200) { 
				    	document.getElementById("label").style.display = "none";
				    	document.getElementById("code").style.display = "none";
				    	document.getElementById("sender").style.display = "none";
				    	document.getElementById("message-success").innerHTML = this.responseText;
				    	document.getElementById("homelink").style.display = "block";
				    }
				    
				    else if (this.readyState == 4 && this.status == 201) { 
				    	alert(this.responseText);
				    }
				};
				xhttp.open("POST", document.getElementById("domain").innerHTML+"Activate?code="+code, true);
				xhttp.send();
			}
		}
	</script>
</body>
</html>