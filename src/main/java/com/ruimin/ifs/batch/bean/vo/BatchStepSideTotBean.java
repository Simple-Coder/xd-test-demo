package com.ruimin.ifs.batch.bean.vo;

public class BatchStepSideTotBean {
	private int totalCnt = 0;
	private int succCnt = 0;
	private int runCnt = 0;
	private int errorCnt = 0;
	private int nonCnt = 0;

	private String sideMsg;

	public int getNonCnt() {
		return nonCnt;
	}

	public void setNonCnt(int nonCnt) {
		this.nonCnt = nonCnt;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getSuccCnt() {
		return succCnt;
	}

	public void setSuccCnt(int succCnt) {
		this.succCnt = succCnt;
	}

	public int getRunCnt() {
		return runCnt;
	}

	public void setRunCnt(int runCnt) {
		this.runCnt = runCnt;
	}

	public int getErrorCnt() {
		return errorCnt;
	}

	public void setErrorCnt(int errorCnt) {
		this.errorCnt = errorCnt;
	}

	public String getSideMsg() {
		if (this.getTotalCnt() > 0) {
			StringBuffer msgBuf = new StringBuffer();
			msgBuf.append("总数量:" + this.getTotalCnt() + ",其中:");
			if (this.getSuccCnt() > 0) {
				msgBuf.append("已完成:" + this.getSuccCnt() + ";");
			}
			if (this.getErrorCnt() > 0) {
				msgBuf.append("执行异常:" + this.getErrorCnt() + ";");
			}
			if (this.getRunCnt() > 0) {
				msgBuf.append("运行中:" + this.getRunCnt() + ";");
			}
			if (this.getNonCnt() > 0) {
				msgBuf.append("未运行:" + this.getNonCnt() + ";");
			}
			this.sideMsg = msgBuf.toString();
		}
		return this.sideMsg;
	}
}
