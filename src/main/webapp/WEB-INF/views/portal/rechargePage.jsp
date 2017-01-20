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
							<img id="currentWXHeadPic" src="${wxUserInfo.headimgurl }" width="100%">
						</div>
					</div>
					<p>${wxUserInfo.nickname }</p>
				</div>
				<ul>
					<li class="yebor"><div class="po">
						<img src="/static/mduomi/img/square-h.png">
					</div>麦哆咪广场</li>
					<li class="yebor" onclick="javascript:location.href='/wx/m/mysong';"><div class="po">
						<img src="/static/mduomi/img/album-h.png">
					</div>我的专辑</li>
					<li class="yebor" onclick="javascript:location.href='/wx/m/myAccount';"><div class="po">
						<img src="/static/mduomi/img/album-h.png">
					</div>我的账户</li>
					<li onclick="javascript:callWxScan();"><div class="po">
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
				充值
			</h1>
		</header>
		<main>
		<form id="rechargeForm" action="/wx/m/recharge" method="post">
			<input type="hidden" name="paytype" value="2" />
			<input type="hidden" name="productId" id="rechargeProductId" />
			<div class="recharge">
				<h1>充值金额</h1>
				<ul>
					<c:forEach items="${commodityVOs }" var="commodityVO" varStatus="index">
						<li tmpProductId="${commodityVO.product_id}" onclick="changeLiBg(this);"
							class="<c:if test="${index.index ==1 }">gelone backred</c:if><c:if test="${index.index !=1 }">backglo</c:if>">${commodityVO.product_name}<br />售价${commodityVO.price}元
						</li>
					</c:forEach>
					<!-- 				<li class="gelone backred">100元<br />售价98.00元 -->
					<!-- 				</li> -->
					<!-- 				<li class="backglo">100元<br />售价98.00元 -->
					<!-- 				</li> -->
				</ul>
				<div class="amountinput">
					<input id="chargeAmount" class="inputY" name="" type="text" placeholder="请输入充值金额">
					元
				</div>
				<h2>1元=1麦币</h2>
			</div>
			<div class="theway">
				<h1>充值方式</h1>
				<div class="wxamoun">
					微信充值
					<br />
					<font>微信账户支付</font>
					<div class="Select">
						<img src="/static/mduomi/img/dg.png">
					</div>
				</div>
			</div>
		</form>
		</main>
		<div id="put-but">
			<button class="botbs " type="button" onclick="toRechargeSubmit();">确定</button>
		</div>
	</div>
	<script type="text/javascript" src="/static/mduomi/js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
		if(!navigator.userAgent.match(/AppleWebKit.*Mobile.*/)){
	        document.getElementById('mainContent').style.width = '320px';
	        document.getElementById('mainContent').className = "mainContent diyScroll";
        }

        //变更选中的li背景色
        function changeLiBg(obj) {
	        $(obj).removeClass('backglo');
	        $(obj).addClass('backred');
	        $(obj).siblings().each(function() {
		        $(this).removeClass('backred');
		        $(this).addClass('backglo');
	        });
        }

        //提交
        function toRechargeSubmit() {
	        if($('#chargeAmount').val() !== '' && $('#chargeAmount').val() > 0){
		        var chargeId = 'c' + $('#chargeAmount').val();
		        $('#rechargeProductId').val(chargeId);
	        }else{
		        var selectedVal = $('li.backred').attr('tmpProductId');
		        $('#rechargeProductId').val(selectedVal);
	        }
	        $('#rechargeForm').submit();
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
