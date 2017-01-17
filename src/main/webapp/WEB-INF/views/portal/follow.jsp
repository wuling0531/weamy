<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="format-detection" content="telephone=no" />
<title>麦哆咪</title>
<link rel="stylesheet" type="text/css" href="/static/mduomi/css/css.css">
</head>
<body>
	<div id="mainContent" class="mainContent">
		<header></header>
		<main>
		<div class="code-main">
			<img src="/static/mduomi/img/1480644953.png" width="100%">
		</div>
		<span class="code-fontA">长按二维码，关注我们</span> <span class="code-fontB">关注后，在公众号内可发布歌曲</span> </main>
	</div>
	<div id="maskLayer" class="maskLayer" style="display: none;">
		<div class="botlo">拍照</div>
		<div class="botlo">本地上传</div>
		<div class="botlo-h">
			<a href="#" onClick="closeDiv()" style="cursor: pointer; text-decoration: none;">取消</a>
		</div>
	</div>
	<div id="popWindow" class="popWindow" style="display: none;"></div>
	<script type="text/javascript">
		if(!navigator.userAgent.match(/AppleWebKit.*Mobile.*/)){
	        document.getElementById('mainContent').style.width = '320px';
	        document.getElementById('mainContent').className = "mainContent diyScroll";
        }
	</script>
</body>
</html>
