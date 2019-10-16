package com.ruimin.ifs.mng.comp;

import java.util.Map;

import org.slf4j.Logger;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.log.SnowLog;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.mng.process.service.DataDicEntryService;
import com.ruimin.ifs.po.TblDataDic;

//ActionResource注解表示该类是个组件资源包
@SnowDoc(desc = "数据字典配置")
@ActionResource
public class DataDicEntryAction extends SnowAction {// 继承SnowAction

	private final Logger logger = SnowLog.getServerLog();

	/**
	 * 根据请求条件查询数据字典信息
	 * 
	 * @param queryBean
	 *            QueryParamBean查询类交易的请求参数
	 * @return 查询结果，返回类型可以为List/PageResult（包含分页信息）/JavaBean对象
	 * @throws SnowException
	 */
	// Action注解表示一个组件，查询类不需要事务
	@Action
	@SnowDoc(desc = "查询")
	public PageResult queryAll(QueryParamBean queryBean) throws SnowException {
		logger.debug("*****************debug**************");
		// 获取查询参数，分页信息已经封装在QueryParamBean对象中了
		String qDataTypeNo = queryBean.getParameter("qDataTypeNo");
		// 还可以使用 getParameter(String key,String defaultValue)来设置空值的默认值
		String qDataTypeName = queryBean.getParameter("qDataTypeName");
		String qDataNo = queryBean.getParameter("qDataNo");
		String qDataName = queryBean.getParameter("qDataName");
		String sort = queryBean.getParameter("sort");
		String order = queryBean.getParameter("order");
		// 调用服务层
		return DataDicEntryService.getInstance().queryList(qDataTypeNo, qDataNo, qDataName, qDataTypeName, queryBean.getPage());
	}

	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "数据字典修改")
	public void saveOrUpdate(Map<String, UpdateRequestBean> updateMap) throws SnowException {
		UpdateRequestBean reqBean = updateMap.get("DataDicEntry");
		TblDataDic tblDataDic = new TblDataDic();
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), tblDataDic);
		}
		DataDicEntryService.getInstance().updateDataDicEntry(tblDataDic);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",
		        new String[] { sessionUserInfo.getTlrno(), sessionUserInfo.getBrCode(), "数据字典修改:id=" + tblDataDic.getId() });
	}

	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "数据字典新增")
	public void saveOrUpdate1(Map<String, UpdateRequestBean> updateMap) throws SnowException {
		UpdateRequestBean reqBean = updateMap.get("DataDicEntry");
		TblDataDic tblDataDic = new TblDataDic();
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), tblDataDic);
		}
		tblDataDic.setId(ContextUtil.getUUID());
		DataDicEntryService.getInstance().saveDataDicEntry(tblDataDic);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",
		        new String[] { sessionUserInfo.getTlrno(), sessionUserInfo.getBrCode(), "数据字典新增:id=" + tblDataDic.getId() });
	}

	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "数据字典删除")
	public void delete(Map<String, UpdateRequestBean> updateMap) throws SnowException {
		UpdateRequestBean reqBean = updateMap.get("DataDicEntry");
		TblDataDic tblDataDic = new TblDataDic();
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), tblDataDic);
		}
		DataDicEntryService.getInstance().deletDataDicEntry(tblDataDic);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",
		        new String[] { sessionUserInfo.getTlrno(), sessionUserInfo.getBrCode(), "数据字典删除:id=" + tblDataDic.getId() });
	}

}
