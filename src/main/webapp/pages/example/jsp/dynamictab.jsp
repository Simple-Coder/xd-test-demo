<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="DynamicTabSet">
	<snow:layout id="layoutid">
		<snow:Layoutcenter id="center1" collapsed="false" title="">
			<snow:tabs id="demo" selected="t3">
				<snow:tab id="t1" title="我的主页3" closable="true" icon="fa fa-start">
				A</snow:tab>
				<snow:tab id="t3" title="baidu" url="http://www.baidu.com" ></snow:tab>
				<snow:tab id="t2" title="我的主页4" closable="true">
				B</snow:tab>
			</snow:tabs>
		</snow:Layoutcenter>
		<snow:Layoutleft id="left" collapsed="false" width="180" title="API操作">
			<ul>
				<li><snow:button id="add" desc="新增"></snow:button></li>
				<li><snow:button id="close" desc="关闭当前"></snow:button></li>
				<li><snow:button id="closeAll" desc="关闭所有"></snow:button></li>
				<li><snow:button id="updateIcon" desc="更新图标"></snow:button></li>
				<li><snow:button id="updateTitle" desc="更新标题"></snow:button></li>
				<li><snow:button id="cancelIcon" desc="取消图标"></snow:button></li>
				<li><snow:button id="closable" desc="可关闭"></snow:button> <snow:button id="noclasable" desc="不可关闭"></snow:button></li>
				<li><snow:button id="openFirst" desc="打开第1个标签页"></snow:button></li>
				<li><snow:button id="freshFirst" desc="刷新第1个标签页"></snow:button></li>
				<li><snow:button id="contextmenu" desc="绑定右键菜单"></snow:button></li>
				<li><snow:button id="uncontextmenu" desc="解绑右键菜单"></snow:button></li>
				<li><snow:button id="enable" desc="可用"></snow:button></li>
				<li><snow:button id="disable" desc="禁用"></snow:button></li>
				<li><snow:button id="length" desc="当前tab个数"></snow:button></li>
			</ul>
		</snow:Layoutleft>
	</snow:layout>

	<script>
		var ids = 0;

		function tabs_demo_beforeTabChange(tabid, pretabid) {
			//alert('before');
			//return false;
		}
		function tabs_demo_afterTabChange(tabid, pretabid) {
			//alert('after');
		}
		function add_onClick(){
			tabs_demo.add("id" + ids, "title" + ids, "helloworld" + ids);
			ids ++;
		}
		
		function close_onClick(){
			tabs_demo.close();
		}
		
		function closeAll_onClick(){
			tabs_demo.closeAll();
		}
		
		function updateIcon_onClick(){
			tabs_demo.setIconCls(tabs_demo.getSelectedId(), "fa fa-save");
		}
		
		function updateTitle_onClick(){
			tabs_demo.setTitle(tabs_demo.getSelectedId(), "我是新标题");			
		}
		
		function cancelIcon_onClick(){
			tabs_demo.setIconCls(tabs_demo.getSelectedId(), "");
		}
		
		function closable_onClick(){
			tabs_demo.setClosable(true);
		}
		
		function noclasable_onClick(){
			tabs_demo.setClosable(false);
		}
		
		function openFirst_onClick(){
			// 选中第一个标签
		}
		
		function freshFirst_onClick(){
			// 刷新第一个标签
			tabs_demo.refresh("t3");
		}
		
		function contextmenu_onClick(){
			// 绑定右键
		}
		
		function uncontextmenu_onClick(){
			// 解绑右键
		}
		
		// 启用 样式未实现
		function enable_onClick(){
			tabs_demo.setEnable(tabs_demo.getSelectedId(),true);
		}
		
		// 禁用 样式未实现
		function disable_onClick(){
			tabs_demo.setEnable(tabs_demo.getSelectedId(),false);
		}
		
		function length_onClick() {
			$.alert("当前个数：" + tabs_demo.length());
		}
		
	</script>

</snow:page>
