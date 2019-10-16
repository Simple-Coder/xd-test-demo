package com.ruimin.ifs.po;

import com.ruimin.ifs.rql.annotation.Id;
import com.ruimin.ifs.rql.annotation.Table;

@Table("tbl_be_record_log")
public class TblMsgLog {
    @Id
    private String uuid;
    private String reqSeqNo;
    private String reqTime;
    private String respTime;
    private String reqSys;
    private String respSys;
    private String tranCode;
    private String reqMsg;
    private String respMsg;
    private String respCode;
    private String serverIp;
    private String respDesc;

    private String reqAccounts;//付款银行（）
    private String respAccounts;//收款银行
    private String originalSeqNo;//原交易流水号
    private String amount;



    public TblMsgLog() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReqSeqNo() {
        return reqSeqNo;
    }

    public void setReqSeqNo(String reqSeqNo) {
        this.reqSeqNo = reqSeqNo;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }


    public String getRespTime() {
        return respTime;
    }

    public void setRespTime(String respTime) {
        this.respTime = respTime;
    }

    public String getReqSys() {
        return reqSys;
    }

    public void setReqSys(String reqSys) {
        this.reqSys = reqSys;
    }

    public String getRespSys() {
        return respSys;
    }

    public void setRespSys(String respSys) {
        this.respSys = respSys;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getReqMsg() {
        return reqMsg;
    }

    public void setReqMsg(String reqMsg) {
        this.reqMsg = reqMsg;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getReqAccounts() {
        return reqAccounts;
    }

    public void setReqAccounts(String reqAccounts) {
        this.reqAccounts = reqAccounts;
    }

    public String getRespAccounts() {
        return respAccounts;
    }

    public void setRespAccounts(String respAccounts) {
        this.respAccounts = respAccounts;
    }

    public String getOriginalSeqNo() {
        return originalSeqNo;
    }

    public void setOriginalSeqNo(String originalSeqNo) {
        this.originalSeqNo = originalSeqNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
