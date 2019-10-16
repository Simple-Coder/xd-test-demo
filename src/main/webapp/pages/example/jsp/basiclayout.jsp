<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="demo_field_1">
	<snow:layout id="layout1">
		<snow:Layouttop>
			<h2>上</h2>
		</snow:Layouttop>
		<snow:Layoutbottom>
			<h2>下</h2>
		</snow:Layoutbottom>
		<snow:Layoutleft>
			<h2>左</h2>
		</snow:Layoutleft>
		<snow:Layoutright>
			<h2>右</h2>
		</snow:Layoutright>
		<snow:Layoutcenter>
			<h2>中</h2>
			<iframe src="${pageContext.request.contextPath}/pages/example/jsp/datagrid_default.jsp"></iframe>
		</snow:Layoutcenter>
	</snow:layout>
</snow:page>
