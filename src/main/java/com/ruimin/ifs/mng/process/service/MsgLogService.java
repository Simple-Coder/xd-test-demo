package com.ruimin.ifs.mng.process.service;

import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.iface.service.SnowService;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.framework.process.query.Page;
import com.ruimin.ifs.framework.utils.ValueUtil;
import com.ruimin.ifs.mng.process.bean.BankDropDpwnListVo;
import com.ruimin.ifs.mng.process.bean.MsgLogDropDownListVo;
import com.ruimin.ifs.po.TblMsgLog;
import com.ruimin.ifs.util.DateUtil;

import java.util.List;

/**
 *
 * @project be-delivery-admin
 * @description  报文日志查询服务类类。
 * @version 1.0.0
 * @errorcode
 *           错误码: 错误描述 <br>
 *
 * @author
 *        2019-07-18 xiedong Create 1.0 <br>
 *
 * @copyright     ©2018-2019   上海睿民，版权所有。
 */
@Service
public class MsgLogService extends SnowService {
    /**
     *  获取 报文日志查询服务类
     * @return
     * @throws SnowException
     */
    public static MsgLogService getInstance() throws SnowException {
        return ContextUtil.getSingleService(MsgLogService.class);
    }
    /**
     *  根据 查询条件获取 接入系统列表
     * @param msgStartTime 报文开始时间
     * @param msgEndTime 报文结束时间
     * @param msgNo 交易流水号
     * @param fromSys 发起系统
     * @param toSys 接收系统
     * @param tradeNum 交易码
     * @param msgType 报文类型
     * @param dealStatus 处理状态
     * @param page  通用分页封装类
     * @return   pageResult
     * @throws SnowException
     */
    public PageResult queryListLog(String msgStartTime, String msgEndTime, String msgNo, String fromSys, String toSys, String tradeNum, String msgType, String dealStatus,String msgAcc, Page page) {
        DBDao dao = DBDaos.newInstance();
        PageResult result=dao.selectList("com.ruimin.ifs.mng.rql.sysmng.queryMsgLog",
                                         RqlParam.map().set("msgStartTime", msgStartTime==null||msgStartTime.equals("000")?"":msgStartTime)
                                                 .set("msgEndTime",msgEndTime==null||msgEndTime.equals("999")?"":msgEndTime)
                                                 .set("msgNo",ValueUtil.getRqlParamValue(msgNo, ""))
                                                 .set("fromSys",ValueUtil.getRqlParamValue(fromSys, ""))
                                                 .set("toSys",ValueUtil.getRqlParamValue(toSys, ""))
                                                 .set("tradeNum",ValueUtil.getRqlParamValue(tradeNum, ""))
                                                 .set("msgType",ValueUtil.getRqlParamValue(msgType, ""))
                                                 .set("dealStatus",ValueUtil.getRqlParamValue(dealStatus, ""))
                                                .set("msgAcc",ValueUtil.getRqlParamValue(msgAcc, ""))
                ,page);
        if(result!=null&&result.getQueryResult()!=null)
        {
            for(int i=0;i<result.getQueryResult().size();i++)
            {
                TblMsgLog log=(TblMsgLog)result.getQueryResult().get(i);
                log.setReqTime(DateUtil.formatTransfer(log.getReqTime(),"yyyyMMddHHmmssSSS","yyyy-MM-dd HH:mm:ss.SSS"));
                log.setRespTime(DateUtil.formatTransfer(log.getRespTime(),"yyyyMMddHHmmssSSS","yyyy-MM-dd HH:mm:ss.SSS"));
            }
        }
        return result;
    }

    /**
     * 交易码名称下拉列表查询
     *
     * @return
     * @throws SnowException
     */
    public List<MsgLogDropDownListVo> msgLogDropDownList() throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return dao.queryAll("select trade_code,trade_name from tbl_be_trade_cfg", MsgLogDropDownListVo.class);
    }
}
