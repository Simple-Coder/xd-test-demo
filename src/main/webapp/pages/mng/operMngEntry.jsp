<%@page import="com.ruimin.ifs.framework.core.SessionUserInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="用户管理">
	<%
		SessionUserInfo si = SessionUserInfo.getSessionUserInfo();
			String tlrno = si.getTlrno();
	%>
	<snow:dataset id="orgtree" path="com.ruimin.ifs.mng.dataset.OrgTree" init="true" parameters="showvir=true"></snow:dataset>
	<snow:dataset id="OperMngEntry" path="com.ruimin.ifs.mng.dataset.OperMngEntry" init="true" submitMode="current"></snow:dataset>
	<snow:dataset id="RoleMngEntry" path="com.ruimin.ifs.mng.dataset.RoleMngEntry" init="false"></snow:dataset>
	<snow:layout id="layout">
		<snow:Layoutleft id="left" width="200">
			<snow:tree dataset="orgtree" id="orgs"></snow:tree>
		</snow:Layoutleft>
		<snow:Layoutcenter id="main">
			<snow:grid dataset="OperMngEntry" label="机构用户" height="99%" width="100%" toolbar="toolbar" id="gridId" fieldstr="tlrno,tlrName,flag[80],status[80],isLock[80],brcode[180],lastaccesstm,lastlogouttm" paginationbar="btnAdd,btnMod,btnStatus,unLock,btnDelete,btnResetPwd,btLoginStatus"></snow:grid>
			<snow:toolbar id="toolbar">
				<snow:query id="querybtn" dataset="OperMngEntry" fieldstr="qtlrno,qtlrname" btnright="true" border="false" ></snow:query>
			</snow:toolbar>
			<snow:window id="windowId" title="用户管理" modal="true" closable="true" width="700" buttons="btnSave">
				<snow:form id="formModifyId" dataset="OperMngEntry" label="*" border="false" fieldstr="tlrno,tlrName,brcode" collapsible="false" colnum="4"></snow:form>
				<br>
				<snow:grid dataset="RoleMngEntry" id="gridRoleId" height="300" fieldstr="select,roleId[100],roleName[140]"></snow:grid>
				<snow:button id="btnSave" dataset="OperMngEntry" hidden="true"></snow:button>
			</snow:window>
			<div style="display: none;">
				<snow:button id="btnStatusSub" dataset="OperMngEntry"></snow:button>
				<snow:button id="unLockSub" dataset="OperMngEntry"></snow:button>
				<snow:button id="btnDeleteSub" dataset="OperMngEntry"></snow:button>
				<snow:button id="btnResetPwdSub" dataset="OperMngEntry"></snow:button>
				<snow:button id="btLoginStatusSub" dataset="OperMngEntry"></snow:button>
			</div>
		</snow:Layoutcenter>
	</snow:layout>
	
	<script>
		function orgtree_dataset_afterScroll(ds,record){
			if(record) {
				OperMngEntry_interface_dataset.setParameter("upbrcode",record.getValue("_id"));
				OperMngEntry_interface_dataset_querybtn.click();
			}
		}
		function btnAdd_onClick(){
			OperMngEntry_dataset.setFieldReadOnly("tlrno",false);
			OperMngEntry_dataset.setFieldReadOnly("tlrName",false);
			OperMngEntry_dataset.setFieldReadOnly("brcodeName",false);
			OperMngEntry_dataset.setParameter("paramStat", "add");
			var foo = OperMngEntry_dataset.getValue("tlrno");
			RoleMngEntry_dataset.setParameter("tlrno", foo);
			RoleMngEntry_dataset.flushData(RoleMngEntry_dataset.pageIndex);
			window_windowId.open();
		}
		function btnMod_onClick(){
			if(checkOwn()){
				OperMngEntry_dataset.setFieldReadOnly("tlrno",true);
				OperMngEntry_dataset.setFieldReadOnly("tlrName",false);
				OperMngEntry_dataset.setFieldReadOnly("brcodeName",false);
				OperMngEntry_dataset.setParameter("paramStat", "mod");
				var foo = OperMngEntry_dataset.getValue("tlrno");
				OperMngEntry_dataset.setParameter("tlrno", foo);
				RoleMngEntry_dataset.setParameter("tlrno", foo);
				RoleMngEntry_dataset.flushData(RoleMngEntry_dataset.pageIndex);
				window_windowId.open();
			}
		}
		function btnSeleceted_postSubmit(btn, param){
			returnPara = param.roleList;
		}
		function gridRoleId_select_onRefresh(record, fieldId, fieldValue){
 			if(record){
 				var roleId = record.getValue("roleId");
 				var select = record.getValue("select");
 				var returnSelect = returnPara.split(",");
 				for (var i = 0; i < returnSelect.length; i++) {
					if(returnSelect[i] == roleId){
						return "<>"
					}
				}
				return ""; 
 			} 
 		}
		
		function window_windowId_beforeClose(wmf){
			OperMngEntry_dataset.setParameter("paramStat", "");
			OperMngEntry_dataset.cancelRecord();
		}
		
		function btnSave_onClickCheck(){
			var hasRoleSelected = false;
			var roleRecord = RoleMngEntry_dataset.getFirstRecord();
			var roleIdList = [];
			while(roleRecord){
				var v_selected = roleRecord.getValue("select");
				if( v_selected == true ){
					roleIdList.push(roleRecord.getValue("roleId"));
					hasRoleSelected=true;
				}
				roleRecord=roleRecord.getNextRecord();
		   	}
		   	if (!hasRoleSelected) {
		   		$.warn("至少选择一个岗位");
		   		return false;
		   	}
			var tlrno = OperMngEntry_dataset.getValue("tlrno");
			OperMngEntry_dataset.setParameter("tlrno", tlrno);
			OperMngEntry_dataset.setParameter("s", roleIdList.join(","));
			return true;
		}
		
		function btnSave_postSubmit(){
			$.success("操作成功!", function() {
				window_windowId.close();
				OperMngEntry_dataset.flushData(OperMngEntry_dataset.pageIndex);
	        });
		}
		function btnStatus_onClick(){
			if(checkOwn()){
				var foo = OperMngEntry_dataset.getValue("flag");
				var msg = ""
				if(foo == "1"){
					msg = "是否要将该用户状态修改为【无效】?";
					foo = "0";
				}else{
					msg = "是否要将该用户状态修改为【有效】?";
					foo = "1";
				}
				$.confirm(msg, function() {
					OperMngEntry_dataset.setParameter("foo",foo);
					btnStatusSub.click();
		        }, function() {
		        	return false;
		        });
			}
		}
		function unLock_onClick(){
			if(checkOwn()){
				var foo = OperMngEntry_dataset.getValue("isLock");
				var msg = ""
				if(foo == "0"){
					$.warn("该用户处于未锁定状态,无需再次解锁!");
		 			return false;
				}else{
					msg = "是否要解锁该用户?";
				}
				$.confirm(msg, function() {
					OperMngEntry_dataset.setParameter("foo","0");
					unLockSub.click();
		        }, function() {
		        	return false;
		        });
			}
		}
		function btnDelete_onClick(){
			if(checkOwn()){
				var foo = OperMngEntry_dataset.getValue("tlrno");
				$.confirm("是否确认删除该用户?注:一经删除无法恢复!!!", function() {
					OperMngEntry_dataset.setParameter("foo",foo);
					btnDeleteSub.click();
		        }, function() {
		        	return false;
		        });
			}
		}
		function btnResetPwd_onClick(){
			if(checkOwn()){
				var foo = OperMngEntry_dataset.getValue("tlrno");
				$.confirm("是否确认重置该用户密码?", function() {
					OperMngEntry_dataset.setParameter("foo",foo);
					btnResetPwdSub.click();
		        }, function() {
		        	return false;
		        });
			}
		}
		function btLoginStatus_onClick(){
			if(checkOwn()){
				var foo = OperMngEntry_dataset.getValue("tlrno");
				$.confirm("是否确认强制签退该用户?", function() {
					OperMngEntry_dataset.setParameter("foo","0");
					btLoginStatusSub.click();
		        }, function() {
		        	return false;
		        });
			}
		}
		function checkOwn(){
			var foo = OperMngEntry_dataset.getValue("tlrno");
			if(foo=="<%=tlrno%>") {
				$.error("您不可以对自己进行操作!");
				return false;
			}
			return true;
		}
		var btnStatusSub_postSubmit = unLockSub_postSubmit = btnDeleteSub_postSubmit = btnResetPwdSub_postSubmit = btLoginStatusSub_postSubmit = function() {
			$.success("操作成功!", function() {
				OperMngEntry_dataset.flushData(OperMngEntry_dataset.pageIndex);
			});
		}
		var btnStatusSub_needCheck = unLockSub_needCheck = btnDeleteSub_needCheck = btnResetPwdSub_needCheck = btLoginStatusSub_needCheck = btnSeleceted_needCheck = function() {
			return false;
		}
	</script>
</snow:page>
