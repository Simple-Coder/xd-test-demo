<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="报文日志查询">
	<%--页面数据源引入--%>
	<snow:dataset id="msgLogQuery" path="com.ruimin.ifs.mng.dataset.MsgLogQuery" submitMode="current"
				  init="true"></snow:dataset>
	<%--配置查询条件--%>
	<snow:query id="queryId" dataset="msgLogQuery"
				fieldstr="msgStartTime,msgEndTime,msgNo,fromSys,toSys,tradeNum,msgAcc">
	</snow:query>
	<%--配置结果集--%>
	<snow:grid dataset="msgLogQuery" id="gridId" sortable="true" remotesort="true"
			   fieldstr="reqSeqNo[150],reqTime[150],respTime[150],reqSys[100],respSys[100],reqAccounts[150],respAccounts[150],originalSeqNo[150],tranCode[80],amount[80],tranCodeName[150],respCode[70],serverIp[150],respDesc[180],opr[60]" >
	</snow:grid>
	<!--详情页面窗口配置-->
	<snow:window id="windowDetailId" title="报文日志详情" modal="true" closable="true" buttons="btnCancel">
		<snow:form id="formDetailId" dataset="msgLogQuery" border="false" label="报文日志管理 --> 详情 --> 参数详情"
				   fieldstr="reqSeqNo,originalSeqNo,amount,reqTime,respTime,reqSys,respSys,reqAccounts,respAccounts,tranCode,tranCodeName,respCode,serverIp,respDesc,reqMsg,respMsg" collapsible="false" colnum="4"></snow:form>
		<snow:button id="btnCancel" desc="确定"  hidden="true"></snow:button>
	</snow:window>
	<script>
        function gridId_opr_onRefresh(record, fieldId, fieldValue){
            if(record){
                var paramId = record.getValue("uuid");
                var magicId = record.getValue("reqSeqNo");
                return "<i class='fa fa-search-plus'></i>&nbsp;<a align='center' href=\"JavaScript:onClickDetail('"+paramId+"','"+magicId+"')\">详情</a>&nbsp;&nbsp;&nbsp;";
            }
        }
        function onClickDetail(paramId,magicId){
            msgLogQuery_dataset.setFieldReadOnly("uuid",true);
            msgLogQuery_dataset.setFieldReadOnly("reqSeqNo",true);
            msgLogQuery_dataset.setFieldReadOnly("reqTime",true);
            msgLogQuery_dataset.setFieldReadOnly("respTime",true);
            msgLogQuery_dataset.setFieldReadOnly("reqSys",true);
            msgLogQuery_dataset.setFieldReadOnly("respSys",true);
            msgLogQuery_dataset.setFieldReadOnly("tranCode",true);
            msgLogQuery_dataset.setFieldReadOnly("reqMsg",true);
            msgLogQuery_dataset.setFieldReadOnly("respMsg",true);
            msgLogQuery_dataset.setFieldReadOnly("respCode",true);
            msgLogQuery_dataset.setFieldReadOnly("serverIp",true);
            msgLogQuery_dataset.setFieldReadOnly("respDesc",true);
            msgLogQuery_dataset.setFieldReadOnly("reqAccounts",true);
            msgLogQuery_dataset.setFieldReadOnly("respAccounts",true);
            msgLogQuery_dataset.setFieldReadOnly("amount",true);
            msgLogQuery_dataset.setFieldReadOnly("originalSeqNo",true);
            window_windowDetailId.open();
        }
        function btnCancel_onClick(){
            window_windowDetailId.close();
        }
	</script>
</snow:page>
