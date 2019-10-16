package com.ruimin.ifs.batch.bean.msg.request;

import java.util.Map;

import com.google.gson.Gson;
import com.ruimin.ifs.batch.bean.msg._RequestBean;

public class StartSingleJobRequestBean extends _RequestBean {
	private int JobNo;
	private int StepNo;
	private int SubStepNo;

	public int getJobNo() {
		return JobNo;
	}

	public void setJobNo(int jobNo) {
		JobNo = jobNo;
	}

	public int getStepNo() {
		return StepNo;
	}

	public void setStepNo(int stepNo) {
		StepNo = stepNo;
	}

	public int getSubStepNo() {
		return SubStepNo;
	}

	public void setSubStepNo(int subStepNo) {
		SubStepNo = subStepNo;
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		Map<String, Object> jsonMap = super.getJsonMap();
		jsonMap.put("jobNo", this.getJobNo());
		jsonMap.put("stepNo", this.getStepNo());
		jsonMap.put("subStepNo", this.getSubStepNo());
		return gson.toJson(jsonMap);
	}

	public StartSingleJobRequestBean(int jobNo, int stepNo, int subStepNo) {
		super.setTxnCd("StartSingleJobRequest");
		JobNo = jobNo;
		StepNo = stepNo;
		SubStepNo = subStepNo;
	}
	
	

}
