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
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.core.util.constant.SnowErrorCode;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.core.bean.TreeNode;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.mng.process.service.FunctionService;
import com.ruimin.ifs.po.TblFunction;

@SnowDoc(desc = "权限管理")
@ActionResource
public class FunctionMngAction extends SnowAction {

	@Action
	@SnowDoc(desc = "查询")
	public List<TreeNode> queryAll(QueryParamBean queryBean) throws SnowException {
		List<TreeNode> list = new ArrayList<TreeNode>();
		
		TreeNode topNode = new TreeNode();
		TblFunction virFunc = new TblFunction();
		virFunc.setFuncid("-1");
		virFunc.setFuncname("系统功能菜单");
		virFunc.setIconCls("fa fa-list");
		virFunc.setLocation(2);
		virFunc.setIsdirectory(1);
		virFunc.setFuncType("0");
		virFunc.setShowseq(0);
		virFunc.setFuncDesc("虚拟菜单");
		topNode.setAttributes(virFunc);
		topNode.setIconCls(virFunc.getIconCls());
		topNode.setText(virFunc.getFuncname());
		topNode.setId(virFunc.getFuncid());
		topNode.setPid(null);
		topNode.setChecked(true);
		list.add(topNode);
		
		FunctionService service = FunctionService.getInstance();
		List<TblFunction> functions = service.listAll();
		for (TblFunction function : functions) {
			TreeNode node = new TreeNode();
			node.setAttributes(function);
			node.setIconCls(function.getIconCls());
			node.setText(function.getFuncname());
			node.setId(function.getFuncid());
			if (StringUtils.isBlank(function.getLastdirectory())) {
				node.setPid(virFunc.getFuncid());
			}else{
				node.setPid(function.getLastdirectory());
			}
			list.add(node);
		}
		return list;

	}

	@SnowDoc(desc = "保存")
	public Map<String, String> saveOrUpdate(Map<String, UpdateRequestBean> updateMap) throws SnowException {
		UpdateRequestBean reqBean = updateMap.get("FunctionMng");
		TblFunction tblFunction = new TblFunction();
		if (reqBean.hasNext()) {
			Map<String, String> mapdata = reqBean.next();
			DataObjectUtils.map2bean(mapdata, tblFunction);
			if (StringUtils.equals(tblFunction.getFuncid(),"-1")) {
				SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_SYS_0005,"不能操作虚拟菜单");
			}
			tblFunction.setIconCls(mapdata.get("_icon"));
			tblFunction.setFuncname(mapdata.get("_text"));
			FunctionService service = FunctionService.getInstance();
			SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
			if (StringUtils.isBlank(tblFunction.getFuncid())) {
				service.insert(tblFunction);
				sessionUserInfo.addBizLog("update.log", new String[] { sessionUserInfo.getTlrno(), sessionUserInfo.getBrCode(),
				        "菜单权限添加:id=" + tblFunction.getFuncid() + ", name=" + tblFunction.getFuncname() });
			} else {
				service.update(tblFunction);
				sessionUserInfo.addBizLog("update.log", new String[] { sessionUserInfo.getTlrno(), sessionUserInfo.getBrCode(),
				        "菜单权限修改:id=" + tblFunction.getFuncid() + ", name=" + tblFunction.getFuncname() });
			}
		}

		Map<String, String> result = new HashMap<String, String>();
		result.put("funcid", tblFunction.getFuncid());
		return result;
	}

	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "删除")
	public void delFunc(Map<String, UpdateRequestBean> updateMap) throws SnowException {
		UpdateRequestBean reqBean = updateMap.get("FunctionMng");
		if (reqBean.hasNext()) {
			Map<String, String> mapdata = reqBean.next();
			String funcid = mapdata.get("funcid");
			if (StringUtils.equals(funcid,"-1")) {
				SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_SYS_0005,"不能操作虚拟菜单");
			}
			String funcname = mapdata.get("funcname");

			FunctionService service = FunctionService.getInstance();
			service.delete(funcid);
			SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
			sessionUserInfo.addBizLog("update.log", new String[] { sessionUserInfo.getTlrno(), sessionUserInfo.getBrCode(),
			        "菜单权限删除:id=" + funcid + ", name=" + funcname });
		}

	}

}
