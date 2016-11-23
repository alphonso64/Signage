//菜单的切换
$(function(){
	$('#nav>ul.menu').on('click','li',function(){
		$(this).addClass('selected').siblings('li.selected').removeClass('selected');
		$(this).parent().parent().siblings('#main').children('div').eq($(this).index())
			   .addClass('selected').siblings('div.selected').removeClass('selected');
	});
});
//////////////////////////////////////
//操作界面查询
function conDown(){
	if($('input.con_invcode').val().length!=0&&($('input.con_invcode').attr('readonly')!='readonly')){
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$('#popup').removeClass('hidden');
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/reqDispatchFileDetails?invcode="+$('input.con_invcode').val(),
			dataType:"json",
			success:function(e){
				if(e.return_code=="success"){
					$('#popup').addClass('hidden');
					$('input.con_invcode').attr('readonly','readonly');
					$('input.con_query').removeClass('query');//去除样式
					$('input.con_submit').addClass('sub');//添加样式
					$('input.con_invcode').removeClass('inv');//去除
					$('#configure>ul>li:first-child select').attr('disabled','disabled');
					con_success(e);
				}else if(e.return_code=="fail"){
					$('#popup p').html(e.return_msg);
					ifButton();
					for(var i=1;i<18;i++){
						$('#con_box>ul:nth-child(2) li').eq(i).html('');
					}
				}
			},
			error:function(){
				$('#popup p').html('服务器连接错误！');
				ifButton();
				for(var i=1;i<18;i++){
					$('#con_box>ul:nth-child(2) li').eq(i).html('');
				}
			}
		});
	}
}
////////////////////////////////////////////////////////////////////
$(function(){
	$('input.con_query').click(function(){
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
//查询成功
function con_success(e){
	var cont=0;
	for(var key in e.configure){
		cont++;
	}
	for(var key in e.configure){
		if(e.configure[key]==null){
			for(var i=0;i<cont;i++){
				if('w'+i==key){
					$('#con_box>ul:nth-child(2) li').eq(i).html('空');
				}
			}
		}else if(key=='invcode'||key=='id'){
			continue;
		}else{
			for(var i=0;i<cont;i++){
				if('w'+i==key){
					$('#con_box>ul:nth-child(2) li').eq(i).html('').html(e.configure[key]);
				}
			}
		}
	}
}

//操作界面提交
$(function(){
//	$('input.con_submit').click(function(){
	$('#confirm1 button').eq(1).click(function(){
	//	if($('input.con_invcode').attr('readonly')=='readonly'){
			$('#confirm1').addClass('hidden');
			$('#popup p').html('连接中请稍后...');
			ifButton();
			$('#popup').removeClass('hidden');
			$.ajax({
				type:"get",
				url:"/Signage/rest/upload/dispatchFiles?productionline=B1&invcode="+$('input.con_invcode').val(),
				dataType:"json",
				success:function(e){
					$('#popup p').html(e.return_msg);
					ifButton();
					if(e.return_msg=='fail'){
						for(var i=1;i<18;i++){
							$('#con_box>ul:nth-child(2) li').eq(i).html('');
						}
					}
				},
				error:function(){
					$('#popup p').html('服务器连接错误！');
					ifButton();
					for(var i=1;i<18;i++){
						$('#con_box>ul:nth-child(2) li').eq(i).html('');
					}
				}
			});
	//	}
	});
});

//配置界面查询
function opeDown(){
	if($('input.ope_invcode').val().length!=0&&($('input.ope_invcode').attr('readonly')!='readonly')){
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$('#popup').removeClass('hidden');
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/reqDispatchFileDetails?invcode="+$('input.ope_invcode').val(),
			dataType:"json",
			success:function(e){
				if(e.return_code=="success"){
					$('#popup').addClass('hidden');
					$('input.ope_invcode').attr('readonly','readonly');
					$('input.ope_query').removeClass('query');//去除样式
					$('input.ope_submit').addClass('sub');//添加样式
					$('input.ope_invcode').removeClass('inv');
					$('#ope_top>ul>li:first-child select').attr('disabled','disabled');
					ope_success(e);
				}else if(e.return_code=="fail"){
					$('#popup p').html(e.return_msg);
					ifButton();
					$('ul.proset_invcode').html('');//清空
					$('ul.toset_invcode').html('');//
					$('#thumbnail>div').html('');//
				}
			},
			error:function(){
				$('#popup p').html('服务器连接错误！');
				ifButton();
				$('ul.proset_invcode').html('');//清空
				$('ul.toset_invcode').html('');//
				$('#thumbnail>div').html('');//
			}
		});
	}
}
$(function(){
	$('input.ope_query').click(function(){
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
	$('ul.proset_invcode').html('');//清空预配置
	$('ul.toset_invcode').html('');//清空编号
	$('#thumbnail>div').html('');//清空预览
	for(var i=0;i<17;i++){
		var sec='<option>空</option>';
		for(var j=0;j<num;j++){
			sec+='<option>'+e.data[j].name+'</option>';
		}
		$('ul.toset_invcode').append('<li><select>'+sec+'</select><button>提交</button></li>');
	}
	for(var a=0;a<num;a++){ //指导书预览
		$('#thumbnail>div').append('<dl>'+e.data[a].name.replace(/\.pdf/g,'')+'<dt>'+e.data[a].path+'</dt></dl>');
	}
	//插入预配置
	for(var key in e.configure){
		if(e.configure[key]==null){
			$('ul.proset_invcode').append('<li>空</li>');
		}else if(key=='invcode'||key=='id'){
			continue;
		}else{
			$('ul.proset_invcode').append('<li>'+e.configure[key]+'</li>');
		}
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
			$('#fullScreen').addClass('hidden');
			$('#popup').removeClass('hidden');
			$('#popup p').html('服务器连接错误！');
			ifButton();
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
	$('#confirm2 button').eq(1).click(function(){
		$('#confirm2').addClass('hidden');
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$('#popup').removeClass('hidden');
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
		var man=true;
		for(var i=0;i<17;i++){
			if($('ul.proset_invcode>li').eq(i).html()!=($('ul.toset_invcode>li').eq(i).children('select').val().replace(/\.pdf/g,''))){
				$('ul.proset_invcode>li').eq(i).html($('ul.toset_invcode>li').eq(i).children('select').val().replace(/\.pdf/g,''));
				man=false;
			}
		}
		if(man){
			$('#popup p').html('和预配置文件相同!');
			ifButton();
		}else{
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
		}
	});
	//单个提交
	$('ul.toset_invcode').on('click','button',function(){
		if($(this).prev('select').val().replace(/\.pdf/g,'') != $('ul.proset_invcode>li').eq($(this).parent().index()).html()){
			$('#confirm3').fadeIn(200);
		}
		$(this).parent().addClass('abc');
	});
	$('#confirm3 button').eq(0).click(function(){//取消
		$('#confirm3').fadeOut(200);
		$('ul.toset_invcode>li.abc').removeClass('abc');
	});
	///////////////////////////////////////
	$('#confirm3 button').eq(1).click(function(){
		$('#confirm3').fadeOut(200);
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$('#popup').removeClass('hidden');
		var wnum=$('ul.toset_invcode>li.abc select').val().replace(/\.pdf/g,'');
		console.log(wnum);
		var rePath={};
		rePath.invcode=$('input.ope_invcode').val();
		if(wnum != '空'){
			for(var i=0;i<$('#thumbnail>div>dl').length+1;i++){
				if( wnum ==  $('#thumbnail>div>dl').eq(i).html().replace(/<dt>.*<\/dt>/g,'')){
					rePath['w'+($('ul.toset_invcode>li.abc').index()+1)] = $('#thumbnail>div>dl').eq(i).children('dt').html();
					break;
				}
			}
		}else{
			rePath['w'+($('ul.toset_invcode>li.abc').index()+1)] = 'null';
		}
		$('ul.proset_invcode>li').eq($('ul.toset_invcode>li.abc').index()).html(wnum);
		$.ajax({
			type:"post",
			url:"/Signage/rest/upload/setSpecificConfigure",
			contentType : "application/json",
			data:JSON.stringify(rePath),
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
		$('ul.toset_invcode>li.abc').removeClass('abc');
	});
});

///操作清空
$(function(){
	$('input.con_clear').click(function(){
		$('input.con_invcode').removeAttr('readonly');
		$('input.con_invcode').val('');
		for(var i=1;i<18;i++){
			$('#con_box>ul:nth-child(2) li').eq(i).html('');
		}
		$('input.con_query').addClass('query');
		$('input.con_submit').removeClass('sub');
		$('input.con_invcode').addClass('inv');
		$('#configure>ul>li:first-child select').removeAttr('disabled');
	});
});
///配置清空
$(function(){
	$('input.ope_clear').click(function(){
		$('input.ope_invcode').removeAttr('readonly');
		$('input.ope_invcode').val('');
		$('ul.proset_invcode').html('');
		$('ul.toset_invcode').html('');
		$('#thumbnail>div').html('');
		$('input.ope_query').addClass('query');
		$('input.ope_submit').removeClass('sub');
		$('input.ope_invcode').addClass('inv');
		$('#ope_top>ul>li:first-child select').removeAttr('disabled');
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
//浏览文件bug修复
$('#btnSubmit').click(function(){
	$('#upfile').val('未选择文件');
	if($("#path").val()==''){
		$('#path').click();
	}else{
		$("#fileUp input[type='file']").val('');
		$('#path').click();
	}
});
//onchange
$("#path").change(function(){
	$('#upfile').val($(this).val().replace(/^.*\\/g,''));
});
//连接中判断，按钮消失
function ifButton(){
	if($('#popup>div>p').html()=='连接中请稍后...'||$('#popup>div>p').html()=='文件上传中...'){
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
//确认提交
$(function(){
	//1
	$('#confirm1 button').eq(0).click(function(){
		$('#confirm1').addClass('hidden');
	});
	$('input.con_submit').click(function(){
		if($('input.con_invcode').attr('readonly')=='readonly'){
			$('#confirm1').removeClass('hidden');
		}
	});
	//2
	$('#confirm2 button').eq(0).click(function(){
		$('#confirm2').addClass('hidden');
	});
	$('input.ope_submit').click(function(){
		if($('input.ope_invcode').attr('readonly')=='readonly'){//先查询在提交
			$('#confirm2').removeClass('hidden');
		}
	});
});
//数据转换
$(function(){
	$('#ope_main>input').click(function(){
		if($('input.ope_invcode').attr('readonly')=='readonly'){
			for(var i=0;i<17;i++){
				if($('.proset_invcode>li').eq(i).html()=='空'){
					$('.toset_invcode>li').eq(i).children('select').val('空');
				}
				else{
					console.log($('.proset_invcode>li').eq(i).html()+'.pdf');
					$('.toset_invcode>li').eq(i).children('select').val($('.proset_invcode>li').eq(i).html()+'.pdf');
				}
			}
		}
	});
});

//文件管理
$(function(){
	//文件刷新按钮 meixie
	$('#btn').click(function(){
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/getProductList",
			dataType:"json",
			success:function(e){
				fileLoad(e);
			}
		});
	});
	//上下翻页
	$('#file_jump>li').eq(0).click(function(){
		if($('#file_jump input').val().length == 0){
			$('#file_jump input').val(1);
			$('#file_box>ul').eq(0).show();////
			$('#file_box>ul').eq($('#file_jump input').val()-1).siblings().hide();
		}else if($('#file_jump input').val() != 1 && $('#file_jump b').html() != 0){
			$('#file_jump input').val($('#file_jump input').val()-1);
			$('#file_box>ul').eq($('#file_jump input').val()-1).show();////
			$('#file_box>ul').eq($('#file_jump input').val()-1).siblings().hide();
		}
	});
	$('#file_jump>li').eq(2).click(function(){
		if($('#file_jump input').val().length == 0){
			$('#file_jump input').val(1);
			$('#file_box>ul').eq(0).show();////
			$('#file_box>ul').eq($('#file_jump input').val()-1).siblings().hide();
		}else if($('#file_jump input').val() != $('#file_jump b').html() && $('#file_jump b').html() != 0){
			$('#file_jump input').val(Number($('#file_jump input').val())+1);
			$('#file_box>ul').eq($('#file_jump input').val()-1).show();////
			$('#file_box>ul').eq($('#file_jump input').val()-1).siblings().hide();
		}
	});
	//内部动画样式  加载
	$('#file_box').on('click','li',function(){
		var aLi=$(this);
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/reqDispatchFileDetails?invcode="+(aLi.children('p').children('b').html()),
			dataType:"json",
			cache:true,
			success:function(e){
				aLi.children('div').html('');
				if(e.return_code=="success"){
					for(var i=0;i<e.data.length;i++){ //指导书预览
						aLi.children('div').append('<dl>'+e.data[i].name.replace(/\.pdf/g,'')+'<dt>'+e.data[i].path+'</dt></dl>');
					}
					aLi.children('div').slideToggle(200);
					aLi.siblings().children('div').slideUp(200);
				}
			},
			error:function(){
				aLi.children('div').html('加载失败...');
				aLi.children('div').slideToggle(200);
				aLi.siblings().children('div').slideUp(200);
			}
		});
		/****待定
		 * $(this).children('div').slideToggle(200);
		 * $(this).siblings('li.sh').children('div').slideUp(200);
		 * $(this).siblings('li.sh').removeClass('sh');
		 * $(this).addClass('sh');
		****/
	});
	//文件pdf预览
	$('#file_box').on('click','dl',function(e){
		e.stopPropagation();
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
				$('#fullScreen').addClass('hidden');
				$('#popup').removeClass('hidden');
				$('#popup p').html('服务器连接错误！');
				ifButton();
			}
		});
	});
	//文件查找
	$('#fileQuery>button').click(function(e){
		e.preventDefault();
		fileQueryBtn();
	});
});
//文件查找
function fileQueryBtn(){
	if($('#fileQuery>input').val().length!=0){
		$('#file_box>ul.fileQuery').remove();
		$('#file_box>ul').hide();
		var queryLi='';
		for(var i=0;i<$('#file_jump b').html();i++){
			var jumpLi=$('#file_box>ul').eq(i).children('li');
			for(var j=0; j<jumpLi.length; j++){
				if(jumpLi.eq(j).children('p').children('b').html().indexOf($('#fileQuery>input').val()) >= 0){
					queryLi+='<li>' + jumpLi.eq(j).html().replace(/style="display: block;"/g,'') + '</li>';
				}
			}
		}
		$('#file_box').append('<ul class="fileQuery">' + queryLi + '</ul>');
	}
}
function file_keyDown(e){
  	var ev= window.event||e;
  	if (ev.keyCode == 13) {
		fileQueryBtn();
  	}
}
//跳页按钮
function jumpBtn(){
	if( $('#file_jump input').val() <= $('#file_jump b').html() && $('#file_jump input').val().length !=0 ){
		$('#file_left>li').eq($('#file_jump input').val()-1).show();////
		$('#file_left>li').eq($('#file_jump input').val()-1).siblings().hide();
	}
}
$('#file_jump>li:last-child button').click(function(){
	jumpBtn();
});
function jumph(e){
  	var ev= window.event||e;
  	if (ev.keyCode == 13) {
		jumpBtn();
  	}
}
//文件列表的加载
function fileLoad(){
	$.ajax({
		type:"get",
		url:"/Signage/rest/upload/getProductList",
		dataType:"json",
		success:function(e){
			var Eth = Math.ceil(e.data.length/10);//页数
			$('#file_jump b').html(Eth);//页数加载
			$('#file_box').html('');//清空内容
			$('#file_jump input').val(1);
			for(var i=0,a=0;i<Eth;i++){
				var li_box='';
				for(var j=a==0?a:a+1; j<(i+1)*10 && j<e.data.length;j++){
					a=j;
					li_box+='<li><p><b>'+e.data[j].invcode+'</b>'+e.data[j].invstd+'</p><div></div></li>';
				}
				var ul_box='<ul>'+li_box+'</ul>';
				$('#file_box').append(ul_box);
			}
			$('#file_box>ul:first-child').show();
		}
	});
}


window.onload=function(){
	ifLoading();
	$('#con_box>ul').eq(0).append('<li>工位号</li>');
	$('#con_box>ul').eq(1).append('<li>文件名</li>');
	for(var i=0;i<17;i++){
		$('#con_box>ul').eq(0).append('<li>'+(i+1)+'</li>');
		$('#con_box>ul').eq(1).append('<li></li>');
	}
}





