package com.ruimin.ifs.mng.comp;

import java.util.Map;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.mng.process.service.SysParamsSecService;
import com.ruimin.ifs.po.TblSysParam;

@SnowDoc(desc = "系统参数设置")
@ActionResource
public class SysParamsSecAction extends SnowAction {

	@Action
	@SnowDoc(desc = "查询")
	public PageResult queryAll(QueryParamBean queryBean) throws SnowException{
		String queryParamId = queryBean.getParameter("queryParamId");
		String queryOprcode1 = queryBean.getParameter("queryOprcode1");
		return SysParamsSecService.getInstance().queryList(queryParamId, queryOprcode1, queryBean.getPage());
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="系统参数修改")
	public void saveOrUpdate(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("SysParamsSec");
		TblSysParam tlp = new TblSysParam();
		SysParamsSecService sps = ContextUtil.getSingleService(SysParamsSecService.class);
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), tlp);
		}
		sps.updateSysParamsSec(tlp);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"系统参数修改:将参数"+tlp.getParamId()+" | "+tlp.getMagicId()+"修改为:{"+tlp.getParamValueTx()+"}"});
	}

}
