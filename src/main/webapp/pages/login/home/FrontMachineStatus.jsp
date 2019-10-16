<%--
  Created by IntelliJ IDEA.
  User: hanweicheng
  Date: 2019/7/15 0015
  Time: 上午 10:58
  Description: 前置机状态页面
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/snowweb" prefix="snow" %>
<snow:page title="银行信息">
    <%--页面数据源引入--%>
    <snow:dataset id="frontMachineStatus" path="com.ruimin.ifs.login.dataset.FrontMachineStatus" submitMode="current"
                  init="true"></snow:dataset>
    <%--配置结果集--%>
    <snow:grid id="gridId" dataset="frontMachineStatus" sortable="true" remotesort="true" pagination="false" rownumbers="false"
               showrefresh="true"
               fieldstr="bankName,status,expireDate"></snow:grid>
    <script type="text/javascript">
        <%--dataset init属性为false（不自定查询）--%>
        setInterval(function () {
            frontMachineStatus_dataset.flushData(frontMachineStatus_dataset.pageIndex);
        }, 60000);
    </script>
</snow:page>
