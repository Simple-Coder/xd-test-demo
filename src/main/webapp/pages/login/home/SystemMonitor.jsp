<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="home" pageWidth="100%">
	<%--页面数据源引入--%>
	<snow:dataset id="HighChartsDtst" path="com.ruimin.ifs.login.dataset.HighCharts" submitMode="current"
				  init="true"></snow:dataset>
	<%--配置查询条件--%>
	<snow:query id="highChartsQueryId" dataset="HighChartsDtst"
				fieldstr="bankSelect" width="100%" btnright="50">
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
                            chart: {
                                type: 'column'
                            },
                            title: {
                                text: '系统运行监控'
                            },
                            xAxis: {
                                categories: data.xtext
                            },
                            yAxis: {
                                min: 0,
                                title: {
                                    text: '系统运行监控'
                                },
                                stackLabels: {  // 堆叠数据标签
                                    enabled: true,
                                    style: {
                                        fontWeight: 'bold',
                                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                                    }
                                }
                            },
                            legend: {
                                align: 'right',
                                x: -30,
                                verticalAlign: 'top',
                                y: 25,
                                floating: true,
                                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
                                borderColor: '#CCC',
                                borderWidth: 1,
                                shadow: false
                            },
                            tooltip: {
                                formatter: function () {
                                    return '<b>' + this.x + '</b><br/>' +
                                        this.series.name + ': ' + this.y + '<br/>' +
                                        '总量: ' + this.point.stackTotal;
                                }
                            },
                            plotOptions: {
                                column: {
                                    stacking: 'normal',
                                    dataLabels: {
                                        enabled: true,
                                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                                        style: {
                                            // 如果不需要数据标签阴影，可以将 textOutline 设置为 'none'
                                            textOutline: '1px 1px black'
                                        }
                                    }
                                }
                            },
                            series: [{
                                name: '成功量',
                                data: data.ysuccess
                            }, {
                                name: '失败量',
                                data: data.yfail
                            },{
                                name: '超时量',
                                data: data.ytimeout
                            }]
                        });
                    });}
            });
            // alert(JSON.stringify(d));
        })
        setInterval(function () {var xtext=[];
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
                    console.log(xtext)
                    yfail=data.yfail;
                    console.log(yfail)
                    ysuccess=data.ysuccess;
                    console.log(ysuccess)
                    ytotal=data.ytotal;
                    console.log(ytotal)
                    $(function() {
                        $('#container').highcharts({
                            chart: {
                                type: 'column'
                            },
                            title: {
                                text: '系统运行监控'
                            },
                            xAxis: {
                                categories: data.xtext
                            },
                            yAxis: {
                                min: 0,
                                title: {
                                    text: '系统运行监控统计'
                                },
                                stackLabels: {  // 堆叠数据标签
                                    enabled: true,
                                    style: {
                                        fontWeight: 'bold',
                                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                                    }
                                }
                            },
                            legend: {
                                align: 'right',
                                x: -30,
                                verticalAlign: 'top',
                                y: 25,
                                floating: true,
                                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
                                borderColor: '#CCC',
                                borderWidth: 1,
                                shadow: false
                            },
                            tooltip: {
                                formatter: function () {
                                    return '<b>' + this.x + '</b><br/>' +
                                        this.series.name + ': ' + this.y + '<br/>' +
                                        '总量: ' + this.point.stackTotal;
                                }
                            },
                            plotOptions: {
                                column: {
                                    stacking: 'normal',
                                    dataLabels: {
                                        enabled: true,
                                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                                        style: {
                                            // 如果不需要数据标签阴影，可以将 textOutline 设置为 'none'
                                            textOutline: '1px 1px black'
                                        }
                                    }
                                }
                            },
                            series: [{
                                name: '成功量',
                                data: data.ysuccess
                            }, {
                                name: '失败量',
                                data: data.yfail
                            },{
                                name: '超时量',
                                data: data.ytimeout
                            }]
                        });
                    });}
            });}, 60000);
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
                console.log(xtext)
                yfail=data.yfail;
                console.log(yfail)
                ysuccess=data.ysuccess;
                console.log(ysuccess)
                ytotal=data.ytotal;
                console.log(ytotal)
            $(function() {
                $('#container').highcharts({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: '系统运行监控'
                    },
                    xAxis: {
                        categories: data.xtext
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: '系统运行监控'
                        },
                        stackLabels: {  // 堆叠数据标签
                            enabled: true,
                            style: {
                                fontWeight: 'bold',
                                color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                            }
                        }
                    },
                    legend: {
                        align: 'right',
                        x: -30,
                        verticalAlign: 'top',
                        y: 25,
                        floating: true,
                        backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
                        borderColor: '#CCC',
                        borderWidth: 1,
                        shadow: false
                    },
                    tooltip: {
                        formatter: function () {
                            return '<b>' + this.x + '</b><br/>' +
                                this.series.name + ': ' + this.y + '<br/>' +
                                '总量: ' + this.point.stackTotal;
                        }
                    },
                    plotOptions: {
                        column: {
                            stacking: 'normal',
                            dataLabels: {
                                enabled: true,
                                color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                                style: {
                                    // 如果不需要数据标签阴影，可以将 textOutline 设置为 'none'
                                    textOutline: '1px 1px black'
                                }
                            }
                        }
                    },
                    series: [{
                        name: '成功量',
                        data: data.ysuccess
                    }, {
                        name: '失败量',
                        data: data.yfail
                    },{
                        name: '超时量',
                        data: data.ytimeout
                    }]
                });
            });}
        });


	</script>
	<script src="${pageContext.request.contextPath}/public/lib/highcharts/highcharts.js"></script>
	<script src="${pageContext.request.contextPath}/public/lib/highcharts/modules/exporting.js"></script>

	

	<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>


</snow:page>
