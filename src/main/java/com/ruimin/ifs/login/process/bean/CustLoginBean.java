package com.ruimin.ifs.login.process.bean;

public class CustLoginBean {
    private String userNo;//用户号
    private String passWord;//用户密码
    private String userIp;//用户登录IP
    private String userBrh;//用户所属机构
    private String encryptedUid;//DES加密串
    public String getUserNo() {
        return userNo;
    }
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getUserIp() {
        return userIp;
    }
    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }
    public String getUserBrh() {
        return userBrh;
    }
    public void setUserBrh(String userBrh) {
        this.userBrh = userBrh;
    }
    public String getEncryptedUid() {
        return encryptedUid;
    }
    public void setEncryptedUid(String encryptedUid) {
        this.encryptedUid = encryptedUid;
    }



}
