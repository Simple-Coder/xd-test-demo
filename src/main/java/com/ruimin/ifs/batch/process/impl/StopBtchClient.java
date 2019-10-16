package com.ruimin.ifs.batch.process.impl;

import com.ruimin.ifs.batch.bean.msg.request.StopBtchRequestBean;
import com.ruimin.ifs.batch.bean.msg.response.StopBtchResponseBean;
import com.ruimin.ifs.batch.process.AbsBatchClientProc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.util.ContextUtil;
/**
 * 停止批量总控
 * @author wangpb
 *
 */
public class StopBtchClient extends AbsBatchClientProc<StopBtchRequestBean,StopBtchResponseBean>{
	
	public synchronized static StopBtchClient getInstance() throws SnowException {
		return ContextUtil.getSingleService(StopBtchClient.class);
	}
	@Override
	public String getReqUrl() {
		return "stop";
	}

	@Override
	public void preProcess(StopBtchRequestBean reqBean) throws SnowException {
	}

	@Override
	public StopBtchResponseBean setRetMsgToBean(String retMsg) throws SnowException {
		StopBtchResponseBean rspBean = new StopBtchResponseBean(retMsg);
		return rspBean;
	}

}
