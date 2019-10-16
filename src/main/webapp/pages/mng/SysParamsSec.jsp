<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="系统参数设置">
	<snow:dataset id="SysParamsSec" path="com.ruimin.ifs.mng.dataset.SysParamsSec" init="true"></snow:dataset>
	<snow:query id="queryId" dataset="SysParamsSec" fieldstr="queryParamId,queryOprcode1" label="请输入查询条件"></snow:query>
	<snow:grid dataset="SysParamsSec" id="gridId" fieldstr="paramId[100],magicId[140],paramValueTx[100],desc0,opr[140]" exporter="exporterId"></snow:grid>
	<snow:exporter dataset="SysParamsSec" id="exporterId" type="XLS|CSV" fieldstr="paramId,magicId,paramValueTx,desc0"></snow:exporter>
	<snow:window id="windowModifyId" title="系统参数编辑" modal="true" closable="true" buttons="btnSave">
		<snow:form id="formModifyId" labelwidth="80" dataset="SysParamsSec" border="false" label="系统参数 --> 编辑 --> 参数设置" fieldstr="paramId,magicId,paramValueTx,desc0" collapsible="false" colnum="2"></snow:form>
		<snow:button id="btnSave" dataset="SysParamsSec" hidden="true"></snow:button>
	</snow:window>
	<snow:window id="windowDetailId" title="系统参数详情" modal="true" closable="true" buttons="btnCancel">
		<snow:form id="formDetailId" labelwidth="80" dataset="SysParamsSec" border="false" label="系统参数 --> 详情 --> 参数详情" fieldstr="paramId,magicId,paramValueTx,desc0" collapsible="false" colnum="2"></snow:form>
		<snow:button id="btnCancel" desc="确定" hidden="true"></snow:button>
	</snow:window>
	<script>
 		function gridId_opr_onRefresh(record, fieldId, fieldValue){ 
 			if(record){
 				var paramId = record.getValue("paramId"); 
 				var magicId = record.getValue("magicId"); 
				return "<i class='fa fa-search-plus'></i>&nbsp;<a href=\"JavaScript:onClickDetail('"+paramId+"','"+magicId+"')\">查看详情</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickModify('"+paramId+"','"+magicId+"')\">修改</a>"; 
 			} 
 		}
 		function onClickDetail(paramId,magicId){
 			SysParamsSec_dataset.setFieldReadOnly("paramValueTx",true);
			window_windowDetailId.open();
 		}
		function onClickModify(paramId,magicId){
			SysParamsSec_dataset.setFieldReadOnly("paramValueTx",false);
			window_windowModifyId.open();
 		}
		function btnCancel_onClick(){
			window_windowDetailId.close();
		}
		function window_windowModifyId_beforeClose(wmf){
			SysParamsSec_dataset.cancelRecord();
		}
		function btnSave_postSubmit(){
			$.success("操作成功!", function() {
				window_windowModifyId.close();
				SysParamsSec_dataset.flushData(SysParamsSec_dataset.pageIndex);
	        });
		}
	</script>
</snow:page>
