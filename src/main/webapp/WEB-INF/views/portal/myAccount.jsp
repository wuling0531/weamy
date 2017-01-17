<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<div class="menu">
			<div class="app-menu">
				<div class="mya">
					<img src="/static/mduomi/img/mya.png">
				</div>
				<div class="avatar">
					<div class="roundPO">
						<div class="poimg">
							<img src="${wxUserInfo.headimgurl }" width="100%">
						</div>
					</div>
					<p>${wxUserInfo.nickname }</p>
				</div>
				<ul>
					<li class="yebor" style="cursor: pointer;" onclick="javascript:location.href='/wx/m/square';"><div class="po">
							<img src="/static/mduomi/img/square-h.png">
						</div>麦哆咪广场</li>
					<li class="yebor" style="cursor: pointer;"><div class="po" onclick="javascript:location.href='/wx/m/mysong';">
							<img src="/static/mduomi/img/album-h.png">
						</div>我的专辑</li>
					<li class="yebor"><div class="po">
							<img src="/static/mduomi/img/album-h.png">
						</div>我的账户</li>
					<li onclick="javascript:callWxScan();" style="cursor: pointer;"><div class="po">
							<img src="/static/mduomi/img/scanning-h.png">
						</div>扫一扫</li>
				</ul>
			</div>
		</div>
	
		<header>
			<h1>
				<div class="btn-slide-left">
					<img src="/static/mduomi/img/home-mo.png" width="58" height="48">
				</div>
				我的帐户
			</h1>
		</header>

		<main>
		<div class="poster">
			<img src="/static/mduomi/img/haibao.jpg" width="100%">
		</div>
		<div class="User-info">
			<div class="user-image">
				<img src="${accountInfoVO.headimgurl }" width="100%">
			</div>
			<p>您好，${accountInfoVO.nickname }</p>
		</div>
		<div class="tails">
			<ul>
				<li>当前麦币(个)<br /> <font>${accountInfoVO.count }</font></li>
				<li onclick="javascript:location.href='/wx/m/myCoupon?status=1';">我的优惠券(张)<br /> <font><fmt:formatNumber value="${accountInfoVO.sum }" pattern="#" /> </font></li>
			</ul>
		</div>
		</main>
		<div id="tails-but">
			<button class="botbs " type="button" onclick="javascript:location.href='/wx/m/rechargePage';">充值</button>
			<p>
				<a href="/wx/m/rechargeRecord">充值记录</a> | <a href="/wx/m/coinDesc">什么是麦币</a>
			</p>
		</div>
	</div>
	<script type="text/javascript">
		if(!navigator.userAgent.match(/AppleWebKit.*Mobile.*/)){
	        document.getElementById('mainContent').style.width = '320px';
	        document.getElementById('mainContent').className = "mainContent diyScroll";
        }
		
		 function toggleClassMenu() {
		        if(!myMenu.classList.contains("menu--visible")){
			        myMenu.classList.add("menu--visible");
		        }else{
			        myMenu.classList.remove('menu--visible');
		        }
	        }

	        var myMenu = document.querySelector(".menu");
	        var oppMenu = document.querySelector(".btn-slide-left");
	        oppMenu.addEventListener("click", toggleClassMenu, false);
	        myMenu.addEventListener("click", toggleClassMenu, false);
	</script>
</body>
</html>
