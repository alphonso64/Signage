
//菜单的切换
$(function(){
	$('#main_left').on('click','li',function(){
		$(this).addClass('selected').siblings('li.selected').removeClass('selected');
		$('#main_box>div').eq($(this).index()-1).addClass('selected').siblings('div.selected').removeClass('selected');
	});
});

//错误选择框
$(function(){
	//选择
	$('#chose_box>ul label').click(function(){
		$(this).addClass('checked').parent().siblings().children('label.checked').removeClass('checked');
		console.log($('label.checked input').val());
	});
	//取消
	$('#chose_box>form input[type="button"]').click(function(){
		$('#chose').hide();
		$('#chose_box>div select').val('0');
		$('#chose_box>div>p>span').html('1');
		$('#chose_box>ul label.checked').removeClass('checked');
	});
	//数量加减
	$('.subtract').click(function(){
		if($(this).siblings('span').html()!=1){
			$(this).siblings('span').html($(this).siblings('span').html()-1);
		}
	});
	$('.add').click(function(){
		$(this).siblings('span').html(parseInt($(this).siblings('span').html())+1);
	});
	
	//确定
	
});

//确认元件的多少
$(function(){
	var num=$('.bad_box>li').length;
	if(num>=20){
		
	}else if(num>=10){
		console.log(123);
	}
	
});









