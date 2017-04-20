<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>麦哆咪</title>
<link rel="stylesheet" type="text/css" href="/static/mduomi/css/css.css">
<style type="text/css">
/* .addWrap{ position:relative; width:100%;background:#fff;margin:0; padding:0;} */
.swipe {
	overflow: hidden;
	visibility: hidden;
	position: relative;
}

.swipe-wrap {
	overflow: hidden;
	position: relative;
}

.swipe-wrap>div {
	float: left;
	width: 100%;
	position: relative;
}

.swipe-wrap>div img {
	width: 100%;
}
</style>
</head>
<body>
	<div id="mainContent" class="mainContent">
		<header>
			<div class="swipe" id="mySwipe">
				<div class="swipe-wrap">
					<c:forEach items="${activityListDataVO.articleMap }" var="articleVO">
						<div>
							<img src="${articleVO.pic_url }" width="100%">
						</div>
					</c:forEach>
					<!-- 			<img src="/static/mduomi/img/maiya_img_02.jpg" width="100%"> -->
				</div>
			</div>
		</header>
		<main>
		<div class="activityMAIN">
			<%--<ul>--%>
				<%--<c:forEach items="${activityListDataVO.baseMusicInfoMap }" var="baseMusicVO">--%>
					<%--<li><div class="img">--%>
							<%--<img src="${baseMusicVO.singer_cover }" width="100%">--%>
						<%--</div>--%>
						<%--<div class="font">${baseMusicVO.music_name }</div>--%>
						<%--<div class="Gname">${baseMusicVO.singer }</div></li>--%>
				<%--</c:forEach>--%>
				<%--<!-- 				<li><div class="img"> -->--%>
				<%--<!-- 						<img src="/static/mduomi/img/Avatar.jpg" width="100%"> -->--%>
				<%--<!-- 					</div> -->--%>
				<%--<!-- 					<div class="font">2infinity And Beyond</div> -->--%>
				<%--<!-- 					<div class="Gname">林俊杰</div></li> -->--%>
			<%--</ul>--%>
		</div>
		</main>

	</div>
	<script type="text/javascript" src="/static/mduomi/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="/static/jquery/swipe.js"></script>
	<script type="text/javascript">
		$(function() {
	        Swipe(document.getElementById('mySwipe'), {
	            continuous : true,
	            disableScroll : false,
	            callback : function(pos) {
	            // 	            var i = bullets.length;
	            // 	            while (i--){
	            // 		            bullets[i].className = 'whited';
	            // 	            }
	            // 	            bullets[pos].className = 'heid';
	            }});
        });
        
        if(!navigator.userAgent.match(/AppleWebKit.*Mobile.*/)){
	        document.getElementById('mainContent').style.width = '320px';
	        document.getElementById('mainContent').className = "mainContent diyScroll";
        }
	</script>
</body>
</html>
