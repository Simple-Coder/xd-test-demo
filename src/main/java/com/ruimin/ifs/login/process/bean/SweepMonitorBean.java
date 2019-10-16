package com.ruimin.ifs.login.process.bean;

public class SweepMonitorBean
{
    private String bankcode;
    private String shortname;
    private String sweepSuccess;
    private String sweepOther;
    private String sweepFailure;
    private String entrySuccess;
    private String entryFailure;

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getSweepSuccess() {
        return sweepSuccess;
    }

    public void setSweepSuccess(String sweepSuccess) {
        this.sweepSuccess = sweepSuccess;
    }

    public String getSweepOther() {
        return sweepOther;
    }

    public void setSweepOther(String sweepOther) {
        this.sweepOther = sweepOther;
    }

    public String getSweepFailure() {
        return sweepFailure;
    }

    public void setSweepFailure(String sweepFailure) {
        this.sweepFailure = sweepFailure;
    }

    public String getEntrySuccess() {
        return entrySuccess;
    }

    public void setEntrySuccess(String entrySuccess) {
        this.entrySuccess = entrySuccess;
    }

    public String getEntryFailure() {
        return entryFailure;
    }

    public void setEntryFailure(String entryFailure) {
        this.entryFailure = entryFailure;
    }
}
