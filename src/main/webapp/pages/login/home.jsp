<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<%@ taglib prefix="flowlayout" uri="/tags/snowweb" %>
<snow:page title="home">
    <style type="text/css">
        html,body{height:95%}
        .loginli {
            padding-top: 2px;
            text-indent: 1em;
            list-style: none;
            background:  3px 50% no-repeat;
            font-family: "Verdana";
            font-size: 9pt;
            text-align: left;
            height: 23px;
        }
        .loginli span{
            color: green;
        }
    </style>
    <% String height = "428"; %><!-- 总行的首页豆腐干高度 -->
    <%-- 	<% if(!"1".equals(userInfo.getBrClass())){ %> --%>
    <% height = "681"; %><!-- 非总行首页的豆腐干高度 -->
    <%-- 	<% } %> --%>

    <snow:portal id="main1">
        <snow:portalgroup width="49%" id="main1-1">
            <snow:portalpanel title="系统监控" id="panel1-1-1" height="550" icon="fa fa-bullhorn"  url="/pages/login/home/SystemMonitor.jsp" />
        </snow:portalgroup>
        <snow:portalgroup width="49%" id="main1-2">
            <snow:portalpanel title="归集监控" id="panel1-2-1" height="550" icon="fa fa-calendar" url="/pages/login/home/SweepMonitor.jsp" />
        </snow:portalgroup>
    </snow:portal>
    <snow:portal id="main2">
        <snow:portalgroup width="49%" id="main2-1">
            <snow:portalpanel title="前置机状态" id="main2-1-1" height="49%" icon="fa fa-list" url="/pages/login/home/FrontMachineStatus.jsp" />
            <snow:portalpanel title="支付监控" id="main2-1-2" height="49%" icon="fa fa-bell" url="/pages/login/home/PayMonitor.jsp" />
        </snow:portalgroup>
        <snow:portalgroup width="49%" id="main2-2">
            <snow:portalpanel title="响应统计" id="main2-2-1" height="49%" icon="fa fa-bullhorn"  url="/pages/login/home/RspNumMonitor.jsp" />
            <snow:portalpanel title="响应时长" id="main2-2-2" height="49%" icon="fa fa-paperclip"  url="/pages/login/home/RspTimeMonitor.jsp" />
        </snow:portalgroup>
    </snow:portal>
    <!-- 	<div style="margin-top: -20px;"> -->
    <%-- 		<snow:portal id="main_1"> --%>
    <%-- 			<snow:portalgroup width="71%" id="group4"> --%>
    <%-- 				<snow:portalpanel title="查询" id="panel4-1" height="197" icon="fa fa-search" url="/pages/login/home/home_query.jsp"> --%>
    <%-- 				</snow:portalpanel> --%>
    <%-- 			</snow:portalgroup> --%>
    <%-- 			<snow:portalgroup width="26%" id="group5"> --%>
    <%-- 				<snow:portalpanel title="常用功能" id="panel5-1" height="197" icon="fa fa-paperclip"  url="/pages/login/home/home_often.jsp"> --%>
    <%-- 				</snow:portalpanel> --%>
    <%-- 			</snow:portalgroup> --%>
    <%-- 		</snow:portal> --%>

    <!-- 	</div> -->

</snow:page>
