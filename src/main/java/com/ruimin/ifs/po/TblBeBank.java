package com.ruimin.ifs.po;

import com.ruimin.ifs.rql.annotation.Id;
import com.ruimin.ifs.rql.annotation.Table;
import lombok.Data;

@Table("tbl_be_bank")
@Data
public class TblBeBank {
    @Id
    private String uuid;
    private String bankCode;
    private String bankName;
    private String bankNumber;
    private String bankAddress;
    private String bankAccount;
    private String bankAccountName;
    private String bankInternalAccount;
    private String bankStatus;
    private String bankCutTradeCode;
    private String bankSigningDate;
    private String bankClientCode;
    private String bankOperator;
    private String bankOperatorPwd;
    private String bankInterfaceAddress;
    private String bankMessageType;
    private String bankWarningThreshold;
    private String bankTerminationThreshold;
    private String createDate;
    private String lastUpdateUser;
    private String lastUpdateTime;
    private String expireDate;
    private String bankIpHost;
}
