	$(document).ready(function() {
		
	setInterval(function(){ 

		if($('#welcome-images-img1').css('z-index') == '3')
		{
			$('#welcome-images-img1').fadeOut(2000, function(){
				$('#welcome-images-img2').css({'display':'block', 'z-index':'3'});
				$('#welcome-images-img3').css({'display':'block', 'z-index':'2'});
				$('#welcome-images-img1').css({'display':'block', 'z-index':'1'});
			});
		}

		else if($('#welcome-images-img2').css('z-index') == '3')
		{
			$('#welcome-images-img2').fadeOut(2000, function(){
				$('#welcome-images-img3').css({'display':'block', 'z-index':'3'});
				$('#welcome-images-img1').css({'display':'block', 'z-index':'2'});
				$('#welcome-images-img2').css({'display':'block', 'z-index':'1'});
			});
		}

		else
		{
			$('#welcome-images-img3').fadeOut(2000, function(){
				$('#welcome-images-img1').css({'display':'block', 'z-index':'3'});
				$('#welcome-images-img2').css({'display':'block', 'z-index':'2'});
				$('#welcome-images-img3').css({'display':'block', 'z-index':'1'});
			});
		}
	}, 7000);

	$("#top-video-play-button").click(function displayFullScreenVideo() {
		document.getElementById("background-video").pause();
		$("#full-screen-video-container").css("display","block");
		$("body").css("overflow",'hidden');
		$("#register").css("display","none");
		document.getElementById("full-screen-video").load();
		document.getElementById("full-screen-video").play();
	});

	$("#full-video-close-button").click(function closeFullScreenVideo() {
		document.getElementById("full-screen-video").pause();
		$("#full-screen-video-container").css("display","none");
		$("body").css("overflow",'scroll');
		document.getElementById("background-video").play();
	});

});	