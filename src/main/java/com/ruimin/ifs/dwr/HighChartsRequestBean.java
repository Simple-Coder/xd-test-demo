package com.ruimin.ifs.dwr;

/**
 * @ProjectName: be-delivery-admin
 * @Package: com.ruimin.ifs.dwr
 * @ClassName: HighChartsRequestBean
 * @Author: dong
 * @Description: ${description}
 * @Date: 2019/9/1 18:12
 * @Version: 1.0
 */
public class HighChartsRequestBean {
    private String tradeStartTime;
    private String tradeEndTime;
    private String bankSelect;

    public String getTradeStartTime() {
        return tradeStartTime;
    }

    public void setTradeStartTime(String tradeStartTime) {
        this.tradeStartTime = tradeStartTime;
    }

    public String getTradeEndTime() {
        return tradeEndTime;
    }

    public void setTradeEndTime(String tradeEndTime) {
        this.tradeEndTime = tradeEndTime;
    }

    public String getBankSelect() {
        return bankSelect;
    }

    public void setBankSelect(String bankSelect) {
        this.bankSelect = bankSelect;
    }
}
