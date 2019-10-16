package com.ruimin.ifs.login.process.bean;

import java.io.Serializable;

/**
 * 用户登录扩展信息
 * 
 * @author ningpeng
 *
 */
public class UserExtInfo implements Serializable{
	private static final long serialVersionUID = -7829027045803546251L;

	private boolean headImg=false;

	private String telNo;

	private String email;

	public boolean isHeadImg() {
		return headImg;
	}

	public void setHeadImg(boolean headImg) {
		this.headImg = headImg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
}
