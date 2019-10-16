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
@Table("tbl_be_trade_def")
@Data
public class TblBeTradeDef {

    /**
     * 主键 uuid
     */
    @Id
    private  String uuid;

    /**
     * 交易代码
     */
    private String tradeCode;

    /**
     * 交易代码名称
     */
    private String tradeName;

    /**
     * 交易状态
     */
    private String tradeStatus;

    /**
     * 交易开始时间
     */
    private String tradeOpenTime;

    /**
     * 交易结束时间
     */
    private String tradeCloseTime;

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
