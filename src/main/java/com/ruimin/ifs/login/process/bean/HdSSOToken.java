package com.ruimin.ifs.login.process.bean;

import org.apache.shiro.authc.UsernamePasswordToken;

public class HdSSOToken extends UsernamePasswordToken {

    public int noPassWord;// 0需要，1不需要

    public int getNoPassWord() {
        return noPassWord;
    }

    public void setNoPassWord(int noPassWord) {
        this.noPassWord = noPassWord;
    }

    public HdSSOToken(String username, String password, int noPassWord) {
        super(username, password, false, "null");
        this.noPassWord = noPassWord;
    }
}
