<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="日志查询">
	<snow:dataset id="bizLogQuery" path="com.ruimin.ifs.mng.dataset.BizLogQuery" init="true"></snow:dataset>
	<snow:query id="queryId" dataset="bizLogQuery" fieldstr="记录日期{startDate|-|endDate}[3],oprcode1" label="请输入查询条件" colnum="6"></snow:query>
	<snow:grid dataset="bizLogQuery" id="gridId" fieldstr="txnDate[100],tlrName[100],txnStartTime[100],txnEndTime[100],ipAdr[100],txnBizLog1" exporter="exporterId"></snow:grid>
	<snow:exporter dataset="bizLogQuery" id="exporterId" type="XLS|CSV" fieldstr="txnDate,tlrName,txnStartTime,txnEndTime,ipAdr,txnBizLog1"></snow:exporter>
</snow:page>
