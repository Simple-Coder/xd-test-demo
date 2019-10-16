package com.ruimin.ifs.po;

import com.ruimin.ifs.rql.annotation.Id;
import com.ruimin.ifs.rql.annotation.Table;
import lombok.Data;

/**
 * @author 2019-07-17 liuxj Create 1.0 <br>
 * @version 1.0.0
 * @project be-delivery-admin
 * @description TblBeTradeCfg 交易配置信息实体类。
 * @errorcode 错误码: 错误描述 <br>
 * @copyright ©2018-2019   上海睿民，版权所有。
 */
@Table("tbl_be_trade_cfg")
@Data
public class TblBeTradeCfg {

    /**
     * 主键 uuid
     */
    @Id
    private String uuid;
    /**
     * 交易代码
     */
    private String tradeCode;

    /**
     * 交易代码名称
     */
    private String tradeName;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 交易状态
     */
    private String tradeStatus;

    /**
     * 交易形态
     */
    private String tradePattern;
    
    /**
     * 循环间隔时间
     */
    private int loopTime;

    /**
     * 最后运行时间
     */
    private String tradeLastRunTime;

    /**
     * 交易开始时间
     */
    private String tradeOpenTime;

    /**
     * 交易结束时间
     */
    private String tradeCloseTime;

    /**
     * 交易处理默认初始化线程池线程个数
     */
    private int threadNums;

    /**
     * 单次处理数量
     */
    private int singleDealNum;

    /**
     * 交易代码功能描述
     */
    private String tradeDesc;

    /**
     * 交易失败返回码
     */
    private String tradeRespErrCode;

    /**
     * 日切时是否停止运行
     */
    private String switchStopFlag;

    /**
     * 定时发起时间
     */
    private String timedExecTime;

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
    private String lastUpdateTime;
}
