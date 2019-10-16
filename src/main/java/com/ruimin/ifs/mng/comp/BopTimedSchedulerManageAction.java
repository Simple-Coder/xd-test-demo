package com.ruimin.ifs.mng.comp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.common.core.SnowApi;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.framework.quartz.TaskJobScheduler;
import com.ruimin.ifs.framework.quartz.bean.IfsCronJob;
import com.ruimin.ifs.framework.quartz.bean.JobConstant;
import com.ruimin.ifs.framework.quartz.util.CronJobUtil;
import com.ruimin.ifs.mng.process.bean.CronJobParamVo;
import com.ruimin.ifs.mng.process.bean.TblCronJobVO;
import com.ruimin.ifs.mng.process.service.BopTimedSchedulerManageService;

@SnowDoc(desc = "定时任务配置")
@ActionResource
public class BopTimedSchedulerManageAction extends SnowAction {

	@Action
	@SnowDoc(desc = "查询")
	public List<TblCronJobVO> queryAll(QueryParamBean queryBean) throws SnowException{
		return BopTimedSchedulerManageService.getInstance().queryList();
	}
	
	@Action
	@SnowDoc(desc = "查询任务参数")
	public List<CronJobParamVo> queryJobParamById(QueryParamBean queryBean) throws SnowException{
		List<CronJobParamVo> retList = new ArrayList<CronJobParamVo>();
		String cid = queryBean.getParameter("cid");
		DBDao dao = DBDaos.newInstance();
		IfsCronJob cronJob = dao.select(IfsCronJob.class, cid);
		if (cronJob==null) {
			return retList;
		}
		Map<String, String> jobParamToMap = CronJobUtil.jobParamToMap(cronJob.getProcessParam());
		for(Entry<String, String> entry:jobParamToMap.entrySet()) {
			String key = entry.getKey();
			if (!StringUtils.equals(key, JobConstant.TRANSACTION_FLAG)) {
				CronJobParamVo vo = new CronJobParamVo(key,entry.getValue());
				retList.add(vo);
			}
		}
		return retList;
	}
	
	
	@Action
	@SnowDoc(desc = "日志查询")
	public PageResult queryLogAll(QueryParamBean queryBean) throws SnowException{
		String excuteTimeStart = queryBean.getParameter("excuteTimeStart");
		String excuteTimeEnd = queryBean.getParameter("excuteTimeEnd");
		String excuteResult = queryBean.getParameter("excuteResult");
		return BopTimedSchedulerManageService.getInstance().queryListLog(excuteTimeStart, excuteTimeEnd, excuteResult, queryBean.getPage());
	}

	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="新增/修改")
	public void saveOrUpdate1(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("BopTimedSchedulerManage");
		UpdateRequestBean paramReqBean = updateMap.get("BopTimedSchedulerParam");
		Map<String, String> paramMap = new HashMap<String,String>();
		IfsCronJob ifsCronJob = new IfsCronJob();
		if (reqBean.hasNext()) {
			Map<String, String> next = reqBean.next();
			DataObjectUtils.map2bean(next, ifsCronJob);
			
			String transactionFlg = next.get("transactionFlg");
			if (StringUtils.equalsIgnoreCase(transactionFlg, "false")) {
				paramMap.put(JobConstant.TRANSACTION_FLAG, "false");
			}
		}
		if (StringUtils.isNotBlank(ifsCronJob.getStartTime())) {
			ifsCronJob.setStartTime(ifsCronJob.getStartTime().replace(":", ""));
		}
		if (StringUtils.isNotBlank(ifsCronJob.getEndTime())) {
			ifsCronJob.setEndTime(ifsCronJob.getEndTime().replace(":", ""));
		}
		while (paramReqBean.hasNext()) {
			Map<String, String> nextMap = paramReqBean.next();
			if (paramReqBean.getRecodeState()!=UpdateRequestBean.DELETE) {
				String paramKey = nextMap.get("paramKey");
				String paramValue = nextMap.get("paramValue");
				paramMap.put(paramKey, paramValue);
			}
		}
		StringBuilder builder = new StringBuilder();
		for(Entry<String, String> entry:paramMap.entrySet()) {
			builder.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
		}
		ifsCronJob.setProcessParam(builder.toString());
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		String msg = "";
		if(StringUtils.isBlank(ifsCronJob.getId())){
			// 新增
			ifsCronJob.setId(ContextUtil.getUUID());
			BopTimedSchedulerManageService.getInstance().saveIfsCronJob(ifsCronJob);
			msg = "定时任务新增:id=";
		}else{
			// 执行方式不是每月某日,需将每月某日字段重置
			if(!ifsCronJob.getRuntime().equals("97")){
				ifsCronJob.setDaysOfMonth(0);
			}
			// 修改
			BopTimedSchedulerManageService.getInstance().updateIfsCronJob(ifsCronJob);
			msg = "定时任务修改:id=";
		}
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),msg+ifsCronJob.getId()});
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="删除")
	public void delete(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean reqBean = updateMap.get("BopTimedSchedulerManage");
		String id = reqBean.getParameter("id");
		IfsCronJob ifsCronJob = new IfsCronJob();
		while (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), ifsCronJob);
		}
		BopTimedSchedulerManageService.getInstance().deleteIfsCronJobById(id);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"定时任务删除:id="+id});
	}
	
	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "停止JOB")
	public void stopJob(Map<String, UpdateRequestBean> updateMap)
			throws SnowException {
		UpdateRequestBean reqBean = updateMap.get("BopTimedSchedulerManage");
		String id = reqBean.getParameter("id");
		if (NumberUtils.isDigits(id)) {
			SnowApi.getInstance().execAction(TaskJobScheduler.class.getName(),
					"stopJob", new Object[] { Integer.parseInt(id) });
		}
	}

	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "启动JOB")
	public void startJob(Map<String, UpdateRequestBean> updateMap)
			throws SnowException {
		UpdateRequestBean reqBean = updateMap.get("BopTimedSchedulerManage");
		String id = reqBean.getParameter("id");
		if (NumberUtils.isDigits(id)) {
			SnowApi.getInstance().execAction(TaskJobScheduler.class.getName(),
					"startJob", new Object[] { Integer.parseInt(id) });
		}
	}
	
	
}
