<%@page import="com.ruimin.ifs.framework.utils.WebCfgUtil"%>
<%@page import="com.ruimin.ifs.framework.utils.ValueUtil"%>
<%@page import="com.ruimin.ifs.framework.session.SessionUtil"%>
<%@page import="com.ruimin.ifs.framework.session.SessionManager"%>
<%@page import="com.ruimin.ifs.framework.core.SessionUserInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/snowweb" prefix="snow"%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
	String contextPath = request.getContextPath();
	int vLen = WebCfgUtil.getWebCfgIntValue("loginVerifyCode");
	boolean isShowVerCode = vLen>0?true:false;
	String headTitle = WebCfgUtil.getWebCfgValue("headTitle");
%>
<link href="<%=contextPath%>/pages/login/css/login.css?_v=2" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/pages/login/js/b.js"></script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon" />
<title>iFinSnow睿雪应用开发平台</title>
<script type="text/JavaScript">
var _application_root = "<%=contextPath%>";
if (window.self != window.top) {
    window.open("<%=contextPath%>/", "_top");
}
function func_check()
{
	document.getElementById("messageinfo").innerHTML="";
	document.loginForm.userName.focus();
	<%if (request.getAttribute("REQ_MSG") != null) {
				String errMsg = ((String) request.getAttribute("REQ_MSG"))
						.replace("\n", "[n]");%>
	var errMsg = "<%=errMsg%>";
		document.getElementById("messageinfo").innerHTML = errMsg.replace("[n]", "\n");
<%}%>
	
<%SessionUserInfo userInfo = SessionUtil.getSessionUserInfo(session);
			if (userInfo != null) {
				out.println("document.loginForm.userName.value = \""
						+ userInfo.getTlrno() + "\";");
				out.println("document.loginForm.userName.readOnly=true;");
				out.println("focus(document.loginForm.passWord);");
			}%>
	return;
	}
	var isenc = false;
	function submitForm() {
		if (document.loginForm.userName.value == "") {
			document.getElementById("messageinfo").innerHTML = "用户名不能为空!";
			document.loginForm.userName.focus();
			return;
		} else if (document.loginForm.passWord.value == "") {
			document.getElementById("messageinfo").innerHTML = "密码不能为空!";
			document.loginForm.passWord.focus();
			return;
		}
		<%if(isShowVerCode){%>
		if (document.loginForm.verifyCode.value == "") {
			document.getElementById("messageinfo").innerHTML = "验证码不能为空!";
			document.loginForm.verifyCode.focus();
			return;
		}
		<%}%>
		if (!isenc) {
			document.getElementById("messageinfo").innerHTML = "登录中,请稍后...";
			document.loginForm.passWord.value = new _B().encode(document.loginForm.passWord.value);
			isenc = true;
			document.loginForm.submit();
		}
	}
	function resetForm() {
		document.loginForm.reset();
		document.loginForm.userName.value = "";
		document.loginForm.passWord.value = "";
		isenc = false;
	}
	function nextEvent(strName) {
		var evt = event || window.event;
		if (evt.keyCode == 13 || evt.keyCode == 9) {
			if (strName == "submit") {
				submitForm();
			} else if (strName == "passWord") {
				document.loginForm.passWord.focus();
			}else if (strName == "verifyCode") {
				document.loginForm.verifyCode.focus();
			}
		}
	}
	<%if(isShowVerCode){%>
	function changeVerifyCode(){
		document.getElementById("verifyCodeImg").src=_application_root+"/verifyCode?date="+new Date();
    }
	<%}%>
</script>
</head>
<body onload="func_check()">
	<div class="header">
		<div class="container">
			<div class="link">
				<a href="">用户协议</a> <span>/</span> <a href="">隐私</a> <span>/</span> <a href="">登录说明</a>
			</div>
		</div>
	</div>

	<div class="center" id="login_center">
		<div class="container">
			<div class="khao" id="khao"></div>
			<div class="form-div" id="form-div">
				<%
					String action = request.getContextPath() + "/login.ifs";
				%>
				<snow:sform name="loginForm" flowId="com.ruimin.ifs.login.comp.LoginAction:snowLogin" action="<%=action%>" className="form">
					<h3>用户登录</h3>
					<div>
						<%
							String para = ValueUtil.parameterValue(request, "userName");
						%>
						<input type="text" id="userName" name="userName" placeholder="用户名" value="<%=para%>" maxlength="20" onkeypress="nextEvent('passWord');" onfocus="inputfocus(this)" onblur="inputblur(this)"> <i class="icon-user"></i>
					</div>
					<%
					String pwdNextId = "submit";
					if(isShowVerCode){ 
						pwdNextId = "verifyCode";
					}%>
					<div>
						<input type="password" id="passWord" name="passWord" placeholder="密码" onkeypress="nextEvent('<%=pwdNextId %>');" onfocus="inputfocus(this)" onblur="inputblur(this)"> <i class="icon-pwd"></i>
					</div>
					<%if(isShowVerCode){ %>
					<div>
						<input  type="text" id="verifyCode" maxlength="6" name="verifyCode" placeholder="验证码" value="" onkeypress="nextEvent('submit');"  onfocus="inputfocus(this)" onblur="inputblur(this)" style="width: 138px;float:left;margin-top:0px;"/><img style="border: 1px solid #ccc;margin-left:3px;height: 36px;" alt="验证码" src="<%=contextPath+"/verifyCode" %>" onclick="changeVerifyCode()" width="148" id="verifyCodeImg"></img>
					</div>
					<%} %>
					<div class="btn-div">
						<a onclick="submitForm()">登 录</a>
					</div>
					<div id="messageinfo" class="msginfo">
					</div>
				</snow:sform>
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="container">
			<p>&copy;&nbsp;2015&nbsp;上海睿民互联网科技有限公司</p>
		</div>
	</div>

	<script type="text/javascript">
		var hg = getHeight();
		var lch = hg - 181;
		document.getElementById("login_center").style.height = lch+"px";
		document.getElementById("khao").style.height=lch+"px";
		// 根据屏幕高度计算登录框，使其上下居中
		function initDivHeight() {
			var formDiv = document.getElementById("form-div");
			var fdHeight = formDiv.offsetHeight;
			mTop = Math.round((lch - fdHeight) / 2);
			formDiv.style.top = mTop + "px";
		}
        function getHeight(){
        	var height;
        	if(window.innerHeight){
        		height=window.innerHeight;
        	}else{
        		height=document.documentElement.clientHeight;
        	}
        	return height;
        }


        // input focus样式控制
        function inputfocus(inputtag){
        	var pardiv=inputtag.parentNode;
        	pardiv.className+=" ifocus";
         }
         function inputblur(inputtag){
        	var pardiv=inputtag.parentNode;
        	var classNames=pardiv.className.split(" ");
        	pardiv.className="";
         }
         window.onload = function(){
        	 initDivHeight();
        	 func_check();
         }
         window.onresize= function(){
        	 hg = getHeight();
     		 lch = hg - 181;
     		 document.getElementById("login_center").style.height = lch+"px";
        	 initDivHeight();
         }
	</script>
</body>
