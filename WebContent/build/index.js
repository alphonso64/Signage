//菜单的切换
$(function(){
	$('#nav>ul.menu').on('click','li',function(){
		$(this).addClass('selected').siblings('li.selected').removeClass('selected');
		$(this).parent().parent().siblings('#main').children('div').eq($(this).index())
			   .addClass('selected').siblings('div.selected').removeClass('selected');
	});
});
//////////////////////////////////////
//操作界面
function conDown(){
	if($('input.con_invcode').val().length!=0){
		$('#popup p').html('连接中请稍后...');
		$('#popup').removeClass('hidden');
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/dispatchFiles?productionline=C4&invcode="+$('input.con_invcode').val(),
			dataType:"json",
			success:function(e){
				$('#popup p').html('').html(e.return_msg);
			},
			error:function(){
				$('#popup p').html('').html('服务器连接错误！');
			}
		});
	}
}
$(function(){
	$('button.con_submit').click(function(){
		conDown();
	});
});
//回车
function con_keyDown(e){
  	var ev= window.event||e;
  	if (ev.keyCode == 13) {
		conDown();
  	}
}

///////////////////////////////////////////////
//配置界面提交
function opeDown(){
	if($('input.ope_invcode').val().length!=0){
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/reqDispatchFileDetails?invcode="+$('input.ope_invcode').val(),
			dataType:"json",
			success:function(e){
				if(e.return_code=="success"){
					ope_success(e);
				}else if(e.return_code=="fail"){
					$('#popup').removeClass('hidden');
					$('#popup p').html('').html(e.return_msg);
				}
			},
			error:function(){
				$('#popup').removeClass('hidden');
				$('#popup p').html('').html('服务器连接错误！');
			}
		});
	}
}
$(function(){
	$('input.ope_submit').click(function(){
		opeDown();
	});
});
//回车
function ope_keyDown(e){
  	var ev= window.event||e;
  	if (ev.keyCode == 13) {
		opeDown();
  	}
}
//配置界面数据处理
function ope_success(e){
	var num=e.data.length;
	//插入name
	$('ul.invcode').html('');//清空编号
	$('#thumbnail>div').html('');//清空预览
	for(var i=0;i<num&&(i<17);i++){
		var sec='<option>空</option>';
		for(var j=0;j<num;j++){
			sec+='<option>'+e.data[j].name+'</option>';
		}
		$('ul.invcode').append('<li><select>'+sec+'</select></li>');
	}
	for(var a=0;a<num;a++){ //指导书预览
		$('#thumbnail>div').append('<dl>'+e.data[a].name+'<dt>'+e.data[a].path+'</dl>');
	}
}
//全屏预览
$('#thumbnail>div').on('click','dl',function(){
	console.log(123);
	console.log($(this).children('dt').html());
	var dlUrl="/Signage/rest/upload/reqpdffile?path="+$(this).children('dt').html();
	$.ajax({
		type:"get",
		url:dlUrl,
		success:function(){
			$('#fullScreen>iframe').attr('src',dlUrl);
		},
		error:function(){
			console.log('连接错误');
		}
	});
	$('#fullScreen').removeClass('hidden');
});


//提示框关闭
$(function(){
	$('#popup>div>button').click(function(){
		$(this).parent().parent().addClass('hidden');
	});
});
//关闭全屏预览
$(function(){
	$('#fullScreen').click(function(){
		$(this).addClass('hidden');	
	});
});

//提交按钮
$(function(){
	$('input.ope_query').click(function(){
		$('#popup p').html('连接中请稍后...');
		var ope_json={};
		ope_json.invcode=$('input.ope_invcode').val();
		for(var i=0;i<$('ul.invcode>li').length;i++){
			if($('ul.invcode>li').eq(i).children('select').val()!='空'){
				for(var j=0;j<$('#thumbnail>div>dl').length+1;j++){
					if($('ul.invcode>li').eq(i).children('select').val()==$('ul.invcode>li>select>option').eq(j).html()){
						ope_json['w'+(i+1)]=$('#thumbnail>div>dl').eq(j-1).children('dt').html();
						break;
					}
				}
			}
		}
		var oper=JSON.stringify(ope_json);
		$.ajax({
			type:"post",
			url:"/Signage/rest/upload/setConfigure",
			contentType : "application/json",
			data:oper,
			dataType:"json",
			success:function(e){
				$('#popup p').html('').html(e.return_msg);
			},
			error:function(){
				$('#popup p').html('').html('服务器连接错误！');
			}
		});
		$('#popup').removeClass('hidden');
	});
});

////文件提交

$("#flieSubmit").click(function(){
	if($("#fileUp input[type='file']").val()==""){
		$('#popup').removeClass('hidden');
        $("#popup p").html("请选择文件!");
        return false;
    }
	$('#popup').removeClass('hidden');
	$("#fileUp").ajaxSubmit({
		type:"post",
		url:"/Signage/rest/upload/uploadProductCraftResource",
		enctype:'multipart/form-data',
		dataType:'json',
		success:function(data) {
			$("#popup p").html(data.return_msg);
		},
		error:function() {
			$("#popup p").html("文件上传失败!");
		}
	});
	$("input[type='file']").val("");
});




