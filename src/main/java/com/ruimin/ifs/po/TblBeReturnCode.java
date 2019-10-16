package com.ruimin.ifs.po;

import com.ruimin.ifs.rql.annotation.Id;
import com.ruimin.ifs.rql.annotation.Table;
import lombok.Data;

/**
 *
 * @project be-delivery-admin
 * @description TblBeTradeCfg 交易配置信息实体类。
 * @version 1.0.0
 * @errorcode
 *           错误码: 错误描述 <br>
 *
 * @author
 *        2019-07-17 liuxj Create 1.0 <br>
 *
 * @copyright     ©2018-2019   上海睿民，版权所有。
 */
@Table("tbl_be_return_code")
@Data
public class TblBeReturnCode {

    /**
     * 主键 uuid
     */
    @Id
    private  String uuid;

    /**
     * 银企平台返回码
     */
    private String beReturnCode;

    /**
     * 银企平台返回码描述
     */
    private String returnCodeDesc;

    /**
     * 银企平台返回码类型
     */
    private String returnCodeType;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    public String createDate;

    /**
     * 更新时间
     */
    private String lastUpdateUser;

    /**
     * 创建人或更新人
     */
    private String lastUpdateTime ;
}
