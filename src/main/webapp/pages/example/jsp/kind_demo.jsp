<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="kindEditor">
	<snow:dataset id="KindBean" path="com.ruimin.ifs.example.dataset.KindBean" init="true" submitMode="all"></snow:dataset>
	<table class="form-table" style="width:100%">
			<tr>
				<td class="form-label">普通文本:</td>
				</td>
				<td class="form-input" colspan="3">
					<snow:formfield dataset="KindBean" fid="f1"></snow:formfield>
				</td>
			</tr>
			<tr>
				<td class="form-label">KindEditor1:</td>
				<td colspan="3">
					<textarea id="kind1" name="kind1" style="width:80%;height:200px;"></textarea>
				</td>
			</tr>
			<tr>
				<td class="form-label">KindEditor2:</td>
				<td colspan="3">
					<textarea id="kind2" name="kind2" style="width:80%;height:200px;"></textarea>
				</td>
			</tr>
			<tr>
				<td class="form-label">KindEditor3:</td>
				<td colspan="3">
					<textarea id="kind3" name="kind3" style="width:80%;height:200px;"></textarea>
				</td>
			</tr>
		</table>
		<snow:button id="btSave" dataset="KindBean" ></snow:button>	
	<jsp:include page="/public/kind/page/KindEditor.jsp"></jsp:include>
	<script type="text/javascript">
	var k1 = null;
	var k2 = null;
	var k3 = null;
	KindEditor.ready(function(K) {
		k1 = Kind.init("kind1",true);
		//自定义工具栏
		var options= {
				allowImageUpload : false,
				items : [
					'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', '|', 'emoticons']
				};
		k2 = Kind.init("kind2",false,options);
		
		k3 = Kind.init("kind3",false);
	});

	function KindBean_dataset_flushDataPost(ds){
		Kind.setValue(k1,ds.getValue("f2"),true);
		Kind.setValue(k2,ds.getValue("f3"),false);
	}
	
	
	function btSave_onClickCheck(){
		var bl = Kind.isEmpty(k3);
		if(bl){
			$.warn("请输入内容!");
			return false;
		}
		//kind获取值默认必须进行编码，否则xss进行拦截
		KindBean_dataset.setValue("f2",Kind.getValue(k1));
		KindBean_dataset.setValue("f3",Kind.getValue(k2));
		KindBean_dataset.setValue("f4",Kind.getValue(k3));
		return true;
	}
	
	function btSave_postSubmit(btn,param){
		$.success("操作成功!", function() {
        });
	}
	
	</script>
</snow:page>
