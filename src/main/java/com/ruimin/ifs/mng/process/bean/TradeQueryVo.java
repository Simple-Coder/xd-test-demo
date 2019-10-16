package com.ruimin.ifs.mng.process.bean;

/**
 * @Description:交易量查询Vo
 * @Author: 谢冬
 * @date: 2019/8/8 14:29
 */

public class TradeQueryVo {
    private String respSys;
    private String tranCode;//tran_code
    private String totalNum;//total_num
    private long successNum;
    private String successPer;
    private long failureNum;
    private String failurePer;
    private long timeOutNum;//超时量
//    private long timeOutNum;

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

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public long getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(long successNum) {
        this.successNum = successNum;
    }



    public long getFailureNum() {
        return failureNum;
    }

    public void setFailureNum(long failureNum) {
        this.failureNum = failureNum;
    }

    public String getSuccessPer() {
        return successPer;
    }

    public void setSuccessPer(String successPer) {
        this.successPer = successPer;
    }

    public String getFailurePer() {
        return failurePer;
    }

    public void setFailurePer(String failurePer) {
        this.failurePer = failurePer;
    }

    public long getTimeOutNum() {
        return timeOutNum;
    }

    public void setTimeOutNum(long timeOutNum) {
        this.timeOutNum = timeOutNum;
    }
}
