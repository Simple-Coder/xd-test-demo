<%--
  Created by IntelliJ IDEA.
  User: hanweicheng
  Date: 2019/7/15 0015
  Time: 上午 10:58
  Description: 支付监控页面
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/snowweb" prefix="snow" %>
<snow:page title="银行信息">
    <%--页面数据源引入--%>
    <snow:dataset id="RspNumMonitor" path="com.ruimin.ifs.login.dataset.RspNumMonitor" submitMode="current"
                  init="true"></snow:dataset>
    <%--配置结果集--%>
    <snow:grid id="gridId" dataset="RspNumMonitor" sortable="true" remotesort="true" pagination="false" rownumbers="false"
               showrefresh="true"
               fieldstr="respCode,respName,totalNum,totalPer"></snow:grid>
    <script type="text/javascript">
        <%--dataset init属性为false（不自定查询）--%>
        setInterval(function () {
            RspNumMonitor_dataset.flushData(RspNumMonitor_dataset.pageIndex);
        }, 60000);
    </script>
</snow:page>
