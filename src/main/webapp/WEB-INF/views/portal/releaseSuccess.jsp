<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="format-detection" content="telephone=no" />
<title>麦芽</title>
<link rel="stylesheet" type="text/css" href="/static/mduomi/css/css.css">
</head>
<body>
	<div id="mainContent" class="mainContent">
		<header>
			<h1>
				<div class="btn-slide-left">取消</div>
				<div class="btn-slide-right">完成</div>
				发布
			</h1>
		</header>

		<main>
		<div class="main">
			<div class="replace-success">发布成功</div>
		</div>
		<p>点击封面可以更换，换上你喜欢的照片</p>
		</main>

		<div class="enter">
			<textarea class="text" name="" cols="" rows="">我刚唱了一首歌，快去听听吧……</textarea>
			<p>15/150</p>
		</div>
	</div>
	<script type="text/javascript">
		if(!navigator.userAgent.match(/AppleWebKit.*Mobile.*/)){
	        document.getElementById('mainContent').style.width = '320px';
	        document.getElementById('mainContent').className = "mainContent diyScroll";
        }
	</script>
</body>
</html>
