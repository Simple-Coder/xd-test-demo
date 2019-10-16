package com.ruimin.ifs.batch.comp;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ruimin.ifs.batch.bean.vo.BatchInfo;
import com.ruimin.ifs.batch.bean.vo.BatchMonitorJob;
import com.ruimin.ifs.batch.bean.vo.BatchStepInfo;
import com.ruimin.ifs.batch.bean.vo.BatchStepSideTotBean;
import com.ruimin.ifs.batch.process.BtchClientService;
import com.ruimin.ifs.batch.process.util.BatchJobConstant;
import com.ruimin.ifs.batch.process.util.BatchJobConstant.BATCH_TYPE;
import com.ruimin.ifs.batch.process.util.BatchUtil;
import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.common.core.SnowApi;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.core.util.constant.SnowErrorCode;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.PageQueryResult;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.framework.utils.SysParamUtil;
import com.ruimin.ifs.po.TblSysInf;

@ActionResource
@SnowDoc(desc = "批量监控")
public class BatchMonitorAction extends SnowAction {
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc = "查询系统信息")
	public TblSysInf getIfsSysInf(QueryParamBean queryBean) throws SnowException {
		TblSysInf ifsSysInf = BatchUtil.getIfsSysInf();
		// 查询任务运行模式
		String runMode = SysParamUtil.getInstance().getString(BatchJobConstant.PARAM_BATCH_RUNMODE_KEY, "0");
		ifsSysInf.setMiscflgs(runMode);
		if (StringUtils.equals(runMode, BatchJobConstant.BATCH_RUNMODE_HAND)) {
			ifsSysInf.setSwitchDayTm("手工触发");
			ifsSysInf.setStartOnlineTm("手工触发");
		} else {
			// 日切时间
			String swtm = SysParamUtil.getInstance().getString(BATCH_TYPE.DAY_SWITCH.getParamKey(), "");
			if (StringUtils.isBlank(swtm)) {
				ifsSysInf.setSwitchDayTm("未设置");
			}else {
				String runTmShow = BatchUtil.getRunTmShow(ifsSysInf.getBhdate(), swtm);
				ifsSysInf.setSwitchDayTm(runTmShow);
			}
			// 联机时间
			String sotm = SysParamUtil.getInstance().getString(BATCH_TYPE.ONLINE.getParamKey(), "");
			if (StringUtils.isBlank(sotm)) {
				ifsSysInf.setStartOnlineTm("未设置");
			}else {
				ifsSysInf.setStartOnlineTm(BatchUtil.getRunTmShow(ifsSysInf.getBhdate(), sotm));
			}
		}
		return ifsSysInf;
	}

	@Action
	@SnowDoc(desc = "执行日切")
	public void switchDay(Map<String, UpdateRequestBean> reqMap) throws SnowException {
		BtchClientService.getInstance().switchDay();
		// 记录日志
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",
				new String[] { sessionUserInfo.getTlrno(), sessionUserInfo.getBrCode(), "执行日切" });
	}

	@Action
	@SnowDoc(desc = "执行联机")
	public void startOnline(Map<String, UpdateRequestBean> reqMap) throws SnowException {
		BtchClientService.getInstance().startOnline();
		// 记录日志
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",
				new String[] { sessionUserInfo.getTlrno(), sessionUserInfo.getBrCode(), "执行联机" });
	}

	@Action
	@SnowDoc(desc = "批量监控汇总查询")
	public PageQueryResult listBatchMonitorTot(QueryParamBean queryBean) throws SnowException {
		String bhDate = BatchUtil.getIfsSysInf().getBhdate();
		String statusCode = queryBean.getParameter("statuscode");
		List<BatchInfo> result = BtchClientService.getInstance().getBatchStepInfoListTot(bhDate, statusCode, null);
		int everypage = queryBean.getPage().getEveryPage();
		int nextpage = queryBean.getPage().getCurrentPage();
		int maxIndex = nextpage * everypage;
		if (maxIndex > result.size()) {
			maxIndex = result.size();
		}
		List<BatchInfo> resultList = result.subList((nextpage - 1) * everypage, maxIndex);
		PageQueryResult queryResult = new PageQueryResult();
		queryResult.setQueryResult(resultList);
		queryResult.setTotalCount(result.size());
		return queryResult;
	}

	@Action
	@SnowDoc(desc = "批量状态查询")
	public PageQueryResult listBatchMonitorStatus(QueryParamBean queryBean) throws SnowException {
		String bhDate = queryBean.getParameter("bhdate");
		if (StringUtils.isBlank(bhDate)) {
			bhDate = BatchUtil.getIfsSysInf().getBhdate();
		}
		List<Object> result = BtchClientService.getInstance().getBatchStatusList(bhDate);
		int everypage = queryBean.getPage().getEveryPage();
		int nextpage = queryBean.getPage().getCurrentPage();
		int maxIndex = nextpage * everypage;
		if (maxIndex > result.size()) {
			maxIndex = result.size();
		}
		List<Object> resultList = result.subList((nextpage - 1) * everypage, maxIndex);
		PageQueryResult queryResult = new PageQueryResult();
		queryResult.setQueryResult(resultList);
		queryResult.setTotalCount(result.size());
		return queryResult;
	}
	
	@Action
	@SnowDoc(desc = "批量监控查询")
	public PageQueryResult listBatchMonitor(QueryParamBean queryBean) throws SnowException {
		String bhDate= queryBean.getParameter("bhdate");
		String statusCode=queryBean.getParameter("statuscode");
		List<BatchStepInfo> result= BtchClientService.getInstance().getBatchStepInfoList(bhDate, statusCode,null);
		int everypage = queryBean.getPage().getEveryPage();
		int nextpage = queryBean.getPage().getCurrentPage();
		int maxIndex = nextpage * everypage;

		if (maxIndex > result.size()) {
			maxIndex = result.size();
		}
		List<BatchStepInfo> resultList = result.subList((nextpage - 1) * everypage, maxIndex);
		PageQueryResult queryResult = new PageQueryResult();
		queryResult.setQueryResult(resultList);
		queryResult.setTotalCount(result.size());
		return queryResult;
	}
	
	@Action
	@SnowDoc(desc = "批量任务监控")
	public PageResult listBatchJobInfo(QueryParamBean queryBean) throws SnowException {
		String bhDate= queryBean.getParameter("bhdate");
		if (StringUtils.isBlank(bhDate)) {
			SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_SYS_0005,"批量日期不能为空!");
		}
		DBDao dao = DBDaos.newInstance();
		RqlParam param = RqlParam.map();
		param.put("bhdate", bhDate);
		PageResult jobPageResult = dao.selectListIn("com.ruimin.ifs.batch.rql.batch.monitorJobInf", param, queryBean.getPage()); 
		List<?> jobList = jobPageResult.getQueryResult();
		for (int i = 0; i < jobList.size(); i++) {
			BatchMonitorJob mjob = (BatchMonitorJob) jobList.get(i);
			int stepCnt = dao.selectCount("com.ruimin.ifs.batch.rql.batch.monitorJobStepCnt",mjob.getJobno());
			mjob.setStepCnt(stepCnt);
		}
		return jobPageResult;
	}
	
	@Action
	@SnowDoc(desc="执行批量任务")
	public void startBatchJob(Map<String, UpdateRequestBean> reqMap) throws SnowException{
		UpdateRequestBean updateRequestBean = reqMap.get("MonitorBatchJobInf");
		String jobNoStr = updateRequestBean.getParameter("jobNo");
		if (StringUtils.isNotBlank(jobNoStr) && NumberUtils.isDigits(jobNoStr)) {
			BtchClientService.getInstance().startJob(NumberUtils.toInt(jobNoStr));
			//记录日志
			SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
			sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"启用批任务[jobno='"+jobNoStr+"']"});
		}else{
			SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_SYS_0005,"任务号为空,不能执行");
		}
	}
	
	@Action
	@SnowDoc(desc="停止批量总控")
	public void stopBtch(Map<String, UpdateRequestBean> reqMap) throws SnowException{
		BtchClientService.getInstance().stopBtch();
		//记录日志
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"停止批量总控"});
	}
	
	@Action
	@SnowDoc(desc = "批量监控查询")
	public PageQueryResult listBatchStepMonitor(QueryParamBean queryBean) throws SnowException {
		String bhDate= queryBean.getParameter("bhdate");
		String jobNo=queryBean.getParameter("jobno");
		List<BatchStepInfo> result= BtchClientService.getInstance().getBatchStepInfoList(bhDate, null,Integer.parseInt(jobNo));
		int everypage = queryBean.getPage().getEveryPage();
		int nextpage = queryBean.getPage().getCurrentPage();
		int maxIndex = nextpage * everypage;
		if (maxIndex > result.size()) {
			maxIndex = result.size();
		}
		int start = (nextpage - 1) * everypage;
		int end = maxIndex;
		if (end>result.size()) {
			end = result.size();
		}
		if (start>end) {
			start = maxIndex-queryBean.getPage().getEveryPage();
		}
		List<BatchStepInfo> resultList = result.subList(start, maxIndex);
		for (int i = 0; i < resultList.size(); i++) {
			BatchStepInfo info = resultList.get(i);
			if (StringUtils.equals(info.getSubFlag(), "1")) {//并行信息处理
				BatchStepSideTotBean totBean = BtchClientService.getInstance().queryStepSideTotal(info.getJobNo(), info.getStepNo(), info.getSubStepNo(), bhDate);
				if (totBean==null) {
					info.setStatusCode(BatchJobConstant.MERGEINFO_FLAG_UNPROCESSED);
				}else{
					if (totBean.getErrorCnt()>0) {
						info.setStatusCode(BatchJobConstant.MERGEINFO_FLAG_FAILED);
					}else if(totBean.getRunCnt()>0){
						info.setStatusCode(BatchJobConstant.MERGEINFO_FLAG_RUNING);
					}else if(totBean.getSuccCnt()>0 && totBean.getErrorCnt()==0 && totBean.getRunCnt()==0 && totBean.getNonCnt()==0){
						info.setStatusCode(BatchJobConstant.MERGEINFO_FLAG_FINISHED);
					}
					//设置开始及结束时间
					String[] stepSideStartAndEndTm = BtchClientService.getInstance().getStepSideStartAndEndTm(info.getJobNo(), info.getStepNo(), info.getSubStepNo(), bhDate);
					info.setStartTime(stepSideStartAndEndTm[0]);
					info.setEndTime(stepSideStartAndEndTm[1]);
					//组装运行信息
					info.setErrorMsg(totBean.getSideMsg());
				}
			}else{
				if (StringUtils.isBlank(info.getStatusCode())) {
					info.setStatusCode(BatchJobConstant.MERGEINFO_FLAG_UNPROCESSED);
				}
			}
		}
		PageQueryResult queryResult = new PageQueryResult();
		queryResult.setQueryResult(resultList);
		queryResult.setTotalCount(result.size());
		return queryResult;
	}
	
	@Action
	@SnowDoc(desc="单步执行批量")
	public void runSingleStepJob(Map<String, UpdateRequestBean> reqMap) throws SnowException{
		UpdateRequestBean requestBean = reqMap.get("MonitorBatchStepList");
		String jobNo = requestBean.getParameter("jobNo");
		String stepNo = requestBean.getParameter("stepNo");
		String subStepNo = requestBean.getParameter("subStepNo");
		BtchClientService.getInstance().startSingleJob(NumberUtils.toInt(jobNo), NumberUtils.toInt(stepNo), NumberUtils.toInt(subStepNo));
		//记录日志
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"单步执行批量id="+jobNo+"-"+stepNo+"-"+subStepNo});
	}
	
	@Action
	@SnowDoc(desc = "并行步骤详细日志查询")
	public PageResult listBatchStepMonitorLog(QueryParamBean queryBean) throws SnowException {
		String bhDate= queryBean.getParameter("bhdate");
		String jobNo=queryBean.getParameter("jobNo");
		String stepNo = queryBean.getParameter("stepNo");
		RqlParam param = RqlParam.map();
		param.put("jobNo", jobNo);
		param.put("stepNo", stepNo);
		param.put("bhdate", bhDate);
		return DBDaos.newInstance().selectList("com.ruimin.ifs.batch.rql.batch.monitorStepSideLog", param,queryBean.getPage());
	}
}
