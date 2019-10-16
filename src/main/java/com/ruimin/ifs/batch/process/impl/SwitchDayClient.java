package com.ruimin.ifs.batch.process.impl;

import com.ruimin.ifs.batch.bean.msg.request.SwitchDayRequestBean;
import com.ruimin.ifs.batch.bean.msg.response.SwitchDayResponseBean;
import com.ruimin.ifs.batch.process.AbsBatchClientProc;
import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.util.ContextUtil;
/**
 * 日切
 * @author ningpeng
 *
 */
@Service
public class SwitchDayClient extends AbsBatchClientProc<SwitchDayRequestBean, SwitchDayResponseBean>{

	public synchronized static SwitchDayClient getInstance() throws SnowException {
		return ContextUtil.getSingleService(SwitchDayClient.class);
	}
	
	@Override
	public String getReqUrl() {
		return "swichDay";
	}

	@Override
	public void preProcess(SwitchDayRequestBean reqBean) throws SnowException {
	}

	@Override
	public SwitchDayResponseBean setRetMsgToBean(String retMsg) throws SnowException {
		SwitchDayResponseBean rspBean = new SwitchDayResponseBean(retMsg);
		return rspBean;
	}

}
