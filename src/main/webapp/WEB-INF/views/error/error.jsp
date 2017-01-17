<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>错误页面</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- normalize是可以定制的CSS文件，它让不同的浏览器在渲染网页元素的时候形式更统一。 -->
<link rel="stylesheet" href="/static/weixin/css/normalize.css">
<!-- Boilerplate: 最流行的web开发前端模版 -->
<link rel="stylesheet" href="/static/weixin/css/main.css">
<!-- jquey-modal-css -->
<link rel="stylesheet" type="text/css" href="/static/weixin/css/jquery.modal.css" media="screen" />
<!-- 自定义样式-css -->
<link rel="stylesheet" href="/static/weixin/css/base.css">
</head>
<body>
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
	<div class="container-white">
		<div class="infobox-top">
			<div class="avatar-imgB">
				<img src="/static/weixin/img/roimg.png" alt="">
			</div>
			<c:choose>	
				<c:when test="${acceptFlag == 9}">
			<div class="text-labelB">${stateVO.msg}</div>
				</c:when>
				<c:otherwise>
			<div class="text-labelB">对不起！页面加载失败，请重试。</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>