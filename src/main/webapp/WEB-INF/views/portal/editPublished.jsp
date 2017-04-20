<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                        <img src="${wxUserInfo.headimgurl }" width="100%">
                    </div>
                </div>
                <p>${wxUserInfo.nickname }</p>
            </div>
            <ul>
                <li class="yebor" style="cursor: pointer;" onclick="javascript:location.href='/wx/m/square';">
                    <div class="po">
                        <img src="/static/mduomi/img/square-h.png">
                    </div>
                    麦哆咪广场
                </li>
                <li class="yebor" style="cursor: pointer;">
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
                <li onclick="javascript:callWxScan();" style="cursor: pointer;">
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
            <div class="btn-slide-right" onclick="javascript:callWxScan();">
                <img src="/static/mduomi/img/scanning.png" width="58" height="48">
            </div>
            我的专辑
        </h1>
    </header>
    <div class="title">
        <ul>
            <li class="dline" onclick="javascript:location.href='/wx/m/mysong';">已发布</li>
            <li class="dline" onclick="javascript:location.href='/wx/m/mynotpublish?publichStatus=1';">未发布</li>
            <li class="red">已编辑</li>
        </ul>
    </div>
    <main id="dataMainDiv"><c:choose>
        <c:when test="${fn:length(songNoPublishedDetailVOs) >0 }">
            <c:forEach items="${songNoPublishedDetailVOs }" var="songNoPublishedVo">
                <div class="hounitLI" >
                    <div class="song-box" onclick="javascript:dispatherDetailPage(${songNoPublishedVo.id});">
                        <div class="Cover">
                            <img src="${songNoPublishedVo.coverUrl }" width="100%" height="100%" />
                        </div>
                        <div class="songmi">
                            <p>${songNoPublishedVo.musicName }</p>
                        </div>
                    </div>
                    <div class="features">
                        <ul>
                            <li onclick="javascript:deleteSong(${songNoPublishedVo.id});" class="font-h">
                                <div
                                        class="icon font-h">
                                    <img src="/static/mduomi/img/del.png">
                                </div>
                                删除
                            </li>
                            <li onclick="javascript:location.href='/wx/m/editSong?baseMusicId=${songNoPublishedVo.id}&status=1';">
                                <div
                                        class="icon">
                                    <img src="/static/mduomi/img/edit.png">
                                </div>
                                编辑
                            </li>
                            <li onclick="javascript:releaseSong(${songNoPublishedVo.id});">
                                <div class="icon">
                                    <img src="/static/mduomi/img/release.png">
                                </div>
                                发布
                            </li>
                        </ul>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="albumD">周边有玻璃房，去唱歌吧</div>
        </c:otherwise>
    </c:choose></main>
    <c:if test="${pageVO.nextPage}">
        <div id="moreDiv" onclick="moreData();"
             style="width:100%;line-height: 32px;text-align: center;font-size: 80%;color: #666;">点击加载更多...
        </div>
    </c:if>
    <input type="hidden" id="curPageNoFlag" value="${pageVO.pageNo}"/>
    <input type="hidden" id="crtTotalCount" value="${fn:length(detailWithPaiseVOs)}"/>
    <input type="hidden" id="firstNextFlag" value="${pageVO.nextPage}"/>
</div>
<script type="text/javascript" src="/static/mduomi/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="/static/layer.mobile/layer/layer.js"></script>
<!-- weixin接口js -->
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
<script type="text/javascript">
    //调用微信js地址位置接口-
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
                var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                console.log(result);
            }
        });
    }

    if (!navigator.userAgent.match(/AppleWebKit.*Mobile.*/)) {
        document.getElementById('mainContent').style.width = '320px';
        document.getElementById('mainContent').className = "mainContent diyScroll";
    }

    //删除功能
    function deleteSong(musicId) {
        layer.open({
            content: '确认删除该歌曲',
            btn: [
                '删除', '取消'],
            yes: function (index) {
                location.href = '/wx/m/deleteSong?musicId=' + musicId + '&type=2';//未发布删除
            }
        });
    }
    //发布功能
    function releaseSong(musicId) {
        layer.open({
            content: '确认发布',
            btn: [
                '发布', '取消'],
            yes: function (index) {
                location.href = '/wx/m/releaseSong?musicId=' + musicId;//未发布删除
            }
        });
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
        $.getJSON('/wx/m/moreSongUnPublished', {
            pageNo: nextPageNo,
            publichStatus:2
        }, function (data, state) {
            if (state == 'success') {
                //为了看到加载中效果故加上定时器
                setTimeout(function () {
                    targetHtml = '';
                    var arrObj = data.detailWithPaiseVOs;
                    for (i = 0; i < arrObj.length; i++) {
                        targetHtml += '<div class="hounitLI">';
                        targetHtml += '<div class="song-box">';
                        targetHtml += '<div class="Cover"><img src="' + arrObj[i].coverUrl + '" width="100%" height="100%"/></div>';
                        targetHtml += '<div class="songmi"><p>' + arrObj[i].musicName + '</p><div class="tisong">' + arrObj[i].playTimes + '次</div></div>';
                        targetHtml += '</div>';
                        targetHtml += '<div class="features"><ul>';
                        targetHtml += '<li onclick="javascript:deleteSong(' + arrObj[i].id + ');" class="font-h">';
                        targetHtml += '<div class="icon font-h"><img src="/static/mduomi/img/del.png"></div>删除';
                        targetHtml += '</li>';
                        targetHtml += '<li onclick="javascript:location.href=\'/wx/m/editSong?baseMusicId=' + arrObj[i].id + '&status=1\';">';
                        targetHtml += '<div class="icon"><img src="/static/mduomi/img/edit.png"></div>编辑';
                        targetHtml += '</li>';
                        targetHtml += '<li onclick="javascript:releaseSong('+ arrObj[i].id + ');">';
                        targetHtml += '<div class="icon"><img src="/static/mduomi/img/release.png" /></div>发布';
                        targetHtml += '</li>';
                        targetHtml += '</ul></div>';
                        targetHtml += '</div>';
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

    function dispatherDetailPage(musicId) {
        $('#curPageNoFlag').val(1);
        location.href = '/wx/m/mydetail?squareMusicId=' + musicId;
    }
</script>
</body>
</html>
