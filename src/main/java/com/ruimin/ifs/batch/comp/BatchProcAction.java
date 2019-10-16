package com.ruimin.ifs.batch.comp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.ruimin.ifs.batch.bean.po.IfsBatJobInf;
import com.ruimin.ifs.batch.bean.vo.BatchJobSelectVo;
import com.ruimin.ifs.batch.bean.vo.BatchRunTimeVo;
import com.ruimin.ifs.batch.process.util.BatchJobConstant;
import com.ruimin.ifs.batch.process.util.BatchJobConstant.BATCH_TYPE;
import com.ruimin.ifs.batch.process.util.BatchUtil;
import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.core.util.DateUtil;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.core.util.constant.SnowErrorCode;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.framework.dataset.field.FieldBean;
import com.ruimin.ifs.mng.process.service.SysParamsSecService;
import com.ruimin.ifs.po.TblBhProcStep;
import com.ruimin.ifs.po.TblSysInf;
import com.ruimin.ifs.po.TblSysParam;
import com.ruimin.ifs.rql.cnd.Cnd;
import com.ruimin.ifs.util.SeqNoGenUtil;

@SnowDoc(desc = "批量管理")
@ActionResource
public class BatchProcAction extends SnowAction {
	@Action
	@SnowDoc(desc = "查询所有批量任务信息")
	public PageResult queryBatchJobInf(QueryParamBean queryBean) throws SnowException {
		DBDao dao = DBDaos.newInstance();
		return dao.selectList("com.ruimin.ifs.batch.rql.batch.queryJobInfByPage", queryBean.getPage());
	}
	
	@Action
	@SnowDoc(desc = "批量任务拾取")
	public List<BatchJobSelectVo> selectBatchJob(QueryParamBean queryBean) throws SnowException {
		List<BatchJobSelectVo> list = new ArrayList<BatchJobSelectVo>();
		BatchJobSelectVo sdVo = new BatchJobSelectVo(BATCH_TYPE.DAY_SWITCH.type,BATCH_TYPE.DAY_SWITCH.name);
		list.add(sdVo);
		BatchJobSelectVo soVo = new BatchJobSelectVo(BATCH_TYPE.ONLINE.type,BATCH_TYPE.ONLINE.name);
		list.add(soVo);
		DBDao dao = DBDaos.newInstance();
		List<Object> jobList = dao.selectList("com.ruimin.ifs.batch.rql.batch.queryJobInfByPage");
		for (Object object : jobList) {
			IfsBatJobInf jobInf = (IfsBatJobInf) object;
			list.add(new BatchJobSelectVo(jobInf.getJobno().toString(), jobInf.getMisc()));
		}
		return list;
	}
	
	
	/**
	 * 获取批量任务名称
	 * @param bean
	 * @param key
	 * @param request
	 * @return
	 * @throws SnowException
	 */
	public static String getJobName(FieldBean bean, String key,ServletRequest request) throws SnowException {
		if(StringUtils.isNotBlank(key)){
			if (StringUtils.equalsIgnoreCase(BATCH_TYPE.DAY_SWITCH.type, key)) {
				return BATCH_TYPE.DAY_SWITCH.name;
			}
			if (StringUtils.equalsIgnoreCase(BATCH_TYPE.ONLINE.type, key)) {
				return BATCH_TYPE.ONLINE.name;
			}
			DBDao dao = DBDaos.newInstance();
			IfsBatJobInf jobInf = dao.select(IfsBatJobInf.class, NumberUtils.toInt(key));
			return jobInf.getMisc();
		}
		return key;
	}

	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "保存批量任务")
	public void saveOrUpdateBatchJob(Map<String, UpdateRequestBean> reqMap) throws SnowException {
		UpdateRequestBean requestBean = reqMap.get("IfsBatJobInf");
		if (requestBean.hasNext()) {
			Map<String, String> valueMap = requestBean.next();
			String flg = valueMap.get("flg");
			DBDao dao = DBDaos.newInstance();
			IfsBatJobInf jobInf = new IfsBatJobInf();
			DataObjectUtils.map2bean(valueMap, jobInf);
			jobInf.setTimestamps(DateUtil.get14Date());
			if (jobInf.getPreJobno() == null) {
				jobInf.setPreJobno(0);
			}
			if (StringUtils.equalsIgnoreCase(flg, "add")) {// 新增
				IfsBatJobInf tmpJobInf = dao.select(IfsBatJobInf.class, jobInf.getJobno());
				if (tmpJobInf != null) {
					SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_SYS_0005,
							"任务编号[" + jobInf.getJobno() + "]不能重复!");
				}
				dao.insert(jobInf);
			} else {
				dao.update(jobInf);
			}
		}
	}

	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "删除批量任务")
	public void deleteBatchJob(Map<String, UpdateRequestBean> reqMap) throws SnowException {
		UpdateRequestBean requestBean = reqMap.get("IfsBatJobInf");
		int jobno = NumberUtils.toInt(requestBean.getParameter("jobno"));
		DBDao dao = DBDaos.newInstance();
		dao.delete(IfsBatJobInf.class, jobno);
		dao.delete(TblBhProcStep.class, Cnd.where("jobno", "=", jobno));
	}

	@Action
	@SnowDoc(desc = "查询所有批量设置信息")
	public PageResult listAllBhProcStep(QueryParamBean queryBean) throws SnowException {
		DBDao dao = DBDaos.newInstance();
		String jobno = queryBean.getParameter("jobno");
		RqlParam param = RqlParam.map();
		param.set("jobno", jobno);
		return dao.selectList("com.ruimin.ifs.batch.rql.batch.queryBhProcStep", param, queryBean.getPage());
	}


	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "新增或修改批量设置")
	public void saveOrUpdateProcStep(Map<String, UpdateRequestBean> reqMap) throws SnowException {
		UpdateRequestBean requestBean = reqMap.get("BhProcStep");
		if (requestBean.hasNext()) {
			DBDao dao = DBDaos.newInstance();
			Map<String, String> valueMap = requestBean.next();
			String id = valueMap.get("id");
			TblBhProcStep proc = new TblBhProcStep();
			DataObjectUtils.map2bean(valueMap, proc);
			SessionUserInfo userInf = SessionUserInfo.getSessionUserInfo();
			proc.setProcessTlrno(userInf.getTlrno());
			proc.setTimestamps(DateUtil.get14Date());
			if (StringUtils.equals(proc.getRuntime(), "00")) {// 指定每月某日
				String rd = valueMap.get("runtimeDay");
				if (StringUtils.isBlank(rd) || !NumberUtils.isDigits(rd) || NumberUtils.toInt(rd) < 1
						|| NumberUtils.toInt(rd) > 28) {
					SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_SYS_0005, "指定每月某日不合法!");
				}
				proc.setRuntime(rd);
			}
			proc.setReportFlag("0");// 写入默认
			if (StringUtils.isNotBlank(id) && Integer.parseInt(id) > 0) {// 编辑
				dao.update(proc);
			} else {// 新增
				proc.setId(SeqNoGenUtil.genBhProcStepId());
				dao.insert(proc);
			}
		}
	}

	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "删除批量设置")
	public void delteProcStep(Map<String, UpdateRequestBean> reqMap) throws SnowException {
		UpdateRequestBean requestBean = reqMap.get("BhProcStep");
		if (requestBean.hasNext()) {
			String id = requestBean.next().get("id");
			DBDao dao = DBDaos.newInstance();
			dao.delete(TblBhProcStep.class, Integer.parseInt(id));
		}
	}

	@Action
	@SnowDoc(desc = "查询系统信息")
	public TblSysInf getRunSysInf(QueryParamBean queryBean) throws SnowException {
		TblSysInf ifsSysInf = BatchUtil.getIfsSysInf();
		return ifsSysInf;
	}

	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "保存系统信息")
	public void updateSystemInf(Map<String, UpdateRequestBean> reqMap) throws SnowException {
		UpdateRequestBean requestBean = reqMap.get("BatchRunSysInf");
		if (requestBean.hasNext()) {
			DBDao dao = DBDaos.newInstance();
			Map<String, String> valueMap = requestBean.next();
			TblSysInf inf = new TblSysInf();
			DataObjectUtils.map2bean(valueMap, inf);
			dao.update(inf);
		}
	}

	@Action
	@SnowDoc(desc = "查询批量运行方式")
	public TblSysParam getBatchRunModeParam(QueryParamBean queryBean) throws SnowException {
		DBDao dao = DBDaos.newInstance();
		TblSysParam param = dao.select(TblSysParam.class, BatchJobConstant.BATCH_PARAM_ID,
				BatchJobConstant.BATCH_RUNMODE_MAGIC_ID);
		if (param == null) {
			SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_SYS_0005, "批量相关信息为设置,请执行初始化!");
		}
		return param;
	}

	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "批量运行方式")
	public void saveOrUpdateBatchRunMode(Map<String, UpdateRequestBean> updateMap) throws SnowException {
		UpdateRequestBean reqBean = updateMap.get("BatchRunMode");
		TblSysParam tlp = new TblSysParam();
		SysParamsSecService sps = ContextUtil.getSingleService(SysParamsSecService.class);
		if (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), tlp);
		}
		sps.updateSysParamsSec(tlp);
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		sessionUserInfo.addBizLog("update.log", new String[] { sessionUserInfo.getTlrno(), sessionUserInfo.getBrCode(),
				"系统参数修改:将参数" + tlp.getParamId() + " | " + tlp.getMagicId() + "修改为:{" + tlp.getParamValueTx() + "}" });
	}

	
	
	
	@Action
	@SnowDoc(desc = "查询批量运行时间")
	public PageResult getBatchRunTimeByPage(QueryParamBean queryBean) throws SnowException {
		DBDao dao = DBDaos.newInstance();
		RqlParam param = RqlParam.map();
		param.set("paramId", BatchJobConstant.BATCH_PARAM_ID);
		param.set("magicId", BatchJobConstant.BATCH_RUNMODE_MAGIC_ID);
		PageResult pageResult = dao.selectList("com.ruimin.ifs.batch.rql.batch.queryBatchRunTimeParam", param,
				queryBean.getPage());
		List<?> queryResultList = pageResult.getQueryResult();
		for (Object object : queryResultList) {
			BatchRunTimeVo brtvo = (BatchRunTimeVo) object;
			brtvo.setJobParamKey(BatchUtil.getJobParamKey(brtvo.getMagicId()));
			int[] batchRunHourAndMinute = BatchUtil.getBatchRunHourAndMinute(brtvo.getParamValueTx());
			brtvo.setHour(batchRunHourAndMinute[0]);
			brtvo.setMinute(batchRunHourAndMinute[1]);
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, brtvo.getHour());
			cal.set(Calendar.MINUTE, brtvo.getMinute());
			
			brtvo.setRunTime(DateFormatUtils.format(cal, "HH 点 mm 分"));
		}
		return pageResult;
	}

	
	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "批量运行时间保存")
	public void saveOrUpdateBatchRunTime(Map<String, UpdateRequestBean> updateMap) throws SnowException {
		UpdateRequestBean reqBean = updateMap.get("BatchRunTimeMng");
		BatchRunTimeVo runTimeVo = new BatchRunTimeVo();
		DBDao dao = DBDaos.newInstance();
		if (reqBean.hasNext()) {
			DataObjectUtils.map2bean(reqBean.next(), runTimeVo);
			int hour = runTimeVo.getHour();
			if (hour<0 || hour>23) {
				SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_SYS_0005,"运行时间小时介于[0-23]之间!");
			}
			int minute = runTimeVo.getMinute();
			if (minute<0 || minute>59) {
				SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_SYS_0005,"运行时间分钟介于[0-59]之间!");
			}
			
			if (StringUtils.isNotBlank(runTimeVo.getParamId()) && StringUtils.isNotBlank(runTimeVo.getMagicId())) {
				TblSysParam tlp = new TblSysParam();
				tlp.setParamId(runTimeVo.getParamId());
				tlp.setMagicId(runTimeVo.getMagicId());
				tlp.setParamValueTx(BatchUtil.getBatchRunTime(hour, minute));
				tlp.setDesc0(runTimeVo.getDesc0());
				dao.update(tlp);
			}else {
				TblSysParam tlp = new TblSysParam();
				tlp.setParamId(BatchJobConstant.BATCH_PARAM_ID);
				tlp.setMagicId(BatchUtil.getBatchMagicId(runTimeVo.getJobParamKey()));
				tlp.setParamValueTx(BatchUtil.getBatchRunTime(hour, minute));
				tlp.setDesc0(runTimeVo.getDesc0());
				dao.insert(tlp);
			}
		}
	}
	
	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc = "删除批量运行时间")
	public void deleteBatchRunTime(Map<String, UpdateRequestBean> reqMap) throws SnowException {
		UpdateRequestBean requestBean = reqMap.get("BatchRunTimeMng");
		BatchRunTimeVo runTimeVo = new BatchRunTimeVo();
		DBDao dao = DBDaos.newInstance();
		if (requestBean.hasNext()) {
			DataObjectUtils.map2bean(requestBean.next(), runTimeVo);
			dao.delete(TblSysParam.class, runTimeVo.getParamId(),runTimeVo.getMagicId());
		}
	}
}
