<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="home">
	<%--页面数据源引入--%>
	<snow:dataset id="HighChartsDtst" path="com.ruimin.ifs.login.dataset.HighCharts" submitMode="current"
				  init="true"></snow:dataset>
	<%--配置查询条件--%>
	<snow:query id="highChartsQueryId" dataset="HighChartsDtst"
				fieldstr="tradeStartTime,tradeEndTime,bankSelect">
	</snow:query>
	<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/HighChartsService.js"> </script>
	<script type="text/javascript">

	</script>
	<script type="text/javascript">
        var d = {};
        $("#HighChartsDtst_interface_dataset_highChartsQueryId").click(function ()
        {
           var t = $("#form_highChartsQueryId").serializeArray();
           $.each(t,function () {
               d[this.name] = this.value;
           })
            var obj1 = new Object();
            obj1.tradeStartTime=d.tradeStartTime;
            obj1.tradeEndTime=d.tradeEndTime;
            obj1.bankSelect=d.bankSelect;
            HighChartsService.getDatas(obj1,function (data) {
                if (data){
                    console.log(data)
                    xtext=data.xtext;
                    // console.log(xtext)
                    yfail=data.yfail;
                    // console.log(yfail)
                    ysuccess=data.ysuccess;
                    // console.log(ysuccess)
                    ytotal=data.ytotal;
                    // console.log(ytotal)
                    $(function() {
                        $('#container').highcharts({
                            title : {
                                text : '系统运行监控'
                            },
                            xAxis : {
                                categories : data.xtext
                            },
                            labels : {
                                items : [ {
                                    html : '总交易量占比（%）',
                                    style : {
                                        left : '80',
                                        top : '-50',
                                        color : (Highcharts.theme && Highcharts.theme.textColor) || 'black'
                                    }
                                } ]
                            },
                            plotOptions: {
                                pie: {
                                    allowPointSelect: true,
                                    cursor: 'pointer',
                                    dataLabels: {
                                        enabled: true,
                                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                        style: {
                                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                        }
                                    }
                                }
                            },
                            series : [ {
                                type : 'column',
                                name : '成功量',
                                data : data.ysuccess,
                                color:"#7cb5ec"
                            }, {
                                type : 'column',
                                name : '失败量',
                                data : data.yfail,
                                color:"#B0C4DE"
                            }, {
                                type : 'column',
                                name : '超时量',
                                data : data.ytimeout,
                                color:"#D2691E"
                            },{
                                type : 'spline',
                                name : '总量',
                                data : data.ytotal,
                                marker : {
                                    lineWidth : 2,
                                    lineColor : Highcharts.getOptions().colors[3],
                                    fillColor : 'white'
                                }
                            }, {
                                type : 'pie',
                                name : '总交易量占比',
                                data : [ {
                                    name : '成功率',
                                    y : parseFloat(data.piesuccess),
                                    // color : Highcharts.getOptions().colors[5]
                                    color : "#7cb5ec"
                                }, {
                                    name : '超时率',
                                    y : parseFloat(data.pietimeout),
                                    // color : Highcharts.getOptions().colors[5]
                                    color : "#D2691E"
                                },{
                                    name : '失败率',
                                    y : parseFloat(data.piefail),
                                    color : "#B0C4DE"
                                } ],
                                center : [ 70, 0 ],
                                size : 90,
                                showInLegend : false,
                                dataLabels : {
                                    enabled : false,
                                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                                }
                            } ]
                        });
                    });}
            });
            // alert(JSON.stringify(d));
        })
        var xtext=[];
        var yfail=[1];
        var ysuccess=[];
        var ytotal=[];
        var obj1 = new Object();
        obj1.tradeStartTime=d.tradeStartTime;
        obj1.tradeEndTime=d.tradeEndTime;
        obj1.bankSelect=d.bankSelect;
        //初始化所有图表
        HighChartsService.getDatas(obj1,function (data) {
            if (data){
                console.log(data)
                xtext=data.xtext;
                // console.log(xtext)
                yfail=data.yfail;
                // console.log(yfail)
                ysuccess=data.ysuccess;
                // console.log(ysuccess)
                ytotal=data.ytotal;
                // console.log(ytotal)
            $(function() {
                $('#container').highcharts({
                    title : {
                        text : '系统运行监控'
                    },
                    xAxis : {
                        categories : data.xtext
                    },
                    labels : {
                        items : [ {
                            html : '总交易量占比（%）',
                            style : {
                                left : '80',
                                top : '-50',
                                color : (Highcharts.theme && Highcharts.theme.textColor) || 'black'
                            }
                        } ]
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                style: {
                                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                }
                            }
                        }
                    },
                    series : [ {
                        type : 'column',
                        name : '成功量',
                        data : data.ysuccess,
						color:"#7cb5ec"
                    }, {
                        type : 'column',
                        name : '失败量',
                        data : data.yfail,
                        color:"#B0C4DE"
                    }, {
                        type : 'column',
                        name : '超时量',
                        data : data.ytimeout,
                        color:"#D2691E"
                    },{
                        type : 'spline',
                        name : '总量',
                        data : data.ytotal,
                        marker : {
                            lineWidth : 2,
                            lineColor : Highcharts.getOptions().colors[3],
                            fillColor : 'white'
                        }
                    }, {
                        type : 'pie',
                        name : '总交易量占比',
                        data : [ {
                            name : '成功率',
                            y : parseFloat(data.piesuccess),
                            // color : Highcharts.getOptions().colors[5]
                            color : "#7cb5ec"
                        }, {
                            name : '失败率',
                            y : parseFloat(data.piefail),
                            color : "#B0C4DE"
                        },
						{
							name : '超时率',
							y : parseFloat(data.pietimeout),
							// color : Highcharts.getOptions().colors[5]
							color : "#D2691E"
						}],
                        center : [ 70, 0 ],
                        size : 90,
                        showInLegend : false,
                        dataLabels : {
                            enabled : false,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                        }
                    } ]
                });
            });}
        });
	</script>
	<script src="${pageContext.request.contextPath}/public/lib/highcharts/highcharts.js"></script>
	<script src="${pageContext.request.contextPath}/public/lib/highcharts/modules/exporting.js"></script>

	

	<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>


</snow:page>
