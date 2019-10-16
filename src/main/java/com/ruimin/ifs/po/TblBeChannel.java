package com.ruimin.ifs.po;

import com.ruimin.ifs.rql.annotation.Id;
import com.ruimin.ifs.rql.annotation.Table;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @project be-delivery-admin
 * @description TblBeChannel实体类。
 * @version 1.0.0
 * @errorcode
 *           错误码: 错误描述 <br>
 *
 * @author
 *        2019-07-15 liuxj Create 1.0 <br>
 *
 * @copyright     ©2018-2019   上海睿民，版权所有。
 */
@Table("tbl_be_channel")
@Data
public class TblBeChannel  {

    /**
     * 主键 uuid
     */
    @Id
    private  String uuid;
    /**
     * 接入系统代码
     */
    private String channelCode;

    /**
     * 可访问时间
     */
    private String openTime;

    /**
     * 可访问截止时间
     */
    private String closeTime;
    /**
     * 接入系统名称
     */
    private String channelName;
    /**
     * 接入系统描述
     */
    private String channelDesc;
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
