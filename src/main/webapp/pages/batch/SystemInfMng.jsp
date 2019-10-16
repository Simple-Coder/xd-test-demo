<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="系统信息设置">
	<snow:dataset id="BatchRunSysInf" path="com.ruimin.ifs.batch.dataset.BatchRunSysInf" init="true" submitMode="current"></snow:dataset>
	<snow:form id="form" dataset="BatchRunSysInf" label="系统信息" border="true" fieldstr="systemName,tbsdy,bhdate,lbhdate,systemType,status,miscflgs" colnum="2"></snow:form>
	<div style="width: 99%;text-align:center; ">
		<snow:button id="btnSave" dataset="BatchRunSysInf"></snow:button>
	</div>
	<script type="text/javascript">
	function btnSave_postSubmit() {
		$.success("操作成功!", function() {
			BatchRunSysInf_dataset.flushData(1);
		});
	}
	</script>
</snow:page>
