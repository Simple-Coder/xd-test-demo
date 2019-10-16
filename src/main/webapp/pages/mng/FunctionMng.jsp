<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="权限管理">
	<snow:dataset id="FunctionMng" path="com.ruimin.ifs.mng.dataset.FunctionMng" init="true" submitMode="current"></snow:dataset>
	<snow:layout id="layout">
		<snow:Layoutleft id="left" width="200">
			<snow:tree dataset="FunctionMng" id="funcs" contextmenu="menu"></snow:tree>
			<snow:menu id="menu">
				<snow:menuitem desc="添加目录菜单" id="addFolder" icon="fa fa-plus"></snow:menuitem>
				<snow:menuitem desc="添加功能菜单" id="addFunc" icon="fa fa-indent"></snow:menuitem>
				<snow:menuitem desc="删除" id="del" icon="fa fa-trash"></snow:menuitem>
			</snow:menu>
		</snow:Layoutleft> 
		<snow:Layoutcenter id="main">
			<snow:form id="form" dataset="FunctionMng" label="详细" fieldstr="_text,_icon,isdirectory,showseq,pagepath,funcDesc,funcType"></snow:form>
			<div style="text-align: center" id="btnDiv">
				<snow:button id="btnSave" dataset="FunctionMng"></snow:button>
				<snow:button id="btnCancel" desc="取消" icon=""></snow:button>
			</div>
			<snow:button id="btnDel" dataset="FunctionMng" hidden="true"></snow:button>
		</snow:Layoutcenter>
	</snow:layout>
	<script>
		function editor_funcType_onTip(element, opt) {
			return "&nbsp;&nbsp;特殊菜单标识（例如:首页待办功能，只支持权限设置不显示在功能菜单中,需配合首页功能)";
		}
		function initCallGetter_post() {
			tree_funcs.expand("-1");
		}
		function btnSave_postSubmit(btn, param) {
			FunctionMng_dataset.setValue("funcid", param.funcid);
			$.success("保存成功");
		}
		function btnCancel_onClick() {
			FunctionMng_dataset.cancelRecord();
		}
		function btnDel_postSubmit(btn, param) {
			$.success("操作成功!", function() {
				FunctionMng_dataset.deleteRecord();
			});
		}
		function FunctionMng_dataset_afterScroll(dataset, record) {
			var funcid = record.getValue("funcid");
			if(funcid=="-1"){//虚拟菜单
				FunctionMng_dataset.setReadOnly(true);
				$('#btnDiv').hide();
			}else{
				FunctionMng_dataset.setReadOnly(false);
				$('#btnDiv').show();
			}
		}
		function menu_menu_onClick(element, item) {
			menu_menu.srcRecord;
			switch (item.id) {
			case "addFolder":
				var pid = FunctionMng_dataset.getValue("funcid");
				var success = FunctionMng_dataset.insertRecord("append");
				if (success) {
					if(pid=="-1"){
						pid = "";
					}
					FunctionMng_dataset.setValue("location",2);
					FunctionMng_dataset.setValue("funcType","0");
					FunctionMng_dataset.setValue("lastdirectory", pid);
					FunctionMng_dataset.setValue("isdirectory", 1);
				}
				break;
			case "addFunc":
				var pid = FunctionMng_dataset.getValue("funcid");
				var success = FunctionMng_dataset.insertRecord("append");
				if(success){
					FunctionMng_dataset.setValue("location",2);
					FunctionMng_dataset.setValue("funcType","0");
					FunctionMng_dataset.setValue("lastdirectory", pid);
					FunctionMng_dataset.setValue("isdirectory", 0);
				}
				break
			case "del":
				if (FunctionMng_dataset.getValue("funcid")) {
					$.confirm("确定删除？", function() {
						btnDel.click();
					});
				} else {
					FunctionMng_dataset.deleteRecord();
				}
				break;
			}
		}
		function tree_funcs_onContextmenu(menu, record) {
			var funcid = record.getValue("funcid");
			if(funcid=="-1"){//虚拟菜单
				menu.hideItem("addFunc");
				menu.hideItem("del");
				menu.showItem("addFolder");
			}else{
				var isdirectory = record.getValue("isdirectory");
				var saved = !!record.getValue("funcid");
				if (saved) {//菜单是否已保存
					if (isdirectory == "1") {//目录菜单
						menu.showItem("addFunc");
						menu.showItem("del");
						menu.showItem("addFolder");
					} else if (isdirectory == "0") {//功能菜单 
						menu.hideItem("addFolder");
						menu.showItem("addFunc");//首页特殊功能菜单
						menu.showItem("del");
					} else {
						$.warn("菜单不能处理!")
					}
				} else {
					menu.hideItem("addFunc");
					menu.hideItem("addFolder");
					menu.showItem("del");
				}
			}
		}
	</script>
</snow:page>
