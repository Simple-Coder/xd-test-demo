<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="demo">
	<snow:dataset id="Fields" path="com.ruimin.ifs.example.dataset.Fields" init="true"></snow:dataset>
	<snow:form label="表单" dataset="Fields" fieldstr="s1,s2,s4,s5,c1,c2,c3,c4,c5" id="f1" border="true" collapsible="true"></snow:form>
	<script>
		function Fields_dataset_c1_beforeOpen(dropdown,dpds){
			dropdown.cache=false;
			dpds.setParameter("t","1");
		}
		
		function Fields_dataset_c1_init(options){
			options.selectBoxWidth=400;
			options.selectBoxHeight=400;
		}
		
		
		function Fields_dataset_c3_init(options){
			options.selectBoxWidth=400;
			options.selectBoxHeight=200;
		}
		
		function Fields_dataset_c5_init(options){
			options.selectBoxWidth=800;
			options.selectBoxHeight=500;
			options.title="测试";
		}
	</script>

</snow:page>
