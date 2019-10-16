package com.ruimin.ifs.batch.bean.vo;

import com.ruimin.ifs.batch.process.util.BatchJobConstant;

public class BatchStepInfo {

	private int id;//
	private int jobNo;// 作业号
	private int stepNo;// 步骤号
	private int subStepNo;// 子步骤号
	private String subStepName;// 子步骤名
	private String jobName;// 作业名
	private String stepName;// 步骤名
	private String suspendCode;// 出错忽视
	private String statusCode;// 运行状态编码
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	private BatchInfo batchInfo;// 总批量信息
	private String errorMsg;//错误原因
	private String subFlag;//并行标识
	public String getSubFlag() {
		return subFlag;
	}
	public void setSubFlag(String subFlag) {
		this.subFlag = subFlag;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the batchInfo
	 */
	public BatchInfo getBatchInfo() {
		return batchInfo;
	}

	/**
	 * @param batchInfo
	 *            the batchInfo to set
	 */
	public void setBatchInfo(BatchInfo batchInfo) {
		this.batchInfo = batchInfo;
	}

	public int getJobNo() {
		return jobNo;
	}

	public void setJobNo(int jobNo) {
		this.jobNo = jobNo;
	}

	public int getSubStepNo() {
		return subStepNo;
	}

	public void setSubStepNo(int subStepNo) {
		this.subStepNo = subStepNo;
	}

	public String getSubStepName() {
		return subStepName;
	}

	public void setSubStepName(String subStepName) {
		this.subStepName = subStepName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getSuspendCode() {
		return suspendCode;
	}

	public void setSuspendCode(String suspendCode) {
		this.suspendCode = suspendCode;
	}

	/**
	 * 是否忽视出错
	 * 
	 * @return
	 */
	public boolean suspend() {
		if (suspendCode != null && suspendCode.equals("1")) {
			return true;
		}

		return false;
	}

	/**
	 * @return the stepNo
	 */
	public int getStepNo() {
		return stepNo;
	}

	/**
	 * @param stepNo
	 *            the stepNo to set
	 */
	public void setStepNo(int stepNo) {
		this.stepNo = stepNo;
	}

	/**
	 * @return the stepName
	 */
	public String getStepName() {
		return stepName;
	}

	/**
	 * @param stepName
	 *            the stepName to set
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 运行状态显示
	 * 
	 * @return the statusDisp
	 */
	public String getStatusDisp() {
		String disp = "未运行";
		if (this.statusCode != null) {
			if (this.statusCode.equals(BatchJobConstant.THREAD_STATUS_ERROR)) {
				disp = "异常中断";
			} else if (this.statusCode.equals(BatchJobConstant.THREAD_STATUS_FINISHED)) {
				disp = "结束";
			} else if (this.statusCode.equals(BatchJobConstant.THREAD_STATUS_RUNNING)) {
				disp = "运行";
			}
		}
		return disp;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStepDispName() {
		return jobNo + "-" + stepNo + "-" + subStepNo;
	}

}
