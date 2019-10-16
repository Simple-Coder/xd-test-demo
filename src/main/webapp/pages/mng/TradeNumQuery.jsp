<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="交易量统计">
	<snow:dataset id="tradeNumQuery" path="com.ruimin.ifs.mng.dataset.TradeNumQuery" init="true"></snow:dataset>
	<snow:query id="queryId" dataset="tradeNumQuery" fieldstr="queryDimension,tradeTimeStart,tradeTimeEnd"></snow:query>
	<snow:grid dataset="tradeNumQuery"  id="gridId" fieldstr="respSys,tranCode,totalNum,successNum,successPer,failureNum,failurePer,timeOutNum" ></snow:grid>

</snow:page>
