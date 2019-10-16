package com.ruimin.ifs.login.process.bean;

import lombok.Data;

/**
 * @program: be-delivery-admin
 * @description:
 * @author: 韩伟成
 * @create: 2019-10-10 14:38
 */
@Data
public class FrontMachine {
    private String bankName;
    private String bankIpHost;
    private String status;
    private String expireDate;
}
