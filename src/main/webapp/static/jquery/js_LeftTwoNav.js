// JavaScript Document
$(function(){
	var oBtn = $(".left_active");		
	
	oBtn.click(function(){	
		if ($(this).children("img").attr("src") == "/static/img/top.gif")
		{
			$(this).children("img").attr("src","/static/img/bottom.gif").parent().siblings("dl").show(200);
		}else {
			$(this).children("img").attr("src","/static/img/top.gif").parent().siblings("dl").hide(200);
		};
	});
});


/**
 * 选中某个菜单
 */
function selectedChildItem(parentId,childId)
{
    $("#left_active_"+parentId).children("img").attr("src","/static/img/bottom.gif");
    $("#left_active_"+parentId).siblings("dl").show(200);
    $("#left_active_"+parentId+"_subChild_"+childId).attr("style","background:#fff;");
}