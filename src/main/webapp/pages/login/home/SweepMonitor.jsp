<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="home">
	<%--&lt;%&ndash;页面数据源引入&ndash;%&gt;--%>
	<%--<snow:dataset id="HighChartsDtst" path="com.ruimin.ifs.login.dataset.HighCharts" submitMode="current"--%>
				  <%--init="true"></snow:dataset>--%>
	<%--&lt;%&ndash;配置查询条件&ndash;%&gt;--%>
	<%--<snow:query id="highChartsQueryId" dataset="HighChartsDtst"--%>
				<%--fieldstr="tradeStartTime,tradeEndTime,bankSelect">--%>
	<%--</snow:query>--%>

	<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/HighChartsService.js"> </script>
	<script type="text/javascript">

	</script>
	<script type="text/javascript">
        var ytext=[];
        var sweepSuccess=[];
        var sweepOther=[];
        var sweepFailure=[];
        var entrySuccess=[];
        var entryFailure=[];
        var obj1 = new Object();
        obj1.sex="我是男的"
        //初始化所有图表
        HighChartsService.getSweepDatas(obj1,function (data)
		{
            if (data){
                console.log(data)

                ytext=data.ytext;
                // console.log(ytext)

                sweepSuccess=data.sweepSuccess;
                // console.log(sweepSuccess)

                sweepOther=data.sweepOther;
                // console.log(sweepOther)

                sweepFailure=data.sweepFailure;
                // console.log(sweepFailure)

                entrySuccess=data.entrySuccess;
                // console.log(entrySuccess)

                entryFailure=data.entryFailure;
                // console.log(entryFailure)
            $(function() {
                $('#container').highcharts({
                    chart: {
                        type: 'bar'
                    },
                    title: {
                        text: '归集监控图'
                    },
                    subtitle: {
                        text: '数据来源: snow'
                    },
                    xAxis: {
                        categories: data.ytext,
                        title: {
                            text: null
                        }
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: '归集总量',
                            align: 'high'
                        },
                        labels: {
                            overflow: 'justify'
                        }
                    },
                    tooltip: {
                        valueSuffix: 'a'
                    },
                    plotOptions: {
                        bar: {
                            dataLabels: {
                                enabled: true,
                                allowOverlap: true // 允许数据标签重叠
                            }
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'top',
                        x: -40,
                        y: 100,
                        floating: true,
                        borderWidth: 1,
                        backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
                        shadow: true
                    },
                    series: [{
                        name: '归集成功',
                        data: data.sweepSuccess
                    }, {
                        name: '提交归集',
                        data: data.sweepOther
                    },{
                        name: '归集失败',
                        data: data.sweepFailure
                    },{
                        name: '入账成功',
                        data: data.entrySuccess
                    }, {
                        name: '入账失败',
                        data: data.entryFailure
                    }]
                });
            });}
        });
        setInterval(function () {
            var ytext=[];
            var sweepSuccess=[];
            var sweepOther=[];
            var sweepFailure=[];
            var entrySuccess=[];
            var entryFailure=[];
            var obj1 = new Object();
            obj1.sex="我是男的"
            //初始化所有图表
            HighChartsService.getSweepDatas(obj1,function (data)
            {
                if (data){
                    console.log(data)

                    ytext=data.ytext;
                    // console.log(ytext)

                    sweepSuccess=data.sweepSuccess;
                    // console.log(sweepSuccess)

                    sweepOther=data.sweepOther;
                    // console.log(sweepOther)

                    sweepFailure=data.sweepFailure;
                    // console.log(sweepFailure)

                    entrySuccess=data.entrySuccess;
                    // console.log(entrySuccess)

                    entryFailure=data.entryFailure;
                    // console.log(entryFailure)
                    $(function() {
                        $('#container').highcharts({
                            chart: {
                                type: 'bar'
                            },
                            title: {
                                text: '归集监控图'
                            },
                            subtitle: {
                                text: '数据来源: snow'
                            },
                            xAxis: {
                                categories: data.ytext,
                                title: {
                                    text: null
                                }
                            },
                            yAxis: {
                                min: 0,
                                title: {
                                    text: '归集总量',
                                    align: 'high'
                                },
                                labels: {
                                    overflow: 'justify'
                                }
                            },
                            tooltip: {
                                valueSuffix: 'a'
                            },
                            plotOptions: {
                                bar: {
                                    dataLabels: {
                                        enabled: true,
                                        allowOverlap: true // 允许数据标签重叠
                                    }
                                }
                            },
                            legend: {
                                layout: 'vertical',
                                align: 'right',
                                verticalAlign: 'top',
                                x: -40,
                                y: 100,
                                floating: true,
                                borderWidth: 1,
                                backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
                                shadow: true
                            },
                            series: [{
                                name: '归集成功',
                                data: data.sweepSuccess
                            }, {
                                name: '提交归集',
                                data: data.sweepOther
                            },{
                                name: '归集失败',
                                data: data.sweepFailure
                            },{
                                name: '入账成功',
                                data: data.entrySuccess
                            }, {
                                name: '入账失败',
                                data: data.entryFailure
                            }]
                        });
                    });}
            });
		}, 60000);
	</script>
	<script src="${pageContext.request.contextPath}/public/lib/highcharts/highcharts.js"></script>
	<script src="${pageContext.request.contextPath}/public/lib/highcharts/modules/exporting.js"></script>

	

	<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>


</snow:page>
