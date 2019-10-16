package com.ruimin.ifs.batch.process.impl;

import com.ruimin.ifs.batch.bean.msg.request.StartSingleJobRequestBean;
import com.ruimin.ifs.batch.bean.msg.response.StartSingleJobResponseBean;
import com.ruimin.ifs.batch.process.AbsBatchClientProc;
import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.util.ContextUtil;
/**
 * 启动批量单步，需填写JobNo、StepNo、SubStepNo
 * @author wangpb
 *
 */
@Service
public class StartSingleJobClient extends AbsBatchClientProc<StartSingleJobRequestBean, StartSingleJobResponseBean>{

	public synchronized static StartSingleJobClient getInstance() throws SnowException {
		return ContextUtil.getSingleService(StartSingleJobClient.class);
	}
	@Override
	public String getReqUrl() {
		return "startSingleJob";
	}
	@Override
	public void preProcess(StartSingleJobRequestBean reqBean) throws SnowException {
	}
	@Override
	public StartSingleJobResponseBean setRetMsgToBean(String retMsg) throws SnowException {
		StartSingleJobResponseBean rspBean = new StartSingleJobResponseBean(retMsg);
		return rspBean;
	}
	

}
