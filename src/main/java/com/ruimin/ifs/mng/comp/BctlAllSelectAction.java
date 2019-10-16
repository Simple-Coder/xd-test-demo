package com.ruimin.ifs.mng.comp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.framework.core.bean.TreeNode;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.framework.dataset.field.FieldBean;
import com.ruimin.ifs.mng.process.bean.LoginLogVO;
import com.ruimin.ifs.mng.process.service.OrgService;
import com.ruimin.ifs.mng.process.service.TlrLoginLogService;
import com.ruimin.ifs.po.TblBctl;
import com.ruimin.ifs.util.SnowConstant;

@SnowDoc(desc = "机构下拉查询")
@ActionResource
public class BctlAllSelectAction extends SnowAction{
	private static final String VIR_BRCODE = "0000";
	@Action
	@SnowDoc(desc = "机构下拉查询")
	public List<LoginLogVO> queryBctlAllSelect(QueryParamBean queryBean) throws SnowException{
		return TlrLoginLogService.getInstance().queryBctlAllSelect();
	}
	
	
	@Action
	@SnowDoc(desc = "机构查询")
	public List<TreeNode> queryAllOrg(QueryParamBean queryBean) throws SnowException {
		
		boolean bl = Boolean.parseBoolean(queryBean.getParameter("showvir","false"));
		List<TreeNode> list = new ArrayList<TreeNode>();
		OrgService service = OrgService.getInstance();
		if (bl) {
			//创建虚拟节点
			TreeNode vnode = new TreeNode();
			vnode.setIconCls("fa fa-bank");
			vnode.setText("机构树");
			vnode.setId(VIR_BRCODE);
			vnode.setState(TreeNode.EXPAND_STATUS_OPEN);
			list.add(vnode);
		}
		
		
		
		List<TblBctl> orgList = service.listAll();
		for (TblBctl org : orgList) {
			TreeNode node = new TreeNode();
			node.setAttributes(org);
			node.setIconCls("fa fa-bank");
			node.setText(org.getBrname());
			node.setId(org.getBrcode());
			if (StringUtils.isBlank(org.getBlnUpBrcode()) && bl) {
				node.setPid(VIR_BRCODE);
			}else{
				node.setPid(org.getBlnUpBrcode());
			}
			node.setState(TreeNode.EXPAND_STATUS_OPEN);
			list.add(node);
		}
		return list;
	}
	
	@Action
	@SnowDoc(desc="分页查询下级机构信息")
	public PageResult queryOrgByCode(QueryParamBean queryBean) throws SnowException {
		String upBrCode = queryBean.getParameter("upbrcode",VIR_BRCODE);
		if (upBrCode.equals(VIR_BRCODE)) {
			upBrCode = "";
		}
		
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("upBrcode", upBrCode);
		queryMap.put("brno", queryBean.getParameter("qbrno",""));
		String brname = queryBean.getParameter("qbrname","");
		if (StringUtils.isNotBlank(brname)) {
			brname = "%"+brname+"%";
		}
		queryMap.put("brname", brname);
		return OrgService.getInstance().listChildOrg(queryMap, queryBean.getPage());
	}
	
	/**
	 * 根据brcode获取机构名称method
	 * @param bean
	 * @param key
	 * @param request
	 * @return
	 */
	public static String getOrgName(FieldBean bean, String key,ServletRequest request) throws SnowException {
		if (StringUtils.isNotBlank(key)) {
			TblBctl org = OrgService.getInstance().queryOrgById(key);
			if (org!=null) {
				return org.getBrname();
			}
		}
		return key;
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="新增机构")
	public void addOrg(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("orgmanger");
		TblBctl ifsOrg = new TblBctl();
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), ifsOrg);
		}
		if (StringUtils.isBlank(ifsOrg.getBlnUpBrcode())) {
			SnowExceptionUtil.throwWarnException("WEB_E0039");
		}
		if (StringUtils.isBlank(ifsOrg.getBrclass())) {
			SnowExceptionUtil.throwWarnException("WEB_E0039","机构级别不能为空!");
		}
		//检查机构号是否重复
		int count = OrgService.getInstance().queryOrgByBrNo(ifsOrg.getBrno(),"");
		if (count>0) {
			SnowExceptionUtil.throwWarnException("WEB_E0037",ifsOrg.getBrno());
		}
		if (ifsOrg.getBrclass().equals(SnowConstant.BRCODE_CLASS_HEAD)) {
			//检查添加是否为总行
			count = OrgService.getInstance().queryOrgByBrClass();
			if (count>0) {
				SnowExceptionUtil.throwWarnException("WEB_E0038");
			}
		}
		OrgService.getInstance().addOrg(ifsOrg);
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="机构修改")
	public void updateOrg(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("orgmanger");
		TblBctl ifsOrg = new TblBctl();
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), ifsOrg);
		}
		if (StringUtils.isBlank(ifsOrg.getBrclass())) {
			SnowExceptionUtil.throwWarnException("WEB_E0039","机构级别不能为空!");
		}
		//不能修改总行的级别
		if(SnowConstant.BRCODE_CLASS_HEAD.equals(ifsOrg.getBrclass())//原级别为总行
				&& !SnowConstant.BRCODE_CLASS_HEAD.equals(ifsOrg.getBrclass())){//修改后级别非总行
			SnowExceptionUtil.throwWarnException("WEB_E0039","不能修改总行的级别");
		}
		if (StringUtils.isBlank(ifsOrg.getBlnUpBrcode())) {
			if(!SnowConstant.BRCODE_CLASS_HEAD.equals(ifsOrg.getBrclass())){
				SnowExceptionUtil.throwWarnException("WEB_E0039","上级机构不能为空!");
			}
		}
		//检查机构号是否重复
		int count = OrgService.getInstance().queryOrgByBrNo(ifsOrg.getBrno(),ifsOrg.getBrcode());
		if (count>0) {
			SnowExceptionUtil.throwWarnException("WEB_E0037",ifsOrg.getBrno());
		}
		OrgService.getInstance().updateOrg(ifsOrg);
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="设置机构有效性")
	public void updateOrgStatus(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("orgmanger");
		
		if (reqBean.hasNext()) {
			String status = reqBean.getParameter("setstatus");
			String brcode = reqBean.next().get("brcode");
			OrgService.getInstance().updateOrgStatus(brcode, status);
		}else{
			SnowExceptionUtil.throwWarnException("WEB_E0039","无法获取提交机构信息!");
		}
	}
}
