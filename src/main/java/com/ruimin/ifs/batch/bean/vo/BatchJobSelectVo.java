package com.ruimin.ifs.batch.bean.vo;

public class BatchJobSelectVo {
	
	private String jobKey;
	
	private String jobName;

	public String getJobKey() {
		return jobKey;
	}

	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public BatchJobSelectVo() {
	}

	public BatchJobSelectVo(String jobKey, String jobName) {
		this.jobKey = jobKey;
		this.jobName = jobName;
	}
	
	
}
