<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
				<div class="btn-slide-right" onclick="javascript:callWxScan();">
					<img src="/static/mduomi/img/scanning.png" width="58" height="48">
				</div>
				我的专辑
			</h1>
		</header>
		<div class="title">
			<ul>
				<li class="dline red">已发布</li>
				<li class="dline" onclick="javascript:location.href='/wx/m/mynotpublish?publichStatus=1';">未发布</li>
				<li onclick="javascript:location.href='/wx/m/mynotpublish?publichStatus=2';">已编辑</li>
			</ul>
		</div>
		<main> <c:choose>
			<c:when test="${fn:length(detailWithPraiseVOs) >0 }">
				<c:forEach items="${detailWithPraiseVOs }" var="songWithPraiseVo">
					<div class="hounitLI">
						<div class="song-box">
							<div class="Cover">
								<img src="${songWithPraiseVo.songDetailVO.cover_url }" width="100%">
							</div>
							<div class="songmi">
								<p>${songWithPraiseVo.songDetailVO.music_name }</p>
								<div class="tisong">${songWithPraiseVo.songDetailVO.play_times }次</div>
							</div>
						</div>
						<ul>
							<li><img src="/static/mduomi/img/love.png"></li>
							<li class="font-t">${songWithPraiseVo.praiseNum}次</li>
							<c:forEach items="${songWithPraiseVo.praiseDetailVOs  }" var="praiseDetailVO">
								<li class="po-image"><img src="${praiseDetailVO.portrait_url }" width="100%"></li>
							</c:forEach>
						</ul>
						<span>${songWithPraiseVo.songDetailVO.board }</span>
						<dd>${songWithPraiseVo.songDetailVO.relative }</dd>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<div class="albumD">您还没有发布属于您的专辑</div>
			</c:otherwise>
		</c:choose> </main>
	</div>
	<script type="text/javascript" src="/static/mduomi/js/jquery-2.1.4.min.js"></script>
	<!-- weixin接口js -->
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
	<script type="text/javascript">
		//调用微信js地址位置接口-
        wx.config({
            debug : false,
            appId : 'wx1f8a269600c90461',
            timestamp : Number('${interfaceParamVO.timestamp}'),
            nonceStr : '${interfaceParamVO.nonceStr}',
            signature : '${interfaceParamVO.signature}',
            jsApiList : [
                    'checkJsApi','scanQRCode']});
        
        /**
         *防串货查询-条用微信‘扫一扫’接口，扫描二维码
         */
        function callWxScan() {
	        wx.scanQRCode({
	            needResult : 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
	            scanType : [
		            "qrCode"], // 可以指定扫二维码还是一维码，默认二者都有
	            success : function(res) {
		            var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
		            console.log(result);
	            }});
        }
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
