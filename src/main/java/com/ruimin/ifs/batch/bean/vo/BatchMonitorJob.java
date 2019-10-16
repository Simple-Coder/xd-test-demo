package com.ruimin.ifs.batch.bean.vo;

public class BatchMonitorJob {
	private int jobno;
	private String misc;
	private String bhdate;
	private int step;
	private String startTime;
	private String endTime;
	private String status;
	private int stepCnt;
	public int getJobno() {
		return jobno;
	}
	public void setJobno(int jobno) {
		this.jobno = jobno;
	}
	public String getMisc() {
		return misc;
	}
	public void setMisc(String misc) {
		this.misc = misc;
	}
	public String getBhdate() {
		return bhdate;
	}
	public void setBhdate(String bhdate) {
		this.bhdate = bhdate;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getStepCnt() {
		return stepCnt;
	}
	public void setStepCnt(int stepCnt) {
		this.stepCnt = stepCnt;
	}
	
}
