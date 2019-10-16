package com.ruimin.ifs.po;

import com.ruimin.ifs.rql.annotation.Id;
import com.ruimin.ifs.rql.annotation.Table;
import com.ruimin.ifs.rql.annotation.Transient;

@Table("IFS_SYS_INF")public class TblSysInf{
	@Id
	private int id;
	private String systemName;
	private String tbsdy;
	private String bhdate;
	private String lbhdate;
	private String status;
	private String systemType;
	private String miscflgs;
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setSystemName(String systemName){
		this.systemName=systemName;
	}
	public String getSystemName(){
		return systemName;
	}
	public void setTbsdy(String tbsdy){
		this.tbsdy=tbsdy;
	}
	public String getTbsdy(){
		return tbsdy;
	}
	public void setBhdate(String bhdate){
		this.bhdate=bhdate;
	}
	public String getBhdate(){
		return bhdate;
	}
	public void setLbhdate(String lbhdate){
		this.lbhdate=lbhdate;
	}
	public String getLbhdate(){
		return lbhdate;
	}
	public void setStatus(String status){
		this.status=status;
	}
	public String getStatus(){
		return status;
	}
	public void setSystemType(String systemType){
		this.systemType=systemType;
	}
	public String getSystemType(){
		return systemType;
	}
	public void setMiscflgs(String miscflgs){
		this.miscflgs=miscflgs;
	}
	public String getMiscflgs(){
		return miscflgs;
	}
	
	private String switchDayTm;
	
	private String startOnlineTm;
	@Transient
	public String getSwitchDayTm() {
		return switchDayTm;
	}
	public void setSwitchDayTm(String switchDayTm) {
		this.switchDayTm = switchDayTm;
	}
	@Transient
	public String getStartOnlineTm() {
		return startOnlineTm;
	}
	public void setStartOnlineTm(String startOnlineTm) {
		this.startOnlineTm = startOnlineTm;
	}
}

