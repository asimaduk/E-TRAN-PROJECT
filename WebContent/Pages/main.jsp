<%@page import="com.asimadu.data.DataAccess"%>
<%@page import="com.asimadu.data.UserDetails"%>

	<%
		if(transacting.equalsIgnoreCase("true")){
			out.print("<section id='wrapper' style='display:none'>");
		} else {
			out.print("<section id='wrapper' style='display:block'>");
		}
	%>
	
		<div id="full-screen-video-container">
			<video id="full-screen-video" poster="images/poster.png" loop preload="none" muted>
				<source src="VIDEO/4.mp4" type="video/mp4">
			</video>
	
			<div id="full-video-close-button">
				<div class="bar1"></div>
	 			<div class="bar2"></div>
			</div>	
		</div>
	
		<div id="top-video-container">
	
			<video id="background-video" poster="images/poster.png" autoplay loop muted>
				<source src="VIDE/4.mp4" type="video/mp4">
			</video>
	
			<div id="top-video-play-button">
				<div class="tvpb-bar1"></div>
	 				<div class="tvpb-bar2"></div>
	 				<div class="tvpb-bar3"></div>
			</div>
	
			<div id="video-container-cover">
				
			</div>
	
			<div id="search-bar-on-video-background">
				
			</div>
	
			<div id="search-bar-on-video">
				<div id="item1">
					
				</div>
	
				<div id="item2">
					
				</div>
	
				<div id="item3">
					
				</div>
	
				<div id="item4">
					
				</div>
			</div>
		</div>
	
		<div id="middle-section">
			<div id="welcome-message-container">
				<div id="welcome-message">
					<div id="welcome-images">
						<img id="welcome-images-img1" src="images/cashless1.png">
						<img id="welcome-images-img2" src="images/cashless2.jpeg">
						<img id="welcome-images-img3" src="images/cashless3.jpeg">
					</div>
		
					<div id="welcome-text">
						<div id="welcome-text-text"></div>
						<div id="welcome-text-button"></div>
					</div>
				</div>
			</div>
			
			<div id="contributors">
				<div id="contributors-item1">
					<div class="cont-desc"></div>
					<img class="explore-image" src="image/bg.jpeg" alt="">
				</div>
	
				<div id="contributors-item2">
					<div class="cont-desc"></div>
					<img class="explore-image" src="image/bg.jpeg" alt="">  
				</div>
	
				<div id="contributors-item3">
					<div class="cont-desc"></div>
					<img class="explore-image" src="image/bg.jpeg" alt="">
				</div>
	
				<div id="contributors-item4">
					<div class="cont-desc"></div>
					<img class="explore-image" src="image/bg.jpeg" alt="">
				</div>
			</div>
			
			<div id="vid-container">
				<div id="vid-container-half1">
					<div class="video-chamber">
						<div class="video-container">
							<iframe src="https://www.youtube.com/embed/itMdJK_ZWnU?rel=0" allowfullscreen style="border:none;"></iframe>
						</div>
					</div>

					<div class="video-chamber">
						<div class="video-container">
							<iframe src="https://www.youtube.com/embed/itMdJK_ZWnU?rel=0" allowfullscreen style="border:none;"></iframe>
						</div>
					</div>

					<div class="video-chamber">
						<div class="video-container">
							<iframe src="https://www.youtube.com/embed/itMdJK_ZWnU?rel=0" allowfullscreen style="border:none;"></iframe>
						</div>
					</div>
				</div>

				<div id="vid-container-half2">
					<div class="video-chamber">
						<div class="video-container">
							<iframe src="https://www.youtube.com/embed/itMdJK_ZWnU?rel=0" allowfullscreen style="border:none;"></iframe>
						</div>
					</div>

					<div class="video-chamber">
						<div class="video-container">
							<iframe src="https://www.youtube.com/embed/itMdJK_ZWnU?rel=0" allowfullscreen style="border:none;"></iframe>
						</div>
					</div>

					<div class="video-chamber">
						<div class="video-container">
							<iframe src="https://www.youtube.com/embed/itMdJK_ZWnU?rel=0" allowfullscreen style="border:none;"></iframe>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		
		<div id="main-footer" class="main-footer-background">
			<div id="main-footer-background-cover"></div>
	
			<div id="main-footer-text">
				
			</div>
	
			<hr id="main-footer-hr-line">
	
			<p id="main-footer-text-p">Join Us On</p>
	
			<div id="main-footer-social-box">
				<div class="mf-social" id="mf-facebook">
					<span>f</span>
				</div>
	
				<div class="mf-social" id="mf-twitter">
					<img src="images/twitter-bird.png"/>
				</div>
	
				<div class="mf-social" id="mf-instagram">
					<div id="ins1">
						<div id="ins2"></div>
						<div id="ins3"></div>
					</div>
				</div>
	
				<div class="mf-social" id="mf-youtube">
					<div class="ytinner"><span class="glyphicon glyphicon-play"></span></div>
				</div>
	
				<div class="mf-social" id="mf-googlep">
					<img src="images/social-googleplus.png"/>
				</div>
			</div>
	
<!-- 			<p id="main-footer-copyright-p"> &copy; 2016 (date('Y') =="2016" ? $year = "" : $year = "- ".date('Y'));  $year;?> AsimKay, Inc.</p>-->			
 	<p id="main-footer-copyright-p"> &copy; 2017 AsimKay, Inc.</p>
		</div>
	<% out.print("</section>"); %>
	
	<%
		if(authenticated.equalsIgnoreCase("true") && transacting.equalsIgnoreCase("true")){
			out.print("<section id='home-content-background'></section>");
		} else {
			out.print("<section id='home-content-background' style='display: none'></section>");
		}
	%>

	<%
		if(authenticated.equalsIgnoreCase("true") && transacting.equalsIgnoreCase("true")){
			out.print("<section id='home-content' style='display: block'>");
		} else {
			out.print("<section id='home-content' style='display: none'>");
		}
	%>

		<div id="header">
			<div class="tabs" id="header-tab1">
				INTERNAL TRANSFER
			</div>
			
			<div class="tabs" id="header-tab2">
				EXTERNAL TRANSFER
			</div>
			
			<div class="tabs" id="header-tab3">
				BANK TO MOBILE
			</div>
			
			<div class="tabs" id="header-tab4">
				MOBILE TO BANK
			</div>
		</div>

		<div id="transaction">
			<!-- <h3>BANK TO BANK</h3> -->
			<div id="details">
				<div id="side1">
					<div id="side1-page1">
						<div id="from">
							<p>SENDER</p>
							
							<p>
								Bank:	<select id="from-select" autocomplete="off">
											<% 
												if(transacting.equalsIgnoreCase("true")){
													if((user != null) && (user.getUserBankAccounts() != null)){
														for(int i=0; i<user.getUserBankAccounts().size(); i++){
															out.print("<option value="+i+">"+user.getUserBankAccounts().get(i).getBank()+"</option>");
														}
													}
												}
											%>
										</select>
							</p>
							
							<p>
								Account Name <br> 
								<span class="user-detail" id="from-name">
									<%
									if(transacting.equalsIgnoreCase("true")){
										if((user != null) && (user.getUserBankAccounts() != null)){
											out.print(user.getUserBankAccounts().get(0).getName());
										}
									}
									%>
								</span>
							</p>
							
							<p>
								Branch: <span class="user-detail" id="from-account-branch">
											<%
									if(transacting.equalsIgnoreCase("true")){
										if((user != null) && (user.getUserBankAccounts() != null)){
											out.print(user.getUserBankAccounts().get(0).getBranch());}
										}
									%>
										</span>
							</p>
							
							<p>
								Balance at least: <span class="user-detail" id="from-account-balance">
									<%
									if(transacting.equalsIgnoreCase("true")){
										if((user != null) && (user.getUserBankAccounts() != null)){
											out.print(user.getUserBankAccounts().get(0).getBalance());
										}
									}
									%>
								</span>
							</p>
							
							<p>
								Transfer Amount <br> <input style="text-align: center;" id="from-amount" type="text" name="">
							</p>
						</div>
						<div id="to">
							<p>RECEIVER</p>
							<p>
								Account Number <br> <input id="to-account-number" type="text" name="">
							</p>
							
							<p>
								Branch: <br> <input id="to-account-branch" type="text" name="">
							</p>
							
							<p>
								Name: <br> <span id="to-name"></span>
							</p>
							
						</div>
					</div>
					
					<div id="side1-page2">
						<div id="from">
							<p>SENDER</p>
							
							<p>
								Bank:	<select id="from-select2" autocomplete="off">
											<% 
												if(transacting.equalsIgnoreCase("true")){
													if((user != null) && (user.getUserBankAccounts() != null)){
														for(int i=0; i<user.getUserBankAccounts().size(); i++){
															out.print("<option value="+i+">"+user.getUserBankAccounts().get(i).getBank()+"</option>");
														}
													}
												}
											%>
										</select>
							</p>
							
							<p>
								Account Name <br> 
								<span class="user-detail" id="from-name2">
									<%
									if(transacting.equalsIgnoreCase("true")){
										if((user != null) && (user.getUserBankAccounts() != null)){
											out.print(user.getUserBankAccounts().get(0).getName());
										}
									}
									%>
								</span>
							</p>
							
							<p>
								Branch: <span class="user-detail" id="from-account-branch2">
											<%
									if(transacting.equalsIgnoreCase("true")){
										if((user != null) && (user.getUserBankAccounts() != null)){
											out.print(user.getUserBankAccounts().get(0).getBranch());}
										}
									%>
										</span>
							</p>
							
							<p>
								Balance at least: <span class="user-detail" id="from-account-balance2">
									<%
									if(transacting.equalsIgnoreCase("true")){
										if((user != null) && (user.getUserBankAccounts() != null)){
											out.print(user.getUserBankAccounts().get(0).getBalance());
										}
									}
									%>
								</span>
							</p>
							
							<p>
								Transfer Amount <br> <input style="text-align: center;" id="from-amount2" type="text" name="">
							</p>
						</div>
						<div id="to">
							<p>RECEIVER</p>
							<p>
								Bank: 	<select id="to-select">
							<% 
								for(int i=0; i<banks.length; i++){
									out.print("<option>"+banks[i]+"</option>");
								}
							%>
							
										</select>
							</p>
							
							<p>
								Account Number <br> <input id="to-account-number2" type="text" name="">
							</p>
							
							<p>
								Branch: <br> <input id="to-account-branch2" type="text" name="">
							</p>
							
							<p>
								Name: <br> <span id="to-name2"></span>
							</p>
						</div>
					</div>
					
					<div id="side1-page3">
						<div id="from">
							<p>SENDER</p>
							
							<p>
								Bank:	<select id="from-select3" autocomplete="off">
											<% 
												if(transacting.equalsIgnoreCase("true")){
													if((user != null) && (user.getUserBankAccounts() != null)){
														for(int i=0; i<user.getUserBankAccounts().size(); i++){
															out.print("<option value="+i+">"+user.getUserBankAccounts().get(i).getBank()+"</option>");
														}
													}
												}
											%>
										</select>
							</p>
							
							<p>
								Account Name <br> 
								<span class="user-detail" id="from-name3">
									<%
									if(transacting.equalsIgnoreCase("true")){
										if((user != null) && (user.getUserBankAccounts() != null)){
											out.print(user.getUserBankAccounts().get(0).getName());
										}
									}
									%>
								</span>
							</p>
							
							<p>
								Branch: <span class="user-detail" id="from-account-branch3">
											<%
									if(transacting.equalsIgnoreCase("true")){
										if((user != null) && (user.getUserBankAccounts() != null)){
											out.print(user.getUserBankAccounts().get(0).getBranch());}
										}
									%>
										</span>
							</p>
							
							<p>
								Balance at least: <span class="user-detail" id="from-account-balance3">
									<%
									if(transacting.equalsIgnoreCase("true")){
										if((user != null) && (user.getUserBankAccounts() != null)){
											out.print(user.getUserBankAccounts().get(0).getBalance());
										}
									}
									%>
								</span>
							</p>
							
							<p>
								Transfer Amount <br> <input style="text-align: center;" id="from-amount3" type="text" name="">
							</p>
						</div>
						<div id="to">
							<p>RECEIVER</p>
							Mobile number <br> <input id="to-mobilenumber" type="text" name="" value="+233 ">
							<br><br><br>
							Name: <span id="to-mobilename"></span>
						</div>
					</div>
					
					<div id="side1-page4">
						<div id="from">
							<p>SENDER</p>
							<p>
								Network: 	<select id="network-select">
												<% 
													if(transacting.equalsIgnoreCase("true")){
														if((user != null) && (user.getUserMobileAccounts() != null)){
															for(int i=0; i<user.getUserMobileAccounts().size(); i++){
																out.print("<option value="+i+">"+user.getUserMobileAccounts().get(i).getName()+"</option>");
															}
														}
													}
												%>
											</select>
							</p>
							
							<p>
								Number: 	<select id="number-select" style="">
												<% 
													if(transacting.equalsIgnoreCase("true")){
														if((user != null) && (user.getUserMobileAccounts() != null)){
															for(int i=0; i<user.getUserMobileAccounts().size(); i++){
																if(user.getUserMobileAccounts().get(0).getName().equals(user.getUserMobileAccounts().get(i).getName())){
																	out.print("<option value="+i+">"+user.getUserMobileAccounts().get(i).getNumber()+"</option>");
																}
															}
														}
													}
												%>
											</select>
							</p>
							
							<p>
								Balance at least: <span id="account-balance4">
											<% 
												if((user != null) && (user.getUserMobileAccounts() != null)){
													out.print(user.getUserMobileAccounts().get(0).getBalance());
												}
											%>
										</span>
							</p>
							
							<p>
								Amount <br> <input style="text-align: center;" id="from-amount4" type="text" name="">
							</p>
							
						</div>
						<div id="to">
							<p>RECEIVER</p>
							
							
							<p>
								Bank: 	<select id="to-select4">
							<% 
								for(int i=0; i<banks.length; i++){
									out.print("<option>"+banks[i]+"</option>");
								}
							%>
							
										</select>
							</p>
							
							<p>
								Account number <br> <input id="to-account-number4" type="text" name="account-number">
							</p>
							<p>
								Branch <br> <input id="to-account-branch4" type="text" name="">
							</p>
							<p>
								Name: <span id="to-name4"></span>
							</p>
							
						</div>
					</div>
				</div>
				<div id="side2">
					<div id="side2-background">
					</div>
					
					<div id="side2-content">
						<p id="t-result-p">Transaction Result</p>
						<div id="server-response">
							<p></p>
						</div>
						<div id="processing-rotation">
							<img id="processing-rotation-img" src="" alt="">
						</div>
						<button id="transact-button">TRANSACT</button>
					</div>
				</div>
			</div>
		</div>
	<%
		out.print("</section>");
	%>
