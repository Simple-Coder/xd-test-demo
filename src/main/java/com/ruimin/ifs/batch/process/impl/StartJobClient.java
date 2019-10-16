package com.ruimin.ifs.batch.process.impl;

import com.ruimin.ifs.batch.bean.msg.request.StartJobRequestBean;
import com.ruimin.ifs.batch.bean.msg.response.StartJobResponseBean;
import com.ruimin.ifs.batch.process.AbsBatchClientProc;
import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.util.ContextUtil;
/**
 * 根据jobNo启动批量
 * @author wangpb
 *
 */
@Service
public class StartJobClient extends AbsBatchClientProc<StartJobRequestBean, StartJobResponseBean>{

	public synchronized static StartJobClient getInstance() throws SnowException {
		return ContextUtil.getSingleService(StartJobClient.class);
	}
	@Override
	public String getReqUrl() {
		return "startJob";
	}

	@Override
	public void preProcess(StartJobRequestBean reqBean) throws SnowException {
	}

	@Override
	public StartJobResponseBean setRetMsgToBean(String retMsg) throws SnowException {
		StartJobResponseBean rspBean = new StartJobResponseBean(retMsg);
		return rspBean;
	}

}
