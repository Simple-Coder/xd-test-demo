package com.ruimin.ifs.batch.bean.vo;

import com.ruimin.ifs.po.TblBhProcStatus;

public class JobInfVO extends TblBhProcStatus{
	private String misc;

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}
	
	
	private String planExecTm;
	
	public String getPlanExecTm() {
		return planExecTm;
	}
	public void setPlanExecTm(String planExecTm) {
		this.planExecTm = planExecTm;
	}
	
}
