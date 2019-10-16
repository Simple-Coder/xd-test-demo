<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="并行运行明细">
<snow:dataset id="MonitorBatchStepLog" path="com.ruimin.ifs.batch.dataset.MonitorBatchStepLog" init="true" readOnly="true"></snow:dataset>
<snow:grid dataset="MonitorBatchStepLog" id="loglist" height="100%" fieldstr="showNm[200],startTime[120],endTime[120],status[100],errMsg"></snow:grid>
<script type="text/javascript">
function loglist_status_onRefresh(record, fieldId, fieldValue) {
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
	} else{
		color = 'blue';
		zhongwen = "未运行";
	}
	if (color == null) {
		return zhongwen;
	} else {
		return "<font color='"+color+"'>" + zhongwen + "</font>";
	}
}

function loglist_showNm_onRefresh(record, fieldId, fieldValue) {
	var formatValue = fieldValue.substring(fieldValue.lastIndexOf("-")+1);
	for(var i = 0; i < 4; i++){
		if(formatValue.length<4){
			formatValue = "0" + formatValue;
		}else{
			break;
		}
	}
	return fieldValue.substring(0,fieldValue.lastIndexOf("-")+1) + formatValue;
	
}

</script>
</snow:page>
