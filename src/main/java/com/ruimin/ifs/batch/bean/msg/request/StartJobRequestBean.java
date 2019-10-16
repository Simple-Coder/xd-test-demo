package com.ruimin.ifs.batch.bean.msg.request;

import java.util.Map;

import com.google.gson.Gson;
import com.ruimin.ifs.batch.bean.msg._RequestBean;
public class StartJobRequestBean extends _RequestBean{
	private int jobNo;
	
	public int getJobNo() {
		return jobNo;
	}
	public void setJobNo(int jobNo) {
		this.jobNo = jobNo;
	}
	
	public StartJobRequestBean(int jobNo){
		super.setTxnCd("startJobRequest");
		this.jobNo = jobNo;
	}
	@Override
	public String toJson() {
		Gson gson = new Gson();
		Map<String, Object> jsonMap = super.getJsonMap();
		jsonMap.put("jobNo", this.getJobNo());
		return gson.toJson(jsonMap);
	}
}
