<%@page import="com.ruimin.ifs.batch.process.util.BatchUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="批量步骤监控">
	<snow:dataset id="IfsSysInf" path="com.ruimin.ifs.batch.dataset.IfsSysInf" init="true" readOnly="false"></snow:dataset>
	<snow:dataset id="MonitorBatchJobInf" path="com.ruimin.ifs.batch.dataset.MonitorBatchJobInf" init="false" readOnly="true"></snow:dataset>
	<snow:dataset id="MonitorBatchStepList" path="com.ruimin.ifs.batch.dataset.MonitorBatchStepList" init="false" submitMode="current" readOnly="true"></snow:dataset>
	<snow:query id="query" dataset="MonitorBatchJobInf" fieldstr="bhdate" width="99%" btnright="true"></snow:query>
	<snow:grid label="批量任务信息"  dataset="MonitorBatchJobInf" id="jobgrid" fieldstr="jobno[60],misc[500],stepCnt[60],startTime[130],endTime[130],status[120],opr[130]" width="99%"></snow:grid>
	<table style="width: 99%;">
		<tr>
		<td align="right" style="vertical-align: top;"><snow:formfield dataset="IfsSysInf" fid="refTime"></snow:formfield></td>
		</tr>
	</table>
	<snow:button id="startJob" dataset="MonitorBatchJobInf" hidden="true"></snow:button>
	<snow:button id="stopBtch" dataset="MonitorBatchJobInf" hidden="true"></snow:button>
	<snow:grid label="步骤信息" dataset="MonitorBatchStepList" id="stepgrid" fieldstr="stepDispName[140],subStepName[220],subFlag[60],suspendCode[60],startTime[130],endTime[130],statusCode[100],opr[80],errorMsg[300]" width="99%"></snow:grid>
	<snow:button id="btnSingle" dataset="MonitorBatchStepList" hidden="true"></snow:button>
	<%
		String bhDate = BatchUtil.getIfsSysInf().getBhdate();
	%>
	<style type="text/css">
		.l-radiolist-table label{
			padding-left: 3px;
			font-weight: bold;
			padding-right: 2px
		}
	</style>
	<script type="text/javascript">
		var finding = false;
		var bhDate="<%=bhDate%>";
		var rt = 0;//默认不刷新
		var interval = null;
		function initCallGetter_post() {
			IfsSysInf_dataset.setValue("refTime",rt);
			MonitorBatchJobInf_interface_dataset.setValue("bhdate", bhDate);
			MonitorBatchJobInf_interface_dataset_query.click();
		}

		function IfsSysInf_dataset_afterChange(dataset,field){
			if(field.fieldName=="refTime"){
				var tm = IfsSysInf_dataset.getValue("refTime");
				if(tm){
					var tt = parseInt(tm);
					if(rt!=tt){
						rt = tt;
						startRefStep();
					}
				}
			}
		}
		
		function MonitorBatchJobInf_dataset_flushDataPost(ds) {
			if(ds.length>0){
				var jobNo = ds.getValue("jobno");
				loadStep(jobNo);
			}
		}

		function loadStep(jobno) {
			MonitorBatchStepList_dataset.setParameter("bhdate", MonitorBatchJobInf_interface_dataset.getString("bhdate"));
			if (jobno) {
				MonitorBatchStepList_dataset.setParameter("jobno", jobno);
			} else {
				MonitorBatchStepList_dataset.setParameter("jobno", "-1");
			}
			MonitorBatchStepList_dataset.flushData(MonitorBatchStepList_dataset.pageIndex);
		}

		function MonitorBatchJobInf_dataset_afterScroll(datset, record) {
			$('#stepgrid .l-panel-header .l-panel-header-text').first().html("<i class='fa fa-list-ol'></i>&nbsp;步骤信息");
			if (null == record)
				return;
			var jobNo = record.getValue("jobno");
			if (jobNo) {
				$('#stepgrid .l-panel-header .l-panel-header-text').first().html("<i class='fa fa-list-ol'></i>&nbsp;任务["+jobNo+"]步骤信息");
			}
			loadStep(jobNo);
		}
		
		function startRefStep() {
			if(rt==0 && interval!=null){
				window.clearInterval(interval);
				interval = null;
			}else if(rt>0){
				interval = window.setInterval(getRunningStep, rt * 1000);
			}
		}

		function getRunningStep() {
			finding = true;
			for (var index = 1; index <= MonitorBatchStepList_dataset.pageCount; index++) {
				MonitorBatchStepList_dataset.flushData(index);
				var findRunning = false;
				for (var rowIndex = 1; rowIndex <= MonitorBatchStepList_dataset.pageSize; rowIndex++) {
					if (MonitorBatchStepList_dataset.getValue("statusCode") == 'R') {
						findRunning = true;
						break;
					}
					MonitorBatchStepList_dataset.moveNext();
				}
				if (findRunning)
					break;
			}
			finding = false;
		}

		function jobgrid_status_onRefresh(record, fieldId, fieldValue) {
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

		function jobgrid_opr_onRefresh(record, fieldId, fieldValue) {
			var html = "<center>";
			html += "<i class='fa fa-play'></i>&nbsp;<a href=\"JavaScript:doStartJob(" + fieldValue + ")\">运行</a>";
			html += "&nbsp;&nbsp;<i class='fa fa-stop'></i>&nbsp;<a href=\"JavaScript:doStopBtch(" + fieldValue + ")\">停止</a>";
			return html + "</center>";
		}

		function doStartJob(jn) {
			MonitorBatchJobInf_dataset.setParameter("jobNo", jn);
			startJob.click();
		}
		function doStopBtch(jn) {
			MonitorBatchJobInf_dataset.setParameter("jobNo", jn);
			stopBtch.click();
		}

		function stepgrid_statusCode_onRefresh(record, fieldId, fieldValue) {
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
			} else if (fieldValue == 'N') {
				color = 'blue';
				zhongwen = "未运行";
			} else {
				zhongwen = "";
			}
			if (color == null) {
				return zhongwen;
			} else {
				return "<font color='"+color+"'>" + zhongwen + "</font>";
			}
		}
		function stepgrid_stepDispName_onRefresh(record, fieldId, fieldValue) {
			var sf = record.getValue("subFlag");
			if (sf == "1") {
				var jobNo = record.getValue("jobNo");
				var stepNo = record.getValue("stepNo");
				return "<a href=\"JavaScript:showSideList(" + jobNo + "," + stepNo + ")\">" + fieldValue + "</a>";
			} else {
				return fieldValue;
			}
		}

		function stepgrid_opr_onRefresh(record, fieldId, fieldValue) {
			var html = "<center>";
			var jobNo = record.getValue("jobNo");
			var stepNo = record.getValue("stepNo");
			var subStepNo = record.getValue("subStepNo");
			html += "<i class='fa fa-crosshairs'></i>&nbsp;<a href=\"JavaScript:doSingleStepJob(" + jobNo + "," + stepNo + "," + subStepNo + ")\">单步执行</a>";
			return html + "</center>";
		}

		function doSingleStepJob(jn, step, subStep) {
			MonitorBatchStepList_dataset.setParameter("jobNo", jn);
			MonitorBatchStepList_dataset.setParameter("stepNo", step);
			MonitorBatchStepList_dataset.setParameter("subStepNo", subStep);
			btnSingle.click();
		}

		function showSideList(jobNo, stepNo) {
			var qBhdate = MonitorBatchStepList_dataset.getParameter("bhdate");
			var path = "/pages/batch/BatchStepMonitorLog.jsp?jobNo=" + jobNo + "&stepNo=" + stepNo + "&bhdate=" + qBhdate;
			$.open("batchDetail", "步骤运行明细", path, "850", "500", false, true, null, false);
		}

		function btnSingle_postSubmit(button) {
			$.success("操作成功", function() {
				MonitorBatchStepList_dataset.flushData(MonitorBatchStepList_dataset.pageIndex);
			});
		}

		function startJob_postSubmit(button) {
			$.success("操作成功", function() {
				MonitorBatchJobInf_dataset.flushData(MonitorBatchJobInf_dataset.pageIndex);
			});
		}

		function stopBtch_onClickCheck(btn, commit) {
			$.confirm("是否确定停止批量总控？", function() {
				commit();
			}, function() {
			});
		}

		function stopBtch_postSubmit(button) {
			$.success("操作成功", function() {
				MonitorBatchJobInf_dataset.flushData(MonitorBatchJobInf_dataset.pageIndex);
			});
		}
	</script>
</snow:page>
