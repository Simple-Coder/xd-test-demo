<%@page import="com.ruimin.ifs.login.process.bean.UserExtInfo"%>
<%@page import="com.ruimin.ifs.util.SnowConstant"%>
<%@page import="com.ruimin.ifs.login.process.LoginService"%>
<%@page import="com.ruimin.ifs.po.TblStaff"%>
<%@page import="com.ruimin.ifs.framework.core.SessionUserInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<snow:page title="个人设置">
<style type="text/css">
	.settab{
		width: 100%;
		line-height: 40px;
	}
	.settab tr{
		height: 30px;
		background-color: #fff;
	}
	
	.settab tr td.td1{
		background-color: #fff;
		padding-right: 15px;
		text-align: right;
		border-bottom: 1px dotted #ccc;
	}
	
	.settab tr td.td2{
		background-color: #fff;
		padding: 5px;
		text-align: left;
		border-bottom: 1px dotted #ccc;
	}
	.fm{
		width: 80%;
		padding: 3px;
	}
</style>
<%
	SessionUserInfo si = SessionUserInfo.getSessionUserInfo();
	UserExtInfo extInf = (UserExtInfo)si.getExtenMap().get(SnowConstant.USER_EXT_INF_KEY);
	Object retError = request.getAttribute("ERROR");
	Object retSuccess = request.getAttribute("SUCCESS");
	String telNo = extInf.getTelNo()==null?"":extInf.getTelNo();
%>
<div style="padding: 5px;text-align: center;">
<snow:sform flowId="com.ruimin.ifs.login.comp.HomeAction:pageSetProcess" name="pageSetForm" isSubFile="true" className="fm">
	<table class="settab"  border="0" width="100%">
		<tr height="100px">
			<td class="td1">
			个人头像:
			</td>
			<td class="td2" valign="bottom">
			<ul style="list-style: none;line-height: 26px">
				<li><div style="width: 74px;height: 74px;border: 1px dotted #ddd;"><img style="padding: 1px" alt="个人头像" src="${pageContext.request.contextPath}/public/lib/ace/avatars/user.jpg" width="72" height="72" id="pageImg"></img></div></li>
				<li><input type="file" style="width: 400px" placeholder="请选择图像文件" name="imgFile" id="imgFile"/></li>
				<li style="color: orange">图片大小不能超过1M</li>
			</ul>
			</td>
		</tr>
		<tr>
			<td class="td1">
			联系电话:
			</td>
			<td class="td2">
			<input type="text" size="30" style="line-height: 24px;height: 24px" placeholder="请输入联系电话" maxlength="20" name="telNo" id="telNo"  value="<%=telNo%>"/>
			</td>
		</tr>
		<tr>
			<td class="td1">
			E-mail:
			</td>
			<td class="td2">
			<%
			String email = extInf.getEmail();
			%>
			<input type="text" size="30" style="line-height: 24px;height: 24px" placeholder="请输入e-mail地址" maxlength="40" name="email" id="email"  value="<%=email==null?"":email%>"/>
			</td>
		</tr>
	</table>
	<br/>
<snow:button id="submit" desc="提交" icon="fa fa-save"></snow:button>
</snow:sform>
</div>

<script type="text/javascript">
	function submit_onClickCheck(){
		var imgf = document.getElementById("imgFile").value;
		var telNo = document.getElementById("telNo").value;
		var email = document.getElementById("email").value;
		if(imgf=="" && email=="" && telNo==""){
			$.warn("请修改后提交!");
			return false;
		}else if(imgf!=""){
			if(imgf.indexOf("..")>=0){
				$.warn("文件路径不合法!");
			}
			var filetypes =[".jpg",".png",".gif"]; 
			var isnext = false; 
			var fileend = imgf.substring(imgf.indexOf(".")).toLowerCase(); 
			if(filetypes && filetypes.length>0){ 
				for(var i =0; i<filetypes.length;i++){ 
					if(filetypes[i]==fileend){ 
						isnext = true; 
						break; 
					}
				}
				if(!isnext){
					$.warn("请上传图片文件!");
					return false;
				}
			}
		}
		return true;
	}

	function submit_onClick(){
		document.pageSetForm.submit();
	}
	
	function submit_postSubmit(){
		$.success("操作成功!",function() {
			changeCurrentImg();
			top.changUsrImg();
		});
	}
	
	function changeCurrentImg(){
		var url = '<snow:url flowId="com.ruimin.ifs.login.comp.HomeAction:getPageSetImg" isDownLoad="true"/>'+"&dt="+new Date();
		$("#pageImg").attr("src",url);
	}
	$(document).ready(function() {
		<%if(extInf.isHeadImg()){%>
			changeCurrentImg();
		<%}%>
		<%if(retError!=null){%>
			$.error("<%=retError.toString()%>");
		<%}else if(retSuccess!=null){%>
			$.success("操作成功!",function() {
				top.changUsrImg();
			});
		<%}%>
	});
	
</script>
</snow:page>