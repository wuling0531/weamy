// JavaScript Document
var oVeri = {
		mail:function(target,num)//匹配邮箱地址
		{
			var result=target.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/); 
			
			if (result == null)
			{
				$(".error").eq(num).css("visibility","visible");
			}else {
				$(".error").eq(num).css("visibility","hidden");
			}
		},
		passsword:function(target,num)//匹配密码是否数字与字母组合
		{
			var regexp = new RegExp("(?![a-z]+$|[0-9]+$)^[a-zA-Z0-9]{7,}$");
														 
			if (regexp.test(target))
			{
				$(".error").eq(num).css("visibility","hidden");
			}else {
				$(".error").eq(num).css("visibility","visible");
			}
		},
		repeat:function(target,target2,num)//判断两次面是否一致
		{
			var oPass = $("#"+target2).val();
			if (target == oPass)
			{
				$(".error").eq(num).css("visibility","hidden");
			}else {
				$(".error").eq(num).css("visibility","visible");
			}
		},
		code:function(target,num)//判断验证码是否相等
		{
			if (target.length >= 4)
			{
				$(".error").eq(num).css("visibility","hidden");
			}else {
				$(".error").eq(num).css("visibility","visible");
			}
		},
		getVerifCode:function(target)//获取邮件验证码
		{
			var _this = $(target);
			if (_this.css("cursor") == "text") return false;
			_this.css("cursor","text");
			var timer = null;
			num = 60;
			
			timer = setInterval(function(){
				num -= 1;
				_this.val("重新获取("+num+")");		
				if (num == 0)
				{
					clearInterval(timer);
					_this.val("免费获取验证码").css("cursor","pointer");
				}
			},1000);
		},
		jump:function(target)
		{
			var _this = $(target);
			var num = 5;
			var timer = null;
			
			timer = setInterval(function(){
				num -= 1;
				_this.html(num);	
				if (num == 0)
				{
					clearInterval(timer);
					window.location = "/login";
				}
			},1000);
		}
	}















