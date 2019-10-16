package com.ruimin.ifs.batch.bean.msg.request;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.ruimin.ifs.batch.bean.msg._RequestBean;
/**
 * 日切
 * @author ningpeng
 *
 */
public class SwitchDayRequestBean  extends _RequestBean{
	private String nextTxdate;

	public String getNextTxdate() {
		return nextTxdate;
	}

	public void setNextTxdate(String nextTxdate) {
		this.nextTxdate = nextTxdate;
	}
	public SwitchDayRequestBean(String nextTxdate){
		super.setTxnCd("switchDayRequest");
		this.nextTxdate = nextTxdate;
	}
	public SwitchDayRequestBean(){
		super.setTxnCd("switchDayRequest");
	}
	
	@Override
	public String toJson() {
		Gson gson = new Gson();
		Map<String, Object> jsonMap = super.getJsonMap();
		if (StringUtils.isNotBlank(nextTxdate)) {
			jsonMap.put("nextTxdate", this.getNextTxdate());
		}
		return gson.toJson(jsonMap);
	}

}
