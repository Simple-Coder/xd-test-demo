<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="接入系统管理">
	<snow:dataset id="TblBeChannel" path="com.ruimin.ifs.mng.dataset.TblBeChannel" init="true"></snow:dataset>
	<snow:query id="queryId" dataset="TblBeChannel" fieldstr="queryChannelCode,queryChannelName" label="请输入查询条件"></snow:query>
	<snow:grid dataset="TblBeChannel"  sortable="true" remotesort="true" id="gridId" fieldstr="channelCode[200],channelName[200],openTime[200],closeTime[200],opr[200]" paginationbar="btnAdd"></snow:grid>

	<snow:window id="windowModifyId" title="接入系统编辑" modal="true" closable="true" buttons="btnSave">
		<snow:form id="formModifyId" labelwidth="80" dataset="TblBeChannel" border="false" label="接入系统管理 --> 编辑 --> 接入系统编辑" fieldstr="uuid,channelCode,channelName,openTime,closeTime,channelDesc" collapsible="false" colnum="2"></snow:form>
		<snow:button id="btnSave" dataset="TblBeChannel" hidden="true"></snow:button>
	</snow:window>
	<snow:window id="windowDetailId" title="接入系统详情" modal="true" closable="true" buttons="btnCancel">
		<snow:form id="formDetailId" labelwidth="80" dataset="TblBeChannel" border="false" label="接入系统管理 --> 详情 --> 参数详情" fieldstr="uuid,channelCode,channelName,openTime,closeTime,channelDesc,createDate,lastUpdateUser,lastUpdateTime" collapsible="false" colnum="2"></snow:form>
		<snow:button id="btnCancel" desc="确定" hidden="true"></snow:button>
	</snow:window>
	<snow:window id="windowAddId" title="接入系统 --> 新增" modal="true" closable="true" buttons="btnSave1">
		<snow:form id="formAddId" dataset="TblBeChannel" border="false" label="接入系统 --> 新增 --> 数据新增" fieldstr="channelCode,channelName,openTime,closeTime,channelDesc" collapsible="false" colnum="4"></snow:form>
		<snow:button id="btnSave1" dataset="TblBeChannel" hidden="true"></snow:button>
	</snow:window>
	<snow:button id="btnDelete" dataset="TblBeChannel" hidden="true"></snow:button>

	<script>
		function gridId_opr_onRefresh(record, fieldId, fieldValue){
			if(record){
				var paramId = record.getValue("uuid");
				var magicId = record.getValue("channelCode");
				return "<i class='fa fa-search-plus'></i>&nbsp;<a href=\"JavaScript:onClickDetail('"+paramId+"','"+magicId+"')\">详情</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickModify('"+paramId+"','"+magicId+"')\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:btnDel_onClick('"+paramId+"')\">删除</a>";
			}
		}
		function onClickDetail(paramId,magicId){
			TblBeChannel_dataset.setFieldReadOnly("channelCode",true);
			TblBeChannel_dataset.setFieldReadOnly("channelName",true);
			TblBeChannel_dataset.setFieldReadOnly("openTime",true);
			TblBeChannel_dataset.setFieldReadOnly("closeTime",true);
			TblBeChannel_dataset.setFieldReadOnly("channelDesc",true);
			window_windowDetailId.open();
		}
		function onClickModify(paramId,magicId){
			TblBeChannel_dataset.setFieldReadOnly("channelCode",false);
			TblBeChannel_dataset.setFieldReadOnly("channelName",false);
			TblBeChannel_dataset.setFieldReadOnly("openTime",false);
			TblBeChannel_dataset.setFieldReadOnly("closeTime",false);
			TblBeChannel_dataset.setFieldReadOnly("channelDesc",false);
			window_windowModifyId.open();
		}
		function btnCancel_onClick(){
			window_windowDetailId.close();
		}
		function window_windowModifyId_beforeClose(wmf){
			TblBeChannel_dataset.cancelRecord();
		}

		function btnDel_onClick(uuid){
			$.confirm("是否确认删除?", function() {
				TblBeChannel_dataset.setParameter("uuid", uuid);
				btnDelete.click();
			}, function() {
				return false;
			});
		}

		function btnDelete_needCheck(btn){
			return false;
		}
		function btnDelete_postSubmit(){
			$.success("操作成功!", function() {
				TblBeChannel_dataset.flushData(TblBeChannel_dataset.pageIndex);
			});
		}

		function btnAdd_onClick() {
			//TblBeChannel_dataset.setFieldReadOnly("dataTypeNo", false);
			window_windowAddId.open();
		}
		function btnSave_postSubmit(){
			$.success("操作成功!", function() {
				window_windowModifyId.close();
				TblBeChannel_dataset.flushData(TblBeChannel_dataset.pageIndex);
			});
		}

		function btnSave1_postSubmit() {
			$.success("操作成功!", function () {
				window_windowAddId.close();
				TblBeChannel_dataset.flushData(TblBeChannel_dataset.pageIndex);
			});
		}
	</script>
</snow:page>
