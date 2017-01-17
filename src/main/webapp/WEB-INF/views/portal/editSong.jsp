<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script language="javascript" type="text/javascript">
	function showDiv() {
	    document.getElementById('maskLayer').style.display = 'block';
	    document.getElementById('popWindow').style.display = 'block';
    }
    function closeDiv() {
	    document.getElementById('maskLayer').style.display = 'none';
	    document.getElementById('popWindow').style.display = 'none';
    }
</script>
<body>
	<div id="mainContent" class="mainContent">
		<form id="editSub" action="/wx/m/editSongSubmit" method="post">
			<input type="hidden" id=subType name="subType" />
			<input type="hidden" id="baseMusicId" name="baseMusicId" value="${baseMusicId }" />
			<header>
				<!-- 				<h1> -->
				<!-- 					<div class="btn-slide-left" onclick="javascript:window.history.back();">取消</div> -->
				<!-- 					<div onclick="javascript:submitFun(1);" class="btn-slide-right">完成</div> -->
				<!-- 					<span onclick="javascript:submitFun(2);" style="display: inline-block; width: 60px; height: 48px;">发布</span> -->
				<!-- 				</h1> -->
				<h1>
					<div class="btn-slide-left">
						<img src="/static/mduomi/img/home-mo.png" width="58" height="48">
					</div>
					发布
				</h1>
			</header>
			<main>
			<div id="picDiv" class="main"
				<c:if test="${!empty songDetailVO.coverUrl}">style="background-image:url(${songDetailVO.coverUrl })"</c:if>>
				<input name="coverPicUrl" value="" id="bgImg" type="hidden" />
				<div class="replace-back" onClick="showDiv();" style="display: block; cursor: pointer">封面</div>
			</div>
			<p>点击封面可以更换，换上你喜欢的照片</p>
			</main>

			<div class="enter">
				<textarea class="text" name="boardStr" cols="" rows="" onkeyup="javascript:disTextCount(this);"
					onmousedown="javascript:disTextCount(this);" maxlength="150">${empty songDetailVO.board?'我刚唱了一首歌，快去听听吧……':songDetailVO.board }</textarea>
				<p id="txtNum">
					<fmt:formatNumber type="number" value="${fn:length(songDetailVO.board)/2 }" maxFractionDigits="0" pattern="#" />
					/150
				</p>
			</div>
			<div class="buts">
				<button class="button" type="button" onclick="javascript:submitFun(2);">发布</button>
				<button class="eitbut" type="button" onclick="javascript:submitFun(1);">保存</button>
			</div>
		</form>
	</div>

	<div id="maskLayer" class="maskLayer" style="display: none;">
		<input type="hidden" id="redirectVal" value="${picRedirect }" />
		<div class="botlo takePhoto">
			<input id="takeId" name="takeId" class="picupload" type="file" capture="camera" accept="image/*"
				onchange="ajaxFileUpload('takeId');" />
		</div>
		<div class="botlo distPhoto">
			<input id="objData" name="objData" class="picupload" type="file" accept="image/*"
				onchange="ajaxFileUpload('objData');" />
		</div>
		<div class="botlo-h">
			<a href="javascript:void(0);" onClick="closeDiv()" style="cursor: pointer; text-decoration: none;">取消</a>
		</div>
	</div>
	<div id="popWindow" class="popWindow" style="display: none;"></div>
	<!-- 	<script type="text/javascript" src="/static/mduomi/js/jquery-2.1.4.min.js"></script> -->
	<script src="/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="/static/jquery/jquery-form.js"></script>
	<script type="text/javascript" src="/static/jquery/ajaxfileupload.js"></script>
	<script type="text/javascript">
		function submitFun(type) {
	        $('#subType').val(type);//type=1,编辑提交；type=2，发布提交
	        $('#editSub').submit();
        }

        function ajaxFileUpload(objId) {
	        var redirectUrlVal = $('#redirectVal').val();
	        $.ajaxFileUpload({
	            url : 'http://101.201.41.109/maemy/api/upload/coverUrl',
	            secureuri : false,
	            fileElementId : objId,
	            data : {
		            redirectUrl : redirectUrlVal},
	            dataType : 'json',
	            success : function(json, status) {
		            closeDiv();//
		            $('#picDiv').css('background-image', 'url(' + json.data + ')');
		            $('#bgImg').val(json.data);
	            },
	            error : function()//服务器响应失败处理函数
	            {
		            alert("图片上传错误，请重新上传");
	            }});
	        return false;
        }

        //显示汉字字数(一个汉字设为两个字符)
        function disTextCount(obj) {
	        // 	        var currentNum = $(obj).val().length;
	        var str = obj.value;
	        myLen = getStrleng(str);
	        var currentNum = Math.floor(myLen / 2);
	        $('#txtNum').text(currentNum + '/150');
        }

        function getStrleng(str) {
	        myLen = 0;
	        i = 0;
	        for (; i < str.length; i++){
		        if(str.charCodeAt(i) > 0 && str.charCodeAt(i) < 128)
			        myLen++;
		        else
			        myLen += 2;
	        }
	        return myLen;
        }

        if(!navigator.userAgent.match(/AppleWebKit.*Mobile.*/)){
	        document.getElementById('mainContent').style.width = '320px';
	        document.getElementById('mainContent').className = "mainContent diyScroll";
        }
	</script>
</body>
</html>
