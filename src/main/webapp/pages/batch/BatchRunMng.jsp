<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="批量运行设置">
	<snow:dataset id="BatchRunMode" path="com.ruimin.ifs.batch.dataset.BatchRunMode" init="true" submitMode="current"></snow:dataset>
	<snow:dataset id="BatchRunTimeMng" path="com.ruimin.ifs.batch.dataset.BatchRunTimeMng" init="true" submitMode="current"></snow:dataset>
	<snow:form dataset="BatchRunMode" fieldstr="paramValueTx" border="true" label="批量运行设置"></snow:form>
	<div style="width: 99%;text-align:center; ">
		<snow:button id="btnRunModeSave" dataset="BatchRunMode"></snow:button>
	</div>
	<snow:grid dataset="BatchRunTimeMng" id="runTmGrid" border="true" label="任务运行时间" fieldstr="jobParamKey,runTime,desc0,opr[100]" paginationbar="btnRunTimeAdd"></snow:grid>
	<snow:window id="BatchRunTimeWin" closable="true" title="任务运行设置" modal="true"  buttons="btnRunTimeSave">
		<snow:form id="rumTimeform" dataset="BatchRunTimeMng" label="" border="false" fieldstr="jobParamKey,hour,minute,desc0" colnum="2"></snow:form>
		<snow:button id="btnRunTimeSave" dataset="BatchRunTimeMng" hidden="true"></snow:button>
	</snow:window>
	<snow:button id="btnRunTimeDelete" dataset="BatchRunTimeMng" hidden="true"></snow:button>
	<script type="text/javascript">
		function editor_hour_onTip(element, opt) {
			return "&nbsp;&nbsp;运行时间[小时]，范围为0-23";
		}
		function editor_minute_onTip(element, opt) {
			return "&nbsp;&nbsp;运行时间[分钟]，范围为0-59";
		}
		function btnRunTimeAdd_onClick() {
			window_BatchRunTimeWin.open();
		}

		function window_BatchRunTimeWin_beforeClose(wmf) {
			BatchRunTimeMng_dataset.cancelRecord();
		}
		
		
		function BatchRunMode_dataset_flushDataPost(){
			btnRunTimeAdd.setDisabled(true);
			var md = BatchRunMode_dataset.getValue("paramValueTx");
			if(md=="1"){//自动运行
				btnRunTimeAdd.setDisabled(false);
			}
		}
		
		function btnRunModeSave_postSubmit() {
			$.success("操作成功,请设置任务运行时间!", function() {
				BatchRunMode_dataset.flushData(1);
				BatchRunTimeMng_dataset.flushData(1);
			});
			
		}
		
		function btnRunTimeSave_postSubmit() {
			$.success("操作成功!", function() {
				window_BatchRunTimeWin.close();
				BatchRunTimeMng_dataset.flushData(BatchRunTimeMng_dataset.pageIndex);
			});
		}
		//当系统刷新单元格的内容时被触发
		function runTmGrid_opr_onRefresh(record, fieldId, fieldValue) {
			if (record && fieldValue) {//当存在记录时
				var md = BatchRunMode_dataset.getValue("paramValueTx");
				if(md == "1"){
					return "<center><i class='fa fa-pencil'></i><a href='JavaScript:showEdit()'>编辑</a> &nbsp;<i class='fa fa-trash-o'></i><a href='JavaScript:void(0);' onclick='doDelete();'>删除</a></center>";
				}else{
					return "<center><i class='fa fa-pencil'></i><a href='JavaScript:void(0)' style='color:#333' title='请设置自动运行保存后操作'>编辑</a> &nbsp;<i class='fa fa-trash-o'></i><a href='JavaScript:void(0);' style='color:#333' title='请设置自动运行保存后操作'>删除</a></center>";
				}
			} else {//当不存在记录时
				return "&nbsp;";
			}
		}

		function showEdit(id) {
			window_BatchRunTimeWin.open();
		}

		//删除
		function doDelete() {
			$.confirm("确认要删除该运行设置吗?", function() {
				btnRunTimeDelete.click();
			});
		}
		function btnRunTimeDelete_needCheck(button) {
			return false;
		}
		function btnRunTimeDelete_postSubmit() {
			$.success("操作成功!", function() {
				BatchRunTimeMng_dataset.flushData(BatchRunTimeMng_dataset.pageIndex);
			});
		}
	</script>
</snow:page>
