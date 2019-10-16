<%--
  Created by IntelliJ IDEA.
  User: hanweicheng
  Date: 2019/7/15 0015
  Time: 上午 10:58
  Description: 银行信息页面
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/snowweb" prefix="snow" %>
<snow:page title="银行信息">
    <%--页面数据源引入--%>
    <snow:dataset id="bankInformation" path="com.ruimin.ifs.mng.dataset.BankInformation" submitMode="current"
                  init="true"></snow:dataset>
    <%--配置查询条件--%>
    <snow:query label="请输入查询条件" id="queryId" dataset="bankInformation" fieldstr="qBankName,qbankNumber"></snow:query>
    <%--配置结果集--%>
    <snow:grid id="gridId" dataset="bankInformation" sortable="true" remotesort="true"
               fieldstr="bankName[100],bankCode[80],bankNumber[100],bankAddress[110],bankAccount[150],bankAccountName[200],bankInternalAccount[100],bankStatus[75],bankSigningDate[80],bankClientCode[100],opr[160]"
               paginationbar="btnAdd"></snow:grid>
    <!--新增页面窗口配置-->
    <snow:window id="bankInformationAdd" title="银行信息 --> 新增" modal="true" closable="true" buttons="btnSaveAdd">
        <snow:form id="bankInformationAddForm" dataset="bankInformation" border="false" label="银行信息 --> 新增"
                   fieldstr="bankName,bankClientCode,bankNumber,bankCode,bankAccount,bankInternalAccount,bankAccountName,bankAddress,bankStatus,bankCutTradeCode,bankSigningDate,bankInterfaceAddress,bankIpHost,bankMessageType,bankOperator,bankOperatorPwd,bankWarningThreshold,bankTerminationThreshold,expireDate" collapsible="false" colnum="4"></snow:form>
        <snow:button id="btnSaveAdd" dataset="bankInformation" hidden="true"></snow:button>
    </snow:window>
    <!--修改页面窗口配置-->
    <snow:window id="bankInformationUpdate" title="银行信息 --> 修改" modal="true" closable="true" buttons="btnSaveUpdate">
        <snow:form id="bankInformationUpdateForm" dataset="bankInformation" border="false" label="银行信息 --> 修改"
                   fieldstr="bankName,bankClientCode,bankNumber,bankCode,bankAccount,bankInternalAccount,bankAccountName,bankAddress,bankStatus,bankCutTradeCode,bankSigningDate,bankInterfaceAddress,bankIpHost,bankMessageType,bankOperator,bankOperatorPwd,bankWarningThreshold,bankTerminationThreshold,expireDate" collapsible="false" colnum="4"></snow:form>
        <snow:button id="btnSaveUpdate" dataset="bankInformation" hidden="true"></snow:button>
    </snow:window>
    <snow:button id="btnDelete" dataset="bankInformation" hidden="true"></snow:button>
    <snow:button id="btnBankStart" dataset="bankInformation" hidden="true"></snow:button>
    <snow:button id="btnBankStop" dataset="bankInformation" hidden="true"></snow:button>
    <script>
        function gridId_opr_onRefresh(record, fieldId, fieldValue) {
            if (record) {
                var uuid = record.getValue("uuid");
                var bankStatus = record.getValue("bankStatus");
                if(bankStatus==1)
                {
                    return "<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickUpdate()\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-trash-o'></i>&nbsp;<a href=\"JavaScript:onClickDelete('" + uuid + "')\">删除</a>"+
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickStop('"+uuid+"')\">停用</a>";
                }
                if (bankStatus==0)
                {
                    return "<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickUpdate()\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-trash-o'></i>&nbsp;<a href=\"JavaScript:onClickDelete('" + uuid + "')\">删除</a>"+
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-pencil'></i>&nbsp;<a href=\"JavaScript:onClickStart('"+ uuid+"')\">启用</a>";
                }
            }
        }

        function onClickUpdate() {

            bankInformation_dataset.setFieldReadOnly("bankCode", false);
            window_bankInformationUpdate.open();
        }

        function onClickStart(uuid) {
            $.confirm("是否确认启用?", function () {
                // locate(id);
                btnBankStart.click();
            }, function () {
                return false;
            });
        }

        function onClickStop(uuid) {
            $.confirm("是否确认停用?", function () {
                // locate(id);
                btnBankStop.click();
            }, function () {
                return false;
            });
        }


        function btnAdd_onClick() {
            bankInformation_dataset.setFieldReadOnly("bankCode", false);
            window_bankInformationAdd.open();

        }

        function btnDelete_needCheck(btn) {
            return false;
        }
        function btnBankStart_needCheck(btn) {
            return false;
        }
        function btnBankStop_needCheck(btn) {
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

        function window_bankInformationUpdate_beforeClose(wmf) {
            bankInformation_dataset.cancelRecord();
        }

        function window_bankInformationAdd_beforeClose(wmf) {
            bankInformation_dataset.cancelRecord();
        }

        function btnDelete_postSubmit() {
            $.success("操作成功!", function () {
                bankInformation_dataset.flushData(bankInformation_dataset.pageIndex);
            });
        }
        function btnBankStart_postSubmit() {
            $.success("操作成功!", function () {
                bankInformation_dataset.flushData(bankInformation_dataset.pageIndex);
            });
        }
        function btnBankStop_postSubmit() {
            $.success("操作成功!", function () {
                bankInformation_dataset.flushData(bankInformation_dataset.pageIndex);
            });
        }

        function btnSaveAdd_postSubmit() {
            $.success("操作成功!", function () {
                window_bankInformationAdd.close();
                bankInformation_dataset.flushData(bankInformation_dataset.pageIndex);
            });
        }

        function btnSaveUpdate_postSubmit() {
            $.success("操作成功!", function () {
                window_bankInformationUpdate.close();
                bankInformation_dataset.flushData(bankInformation_dataset.pageIndex);
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
