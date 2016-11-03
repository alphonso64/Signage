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
		ifButton();
		$('#popup').removeClass('hidden');
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/dispatchFiles?productionline=C4&invcode="+$('input.con_invcode').val(),
			dataType:"json",
			success:function(e){
				$('#popup p').html(e.return_msg);
				ifButton();
			},
			error:function(){
				$('#popup p').html('服务器连接错误！');
				ifButton();
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

//配置界面提交
function opeDown(){
	if($('input.ope_invcode').val().length!=0){
		$('#popup').removeClass('hidden');
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/reqDispatchFileDetails?invcode="+$('input.ope_invcode').val(),
			dataType:"json",
			success:function(e){
				if(e.return_code=="success"){
					$('#popup').addClass('hidden');
					ope_success(e);
				}else if(e.return_code=="fail"){
					$('#popup p').html(e.return_msg);
					ifButton();
				}
			},
			error:function(){
				$('#popup p').html('服务器连接错误！');
				ifButton();
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
	//插入
	$('ul.toset_invcode').html('');//清空编号
	$('#thumbnail>div').html('');//清空预览
	for(var i=0;i<num&&(i<17);i++){
		var sec='<option>空</option>';
		for(var j=0;j<num;j++){
			sec+='<option>'+e.data[j].name+'</option>';
		}
		$('ul.toset_invcode').append('<li><select>'+sec+'</select></li>');
	//	$('ul.proset_invcode').append('<li>'+e+'</li>');插入预配置
//		for(var b=0;b<num;b++){
//			if(e.invcode[i]==('w'+b)){
//			}else{}
//		}
	}
	for(var a=0;a<num;a++){ //指导书预览
		$('#thumbnail>div').append('<dl>'+e.data[a].name.replace(/.pdf/g,'')+'<dt>'+e.data[a].path+'</dl>');
	}
}
//全屏预览
$('#thumbnail>div').on('click','dl',function(){
	$('#fullScreen').removeClass('hidden');
	$('#fullScreen iframe').attr('src','');
	ifLoading();
	var dlUrl="/Signage/rest/upload/reqpdffile?path="+$(this).children('dt').html();
	$.ajax({
		type:"get",
		url:dlUrl,
		success:function(){
			$('#fullScreen iframe').attr('src',dlUrl);
			ifLoading();
		},
		error:function(){
			if($('#fullScreen').attr('class')==''){
				$('#fullScreen').addClass('hidden');
				$('#popup').removeClass('hidden');
				$('#popup p').html('服务器连接错误！');
				ifButton();
			}
		}
	});
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
		ifButton();
		var ope_json={};
		ope_json.invcode=$('input.ope_invcode').val();
		for(var i=0;i<$('ul.toset_invcode>li').length;i++){
			if($('ul.toset_invcode>li').eq(i).children('select').val()!='空'){
				for(var j=0;j<$('#thumbnail>div>dl').length+1;j++){
					if($('ul.toset_invcode>li').eq(i).children('select').val()==$('ul.toset_invcode>li>select>option').eq(j).html()){
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
				$('#popup p').html(e.return_msg);
				ifButton();
			},
			error:function(){
				$('#popup p').html('服务器连接错误！');
				ifButton();
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
        ifButton();
        return false;
    }
	$("#popup p").html("文件上传中...");
	ifButton();
	$('#popup').removeClass('hidden');
	$("#fileUp").ajaxSubmit({
		type:"post",
		url:"/Signage/rest/upload/uploadProductCraftResource",
		enctype:'multipart/form-data',
		dataType:'json',
		success:function(data) {
			$("#popup p").html(data.return_msg);
			ifButton();
		},
		error:function() {
			$("#popup p").html("文件上传失败!");
			ifButton();
		}
	});
	$("input[type='file']").val("");
});

//连接中判断，按钮消失
function ifButton(){
	if($('#popup>div>p').html() == ('连接中请稍后...'||'文件上传中...')){
		$('#popup>div>button').hide();
	}else{
		$('#popup>div>button').show();
	}
}
//loading出现于消失
function ifLoading(){
	if($('#fullScreen iframe').attr('src') == ''){
		$('#fullScreen iframe').hide();
		$('#fullScreen img').show();
	}else{
		$('#fullScreen img').hide();
		$('#fullScreen iframe').show();
	}
}
ifLoading();













