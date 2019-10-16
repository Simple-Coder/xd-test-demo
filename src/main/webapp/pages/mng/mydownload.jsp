<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="批量导出下载任务">

	<snow:dataset id="mydownload" path="com.ruimin.ifs.mng.dataset.BatchExpTask" init="true" readOnly="true"></snow:dataset>
	<snow:query id="query" label="请输入查询条件" dataset="mydownload" fieldstr="tskNm,tskStartTms,tskStat"></snow:query>
	<snow:grid dataset="mydownload" id="grid" fitcolumns="true" fieldstr="fileNm,tskStartTms[130],tskEndTms[130],expRcdSumNum,expFileSize,tskStat[60],op[60]"></snow:grid>
	<snow:window id="winDetail" title="导出失败原因" modal="true" closable="true" width="800">
	</snow:window>
	<iframe name="dfrm" id="dfrm" height="0" width="0" src="" style="display: none"></iframe>
	<script>
		function grid_op_onRefresh(record, fid, value) {
			if (record) {//当存在记录时
				var st = record.getValue("tskStat");
				if (st == "3") {
					return "<a href='JavaScript:download(\"" + value + "\")' title='下载'><i class='fa fa-download'></i></a> ";
				}else if(st == "4"){//运行失败
					return "<a href='JavaScript:showTskErrMsg()' title='查看错误信息'><i class='fa fa-wpforms'></i></a> ";
				}else {
					return "<a href='JavaScript:void(0)' title='不能操作'><i class='fa fa-ban'></i></a> ";
				}
			}
		}
		function download(id) {
			var url = '<snow:url flowId="com.ruimin.ifs.mng.comp.BatchExpTaskAction:download" isDownLoad="true"/>' + "&oid=" + id;
			document.getElementById('dfrm').src = url;
		}
		
		function showTskErrMsg(){
			$("#window_winDetail").html(mydownload_dataset.getValue("tskErrMsg"));
			window_winDetail.open();
		}
	</script>
</snow:page>
