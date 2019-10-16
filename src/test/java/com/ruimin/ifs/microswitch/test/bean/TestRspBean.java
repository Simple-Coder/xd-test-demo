package com.ruimin.ifs.microswitch.test.bean;

import java.util.ArrayList;
import java.util.List;

public class TestRspBean {
	private String rspcode;
	
	private String rspmsg;
	
	private List<Purchase> array = new ArrayList<Purchase>();

	public String getRspcode() {
		return rspcode;
	}

	public void setRspcode(String rspcode) {
		this.rspcode = rspcode;
	}

	public String getRspmsg() {
		return rspmsg;
	}

	public void setRspmsg(String rspmsg) {
		this.rspmsg = rspmsg;
	}

	public List<Purchase> getArray() {
		return array;
	}

	public void setArray(List<Purchase> array) {
		this.array = array;
	}
	
}
