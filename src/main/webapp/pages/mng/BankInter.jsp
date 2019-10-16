<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/7/16 0016
  Time: 下午 15:01
  Description: 银行接口页面
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/snowweb" prefix="snow" %>
<snow:page title="银行接口">
    <%--页面数据源引入--%>
    <snow:dataset id="bankInter" path="com.ruimin.ifs.mng.dataset.BankInter" submitMode="current"
                  init="true"></snow:dataset>
    <%--配置查询条件--%>
    <snow:query id="queryId" label="请输入查询条件" dataset="bankInter"
                fieldstr="qBankCode,qInterCode,qOpenTime,qCloseTime"></snow:query>
    <%--配置结果集--%>
    <snow:grid id="gridId" dataset="bankInter" sortable="true" remotesort="true"
               fieldstr="bankCode,interName,interCode,openTime,closeTime,opr[160]"
               paginationbar="btnAdd"></snow:grid>
    <!--新增页面窗口配置-->
    <snow:window id="bankInterAdd" title="银行接口 --> 新增" modal="true" closable="true" buttons="btnSaveAdd">
        <snow:form id="bankInterAddForm" dataset="bankInter" border="false" label="银行信息 --> 新增"
                   fieldstr="bankCode,interName,openTime,closeTime,interCode"
                   collapsible="false" colnum="4"></snow:form>
        <snow:button id="btnSaveAdd" dataset="bankInter" hidden="true"></snow:button>
    </snow:window>
    <!--修改页面窗口配置-->
    <snow:window id="bankInterUpdate" title="银行信息 --> 修改" modal="true" closable="true" buttons="btnSaveUpdate">
        <snow:form id="bankInterUpdateForm" dataset="bankInter" border="false" label="银行信息 --> 修改"
                   fieldstr="bankCode,interName,openTime,closeTime,interCode"
                   collapsible="false" colnum="4"></snow:form>
        <snow:button id="btnSaveUpdate" dataset="bankInter" hidden="true"></snow:button>
    </snow:window>
    <!--详情页面窗口配置-->
    <snow:window id="windowDetailId" title="银行信息 --> 详情" modal="true" closable="true" buttons="btnCancel">
        <snow:form id="formDetailId" dataset="bankInter" border="false" label="银行信息 --> 详情 --> 参数详情"
                   fieldstr="uuid,bankCode,interName,interCode,openTime,closeTime,requestTemplate" collapsible="false" colnum="4"></snow:form>
        <snow:button id="btnCancel" desc="确定"  hidden="true"></snow:button>
    </snow:window>
    <snow:button id="btnDelete" dataset="bankInter" hidden="true"></snow:button>
    <script>
        function gridId_opr_onRefresh(record, fieldId, fieldValue) {
            if (record) {
                var uuid = record.getValue("uuid");
                return "<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickUpdate()\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-trash-o'></i>&nbsp;<a href=\"JavaScript:onClickDelete('" + uuid + "')\">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-search-plus'></i>&nbsp;<a href=\"JavaScript:onClickDetail('" + uuid + "')\">详情</a>";
            }
        }

        function onClickDetail(uuid)
        {
            bankInter_dataset.setFieldReadOnly("uuid", true);
            bankInter_dataset.setFieldReadOnly("bankCode", true);
            bankInter_dataset.setFieldReadOnly("interName", true);
            bankInter_dataset.setFieldReadOnly("interCode", true);
            bankInter_dataset.setFieldReadOnly("openTime", true);
            bankInter_dataset.setFieldReadOnly("closeTime", true);
            bankInter_dataset.setFieldReadOnly("requestTemplate", true);
            window_windowDetailId.open();
        }
        function btnCancel_onClick(){
            window_windowDetailId.close();
        }
        function onClickUpdate() {
            bankInter_dataset.setFieldReadOnly("bankCode", false);
            window_bankInterUpdate.open();
        }

        function btnAdd_onClick() {
            bankInter_dataset.setFieldReadOnly("bankCode", false);
            window_bankInterAdd.open();
        }

        function btnDelete_needCheck(btn) {
            return false;
        }

        function onClickDelete(uuid) {
            $.confirm("是否确认删除?", function () {
                // locate(id);
                btnDelete.click();
            }, function () {
                return false;
            });
        }

        function window_bankInterUpdate_beforeClose(wmf) {
            bankInter_dataset.cancelRecord();
        }

        function window_bankInterAdd_beforeClose(wmf) {
            bankInter_dataset.cancelRecord();
        }

        function btnDelete_postSubmit() {
            $.success("操作成功!", function () {
                bankInter_dataset.flushData(bankInter_dataset.pageIndex);
            });
        }

        function btnSaveAdd_postSubmit() {
            $.success("操作成功!", function () {
                window_bankInterAdd.close();
                bankInter_dataset.flushData(bankInter_dataset.pageIndex);
            });
        }

        function btnSaveUpdate_postSubmit() {
            $.success("操作成功!", function () {
                window_bankInterUpdate.close();
                bankInter_dataset.flushData(bankInter_dataset.pageIndex);
            });
        }

        //定位一条记录
        // 		function locate(id) {
        // 			var record = DataDicEntry_dataset.find(["id"],[id]);
        // 			if (record) {
        // 				DataDicEntry_dataset.setRecord(record);
        // 			}
        // 		}
    </script>
</snow:page>
