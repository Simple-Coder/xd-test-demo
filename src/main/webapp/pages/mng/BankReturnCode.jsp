<%--
  Created by IntelliJ IDEA.
  User: hanweicheng
  Date: 2019/9/26 0015
  Time: 下午 17点10分
  Description: 接入银行返回码配置
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/snowweb" prefix="snow" %>
<snow:page title="接入银行返回码配置">
	<%--页面数据源引入--%>
	<snow:dataset id="beBankReturnCode" path="com.ruimin.ifs.mng.dataset.BeBankReturnCode" submitMode="current"
				  init="true"></snow:dataset>
	<%--配置查询条件--%>
	<snow:query label="请输入查询条件" id="queryId" dataset="beBankReturnCode" fieldstr="queryBankCode,queryBeReturnCode"></snow:query>
	<%--配置结果集--%>
	<snow:grid id="gridId" dataset="beBankReturnCode" sortable="true" remotesort="true"
			   fieldstr="bankCode,beReturnCode,returnCode,returnCodeDesc,status,opr[160]"
			   paginationbar="btnAdd"></snow:grid>
	<!--新增页面窗口配置-->
	<snow:window id="beBankReturnCodeAdd" title="接入银行返回码配置 --> 新增" modal="true" closable="true" buttons="btnSaveAdd">
		<snow:form id="beBankReturnCodeAddForm" dataset="beBankReturnCode" border="false" label="接入银行返回码配置 --> 新增"
				   fieldstr="bankCode,beReturnCode,returnCode,returnCodeDesc,status" collapsible="false" colnum="4"></snow:form>
		<snow:button id="btnSaveAdd" dataset="beBankReturnCode" hidden="true"></snow:button>
	</snow:window>
	<!--修改页面窗口配置-->
	<snow:window id="beBankReturnCodeUpdate" title="接入银行返回码配置 --> 修改" modal="true" closable="true" buttons="btnSaveUpdate">
		<snow:form id="beBankReturnCodeUpdateForm" dataset="beBankReturnCode" border="false" label="接入银行返回码配置 --> 修改"
				   fieldstr="bankCode,beReturnCode,returnCode,returnCodeDesc,status" collapsible="false" colnum="4"></snow:form>
		<snow:button id="btnSaveUpdate" dataset="beBankReturnCode" hidden="true"></snow:button>
	</snow:window>
	<snow:button id="btnDelete" dataset="beBankReturnCode" hidden="true"></snow:button>
	<script>
		function gridId_opr_onRefresh(record, fieldId, fieldValue) {
			if (record) {
				var uuid = record.getValue("uuid");
				return "<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickUpdate()\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-trash-o'></i>&nbsp;<a href=\"JavaScript:onClickDelete('" + uuid + "')\">删除</a>";
			}
		}

		function onClickUpdate() {
			beBankReturnCode_dataset.setFieldReadOnly("uuid", false);
			window_beBankReturnCodeUpdate.open();
		}

		function btnAdd_onClick() {
			beBankReturnCode_dataset.setFieldReadOnly("uuid", false);
			window_beBankReturnCodeAdd.open();
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

		function window_beBankReturnCodeUpdate_beforeClose(wmf) {
			beBankReturnCode_dataset.cancelRecord();
		}

		function window_beBankReturnCodeAdd_beforeClose(wmf) {
			beBankReturnCode_dataset.cancelRecord();
		}

		function btnDelete_postSubmit() {
			$.success("操作成功!", function () {
				beBankReturnCode_dataset.flushData(beBankReturnCode_dataset.pageIndex);
			});
		}

		function btnSaveAdd_postSubmit() {
			$.success("操作成功!", function () {
				window_beBankReturnCodeAdd.close();
				beBankReturnCode_dataset.flushData(beBankReturnCode_dataset.pageIndex);
			});
		}

		function btnSaveUpdate_postSubmit() {
			$.success("操作成功!", function () {
				window_beBankReturnCodeUpdate.close();
				beBankReturnCode_dataset.flushData(beBankReturnCode_dataset.pageIndex);
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
