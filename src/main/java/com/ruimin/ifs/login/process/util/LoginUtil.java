package com.ruimin.ifs.login.process.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.core.bean.Function;
import com.ruimin.ifs.framework.session.SessionUtil;

public class LoginUtil {
	/**
	 * 获取登录真实IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getLoginIp(HttpServletRequest request) {
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
	
	public static String getTopFunctionName(Function currentFunc) throws SnowException {
		SessionUserInfo user = SessionUtil.getSessionUserInfo();
		Map<String, Function> functions = user.getUserFuncMap();
		String retName = getParentFunc(currentFunc, functions);
		if (StringUtils.isBlank(retName)) {
			retName = currentFunc.getFuncname();
		}
		return retName;
	}

	private static String getParentFunc(Function currentFunc, Map<String, Function> functions) {
		String retNm = null;
		if (StringUtils.isNotBlank(currentFunc.getLastdirectory())) {
			Function parentFunc = (Function) functions.get(currentFunc.getLastdirectory());
			if (parentFunc != null && StringUtils.isNotBlank(parentFunc.getLastdirectory())) {
				retNm = getParentFunc(parentFunc, functions);
			} else if (parentFunc != null) {
				retNm = parentFunc.getFuncname();
			}
		}
		return retNm;
	}
	
	public static String formateDateTime(String dtTm) throws SnowException {
		if (StringUtils.isBlank(dtTm)) {
			return "";
		}
		try {
			Date date = DateUtils.parseDate(dtTm, new String[] { "yyyyMMddHHmmss", "yyyyMMdd" });
			return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			StringBuffer buff = new StringBuffer();
			buff.append(dtTm.substring(0, 4)).append("-");
			buff.append(dtTm.substring(4, 6)).append("-");
			buff.append(dtTm.substring(6, 8));
			if (dtTm.length() > 8) {
				buff.append(" ").append(dtTm.substring(8, 10));
				buff.append(":").append(dtTm.substring(10, 12));
				buff.append(":").append(dtTm.substring(12, 14));
			}
			return buff.toString();
		}
	}
}
