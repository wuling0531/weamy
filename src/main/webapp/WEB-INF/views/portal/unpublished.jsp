<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="format-detection" content="telephone=no" />
		<title>麦芽</title>
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script src="http://api.map.baidu.com/api?type=quick&ak=4ae721a05484fca11cb57c1a33b98b30&v=1.0" type="text/javascript"></script>
	</head>
	<body>
		<div id="mainContent" class="mainContent">
		  <header>
            <div class="my-music-back">
			  <div class="del"><img src="img/del-mis.png"></div>
			  <div class="play">
			    <a href='JavaScript:void(null)' onClick= 'ClickImg(0)'>
			    <script language="JavaScript">
			        function ClickImg(indexedNum)  {
			        if (document.images[indexedNum].src.search("img/play.png") != -1) {
			        document.images[indexedNum].src = "img/stop.png"
			        }
			        else {
			        if (document.images[indexedNum].src.search("img/stop.png") != -1) {
			        document.images[indexedNum].src = "img/play.png"
			        }
			        }
			        }
			    </script>
			        <img src="img/play.png" name="rollover1" border=0 alt="" align="middle"></a>
			    <script language="JavaScript">
			        var checkon = new Image(30,30)
			        checkon.src = "img/stop.png"
			    </script>			  
			  </div>
			<img src="img/music_img_01.jpg" width="100%">
			  <div class="player">
			     <audio src="audio/seeyouagain.mp3" preload="auto" controls></audio>
			    <script src="js/jquery.min.js"></script> 
			    <script src="js/audioplayer.js"></script> 
			    <script>$( function() { $( 'audio' ).audioPlayer(); } );</script> 
			  </div>
			</div>
		  </header>

		  <main>
           <div class="details">
		     <h1>See You Again</h1>
			  <h2>
			    <div class="round-images"><img src="img/maiya_img_05.jpg" width="100%"></div>
				<p>P09</p>
			  </h2>		  
		    </div>

		  </main>
			<div class="buts"><button class="button" type="button">发布</button><button class="eitbut" type="button">编辑</button></div>
    </div>
	
		<script>
			(function(doc){var addEvent='addEventListener',type='gesturestart',qsa='querySelectorAll',scales=[1,1],meta=qsa in doc?doc[qsa]('meta[name=viewport]'):[];function fix(){meta.content='width=device-width,minimum-scale='+scales[0]+',maximum-scale='+scales[1];doc.removeEventListener(type,fix,true);}if((meta=meta[meta.length-1])&&addEvent in doc){fix();scales=[.25,1.6];doc[addEvent](type,fix,true);}}(document));
		</script>
		
		<script type="text/javascript">
			if(!navigator.userAgent.match(/AppleWebKit.*Mobile.*/)){
				document.getElementById('mainContent').style.width = '320px';
				document.getElementById('mainContent').className="mainContent diyScroll";
			}
		</script>
	</body>
</html>
