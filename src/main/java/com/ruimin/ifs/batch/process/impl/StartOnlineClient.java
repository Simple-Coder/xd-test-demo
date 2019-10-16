package com.ruimin.ifs.batch.process.impl;

import com.ruimin.ifs.batch.bean.msg.request.StartOnlineRequestBean;
import com.ruimin.ifs.batch.bean.msg.response.StartOnlineResponseBean;
import com.ruimin.ifs.batch.process.AbsBatchClientProc;
import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.util.ContextUtil;
/**
 * 启动联机
 * @author ningpeng
 *
 */
@Service
public class StartOnlineClient  extends AbsBatchClientProc<StartOnlineRequestBean, StartOnlineResponseBean>{
	public synchronized static StartOnlineClient getInstance() throws SnowException {
		return ContextUtil.getSingleService(StartOnlineClient.class);
	}
	@Override
	public String getReqUrl() {
		return "startOnline";
	}

	@Override
	public void preProcess(StartOnlineRequestBean reqBean) throws SnowException {
	}

	@Override
	public StartOnlineResponseBean setRetMsgToBean(String retMsg) throws SnowException {
		StartOnlineResponseBean rspBean = new StartOnlineResponseBean(retMsg);
		return rspBean;
	}

}
