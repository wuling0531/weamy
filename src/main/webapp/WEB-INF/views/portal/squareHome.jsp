<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <title>麦哆咪</title>
    <link rel="stylesheet" type="text/css" href="/static/mduomi/css/css.css">
</head>
<body>
<%--<div id="wrapper">--%>
<div id="mainContent" class="mainContent">
    <div class="menu">
        <div class="app-menu">
            <div class="mya">
                <img src="/static/mduomi/img/mya.png">
            </div>
            <div class="avatar">
                <input type="hidden" value="${wxUserInfo.openid }"/>
                <div class="roundPO">
                    <div class="poimg">
                        <img id="currentWXHeadPic" src="${wxUserInfo.headimgurl }" width="100%">
                    </div>
                </div>
                <p>${wxUserInfo.nickname }</p>
            </div>
            <ul>
                <li class="yebor">
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
                        <img src="/static/mduomi/img/album-h.png">
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
            <div class="btn-slide-right" onclick="javascript:callWxScan();">
                <img src="/static/mduomi/img/scanning.png" width="58" height="48">
            </div>
            麦哆咪广场
        </h1>
    </header>
    <main id="dataMainDiv"><c:forEach items="${detailWithPaiseVOs }" var="detailWithPaiseVO" varStatus="index">
        <div class="hounitLI">
            <h1>
                <div class="round-image">
                    <img src="${detailWithPaiseVO.songDetailVO.portrait_url }" width="100%">
                </div>
                <p>${detailWithPaiseVO.songDetailVO.user_name}</p>
                <div class="homeDI">${detailWithPaiseVO.songDetailVO.relative }</div>
            </h1>
            <div class="song-box"
                 onclick="javascript:location.href='/wx/m/mydetail?squareMusicId=${detailWithPaiseVO.songDetailVO.music_id}';">
                <div class="Cover">
                    <img src="${detailWithPaiseVO.songDetailVO.cover_url }" width="100%">
                </div>
                <div class="songmi">
                    <p>${detailWithPaiseVO.songDetailVO.music_name }</p>
                    <div class="tisong">${detailWithPaiseVO.songDetailVO.play_times }次</div>
                </div>
            </div>
            <ul id="praiseDis_${index.index }">
                <input type="hidden" id="praise_info_${index.index }" value="${detailWithPaiseVO.praiseNum }"/>
                <input type="hidden" id="praise_operate_${index.index }" value="1"/>
                <li><img onclick="addPraiseTimes(${detailWithPaiseVO.songDetailVO.music_id },${index.index });"
                         src="/static/mduomi/img/love.png"></li>
                <li class="font-t" id="praiseTimes_${index.index }">${detailWithPaiseVO.praiseNum }次</li>
                <c:forEach items="${detailWithPaiseVO.praiseDetailVOs  }" var="praiseDetailVO">
                    <li class="po-image"><img src="${praiseDetailVO.portrait_url }" width="100%"></li>
                </c:forEach>
                <!-- 					<li class="po-image"><img src="/static/mduomi/img/maiya_img_05.jpg" width="100%"></li> -->
            </ul>
            <span>${detailWithPaiseVO.songDetailVO.board }</span>
        </div>
    </c:forEach>
    </main>
    <c:if test="${pageVO.nextPage}">
        <div id="moreDiv" onclick="moreData();"
             style="width:100%;line-height: 26px;text-align: center;font-size: 80%;color: #666;">点击加载更多...
        </div>
    </c:if>
    <input type="hidden" id="curPageNoFlag" value="${pageVO.pageNo}"/>
    <input type="hidden" id="crtTotalCount" value="${fn:length(detailWithPaiseVOs)}"/>
    <input type="hidden" id="firstNextFlag" value="${pageVO.nextPage}"/>
</div>
<%--</div>--%>
<script type="text/javascript" src="/static/mduomi/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="/static/jquery/iscroll-4.2.5.js"></script>
<script type="text/javascript" src="/static/jquery/pullToRefresh.js"></script>
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

    //增加点赞次数
    function addPraiseTimes(musicId, index) {
        if ($('#praise_operate_' + index).val() == 1) {
            $.ajax({
                url: "/wx/m/addPraiseTimes",
                data: {squareMusicId: musicId},
                success: function (stateVO) {
                    if (stateVO.code != -1) {
                        //增加头像
                        $('#praiseDis_' + index).append('<li class="po-image"><img src="' + $('#currentWXHeadPic').attr('src') + '" width="100%"></li>');
                        $('#praiseTimes_' + index).empty().append((Number($('#praise_info_' + index).val()) + 1) + '次');
                        $('#praise_operate_' + index).val(2)
                    }
                }
            });
        }
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
        $.getJSON('/wx/m/nextSquareData', {
            pageNo: nextPageNo
        }, function (data, state) {
            if (state == 'success') {
                //为了看到加载中效果故加上定时器
                setTimeout(function () {
                    targetHtml = '';
                    var arrObj = data.detailWithPaiseVOs;
                    for (i = 0; i < arrObj.length; i++) {
                        targetHtml += '<div class="hounitLI">';
                        targetHtml += '<h1><div class="round-image"><img src="' + arrObj[i].songDetailVO.portrait_url + '" width="100%"></div>';
                        targetHtml += '<p>' + arrObj[i].songDetailVO.user_name + '</p>';
                        targetHtml += '<div class="homeDI">' + arrObj[i].songDetailVO.relative + '</div></h1>';
                        targetHtml += '<div class="song-box" onclick="javascript:location.href=\'/wx/m/mydetail?squareMusicId=' + arrObj[i].songDetailVO.music_id + '\';">';
                        targetHtml += '<div class="Cover"><img src="' + arrObj[i].songDetailVO.cover_url + '" width="100%" /></div>';
                        targetHtml += '<div class="songmi"><p>' + arrObj[i].songDetailVO.music_name + '</p><div class="tisong">' + arrObj[i].songDetailVO.play_times + '次</div></div>';
                        targetHtml += '</div>';
                        targetHtml += '<ul id="praiseDis_' + (crtTotalCount) + '">';
                        targetHtml += '<input type="hidden" id="praise_info_' + crtTotalCount + '" value="' + arrObj[i].praiseNum + '" />';
                        targetHtml += '<input type="hidden" id="praise_operate_' + crtTotalCount + '" value="1" />';
                        targetHtml += '<li><img onclick="addPraiseTimes(' + arrObj[i].songDetailVO.music_id + ',' + crtTotalCount + ');" src="/static/mduomi/img/love.png"></li>';
                        targetHtml += '<li class="font-t" id="praiseTimes_' + crtTotalCount + '">' + arrObj[i].praiseNum + '次</li>';
                        for (j = 0; j < arrObj[i].praiseDetailVOs.length; j++) {
                            targetHtml += '<li class="po-image"><img src="' + arrObj[i].praiseDetailVOs[j].portrait_url + '" width="100%"></li>';
                        }
                        targetHtml += '</ul>';
                        targetHtml += '<span>' + arrObj[i].songDetailVO.board + '</span>';
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

    //下拉加载更多数据
    //    var myScroll, targetHtml;
    //    var enableRefresh = true;
    //1.1 把绑定事件‘DOMContentLoaded’
    //    document.addEventListener('DOMContentLoaded', load(), false);
    //    //1.2 绑定事件‘touchmove’
    //    document.addEventListener('touchmove', function (e) {
    //        e.preventDefault();
    //    }, false);

    function pullUpAction() {
        var nextPageNo = parseInt($('#curPageNoFlag').val()) + 1;
        var crtTotalCount = parseInt($('#crtTotalCount').val());
        $.getJSON('/wx/m/nextSquareData', {
            pageNo: nextPageNo
        }, function (data, state) {
            if (state == 'success') {
                //为了看到加载中效果故加上定时器
                setTimeout(function () {
                    targetHtml = '';
                    var arrObj = data.detailWithPaiseVOs;
                    for (i = 0; i < arrObj.length; i++) {
                        targetHtml += '<div class="hounitLI">';
                        targetHtml += '<h1><div class="round-image"><img src="' + arrObj[i].songDetailVO.portrait_url + '" width="100%"></div>';
                        targetHtml += '<p>' + arrObj[i].songDetailVO.user_name + '</p>';
                        targetHtml += '<div class="homeDI">' + arrObj[i].songDetailVO.relative + '</div></h1>';
                        targetHtml += '<div class="song-box" onclick="javascript:location.href=\'/wx/m/mydetail?squareMusicId=' + arrObj[i].songDetailVO.music_id + '\';">';
                        targetHtml += '<div class="Cover"><img src="' + arrObj[i].songDetailVO.cover_url + '" width="100%" /></div>';
                        targetHtml += '<div class="songmi"><p>' + arrObj[i].songDetailVO.music_name + '</p><div class="tisong">' + arrObj[i].songDetailVO.play_times + '次</div></div>';
                        targetHtml += '</div>';
                        targetHtml += '<ul id="praiseDis_' + (crtTotalCount) + '">';
                        targetHtml += '<input type="hidden" id="praise_info_' + crtTotalCount + '" value="' + arrObj[i].praiseNum + '" />';
                        targetHtml += '<input type="hidden" id="praise_operate_' + crtTotalCount + '" value="1" />';
                        targetHtml += '<li><img onclick="addPraiseTimes(' + arrObj[i].songDetailVO.music_id + ',' + crtTotalCount + ');" src="/static/mduomi/img/love.png"></li>';
                        targetHtml += '<li class="font-t" id="praiseTimes_' + crtTotalCount + '">' + arrObj[i].praiseNum + '次</li>';
                        for (j = 0; j < arrObj[i].praiseDetailVOs.length; j++) {
                            targetHtml += '<li class="po-image"><img src="' + arrObj[i].praiseDetailVOs[j].portrait_url + '" width="100%"></li>';
                        }
                        targetHtml += '</ul>';
                        targetHtml += '<span>' + arrObj[i].songDetailVO.board + '</span>';
                        targetHtml += '</div>';
                    }
                    $('#dataMainDiv').append(targetHtml);
                    myScroll.refresh();
                    if (data.next) {
                        $('#curPageNoFlag').val(data.currentPageNo);
                    } else {
//     					    $('#pullUp').hide();
                        enableRefresh = false;
                    }
                }, 600);
            }
        });
    }

    //1.3 初始化iScroll控件
    function load() {
        myScroll = new iScroll('wrapper', {
            useTransition: true,
            vScrollbar: false,
            onRefresh: function () {
                //todo 关闭layer的加载层
//     			        if(pullUpEl.className.match('loading')){
//     				        pullUpEl.className = '';
//     				        pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
//     			        }
            },
            onScrollMove: function () {
                if (enableRefresh && this.y < (this.maxScrollY - 5)) {
//     				        pullUpEl.className = 'flip';
//     				        pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
                }
            },
            onScrollEnd: function () {
//     			        if(enableRefresh && pullUpEl.className.match('flip')){
                if (enableRefresh && $('#firstNextFlag').val()) {
//     				        pullUpEl.className = 'loading';
//     				        pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
                    //todo 可以出现layer加载页面
                    pullUpAction();
                }
            }
        });
    }
</script>
</body>
</html>
