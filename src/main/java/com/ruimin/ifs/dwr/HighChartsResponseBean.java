package com.ruimin.ifs.dwr;

import java.util.List;

/**
 * @ProjectName: be-delivery-admin
 * @Package: com.ruimin.ifs.dwr
 * @ClassName: HighChartsRequestBean
 * @Author: dong
 * @Description: ${description}
 * @Date: 2019/9/1 18:12
 * @Version: 1.0
 */
public class HighChartsResponseBean {
    private List<String> xtext;
    private List<Integer> ysuccess;
    private List<Integer> yfail;
    private List<Integer> ytotal;
    private List<Integer> ytimeout;
    private String piesuccess;
    private String piefail;
    private String pietimeout;

    public List<String> getXtext() {
        return xtext;
    }

    public void setXtext(List<String> xtext) {
        this.xtext = xtext;
    }

    public List<Integer> getYsuccess() {
        return ysuccess;
    }

    public void setYsuccess(List<Integer> ysuccess) {
        this.ysuccess = ysuccess;
    }

    public List<Integer> getYfail() {
        return yfail;
    }

    public void setYfail(List<Integer> yfail) {
        this.yfail = yfail;
    }

    public List<Integer> getYtotal() {
        return ytotal;
    }

    public void setYtotal(List<Integer> ytotal) {
        this.ytotal = ytotal;
    }

    public String getPiesuccess() {
        return piesuccess;
    }

    public void setPiesuccess(String piesuccess) {
        this.piesuccess = piesuccess;
    }

    public String getPiefail() {
        return piefail;
    }

    public void setPiefail(String piefail) {
        this.piefail = piefail;
    }

    public String getPietimeout() {
        return pietimeout;
    }

    public void setPietimeout(String pietimeout) {
        this.pietimeout = pietimeout;
    }

    public List<Integer> getYtimeout() {
        return ytimeout;
    }

    public void setYtimeout(List<Integer> ytimeout) {
        this.ytimeout = ytimeout;
    }
}
