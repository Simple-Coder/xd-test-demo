package com.ruim.ifs.tools;

public class ColumnDetailVo {
	private String cloumnName;
	private String dataType;

	private Integer columnLen;

	private Integer scaleLen;

	private Class<?> javaType = String.class;

	private String dbShowType;

	private boolean isNotNull = false;

	private boolean isPk = false;

	private String comment;

	public String getCloumnName() {
		return cloumnName;
	}

	public void setCloumnName(String cloumnName) {
		this.cloumnName = cloumnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getColumnLen() {
		return columnLen;
	}

	public void setColumnLen(Integer columnLen) {
		this.columnLen = columnLen;
	}

	public Integer getScaleLen() {
		return scaleLen;
	}

	public void setScaleLen(Integer scaleLen) {
		this.scaleLen = scaleLen;
	}

	public String getDbShowType() {
		return dbShowType;
	}

	public void setDbShowType(String dbShowType) {
		this.dbShowType = dbShowType;
	}

	public boolean isNotNull() {
		return isNotNull;
	}

	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

	public boolean isPk() {
		return isPk;
	}

	public void setPk(boolean isPk) {
		this.isPk = isPk;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Class<?> getJavaType() {
		return javaType;
	}
	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}
}
