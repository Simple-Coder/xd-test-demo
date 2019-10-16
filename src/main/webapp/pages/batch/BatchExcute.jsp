<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="批量总控监控">
	<snow:dataset id="IfsSysInf" path="com.ruimin.ifs.batch.dataset.IfsSysInf" init="true" readOnly="false"></snow:dataset>
	<snow:dataset id="BatchStepListTot" path="com.ruimin.ifs.batch.dataset.BatchStepListTot" init="true" readOnly="true"></snow:dataset>
	<snow:dataset id="BatchStatusList" path="com.ruimin.ifs.batch.dataset.BatchStatusList" init="true" submitMode="current" readOnly="true"></snow:dataset>
	<snow:form id="bhdateForm" dataset="IfsSysInf" fieldstr="bhdate,status,tbsdy,miscflgs,switchDayTm,startOnlineTm" width="99%" label="批量总控" colnum="6"></snow:form>
	<div style="width: 99%;margin-top: 1px;margin-bottom: 3px;border: 1px solid #dddddd">
		<table style="width: 100%;margin: 3px">
			<tr>
			<td>
			<snow:button id="switchDay" dataset="IfsSysInf"></snow:button>
			<snow:button id="startOnline" dataset="IfsSysInf"></snow:button>
			</td>
			<td align="right" style="vertical-align: top;"><snow:formfield dataset="IfsSysInf" fid="refTime"></snow:formfield></td>
			</tr>
			<tr>
			<td colspan="2">
			<ul style="line-height: 24px; margin: 3px; color: red">
				<li>启动日切:将批量日期切换至下一日，修改批量状态为[批量状态]（批量总控状态必须为[联机状态]）</li>
				<li>启动联机:在当前批量日期所有任务运行成功的情况下，将总控状态切换为联机状态，表示当前批量日所有任务已成功运行完成，工作日可以切换到下一日.</li>
			</ul>
			</td>
			</tr>
		</table>
	</div>
	<snow:grid label="批量运行信息" dataset="BatchStepListTot" id="datatable2" fitcolumns="true" fieldstr="batchstatus[300],batchcurrentstep[300],batchsubstepcount[130],successstepcount[130],failstepcount[130]" width="99%" showpagelist="false"></snow:grid>
	<br />
	<snow:grid dataset="BatchStatusList" label="批量任务信息" id="datatable1" height="280" fieldstr="misc[260],jobno[100],step[100],planExecTm,startTime[130],endTime[130],status,bhdate" width="99%"></snow:grid>
	<style type="text/css">
		.l-radiolist-table label{
			padding-left: 3px;
			font-weight: bold;
			padding-right: 2px
		}
	</style>
	<script type="text/javascript">
		var finding = false;
		var interval = null;
		var rt = 0;//默认不刷新
		function initCallGetter_post() {
			IfsSysInf_dataset.setValue("refTime",rt);
			refresh();
		}
		
		function IfsSysInf_dataset_flushDataPost(dataset) {
			IfsSysInf_dataset.setValue("refTime",rt);
		}
		
		function refresh() {
			if(rt==0 && interval!=null){
				window.clearInterval(interval);
				interval = null;
			}else if(rt>0){
				interval = window.setInterval(query, rt * 1000);
			}
		}
		
		function IfsSysInf_dataset_afterChange(dataset,field){
			if(field.fieldName=="refTime"){
				var tm = IfsSysInf_dataset.getValue("refTime");
				if(tm){
					var tt = parseInt(tm);
					if(rt!=tt){
						rt = tt;
						refresh();
					}
				}
			}
		}

		function query() {
			IfsSysInf_dataset.flushData();
			BatchStatusList_dataset.flushData();
			BatchStepListTot_dataset.flushData();
		}

		function setRowColor() {
			for (var i = 1; i < datatable1.rows.length; i++) {
				datatable1.rows[i].bgColor = 'red';
			}
		}

		function BatchStatusList_dataset_flushDataPost(dataset) {
		}

		function datatable2_successstepcount_onRefresh(record, fieldId, fieldValue) {
			if (record) {
				var bhDate = IfsSysInf_dataset.getString("bhdate");
				return "<a href=\"Javascript:openBatchDetails('F','" + bhDate + "')\">" + fieldValue + "</a>";
			}
		}

		function datatable2_failstepcount_onRefresh(record, fieldId, fieldValue) {
			if (record) {
				var bhDate = IfsSysInf_dataset.getString("bhdate");
				return "<a href=\"Javascript:openBatchDetails('E','" + bhDate + "')\">" + fieldValue + "</a>";
			}

		}

		function openBatchDetails(statuscode, bhDate) {
			var path = "/pages/batch/BatchStepMonitor.jsp?statuscode=" + statuscode + "&bhdate=" + bhDate;
			$.open("batchDetail", "批量步骤运行明细", path, "850", "500", false, true, null, false);
		}

		function datatable1_status_onRefresh(record, fieldId, fieldValue) {
			var color = null;
			var zhongwen = fieldValue;
			if (fieldValue == 'E') {
				color = "red";
				zhongwen = "异常中断";
			} else if (fieldValue == 'R') {
				color = 'orange';
				zhongwen = "运行中";
			} else if (fieldValue == 'F') {
				color = 'green';
				zhongwen = "运行结束";
			} else {
				color = 'blue';
				zhongwen = "未运行";
			}
			if (color == null) {
				return zhongwen;
			} else {
				return "<font color='"+color+"'>" + zhongwen + "</font>";
			}
		}

		function switchDay_needCheck() {
			return false;
		}

		function startOnline_needCheck() {
			return false;
		}

		function switchDay_postSubmit(button) {
			$.success("操作成功", function() {
				IfsSysInf_dataset.flushData(1);
				BatchStepListTot_dataset.flushData(BatchStepListTot_dataset.pageIndex);
				BatchStatusList_dataset.flushData(BatchStatusList_dataset.pageIndex);
			});
		}
		function startOnline_postSubmit(button) {
			$.success("操作成功", function() {
				IfsSysInf_dataset.flushData(1);
				BatchStepListTot_dataset.flushData(BatchStepListTot_dataset.pageIndex);
				BatchStatusList_dataset.flushData(BatchStatusList_dataset.pageIndex);
			});
		}
	</script>
</snow:page>
