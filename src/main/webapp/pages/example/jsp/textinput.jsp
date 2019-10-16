<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="demo">
	<snow:dataset id="Fields" path="com.ruimin.ifs.example.dataset.Fields" init="true" readOnly=""></snow:dataset>
	<snow:button id="testRequired" desc="设置必须"></snow:button>
	<snow:button id="testReadonly" desc="设置只读"></snow:button>
	<snow:form label="表单" dataset="Fields" fieldstr="" id="f1" border="true" collapsible="true" width="90%"></snow:form>
	<script>
	function initCallGetter_post() {
	}
	function Fields_dataset_c1_init(options){
		options.selectBoxWidth=350;
	}
		function editor_f1_onRefresh(element, v) {
			if (v) {
				return "<a href=''>" + v + "</a>";
			} else {
				return "&nbsp;";
			}
		}
		function editor_f1_onTip(element, opt) {
			//opt.wrap=true;
			return "tip:这是通过editor_f1_onTip事件设置的";
		}
		var f = false;
		function testRequired_onClick() {
			f = !f;
			
			var c = Fields_dataset.fields.fieldCount;
			for(var i=0;i<c;i++){
				var field = Fields_dataset.fields[i];
				Fields_dataset.setFieldRequired(field.fieldName, f);
				
			}
		}
		function testReadonly_onClick() {
			Fields_dataset.setReadOnly(!Fields_dataset.readOnly);
		}
		function Fields_dataset_s1_onSelect(v) {
			//alert("select " + v)
		}
		function Fields_dataset_c3_onSelect(dropdown, record) {
			//alert("select " + record)
		}
	</script>

</snow:page>
