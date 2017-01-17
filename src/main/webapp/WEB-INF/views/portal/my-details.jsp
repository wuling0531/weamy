<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>麦哆咪</title>
<link rel="stylesheet" type="text/css" href="/static/mduomi/css/css.css">
<script src="/static/mduomi/js/jquery-1.11.2.min.js"></script>
</head>
<body>
	<div id="mainContent" class="mainContent">
		<header>
			<h1>
				<div class="btn-slide-left">
					<img src="/static/mduomi/img/home-mo.png" width="58" height="48">
				</div>
				${songDetailWithPraiseVO.songDetailVO.musicName}
			</h1>
			<div class="my-music-back">
				<div class="play">
					<a href='JavaScript:void(null)'> <img id="playImg" src="/static/mduomi/img/play.png" name="rollover1" border=0
						alt="" align="middle"></a>
				</div>
				<img src="${songDetailWithPraiseVO.songDetailVO.coverUrl }" width="100%">
				<div class="player">
					<audio src="/static/mduomi/audio/seeyouagain.mp3" preload="auto" controls></audio>
					<%-- 					<audio src="${songDetailWithPraiseVO.songDetailVO.playUrl }" preload="auto" controls></audio> --%>
				</div>
			</div>
		</header>

		<main> <input type="hidden" id="baseMusicId" value="${songDetailWithPraiseVO.songDetailVO.baseMusicId }" /> <!-- 		<div class="details"> -->
		<%-- 			<h1>${songDetailWithPraiseVO.songDetailVO.musicName }</h1> --%> <!-- 			<h2> --> <!-- 				<div class="round-images"> -->
		<%-- 					<img src="${songDetailWithPraiseVO.songDetailVO.portraitUrl }" width="100%"> --%> <!-- 				</div> --> <%-- 				<p>${songDetailWithPraiseVO.songDetailVO.userName }</p> --%>
		<!-- 			</h2> --> <%-- 			<span>${songDetailWithPraiseVO.songDetailVO.board }</span> --%> <!-- 		</div> -->

		<div class="details">
			<h1>我刚唱了一首歌，快来听听吧！</h1>
			<h2>
				<div class="round-images">
					<img src="/static/mduomi/img/maiya_img_05.jpg" width="100%">
				</div>
				<p>
					<font>${songDetailWithPraiseVO.songDetailVO.userName }</font>
					<br>
					${songDetailWithPraiseVO.songDetailVO.board }
				</p>
			</h2>
			<h3>
				${songDetailWithPraiseVO.songDetailVO.playTimes }次播放
				<div class="data">
					<fmt:formatDate value="${songCreate}" pattern="yyyy-MM-dd" />
				</div>
			</h3>
			<h4>
				${songDetailWithPraiseVO.praiseNum }个赞 
<!-- 				<font>1849 条评论</font> -->
			</h4>
		</div>

		<!-- 		<div class="details-up"> --> <!-- 			<ul> --> <!-- 				<li><img src="/static/mduomi/img/love.png"></li> --> <%-- 				<li class="font-t">${songDetailWithPraiseVO.praiseNum }次</li> --%>
		<%-- 				<c:forEach items="${songDetailWithPraiseVO.praiseDetailVOs }" var="praiseDetailVO"> --%> <%-- 					<li class="po-image"><img src="${praiseDetailVO.portrait_url }" width="100%"></li> --%>
		<%-- 				</c:forEach> --%> <!-- 								<li class="po-image"><img src="img/maiya_img_05.jpg" width="100%"></li> --> <!-- 			</ul> -->
		<%-- 			<input type="hidden" id="playNum" value="${songDetailWithPraiseVO.songDetailVO.playTimes }" /> --%> <%-- 			<div id="playTimesDiv" class="tisong-up">${songDetailWithPraiseVO.songDetailVO.playTimes }次</div> --%>
		<!-- 		</div> --> <font> </font> </main>
		<font>
			<div id="menu">
				<ul>
					<li class="line"><img src="/static/mduomi/img/love-02.png"></li>
					<li class="line"><img src="/static/mduomi/img/message-02.png"></li>
					<li><img src="/static/mduomi/img/move-02.png"></li>
				</ul>
			</div>
		</font>
	</div>
	<script src="/static/mduomi/js/audioplayer.js"></script>
	<script type="text/javascript" language="JavaScript">
		//音乐播放次数的增加
        var playOnceFlag = 1;
        
        $(function() {
	        $('audio').audioPlayer();
        });
        
        /*
        	iOS浏览BUG修复
        	by @mathias, @cheeaun and @jdalton
         */
        (function(doc) {
	        var addEvent = 'addEventListener', type = 'gesturestart', qsa = 'querySelectorAll', scales = [
	                1,1], meta = qsa in doc ? doc[qsa]('meta[name=viewport]') : [];
	        function fix() {
		        meta.content = 'width=device-width,minimum-scale=' + scales[0] + ',maximum-scale=' + scales[1];
		        doc.removeEventListener(type, fix, true);
	        }
	        if((meta = meta[meta.length - 1]) && addEvent in doc){
		        fix();
		        scales = [
		                .25,1.6];
		        doc[addEvent](type, fix, true);
	        }
        }(document));
        
        if(!navigator.userAgent.match(/AppleWebKit.*Mobile.*/)){
	        document.getElementById('mainContent').style.width = '320px';
	        document.getElementById('mainContent').className = "mainContent diyScroll";
        }
	</script>
</body>
</html>
