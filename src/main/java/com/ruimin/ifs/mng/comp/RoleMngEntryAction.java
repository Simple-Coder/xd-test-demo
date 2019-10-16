package com.ruimin.ifs.mng.comp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.framework.utils.JSONUtil;
import com.ruimin.ifs.mng.process.bean.TblStaffRoleRelVO;
import com.ruimin.ifs.mng.process.service.RoleMngEntryService;
import com.ruimin.ifs.po.TblResInfo;
import com.ruimin.ifs.po.TblRole;
import com.ruimin.ifs.util.SeqNoGenUtil;

@SnowDoc(desc = "岗位管理")
@ActionResource
public class RoleMngEntryAction extends SnowAction{
	@Action
	@SnowDoc(desc = "岗位查询")
	public List<Object> queryAll(QueryParamBean queryBean) throws SnowException{
		String tlrno = queryBean.getParameter("tlrno");
		List<Object> role = RoleMngEntryService.getInstance().queryAll();	// 查到所有岗位
		List<Object> roleList = new ArrayList<Object>();
		if (StringUtils.isNotBlank(tlrno)) {
			roleList = RoleMngEntryService.getInstance().queryAllByTlrno(tlrno); 	// 用户拥有的岗位权限
		}
		// 查询id
		int size = role.size();
		int size1 = roleList.size();
		if(roleList.size() > 0){
			for(int i = 0; i < size; i++){
				TblStaffRoleRelVO tsVo = (TblStaffRoleRelVO) role.get(i);
				if(roleList.size() >0){
					for (int j = 0; j < size1; j++) {
						Integer in = (Integer) roleList.get(j);
						Integer roleId = tsVo.getRoleId();
						if(roleId.equals(in)){
							tsVo.setSelect(true);
						}
						
					}
				}
			}
		}
		return role;
	}
	@Action
	@SnowDoc(desc = "岗位人员权限查询")
	public PageResult queryStaffRoleRef(QueryParamBean queryBean) throws SnowException{
		String roleId = queryBean.getParameter("roleId");
		return RoleMngEntryService.getInstance().queryStaffRoleRef(roleId, queryBean.getPage());
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="新增/修改 岗位")
	public void addRoleFunc(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("RoleMngEntry");
		String roleName = reqBean.getParameter("rb");	// 岗位名称
		String roleId = reqBean.getParameter("roleId");	// 岗位id
		String s = reqBean.getParameter("s");
		String[] ss = {};
		if(StringUtils.isNotBlank(s)){
			ss = reqBean.getParameter("s").split(",");	// 勾选的菜单权限
		}
		TblRole tblRole = new TblRole();
		RoleMngEntryService roleMngEntryService = RoleMngEntryService.getInstance();
		String msg = "";
		if(StringUtils.isBlank(roleId)){
			// 新增
			tblRole.setRoleId(SeqNoGenUtil.genRoleId());
			tblRole.setRoleName(roleName);
			tblRole.setStatus("1");
			tblRole.setIsLock("0");
			roleMngEntryService.saveRoleMngEntry(tblRole);
			msg += "新增岗位,id="+tblRole.getRoleId();
		}else{
			// 修改
			// 管理岗必须包含系统维护,系统配置的所有功能
//			if(null != roleId && roleId.equals("111")){
//				// 系统配置 1000,100001,100002,100003,100004
//				// 系统维护 1001,100101,100102,100103,100104,100105
//				String[] reg = "1000,100001,100002,100003,100004,1001,100101,100102,100103,100104,100105".split(",");
//				String roleList = s;
//				if(roleList.isEmpty()){
//					SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_WEB_0042);
//				}
//				int size = 0;
//				if(roleList.split(",").length >= reg.length){
//					size = roleList.split(",").length;
//				}else{
//					size = reg.length;
//				}
//				try {
//					for (int i = 0; i < size; i++) {
//						if(roleList.contains(reg[i])){
//							continue;
//						}else{
//							SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_WEB_0042);
//						}
//					}
//					
//				} catch (IndexOutOfBoundsException e) {
//					e.printStackTrace();
//				}
//			}
			tblRole = roleMngEntryService.checkId(roleId);
			roleMngEntryService.deleteRoleResInf(roleId);	// 删除老权限
			msg += "修改岗位,id="+tblRole.getRoleId();
		}
		List<TblResInfo> list = new ArrayList<TblResInfo>();
		TblResInfo tblResInfo = null;
		int size = ss.length;
		for (int i = 0; i < size; i++) {
			tblResInfo = new TblResInfo();
			tblResInfo.setId(ContextUtil.getUUID());
			tblResInfo.setFuncid(ss[i]);
			tblResInfo.setRoleId(String.valueOf(tblRole.getRoleId()));
			list.add(tblResInfo);
		}
		if(list.size() > 0){
			roleMngEntryService.saveResInfo(list);
			msg += ",岗位权限新增,role_id="+roleId==null?tblRole.getRoleId():roleId;
		}
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),msg});
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="有效/无效")
	public void setStatus(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("RoleMngEntry");
		String foo = reqBean.getParameter("foo");
		String roleId = reqBean.getParameter("roleId");
		TblRole tblRole = new TblRole();
		RoleMngEntryService roleMngEntryService = RoleMngEntryService.getInstance();
		tblRole = roleMngEntryService.checkId(roleId);
		tblRole.setStatus(foo);
		roleMngEntryService.updateRoleMngEntry(tblRole);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		String msg = "";
		if("1".equals(foo)){
			msg = "更改角色岗位为有效,id="+tblRole.getRoleId();
		}else{
			msg = "更改角色岗位为无效,id="+tblRole.getRoleId();
		}
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),msg});
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc = "设置岗位权限 查询岗位拥有的权限")
	public Map<String, Object> setRoleMng(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("RoleMngEntry");
		String roleId = reqBean.getParameter("roleId");
		RoleMngEntryService roleMngEntryService = RoleMngEntryService.getInstance();
		List<Object> list = roleMngEntryService.queryResInf(roleId);
		List<String> returnList = new ArrayList<String>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			TblResInfo tblResInfo = (TblResInfo) list.get(i);
			returnList.add(tblResInfo.getFuncid());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("funcs", JSONUtil.toJSON(returnList).replace("\"","").replace("[","").replace("]",""));
		return map;
	}

}
