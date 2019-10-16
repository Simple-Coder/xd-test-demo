package com.ruimin.ifs.batch.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ruimin.ifs.batch.bean.msg.request.StartJobRequestBean;
import com.ruimin.ifs.batch.bean.msg.request.StartOnlineRequestBean;
import com.ruimin.ifs.batch.bean.msg.request.StartSingleJobRequestBean;
import com.ruimin.ifs.batch.bean.msg.request.StopBtchRequestBean;
import com.ruimin.ifs.batch.bean.msg.request.SwitchDayRequestBean;
import com.ruimin.ifs.batch.bean.msg.response.StartJobResponseBean;
import com.ruimin.ifs.batch.bean.msg.response.StartOnlineResponseBean;
import com.ruimin.ifs.batch.bean.msg.response.StartSingleJobResponseBean;
import com.ruimin.ifs.batch.bean.msg.response.StopBtchResponseBean;
import com.ruimin.ifs.batch.bean.msg.response.SwitchDayResponseBean;
import com.ruimin.ifs.batch.bean.po.IfsBatProcLog;
import com.ruimin.ifs.batch.bean.vo.BatchInfo;
import com.ruimin.ifs.batch.bean.vo.BatchStepInfo;
import com.ruimin.ifs.batch.bean.vo.BatchStepSideTotBean;
import com.ruimin.ifs.batch.bean.vo.JobInfVO;
import com.ruimin.ifs.batch.process.impl.StartJobClient;
import com.ruimin.ifs.batch.process.impl.StartOnlineClient;
import com.ruimin.ifs.batch.process.impl.StartSingleJobClient;
import com.ruimin.ifs.batch.process.impl.StopBtchClient;
import com.ruimin.ifs.batch.process.impl.SwitchDayClient;
import com.ruimin.ifs.batch.process.util.BatchJobConstant;
import com.ruimin.ifs.batch.process.util.BatchUtil;
import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.service.SnowService;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.framework.utils.SysParamUtil;
import com.ruimin.ifs.po.TblBhProcStep;

@Service
@SnowDoc(desc = "请求批量应用服务")
public class BtchClientService extends SnowService {
	public synchronized static BtchClientService getInstance() throws SnowException {
		return ContextUtil.getSingleService(BtchClientService.class);
	}

	/**
	 * 日切（未传入日期，采用下一交易日为批量日期）
	 * @return
	 * @throws SnowException
	 */
	public SwitchDayResponseBean switchDay() throws SnowException{
		SwitchDayRequestBean reqBean = new SwitchDayRequestBean();
		SwitchDayResponseBean rsp = SwitchDayClient.getInstance().execProcess(reqBean);
		if (rsp != null && rsp.isSuccess()) {
			return rsp;
		} else {
			SnowExceptionUtil.throwErrorException(BatchJobConstant.BATCH_CLIENT_ERROR_CODE, new String[] { rsp.getRspMsg() });
		}
		return null;
	}
	
	/**
	 * 联机
	 * @return
	 * @throws SnowException
	 */
	public StartOnlineResponseBean startOnline() throws SnowException{
		StartOnlineRequestBean reqBean = new StartOnlineRequestBean();
		StartOnlineResponseBean rsp = StartOnlineClient.getInstance().execProcess(reqBean);
		if (rsp != null && rsp.isSuccess()) {
			return rsp;
		} else {
			SnowExceptionUtil.throwErrorException(BatchJobConstant.BATCH_CLIENT_ERROR_CODE, new String[] { rsp.getRspMsg() });
		}
		return null;
	}
	
	/**
	 * 根据jobNo启动批量任务
	 * 
	 * @param jobNo
	 * @return
	 * @throws SnowException
	 */
	public StartJobResponseBean startJob(int jobNo) throws SnowException {
		StartJobRequestBean reqBean = new StartJobRequestBean(jobNo);
		StartJobResponseBean rsp = StartJobClient.getInstance().execProcess(reqBean);
		if (rsp != null && rsp.isSuccess()) {
			return rsp;
		} else {
			SnowExceptionUtil.throwErrorException(BatchJobConstant.BATCH_CLIENT_ERROR_CODE, new String[] { rsp.getRspMsg() });
		}
		return null;
	}

	/**
	 * 启动批量单步，需填写JobNo、StepNo、SubStepNo
	 * 
	 * @param jobNo
	 * @param StepNo
	 * @param SubStepNo
	 * @return
	 * @throws SnowException
	 */
	public StartSingleJobResponseBean startSingleJob(int jobNo, int stepNo, int subStepNo) throws SnowException {
		StartSingleJobRequestBean reqBean = new StartSingleJobRequestBean(jobNo, stepNo, subStepNo);
		StartSingleJobResponseBean rsp = StartSingleJobClient.getInstance().execProcess(reqBean);
		if (rsp != null && rsp.isSuccess()) {
			return rsp;
		} else {
			SnowExceptionUtil.throwErrorException(BatchJobConstant.BATCH_CLIENT_ERROR_CODE, new String[] { rsp.getRspMsg() });
		}
		return null;
	}

	/**
	 * 停止批量总控
	 * 
	 * @return
	 * @throws SnowException
	 */
	public StopBtchResponseBean stopBtch() throws SnowException {
		StopBtchRequestBean reqBean = new StopBtchRequestBean();
		StopBtchResponseBean rsp = StopBtchClient.getInstance().execProcess(reqBean);
		if (rsp != null && rsp.isSuccess()) {
			return rsp;
		} else {
			SnowExceptionUtil.throwErrorException(BatchJobConstant.BATCH_CLIENT_ERROR_CODE, new String[] { rsp.getRspMsg() });
		}
		return null;
	}

	/**
	 * 根据批量日期查询批量步骤
	 * @Title  getBatchStepInfoList  
	 * @Description TODO  
	 * @param bhDate
	 * @param statusCode
	 * @param jobNo
	 * @return
	 * @throws SnowException
	 */
	public List<BatchStepInfo> getBatchStepInfoList(String bhDate, String statusCode, Integer jobNo) throws SnowException {
		if (StringUtils.isBlank(bhDate)) {/** 批量日期为空 查询当前批量日期 */
			bhDate = BatchUtil.getIfsSysInf().getBhdate();
		}
		List<BatchStepInfo> result = new ArrayList<BatchStepInfo>();
		List<Object> stepInfoList = this.getTblBhProcStepListByBhDate(bhDate, jobNo);
		BatchInfo batchInfo = new BatchInfo();// 总批量信息
		batchInfo.setBhDate(bhDate);
		batchInfo.setStepList(result);
		for (int i = 0; i < stepInfoList.size(); i++) {
			TblBhProcStep step = (TblBhProcStep) stepInfoList.get(i);
			IfsBatProcLog stepStatus = this.findStatusByStep(step, bhDate);
			BatchStepInfo stepBean = new BatchStepInfo();
			stepBean.setId(step.getId());
			stepBean.setJobNo(step.getJobno());
			stepBean.setStepNo(step.getStep());
			stepBean.setSubStepNo(step.getSubStep());
			stepBean.setSuspendCode(step.getSuspend());
			stepBean.setSubStepName(step.getDesc0());
			if (stepStatus != null) {
				stepBean.setStatusCode(stepStatus.getStatus());
				stepBean.setStartTime(stepStatus.getStartTime());
				stepBean.setEndTime(stepStatus.getEndTime());
				stepBean.setErrorMsg(stepStatus.getErrMsg());
			}
			stepBean.setBatchInfo(batchInfo);
			stepBean.setSubFlag(step.getSubFlag());
			if (statusCode == null || statusCode.equals(stepBean.getStatusCode())) {
				result.add(stepBean);
			}
		}
		return result;
	}

	public IfsBatProcLog findStatusByStep(TblBhProcStep step, String bhDate) throws SnowException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bhdate", bhDate);
		paramMap.put("jobno", step.getJobno());
		paramMap.put("step", step.getStep());
		paramMap.put("substep", step.getSubStep());
		Object obj = DBDaos.newInstance().selectOne("com.ruimin.ifs.batch.rql.batch.queryIfsProcStepLog", paramMap);
		if (obj != null) {
			return (IfsBatProcLog) obj;
		}
		return null;
	}

	/**
	 * 获取当前批量日期待运行步骤
	 * @Title  getTblBhProcStepList  
	 * @Description TODO  
	 * @param bhDate 批量日期
	 * @param jobNo 
	 * @return
	 * @throws SnowException
	 */
	public List<Object> getTblBhProcStepListByBhDate(String bhDate, Integer jobNo) throws SnowException {
		DBDao dao = DBDaos.newInstance();
		RqlParam param = RqlParam.map();
		param.set("jobNo", jobNo==null?"":jobNo);
		// runtime参数
		param.set("runtimes", BatchUtil.getRunTimesByBhDate(bhDate));
		return dao.selectList("com.ruimin.ifs.batch.rql.batch.queryIfsProcStepInfo",param);
	}

	/**
	 * 批量并行信息统计
	 * 
	 * @param jobNo
	 * @param stepNo
	 * @param subStepNo
	 * @param bhDate
	 * @return
	 * @throws SnowException
	 */
	@SuppressWarnings("rawtypes")
	public BatchStepSideTotBean queryStepSideTotal(int jobNo, int stepNo, int subStepNo, String bhDate) throws SnowException {
		RqlParam param = RqlParam.map();
		param.put("jobNo", jobNo);
		param.put("stepNo", stepNo);
		param.put("bhdate", bhDate);
		List totalList = DBDaos.newInstance().selectList("com.ruimin.ifs.batch.rql.batch.monitorJobStepSideTotal", param);
		if (totalList.size() > 0) {
			BatchStepSideTotBean bean = new BatchStepSideTotBean();
			int totalCnt = 0;
			for (int i = 0; i < totalList.size(); i++) {
				Map inMap = (Map) totalList.get(i);
				Object staObj = inMap.get("status");
				int cnt = inMap.get("cnt") == null ? 0 : Integer.parseInt(inMap.get("cnt").toString());
				totalCnt += cnt;
				if (staObj != null) {
					String sta = staObj.toString();
					if (sta.equalsIgnoreCase(BatchJobConstant.THREAD_STATUS_RUNNING)) {
						bean.setRunCnt(cnt);
					} else if (sta.equalsIgnoreCase(BatchJobConstant.THREAD_STATUS_ERROR)) {
						bean.setErrorCnt(cnt);
					} else if (sta.equalsIgnoreCase(BatchJobConstant.THREAD_STATUS_FINISHED)) {
						bean.setSuccCnt(cnt);
					}else {
						bean.setNonCnt(cnt);
					}
				} else {
					bean.setNonCnt(cnt);
				}
			}
			bean.setTotalCnt(totalCnt);
			return bean;
		}
		return null;
	}

	/**
	 * 并行运行的开始及结束时间
	 * 
	 * @param jobNo
	 * @param stepNo
	 * @param subStepNo
	 * @param bhDate
	 * @return
	 * @throws SnowException
	 */
	@SuppressWarnings("rawtypes")
	public String[] getStepSideStartAndEndTm(int jobNo, int stepNo, int subStepNo, String bhDate) throws SnowException {
		String[] startEndTm = new String[2];
		RqlParam param = RqlParam.map();
		param.put("jobNo", jobNo);
		param.put("stepNo", stepNo);
		param.put("bhdate", bhDate);
		List<Object> list = DBDaos.newInstance().selectList("com.ruimin.ifs.batch.rql.batch.monitorJobStepStartEndTm", param);
		if (list != null && list.size()>0) {
			Map map = (Map) list.get(0);
			startEndTm[0]= map.get("startTime")==null?"":map.get("startTime").toString();
			startEndTm[1] = map.get("endTime")==null?"":map.get("endTime").toString();
		}
		return startEndTm;
	}

	/**
	 * 根据批量日期统计批量步骤运行汇总信息
	 * @Title  getBatchStepInfoListTot  
	 * @Description TODO  
	 * @param bhDate
	 * @param statusCode
	 * @param jobNo
	 * @return
	 * @throws SnowException
	 */
	public List<BatchInfo> getBatchStepInfoListTot(String bhDate, String statusCode, Integer jobNo) throws SnowException {
		if (StringUtils.isBlank(bhDate)) {/** 批量日期为空 查询当前批量日期 */
			bhDate = BatchUtil.getIfsSysInf().getBhdate();
		}
		List<BatchStepInfo> result = new ArrayList<BatchStepInfo>();
		List<BatchInfo> tot = new ArrayList<BatchInfo>();
		List<Object> stepInfoList = this.getTblBhProcStepListByBhDate(bhDate, jobNo);
		BatchInfo batchInfo = new BatchInfo();// 总批量信息
		batchInfo.setBhDate(bhDate);
		batchInfo.setStepList(result);
		for (int i = 0; i < stepInfoList.size(); i++) {
			TblBhProcStep step = (TblBhProcStep) stepInfoList.get(i);
			IfsBatProcLog stepStatus = this.findStatusByStep(step, bhDate);
			BatchStepInfo stepBean = new BatchStepInfo();
			stepBean.setId(step.getId());
			stepBean.setJobNo(step.getJobno());
			stepBean.setStepNo(step.getStep());
			stepBean.setSubStepNo(step.getSubStep());
			stepBean.setSuspendCode(step.getSuspend());
			stepBean.setSubStepName(step.getDesc0());
			if (stepStatus != null) {
				stepBean.setStatusCode(stepStatus.getStatus());
				stepBean.setStartTime(stepStatus.getStartTime());
				stepBean.setEndTime(stepStatus.getEndTime());
			}
			stepBean.setBatchInfo(batchInfo);
			if (statusCode == null || statusCode.equals(stepBean.getStatusCode())) {
				result.add(stepBean);
			}
		}
		tot.add(batchInfo);
		return tot;
	}

	public List<Object> getBatchStatusList(String bhDate) throws SnowException {
		if (StringUtils.isBlank(bhDate)) {/** 批量日期为空 查询当前批量日期 */
			bhDate = BatchUtil.getIfsSysInf().getBhdate();
		}
		DBDao dao = DBDaos.newInstance();
		List<Object> jobInfList = dao.selectList("com.ruimin.ifs.batch.rql.batch.queryBatchStatus", bhDate);
		//获取计划执行时间
		Map<String, String> map = SysParamUtil.getInstance().get(BatchJobConstant.BATCH_PARAM_ID);
		Map<Integer,String> tmpMap = new HashMap<>();
		for(Entry<String, String> entry:map.entrySet()) {
			String key = entry.getKey();
			if (StringUtils.isNotBlank(key)) {
				String jonNo = BatchUtil.getJobParamKey(key);
				if (NumberUtils.isDigits(jonNo)) {
					tmpMap.put(NumberUtils.toInt(jonNo), entry.getValue());
				}
			}
		}
		
		for (Object object : jobInfList) {
			JobInfVO vo = (JobInfVO) object;
			int jobno = vo.getJobno();
			String tm = tmpMap.get(jobno);
			String bhdt = vo.getBhdate();
			if(StringUtils.isBlank(bhdt)) {
				bhdt = bhDate;
			}
			vo.setPlanExecTm(StringUtils.defaultString(BatchUtil.getRunTmShow(bhDate, tm), "-"));
		}
		return jobInfList;
	}
}
