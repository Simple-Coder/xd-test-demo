package com.ruimin.ifs.batch.bean.msg.request;

import java.util.Map;

import com.google.gson.Gson;
import com.ruimin.ifs.batch.bean.msg._RequestBean;
/**
 * 启动联机
 * @author ningpeng
 *
 */
public class StartOnlineRequestBean extends _RequestBean{
	public StartOnlineRequestBean(){
		super.setTxnCd("startOnlineRequest");
	}
	@Override
	public String toJson() {
		Gson gson = new Gson();
		Map<String, Object> jsonMap = super.getJsonMap();
		return gson.toJson(jsonMap);
	}
}
