package com.ruimin.ifs.mng.process.bean;

public class TlrRoleVO{
	private java.lang.String tlrno;
	private java.lang.String tlrName;
	private java.lang.String roleName;
	public java.lang.String getTlrno() {
		return tlrno;
	}
	public void setTlrno(java.lang.String tlrno) {
		this.tlrno = tlrno;
	}
	public java.lang.String getTlrName() {
		return tlrName;
	}
	public void setTlrName(java.lang.String tlrName) {
		this.tlrName = tlrName;
	}
	public java.lang.String getRoleName() {
		return roleName;
	}
	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}
	public TlrRoleVO() {
	}
	public TlrRoleVO(String tlrno, String tlrName, String roleName) {
		super();
		this.tlrno = tlrno;
		this.tlrName = tlrName;
		this.roleName = roleName;
	}
	
}
