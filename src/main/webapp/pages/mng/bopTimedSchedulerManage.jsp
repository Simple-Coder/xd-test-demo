<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="定时任务设置">
	<snow:dataset id="BopTimedSchedulerManage" path="com.ruimin.ifs.mng.dataset.BopTimedSchedulerManage" init="true" submitMode="current"></snow:dataset>
	<snow:dataset id="BopTimedSchedulerParam" path="com.ruimin.ifs.mng.dataset.BopTimedSchedulerParam" init="false" readOnly="false" submitMode="all"></snow:dataset>
	<snow:grid dataset="BopTimedSchedulerManage" id="gridId" fieldstr="jobno,transactionFlg,processFunction[200],runtime,daysOfMonth,startTime,endTime,repeatTime,repeatCnt,desc0,isRunning" paginationbar="btnAdd,btnMod,btnDel" fitcolumns="false"></snow:grid>
	<snow:window id="windowId" title="定时任务" modal="true" closable="true" buttons="btnSave">
		<snow:form id="formAddId" dataset="BopTimedSchedulerManage" label="*" border="false" fieldstr="jobno,transactionFlg,processFunction,runtime,daysOfMonth,startTime,endTime,repeatTime,repeatCnt,desc0" collapsible="false" colnum="4"></snow:form>
		<br/>
		<snow:grid id="jobParamGrid" dataset="BopTimedSchedulerParam" fieldstr="paramKey[300],paramValue[300]" editable="true" height="200" pagecache="true" label="任务参数" paginationbar="btnParamAdd,btnParamDel"></snow:grid>
		<snow:button id="btnSave" dataset="BopTimedSchedulerManage" hidden="true"></snow:button>
	</snow:window>
	<snow:button id="btnDelete" dataset="BopTimedSchedulerManage" hidden="true"></snow:button>
	<script>
		function btnAdd_onClick(btn){
			BopTimedSchedulerManage_dataset.setFieldReadOnly("jobno",false);
			BopTimedSchedulerParam_dataset.setParameter("cid", "-1");
			BopTimedSchedulerParam_dataset.flushData(1);
			window_windowId.open();
		}
		
		function gridId_startTime_onRefresh(record, fieldId, fieldValue) {
			var ret = fieldValue;
			if (record && fieldValue) {
				ret = fieldValue.substr(0,2)+":"+fieldValue.substr(2,2)+":"+fieldValue.substr(4)
				return "<center>"+ret+"</center>";
			}
			return ret;
		}
		
		function gridId_endTime_onRefresh(record, fieldId, fieldValue) {
			var ret = fieldValue;
			if (record && fieldValue) {
				ret = fieldValue.substr(0,2)+":"+fieldValue.substr(2,2)+":"+fieldValue.substr(4)
				return "<center>"+ret+"</center>";
			}
			return ret;
		}
		
		function btnMod_onClick(btn){
			BopTimedSchedulerManage_dataset.setFieldReadOnly("jobno",true);
			
			BopTimedSchedulerParam_dataset.setParameter("cid", BopTimedSchedulerManage_dataset.getValue("id"));
			BopTimedSchedulerParam_dataset.flushData(1);
			
			window_windowId.open();
		}
		function window_windowId_beforeClose(wmf){
			BopTimedSchedulerManage_dataset.cancelRecord();
		}
		function btnSave_onClickCheck(btn){
			var runtime = BopTimedSchedulerManage_dataset.getValue("runtime");
			if((null != runtime || "" != runtime) && "97" == runtime){
				var daysOfMonth = BopTimedSchedulerManage_dataset.getValue("daysOfMonth");
				if(null == daysOfMonth || "" == daysOfMonth){
					$.error("当执行方式为【每月某日】时,必须指定对应的执行时间!");
					return false;
				}
			}
			return true;
		}
		
		function btnSave_postSubmit(){
			$.success("操作成功!", function() {
				window_windowId.close();
				BopTimedSchedulerManage_dataset.flushData(BopTimedSchedulerManage_dataset.pageIndex);
	        });
		}
		function btnDel_onClick(btn){
			$.confirm("是否确认删除?", function() {
				BopTimedSchedulerManage_dataset.setParameter("id", BopTimedSchedulerManage_dataset.record.getValue("id"));
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
				BopTimedSchedulerManage_dataset.flushData(BopTimedSchedulerManage_dataset.pageIndex);
	        });
		}
		
		function btnParamDel_needCheck(btn){
			return false;
		}
		
		/**可编辑表格删除验证**/
		function btnParamDel_onClickCheck(){
			var rec = BopTimedSchedulerParam_dataset.firstUnit;
			var bl = false;
			while (rec) {
				if(rec.recordState!="delete" && rec.recordState!="discard" && rec.visible){
        			bl = true;
        			break;
        		}
        		//获取下一行
				rec = rec.nextUnit;
			}
			return bl;
		}
		
	</script>
</snow:page>

