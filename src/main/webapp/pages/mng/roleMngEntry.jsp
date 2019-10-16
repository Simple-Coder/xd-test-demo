<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="岗位管理">
	<snow:dataset id="RoleMngEntry" path="com.ruimin.ifs.mng.dataset.RoleMngEntry" init="true"></snow:dataset>
	<snow:dataset id="FunctionMng" path="com.ruimin.ifs.mng.dataset.FunctionMng" init="true" parameters="status=1"></snow:dataset>
	<snow:dataset id="StaffRoleRef" path="com.ruimin.ifs.mng.dataset.StaffRoleRef" init="false" ></snow:dataset>
	<snow:grid dataset="RoleMngEntry" id="gridId" fieldstr="roleName,status" paginationbar="btnAdd,btnStatus,btnRoleAuthorityManagement,btnViewOper" pagecache="true"></snow:grid>
	<snow:window id="windowCheckId" title="岗位信息" modal="true" closable="true" width="750" height="500" buttons="btnOpen,btnSelectAll,btnUnSelectAll,btnSave">
		<snow:form dataset="RoleMngEntry" fieldstr="roleName" id="rnform" label="岗位信息" border="false"></snow:form>
		<snow:tree dataset="FunctionMng" id="funcs" checkboxs="true" ></snow:tree>
		<snow:button id="btnOpen" desc="展开/合并" hidden="true"></snow:button>
		<snow:button id="btnSelectAll" desc="全选" hidden="true"></snow:button>
		<snow:button id="btnUnSelectAll" desc="全不选" hidden="true"></snow:button>
		<snow:button id="btnSave" dataset="RoleMngEntry" hidden="true"></snow:button>
	</snow:window>
	<snow:window id="windowOwnId" title="岗位人员信息" modal="true" closable="true" width="700">
		<snow:form dataset="RoleMngEntry" fieldstr="roleName" id="rnform2" label="岗位信息" border="false"></snow:form>
		<snow:grid dataset="StaffRoleRef" id="gridStaffId" fieldstr="tlrno[120],tlrName[160],flag[100],lastaccesstm[120]" height="400"></snow:grid>
	</snow:window>
	<div style="display: none;"><snow:button id="btnStatusSub" dataset="RoleMngEntry"></snow:button></div>
	<script>
		function btnAdd_onClick(){
			RoleMngEntry_dataset.setFieldReadOnly("roleName",false);
			tree_funcs.expandAll();
			tree_funcs.unCheckAll();
			window_windowCheckId.open();
		}
		function window_windowCheckId_beforeClose(wmf){
			RoleMngEntry_dataset.cancelRecord();
		}
		function btnRoleAuthorityManagement_onClickCheck(btn){
			var roleId = RoleMngEntry_dataset.getValue("roleId");
			RoleMngEntry_dataset.setFieldReadOnly("roleName",true);
			RoleMngEntry_dataset.setParameter("roleId", roleId);
			return true;
		}
		function btnRoleAuthorityManagement_postSubmit(btn, param){
			tree_funcs.unCheckAll();
			tree_funcs.expandAll();
			var arr = param.funcs.split(",");	// 根据后台返回的数据,进行相应的勾选
			tree_funcs.check(arr);
			window_windowCheckId.open();
		}
		function btnSave_onClickCheck(btn){
			var roleName = RoleMngEntry_dataset.getValue("roleName");
			var roleId = RoleMngEntry_dataset.getValue("roleId");
			var s = "";
			var flag = 0;
			var checkedArr = tree_funcs.getChecked();
			for(var i=0;i<checkedArr.length;i++){
				if(flag++>0){
					s+=",";
				}
				s+=checkedArr[i].getValue("funcid");
			}
			var indeterminateArr = tree_funcs.getIndeterminate();
			for(var i=0;i<indeterminateArr.length;i++){
				s+=",";
				s+=indeterminateArr[i].getValue("funcid");
			}
			RoleMngEntry_dataset.setParameter("rb", roleName);
			RoleMngEntry_dataset.setParameter("s", s);
			RoleMngEntry_dataset.setParameter("roleId", roleId);
			return true;
		}
		function btnViewOper_onClickCheck(btn){
			var roleId = RoleMngEntry_dataset.getValue("roleId");
			StaffRoleRef_dataset.setParameter("roleId", roleId);
			StaffRoleRef_dataset.flushData(StaffRoleRef_dataset.pageIndex);
			window_windowOwnId.open();
			return true;
		}
		function btnSave_postSubmit(){
			$.success("操作成功!", function() {
				window_windowCheckId.close();
				RoleMngEntry_dataset.flushData(RoleMngEntry_dataset.pageIndex);
	        });
		}
		function btnStatusSub_postSubmit(){
			$.success("操作成功!", function() {
				RoleMngEntry_dataset.flushData(RoleMngEntry_dataset.pageIndex);
	        });
		}
		function btnStatusSub_needCheck(){
			return false;
		}
		function btnRoleAuthorityManagement_needCheck(){
			return false;
		}
		function btnSave_needCheck(){
			return false;
		}
		function btnViewOper_needCheck(){
			return false;
		}
		function btnStatus_onClick(){
			var foo = RoleMngEntry_dataset.getValue("status");
			var roleId = RoleMngEntry_dataset.getValue("roleId");
			var msg = ""
			if(foo == "1"){
				msg = "是否要将该岗位设置为【无效】?";
				foo = "0";
			}else{
				msg = "是否要将该岗位设置为【有效】?";
				foo = "1";
			}
			$.confirm(msg, function() {
				RoleMngEntry_dataset.setParameter("foo",foo);
				RoleMngEntry_dataset.setParameter("roleId",roleId);
				btnStatusSub.click();
	        }, function() {
	        	return false;
	        });
		}

		var expandAllFlag = false;
		function btnOpen_onClick(){
			if(!expandAllFlag){
				tree_funcs.expandAll();
			}else{
				tree_funcs.collapseAll();
			}
			expandAllFlag=!expandAllFlag;
		}

		function btnSelectAll_onClick(){
			tree_funcs.checkAll();
		}

		function btnUnSelectAll_onClick(){
			tree_funcs.unCheckAll();
		}

	</script>
</snow:page>
