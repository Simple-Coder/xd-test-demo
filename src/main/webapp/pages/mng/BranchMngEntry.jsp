<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="机构信息维护">
<snow:dataset id="orgtree" path="com.ruimin.ifs.mng.dataset.OrgTree" init="true" parameters="showvir=true"></snow:dataset>
<snow:dataset id="orgmanger" path="com.ruimin.ifs.mng.dataset.OrgManager" init="false" submitMode="current"></snow:dataset>
<snow:layout id="layout">
	<snow:Layoutleft id="left" width="200">
		<snow:tree dataset="orgtree" id="orgs"></snow:tree>
	</snow:Layoutleft>
	<snow:Layoutcenter id="main">
		<snow:grid dataset="orgmanger" height="99%" label="下级机构" id="orglist" fieldstr="brno,brname,brclass,status,opr" paginationbar="btAdd,btStatus,btnShowDetail" toolbar="toolbar"></snow:grid>
			<snow:toolbar id="toolbar">
				<snow:query id="querybtn" dataset="orgmanger" fieldstr="qbrno,qbrname" btnright="true" border="false" ></snow:query>
			</snow:toolbar>
		<snow:window id="win" closable="true" title="机构新增" modal="true" buttons="btSave">
			<snow:form id="orgwin" dataset="orgmanger" label=""  border="false" fieldstr="brno,brname,brclass,blnUpBrcode,blnManageBrcode,teleno,postno,address"></snow:form>
			<br/>
			<snow:button id="btSave" dataset="orgmanger" hidden="true"></snow:button>
		</snow:window>
		<snow:window id="winupdate" closable="true" title="机构信息" modal="true" buttons="btUpdate">
			<snow:form id="orgwinupd" dataset="orgmanger" label="" border="false" fieldstr="brno,brname,brclass,blnUpBrcode,blnManageBrcode,teleno,postno,address"></snow:form>
			<br/>
			<snow:button id="btUpdate" dataset="orgmanger" hidden="true"></snow:button>
		</snow:window>
	</snow:Layoutcenter>
</snow:layout>
<script language="javascript">
	function orgtree_dataset_afterScroll(ds,record){
		if(record) {
			orgmanger_interface_dataset.setParameter('upbrcode',record.getValue("_id"));
			orgmanger_interface_dataset_querybtn.click();
		}
	}
	
	function btAdd_onClick(){
		window_win.open();
	}
	
	function window_win_beforeClose(wmf){
		orgmanger_dataset.cancelRecord();
	}
	
	function orglist_opr_onRefresh(record, fieldId, fieldValue){
		if(record){
			return "<span style='width:100%;text-align:center' class='fa fa-pencil'><a href=\"JavaScript:openupdate()\">修改</a></span>"; 
		}else{
			return "&nbsp;";
		}
	}
	function btSave_postSubmit(btn){
		$.success("操作成功!", function() {
			window_win.close();
			orgtree_dataset.flushData(orgtree_dataset.pageIndex);
        });
	}
	
	function openupdate(){
		orgmanger_dataset.setReadOnly(false);
		btUpdate.setHidden(false);
		window_winupdate.open();
	}
	function window_winupdate_beforeClose(wmf){
		orgmanger_dataset.cancelRecord();
	}
	function btUpdate_postSubmit(btn){
		$.success("操作成功!", function() {
			window_winupdate.close();
			orgtree_dataset.flushData(orgtree_dataset.pageIndex);
        });
	}
	
	function btStatus_onClickCheck(button,commit) {
		var status = orgmanger_dataset.getValue("status");
		if(status == '0'){
			$.confirm("确认将该机构设置为有效?", function() {
				orgmanger_dataset.setParameter("setstatus", "1");
				commit();
			});
		} else {
			$.confirm("确认将该机构设置为无效?", function() {
				orgmanger_dataset.setParameter("setstatus", "0");
				commit();
			});
		}
	}
	
	function btStatus_postSubmit(btn){
		$.success("操作成功!", function() {
			orgtree_dataset.flushData(orgtree_dataset.pageIndex);
        });
	}
	
	function orglist_onDblClick(){
		btnShowDetail.click();
	}
	
	function btnShowDetail_onClick(){
		orgmanger_dataset.setReadOnly(true);
		btUpdate.setHidden(true);
		window_winupdate.open();
	}
	
	
</script>
</snow:page>
