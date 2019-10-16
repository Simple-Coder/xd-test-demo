package com.ruimin.ifs.po;

import com.ruimin.ifs.framework.core.bean.DataDic;
import com.ruimin.ifs.rql.annotation.Id;
import com.ruimin.ifs.rql.annotation.Table;

@Table("ifs_data_dic")
public class TblDataDic implements DataDic{
	@Id
	private String id;
	private String dataTypeNo;
	private String dataNo;
	private String dataTypeName;
	private Integer dataNoLen;
	private String dataName;
	private String limitFlag;
	private String highLimit;
	private String lowLimit;
	private String miscflgs;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataTypeNo() {
		return dataTypeNo;
	}
	public void setDataTypeNo(String dataTypeNo) {
		this.dataTypeNo = dataTypeNo;
	}
	public String getDataNo() {
		return dataNo;
	}
	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}
	public String getDataTypeName() {
		return dataTypeName;
	}
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}
	public Integer getDataNoLen() {
		return dataNoLen;
	}
	public void setDataNoLen(Integer dataNoLen) {
		this.dataNoLen = dataNoLen;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getLimitFlag() {
		return limitFlag;
	}
	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
	}
	public String getHighLimit() {
		return highLimit;
	}
	public void setHighLimit(String highLimit) {
		this.highLimit = highLimit;
	}
	public String getLowLimit() {
		return lowLimit;
	}
	public void setLowLimit(String lowLimit) {
		this.lowLimit = lowLimit;
	}
	public String getMiscflgs() {
		return miscflgs;
	}
	public void setMiscflgs(String miscflgs) {
		this.miscflgs = miscflgs;
	}
	
}
