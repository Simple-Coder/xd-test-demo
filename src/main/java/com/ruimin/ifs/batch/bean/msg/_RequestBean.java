package com.ruimin.ifs.batch.bean.msg;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.batch.process.util.BatchUtil;
import com.ruimin.ifs.core.util.ContextUtil;

/**
 * 通用请求报文
 * 
 * @author wangpb
 *
 */
public abstract class _RequestBean {
	private String reqServer;//服务名
	private String txnCd;//交易码
	private String reqTrace;//交易流水
	private Long reqTime;//交易时间

	public String getReqServer() {
		if (StringUtils.isBlank(reqServer)) {
			reqServer = BatchUtil.getServerNm();
		}
		return reqServer;
	}

	public void setReqServer(String reqServer) {
		this.reqServer = reqServer;
	}

	public String getTxnCd() {
		return txnCd;
	}

	public void setTxnCd(String txnCd) {
		this.txnCd = txnCd;
	}

	public String getReqTrace() {
		if (StringUtils.isBlank(reqTrace)) {
			reqTrace = ContextUtil.getUUID();
		}
		return reqTrace;
	}

	public void setReqTrace(String reqTrace) {
		this.reqTrace = reqTrace;
	}

	public Long getReqTime() {
		if (reqTime == null) {
			reqTime = System.currentTimeMillis();
		}
		return reqTime;
	}

	public void setReqTime(Long reqTime) {
		this.reqTime = reqTime;
	}
	
	protected Map<String, Object> getJsonMap(){
		Map<String, Object> jsonMap = new LinkedHashMap<String,Object>();
		jsonMap.put("reqServer", this.getReqServer());
		jsonMap.put("txnCd", this.getTxnCd());
		jsonMap.put("reqTrace", this.getReqTrace());
		jsonMap.put("reqTime", this.getReqTime());
		return jsonMap;
	}

	public abstract String toJson();
}
