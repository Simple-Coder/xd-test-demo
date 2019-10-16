<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="普通表格">
	<style>
.imp .l-grid-row-cell {
	background: #F1D3F7 !important;
}
</style>
<snow:layout id="">
	<snow:dataset id="GridFieldsAlias" path="com.ruimin.ifs.example.dataset.GridFields" init="true"></snow:dataset>
	<snow:grid dataset="GridFieldsAlias" id="grid" height="100%" toolbar="tt" label="普通表格" pagecache="false" fieldstr="r1,select,f1,f2,f3,n2,n3,d2" exporter="xlsExp" >
	</snow:grid>
	<snow:toolbar id="tt">
		<snow:query id="" dataset="GridFieldsAlias" fieldstr="f1,f2,c1" btnright="true"></snow:query>
	</snow:toolbar>
	<snow:exporter dataset="GridFieldsAlias" id="xlsExp" type="XLS|CSV"></snow:exporter>
</snow:layout>
	
	<script>
		function export_onClick() {
			GridFields_dataset_exporter.show();
		}
		function grid_col1_onRefresh(record, fid) {
			return "<a href='#'>No." + record.getValue(fid) + "</a>";
		}
		function grid_onDblClick(record) {
			$.alert("双击:" + record.getValue("f2"))
		}
/* 		var ix = 1;
		function GridFields_1dataset_beforeScroll() {
			return ix++ < 4;
		} */
		function dropDown_onGetRecord() {
			return GridFieldsAlias_dataset.record;
		}
	</script>

</snow:page>
