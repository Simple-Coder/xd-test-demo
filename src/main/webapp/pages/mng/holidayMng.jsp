<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="工作日期维护">
<snow:dataset id="holidayDs" path="com.ruimin.ifs.mng.dataset.Holiday" init="true" submitMode="current"></snow:dataset>
<snow:query id="query" dataset="holidayDs" fieldstr="year" label="请输入查询条件" width="85%"></snow:query>
<snow:grid dataset="holidayDs" id="daylist" fieldstr="year,sunWorkDay,sunHoliDay" paginationbar="btnAdd,btnUpdate,btnDetail" width="85%"></snow:grid>
<snow:window id="holidayAdd" closable="true" title="工作日期维护" modal="true" buttons="btAddOk">
	<snow:form id="holidayAddForm" dataset="holidayDs" label="" border="false" fieldstr="year"></snow:form>
	<snow:button id="btAddOk" dataset="holidayDs" hidden="true" ></snow:button>
</snow:window>
<script type="text/javascript">
	function btAddOk_onClickCheck(){
		var yr = holidayDs_dataset.getValue("year");
		if(!yr){
			$.warn("年份不能为空!");
			return false;
		}
		return true;
	}

	function btAddOk_postSubmit(btn,retmap){
		if(retmap){
			var retid = retmap.id;
			var yr = retmap.year;
			window_holidayAdd.close();
			if(retid.length>0){
				editOpen(retid,yr);
			}else{
				$.open("holidayadd", "工作日期维护", "/pages/mng/holidayMngDetail.jsp?year="+yr, "750", "500", false, true, null, false,"确定,取消");
			}
		}
	}

	function btnAdd_onClick(){
		window_holidayAdd.open();
	}
	
	function window_holidayAdd_beforeClose(wmf){
		holidayDs_dataset.cancelRecord();
	}
	function window_holidayAdd_onButtonClick(index, win) {
		btAddOk.click();
	}
	
	function btnDetail_onClick(){
		var id = holidayDs_dataset.getValue("id");
		var yr = holidayDs_dataset.getValue("year");
		if (id && yr) {
			editOpen(id,yr);
		}else{
			$.warn("请选择操作记录");
		}
	}
    function btnUpdate_onClick(){
        var id = holidayDs_dataset.getValue("id");
        var yr = holidayDs_dataset.getValue("year");
        var type = "edit";
        editOpen(id,yr);
    }
	function editOpen(id,yr){
		$.open("holidayadd", yr+'年', "/pages/mng/holidayMngDetail.jsp?id="+id, "750", "500", false, true, null, false,"确定,取消");
	}
	
	function holidayadd_onButtonClick (i,win,framewin){
		if(i==1){//取消按钮
			win.close();
		}else if(i==0){
			framewin.saveHoliday();
		}
	}
	
	function editPost(){
		holidayadd.close();
		holidayDs_dataset.flushData(holidayDs_dataset.pageIndex);
	}
	
	
</script>
</snow:page>
