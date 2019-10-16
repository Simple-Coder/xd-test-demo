package com.ruimin.ifs.batch.bean.vo;

public class BatchRunTimeVo {

	private String paramId;

	private String magicId;

	private String jobParamKey;

	private String paramValueTx;

	private int minute;

	private int hour;

	private String desc0;
	
	private String runTime;

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getMagicId() {
		return magicId;
	}

	public void setMagicId(String magicId) {
		this.magicId = magicId;
	}

	public String getJobParamKey() {
		return jobParamKey;
	}

	public void setJobParamKey(String jobParamKey) {
		this.jobParamKey = jobParamKey;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getParamValueTx() {
		return paramValueTx;
	}

	public void setParamValueTx(String paramValueTx) {
		this.paramValueTx = paramValueTx;
	}

	public String getDesc0() {
		return desc0;
	}

	public void setDesc0(String desc0) {
		this.desc0 = desc0;
	}
	
	public String getRunTime() {
		return runTime;
	}
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

}
