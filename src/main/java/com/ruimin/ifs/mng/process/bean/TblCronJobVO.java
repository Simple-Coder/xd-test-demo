package com.ruimin.ifs.mng.process.bean;

import com.ruimin.ifs.framework.quartz.bean.IfsCronJob;
import com.ruimin.ifs.rql.annotation.Transient;

public class TblCronJobVO extends IfsCronJob {

	private Boolean isRunning; // true-运行中, false-已停止

	// 是否带事务执行
	private String transactionFlg = "true";

	@Transient
	public String getTransactionFlg() {
		return transactionFlg;
	}

	public void setTransactionFlg(String transactionFlg) {
		this.transactionFlg = transactionFlg;
	}
	@Transient
	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

}
