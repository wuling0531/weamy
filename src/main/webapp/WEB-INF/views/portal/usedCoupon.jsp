<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no"/>
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
                <li class="yebor" onclick="javascript:location.href='/wx/m/square';">
                    <div class="po">
                        <img src="/static/mduomi/img/square-h.png">
                    </div>
                    麦哆咪广场
                </li>
                <li class="yebor" onclick="javascript:location.href='/wx/m/mysong';">
                    <div class="po">
                        <img src="/static/mduomi/img/album-h.png">
                    </div>
                    我的专辑
                </li>
                <li class="yebor" onclick="javascript:location.href='/wx/m/myAccount';">
                    <div class="po">
                        <img src="/static/mduomi/img/acct.png">
                    </div>
                    我的账户
                </li>
                <li onclick="javascript:callWxScan();">
                    <div class="po">
                        <img src="/static/mduomi/img/scanning-h.png">
                    </div>
                    扫一扫
                </li>
            </ul>
        </div>
    </div>
    <header>
        <h1>
            <div class="btn-slide-left">
                <img src="/static/mduomi/img/home-mo.png" width="58" height="48">
            </div>
            我的优惠券
        </h1>
    </header>

    <main id="dataMainDiv">
        <div class="cutitle">
            <ul>
                <li onclick="javascript:location.href='/wx/m/myCoupon?status=1';">未使用</li>
                <li class="borter-l-r <c:if test="${param.status == 2 }">title-redcs</c:if>"
                    <c:if test="${param.status != 2 }">onclick="javascript:location.href='/wx/m/myCoupon?status=2';"</c:if>>
                    已使用
                </li>
                <li
                        <c:if test="${param.status == 3 }">class='title-redcs'</c:if>
                        <c:if test="${param.status != 3 }">onclick="javascript:location.href='/wx/m/myCoupon?status=3';"</c:if>>
                    已过期
                </li>
            </ul>
        </div>
        <c:forEach items="${couponDetailVOs }" var="couponVO">
            <div class="volume-used">
                <div class="bolbo">
                    <div class="b-title">
                            ${couponVO.name }
                        <div class="Description">${couponVO.house_id }</div>
                    </div>
                    <p>
                        兑换码：
                        <br/>
                        <font>${couponVO.no }</font>
                        <br/>
                        <fmt:formatDate value="${couponVO.time_start }" pattern="yyyy.MM.dd"/>～<fmt:formatDate
                            value="${couponVO.time_end }" pattern="yyyy.MM.dd"/>
                    </p>
                </div>
            </div>
        </c:forEach>
    </main>
    <c:if test="${pageVO.nextPage}">
        <div id="moreDiv" onclick="moreData();"
             style="width:100%;line-height: 26px;text-align: center;font-size: 80%;color: #666;">点击加载更多...
        </div>
    </c:if>
    <input type="hidden" id="curPageNoFlag" value="${pageVO.pageNo}"/>
    <input type="hidden" id="crtTotalCount" value="${fn:length(rechargeDetailVOs)}"/>
    <input type="hidden" id="firstNextFlag" value="${pageVO.nextPage}"/>
    <input type="hidden" id="couponStatus" value="${status}"/>
</div>
<script type="text/javascript" src="/static/mduomi/js/jquery-2.1.4.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
<script type="text/javascript">
    //调用微信
    wx.config({
        debug: false,
        appId: 'wx1f8a269600c90461',
        timestamp: Number('${interfaceParamVO.timestamp}'),
        nonceStr: '${interfaceParamVO.nonceStr}',
        signature: '${interfaceParamVO.signature}',
        jsApiList: [
            'checkJsApi', 'scanQRCode']
    });

    /**
     *防串货查询-条用微信‘扫一扫’接口，扫描二维码
     */
    function callWxScan() {
        wx.scanQRCode({
            needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
            scanType: [
                "qrCode"], // 可以指定扫二维码还是一维码，默认二者都有
            success: function (res) {
//                var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
//                console.log(result);
            }
        });
    }
    if (!navigator.userAgent.match(/AppleWebKit.*Mobile.*/)) {
        document.getElementById('mainContent').style.width = '320px';
        document.getElementById('mainContent').className = "mainContent diyScroll";
    }

    function toggleClassMenu() {
        if (!myMenu.classList.contains("menu--visible")) {
            myMenu.classList.add("menu--visible");
        } else {
            myMenu.classList.remove('menu--visible');
        }
    }

    var myMenu = document.querySelector(".menu");
    var oppMenu = document.querySelector(".btn-slide-left");
    oppMenu.addEventListener("click", toggleClassMenu, false);
    myMenu.addEventListener("click", toggleClassMenu, false);

    function moreData() {
        var nextPageNo = parseInt($('#curPageNoFlag').val()) + 1;
        var crtTotalCount = parseInt($('#crtTotalCount').val());
        var statusVal = $('#couponStatus').val();
        $.getJSON('/wx/m/moreCouponData', {
            pageNo: nextPageNo,
            status: statusVal
        }, function (data, state) {
            if (state == 'success') {
                //为了看到加载中效果故加上定时器
                setTimeout(function () {
                    targetHtml = '';
                    var arrObj = data.couponDetailVOs;
                    for (i = 0; i < arrObj.length; i++) {
                        targetHtml += '<div class="volume-used">';
                        <%--targetHtml += '<div class="bolbo" onclick="activateCoupon(${couponVO.no },${couponVO.house_id });">';--%>
                        targetHtml += '<div class="bolbo" onclick="activateCoupon(' + arrObj[i].no + ',' + arrObj[i].house_id + ');">';
                        targetHtml += '<div class="b-title">';
                        targetHtml += arrObj[i].name;
                        targetHtml += '<div class="Description">' + arrObj[i].house_id + '</div>';
                        targetHtml += '</div><p>兑换码：<br/><font>' + arrObj[i].no + '</font><br/>';
                        targetHtml += arrObj[i].startTimeStr + '~' + arrObj[i].endTimeStr;
                        targetHtml += '</p></div></div>';
                    }
                    $('#dataMainDiv').append(targetHtml);
                    if (data.next) {
                        $('#curPageNoFlag').val(data.currentPageNo);
                    } else {
                        $('#moreDiv').css("display", "none")
                    }
                }, 600);
            }
        });
    }

</script>
</body>
</html>
