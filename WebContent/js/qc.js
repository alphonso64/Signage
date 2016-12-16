window.onload=function(){
	
}
//菜单的切换
$(function(){
	$('header>ul').on('click','li',function(){
		$(this).addClass('selected').siblings('li.selected').removeClass('selected');
		$('#main>div').eq( $(this).index() ).show().siblings('div').hide();
	});
});
//查询
$(function(){
	//查询
	$('.query').click(function(){
		queryClick();
	});
	//清空
	$('.empty').click(function(){
		$(this).prev().prev().removeAttr('disabled').val('').removeClass('ipt');
		$(this).prev().removeAttr('disabled');
		$('#top_left span>select').removeAttr('disabled');
		$('#top_left>p>select').removeAttr('disabled');
		$('.materiel').html('');
		$('#chart_bar').html('');
		$('#chart_pie').html('');
	});
});
function keyDown(e){
  	var ev= window.event||e;
  	if (ev.keyCode == 13){
		queryClick();
  	}
}
function queryClick(){
	if( $('.input').val().length != 0 ){
		$('#popup p').html('查询中请稍后...');
		ifButton();
		$('#popup').show();
		$.ajax({
			type:"get",
			url:"php/qc.php?station=" + $('#top_left span>select').val() + "&invcode=" + $('.input').val(),
			dataType: 'json',
			success: function(e){
				if( e.return_code == 'success' ){
					$('.input').attr('disabled','disabled').addClass('ipt');
					$('.query').attr('disabled','disabled');
					$('#top_left span>select').attr('disabled','disabled');
					$('#top_left>p>select').attr('disabled','disabled');
					$('#popup').hide();
					querySuccess();
				}else{
					$('#popup p').html( e.return_msg );
					ifButton()
				}
			},
			error: function(){
				$('#popup p').html( '连接服务器失败！' );
				ifButton()
			}
		});
	}
}
function querySuccess(){
	$.ajax({//物料的加载
		type:"get",
		url:"php/qc.php",
		dataType:"json",
		success:function(e){
			if( e.return_code == 'success' ){
				var badLi = '';
				for(var i=0; i<e.data.length; i++){
					badLi += '<li>' + e.data[i].elecode + '</li>';
				}
				$('.materiel').append( badLi );
			}
		}
	});
	$.ajax({//柱状图数据
		type:"get",
		url:"php/echarts.php",
		dataType:'json',
		success:function(e){
			if( e.return_code == 'success' ){
				charts(e);
			}
		}
	});
}
//错误选择框
$(function(){
	$('.materiel').on('click','li',function(){
		$('#chose').fadeIn(200);
	});
	//选择
	$('#chose_box>ul label').click(function(){
		$(this).addClass('checked').parent().siblings().children('label.checked').removeClass('checked');
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
	//取消
	$('.chose_cancel').click(function(){
		$('#chose').fadeOut(200);
		$('#chose_box>div>span').html('1');
		$('#chose_box>ul label.checked').removeClass('checked');
	});
	//确定
	$('.chose_confirm').click(function(e){
		e.preventDefault();
		if($('#chose_box>ul label').hasClass('checked')){
			$('#confirm').fadeIn(200);
		}
	});
	//确认提交框
	$('#confirm button').eq(0).click(function(){
		$('#confirm').fadeOut(200);
	});
	$('#confirm button').eq(1).click(function(){
		$('#confirm').hide();
		$('#chose').hide();
		$('#popup p').html('提交中请稍后...');
		ifButton();
		$('#popup').show();
		$.ajax({
			type:"post",
			url:"",
			dataType:'json',
			success: function(e){
				$('#popup p').html( e.return_msg );
				ifButton()
				$('#chose_box>div>span').html('1');
				$('#chose_box>ul label.checked').removeClass('checked');
			},
			error: function(){
				$('#popup p').html('连接服务器错误！');
				ifButton()
				$('#chose_box>div>span').html('1');
				$('#chose_box>ul label.checked').removeClass('checked');
			}
		});
	});
});
$('#popup button').click(function(){
	$(this).parent().parent().hide();
});
function ifButton(){
	if($('#popup p').html() == '查询中请稍后...' || $('#popup p').html() == '提交中请稍后...'){
		$('#popup button').hide();
	}else{
		$('#popup button').show();
	}
}
//统计图表
function charts(e){
	var pieData=[];//扇形图数据
	var barDataName = {};//柱形图名字集合
	var barDataNum = {};//柱形图数字集合
	for(var key1 in e.data){
		var pieAdd = 0;
		var barName = [];//名字
		var barNum = [];//数字
		for(var key2 in e.data[key1]){
			pieAdd += Number(e.data[key1][key2]);
			barName.push( key2 ); //名字子数组
			barNum.push( Number(e.data[key1][key2]) );//数字子数组
		}
		var pieObj = {
			value: pieAdd,
			name: key1,
			tooltip:{}
		};
		pieData.push( pieObj );
		barDataName[key1] = barName;
		barDataNum[key1] = barNum;
	}
	pieData.push( key1 );
	//////
	require.config({
	    paths: {
	        echarts: 'js/build/dist'
	    }
	});
	require(
	    [
	        'echarts',
	        'echarts/chart/bar',
	        'echarts/chart/line',
	        'echarts/chart/pie'
	    ],
	    function (ec) {
	    	var containerBar = document.getElementById('chart_bar');
	    	var containerPie = document.getElementById('chart_pie');
			var resize = function () {
    			$('#chart_bar').css('width',($('#main').width()-40)*0.6+'px');
    			$('#chart_bar').css('height',$(window).height()-250+'px');
    			$('#chart_pie').css('width',($('#main').width()-40)*0.4+'px');
    			$('#chart_pie').css('height',$(window).height()-250+'px');
			};
			resize();
	        var myChartBar = ec.init(containerBar);
	        var myChartPie = ec.init(containerPie);
	        var option1 = {
				title: {
					x: 'center',
					subtext: '2016年12月12日'
				},
				grid: {
					borderWidth: 0,
					y: 80,
					y2: 60
				},
				xAxis: [
					{
					    type: 'category',
					    show: true
					}
				],
				yAxis: [
					{
					    type: 'value',
					    show: true
					}
				],
				series: [
					{
					    name: '达峰不良品个数统计',
					    type: 'bar',
					    itemStyle: {
					        normal: {
					            color: function(params) {
					                var colorList = [
					                    '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B','#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
					                    '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0','#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
					                    '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD','#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0',
					                    '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B','#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD'
					                ];
					                return colorList[params.dataIndex]
					            },
					            label: {
					                show: true,
					                position: 'top',
					                formatter: '{b}\n{c}'
					            }
					        }
					    }
					}
				]
			};
			var option2 = {
			    title : {
			        text: '不良信息总览',
			        subtext: '2016年12月12日',
			        x:'center'
			    },
			    series : [
			        {
			            name:'不良率总览',
			            type:'pie',
			            radius : '60%',
			            center: ['50%', '55%'],
			            selectedMode: 'single',
			            selectedOffset: 15,
						data: pieData,
						itemStyle: {
							normal: {
								label: {
					                show: true,
					                formatter: '{b}\n{c}\n{d}%'
					            }
							}
						}
			        }
			    ]
			};
			for(var key1 in barDataName){
				option1.xAxis[0].data = barDataName[key1];
				option1.title.text ='"' + key1 + '" ' + '详细信息';
				break;
			}
			for(var key2 in barDataNum){
				option1.series[0].data = barDataNum[key2];
				break;
			}
	        myChartBar.setOption(option1);
	        myChartPie.setOption(option2);
			window.onresize = function(){///重置容器高宽
			    resize();
			    myChartBar.resize();
			    myChartPie.resize();
			};
			//鼠标点击事件
			var ecConfig = require('echarts/config');
			function eConsole(param) {
			    var mes = '【' + param.type + '】';
			    if (typeof param.seriesIndex != 'undefined') {
			        mes += '  seriesIndex : ' + param.seriesIndex;
			        mes += '  dataIndex : ' + param.dataIndex;
			    }
			    if (param.type == 'hover') {
		//	        document.getElementById('hover-console').innerHTML = 'Event Console : ' + mes;
			    }
			    else {
		//	        document.getElementById('console').innerHTML = mes;
			    }
			    myChartBar.clear();//清空数据
			    option1.xAxis[0].data = barDataName[param.name];
			    option1.series[0].data = barDataNum[param.name];
			    option1.title.text ='"' + param.name + '" ' + '详细信息';
			    myChartBar.setOption(option1);
			}
			myChartPie.on(ecConfig.EVENT.CLICK, eConsole);//绑定事件
	    }
	);
}




