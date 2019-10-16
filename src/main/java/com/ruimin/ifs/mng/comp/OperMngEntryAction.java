package com.ruimin.ifs.mng.comp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.encrypt.EncryptUtil;
import com.ruimin.ifs.core.encrypt.EncryptUtil.EncryptType;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.core.util.constant.SnowErrorCode;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.core.WebConstants;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.framework.utils.SysParamUtil;
import com.ruimin.ifs.mng.process.service.OperMngEntryService;
import com.ruimin.ifs.po.TblStaff;
import com.ruimin.ifs.po.TblStaffRoleRel;

@SnowDoc(desc = "用户管理查询")
@ActionResource
public class OperMngEntryAction extends SnowAction{
	private static final String DEF_PASSWORD = "123456";
	@Action
	@SnowDoc(desc = "用户查询")
	public PageResult queryAll(QueryParamBean queryBean) throws SnowException{
		String upbrcode = queryBean.getParameter("upbrcode");
		String tlrno = queryBean.getParameter("qtlrno");
		String tlrName = queryBean.getParameter("qtlrname");
		if(("0000").equals(upbrcode)){
			upbrcode = "";
		}
		return OperMngEntryService.getInstance().queryAll(upbrcode, tlrno, tlrName, queryBean.getPage());
	}
	
	@SuppressWarnings("rawtypes")
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="新增 / 修改")
	public void saveOrUpdate(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("OperMngEntry");
		String paramStat = reqBean.getParameter("paramStat");
		String tlrno = reqBean.getParameter("tlrno");
		String s = reqBean.getParameter("s");
		TblStaff tblStaff = new TblStaff();
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), tblStaff);
		}
		OperMngEntryService operMngEntryService = OperMngEntryService.getInstance();
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		String msg = "";
		if("add".equals(paramStat)){
			// 新增
			if(null != operMngEntryService.checkId(tblStaff.getTlrno())){
				SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_WEB_0034, new String[]{"操作员号"});
			}
			String encMode = SysParamUtil.getInstance().getString("PSWD.ENC_MODE", "MD5");	// 获取系统参数配置的加密方式
			if(StringUtils.isBlank(encMode)){
				encMode = "MD5";
			}
			
			String defPwd = SysParamUtil.getInstance().getString("PSWD.DEFAULT_PWD", DEF_PASSWORD);
			
			if("MD5".equals(encMode)){
				tblStaff.setPassword(EncryptUtil.getEncrypt(EncryptType.MD5).encrypt(defPwd));
			}else{
				tblStaff.setPassword(EncryptUtil.getEncrypt(EncryptType.AES).encrypt(defPwd));
			}
			tblStaff.setStatus("0");
			tblStaff.setFlag(1);
			tblStaff.setSessionId(sessionUserInfo.getSessionId());
			tblStaff.setPswderrcnt(0);
			tblStaff.setTotpswderrcnt(0);
			tblStaff.setPasswdenc(encMode);
			tblStaff.setIsLock("0");
			tblStaff.setIsLockModify("1");
			operMngEntryService.saveOperMngEntry(tblStaff);
			msg = "用户管理 - 新增用户 - id="+tblStaff.getTlrno();
		}else{
			String tlrname = tblStaff.getTlrName();
			String brcode = tblStaff.getBrcode();
			tblStaff = operMngEntryService.checkId(tblStaff.getTlrno());
			tblStaff.setTlrName(tlrname);
			tblStaff.setBrcode(brcode);
			operMngEntryService.updateOperMngEntry(tblStaff);
			msg = "用户管理 - 修改用户信息 - id="+tblStaff.getTlrno();
		}
		operMngEntryService.deleteStaffRoleRel(tlrno);	// 删除老权限
		// 增加用户岗位权限
		String[] ss = s.split(",");
		List lists = Arrays.asList(ss);
		// admin管理员必须包含管理岗
		if("admin".equals(tlrno) && !lists.contains("111")){
			SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_WEB_0043);
		}
		int size = lists.size();
		List<TblStaffRoleRel> list = new ArrayList<TblStaffRoleRel>();
		for (int i = 0; i < size; i++) {
			TblStaffRoleRel tblStaffRoleRel = new TblStaffRoleRel();
			tblStaffRoleRel.setId(ContextUtil.getUUID());
			tblStaffRoleRel.setTlrno(tlrno);
			tblStaffRoleRel.setRoleId(ss[i]);
			tblStaffRoleRel.setStatus("1");
			list.add(tblStaffRoleRel);
		}
		operMngEntryService.insertStaffRoleRel(list);
		
		
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),msg});
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="有效/无效")
	public void modStat(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("OperMngEntry");
		String foo = reqBean.getParameter("foo");
		TblStaff tblStaff = new TblStaff();
		OperMngEntryService operMngEntryService = OperMngEntryService.getInstance();
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), tblStaff);
		}
		tblStaff = operMngEntryService.checkId(tblStaff.getTlrno());
		if("admin".equals(tblStaff.getTlrno())){
			SnowExceptionUtil.throwWarnException(WebConstants.MESSAGE_WEB_0044, new String[]{"设置"});
		}
		tblStaff.setFlag(Integer.parseInt(foo));
		operMngEntryService.updateOperMngEntry(tblStaff);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		String msg = "";
		if("1".equals(foo)){
			msg = "更改用户状态为有效,id="+tblStaff.getTlrno();
		}else{
			msg = "更改用户状态为无效,id="+tblStaff.getTlrno();
		}
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),msg});
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="解锁")
	public void unLock(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("OperMngEntry");
		String foo = reqBean.getParameter("foo");
		TblStaff tblStaff = new TblStaff();
		OperMngEntryService operMngEntryService = OperMngEntryService.getInstance();
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), tblStaff);
		}
		tblStaff = operMngEntryService.checkId(tblStaff.getTlrno());
		tblStaff.setIsLock(foo);
		tblStaff.setLockReason("");
		tblStaff.setIsLockModify("1");
		operMngEntryService.updateOperMngEntry(tblStaff);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"解锁用户状态,id="+tblStaff.getTlrno()});
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="删除")
	public void delete(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("OperMngEntry");
		String foo = reqBean.getParameter("foo");
		if("admin".equals(foo)){
			SnowExceptionUtil.throwWarnException(WebConstants.MESSAGE_WEB_0044, new String[]{"删除"});
		}
		OperMngEntryService.getInstance().deleteOperMngEntryById(foo);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"删除用户,id="+foo});
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="重置密码")
	public void restPassword(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("OperMngEntry");
		String foo = reqBean.getParameter("foo");
		TblStaff tblStaff = new TblStaff();
		OperMngEntryService operMngEntryService = OperMngEntryService.getInstance();
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), tblStaff);
		}
		tblStaff = operMngEntryService.checkId(tblStaff.getTlrno());
		String passwdenc = tblStaff.getPasswdenc();
		if(StringUtils.isBlank(passwdenc)){
			passwdenc = "MD5";
		}
		
		String defPwd = SysParamUtil.getInstance().getString("PSWD.DEFAULT_PWD", DEF_PASSWORD);
		
		if("MD5".equals(passwdenc)){
			tblStaff.setPassword(EncryptUtil.getEncrypt(EncryptType.MD5).encrypt(defPwd));
		}else{
			tblStaff.setPassword(EncryptUtil.getEncrypt(EncryptType.AES).encrypt(defPwd));
		}
		operMngEntryService.updateOperMngEntry(tblStaff);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"删除用户,id="+foo});
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="强制签退")
	public void powerLogout(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("OperMngEntry");
		String foo = reqBean.getParameter("foo");
		TblStaff tblStaff = new TblStaff();
		OperMngEntryService operMngEntryService = OperMngEntryService.getInstance();
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), tblStaff);
		}
		tblStaff = operMngEntryService.checkId(tblStaff.getTlrno());
		tblStaff.setStatus(foo);
		operMngEntryService.updateOperMngEntry(tblStaff);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"删除用户,id="+foo});
	}
	
}
