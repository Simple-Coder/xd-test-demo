package com.ruimin.ifs.batch.bean.msg.request;

import java.util.Map;

import com.google.gson.Gson;
import com.ruimin.ifs.batch.bean.msg._RequestBean;
/**
 * 停止批量总控
 * @author wangpb
 *
 */
public class StopBtchRequestBean extends _RequestBean{
	public StopBtchRequestBean(){
		super.setTxnCd("stopRequest");
	}
	@Override
	public String toJson() {
		Gson gson = new Gson();
		Map<String, Object> jsonMap = super.getJsonMap();
		return gson.toJson(jsonMap);
	}
}
