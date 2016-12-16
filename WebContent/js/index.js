//菜单的切换
$(function(){
	$('#nav>ul.menu').on('click','li',function(){
		$(this).addClass('selected').siblings('li.selected').removeClass('selected');
		$(this).parent().parent().siblings('#main').children('div').eq($(this).index())
			   .addClass('selected').siblings('div.selected').removeClass('selected');
	});
});
//操作界面查询文件一
function conDown1(){
	if($('input.con_invcode').val().length!=0&&($('input.con_invcode').attr('readonly')!='readonly')){//输入不为空 不能是禁止输入
		//当'显示混合配置时'输入不能和搜索二一样
		if(!($('#configure>button').html()=='隐藏混合配置'&&$('input.con_invcode2').attr('readonly')=='readonly'&&$('input.con_invcode').val()==$('input.con_invcode2').val())){
			$('#popup p').html('连接中请稍后...');
			ifButton();
			$('#popup').removeClass('hidden');
			$.ajax({
				type:"get",
				url:"/Signage/rest/upload/reqDispatchFileDetails?invcode="+$('input.con_invcode').val(),
//				url:"php/video.php?invcode="+$('input.con_invcode').val(),
				dataType:"json",
				success:function(e){
					if(e.return_code=="success"){
						conSuccess1(e);////查询成功
					}else if(e.return_code=="fail"){
						$('#popup p').html(e.return_msg);
						ifButton();
						for(var i=1;i<18;i++){
							$('#con_box>ul:nth-child(2) li').eq(i).html('');
							$('#con_box>ul:nth-child(3) li').eq(i).html('');
						}
					}
				},
				error:function(){
					$('#popup p').html('服务器连接错误！');
					ifButton();
					for(var i=1;i<18;i++){
						$('#con_box>ul:nth-child(2) li').eq(i).html('');
						$('#con_box>ul:nth-child(3) li').eq(i).html('');
					}
				}
			});
		}
	}
}
$(function(){
	$('input.con_query').click(function(){
		conDown1();
	});
});
function con_keyDown1(e){
  	var ev= window.event||e;
  	if (ev.keyCode == 13){
		conDown1();
  	}
}
function conSuccess1(e){//查询成功
	$('#popup').addClass('hidden');
	$('input.con_invcode').attr('readonly','readonly');
	$('input.con_invcode').removeClass('inv');//去除
	$('input.con_query').removeClass('query');//去除样式
	if($('#configure>button').html() == '显示混合配置'){
		$('input.con_submit').addClass('sub');//添加样式 @
	}else if( $('input.con_invcode2').attr('readonly') == 'readonly' ){
		$('input.con_submit').addClass('sub');//添加样式 @
	}
	var conQuery1_pdf = '<li>文件名</li>';
	var conQuery1_video = '<li>视频名</li>';
	for(var key1 in e.pdfconfigure){//pdf文件
		if( key1 != 'invcode' && key1 != 'id' && e.pdfconfigure[key1] != null){
			for(var i=0; i<e.pdf.length; i++){
				if( e.pdfconfigure[key1] == e.pdf[i].name.replace(/\.\w+$/ig,'') ){
					conQuery1_pdf += '<li>' + e.pdfconfigure[key1] + '<span>' + e.pdf[i].path + '</span></li>'
				}
			}
		}else if( e.pdfconfigure[key1] == null ){
			conQuery1_pdf += '<li>' + '空' + '<span>空</span>' + '</li>';
		}
	}
	for(var key2 in e.videoconfigure){//视频文件
		if( key2 != 'invcode' && key2 != 'id' && e.videoconfigure[key2] != null){
			if( e.videoconfigure[key2].indexOf('通用-') == -1 ){//不是通用视频
				for(var i=0; i<e.video.length; i++){
					if( e.videoconfigure[key2] == e.video[i].name.replace(/\.\w+$/ig,'') ){
						conQuery1_video += '<li>' + e.videoconfigure[key2] + '<span>' + e.video[i].path + '</span></li>';
					}
				}
			}else{
				var pubvideo = $('#publicVideo dl');
				for(var j=0; j<pubvideo.length; j++){
					if( e.videoconfigure[key2] == pubvideo.eq(j).html().replace(/<dt>.*<\/dt>/ig,'') ){
						conQuery1_video += '<li>' + e.videoconfigure[key2] + '<span>' + pubvideo.eq(j).children('dt').html() + '</span></li>';
					}
				}
			}
		}else if( e.videoconfigure[key2] == null ){
			conQuery1_video += '<li>' + '空' + '<span>空</span>' + '</li>';
		}
	}
	$('#con_box>ul').eq(1).html( conQuery1_pdf );
	$('#con_box>ul').eq(2).html( conQuery1_video );
	$('#con_box2>ul').eq(1).html( conQuery1_pdf );
	$('#con_box2>ul').eq(2).html( conQuery1_video );
}
//操作界面查询文件二
function conDown2(){
	if($('input.con_invcode2').val().length!=0&&($('input.con_invcode2').attr('readonly')!='readonly')){//不能为空并且不为只读
		//在查询一只读的情况下不能一样
		if( !($('input.con_invcode').attr('readonly')=='readonly' && $('input.con_invcode').val()==$('input.con_invcode2').val())){
			$('#popup p').html('连接中请稍后...');
			ifButton();
			$('#popup').removeClass('hidden');
			$.ajax({
				type:"get",
				url:"/Signage/rest/upload/reqDispatchFileDetails?invcode="+$('input.con_invcode2').val(),
//				url:"php/video.php?invcode="+$('input.con_invcode2').val(),
				dataType:"json",
				success:function(e){
					if(e.return_code=="success"){
						conSuccess2(e);//查询成功
					}else if(e.return_code=="fail"){
						$('#popup p').html(e.return_msg);
						ifButton();
						for(var i=1;i<18;i++){
							$('#con_box1>ul:nth-child(2) li').eq(i).html('');
						}
					}
				},
				error:function(){
					$('#popup p').html('服务器连接错误！');
					ifButton();
					for(var i=1;i<18;i++){
						$('#con_box1>ul:nth-child(2) li').eq(i).html('');
					}
				}
			});
		}
	}
}
$(function(){
	$('input.con_query2').click(function(){
		conDown2();
	});
});
function con_keyDown2(e){
  	var ev= window.event||e;
  	if (ev.keyCode == 13) {
		conDown2();
  	}
}
function conSuccess2(e){//查询成功
	$('#popup').addClass('hidden');
	$('input.con_invcode2').attr('readonly','readonly');
	$('input.con_invcode2').removeClass('inv');//去除
	$('input.con_query2').removeClass('query');//去除样式
	if( $('input.con_invcode').attr('readonly') == 'readonly'){
		$('input.con_submit').addClass('sub');//添加样式@
	}
	var conQuery2_pdf = '<li>文件名</li>';
	var conQuery2_video = '<li>视频名</li>';
	for(var key1 in e.pdfconfigure){//pdf
		if( key1 != 'invcode' && key1 != 'id' && e.pdfconfigure[key1] != null){
			for(var i=0; i<e.pdf.length; i++){
				if( e.pdfconfigure[key1] == e.pdf[i].name.replace(/\.\w+$/ig,'') ){
					conQuery2_pdf += '<li>' + e.pdfconfigure[key1] + '<span>' + e.pdf[i].path + '</span></li>'
				}
			}
		}else if( e.pdfconfigure[key1] == null ){
			conQuery2_pdf += '<li>' + '空' + '<span>空</span>' + '</li>';
		}
	}
	for(var key2 in e.videoconfigure){//video
		if( key2 != 'invcode' && key2 != 'id' && e.videoconfigure[key2] != null){
			if( e.videoconfigure[key2].indexOf('通用-') == -1 ){//不是通用视频
				for(var i=0; i<e.video.length; i++){
					if( e.videoconfigure[key2] == e.video[i].name.replace(/\.\w+$/ig,'') ){
						conQuery2_video += '<li>' + e.videoconfigure[key2] + '<span>' + e.video[i].path + '</span></li>';
					}
				}
			}else{
				var pubvideo = $('#publicVideo dl');
				for(var j=0; j<pubvideo.length; j++){
					if( e.videoconfigure[key2] == pubvideo.eq(j).html().replace(/<dt>.*<\/dt>/ig,'') ){
						conQuery2_video += '<li>' + e.videoconfigure[key2] + '<span>' + pubvideo.eq(j).children('dt').html() + '</span></li>';
					}
				}
			}
		}else if( e.videoconfigure[key2] == null ){
			conQuery2_video += '<li>' + '空' + '<span>空</span>' + '</li>';
		}
	}
	$('#con_box1>ul').eq(1).html( conQuery2_pdf );
	$('#con_box1>ul').eq(2).html( conQuery2_video );
}
//操作界面换线
$(function(){
	//点击提交
	$('input.con_submit').click(function(){
		if($('input.con_invcode').attr('readonly')=='readonly'){
			if( $('#configure>button').html() == '显示混合配置' ){
				$('#confirm1').show();
			}else if( $('input.con_invcode2').attr('readonly')=='readonly' ){
				$('#confirm1').show();
			}
		}
	});
	//取消提交
	$('#confirm1 button').eq(0).click(function(){
		$(this).parent().parent().hide();
	});
	//确认提交
	$('#confirm1 button').eq(1).click(function(){
		$('#confirm1').hide();
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$('#popup').removeClass('hidden');
//		var coninv={};
		if( $('#configure>button').html() == '显示混合配置' ){//只有一条线
			conHuanxian( $('input.con_invcode').val() );
		}else{//混合配置
			for(var i=1,redList=0; i<18; i++){
				if( $('#con_box2>ul:nth-child(1) li').eq(i).hasClass('red') ){
//					coninv['w'+i] = $('input.con_invcode2').val();
					redList ++;
				}else{
//					coninv['w'+i] = $('input.con_invcode').val();
				}
			}
		}
		if( redList == 0 ){//只有第一条
			conHuanxian( $('input.con_invcode').val() );
		}else if( redList == 17 ){//只有第二条
			conHuanxian( $('input.con_invcode2').val() );
		}else{//混合配置
			
		}
	});
	///操作一清空
	$('input.con_clear').click(function(){
		$('input.con_invcode').removeAttr('readonly');
		$('input.con_invcode').val('');
		for(var i=1;i<19;i++){
			$('#con_box>ul:nth-child(2) li').eq(i).html('');
			$('#con_box>ul:nth-child(3) li').eq(i).html('');
			$('#con_box2>ul:nth-child(2) li').eq(i).html('');
			$('#con_box2>ul:nth-child(3) li').eq(i).html('');
		}
		$('#con_box2 li.red').removeClass('red');//去除混合样式
		$('#con_box2>ul:nth-child(2) li').removeClass('orange');//去除橙色
		$('input.con_query').addClass('query');
		$('input.con_submit').removeClass('sub');
		$('input.con_invcode').addClass('inv');
	});
	///操作二清空
	$('input.con_clear2').click(function(){
		$('input.con_invcode2').removeAttr('readonly');
		$('input.con_invcode2').val('');
		for(var i=1;i<19;i++){
			$('#con_box1>ul:nth-child(2) li').eq(i).html('');//清空
			$('#con_box1>ul:nth-child(3) li').eq(i).html('');//清空
		}
		reduction();//清除有red的
		$('input.con_query2').addClass('query');
		$('input.con_submit').removeClass('sub');
		$('input.con_invcode2').addClass('inv');
	});
	//混合配置显示隐藏按钮
	$('#configure>button').click(function(){
		$('#btn_box').slideToggle(200);
		if($(this).html() == '显示混合配置'){
			$(this).html('隐藏混合配置');
			$('#configure>span').show();
			$('input.con_submit').removeClass('sub');
			$('input.con_clear2').click();//文件二清除
		}else{
			$(this).html('显示混合配置');
			$('#configure>span').hide();
			if($('input.con_invcode').attr('readonly') == 'readonly'){
				$('input.con_submit').addClass('sub');
			}
		}
	});
	$('#btn_box>button').click(function(){
		reduction();
	});
});
function conHuanxian(val){
	$.ajax({
		type:"get",
		url:"/Signage/rest/upload/dispatchFiles?productionline=" + $('.con_sec').val() + "&invcode=" + val,
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
function reduction(){//还原混合界面
	for(var i=1; i< 19; i++){
		$('#con_box2 ul:nth-child(2) li').eq(i).html( $('#con_box>ul:nth-child(2) li').eq(i).html() );
		$('#con_box2 ul:nth-child(3) li').eq(i).html( $('#con_box>ul:nth-child(3) li').eq(i).html() );
		$('#con_box2 li').eq(i).removeClass('red');//清除样式
		$('#con_box2>ul:nth-child(2) li').removeClass('orange');
	}
}
//配置界面查询
function opeDown(){
	if($('input.ope_invcode').val().length!=0 && ($('input.ope_invcode').attr('readonly')!='readonly')){
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$('#popup').removeClass('hidden');
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/reqDispatchFileDetails?invcode="+$('input.ope_invcode').val(),
//			url:"php/video.php?invcode="+$('input.ope_invcode').val(),
			dataType:"json",
			success:function(e){
				if(e.return_code=="success"){
					ope_success(e);
				}else if(e.return_code=="fail"){
					opeClear();//清空
					$('#popup p').html(e.return_msg);
					ifButton();
				}
			},
			error:function(){
				$('#popup p').html('服务器连接错误！');
				ifButton();
				opeClear();
			}
		});
	}
}
$('input.ope_query').click(function(){opeDown();});
//回车
function ope_keyDown(e){
  	var ev= window.event||e;
  	if (ev.keyCode == 13){
		opeDown();
  	}
}
//配置界面数据处理
function ope_success(e){
	$('#popup').addClass('hidden');
	$('input.ope_invcode').attr('readonly','readonly');
	$('input.ope_invcode').removeClass('inv');//禁止输入
	$('input.ope_query').removeClass('query');//禁止查找
	$('input.ope_submit').addClass('chen');//添加提交样式
	$('input.ope_changeLine').addClass('sub');//添加换线样式
	$('ul.proset_invcode').html('');
	$('ul.toset_invcode').html('');
	$('ul.video').html('');
	var pdfNth=e.pdf.length;
	var videoNth=e.video.length;
	var pubVideoNth=$('#publicVideo>dl').length;
	var pdf='<legend>指导书文件缩略图</legend>',
		video='<legend>视频文件缩略图</legend>';
	//插入to配置//视频
	for(var i=0; i<17; i++){
		var sec='<option>空</option>';
		var vec='<option>空</option>';
		for(var j=0; j<pdfNth; j++){//文件to配置
			sec+='<option>'+e.pdf[j].name.replace(/\.\w+$/ig,'')+'</option>';
		}
		for(var k=0; k<videoNth; k++){//文件视频
			vec+='<option>'+e.video[k].name.replace(/\.\w+$/ig,'')+'</option>';
		}
		for(var m=0; m<pubVideoNth; m++){//通用视频
			vec+='<option>'+$('#publicVideo>dl').eq(m).html().replace(/<dt>.*<\/dt>/ig,'')+'</option>';
		}
		$('ul.toset_invcode').append('<li><select>'+sec+'</select></li>');
		$('ul.video').append('<li><select>'+vec+'</select><button>提交</button></li>');
	}
	for(var a=0; a<pdfNth; a++){//文件预览
		pdf += '<dl>'+e.pdf[a].name.replace(/\.\w+$/ig,'')+'<dt>'+e.pdf[a].path+'</dt></dl>';
	}
	for(var b=0; b<videoNth; b++){//视频预览
		video += '<dl>'+e.video[b].name.replace(/\.\w+$/ig,'')+'<dt>'+e.video[b].path+'</dt></dl>';
	}
	$('#pdfView').html(pdf);
	$('#videoView').html(video);
	//插入预配置文件和视频
	for(var key1 in e.pdfconfigure){
		for(var key2 in e.videoconfigure){
			if( key1 == key2 && key1 != 'invcode' && key1 != 'id' ){
				var pdfkey= e.pdfconfigure[key1] == null ? '空' : e.pdfconfigure[key1];
				var videokey= e.videoconfigure[key2] == null ? '空' : e.videoconfigure[key2];
				$('ul.proset_invcode').append('<li>' + pdfkey + ' / ' + videokey + '</li>');
			}
		}
	}
}
$(function(){
	///配置清空
	$('input.ope_clear').click(function(){$('input.ope_invcode').val('');opeClear();});
	//提示框关闭
	$('#popup>div>button').click(function(){$(this).parent().parent().addClass('hidden');});
	//关闭全屏预览
	$('#fullScreen').click(function(){$(this).hide();});
	//关闭视频全屏
	$('#fullvideo').click(function(){$(this).hide(); $('#fullvideo>video').attr('src','');});
	$('#fullvideo>video').click(function(e){e.stopPropagation();});//阻止事件冒泡
//配置提交按钮
	//点击提交
	$('input.ope_submit').click(function(){
		if($('input.ope_invcode').attr('readonly')=='readonly'){//先查询在提交
			$('#confirm2').show();
		}
	});
	//取消提交
	$('#confirm2 button').eq(0).click(function(){
		$(this).parent().parent().hide();
	});
	//确认提交
	$('#confirm2 button').eq(1).click(function(){
		$('#confirm2').hide();
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$('#popup').removeClass('hidden');
		var opeJson={
			'pdf': {'invcode': $('input.ope_invcode').val()},
			'video': {'invcode': $('input.ope_invcode').val()}
		};
		for(var i=0; i<17; i++){
			//pdf文件集合
			if($('ul.toset_invcode>li').eq(i).children('select').val() != '空'){//不为空的时候
				var pdfdlNth = $('ul.toset_invcode>li').eq(i).children('select').children('option:selected').index() - 1;
				opeJson.pdf['w' + (i+1)] = $('#pdfView dl').eq(pdfdlNth).children('dt').html();
			}else{
				opeJson.pdf['w' + (i+1)] = null;
			}
			//视频文件集合
			if($('ul.video>li').eq(i).children('select').val() != '空'){
				var videodlNth = $('ul.video>li').eq(i).children('select').children('option:selected').index() - 1;
				if( $('ul.video>li').eq(i).children('select').val().indexOf('通用') == -1 ){//判断通用视频
					opeJson.video['w' + (i+1)] = $('#videoView dl').eq(videodlNth).children('dt').html();
				}else{
					opeJson.video['w' + (i+1)] = $('#publicVideo dl').eq( videodlNth - $('#videoView dl').length ).children('dt').html();
				}
			}else{
				opeJson.video['w' + (i+1)] = null;
			}
		}
		var man = true;
		var kong = 17;
		var jiHe = '';
		for(var i=0;i<17;i++){
			var pdfHtml = $('ul.toset_invcode>li').eq(i).children('select').val();
			var videoHtml = $('ul.video>li').eq(i).children('select').val();
			jiHe += '<li>' + pdfHtml + ' / ' + videoHtml + '</li>';
			if( man == true){
				if($('ul.proset_invcode>li').eq(i).html() != (pdfHtml + ' / ' + videoHtml)){
					man = false;
				}
			}
			if( pdfHtml == '空' && videoHtml == '空' ){
				kong--;
			}
		}
		if(man == true){//循环完毕
			$('#popup p').html('和预配置文件相同!');
			ifButton();
		}else if(kong == 0){
			$('#popup p').html('全部为空无法提交!');
			ifButton();
		}else{
			$.ajax({
				type: "post",
				url: "/Signage/rest/upload/setConfigure",
				contentType: "application/json",
				data: JSON.stringify(opeJson),
				dataType: "json",
				success: function(e){
					$('#popup p').html(e.return_msg);
					ifButton();
					if(e.return_code == "success" && man == false ){
						$('ul.proset_invcode').html( jiHe );
					}
				},
				error: function(){
					$('#popup p').html('服务器连接错误！');
					ifButton();
				}
			});
		}
	});
	//单个提交
	$('ul.video').on('click','button',function(){
		$('#confirm3').show();
		$(this).parent().addClass('abc');
	});
	$('#confirm3 button').eq(0).click(function(){//取消
		$(this).parent().parent().hide();
		$('ul.video>li.abc').removeClass('abc');
	});
	$('#confirm3 button').eq(1).click(function(){//确认单个提交
		$(this).parent().parent().hide();
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$('#popup').removeClass('hidden');
		var abcNth = $('ul.video>li.abc').index();
		var pdfAbc = $('ul.toset_invcode>li').eq( abcNth ).children('select').val();
		var videoAbc = $('ul.video>li.abc select').val();
		var rePath={
			pdf: {invcode: $('input.ope_invcode').val()},
			video: {invcode: $('input.ope_invcode').val()}
		};
		//pdf中的dt位置查找
		if(pdfAbc != '空'){
			var pdfQth = $('ul.toset_invcode>li').eq(abcNth).children('select').children('option:selected').index();
			rePath.pdf['w'+(abcNth+1)] = $('#pdfView dl').eq( pdfQth-1 ).children('dt').html();
		}else{
			rePath.pdf['w'+(abcNth+1)] = 'empty';
		}
		//video中的dt位置查找
		if(videoAbc != '空'){
			var videoQth = $('ul.video>li.abc option:selected').index();
			if( videoAbc.indexOf('通用') == -1 ){//通用视频判断
				rePath.video['w'+(abcNth+1)] = $('#videoView dl').eq( videoQth-1 ).children('dt').html();
			}else{//不包含通用视频
				rePath.video['w'+(abcNth+1)] = $('#publicVideo dl').eq( videoQth - $('#videoView>dl').length-1 ).children('dt').html();
			}
		}else{
			rePath.video['w'+(abcNth+1)] = 'empty';
		}
		$('ul.proset_invcode>li').eq( abcNth ).html( pdfAbc + ' / ' + videoAbc );
		$.ajax({
			type: "post",
			url: "/Signage/rest/upload/setSpecificConfigure",
			contentType: "application/json",
			data: JSON.stringify(rePath),
			dataType: "json",
			success: function(e){
				$('#popup p').html(e.return_msg);
				ifButton();
				alike();//提交成功操作界面更新数据(如果数据相同)
			},
			error: function(){
				$('#popup p').html('服务器连接错误！');
				ifButton();
			}
		});
		$('ul.video>li.abc').removeClass('abc');
	});
//配置界面换线
	//点击提交
	$('input.ope_changeLine').click(function(){
		if($('input.ope_invcode').attr('readonly')=='readonly'){//先查询在提交
			$('#confirm4').show();
		}
	});
	//取消提交
	$('#confirm4 button').eq(0).click(function(){
		$(this).parent().parent().hide();
	});
	//确认提交
	$('#confirm4 button').eq(1).click(function(){
		$(this).parent().parent().hide();
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$('#popup').removeClass('hidden');
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/dispatchFiles?productionline=" + $('.ope_sec').val() + "&invcode=" + $('input.ope_invcode').val(),
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
	});
});
//配置提交数据和操作一样时刷新操作页面
function alike(){
	if( $('input.con_invcode').attr('readonly')=='readonly' && $('input.ope_invcode').val() == $('input.con_invcode').val()){
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/reqDispatchFileDetails?invcode="+$('input.ope_invcode').val(),
			dataType:"json",
			success:function(event){
				if(event.return_code=="success"){
					var conQuery1_pdf = '<li>文件名</li>';
					var conQuery1_video = '<li>视频名</li>';
					for(var key1 in event.pdfconfigure){//pdf文件
						if( key1 != 'invcode' && key1 != 'id' ){
							var pdfkey= event.pdfconfigure[key1] == null ? '空' : event.pdfconfigure[key1];
							conQuery1_pdf += '<li>' + pdfkey + '</li>';
						}
					}
					for(var key2 in event.videoconfigure){//视频文件
						if( key2 != 'invcode' && key2 != 'id' ){
							var videokey= event.videoconfigure[key2] == null ? '空' : event.videoconfigure[key2];
							conQuery1_video += '<li>' + videokey + '</li>';
						}
					}
					$('#con_box>ul').eq(1).html( conQuery1_pdf );
					$('#con_box>ul').eq(2).html( conQuery1_video );
					$('#con_box2>ul').eq(1).html( conQuery1_pdf );
					$('#con_box2>ul').eq(2).html( conQuery1_video );
				}
			}
		});
	}else if( $('input.con_invcode2').attr('readonly')=='readonly' && $('input.ope_invcode').val() == $('input.con_invcode2').val()){
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/reqDispatchFileDetails?invcode="+$('input.ope_invcode').val(),
			dataType:"json",
			success:function(event){
				if(event.return_code=="success"){
					var conQuery2_pdf = '<li>文件名</li>';
					var conQuery2_video = '<li>视频名</li>';
					for(var key1 in event.pdfconfigure){
						if( key1 != 'invcode' && key1 != 'id' ){
							var pdfkey= event.pdfconfigure[key1] == null ? '空' : event.pdfconfigure[key1];
							conQuery2_pdf += '<li>' + pdfkey + '</li>';
						}
					}
					for(var key2 in event.videoconfigure){
						if( key2 != 'invcode' && key2 != 'id' ){
							var videokey= event.videoconfigure[key2] == null ? '空' : event.videoconfigure[key2];
							conQuery2_video += '<li>' + videokey + '</li>';
						}
					}
					$('#con_box1>ul').eq(1).html( conQuery2_pdf );
					$('#con_box1>ul').eq(2).html( conQuery2_video );
				}
			}
		});
	}
}
//预配置数据转移到配置
$(function(){
	$('#ope_main>input').click(function(){
		if($('input.ope_invcode').attr('readonly')=='readonly'){
			for(var i=0;i<17;i++){
				var pdfValue = $('.proset_invcode>li').eq(i).html().replace(/\s\/.*/ig,'');
				var videoValue = $('.proset_invcode>li').eq(i).html().replace(/.*\s\/\s/ig,'');
				if(pdfValue == '空'){//pdf文件
					$('.toset_invcode>li').eq(i).children('select').val('空');
				}
				else{
					$('.toset_invcode>li').eq(i).children('select').val( pdfValue );
				}
				if(videoValue == '空'){//视频文件
					$('.video>li').eq(i).children('select').val('空');
				}
				else{
					$('.video>li').eq(i).children('select').val( videoValue );
				}
			}
		}
	});
});
//全屏预览
$('#pdfView').on('click','dl',function(){
	$('#fullScreen').show();
	$('#fullScreen iframe').attr('src','');
	ifLoading();
	var dlUrl="/Signage/rest/upload/reqpdffile?path=" + $(this).children('dt').html();
	$.ajax({
		type:"get",
		url:dlUrl,
		success:function(){
			$('#fullScreen iframe').attr('src',dlUrl);
			ifLoading();
		},
		error:function(){
			$('#fullScreen').hide();
			$('#popup').removeClass('hidden');
			$('#popup p').html('服务器连接错误！');
			ifButton();
		}
	});
});
//视频全屏
$('#videoView,#publicVideo').on('click','dl',function(){
	$('#fullvideo').show();
	var videoUrl="/Signage/rest/upload/reqvideofile?path="+$(this).children('dt').html();
	$.ajax({
		type:"get",
		url:videoUrl,
		success:function(e){
			$('#fullvideo>video').attr('src',videoUrl);
		},
		error:function(){
			$('#fullvideo').hide();
			$('#popup').removeClass('hidden');
			$('#popup p').html('服务器连接错误！');
			ifButton();
		}
	});
});
//配置清空
function opeClear(){
	$('input.ope_invcode').removeAttr('readonly').addClass('inv');
	$('input.ope_query').addClass('query');//允许查找
	$('input.ope_submit').removeClass('chen');//允许提交
	$('input.ope_changeLine').removeClass('sub');//允许换线
	for(var i=0,add=''; i<17;i++){
		add+='<li></li>';
	}
	$('ul.proset_invcode,ul.toset_invcode,ul.video').html(add);
	$('#pdfView').html('<legend>指导书文件缩略图</legend>');
	$('#videoView').html('<legend>视频文件缩略图</legend>');
}
////文件提交///判断是否存在
$("#flieSubmit").click(function(){
	if($("#path").val()==""){
		$('#popup').removeClass('hidden');
        $("#popup p").html("请选择文件!");
        ifButton();
        return false;
    }
	$("#popup p").html("正在查询文件是否存在...");
	ifButton();
	$('#popup').removeClass('hidden');
	var fileValue = $('#upfile').val().replace(/(\(.*\))*\.\w+$/ig,'');
	$.ajax({//查询是否存在
		type:"get",
		url:"/Signage/rest/upload/getProductList",
//		url:"php/pub.php",
		dataType:"json",
		success:function(e){
			if(e.return_code == "success"){
				if(e.data.length != 0){
					for(var i=0; i<e.data.length; i++){
						if( fileValue == e.data[i].invcode ){
							$('#popup').addClass('hidden');
							$('#confirm5 p').html('文件已存在，确认覆盖？');
							$('#confirm5').show();
							break;
						}
						if( i==e.data.length-1 ){
							$('#popup').addClass('hidden');
							$('#confirm5 p').html('确认上传？');
							$('#confirm5').show();
						}
					}
				}else{
					$('#popup').addClass('hidden');
					$('#confirm5 p').html('确认上传？');
					$('#confirm5').show();
				}
			}else{
				$("#popup p").html(return_msg);
				ifButton();
			}
		},
		error: function(){
			$("#popup p").html("文件上传失败!");
			ifButton();
		}
	});
});
//取消提交
$('#confirm5 button').eq(0).click(function(){
	$(this).parent().parent().hide();
});
//确认提交
$('#confirm5 button').eq(1).click(function(){
	$(this).parent().parent().hide();
	$('#popup p').html('文件上传中...');
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
	$("#path").val('');
	$("#upfile").val("未选择文件");
});
////通用视频文件提交///判断是否存在
$("#videoflieSubmit").click(function(){
	if($("#videopath").val()==""){
		$('#popup').removeClass('hidden');
        $("#popup p").html("请选择文件!");
        ifButton();
        return false;
    }
	$("#popup p").html("正在查询文件是否存在...");
	ifButton();
	$('#popup').removeClass('hidden');
	var videofileValue = '通用-' + $('#upvideofile').val().replace(/\.\w+$/ig,'');
	$.ajax({//查询是否存在
		type:"get",
		url:"/Signage/rest/upload/getUniversalVideoList",
//		url:"php/publicVideo.php",
		dataType:"json",
		success:function(e){
			if(e.return_code == "success"){
				if( e.data.length != 0 ){//判断是否为空
					for(var i=0; i<e.data.length; i++){
						if( videofileValue == e.data[i].name.replace(/\.\w+$/ig,'') ){
							$('#popup').addClass('hidden');
							$('#confirm6 p').html('视频已存在，确认覆盖？');
							$('#confirm6').show();
							break;
						}
						if( i==e.data.length-1 ){
							$('#popup').addClass('hidden');
							$('#confirm6 p').html('确认上传？');
							$('#confirm6').show();
						}
					}
				}else{
					$('#popup').addClass('hidden');
					$('#confirm6 p').html('确认上传？');
					$('#confirm6').show();
				}
			}else{
				$("#popup p").html(return_msg);
				ifButton();
			}
		},
		error: function(){
			$("#popup p").html("文件上传失败!");
			ifButton();
		}
	});
});
//取消提交
$('#confirm6 button').eq(0).click(function(){
	$(this).parent().parent().hide();
});
//确认提交
$('#confirm6 button').eq(1).click(function(){
	$(this).parent().parent().hide();
	$('#popup p').html('文件上传中...');
	ifButton();
	$('#popup').removeClass('hidden');
	$("#videofileUp").ajaxSubmit({
		type:"post",
		url:"/Signage/rest/upload/uploadUniversalVideoResource",
		enctype:'multipart/form-data',
		dataType:'json',
		success:function(data) {
			$("#popup p").html(data.return_msg);
			ifButton();
		},
		error:function() {
			$("#popup p").html("视频上传失败!");
			ifButton();
		}
	});
	$("#videopath").val('');
	$("#upvideofile").val("未选择文件");
});
//浏览文件bug修复
$('#btnSubmit').click(function(){
	$('#upfile').val('未选择文件');
	$("#path").val() != '' && $("#path").val('');
	$('#path').click();
});
$('#videobtnSubmit').click(function(){
	$('#upvideofile').val('未选择文件');
	$("#videopath").val() != '' && $("#videopath").val('');
	$('#videopath').click();
});
//onchange
$("#path").change(function(){
	$('#upfile').val($(this).val().replace(/^.*\\/ig,''));
});
$("#videopath").change(function(){
	$('#upvideofile').val($(this).val().replace(/^.*\\/ig,''));
});
//连接中判断，按钮消失
function ifButton(){
	if($('#popup>div>p').html()=='连接中请稍后...'||$('#popup>div>p').html()=='文件上传中...'||$('#popup>div>p').html()=='正在查询文件是否存在...'){
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
//文件管理
$(function(){
	//文件刷新按钮
	$('#btn').click(function(){
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/getProductList",
//			url:"php/pub.php",
			dataType:"json",
			success:function(e){
				if(e.return_code = 'success'){
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
				}else{
					$('#popup').removeClass('hidden');
					$("#popup p").html(data.return_msg);
					ifButton();
				}
			}
		});
	});
	//上下翻页
	$('#file_jump>li').eq(0).click(function(){
		if($('#file_jump input').val().length == 0){
			$('#file_jump input').val(1);
			$('#file_box>ul').eq(0).show();
			$('#file_box>ul').eq($('#file_jump input').val()-1).siblings().hide();
		}else if($('#file_jump input').val() != 1 && $('#file_jump b').html() != 0){
			$('#file_jump input').val($('#file_jump input').val()-1);
			$('#file_box>ul').eq($('#file_jump input').val()-1).show();
			$('#file_box>ul').eq($('#file_jump input').val()-1).siblings().hide();
		}
	});
	$('#file_jump>li').eq(2).click(function(){
		if($('#file_jump input').val().length == 0){
			$('#file_jump input').val(1);
			$('#file_box>ul').eq(0).show();
			$('#file_box>ul').eq($('#file_jump input').val()-1).siblings().hide();
		}else if($('#file_jump input').val() != $('#file_jump b').html() && $('#file_jump b').html() != 0){
			$('#file_jump input').val(Number($('#file_jump input').val())+1);
			$('#file_box>ul').eq($('#file_jump input').val()-1).show();
			$('#file_box>ul').eq($('#file_jump input').val()-1).siblings().hide();
		}
	});
	//内部动画样式  加载
	$('#file_box').on('click','li',function(){
		var aLi=$(this);
		$.ajax({
			type:"get",
			url:"/Signage/rest/upload/reqDispatchFileDetails?invcode="+(aLi.children('p').children('b').html()),
//			url:"php/video.php?invcode=" + (aLi.children('p').children('b').html()),
			dataType:"json",
			cache:true,
			success:function(e){
				aLi.children('div').html('');
				if(e.return_code=="success"){
					var pdfFile = '<legend>指导书文件缩略图</legend>',
						videoFile = '<legend>视频文件缩略图</legend>';
					for(var i=0;i<e.pdf.length;i++){ //指导书预览
						pdfFile += '<dl>'+e.pdf[i].name.replace(/\.\w+$/ig,'')+'<dt>'+e.pdf[i].path+'</dt></dl>';
					}
					for(var i=0;i<e.video.length;i++){ //视频预览
						videoFile += '<dl>'+e.video[i].name.replace(/\.\w+$/ig,'')+'<dt>'+e.video[i].path+'</dt></dl>';
					}
					aLi.children('div').append('<fieldset>' + pdfFile + '</fieldset>');//添加pdf
					aLi.children('div').append('<fieldset>' + videoFile + '</fieldset>');//添加视频
					aLi.children('div').slideToggle(200);
					aLi.siblings().children('div').slideUp(200);
				}else{
					aLi.children('div').html('加载失败...');
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
	});
	//pdf和视频预览
	$('#file_box ').on('click','dl',function(e){
		e.stopPropagation();
		var fileUrl="/Signage/rest/upload/reqpdffile?path="+$(this).children('dt').html();
		if( $(this).siblings('legend').html() == '指导书文件缩略图' ){
			$('#fullScreen iframe').attr('src','');
			$('#fullScreen').show();
			ifLoading();
			$.ajax({
				type:"get",
				url:fileUrl,
				success:function(){
					$('#fullScreen iframe').attr('src',fileUrl);
					ifLoading();
				},
				error:function(){
					$('#fullScreen').hide();
					$('#popup').removeClass('hidden');
					$('#popup p').html('服务器连接错误！');
					ifButton();
				}
			});
		}else if( $(this).siblings('legend').html() == '视频文件缩略图' ){
			$('#fullvideo video').attr('src','');
			$('#fullvideo').show();
			ifLoading();
			$.ajax({
				type:"get",
				url:fileUrl,
				success:function(){
					$('#fullvideo video').attr('src',fileUrl);
					ifLoading();
				},
				error:function(){
					$('#fullvideo').hide();
					$('#popup').removeClass('hidden');
					$('#popup p').html('服务器连接错误！');
					ifButton();
				}
			});
		}
	});
	//文件查找
	$('#fileQuery>button').click(function(e){
		e.preventDefault();
		fileQueryBtn();
	});
	$('#file_box div').click(function(e){
		e.stopPropagation();
		e.preventDefault();
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
					queryLi+='<li>' + jumpLi.eq(j).html().replace(/style="display: block;"/ig,'') + '</li>';
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
		$('#file_box>ul').eq( $('#file_jump input').val()-1 ).show();
		$('#file_box>ul').eq( $('#file_jump input').val()-1 ).siblings().hide();
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
//canvas画图
$(function(){
	var canvas=document.getElementById('mycanvas'),
		ctx=canvas.getContext("2d"),
		rubberbandDiv = document.getElementById("rubberbandDiv"),
		mousedown = {},//鼠标开始
		mouseup = {},//鼠标结束
		dragging = false;
	canvas.onmousedown = function(e){
		if( $('input.con_invcode').attr('readonly') == 'readonly' && ($('input.con_invcode2').attr('readonly') == 'readonly') ){
			e.preventDefault();
			mousedown.x = e.offsetX + canvas.offsetLeft;
			mousedown.y = e.offsetY + canvas.offsetTop;
			rubberbandDiv.style.left = mousedown.x + 'px';
			rubberbandDiv.style.top = mousedown.y + 'px';
			rubberbandDiv.style.display = 'inline';
			dragging = true;
		}
	}
	canvas.onmousemove = function(e){
		mouseup.x = e.offsetX + canvas.offsetLeft;
		mouseup.y = e.offsetY + canvas.offsetTop;
		e.preventDefault();
		if(dragging){
			rubberbandDiv.style.left = mouseup.x < mousedown.x ? (mouseup.x +'px') : (mousedown.x + 'px');
			rubberbandDiv.style.top = mouseup.y < mousedown.y ? (mouseup.y + 'px') : (mousedown.y + 'px');
			rubberbandDiv.style.width = Math.abs(mouseup.x - mousedown.x) + 'px';
			rubberbandDiv.style.height = Math.abs(mouseup.y - mousedown.y) + 'px';
		}
	}
	window.onmouseup = function(e){
		if(dragging){
			e.preventDefault();
			rubberbandDiv.style.width = 0;
			rubberbandDiv.style.height = 0;
			rubberbandDiv.style.display = 'none';
			dragging = false;
			var	liwidth = $('#con_box2 li').width()+1;//li宽度
			var start = parseInt((mousedown.x < mouseup.x ? (mousedown.x -20) : (mouseup.x -20)) / liwidth);//开始点位置
			var end = parseInt((mousedown.x > mouseup.x ? (mousedown.x-20) : (mouseup.x -20)) / liwidth);//结束点位置
			if(end > 17){end=17;}
			if(mouseup.x != mousedown.x){
				if(start == 0){start=1;}
				for(var i=start; i<end+1; i++){
					$('#con_box2 li').eq(i).addClass('red');
					$('#con_box2>ul:nth-child(2) li').eq(i).html( $('#con_box1>ul:nth-child(2) li').eq(i).html() ).addClass('orange');
					$('#con_box2>ul:nth-child(3) li').eq(i).html( $('#con_box1>ul:nth-child(3) li').eq(i).html() );
				}
			}else{//单机
				if(start != 0){
					if($('#con_box2 li').eq(start).hasClass('red')){
						$('#con_box2 li').eq(start).removeClass('red');
						$('#con_box2>ul:nth-child(2) li').eq(start).html( $('#con_box>ul:nth-child(2) li').eq(start).html() ).removeClass('orange');
						$('#con_box2>ul:nth-child(3) li').eq(start).html( $('#con_box>ul:nth-child(3) li').eq(start).html() );
					}else{
						$('#con_box2 li').eq(start).addClass('red');
						$('#con_box2>ul:nth-child(2) li').eq(start).html( $('#con_box1>ul:nth-child(2) li').eq(start).html() ).addClass('orange');
						$('#con_box2>ul:nth-child(3) li').eq(start).html( $('#con_box1>ul:nth-child(3) li').eq(start).html() );
					}
				}
			}
		}
	}
});
//通用设置
$(function(){
	//pdf和视频显示时间提交
	$('.publicset>li:first-child button').click(function(){
		$('#confirm7').show();
	});
	//取消提交
	$('#confirm7 button').eq(0).click(function(){
		$(this).parent().parent().hide();
	});
	//确认提交
	$('#confirm7 button').eq(1).click(function(){
		var interval ={
			"pdfinterval": '' + $('.publicset>li:first-child select').eq(0).val(),
  			"videointerval": '' + $('.publicset>li:first-child select').eq(1).val()
		};
		$('#confirm7').hide();
		$("#popup p").html("文件上传中...");
		ifButton();
		$('#popup').removeClass('hidden');
		$.ajax({
			type:"post",
			url:"/Signage/rest/upload/updateSet",
			data:JSON.stringify( interval ),
			contentType:"application/json",
			success:function(e){
				$("#popup p").html( e.return_msg );
				ifButton();
			},
			error:function(e){
				$("#popup p").html("连接服务器错误！");
				ifButton();
			}
		});
	});
	//管理流水线
	$('#line_btn').click(function(){
		if($(this).val() == '点击管理'){
			$('#line_box button').show();
			$(this).val('结束管理');
		}else{
			$('#line_box button').hide();
			$(this).val('点击管理');
		}
	});
	//增加流水线
	$('#line_add').click(function(){
		if( $(this).hasClass('addipt') && $(this).val() == '增加流水线' ){//关闭
			$('#line_ipt').animate({
				width: 0
			},200,function(){
				$('#line_ipt').hide();
				$('#line_add').css('border-top-left-radius','3px').css('border-bottom-left-radius','3px');
				$('#line_add').removeClass('addipt');
			});
		}else if($(this).val() == '增加流水线'){//展开
			$(this).css('border-top-left-radius',0).css('border-bottom-left-radius',0).addClass('addipt');
			$('#line_ipt').show().animate({
				width: "100px"
			}, 300);
		}else if($(this).val() == '确认增加'){//增加流水线
			for(var i=1; i<$('#line_box>li').length; i++){
				if($(this).prev().val().toUpperCase() == $('#line_box>li').eq(i).html().replace(/<button>.*<\/button>/ig,'')){break;}
			}
			if( i == $('#line_box>li').length ){
				$('#confirm8').show();
			}else{
				$('#popup').removeClass('hidden');
				$('#popup p').html('流水线已存在!');
				ifButton();
			}
		}
	});
	$('#confirm8 button').eq(0).click(function(){//取消提交
		$(this).parent().parent().hide();
	});
	$('#confirm8 button').eq(1).click(function(){//确认提交
		$('#confirm8').hide();
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$('#popup').removeClass('hidden');
		$.ajax({
			type:"get",
			url:"?line=" + $('#line_ipt').val(),
			dataType:'json',
			success:function(e){
				$('#popup p').html(e.return_msg);
				ifButton();
				if(e.return_msg == 'success'){
					lineRefresh();//刷新流水线
				}
			},
			error:function(){
				$('#popup p').html('连接服务器错误!');
				ifButton();
			}
		});
	});
	$('#line_ipt').on('input',function(){
		$('#line_add').val( $(this).val().length != 0 ? '确认增加' : '增加流水线');
	});
	//删除流水线
	$('#line_box').on('click','button',function(){
		$(this).parent().addClass('removeline');
		$('#confirm9').show();
	});
	$('#confirm9 button').eq(0).click(function(){//取消提交
		$(this).parent().parent().hide();
		$('#line_box li.removeline').removeClass('removeline');
	});
	$('#confirm9 button').eq(1).click(function(){//确认提交
		$('#confirm9').hide();
		$('#popup p').html('连接中请稍后...');
		ifButton();
		$('#popup').removeClass('hidden');
		$.ajax({
			type:"get",
			url:"?daleteline=" + $('#line_box li.removeline').html().replace(/<button>.*<\/button>/ig,''),
			dataType:'json',
			success:function(e){
				$('#line_box li.removeline').removeClass('removeline');
				$('#popup p').html(e.return_msg);
				ifButton();
				if(e.return_msg == 'success'){
					lineRefresh();//刷新流水线
				}
			},
			error:function(){
				$('#popup p').html('连接服务器错误!');
				ifButton();
				$('#line_box li.removeline').removeClass('removeline');
			}
		});
	});
});
//流水线刷新
function lineRefresh(){
	$.ajax({
		type:"get",
		url:"/Signage/rest/upload/getRmLineList",
//		url:"php/line.php",
		dataType:"json",
		success:function(e){
			if(e.return_code == 'success'){
				for(var i=0; i<e.data.length; i++){
					$('.con_sec').append( '<option>' + e.data[i] + '</option>' );
					$('.ope_sec').append( '<option>' + e.data[i] + '</option>' );
					$('#line_box').append('<li>' + e.data[i] + '<button>删除</button></li>');
				}
			}
		}
	});
}
window.onload=function(){
	ifLoading();
	for(var i=0,addLi=''; i<17; i++){
		addLi += '<li></li>';
	}
	$('ul.proset_invcode,ul.toset_invcode,ul.video').html(addLi);
	$.ajax({//通用视频
		type:"get",
		url:"/Signage/rest/upload/getUniversalVideoList",
//		url:"php/publicVideo.php",
		dataType: 'json',
		success: function(e){
			if(e.return_code == 'success'){
				for(var j=0; j<e.data.length; j++){
					$('#publicVideo').append( '<dl>'+e.data[j].name.replace(/\.\w+$/ig,'')+'<dt>'+e.data[j].path+'</dt></dl>' );
				}
			}
		}
	});
	lineRefresh();//刷新流水线
	$.ajax({//加载时间
		type:"get",
		url:"/Signage/rest/upload/getSet",
		dataType:"json",
		success:function(e){
			if(e.return_code == 'success'){
				$('.publicset>li:first-child select').eq(0).val( Number(e.data[0].pdfinterval) );
				$('.publicset>li:first-child select').eq(1).val( Number(e.data[0].videointerval) );
			}
		}
	});
}






