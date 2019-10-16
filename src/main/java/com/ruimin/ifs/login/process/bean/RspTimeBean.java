package com.ruimin.ifs.login.process.bean;

import lombok.Data;


public class RspTimeBean
{
    private String bankname;
    private String rspsys;
    private String avgtime;
    private String maxtime;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getRspsys() {
        return rspsys;
    }

    public void setRspsys(String rspsys) {
        this.rspsys = rspsys;
    }

    public String getAvgtime() {
        return avgtime;
    }

    public void setAvgtime(String avgtime) {
        this.avgtime = avgtime;
    }

    public String getMaxtime() {
        return maxtime;
    }

    public void setMaxtime(String maxtime) {
        this.maxtime = maxtime;
    }
}
