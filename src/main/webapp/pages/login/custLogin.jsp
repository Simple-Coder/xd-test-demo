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
    %>
    <link href="<%=contextPath%>/pages/login/css/login.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=contextPath%>/pages/login/js/b.js"></script>
    <title>后管子系统</title>
</head>
<body>
<div class="center" style="display:none;">
    <div class="container">
        <div class="khao" id="khao"></div>
        <div class="form-div" id="form-div">
            <%
                String action = request.getContextPath() + "/login.ifs";
                System.out.println("login test1");
                System.out.println(request.getParameter("userNo"));
                System.out.println(request.getParameter("passWord"));
                System.out.println(request.getParameter("userIp"));
                System.out.println(request.getParameter("userBrh"));
                System.out.println(request.getParameter("encryptedUid"));
            %>
            <snow:sform name="loginForm" flowId="com.ruimin.ifs.login.comp.CustLoginAction:custLogin" action="<%=action%>" className="form">
                <h3>用户登录</h3>
                <div style="display: none;" >
                    <input type="text" id="userNo" name="userNo"  value="<%=request.getParameter("userNo") %>" />
                    <input type="text" id="passWord" name="passWord"  value="<%=request.getParameter("passWord") %>" />
                    <input type="text" id="userIp" name="userIp"  value="<%=request.getParameter("userIp") %>" />
                    <input type="text" id="userBrh" name="userBrh"  value="<%=request.getParameter("userBrh") %>" />
                    <input type="text" id="encryptedUid" name="encryptedUid"  value="<%=request.getParameter("encryptedUid") %>" />
                </div>
                <div class="btn-div">
                    <a onclick="submitForm()">登 录</a>
                </div>
                <div id="messageinfo" style="font-family: Microsoft Yahei; font-size: 15px; font-weight: 200; color: orangered;">
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
<script src="${pageContext.request.contextPath}/public/lib/jquery/jquery-1.8.2.js"></script>
<script type="text/JavaScript">
    $(function(){
        document.loginForm.submit();
    });
</script>
</body>
