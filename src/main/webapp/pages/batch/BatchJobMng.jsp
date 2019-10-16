<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="批量参数设置">
	<snow:dataset id="IfsBatJobInf" path="com.ruimin.ifs.batch.dataset.IfsBatJobInf" init="true" submitMode="current"></snow:dataset>
	<snow:dataset id="BhProcStep" path="com.ruimin.ifs.batch.dataset.BhProcStep" init="false" submitMode="current"></snow:dataset>
	<snow:grid id="jobGrid" dataset="IfsBatJobInf" fieldstr="jobno[120],preJobno[120],preAutorun[150],runonline[170],misc,opr[100]" paginationbar="btnJobAdd" label="任务列表" height="260"></snow:grid>
	<snow:window id="jobEditWin" closable="true" title="任务信息" modal="true"  buttons="btnJobSave">
		<snow:form id="jobForm" dataset="IfsBatJobInf" label="" border="false" fieldstr="jobno,preJobno,preAutorun,runonline,systemType,miscflgs,misc" colnum="2"></snow:form>
		<snow:button id="btnJobSave" dataset="IfsBatJobInf" hidden="true"></snow:button>
	</snow:window>
	<snow:button id="btnJobDelete" dataset="IfsBatJobInf" hidden="true"></snow:button>
	<div style="height:3px"></div>
	<snow:grid dataset="BhProcStep" id="stepgrid" fieldstr="jobno[70],step[70],subStep[70],processFunction,runtime[70],suspend[100],repeatCnt[90],desc0[240],opr[100]" label="步骤信息" paginationbar="btStepAdd"></snow:grid>
	<snow:window id="jobStepEditWin" closable="true" title="步骤信息" modal="true" buttons="btStepSave">
		<snow:form id="jobStepForm" dataset="BhProcStep" label="" border="false" fieldstr="jobno,step,subStep,processFunction,processParam,runtime,runtimeDay,subFlag,maxproc,tempFlag,auto,suspend,repeatCnt,desc0,desc1,desc2" colnum="4"></snow:form>
		<snow:button id="btStepSave" dataset="BhProcStep" hidden="true"></snow:button>
	</snow:window>
	<snow:button id="btStepDelete" dataset="BhProcStep" hidden="true"></snow:button>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/DwrBatchService.js"></script>
	<script type="text/javascript">
		function IfsBatJobInf_dataset_afterScroll(datset, record) {
			$('#stepgrid .l-panel-header .l-panel-header-text').first().html("<i class='fa fa-list-ol'></i>&nbsp;步骤信息");
			if (null == record)
				return;
			var jobNo = record.getValue("jobno");
			if (jobNo) {
				$('#stepgrid .l-panel-header .l-panel-header-text').first().html("<i class='fa fa-list-ol'></i>&nbsp;任务["+jobNo+"]步骤信息");
				BhProcStep_dataset.setParameter("jobno", jobNo);
			} else {
				BhProcStep_dataset.setParameter("jobno", "-1");
			}
			BhProcStep_dataset.flushData(1);
		}
		
		function jobGrid_opr_onRefresh(record, fieldId, fieldValue) {
			if (record) {
				var jobno = record.getValue("jobno");
				if(jobno){
					return "<center><i class='fa fa-pencil'></i><a href=\"JavaScript:editJob()\">编辑</a>&nbsp;&nbsp;<i class='fa fa-trash-o'></i><a href=\"JavaScript:deleteJob(" + jobno + ")\">删除</a></center>";
				}else{
					return "";
				}
			}
		}
	
		function btnJobAdd_onClick(){
			DwrBatchService.getSystemType(function(data){
				if(data){
					IfsBatJobInf_dataset.setValue("systemType",data);
				}
			});
			IfsBatJobInf_dataset.setValue("flg","add");
			window_jobEditWin.open();
		}
		
		function editJob(){
			IfsBatJobInf_dataset.setValue("flg","edit");
			IfsBatJobInf_dataset.setFieldReadOnly("jobno",true);
			window_jobEditWin.open();
		}
	
		function deleteJob(jobno){
			$.confirm("删除任务将同时删除该任务步骤，确定要删除吗?", function() {
				IfsBatJobInf_dataset.setParameter("jobno", jobno);
				btnJobDelete.click();
			});
		}
		
		function window_jobEditWin_beforeClose(wmf) {
			IfsBatJobInf_dataset.setFieldReadOnly("jobno",false);
			IfsBatJobInf_dataset.cancelRecord();
		}
		
	
		function btStepAdd_onClick() {
			BhProcStep_dataset.setValue("jobno",IfsBatJobInf_dataset.getValue("jobno"));
			window_jobStepEditWin.open();
		}

		function window_jobStepEditWin_beforeClose(wmf) {
			BhProcStep_dataset.cancelRecord();
		}
		function btStepSave_postSubmit() {
			$.success("操作成功!", function() {
				window_jobStepEditWin.close();
				BhProcStep_dataset.flushData(BhProcStep_dataset.pageIndex);
			});
		}
		//当系统刷新单元格的内容时被触发
		function stepgrid_opr_onRefresh(record, fieldId, fieldValue) {
			if (record) {//当存在记录时
				return "<center><i class='fa fa-pencil'></i><a href='JavaScript:showEdit(" + fieldValue + ")'>编辑</a> &nbsp; <i class='fa fa-trash-o'></i><a href='JavaScript:void(0);' onclick='doDelete(" + fieldValue + ");'>删除</a></center>";
			} else {//当不存在记录时
				return "&nbsp;";
			}
		}
		var runtimeArrays=["9N","90","91","93","94","95","96","41","42","43","44","45","46","47"];
		function showEdit(id) {
			var rt = BhProcStep_dataset.getValue("runtime");
			var idx = runtimeArrays.indexOf(rt);
			if(idx<0){
				BhProcStep_dataset.setFieldReadOnly("runtimeDay",false);
				BhProcStep_dataset.setValue("runtime","00");
				BhProcStep_dataset.setValue("runtimeDay",rt);
			}else{
				BhProcStep_dataset.setFieldReadOnly("runtimeDay",true);
				BhProcStep_dataset.setFieldRequired("runtimeDay",true);
			}
			window_jobStepEditWin.open();
		}

		//删除
		function doDelete(id) {
			$.confirm("确认删除该步骤吗?", function() {
				btStepDelete.click();
			});
		}
		function btStepDelete_needCheck(button) {
			return false;
		}
		function btStepDelete_postSubmit() {
			$.success("操作成功!", function() {
				BhProcStep_dataset.flushData(BhProcStep_dataset.pageIndex);
			});
		}
		
		function BhProcStep_dataset_afterChange(dataset,field){
			if(field.fieldName=="runtime"){
				var rt = BhProcStep_dataset.getValue("runtime");
				if(rt=="00"){
					BhProcStep_dataset.setFieldReadOnly("runtimeDay",false);
					BhProcStep_dataset.setFieldRequired("runtimeDay",true);
				}else{
					BhProcStep_dataset.setFieldReadOnly("runtimeDay",true);
					BhProcStep_dataset.setFieldRequired("runtimeDay",false);
					BhProcStep_dataset.setValue("runtimeDay","");
				}
			}
		}
		
		
	</script>
</snow:page>
