package com.ruimin.ifs.mng.process.bean;

public class CronJobParamVo {
	private String paramKey;
	private String paramValue;
	public String getParamKey() {
		return paramKey;
	}
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public CronJobParamVo(String paramKey, String paramValue) {
		this.paramKey = paramKey;
		this.paramValue = paramValue;
	}
	
	
}
