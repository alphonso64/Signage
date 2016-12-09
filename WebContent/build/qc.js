window.onload=function(){
	$.ajax({
		type:"get",
		url:"qc.php",
		dataType:"json",
		success:function(e){
			if( e.return_code == 'success' ){
				var badLi = '';
				for(var i=0; i<e.data.length; i++){
					badLi += '<li>' + e.data[i].elecode + '</li>';
				}
				$('.bad_box').append( badLi );
			}
		}
	});
}
//菜单的切换
$(function(){
	$('#main_left').on('click','li',function(){
		$(this).addClass('selected').siblings('li.selected').removeClass('selected');
		$('#main_box>div').eq($(this).index()-1).fadeIn(0).siblings('div').fadeOut(0);
	});
});

//错误选择框
$(function(){
	$('.bad_box').on('click','li',function(){
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
		$('#chose_box>div select').val('0');
		$('#chose_box>div>p>span').html('1');
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
		
		$.ajax({
			type:"post",
			url:"",
			dataType:'json',
			success: function(){
				
			},
			error: function(){
				
			}
		});
	});
});
//确认元件的多少计算li的多少
function liNumber(){
	var num=$('.bad_box>li').length;
	if(num>=20){
		
	}else if(num>=10){
		console.log(123);
	}
}
//统计图表
$(function(){
	require.config({
	    paths: {
	        echarts: 'build/dist'
	    }
	});
	require(
	    [
	        'echarts',
	        'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
	        'echarts/chart/line'
	    ],
	    function (ec) {
	    	var container = document.getElementById('chart_box');
			var resizeContainer = function () {
    			$('#chart_box').css('width',$('#main_box').width()-3+'px');
    			$('#chart_box').css('height',$(window).height()-191+'px');
			};
			resizeContainer();
	        var myChart = ec.init(container);
	      	///////
	        var option1 = {
				title: {
					x: 'center',
					text: '达峰不良个数统计'
			//		subtext: 'Rainbow bar example',
				},
				toolbox: {
					show: true,
					itemSize: 20,
					itemGap: 40,
					feature: {
					//   	dataView: {show: true, readOnly: false},
					    restore: {show: true},
					    saveAsImage: {show: true}
					}
				},
				calculable: true,
				grid: {
					borderWidth: 0,
					y: 80,
					y2: 60
				},
				xAxis: [
					{
					    type: 'category',
					    show: true,
					    data: ['abc', 'acfd', '172', '45', '45', '565', '11', '67', '5675as', 'Gauge', 'Funnel','Radar', 'Chord', 'Force', 'Map', 'Gauge', 'Funnel']
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
					    name: 'ECharts例子个数统计',
					    type: 'bar',
					    itemStyle: {
					        normal: {
					            color: function(params) {
					                var colorList = [
					                    '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
					                    '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
					                    '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0',
					                    '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
					                    '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
					                    '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0',
					                    '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
					                    '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
					                    '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0',
					                    '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
					                    '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
					                    '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
					                ];
					                return colorList[params.dataIndex]
					            },
					            label: {
					                show: true,
					                position: 'top',
					                formatter: '{b}\n{c}'
					            }
					        }
					    },
					    data: [12,21,10,4,12,5,6,5,25,23,7,24,45,35,9,16,20,3,5]
					}
				]
			};
			var option2 = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    legend: {
			        data:['直接访问', '邮件营销','联盟广告','视频广告','搜索引擎']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    yAxis : [
			        {
			            type : 'category',
			            data : ['周一','周二','周三','周四','周五','周六','周日']
			        }
			    ],
			    series : [
			        {
			            name:'直接访问',
			            type:'bar',
			            stack: '总量',
			            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
			            data:[320, 302, 301, 334, 390, 330, 320]
			        },
			        {
			            name:'邮件营销',
			            type:'bar',
			            stack: '总量',
			            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
			            data:[120, 132, 101, 134, 90, 230, 210]
			        },
			        {
			            name:'联盟广告',
			            type:'bar',
			            stack: '总量',
			            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
			            data:[220, 182, 191, 234, 290, 330, 310]
			        },
			        {
			            name:'视频广告',
			            type:'bar',
			            stack: '总量',
			            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
			            data:[150, 212, 201, 154, 190, 330, 410]
			        },
			        {
			            name:'搜索引擎',
			            type:'bar',
			            stack: '总量',
			            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
			            data:[820, 832, 901, 934, 1290, 1330, 1320]
			        }
			    ]
			};
			var option3 = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    legend: {
			        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']
			    },
			    toolbox: {
			        show : true,
			        orient: 'vertical',
			        x: 'right',
			        y: 'center',
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            data : ['周一','周二','周三','周四','周五','周六','周日']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'直接访问',
			            type:'bar',
			            data:[320, 332, 301, 334, 390, 330, 320]
			        },
			        {
			            name:'邮件营销',
			            type:'bar',
			            stack: '广告',
			            data:[120, 132, 101, 134, 90, 230, 210]
			        },
			        {
			            name:'联盟广告',
			            type:'bar',
			            stack: '广告',
			            data:[220, 182, 191, 234, 290, 330, 310]
			        },
			        {
			            name:'视频广告',
			            type:'bar',
			            stack: '广告',
			            data:[150, 232, 201, 154, 190, 330, 410]
			        },
			        {
			            name:'搜索引擎',
			            type:'bar',
			            data:[862, 1018, 964, 1026, 1679, 1600, 1570],
			            markLine : {
			                itemStyle:{
			                    normal:{
			                        lineStyle:{
			                            type: 'dashed'
			                        }
			                    }
			                },
			                data : [
			                    [{type : 'min'}, {type : 'max'}]
			                ]
			            }
			        },
			        {
			            name:'百度',
			            type:'bar',
			            barWidth : 5,
			            stack: '搜索引擎',
			            data:[620, 732, 701, 734, 1090, 1130, 1120]
			        },
			        {
			            name:'谷歌',
			            type:'bar',
			            stack: '搜索引擎',
			            data:[120, 132, 101, 134, 290, 230, 220]
			        },
			        {
			            name:'必应',
			            type:'bar',
			            stack: '搜索引擎',
			            data:[60, 72, 71, 74, 190, 130, 110]
			        },
			        {
			            name:'其他',
			            type:'bar',
			            stack: '搜索引擎',
			            data:[62, 82, 91, 84, 109, 110, 120]
			        }
			    ]
			};
			// 为echarts对象加载数据
	        myChart.setOption(option1); 
			window.onresize = function () {
			    //重置容器高宽
			    resizeContainer();
			    myChart.resize();
			};
	        
	    }
	);
})




