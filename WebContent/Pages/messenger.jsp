
	<%
		if(authenticated.equalsIgnoreCase("true")){
			out.print("<div id='chat-box-open-close' style='display: block; right: 30px;'>CHAT</div>");
		} else {
			out.print("<div id='chat-box-open-close' style='display: none; right: 30px;'>CHAT</div>");
		}
	%>

		<div id="chat-control">
			<input type="text" id="input1" placeholder="chatmate"/>
			<input type="button" id="input2" value="Ping"/>
		</div>

		<section id="chat-box-container">
			<div id="recipient-picture-container">
				<div id="recipient-picture">
					<!--<img src="fotos/homepage/att-pic.jpg">-->


					<!--START CAMERA-->


					<!--
					Ideally these elements aren't created until it's confirmed that the 
					client supports video/camera, but for the sake of illustrating the 
					elements involved, they are created with markup (not JavaScript)
					-->
					<video id="recipient-picture-video" autoplay></video>
					<!--<button id="snap">Snap Photo</button>
					<canvas id="canvas" width="480" height="480"></canvas>-->


					<!--END CAMERA-->
				</div>

				<div id="chat-audio-container"><audio id="chat-audio" autoplay></audio></div>
			</div>

			<div id="chat-box-header"></div>
			<div id="chatmate">Kingsford</div>
			
			<div id="chat-box-message-body">
				<img id="chat-box-background-picture" src="image/b.jpg" alt="">

				<div id="chat-box-sender-video">
					<video id="sender-video" autoplay></video>
				</div>

				<div id="chat-box-messages-pane">
				</div>
			</div>

			<div id="chat-box-generate">
				<div id="chat-box-generate-text">
					<textarea id="chat-box-generate-textArea" placeholder="..."  maxlength="300" autofocus></textarea>
				</div>

				<div id="chat-box-generate-send">
					<div id="chat-box-send-button">
						<p>SEND</p> 
					</div>
				</div>
			</div>
		</section>