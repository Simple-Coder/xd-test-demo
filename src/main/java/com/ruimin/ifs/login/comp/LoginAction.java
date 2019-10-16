package com.ruimin.ifs.login.comp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.global.GlobalUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.FormRequestBean;
import com.ruimin.ifs.framework.dataobject.FormReturnBean;
import com.ruimin.ifs.framework.session.SessionManager;
import com.ruimin.ifs.framework.session.SessionUtil;
import com.ruimin.ifs.framework.utils.WebCfgUtil;
import com.ruimin.ifs.login.process.LogService;
import com.ruimin.ifs.login.process.LoginService;
import com.ruimin.ifs.login.process.bean.LoginBean;
import com.ruimin.ifs.po.TblLoginLog;
import com.ruimin.ifs.po.TblStaff;

@SnowDoc(desc = "登录模块")
@ActionResource
public class LoginAction extends SnowAction{
	
	@Action(propagation = Propagation.REQUIRE_NEW)
	@SnowDoc(desc = "更新用户安全信息")
	public void updatePasswordInfo(TblStaff staff) throws SnowException {
		LoginService.getInstance().updatePasswordInfo(staff);
	}
	
	/**
	 * 获取登录真实IP
	 * 
	 * @param request
	 * @return
	 */
	private String getLoginIp(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (StringUtils.isNotBlank(ipAddress) && ipAddress.length() > 15) {
			int idx = ipAddress.indexOf(",");
			if (idx > 0) {
				ipAddress = ipAddress.substring(0, idx);
			}
		}
		return ipAddress;
	}
	
	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc="用户登录")
	public FormReturnBean snowLogin(FormRequestBean requestBean) throws SnowException{
		FormReturnBean retBean = new FormReturnBean();
		String opsType = "login";
		boolean successFlag = false;
		String remark = null;
		LoginBean loginBaen = DataObjectUtils.map2bean(requestBean.getRequestParams(), LoginBean.class);
		String logId = ContextUtil.getUUID();
		String username = loginBaen.getUserName();
		String password = loginBaen.getPassword();
		String brno = loginBaen.getBrno();
		boolean isSaveLog = true;
		String loginIp = this.getLoginIp(requestBean.getRequest());
		String sessionId = requestBean.getRequest().getSession().getId();
		try{
			//生产模式验证码生效
			if (GlobalUtil.isProductMode() && WebCfgUtil.getWebCfgIntValue("loginVerifyCode") >= 0) {
				String verifyCode = requestBean.getParameter("verifyCode");
				if (StringUtils.isNotBlank(verifyCode)) {
					Object imgCodeObj = SessionManager.getInstance().getSessionObject(requestBean.getRequest(),
							"LOGIN_IMG_CODE");
					if (imgCodeObj == null) {
						isSaveLog = false;
						throw new Exception("验证码错误!");
					} else {
						String imgCode = imgCodeObj.toString();
						if (!verifyCode.equalsIgnoreCase(imgCode)) {
							isSaveLog = false;
							throw new Exception("验证码错误!");
						}
					}
				} else {
					isSaveLog = false;
					throw new Exception("验证码错误!");
				}
			}
			UsernamePasswordToken userToken = new UsernamePasswordToken(username, password.toCharArray());
			userToken.setHost(brno);
			try {
				SecurityUtils.getSubject().login(userToken);
			} catch (AuthenticationException e) {
				throw e;
			}
			//语言
			String language = GlobalUtil.getDefLanguage();
			String[] lang = language.split("_");
			Locale locale = new Locale(lang[0], lang[1]) ;
			//session信息
			SessionUserInfo userinfo = SessionUserInfo.getSessionUserInfo();
			userinfo.setIp(loginIp);
			userinfo.setSessionId(sessionId);
			SessionUtil.setSessionUserInfo(requestBean.getRequest(),userinfo);
			userinfo.setLocale(locale);
			if (userinfo.getPassInfo().isPswdForcedToChange()) {//需要修改密码
				retBean.setSendUrl("/pages/login/changePwd.jsp");
			}else{//登录成功
				retBean.setSendUrl("/pages/login/index.jsp");
				//更新用户登录信息
				LoginService.getInstance().updateUserLoginSucess(userinfo);
				successFlag = true;
				remark = "登录成功";
			}
		}catch (Exception e) {//登录失败
			retBean.setSendUrl("/");
			retBean.getReturnAttrMap().put("REQ_MSG", e.getMessage());
			successFlag = false;
			remark = "登录失败:"+e.getMessage();
		} finally {
			if (isSaveLog) {
				//记录登录日志
				TblLoginLog tblLoginLog = new TblLoginLog();
				tblLoginLog.setLogId(logId);
				tblLoginLog.setTlrNo(username);
				tblLoginLog.setBrNo(brno);
				tblLoginLog.setLoginAddr(loginIp);
				tblLoginLog.setSessionId(sessionId);
				LogService.getInstance().saveOrUpdateLog(tblLoginLog, opsType, successFlag, remark);
			}
		}
		return retBean;
	}
	
	@Action(propagation = Propagation.TRX_NONE)
	@SnowDoc(desc="用户退出")
	public String snowLogOut(FormRequestBean requestBean) throws SnowException{
		SessionUserInfo userInfo = SessionUtil.getSessionUserInfo(requestBean.getRequest());
		try {
			this.userLogOutUpdate(userInfo);
		}finally{
			SessionManager.getInstance().destroySessionData(requestBean.getRequest());
			SessionUtil.removeSessionUserInfo(requestBean.getRequest());
			SessionUserInfo.removeSessionUserInfo();
			SessionManager.getInstance().destroySession(requestBean.getRequest());
		}
		return "/";
	}
	
	@Action(propagation=Propagation.REQUIRED)
	public void userLogOutUpdate(SessionUserInfo userInfo) throws SnowException{
		String opsType = "loginout";
		boolean successFlag = false;
		String remark = null;
		try{
			if(userInfo==null){
				userInfo = SessionUserInfo.getSessionUserInfoNoException();
			}
			if (userInfo!=null) {
				LoginService.getInstance().setLoginOutInfo(userInfo.getTlrno());
				successFlag = true;
				remark = "用户登出";
				//记录登录日志
				TblLoginLog tblLoginLog = new TblLoginLog();
				LogService.getInstance().saveOrUpdateLog(tblLoginLog, opsType, successFlag, remark);
			}
		}catch (SnowException e) {
			throw e;
		}
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="重新启动应用时，设置用户为登录状态")
	public void setAllUserLogOut() throws SnowException{
		LoginService.getInstance().updateUserLogOut();
	}
}
