package com.ruimin.ifs.login.comp;

import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.global.GlobalUtil;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.FormRequestBean;
import com.ruimin.ifs.framework.dataobject.FormReturnBean;
import com.ruimin.ifs.framework.session.SessionManager;
import com.ruimin.ifs.framework.session.SessionUtil;
import com.ruimin.ifs.framework.utils.WebCfgUtil;
import com.ruimin.ifs.login.process.LogService;
import com.ruimin.ifs.login.process.LoginService;
import com.ruimin.ifs.login.process.bean.CustLoginBean;
import com.ruimin.ifs.login.process.bean.HdSSOToken;
import com.ruimin.ifs.login.process.bean.LoginBean;
import com.ruimin.ifs.po.TblLoginLog;
import com.ruimin.ifs.po.TblStaff;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Locale;

@SnowDoc(desc = "企业客户登录模块")
@ActionResource
@Slf4j
public class CustLoginAction {

    public FormReturnBean custLogin(FormRequestBean requestBean) throws SnowException, UnsupportedEncodingException {
        log.info("custLoginAction开始：");
        FormReturnBean retBean = new FormReturnBean();
        String opsType = "custlogin";
        boolean successFlag = false;
        String remark = null;
        CustLoginBean loginBaen = DataObjectUtils.map2bean(requestBean.getRequestParams(), CustLoginBean.class);
        String logId = ContextUtil.getUUID();
        String username = loginBaen.getUserNo();
        String password = loginBaen.getPassWord();
        String brno = loginBaen.getUserBrh();
        String loginAddr = requestBean.getRequest().getRemoteAddr();
        String sessionId = requestBean.getRequest().getSession().getId();
        String loginIp = this.getLoginIp(requestBean.getRequest());
        log.info("username：【{}】,password:【{}】",username,password);
        try {
            // 如果session中已经存在操作员，则不让登录其他操作员
            SessionUserInfo userInfo = SessionUtil.getSessionUserInfo(requestBean.getRequest().getSession());
            if (userInfo != null) {
                if (!userInfo.getTlrno().equals(username)) {
                    SnowExceptionUtil.throwErrorException("WEB_E0066");
                }
            }
//
//           password="null".equals(password)?null:password;
//            HdSSOToken userToken= new HdSSOToken(username,password,1);//最后一个 参数为1时不需要密码验证直接登录

            password=LoginService.getInstance().getTblStaff(username).getPassword();
            UsernamePasswordToken userToken = new UsernamePasswordToken(username, password.toCharArray());
            userToken.setHost(brno);
            try {
                SecurityUtils.getSubject().login(userToken);
            } catch (AuthenticationException e) {
                throw e;
            }
            log.info("username：【{}】通过验证！",username);
            //语言
            String language = GlobalUtil.getDefLanguage();
            String[] lang = language.split("_");
            Locale locale = new Locale(lang[0], lang[1]);

            //session信息
            SessionUserInfo userinfo = SessionUserInfo.getSessionUserInfo();
            userinfo.setIp(loginIp);
            userinfo.setSessionId(sessionId);
            SessionUtil.setSessionUserInfo(requestBean.getRequest(),userinfo);
            userinfo.setLocale(locale);

            //更新用户登录信息
            LoginService.getInstance().updateUserLoginSucess(userinfo);

            //登录成功
            retBean.setSendUrl("/pages/login/index.jsp");
            successFlag = true;
            remark = "登录成功";

        } catch (Exception e) {//登录失败
            retBean.setSendUrl("/");
            retBean.getReturnAttrMap().put("REQ_MSG", e.getMessage());
            successFlag = false;
            remark = "登录失败:" + e.getMessage();
        } finally {
            // 记录登录日志
            TblLoginLog tblLoginLog = new TblLoginLog();
            tblLoginLog.setLogId(logId);
            tblLoginLog.setTlrNo(username);
            tblLoginLog.setBrNo(brno);
            tblLoginLog.setLoginAddr(loginAddr);
            tblLoginLog.setSessionId(sessionId);
            LogService.getInstance().saveOrUpdateLog(tblLoginLog, opsType, successFlag, remark);

        }
        return retBean;

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
        }// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (StringUtils.isNotBlank(ipAddress) && ipAddress.length() > 15) {
            int idx = ipAddress.indexOf(",");
            if (idx > 0) {
                ipAddress = ipAddress.substring(0, idx);
            }
        }
        return ipAddress;
    }

    public String decryptData(String input, String rawKey) throws Exception {
        byte[] dec = new BASE64Decoder().decodeBuffer(input);
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = rawKey.getBytes();
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher c = Cipher.getInstance("DES");
        c.init(Cipher.DECRYPT_MODE, key, sr);
        byte[] clearByte = c.doFinal(dec);
        return new String(clearByte);

    }
}
