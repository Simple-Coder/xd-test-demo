<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<%
	String flag=request.getParameter("flag");
	String gridFileds="";
	String editFileds="";
	if("it".equals(flag))
	{
	    //技术使用
		gridFileds="bankCode[120],tradeName[120],tradeCode[120],tradeStatus[120],tradePattern[120],singleDealNum[120],threadNums[120],tradeOpenTime[120],tradeCloseTime[120],timedExecTime[120],tradeLastRunTime[120],loopTime[120],tradeRespErrCode[120],switchStopFlag[120],opr[240]";
		editFileds="bankCode,tradeName,tradeCode,loopTime,tradeOpenTime,tradeCloseTime,timedExecTime,threadNums,singleDealNum,tradeStatus,tradePattern,switchStopFlag,tradeRespErrCode";
	}
	else
	{
		//业务使用
		gridFileds="bankCode[120],tradeName[120],tradeCode[120],tradeStatus[120],tradePattern[120],tradeOpenTime[120],tradeCloseTime[120],timedExecTime[120],tradeLastRunTime[120],loopTime[120],tradeRespErrCode[120],switchStopFlag[120],opr[240]";
		editFileds="bankCode,tradeName,tradeCode,loopTime,tradeOpenTime,tradeCloseTime,timedExecTime,tradeStatus,tradePattern,tradeRespErrCode,switchStopFlag";
	}
%>
<snow:page title="交易管理">
	<snow:dataset id="TblBeTradeCfg" path="com.ruimin.ifs.mng.dataset.TblBeTradeCfg" submitMode="current" init="true"></snow:dataset>
	<snow:query label="请输入查询条件" id="queryId" dataset="TblBeTradeCfg" fieldstr="qBankCode,queryTradeName"></snow:query>
	<snow:grid dataset="TblBeTradeCfg" sortable="true" remotesort="true" id="gridId" fieldstr="<%=gridFileds%>" paginationbar="btnAdd"></snow:grid>
	<!-- 可使用一个窗体 -->
	<snow:window id="windowModifyId" title="交易管理 --> 修改" modal="true" closable="true" buttons="btnSave">
		<snow:form id="formModifyId" dataset="TblBeTradeCfg" border="false" label="交易管理 --> 编辑 --> 交易管理" fieldstr="<%=editFileds%>" collapsible="false" colnum="4"></snow:form>
		<snow:button id="btnSave" dataset="TblBeTradeCfg" hidden="true"></snow:button>
	</snow:window>
	<snow:window id="windowAddId" title="交易管理 --> 新增" modal="true" closable="true" buttons="btnSave1">
		<snow:form id="formAddId" dataset="TblBeTradeCfg" border="false" label="交易管理 --> 新增 --> 数据新增" fieldstr="<%=editFileds%>" collapsible="false" colnum="4"></snow:form>
		<snow:button id="btnSave1" dataset="TblBeTradeCfg" hidden="true"></snow:button>
	</snow:window>
	<snow:button id="btnDelete" dataset="TblBeTradeCfg" hidden="true"></snow:button>

	<snow:window id="windowQueryDate" title="交易管理 --> 运行" modal="true" closable="true" buttons="btnSaveRetryDate">
		<snow:form id="formDateId" dataset="TblBeTradeCfg" border="false" label="交易管理 --> 编辑 --> 运行" fieldstr="tradeName,tradeCode,tradeOpenDateRetry,tradeCloseDateRetry" collapsible="false" colnum="4"></snow:form>
		<snow:button id="btnSaveRetryDate" dataset="TblBeTradeCfg" hidden="true"></snow:button>
	</snow:window>

	<script>
		function gridId_opr_onRefresh(record, fieldId, fieldValue) {
			if (record) {
				var id = record.getValue("uuid");
                var tradeCode = record.getValue("tradeCode");
                var tradeName = record.getValue("tradeName");
                var tradeCode = record.getValue("tradeCode");
                return "<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickModify()\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-trash-o'></i>&nbsp;<a href=\"JavaScript:onClickDelete('" + id + "')\">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickRetry('" + tradeName + "')\">运行</a>";
			}
		}
		function onClickModify() {
			TblBeTradeCfg_dataset.setFieldReadOnly("uuid", true);
			window_windowModifyId.open();
		}
        function onClickRetry(tradeName) {
            TblBeTradeCfg_dataset.setFieldReadOnly("uuid", true);
            TblBeTradeCfg_dataset.setFieldReadOnly("tradeCode", true);
            TblBeTradeCfg_dataset.setFieldReadOnly("tradeName", true);
            TblBeTradeCfg_dataset.setFieldReadOnly("bankCode", true);
            if (tradeName=="历史余额查询"||tradeName=="账户历史明细查询")
            {
                TblBeTradeCfg_dataset.setFieldReadOnly("tradeOpenDateRetry", false);
                TblBeTradeCfg_dataset.setFieldReadOnly("tradeCloseDateRetry", false);
            }
            else {
                TblBeTradeCfg_dataset.setFieldReadOnly("tradeOpenDateRetry", true);
                TblBeTradeCfg_dataset.setFieldReadOnly("tradeCloseDateRetry", true);
			}

            window_windowQueryDate.open();
        }
		function btnAdd_onClick() {
			window_windowAddId.open();
		}
		function btnDelete_needCheck(btn) {
			return false;
		}
		function onClickDelete(id) {
			$.confirm("是否确认删除?", function() {
				//TblBeTradeCfg_dataset.setParameter("uuid", id);
				btnDelete.click();
			}, function() {
				return false;
			});
		}
		function window_windowModifyId_beforeClose(wmf) {
			TblBeTradeCfg_dataset.cancelRecord();
		}
        function window_windowQueryDate_beforeClose(wmf) {
            TblBeTradeCfg_dataset.cancelRecord();
        }
		function window_windowAddId_beforeClose(wmf) {
			TblBeTradeCfg_dataset.cancelRecord();
		}
		function btnDelete_postSubmit() {
			$.success("操作成功!", function() {
				TblBeTradeCfg_dataset.flushData(TblBeTradeCfg_dataset.pageIndex);
			});
		}
		function btnSave_postSubmit() {
			$.success("操作成功!", function() {
				window_windowModifyId.close();
				TblBeTradeCfg_dataset.flushData(TblBeTradeCfg_dataset.pageIndex);
			});
		}
		function btnSave1_postSubmit() {
			$.success("操作成功!", function() {
				window_windowAddId.close();
				TblBeTradeCfg_dataset.flushData(TblBeTradeCfg_dataset.pageIndex);
			});
		}
        function btnSaveRetryDate_postSubmit() {
            $.success("操作成功!", function() {
                window_windowQueryDate.close();
                TblBeTradeCfg_dataset.flushData(TblBeTradeCfg_dataset.pageIndex);
            });
        }
	</script>
</snow:page>
