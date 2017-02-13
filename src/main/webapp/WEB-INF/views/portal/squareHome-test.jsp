<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jQuery手机端上拉刷新下拉加载更多页面 - 爱编程w2bc.com</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://www.w2bc.com/demo/201508/2015-08-05-jquery-pull-refresh/reset.css">
    <link rel="stylesheet" href="http://www.w2bc.com/demo/201508/2015-08-05-jquery-pull-refresh/pullToRefresh.css">
    <script src="http://www.w2bc.com/demo/201508/2015-08-05-jquery-pull-refresh/iscroll.js"></script>
    <script src="http://www.w2bc.com/demo/201508/2015-08-05-jquery-pull-refresh/pullToRefresh.js"></script>
    <script src="http://www.w2bc.com/demo/201508/2015-08-05-jquery-pull-refresh/colorful.js"></script>
    <style type="text/css" media="all">
        body, html {
            padding: 0;
            margin: 0;
            height: 100%;
            font-family: Arial, Microsoft YaHei;
            color: #111;
        }

        li {
            /*border-bottom: 1px #CCC solid;*/
            text-align: center;
            line-height: 80px;
        }
    </style>
</head>
<body>
<div id="wrapper" style="overflow: hidden;">

    <div class="scroller"
         style="transition-property: transform; transform-origin: 0px 0px 0px; transition-timing-function: cubic-bezier(0.33, 0.66, 0.66, 1); transform: translate(0px, -40px) scale(1) translateZ(0px); transition-duration: 400ms;">
        <div class="pullDown">
            <div class="loader"><span></span><span></span><span></span><span></span></div>
            <div class="pullDownLabel">Pull down to refresh...</div>
        </div>
        <ul>
            <li style="background-color: rgb(250, 105, 0);">row 10</li>
            <li style="background-color: rgb(243, 134, 48);">row 9</li>
            <li style="background-color: rgb(105, 210, 231);">row 8</li>
            <li style="background-color: rgb(224, 228, 204);">row 7</li>
            <li style="background-color: rgb(105, 210, 231);">
            </li>
            <li style="background-color: rgb(250, 105, 0);">row 5</li>
            <li style="background-color: rgb(167, 219, 216);">row 4</li>
            <li style="background-color: rgb(250, 105, 0);">row 3</li>
            <li style="background-color: rgb(224, 228, 204);">row 2</li>
            <li style="background-color: rgb(83, 119, 122);">row 1</li>
        </ul>
        <div class="pullUp">
            <div class="loader"><span></span><span></span><span></span><span></span></div>
            <div class="pullUpLabel">Pull up to load more...</div>
        </div>
    </div>
</div>
<script>
    for (var i = 0; i < document.querySelectorAll("#wrapper ul li").length; i++) {
        document.querySelectorAll("#wrapper ul li")[i].colorfulBg();
    }
//    refresher.init({
//        id: "wrapper",//<------------------------------------------------------------------------------------┐
//        pullDownAction: Refresh,
//        pullUpAction: Load
//    });

    //下拉加载更多数据
    var myScroll, targetHtml;
    var enableRefresh = true;
    //1.1 把绑定事件‘DOMContentLoaded’
    document.addEventListener('DOMContentLoaded', load(), false);
    //1.2 绑定事件‘touchmove’
    document.addEventListener('touchmove', function (e) {
        e.preventDefault();
    }, false);

    var generatedCount = 0;
    function Refresh() {
        setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
            var el, li, i;
            el = document.querySelector("#wrapper ul");
            el.innerHTML = '';
            for (i = 0; i < 11; i++) {
                li = document.createElement('li');
                li.appendChild(document.createTextNode('async row ' + (++generatedCount)));
                el.insertBefore(li, el.childNodes[0]);
            }
            wrapper.refresh();
            /****remember to refresh after  action completed！ ---yourId.refresh(); ----| ****/
            for (var i = 0; i < document.querySelectorAll("#wrapper ul li").length; i++) {
                document.querySelectorAll("#wrapper ul li")[i].colorfulBg();
            }
        }, 1000);

    }

    function moreAct() {
        setTimeout(function () {// <-- Simulate network congestion, remove setTimeout from production!
            var el, li, i;
            el = document.querySelector("#wrapper ul");
            for (i = 0; i < 2; i++) {
                li = document.createElement('li');
                li.appendChild(document.createTextNode('async row ' + (++generatedCount)));
                el.appendChild(li, el.childNodes[0]);
            }
//            wrapper.refresh();
            /****remember to refresh after action completed！！！   ---id.refresh(); --- ****/
            for (var i = 0; i < document.querySelectorAll("#wrapper ul li").length; i++) {
                document.querySelectorAll("#wrapper ul li")[i].colorfulBg();
            }
        }, 1000);
    }

    //1.3 初始化iScroll控件
    function load() {
//        wrapper
        myScroll = new iScroll('wrapper', {
            useTransition: true,
            vScrollbar: true,
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
//     				        pullUpEl.className = 'loading';
//     				        pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
                    //todo 可以出现layer加载页面
                moreAct();
//                    pullUpAction();
            }
        });
    }
</script>
</body>
</html>
