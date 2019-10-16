package com.ruimin.ifs.batch.bean.msg;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class _ResponseBean {
	private String rspServer;// batch server node name
	private String txnCd;// txn code
	private String rspCode;// response code,char(4)，default 0000(success)
	private String rspMsg;// response message
	private long rspTime;// batch server timestamp

	public String getRspServer() {
		return rspServer;
	}

	public void setRspServer(String rspServer) {
		this.rspServer = rspServer;
	}

	public String getTxnCd() {
		return txnCd;
	}

	public void setTxnCd(String txnCd) {
		this.txnCd = txnCd;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspMsg() {
		if(StringUtils.isBlank(rspMsg)){
			rspMsg = "返回信息为空!";
		}
		return rspMsg;
	}

	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}

	public long getRspTime() {
		return rspTime;
	}

	public void setRspTime(long rspTime) {
		this.rspTime = rspTime;
	}
	
	public boolean isSuccess(){
		if (StringUtils.equals(this.rspCode, "0000")) {
			return true;
		}
		return false;
	}
	
	protected JsonObject parseJson(String jsonStr){
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(jsonStr);
		JsonObject jsonObj = element.getAsJsonObject();
		this.rspServer = jsonObj.get("rspServer").getAsString();
		this.txnCd = jsonObj.get("txnCd").getAsString();
		this.rspCode = jsonObj.get("rspCode").getAsString();
		JsonElement jsonElement = jsonObj.get("rspMsg");
		if (jsonElement!=null) {
			this.rspMsg = jsonElement.getAsString();
		}
		this.rspTime = jsonObj.get("rspTime").getAsLong();
		return jsonObj;
	}
}
