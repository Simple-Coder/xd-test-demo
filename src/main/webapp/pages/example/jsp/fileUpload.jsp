<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="文件上传">
	<snow:sform name="uploadFileForm"  flowId="com.ruimin.ifs.example.comp.FileUploadAction:uploadFileProcess" isSubFile="true">
		<input type="file" name="uf"/>
		<input type="submit" value="提交"/>		
	</snow:sform>
	<snow:groupbox id="group" label="文件上传结果">
	<%
	Object retFilePath = request.getAttribute("uploadFilePath");
	if(retFilePath!=null){
	%>
	<div style="padding: 10px">上传成功，存储路径:<%=retFilePath.toString() %></div>
	<%} %>
	</snow:groupbox>
	
</snow:page>
