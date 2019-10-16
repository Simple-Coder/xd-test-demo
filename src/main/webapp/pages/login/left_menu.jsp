<%@page import="com.ruimin.ifs.login.process.util.PinYinUtil"%>
<%@page import="com.ruimin.ifs.login.process.util.LoginUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ruimin.ifs.framework.core.bean.Function"%>
<%@page import="java.util.Map"%>
<%@page import="com.ruimin.ifs.framework.session.SessionUtil"%>
<%@page import="com.ruimin.ifs.framework.core.SessionUserInfo"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
int topFuncCnt = 0;
%>
<ul id="menus" class="nav nav-list" style="top: 0px;">
	<li id="menu_home" class="active"><a href="javascript:void(0)" onclick="doWork('home', '主页', '/pages/login/home1.jsp')"> <i class="menu-icon fa fa-home"></i> <span class="menu-text"> 主页 </span>
	</a> <b class="arrow"></b></li>
	<%!public void append(StringBuffer sb, Function func, int level) {
		sb.append("<li id=\"menu_" + func.getFuncid() + "\" class=''>");
		sb.append("<a href='javascript:void(0)'");
		if (!func.children().isEmpty()) {
			sb.append(" class='dropdown-toggle'");
		} else {
			sb.append(" onclick='doWork(\"" + func.getFuncid() + "\",\"" + func.getFuncname() + "\",\"" + func.getPagepath()
			        + "\")'");
		}
		sb.append(">");
		sb.append("<i class='menu-icon ");
		if (func.getIconCls() == null || "".equals(func.getIconCls())) {
			sb.append("fa fa-caret-right");
		} else {
			sb.append(func.getIconCls());
		}
		sb.append("'></i> ");
		sb.append("<span class='menu-text'> " + func.getFuncname() + " </span> ");
			if (!func.children().isEmpty()) {
				sb.append("<b class='arrow fa fa-angle-down'></b>");
			}
			
		sb.append("</a> ");
		sb.append("<b class='arrow'></b>");
		if (!func.children().isEmpty()) {
			sb.append("<ul class='submenu'>");
			for (Function f : func.children()) {
				append(sb, f, level + 1);
			}
			sb.append("</ul>");
		}
		sb.append("</li>");
	}%>
	<%
		SessionUserInfo user = SessionUtil.getSessionUserInfo(request);
		Map<String, Function> functions = user.getUserFuncMap();
		List<Function> firstLevelFunctions = new ArrayList<Function>();
		for (Function func : functions.values()) {
			func.children().clear();
			if("1".equals(func.getFuncType())) {
				continue;
			}
			if (!functions.containsKey(func.getLastdirectory())) {
				firstLevelFunctions.add(func);
			}
			if(StringUtils.isBlank(func.getLastdirectory())){
				topFuncCnt++;
			}
		}

		for (Function func : functions.values()) {
			if("1".equals(func.getFuncType())) {
				continue;
			}
			String pid = func.getLastdirectory();
			if (functions.containsKey(pid)) {
				functions.get(pid).addChild(func);
			}
		}
	%>
	<%
		StringBuffer sb = new StringBuffer();
		for (Function func : firstLevelFunctions) {
			append(sb, func, 0);
		}
		out.print(sb);
	%>
</ul>
<script>
	function selectMenu(id) {
		$("#menus .active").removeClass("active");
		$("#menu_" + id).closest("li.open").andSelf().addClass("active");
	}
	var menuArray = new Array();
	<%
	for (Function func : functions.values()) {
		if(StringUtils.isNotBlank(func.getPagepath()) && !StringUtils.equals(func.getFuncType(), "1")){
	%>
		var obj = new Object();
		obj.id='<%=func.getFuncid()%>';
		obj.name='<%=func.getFuncname()%>';
		obj.path = "<%=func.getPagepath()%>";
		obj.pinyin = "<%=PinYinUtil.getFirstSpell(func.getFuncname())%>";
		obj.allPinYin = "<%=PinYinUtil.getFullSpell(func.getFuncname())%>";
		obj.topName = "<%=LoginUtil.getTopFunctionName(func) %>";
		menuArray.push(obj);
	<%}}%>
</script>