package com.ruimin.ifs.dwr;

import java.util.List;

public class SweepMonitorResponseBean
{
    //y轴（各个银行）
    private List<String> ytext;
    //归集成功
    private List<Integer> sweepSuccess;
    //提交归集
    private List<Integer> sweepOther;
    //归集失败
    private List<Integer> sweepFailure;
    //入账成功
    private List<Integer> entrySuccess;
    //入账失败
    private List<Integer> entryFailure;

    public List<String> getYtext() {
        return ytext;
    }

    public void setYtext(List<String> ytext) {
        this.ytext = ytext;
    }

    public List<Integer> getSweepSuccess() {
        return sweepSuccess;
    }

    public void setSweepSuccess(List<Integer> sweepSuccess) {
        this.sweepSuccess = sweepSuccess;
    }

    public List<Integer> getSweepOther() {
        return sweepOther;
    }

    public void setSweepOther(List<Integer> sweepOther) {
        this.sweepOther = sweepOther;
    }

    public List<Integer> getSweepFailure() {
        return sweepFailure;
    }

    public void setSweepFailure(List<Integer> sweepFailure) {
        this.sweepFailure = sweepFailure;
    }

    public List<Integer> getEntrySuccess() {
        return entrySuccess;
    }

    public void setEntrySuccess(List<Integer> entrySuccess) {
        this.entrySuccess = entrySuccess;
    }

    public List<Integer> getEntryFailure() {
        return entryFailure;
    }

    public void setEntryFailure(List<Integer> entryFailure) {
        this.entryFailure = entryFailure;
    }
}
