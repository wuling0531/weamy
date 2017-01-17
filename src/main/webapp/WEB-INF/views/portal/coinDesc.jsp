<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
				什么是麦币
			</h1>
		</header>

		<main>
		<div class="about">
			岗位职责
			<br>
			唱吧各个产品线的底层系统维护；
			<br>
			产品迭代的设计和架构；
			<br>
			产品模块的设计和开发；
			<br>
			编写代码，版本的快速迭代。
			<br>
			任职要求
			<br>
			本科及以上学历，三年以上移动平台开发经验；
			<br>
			熟悉C++ Java OC至少一门语言以及常用的算法和数据结构，对设计模式有一定理解，良好的面向对象编程基础有过音视频开发的经验, 音频的特效处理或者视频方面的滤镜处理；
			<br>
			熟悉ffmpeg，以及基于ffmpeg做过二次开发；
			<br>
			了解OpenGL，并熟悉OpenGL ES的简单处理；
			<br>
			了解常见音频视频编解码器；
			<br>
			熟练掌握svn/git之一的SCM工具；
			<br>
			聪明严谨，有良好的编码风格和工作习惯；
			<br>
			无障碍阅读英文文档，有独立解决未知复杂技术问题的能力；
			<br>
			懂折衷，擅沟通，有团队精神懂折衷，擅沟通，有团队精神。
			<br>
			加分项
			<br>
			曾经做过直播项目中的推流SDK或者播放器；
			<br>
			有发布的App(App Store或者其他Android平台)；
			<br>
			Github开源项目。
		</div>
		</main>
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
