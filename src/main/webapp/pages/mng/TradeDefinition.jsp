<%--
  Created by IntelliJ IDEA.
  User: hanweicheng
  Date: 2019/7/15 0015
  Time: 上午 10:58
  Description: 银行信息页面
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/snowweb" prefix="snow" %>
<snow:page title="交易定义管理">
	<%--页面数据源引入--%>
	<snow:dataset id="tradeDefinition" path="com.ruimin.ifs.mng.dataset.TradeDefinition" submitMode="current"
				  init="true"></snow:dataset>
	<%--配置查询条件--%>
	<snow:query label="请输入查询条件" id="queryId" dataset="tradeDefinition" fieldstr="queryTradeName,queryTradeCode"></snow:query>
	<%--配置结果集--%>
	<snow:grid id="gridId" dataset="tradeDefinition" sortable="true" remotesort="true"
			   fieldstr="tradeName,tradeCode,tradeStatus,tradeOpenTime,tradeCloseTime,opr[160]"
			   paginationbar="btnAdd"></snow:grid>
	<!--新增页面窗口配置-->
	<snow:window id="tradeDefinitionAdd" title="交易定义管理 --> 新增" modal="true" closable="true" buttons="btnSaveAdd">
		<snow:form id="tradeDefinitionAddForm" dataset="tradeDefinition" border="false" label="交易定义管理 --> 新增"
				   fieldstr="tradeName,tradeCode,tradeOpenTime,tradeCloseTime,tradeStatus" collapsible="false" colnum="4"></snow:form>
		<snow:button id="btnSaveAdd" dataset="tradeDefinition" hidden="true"></snow:button>
	</snow:window>
	<!--修改页面窗口配置-->
	<snow:window id="tradeDefinitionUpdate" title="银行信息 --> 修改" modal="true" closable="true" buttons="btnSaveUpdate">
		<snow:form id="tradeDefinitionUpdateForm" dataset="tradeDefinition" border="false" label="银行信息 --> 修改"
				   fieldstr="tradeName,tradeCode,tradeOpenTime,tradeCloseTime,tradeStatus" collapsible="false" colnum="4"></snow:form>
		<snow:button id="btnSaveUpdate" dataset="tradeDefinition" hidden="true"></snow:button>
	</snow:window>
	<snow:button id="btnDelete" dataset="tradeDefinition" hidden="true"></snow:button>
	<script>
		function gridId_opr_onRefresh(record, fieldId, fieldValue) {
			if (record) {
				var uuid = record.getValue("uuid");
				return "<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickUpdate()\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-trash-o'></i>&nbsp;<a href=\"JavaScript:onClickDelete('" + uuid + "')\">删除</a>";
			}
		}

		function onClickUpdate() {
			tradeDefinition_dataset.setFieldReadOnly("uuid", false);
			window_tradeDefinitionUpdate.open();
		}

		function btnAdd_onClick() {
			tradeDefinition_dataset.setFieldReadOnly("uuid", false);
			window_tradeDefinitionAdd.open();
		}

		function btnDelete_needCheck(btn) {
			return false;
		}

		function onClickDelete(uuid) {
			$.confirm("是否确认删除?", function () {
				// locate(id);
				btnDelete.click();
			}, function () {
				return false;
			});
		}

		function window_tradeDefinitionUpdate_beforeClose(wmf) {
			tradeDefinition_dataset.cancelRecord();
		}

		function window_tradeDefinitionAdd_beforeClose(wmf) {
			tradeDefinition_dataset.cancelRecord();
		}

		function btnDelete_postSubmit() {
			$.success("操作成功!", function () {
				tradeDefinition_dataset.flushData(tradeDefinition_dataset.pageIndex);
			});
		}

		function btnSaveAdd_postSubmit() {
			$.success("操作成功!", function () {
				window_tradeDefinitionAdd.close();
				tradeDefinition_dataset.flushData(tradeDefinition_dataset.pageIndex);
			});
		}

		function btnSaveUpdate_postSubmit() {
			$.success("操作成功!", function () {
				window_tradeDefinitionUpdate.close();
				tradeDefinition_dataset.flushData(tradeDefinition_dataset.pageIndex);
			});
		}

		//定位一条记录
		// 		function locate(id) {
		// 			var record = DataDicEntry_dataset.find(["id"],[id]);
		// 			if (record) {
		// 				DataDicEntry_dataset.setRecord(record);
		// 			}
		// 		}
	</script>
</snow:page>
