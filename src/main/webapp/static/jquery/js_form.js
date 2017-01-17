// JavaScript Document
var iKey = 0;
$(document).click(function(){
	$(".select_btn_box").hide();
	iKey = 0;
});

//事件冒泡
function eventBubbling(ev){
	//当前时间是否停止传播?
	if (ev.stopPropagation){
		ev.stopPropagation();
	}else {
		ev.cancelBubble = true;	
	}
}

//按键监听
//搜索框-智能提示功能
var iNd = 0;
$(document).keydown(function(event){
	$(".select_btn_box").each(function(){
		//当前元素的display状态为‘BLOCK’
		if ($(this).css("display") == "block"){ 
			$(this).children().each(function(){
				if ($(this).attr("class") == "select_active"){
					iNd = $(this).index();
					if (event.keyCode == 13)
					{
						$(this).parent().css("display","none").siblings().html($(this).html());
					}
					if (event.keyCode == 38)
					{
						if (iNd == 0)
						{
							iNd = $(this).parent().children().length;
							$(this).parent().children().eq(iNd-1).addClass("select_active").siblings().removeClass("select_active");	
							return false;
						}else {
							$(this).parent().children().eq(iNd-1).addClass("select_active").siblings().removeClass("select_active");
							return false;
						}
					}else if (event.keyCode == 40){
						if (iNd == $(this).parent().children().length-1)
						{
							iNd = 0;
							$(this).parent().children().eq(iNd).addClass("select_active").siblings().removeClass("select_active");
							return false;
						}else {
							$(this).parent().children().eq(iNd+1).addClass("select_active").siblings().removeClass("select_active");
							return false;
						}
					}
				}
			});
			return false;
		}
	});
});

//禁止‘方向键’作用
if (window.addEventListener) {
	 document.addEventListener("keydown", function(e) {
		 var keyCode = e.keyCode;
		 if (keyCode >= 37 && keyCode <= 40) {
			 //规定阻止哪个事件的默认动作,即在按‘方向键’时，不起作用
			e.preventDefault();
		 }
	 });
 } else if (window.attachEvent) {
	 document.attachEvent("onkeydown", function(e) {
		 var keyCode = e.keyCode;
		 if (keyCode >= 37 && keyCode <= 40) {
			e.returnValue = false;
		 }
	});
 }

function showSelect(target,event)
{
	if ($(target).siblings(".select_btn_box").css("display") == "none")
	{
		 $(".select_btn_box").css("display","none");
		 $(target).siblings(".select_btn_box").children().eq(0).addClass("select_active").siblings().removeClass("select_active");
		 $(target).siblings(".select_btn_box").css("display","block");
	}else {
		$(target).siblings(".select_btn_box").css("display","none");		
	}
	eventBubbling(event);
}

function clickSelect(target,event)//��ֵ��DIV
{
	$(target).addClass("select_active").siblings().removeClass("select_active").parent().hide().siblings(".select_box").html($(target).html());	
	if (event.stopPropagation)
	eventBubbling(event);
}

function showSelect2(target,event)//�ı�ֵ�ı�ʱ��ʵ������
{
	$(".select_btn_box").css("display","none");
	 $(target).siblings(".select_btn_box").children().eq(0).addClass("select_active").siblings().removeClass("select_active");
	 $(target).siblings(".select_btn_box").css("display","block");
	eventBubbling(event);
	var nCode = event.keyCode;
	var oSear = $("#select_btn_box").children();
	
	if (nCode == 13)
	{
		$(target).val($("#select_btn_box").css("display","none").children().eq(iKey).html());
		iKey = 0;
		return false;
	}
	if (nCode == 38)
	{
		if (iKey == 0)
		{
			iKey = $("#select_btn_box").children().length-1;
			oSear.eq(iKey).addClass("select_active").siblings().removeClass("select_active");	
			return false;
		}else {
			iKey -= 1;
			oSear.eq(iKey).addClass("select_active").siblings().removeClass("select_active");
			return false;
		}
	}else if (nCode == 40){
		if (iKey == oSear.length-1)
		{
			iKey = 0;
			oSear.eq(iKey).addClass("select_active").siblings().removeClass("select_active");
			return false;
		}else {
			iKey += 1;
			oSear.eq(iKey).addClass("select_active").siblings().removeClass("select_active");
			return false;
		}
	}
}

function clickSelect2(target,event)//��ֵ��input
{
	$(target).addClass("select_active").siblings().removeClass("select_active").parent().hide().siblings(".select_box").val($(target).html());	
	if (event.stopPropagation)
	eventBubbling(event);
}

function hoverSelect(target)
{
	$(target).addClass("select_active").siblings().removeClass("select_active");
}

//�۵���ҵ����?
function clickFoldTable(target)
{
	if ($(target).attr("class") == "jia")
	{
		$(target).removeClass("jia").parent().next().show();			
	}else {
		$(target).addClass("jia").parent().next().hide();	
	}
}

function fillSpaces(target,ev)//���ո�
{
	var oEv = ev || window.event;
	if (($(target).val().length == 3 || $(target).val().length == 7 || $(target).val().length == 11) && oEv.keyCode != 8)
	{
		$(target).val($(target).val()+" ");
	}
}

//������
function showLayer()
{
	$(".layer_back_box").css("display","block");	
	$(".layer_box").css("display","block");	
}

function hideLayer()
{
	$(".layer_back_box").css("display","none");	
	$(".layer_box").css("display","none");	
}

function clearTextValue(target,ovalue)
{
	if ($(target).val() == ovalue)
	{
		$(target).val("").css("color","#333");
	}
}
function addTextValue(target,ovalue)
{
	if ($(target).val() == "")
	{
		$(target).val(ovalue).css("color","#ccc");
	}
}
//���ӹɶ�
function increaseShareholder(target)
{
	if (confirm("�Ƿ�����һ�ݡ���˾��Ȩ�ṹ��Ϣ����"))
	{
		var oTa = $(".td_length").length+1;
		var oTrElement = '<tr><td class="td_length" colspan="4"><strong>�ɶ�'+oTa+'</strong></td></tr><tr><td width="125">����</td><td class="td_border"><div class="form_box"><input type="text" /></div></td><td width="125">����*</td><td><div class="form_select_box"><div class="form_box select_box" onclick="showSelect(this,event)"></div>	<div class="select_btn_box"><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">10�� - 99��</a><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">100�� - 199��</a><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">200�� - 299��</a><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">300������</a></div></div></td></tr><tr><td>�Ƿ�Ϊ���й�˾*</td><td class="td_border"><div class="form_select_box" style="z-index:99;"><div class="form_box select_box" onclick="showSelect(this,event)"></div>	<div class="select_btn_box"><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">10�� - 99��</a><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">100�� - 199��</a><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">200�� - 299��</a><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">300������</a></div></div></td><td colspan="2">&nbsp;</td></tr><tr><td>���ʷ�ʽ*</td><td class="td_border"><div class="form_select_box" style="z-index:98;"><div class="form_box select_box" onclick="showSelect(this,event)"></div>	<div class="select_btn_box"><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">10�� - 99��</a><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">100�� - 199��</a><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">200�� - 299��</a><a href="javascript:;" onclick="clickSelect(this,event)" onmouseover="hoverSelect(this)">300������</a></div></div></td><td>֤������*</td><td><div class="form_box"><input type="text" /></div></td></tr><tr><td>�Ͻ��ʱ�(��Ԫ)</td><td class="td_border"><div class="form_box"><input type="text" /></div></td><td>��ռ����*</td><td><div class="form_box"><input type="text" /></div></td></tr>';
		$(target).parent().next().children().append(oTrElement);
	}
}