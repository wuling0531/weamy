<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
					<li class="yebor" style="cursor: pointer;" onclick="javascript:location.href='/wx/m/mysong';"><div class="po">
							<img src="/static/mduomi/img/album-h.png">
						</div>我的专辑</li>
					<li class="yebor" onclick="javascript:location.href='/wx/m/myAccount';"><div class="po">
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
				充值记录
			</h1>
		</header>

		<main> <c:forEach items="${rechargeDetailVOs }" var="rechargeVO">
			<c:choose>
				<c:when test="${rechargeVO.type == 2 }">
					<div class="titleB">
						<fmt:formatDate value="${rechargeVO.create_time }" pattern="yyyy年MM日" />
						<div class="t-number">
							消费：¥<fmt:formatNumber value="${rechargeVO.money }" pattern="#.##" />
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="r-record">
						<c:choose>
							<c:when test="${rechargeVO.pay_type == 1}">微信充值</c:when>
							<c:otherwise>支付宝充值</c:otherwise>
						</c:choose>
						<br />
						<font><c:choose>
							<c:when test="${rechargeVO.status == 1}">支付成功</c:when>
							<c:when test="${rechargeVO.status == 0}">未支付</c:when>
							<c:otherwise>支付失败</c:otherwise>
						</c:choose></font>
						<br />
						<span><fmt:formatDate value="${rechargeVO.create_time }" pattern="yyyy-MM-dd" /></span>
						<div class="mount">
							¥<fmt:formatNumber value="${rechargeVO.money }" pattern="#.##" />
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</c:forEach> </main>
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
