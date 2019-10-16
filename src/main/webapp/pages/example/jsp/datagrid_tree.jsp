<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="demo_field">
	<snow:dataset id="Tree" path="com.ruimin.ifs.example.dataset.Tree" init="true" parameters="async=false"></snow:dataset>
	<snow:treegrid dataset="Tree" id="treegrid" fieldstr="_text,_id,_parentId,_hasChild" height="400"></snow:treegrid>
</snow:page>
 