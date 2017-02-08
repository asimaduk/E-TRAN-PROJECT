$(document).ready(function() {
	
	if($("#auth").html() == "true")
		var auth = true;
	
	if($("#transact").html() == "true")
		var transacting = true;
	
	var user = null;
	
	var domain = $("#domain").html();
	
	var supportedBanks = $("#supportedBanks").html();
	var banks = supportedBanks.split(":");
		
	var videoTracks;
	
	$("#user-search-query").focus();

	function displayOrCloseChatBox() {
		if(document.getElementById("chat-box-open-close").style.right == '30px')
		{	
			document.getElementById("chat-box-open-close").innerHTML = 'CLOSE';
			document.getElementById("chat-box-open-close").style.padding = '19px 5px 5px';
			document.getElementById("chat-box-open-close").style.right = '26%';
			document.getElementById("chat-box-container").style.display = 'block';
			document.getElementById("chat-box-generate-textArea").focus();

		//Grab elements, create settings, etc.
		var video = document.getElementById('recipient-picture-video');
		var audio = document.getElementById('audio-chat');
		
		var senderVideo = document.getElementById('sender-video');

		// Get access to the camera!
		if(navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
		    // Not adding `{ audio: true }` since we only want video now
		    navigator.mediaDevices.getUserMedia({ audio: true, video: true }).then(function(stream) {
		        video.src = window.URL.createObjectURL(stream);
		        video.play();
		        
		        senderVideo.src = window.URL.createObjectURL(stream);
		        senderVideo.play();

		        videoTracks = stream.getVideoTracks();

		        audio.src = window.URL.createObjectURL(stream);
		        audio.play();
		    });
		}

		/* Legacy code below: getUserMedia 
		else if(navigator.getUserMedia) { // Standard
		    navigator.getUserMedia({ video: true }, function(stream) {
		        video.src = stream;
		        video.play();
		    }, errBack);
		} else if(navigator.webkitGetUserMedia) { // WebKit-prefixed
		    navigator.webkitGetUserMedia({ video: true }, function(stream){
		        video.src = window.webkitURL.createObjectURL(stream);
		        video.play();
		    }, errBack);
		} else if(navigator.mozGetUserMedia) { // Mozilla-prefixed
		    navigator.mozGetUserMedia({ video: true }, function(stream){
		        video.src = window.URL.createObjectURL(stream);
		        video.play();
		    }, errBack);
		}
		*/

		}

		else {
			document.getElementById("chat-box-open-close").style.padding = '19px 10px 5px';
			document.getElementById("chat-box-open-close").innerHTML = 'CHAT';
			document.getElementById("chat-box-container").style.display = 'none';
			document.getElementById("chat-box-open-close").style.right = '30px';

			videoTracks.forEach(function(track) { track.stop() });
		}	
	} 
	
	$("#chat-box-open-close").click(function(){
		$("#chat-control").css("display","block");
		$("#chat-box-open-close").css("display","none");
		$("#chat-control #input1").focus();
	});
	
	$("#chat-control #input2").click(function(){
		alert()
	});
	
	document.getElementById("chat-box-generate-textArea").onkeydown = function checkFirst(e){
		if(this.selectionStart == 0)
		{
			var char = String.fromCharCode(e.which);
			if(char.match(/^\w$/))
			{
				//Empty with printable key
				this.value = char.toUpperCase() + this.value;
				this.selectionStart = 1;
				this.selectionEnd = 1;
				return false;
			}
		} else {
			
		}
	}
	
	function validateEmail(){
    	var email = $("#pf-email").val();
    	var symbolsDisallowed = "±!#$%^&*()_+|';\/,~";
    	var iterate = true;

    	for(var i=0; i<email.length; i++){
    		//check for any symbol apart from '@' and '.'
    		for(var j=0; j<symbolsDisallowed.length; j++){
				if(email[i] == symbolsDisallowed[j]){
					alert("disallowed symbol is: "+symbolsDisallowed[j]);
					return false;
				}
			}
    		
    		//check for '@.' existing and '@' appearing before '.'
    		if((email.indexOf('@') > 2) && (email.indexOf('.') > (email.indexOf('@')+2)) 
    								  && (email.charAt(email.indexOf('.')+1))
    								  && (email.charAt(email.indexOf('.')+2))){
    			return true;
    		} else {
    			return false;
    		}    		
    	}	
    }
	
	function displayLoginForm(){
		$("#submit").css({"-webkit-transform":"rotate(0deg)","-moz-transform":"rotate(0deg)","-ms-transform":"rotate(0deg)","transform":"rotate(0deg)"});
		$("#register").css("display","none");
		$("#login-form-background").css("display", "block");
		$("#login-form").css("display","block");
		$("#login-username").focus();
		$("#login-form-background").css("height","250px");
		$("#password-forgotten-form").css("display","none");
		$("#banner-login").css("background-color","#28b463");
		$("#banner-signup").css("background-color","#34495e");
	}
	
	if(transacting){
		var xhttp;
	  	xhttp = new XMLHttpRequest();
	  	xhttp.onreadystatechange = function(xhr) {
  	    if (this.readyState == 4 && this.status == 200) { 
  	    	user = JSON.parse(this.responseText);
  	    }
  	  };
  	  xhttp.open("POST", domain+"GetUser", true);
  	  xhttp.send();
	}
	
	function authenticate() {
    	var username = $("#login-username").val();
    	var password = $("#login-password").val();
    	var rememberMe = $("#keep-signedIn").prop("checked");
    	  
    	  if (username == "" || password == "") {
    	    alert("Both fields required!");
    	  } 
    	  
    	  else {
    		  var angle = 0;
    		  var setVar = setInterval(function() {
    			  angle += 50;
    			  $("#submit").css({"-webkit-transform":"rotate("+angle+"deg)","-moz-transform":"rotate("+angle+"deg)","-ms-transform":"rotate("+angle+"deg)","transform":"rotate("+angle+"deg)"});
    		  }, 200);
    		  
    		  var xhttp;
    		  xhttp = new XMLHttpRequest();
	    	  xhttp.onreadystatechange = function(xhr) {
	    	    if (this.readyState == 4 && this.status == 200) { 
	    	    		
	    	    	clearInterval(setVar);
	    	    	
    	    		user = JSON.parse(this.responseText);
    	    		$("#welcome-msg").html("Welcome,");
    	    		$("#nameSpan").html(user.firstname);
    	    		$("#dp").attr("src",domain+"GetImage?imageId="+user.imageId);
    	    		
    	    		$("#login-form-background").css("display", "none");	
    	    		$("#login-form").css("display","none");
    	    		
    	    		$("#banner-login").css({"display":"none","background-color":"#34495e"});
    	    		$("#banner-logout").css("display","block");
    	    		$("#banner-signup").css("display","none");
    	    		$("#banner-profile").css("display","block");
    	    		$("#banner-app").css("display","block");
    	    		
    	    		if(user.isAdmin == 1){
    	    			$("#banner-admin").css("display","block");
    	    		}
    	    		
    	    		$("#login-form-background").css("display","none");	
    	    		$("#login-form").css("display", "none");
    	    		$("#login-username").val("");
    	    		$("#login-password").val("");

    	    		$("#chat-box-open-close").css("display","block");
    	    		
    	    		$("#from-select option").remove();
    	    		$("#from-select2 option").remove();
    	    		$("#from-select3 option").remove();
    	    		$("#network-select option").remove();
    	    		
    	    		if(user.userBankAccounts != null){
    	    			for(var i=0; i<user.userBankAccounts.length; i++){
	    	    			var option = new Option(user.userBankAccounts[i].bank,i);
	    	    			$("#from-select").append($(option));
	    	    		}
	    	    		
    	    			for(var i=0; i<user.userBankAccounts.length; i++){
	    	    			var option = new Option(user.userBankAccounts[i].bank,i);
	    	    			$("#from-select2").append($(option));
	    	    		}
    	    			
    	    			for(var i=0; i<user.userBankAccounts.length; i++){
	    	    			var option = new Option(user.userBankAccounts[i].bank,i);
	    	    			$("#from-select3").append($(option));
	    	    		}
    	    			
    	    			for(var i=0; i<user.userMobileAccounts.length; i++){
    	    				//if()
	    	    			var option = new Option(user.userMobileAccounts[i].name,i);
	    	    			$("#network-select").append($(option));
	    	    		}
    	    			
    	    			for(var i=0; i<user.userMobileAccounts.length; i++){
    	    				if(user.userMobileAccounts[i].name == user.userMobileAccounts[$("#network-select").val()].name){
    	    					var option = new Option(user.userMobileAccounts[i].number,i);
		    	    			$("#number-select ").append($(option));
    	    				}
	    	    			
	    	    		}
    	    			
	    	    		$("#from-name, #from-name2, #from-name3").html(user.userBankAccounts[0].name);
	    	    		$("#from-account-branch, #from-account-branch2, #from-account-branch3").html(user.userBankAccounts[0].branch);
	    	    		$("#from-account-balance, #from-account-balance2, #from-account-balance3").html(user.userBankAccounts[0].balance);
    	    		}

    	    		auth = true; 
	    	    }
	    	    else if (this.readyState == 4 && this.status == 203){
	    	    	clearInterval(setVar);
	    			$("#submit").css({"-webkit-transform":"rotate(0deg)","-moz-transform":"rotate(0deg)","-ms-transform":"rotate(0deg)","transform":"rotate(0deg)"});
	    	    	alert(this.responseText);
	    	    } 
	    	    else if (this.readyState == 4 && this.status == 500){
	    	    	clearInterval(setVar);
	    			$("#submit").css({"-webkit-transform":"rotate(0deg)","-moz-transform":"rotate(0deg)","-ms-transform":"rotate(0deg)","transform":"rotate(0deg)"});
	    	    	alert("Failed to login");
	    	    }
	    	  };
	    	  xhttp.open("POST", domain+"Authenticate?username="+username+"&password="+password+"&remember-me="+rememberMe, true);
	    	  xhttp.send();
		  }
    }
	
	function showTransactionPage(){
		$("#wrapper").css("display","none");
		
		$("#home-content-background").css("display","block");
		$("#home-content").css("display","block");
		$("#banner-app").css("background-color","#28b463");
		
		var xhttp;
		xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function(xhr) {
	  	    if (this.readyState == 4 && this.status == 200) {
	  	    		
	  	    }
  	  	};
  	  	xhttp.open("GET", domain+"Transacting?status=true", true);
  	  	xhttp.send();
  	  	
  	  	transacting = true;
	}
	
	function hideTransactionPage(){
		$("#from-select option").remove();
		
		$("#wrapper").css("display","block");
		
		$("#home-content-background").css("display","none");
		$("#home-content").css("display","none");
		$("#banner-app").css("background-color","#34495e");
		
		var xhttp;
		xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function(xhr) {
	  	    if (this.readyState == 4 && this.status == 200) {
	  	    		//alert(bak);
	  	    }
	  	};
  	    xhttp.open("GET", domain+"Transacting?status=false", true);
  	    xhttp.send();
  	    
  	    transacting = false;
	}

	$("#banner-login").click(function(){
		displayLoginForm();
	});
	
	$("#login-submit").click(function(){
		authenticate();
	});
	
	$("#inner-login-form").keypress(function(e) {
		if(e.keyCode === 13 && !e.shiftKey)
		{
			authenticate();
		}
	});
	
	$("#banner-logout").click(function logout(){ 
		var xhttp;
		xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function(xhr) {
	  	    if (this.readyState == 4 && this.status == 200) {
	  	    		$("#welcome-msg").html("Hi, ");
	  	    		$("#nameSpan").html("Guest");
	  	    		$("#dp").attr("src",domain+"images/photo.png");
	  	    		$("#banner-login").css("display","block");
	  	    		$("#banner-logout").css("display","none");
	  	    		$("#banner-signup").css("display","block");
	  	    		$("#banner-admin").css("display","none");
	  	    		$("#banner-profile").css("display","none");	
	  	    		$("#banner-app").css("display","none");
	  	    		$("#chat-box-container").css("display","none");
		    		$("#chat-box-open-close").css("display","none");
		    		$("#home-content").css("display","none");
		    		$("#home-content-background").css("display","none");
		    		
		    		$("#wrapper").css("display","block");
		    		$("#banner-app").css("background-color","#34495e");
		    		
		    		auth = false;
		    		location.hash = "#home";
	  	    }
  	  	};
  	    xhttp.open("POST", domain+"Logout", true);
  	    xhttp.send();
	});
	
	
	if (window.history && window.history.pushState) {

        $(window).on('popstate', function() {
          var hashLocation = location.hash;
          var hashSplit = hashLocation.split("#!/");
          var hashName = hashSplit[0];
          
          //alert("hash name is: "+hashName);
          
          if (hashName !== '') {
            var hash = window.location.hash;
            if ((hash === '#home') && transacting) {
            	hideTransactionPage();
            }
            
            else if((hash === '#transact') && !transacting){
            	if(auth === true){
            		showTransactionPage();
            	} else {
            		//alert("auth not true for transaction, redirection home and auth is: "+auth);
            		location.hash = "#home";
            	}
            }
            
            else if((hash === '#home') && !transacting){
            	//alert("about to go home");
            }
            
            else {
            	if(auth === true){
            		//alert("alert auth before show transaction page: "+auth+" and trans is: "+transacting)
            		showTransactionPage();
            		location.hash = "#transact";
            	} else {
            		//alert("alert auth before redirection to home: "+auth.length)
            		location.hash = "#home";
            	}            	
            }
          } else if(hashName === '' && transacting) {
        	  hideTransactionPage();
          } else if(hashName === '#home' && !transacting) {
        	 // alert("case here");//showTransactionPage();
          }
          
        });
        
        if(auth === true && transacting === true){
        	window.history.pushState(null, null, './#transact');
        } else {
        	window.history.pushState(null, null, './#home');
        }
     }
	
	
	$("#close-login-form").click(function hideLoginForm(){
		$("#banner-login").css("background-color","#34495e");
		$("#login-form-background").css("display", "none");
		$("#login-username").val("");
		$("#login-password").val("");
	});
	
	$("#close-pff-form").click(function hidePFForm(){
		$("#login-form-background").css("display", "none");
		$("#banner-login").css("background-color","#34495e");
		$("#email").val("");
	});
	
	
	function displayRegistrationForm(){
		$("#register").css("display","block");
		$("#login-form-background").css("display","none");
		$("#login-form").css("display","none");
		$("#banner-signup").css("background-color","#28b463");
		$("#banner-login").css("background-color","#34495e");
	}
	
	$("#create-account, #banner-signup").click(function(e){
		e.preventDefault();
		displayRegistrationForm();
	});
	
	document.getElementById("close-reg-form").onclick = function hideRegForm(){
		$("#register-cover").css('top','100%');
	    $("#processn-rotation-img").attr("src","");
		document.getElementById("register").style.display = 'none';
		$("#banner-signup").css("background-color","#34495e");
	}
	
	document.getElementById("forgot-password").onclick = function displayPasswordForgottenForm(e){
		document.getElementById("password-forgotten-form").style.display = 'block';
		document.getElementById("login-form-background").style.height = '210px';
		document.getElementById("login-form").style.display = 'none';
	}

	  function readURL(input) {
	        if (input.files && input.files[0]) {
	            var reader = new FileReader();
	            
	            reader.onload = function (e) {
	                $('#image-preview img').attr('src', e.target.result);
	            }
	            
	            reader.readAsDataURL(input.files[0]);
	        }
	    }
	    
	    $("#imgInp").change(function(){
	        readURL(this);
	    });
	    
	    
        var waitingMsg = false;
        function getMessage(){ 
            if(!waitingMsg){
                waitingMsg = true;
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function(){
                    if(xhr.readyState == 4){
                        if(xhr.status == 200){
                            var messagePane = document.getElementById('chat-box-messages-pane');
                            messagePane.innerHTML = messagePane.innerHTML + xhr.responseText;
            				messagePane.scrollTop = messagePane.scrollHeight;
                        }
                        waitingMsg = false;
                    }
                };
                xhr.open('get', domain+'ChatServlet', true);
                xhr.send();
            }
        }
        
        setInterval(getMessage, 1000); //check out this
          	
        function postMessage(){
            var xhr = new XMLHttpRequest();
            xhr.open('post',domain+'ChatServlet', false);
            xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
            
            var name = escape(document.getElementById('q').value);
            var msg = escape(document.getElementById('chat-box-generate-textArea').value);
           
            document.getElementById('chat-box-generate-textArea').value='';
            document.getElementById("chat-box-generate-textArea").focus();
   
            var hours = (new Date()).getHours() > 9 ? (new Date()).getHours() : '0'+(new Date()).getHours();
            var minutes = (new Date()).getMinutes() > 9 ? (new Date()).getMinutes() : '0'+(new Date()).getMinutes();
            
            xhr.send('name='+name+'&msg='+msg+'&t='+hours+":"+minutes);
        }
	    
        document.getElementById("chat-box-generate-send").onclick = function()
        {
        	if(document.getElementById("chat-box-generate-textArea").value != "") //FIX EMPTY SPACES
			{
				postMessage();
				
				var messagePane = document.getElementById("chat-box-messages-pane");
				messagePane.scrollTop = messagePane.scrollHeight;
				
			} else {
				document.getElementById("chat-box-generate-textArea").focus();
			}
        }
      
        document.getElementById("chat-box-generate-textArea").onkeypress = function enterKeyAppendMessage(e) {
    		if(e.keyCode === 13 && !e.shiftKey)
    		{
    			e.preventDefault();

    			if(document.getElementById("chat-box-generate-textArea").value != "") //FIX EMPTY SPACES
    			{
    				postMessage();
    			} else {
    				document.getElementById("chat-box-generate-textArea").focus();
    			}
    		}
    		
    	}
        
        var bCounter = 1;
        $("#reg-opt1").click(function(){
        	if(bCounter < 4){
        		var $newDiv = $("<div class='reg-single-acc'></div>");
            	$newDiv.attr("id", "newDiv" + bCounter);
            	
            	var $newSelect = $("<select></select>");
            	$newSelect.attr("class", "reg-acc-select");
            	$newSelect.attr("id", "reg-acc-select" + bCounter);
            	
            	for(var i=0; i<banks.length; i++){
            		var $newOption = $("<option>"+banks[i]+"</option>");
                	$newSelect.append($newOption);
            	}
            	
            	var $newInput = $("<input/>");
            	$newInput.attr("class", "reg-acc-input");
            	$newInput.attr("id", "reg-acc-input" + bCounter);
            	$newInput.attr("type", "text");
            	$newInput.attr("placeholder", "*Account number");
            	alert("reg-acc-input"+bCounter);
            	$newDiv.append($newSelect);
            	$newDiv.append($newInput);
            	
            	$("#reg-account-detail").append($newDiv);
            	bCounter++;
        	}
        	else {
        		alert("Can not add anymore bank accounts");
        	}
      
        });
        
        var mCounter = 1;
        $("#reg-opt2").click(function(){
        	if(mCounter < 4){
        		var $newDiv = $("<div class='reg-single-acc'></div>");
            	$newDiv.attr("id", "newDivMobi" + mCounter);
            	
            	var $newInput = $("<input/>");
            	$newInput.attr("class", "reg-mobi-input");
            	$newInput.attr("id", "reg-mobi-input" + mCounter);
            	$newInput.attr("type", "text");
            	$newInput.attr("placeholder", "*Mobile number");
            	
            	$newDiv.append($newInput);
            	
            	$("#reg-account-detail").append($newDiv);
            	mCounter++;
        	}
        	else {
        		alert("Can not add anymore mobile accounts");
        	}
      
        });
        
        function registerClient(){
	        $("#register-cover").css('top','50px');
	        $("#processn-rotation-img").attr("src","images/loading2.gif");
	       
	        var url = domain+"RegisterUser";
	        
	        var data = new FormData();
	        data.append('firstname', $("#signUp-fn").val());
	        data.append('lastname', $("#signUp-ln").val());
	        data.append('email', $("#email-id").val());
	        data.append('username', $("#username").val());
	        data.append('phone-number', $("#phone-number").val());
	        data.append('gender', $("#gender").val());
	        data.append('password', $("#password").val());
	        data.append('confirm-password', $("#confirm-password").val());
	        data.append('address', $("#address").val());
	        
	        data.append('number-of-bank-accounts',bCounter-1);
	        data.append('number-of-mobile-accounts',mCounter-1);
	        
	        for(var i=1; i<bCounter; i++){
	        	data.append('bank-account'+i,$("#reg-acc-select"+i).val()+":"+$("#reg-acc-input"+i).val());
	        }
	     
	        for(var i=1; i<mCounter; i++){
	        	data.append('mobile-account'+i,$("#reg-mobi-input"+i).val());
	        }
	        
	        $.each($("#imgInp")[0].files, function(i, file) {
	        	data.append("photo", file);
	        });
	        
	        var jqxhr = $.ajax({
        	  type: "POST",
        	  url: url,
        	  data: data,
        	  cache: false,
        	  contentType: false,
        	  enctype: 'multipart/form-data',
        	  processData: false,
        	  async: false,
        	  error: function(xhr, status, error)
        	  {
        		  alert("Registration failed!");
        		  $("#register-cover").css('top','100%');
      	       	  $("#processn-rotation-img").attr("src","");
        	  },
        	  success: function(xhr)
        	  {
        		  $("#register-cover").css('top','100%');
      	       	  $("#processn-rotation-img").attr("src","");
        		  $("#register").css('display','none');
        		  $("#banner-signup").css("background-color","#34495e");
        		  alert(xhr);
        	  }
	        });
        }
        
        $("#registrationForm").submit(function(event) {
	        event.preventDefault();
	        
	        if((bCounter > 1) || (mCounter > 1)){
	        	registerClient();
	        } else {
	        	alert("Provide at least one account info...");
	        }
        });
        
        var activePageID = 1;
        
        $(".tabs").click(function(event){  
            var element = event.target.id;
         
            var tabNumber = element.charAt(element.length-1);
            
            $("#side1-page"+tabNumber).css("top", "0px");
            
            $("#"+element).css("background-color", "#28b463");
            $("#header-tab"+activePageID).css("background-color", "#34495e");
            
            if(activePageID != tabNumber){
            	$("#side1-page"+activePageID).css("top", "100%");
                $("#header-tab"+activePageID).css("opacity", "0.8");
            }
           
            if(tabNumber == 1 && user != null && user.userBankAccounts != null){
            	var index = $("#from-select").val();
            	$("#from-account-balance").html(user.userBankAccounts[index].balance);
            }
            
            else if(tabNumber == 2 && user != null && user.userBankAccounts != null){
            	var index = $("#from-select2").val();
            	$("#from-account-balance2").html(user.userBankAccounts[index].balance);
            }
            
            else if(tabNumber == 3 && user != null && user.userBankAccounts != null){
            	var index = $("#from-select3").val();
            	$("#from-account-balance3").html(user.userBankAccounts[index].balance);
            }
            
            else if(tabNumber == 4 && user != null && user.userMobileAccounts != null){
            	var value = $("#network-select").val();
            	
            	$("#number-select option").remove();
            	
            	for(var i=0; i<user.userMobileAccounts.length; i++){
            		if(user.userMobileAccounts[i].name == user.userMobileAccounts[value].name){
            			var option = new Option(user.userMobileAccounts[i].number,i);
            			$("#number-select").append($(option));
            		}    			
        		}
    			
            	var index = $("#number-select").val();
        		$("#account-balance4").html(user.userMobileAccounts[index].balance);
            }
            
            activePageID = tabNumber;
        }); 
        
//        function changeBackgroundImage(){
//        	var bgImage = $('body').css('background-image');
//        	var fileName = bgImage.slice(bgImage.lastIndexOf('/')+1, bgImage.length - 2);
//        	
//        	if(fileName == "bg.jpeg"){
//        		$("body").css("background-image","url('images/b.jpeg')");
//        	} else {
//        		$("body").css("background-image","url('images/bg.jpeg')");;
//        	}
//        }
        
        //setInterval(changeBackgroundImage, 5000);
        
        function transactInternalTransfer(){
        	var id = $("#to-account-number").val();
        	var branch = $("#to-account-branch").val();
        	var amount = $("#from-amount").val();
        	var index = $("#from-select").val();
        	
        	if(id.length > 5){
        		//alert("id length: "+id.length)
        	}
        	var xhttp;
    	  	xhttp = new XMLHttpRequest();
    	  	xhttp.onreadystatechange = function(xhr) {
      	    if (this.readyState == 4 && this.status == 200) {
      	    	$("#server-response p").css("color","#28b463");
      	    	$("#server-response").css("border-color","#28b463");
      	    	$("#server-response p").html("Transaction successful, new balance is "+this.responseText);
      	    	user.userBankAccounts[index].balance = this.responseText;
      	    	$("#from-account-balance").html(this.responseText);
      	    	$("#processing-rotation-img").attr("src","");
      	    	$("#transact-button").html("TRANSACT");
      	    }
      	    
      	    else if (this.readyState == 4 && this.status == 201) {
      	    	$("#server-response p").css("color","red");
      	    	$("#server-response").css("border-color","red");
      	    	$("#server-response p").html("Transaction failed! Please try again... or if failure persist, kindly contact admin.");
      	    	$("#processing-rotation-img").attr("src","");
      	    	$("#transact-button").html("TRANSACT");
      	    } 
      	    
      	    else if (this.readyState == 4 && this.status == 500) {
      	    	$("#server-response p").css("color","red");
      	    	$("#server-response").css("border-color","red");
      	    	$("#server-response p").html("Transaction failed! Please try again... or if failure persist, kindly contact admin.");
      	    	$("#processing-rotation-img").attr("src","");
      	    	$("#transact-button").html("TRANSACT");
      	    }
      	    
      	  };
      	  xhttp.open("POST", domain+"InternalTransfer?id="+id+"&branch="+branch+
      			  "&amount="+amount+"&index="+index, true);
      	  xhttp.send();
        }
        
        function transactExternalTransfer(){
        	var id = $("#to-account-number2").val();
        	var branch = $("#to-account-branch2").val();
        	var amount = $("#from-amount2").val();
        	var bank = $("#to-select").val();
        	
        	var index = $("#from-select2").val();
        	
        	if(id.length > 5){
        		//alert()
        	}
        	
        	var xhttp;
    	  	xhttp = new XMLHttpRequest();
    	  	xhttp.onreadystatechange = function(xhr) {
      	    if (this.readyState == 4 && this.status == 200) {
      	    	$("#server-response p").css("color","#28b463");
      	    	$("#server-response").css("border-color","#28b463");
      	    	$("#server-response p").html("Transaction successful, new balance is "+this.responseText);
      	    	user.userBankAccounts[index].balance = this.responseText;
      	    	$("#from-account-balance2").html(this.responseText);
      	    	$("#processing-rotation-img").attr("src","");
      	    	$("#transact-button").html("TRANSACT");
      	    }
      	    
      	    else if (this.readyState == 4 && this.status == 201) {
      	    	$("#server-response p").css("color","red");
      	    	$("#server-response").css("border-color","red");
      	    	$("#server-response p").html("Transaction failed! Please try again... or if failure persist, kindly contact admin.");
      	    	$("#processing-rotation-img").attr("src","");
      	    	$("#transact-button").html("TRANSACT");
      	    } 
      	    
      	    else if (this.readyState == 4 && this.status == 500) {
      	    	$("#server-response p").css("color","red");
      	    	$("#server-response").css("border-color","red");
      	    	$("#server-response p").html("Transaction failed! Please try again... or if failure persist, kindly contact admin.");
      	    	$("#processing-rotation-img").attr("src","");
      	    	$("#transact-button").html("TRANSACT");
      	    }
      	    
      	  };
      	  xhttp.open("POST", domain+"ExternalTransfer?id="+id+"&branch="+branch+
      			  "&amount="+amount+"&bank="+bank+"&index="+index, true);
      	  xhttp.send();
        }
        
        function transactBankToMobile(){
        	if($("#to-mobilenumber").val().length == 14){
        		var index = $("#from-select3").val();
        		var mobile = $("#to-mobilenumber").val();
        		var amount = $("#from-amount3").val();
        		
        		var xhttp;
        	  	xhttp = new XMLHttpRequest();
        	  	xhttp.onreadystatechange = function(xhr) {
          	    if (this.readyState == 4 && this.status == 200) {
          	    	$("#server-response p").css("color","#28b463");
          	    	$("#server-response").css("border-color","#28b463");
          	    	$("#server-response p").html("Transaction successful, new balance is "+this.responseText);
          	    	user.userBankAccounts[index].balance = this.responseText;
          	    	$("#from-account-balance3").html(this.responseText);
          	    	$("#processing-rotation-img").attr("src","");
          	    	$("#transact-button").html("TRANSACT");
          	    }
          	    
          	    else if (this.readyState == 4 && this.status == 201) {
          	    	$("#server-response p").css("color","red");
          	    	$("#server-response").css("border-color","red");
          	    	$("#server-response p").html("Transaction failed! Please try again... or if failure persist, kindly contact admin.");
          	    	$("#processing-rotation-img").attr("src","");
          	    	$("#transact-button").html("TRANSACT");
          	    } 
          	    
          	    else if (this.readyState == 4 && this.status == 500) {
          	    	$("#server-response p").css("color","red");
          	    	$("#server-response").css("border-color","red");
          	    	$("#server-response p").html("Transaction failed! Please try again... or if failure persist, kindly contact admin.");
          	    	$("#processing-rotation-img").attr("src","");
          	    	$("#transact-button").html("TRANSACT");
          	    }
          	    
          	  };
          	  xhttp.open("POST", domain+"BankToMobileTransfer?index="+index+"&mobile="+mobile+"&amount="+amount, true);
          	  xhttp.send();
        	}
        	else {
        		alert("Invalid mobile number provided");
        	}
        }
        
        function transactMobileToBank(){
        	var index = $("#number-select").val();
			var mobile = user.userMobileAccounts[index].number;
    		var amount = $("#from-amount4").val();

    		var toBank = $("#to-select4").val();
    		var toNumber = $("#to-account-number4").val();
    		var toBranch = $("#to-account-branch4").val();
    		
    		var xhttp;
    	  	xhttp = new XMLHttpRequest();
    	  	xhttp.onreadystatechange = function(xhr) {
      	    if (this.readyState == 4 && this.status == 200) {
      	    	$("#server-response p").css("color","#28b463");
      	    	$("#server-response").css("border-color","#28b463");
      	    	$("#server-response p").html("Transaction successful, new balance is "+this.responseText);
      	    	user.userMobileAccounts[index].balance = this.responseText;
      	    	$("#account-balance4").html(this.responseText);
      	    	$("#processing-rotation-img").attr("src","");
      	    	$("#transact-button").html("TRANSACT");
      	    }
      	    
      	    else if (this.readyState == 4 && this.status == 201) {
      	    	$("#server-response p").css("color","red");
      	    	$("#server-response").css("border-color","red");
      	    	$("#server-response p").html("Transaction failed! Please try again... or if failure persist, kindly contact admin.");
      	    	$("#processing-rotation-img").attr("src","");
      	    	$("#transact-button").html("TRANSACT");
      	    } 
      	    
      	    else if (this.readyState == 4 && this.status == 500) {
      	    	$("#server-response p").css("color","red");
      	    	$("#server-response").css("border-color","red");
      	    	$("#server-response p").html("Transaction failed! Please try again... or if failure persist, kindly contact admin.");
      	    	$("#processing-rotation-img").attr("src","");
      	    	$("#transact-button").html("TRANSACT");
      	    }
      	    
      	  };
      	  xhttp.open("POST", domain+"MobileToBankTransfer?mobile="+mobile+"&amount="+amount+"&bank="+toBank+"&number="+toNumber+"&branch="+toBranch, true);
      	  xhttp.send();
        	
        }
        
        $("#transact-button").click(function(event){
        	if($("#transact-button").html() == "TRANSACT"){
      	    	$("#server-response p").html("");
      	    	$("#server-response").css("border-color","transparent");
        		$("#processing-rotation-img").attr("src","images/loading2.gif");
            	
            	$(this).html("Processing...");
            	
            	switch(Number(activePageID)){
            		case 1:
            			transactInternalTransfer();
            			break;
            		
            		case 2:
            			transactExternalTransfer();
            			break;
            	
            		case 3:
            			transactBankToMobile();
            			break;
            		
            		case 4:
            			transactMobileToBank();
            			break;
            			
            		default:
            			alert("An error occured processing transaction");
            			break;
            	}
        	} else {
        		$("#server-response p").html("Transacting... please wait");
        	}
        	
        });
        
        function validateNonEmptyString(a){
        	var count = 0;
        	for(var i=0; i<a.length; i++){
        		if(a[i] == " "){
        			count++;
        		}
        	}
        	
        	if(count == a.length)
        		return false;
        	else
        		return true;
        		
        }
        
        $("#pf-send").click(function(){
        	var email = $("#pf-email").val();
        	$("#pf-send").val("PROCESSING...");
        	var ret = validateNonEmptyString(email);
        	if(ret){
        		var status = validateEmail();
        		if(status){
        			var xhttp;
        			xhttp = new XMLHttpRequest();
        			xhttp.onreadystatechange = function(xhr) {
        		  	    if (this.readyState == 4 && this.status == 200) {
        		  	    		alert('A link has been sent to your email and shortcode to your phone, follow the link and provide the shortcode to reset your password.');
        		  	    		$("#pf-send").val("RESET PASSWORD");
        		  	    		$("#login-form-background").css("display","none");
            		  			$("#banner-login").css("background-color","#34495e");
        		  	    } 
        		  	    else if (this.readyState == 4 && this.status == 201) {
        		  	    	alert('Processing reset failed. Please try again...');
        		  	    	$("#pf-send").val("RESET PASSWORD");
        		  	    }
        	  	  	};
        	  	  	xhttp.open("POST", domain+"ResetPassword?email="+email, true);
        	  	  	xhttp.send();
        	  	  	
        		} else {
        			alert('Email format invalid');
        		}
        	}
        	else
        		alert('Attempt failed! Email may contain empty spaces...');
        });
        
        $("#from-select").change(function(){
        	var value = $("#from-select").val();
        	$("#from-name").html(user.userBankAccounts[value].name);
    		$("#from-account-branch").html(user.userBankAccounts[value].branch);
    		$("#from-account-balance").html(user.userBankAccounts[value].balance);
        }); 
        
        $("#from-select2").change(function(){
        	var value = $("#from-select2").val();
        	$("#from-name2").html(user.userBankAccounts[value].name);
    		$("#from-account-branch2").html(user.userBankAccounts[value].branch);
    		$("#from-account-balance2").html(user.userBankAccounts[value].balance);
        });
        
        $("#from-select3").change(function(){
        	var value = $("#from-select3").val();
        	$("#from-name3").html(user.userBankAccounts[value].name);
    		$("#from-account-branch3").html(user.userBankAccounts[value].branch);
    		$("#from-account-balance3").html(user.userBankAccounts[value].balance);
        });
        
        $("#to-select").change(function(){
        	var value = $("#to-select").val();
        	//alert(value);
        });
        
        $("#network-select").change(function(){
        	var value = $("#network-select").val();
        	
        	$("#number-select option").remove();
        	
        	for(var i=0; i<user.userMobileAccounts.length; i++){
        		if(user.userMobileAccounts[i].name == user.userMobileAccounts[value].name){
        			var option = new Option(user.userMobileAccounts[i].number,i);
        			$("#number-select").append($(option));
        		}    			
    		}
			
        	var index = $("#number-select").val();
    		$("#account-balance4").html(user.userMobileAccounts[index].balance);
        });
        
        $("#number-select").change(function(){
        	$("#account-balance4").html(user.userMobileAccounts[$("#number-select").val()].balance);
        });
        
        $("#from-amount, #from-amount2, #from-amount3, #from-amount4").keydown(function checkFirst(e){
			var char = String.fromCharCode(e.which);
			var keyCode = e.which;
			
			if (keyCode > 57 && keyCode != 190) {
		        e.preventDefault();
		        return false;
		    } 
			
			if (keyCode == 32) {
		        e.preventDefault();
		        return false;
		    }
			
			if(char.match(/^\w$/))
			{
				if((this.value == "") && (keyCode == 48)){
					e.preventDefault();
			        return false;
				}
				
				if(((this.value).length == 4) && (!(this.value).includes("."))){
					this.value = this.value + ".";
				}
				
				if((this.value).length > 6){
					e.preventDefault();
			        return false;	
				}
			} 
    	});
        
        $("#to-account-number, #to-account-number2, #to-account-number4").keydown(function checkFirst(e){
			var char = String.fromCharCode(e.which);
			var keyCode = e.which;
			
			if (keyCode > 57) {
		        e.preventDefault();
		        return false;
		    } 
			
			if (keyCode == 32) {
		        e.preventDefault();
		        return false;
		    }
			
			if(char.match(/^\w$/))
			{
				if((this.value == "") && (keyCode == 48)){
					e.preventDefault();
			        return false;
				}
				
				if((this.value).length > 14){
					e.preventDefault();
			        return false;	
				}
			} 
    	});
        
        $("#signUp-fn,#signUp-ln,#username,#to-account-branch, #to-account-branch2, #to-account-branch4").keydown(function checkFirst(e){
			var char = String.fromCharCode(e.which);
			var keyCode = e.which;
			
			if (!((keyCode > 64) && (keyCode < 91)) && (keyCode != 37) && (keyCode != 39) && (keyCode != 8) && (keyCode != 32)) {
				e.preventDefault();
		        return false;
		    } 
			
			if(char.match(/^\w$/)){
				if((this.value == "" || this.selectionStart == 0)){
					this.value = char.toUpperCase() + this.value;
					this.selectionStart = 1;
					this.selectionEnd = 1;
					return false;
				}
				
				if((this.value).length > 29){
					e.preventDefault();
			        return false;	
				}
			} 
    	});
        
        $("#to-mobilenumber").keydown(function checkFirst(e){
			var char = String.fromCharCode(e.which);
			var keyCode = e.which;
			
			if (keyCode > 57) {
		        e.preventDefault();
		        return false;
		    } 
			
			if (keyCode == 32) {
		        e.preventDefault();
		        return false;
		    }
			
			if (this.selectionStart < 5 || (this.selectionStart == 5 && keyCode == 48) || 
					(this.selectionStart == 5 && keyCode == 37)) {
				this.selectionStart = 5;
		        e.preventDefault();
		        return false;
		    }
			
			if (keyCode == 8 && (this.selectionStart == 5)) {
		        e.preventDefault();
		        return false;
		    }
			
			if(char.match(/^\w$/)) {
				if((this.value == "") && (keyCode == 48)){
					e.preventDefault();
			        return false;
				}
				
				if((this.value).length > 13){
					e.preventDefault();
			        return false;	
				}
			} 
    	});
        
        $("#reg-next").click(function(){
        	$("#reg-user-info").css("left","-100%");
        	$("#reg-account-detail").css("left","0");
        	$("#reg-prev").css("background-color","#34495e");
        	$("#reg-next").css("background-color","#28b463");
        });
        
        $("#reg-prev").click(function(){
        	$("#reg-user-info").css("left","0");
        	$("#reg-account-detail").css("left","100%");
        	$("#reg-prev").css("background-color","#28b463");
        	$("#reg-next").css("background-color","#34495e");
        });
        
});		