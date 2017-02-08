
	<%@page import="com.asimadu.data.DataAccess"%>
<div id="bar">
		
	</div>

	<div id="banner">
		<div id="logo">
				
		</div>

		<div id="user-info">
			<div id="user-info-name">
				<span id="welcome-msg">
					<%
						if(authenticated.equalsIgnoreCase("true")){
							out.print("Welcome,");
							out.print("<span id='auth' style='display:none'>true</span>");
						} else {
							out.print("Hi,");
							out.print("<span id='auth' style='display:none'>false</span>");
						}
					%>
				</span> 
				<span id="nameSpan">
					<%
						if(authenticated.equalsIgnoreCase("true")){
							out.print(session.getAttribute("firstname"));
						} else {
							out.print("Guest");
						}
					%>
				</span>
			</div>
			<div id="user-info-picture">
				 <% 
				 	if(authenticated.equalsIgnoreCase("true")){
				 		String imageId = (String) session.getAttribute("imageId");
					 	out.print("<img id='dp' src='"+DataAccess.baseUrl+"GetImage?imageId="+imageId+"' alt=''>");
				 	} else {
				 		out.print("<img id='dp' src='images/photo.png' alt=''>");
				 	}
				 	
				 %>
			</div>
		</div>

		<%
			if(authenticated.equalsIgnoreCase("true")){
				out.print("<div id='banner-logout' style='display:block'> LOG OUT</div>");
				out.print("<div id='banner-login'style='display:none'> LOG IN</div>");
			} else {
				out.print("<div id='banner-logout' style='display:none'> LOG OUT</div>");
				out.print("<div id='banner-login'style='display:block'> LOG IN</div>");
			}
		%>

		<div id="login-form-background">
			<div id="login-form">
			<div id="close-login-form">
				<div class="bar1"></div>
	  			<div class="bar2"></div>
			</div>
				<div id="inner-login-form">
					<input id="login-username" type="text" name="" placeholder="username">
					<hr>
					<input id="login-password" type="password" name="" placeholder="password">
					
					<input id="keep-signedIn" type="checkbox"> <span id="remember-me">Remember me</span>
				</div>
				<div id="submit">
					<div id="login-submit">Log in</div>
				</div>
				<div id="forgot-password">Password forgotten? Click</div>
				
				<div id="create-account" >
					<span style="displa: none"> New here? <a href="#">Create account</a></span>
				</div>
			</div>

			<div id="password-forgotten-form">
				<div id="close-pff-form">
					<div class="bar1"></div>
		  			<div class="bar2"></div>
				</div>
				Provide email
				<br>
				<input id="pf-email" type="email" name="" autofocus>
				<br>
				<input id="pf-send" type="submit" value="RESET PASSWORD">
			</div>
		</div>
		
		<%
			if(authenticated.equalsIgnoreCase("true")){
				out.print("<div id='banner-profile' style='display:block'> ACCOUNT </div>");
				out.print("<div id='banner-signup'style='display:none'> SIGN UP</div>");
				
				if(isAdmin == 1){
					out.print("<a href='#admin'><div id='banner-admin' style='display:block;'> ADMIN </div></a>");					
				}
				
				if(transacting.equalsIgnoreCase("true")){
					out.print("<a href='#transact'><div id='banner-app'style='display:block; background-color:#28b463'> TRANSACT </div></a>");
					out.print("<span id='transact' style='display:none'>true</span>");
				} else {
					out.print("<a href='#transact'><div id='banner-app'style='display:block'> TRANSACT </div></a>");
					out.print("<span id='transact' style='display:none'>false</span>");
				}
					
			} else { 					
				out.print("<div id='banner-profile' style='display:none'> ACCOUNT </div>");
				out.print("<div id='banner-signup'style='display:block'> SIGN UP</div>");
				out.print("<a href='#admin'><div id='banner-admin' style='display:none;'> ADMIN </div></a>");
				out.print("<a href='#transact'><div id='banner-app'style='display:none'> TRANSACT </div></a>");
			}
		%>

		<div id="search-container">
			<input type="text" id="user-search-query">
			<div id="search-icon"></div>
		</div>
	</div>