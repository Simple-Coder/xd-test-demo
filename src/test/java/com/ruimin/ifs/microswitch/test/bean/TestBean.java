package com.ruimin.ifs.microswitch.test.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class TestBean {

	private String version;
	private String xtype;
	private String xclass;
	private String pan;
	private Merchant merchant;
	private List<Purchase> array = new ArrayList<Purchase>();
	private Date xdate;
	private String testAttr;
	private int numberFmtTest;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getXtype() {
		return xtype;
	}
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	public String getXclass() {
		return xclass;
	}
	public void setXclass(String xclass) {
		this.xclass = xclass;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	public List<Purchase> getArray() {
		return array;
	}
	public void setArray(List<Purchase> array) {
		this.array = array;
	}
	public String getTestAttr() {
		return testAttr;
	}
	public void setTestAttr(String testAttr) {
		this.testAttr = testAttr;
	}
	public Date getXdate() {
		return xdate;
	}
	public void setXdate(Date xdate) {
		this.xdate = xdate;
	}
	
	public int getNumberFmtTest() {
		return numberFmtTest;
	}
	public void setNumberFmtTest(int numberFmtTest) {
		this.numberFmtTest = numberFmtTest;
	}
}
