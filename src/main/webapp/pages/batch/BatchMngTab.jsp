<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="批量设置">
	<snow:tabs id="tabsId" menu="false" selected="systemTab">
		<snow:tab title="系统信息设置" id="systemTab" url="/pages/batch/SystemInfMng.jsp" closable="false"></snow:tab>
		<snow:tab title="批量任务设置" id="jobTab" url="/pages/batch/BatchJobMng.jsp" closable="false"></snow:tab>
		<snow:tab title="批量运行设置" id="runTab" url="/pages/batch/BatchRunMng.jsp" closable="false"></snow:tab>
	</snow:tabs>
</snow:page>
