<%--
  Created by IntelliJ IDEA.
  User: hanweicheng
  Date: 2019/9/26 0015
  Time: 下午 17点55分
  Description: 短息管理
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/snowweb" prefix="snow" %>
<snow:page title="短信管理">
	<%--页面数据源引入--%>
	<snow:dataset id="sms" path="com.ruimin.ifs.mng.dataset.SMS" submitMode="current"
				  init="true"></snow:dataset>
	<%--配置查询条件--%>
	<snow:query label="请输入查询条件" id="queryId" dataset="sms" fieldstr="querySmsType,queryStatus"></snow:query>
	<%--配置结果集--%>
	<snow:grid id="gridId" dataset="sms" sortable="true" remotesort="true"
			   fieldstr="smsType,phoneNumber,smsMessage,status"
			   ></snow:grid>
	<!--新增页面窗口配置-->
	<%--<snow:window id="smsAdd" title="交易定义管理 --> 新增" modal="true" closable="true" buttons="btnSaveAdd">
		<snow:form id="smsAddForm" dataset="sms" border="false" label="交易定义管理 --> 新增"
				   fieldstr="smsType,phoneNumber,smsMessage,status" collapsible="false" colnum="4"></snow:form>
		<snow:button id="btnSaveAdd" dataset="sms" hidden="true"></snow:button>
	</snow:window>--%>
	<!--修改页面窗口配置-->
	<%--<snow:window id="smsUpdate" title="银行信息 --> 修改" modal="true" closable="true" buttons="btnSaveUpdate">
		<snow:form id="smsUpdateForm" dataset="sms" border="false" label="银行信息 --> 修改"
				   fieldstr="smsType,phoneNumber,smsMessage,status" collapsible="false" colnum="4"></snow:form>
		<snow:button id="btnSaveUpdate" dataset="sms" hidden="true"></snow:button>
	</snow:window>
	<snow:button id="btnDelete" dataset="sms" hidden="true"></snow:button>--%>
	<script>
		/*function gridId_opr_onRefresh(record, fieldId, fieldValue) {
			if (record) {
				var uuid = record.getValue("uuid");
				return "<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickUpdate()\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-trash-o'></i>&nbsp;<a href=\"JavaScript:onClickDelete('" + uuid + "')\">删除</a>";
			}
		}

		function onClickUpdate() {
			sms_dataset.setFieldReadOnly("uuid", false);
			window_smsUpdate.open();
		}

		function btnAdd_onClick() {
			sms_dataset.setFieldReadOnly("uuid", false);
			window_smsAdd.open();
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

		function window_smsUpdate_beforeClose(wmf) {
			sms_dataset.cancelRecord();
		}

		function window_smsAdd_beforeClose(wmf) {
			sms_dataset.cancelRecord();
		}

		function btnDelete_postSubmit() {
			$.success("操作成功!", function () {
				sms_dataset.flushData(sms_dataset.pageIndex);
			});
		}

		function btnSaveAdd_postSubmit() {
			$.success("操作成功!", function () {
				window_smsAdd.close();
				sms_dataset.flushData(sms_dataset.pageIndex);
			});
		}

		function btnSaveUpdate_postSubmit() {
			$.success("操作成功!", function () {
				window_smsUpdate.close();
				sms_dataset.flushData(sms_dataset.pageIndex);
			});
		}*/

		//定位一条记录
		// 		function locate(id) {
		// 			var record = DataDicEntry_dataset.find(["id"],[id]);
		// 			if (record) {
		// 				DataDicEntry_dataset.setRecord(record);
		// 			}
		// 		}
	</script>
</snow:page>
