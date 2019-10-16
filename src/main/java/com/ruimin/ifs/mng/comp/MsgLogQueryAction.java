package com.ruimin.ifs.mng.comp;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataset.field.FieldBean;
import com.ruimin.ifs.mng.process.bean.BankDropDpwnListVo;
import com.ruimin.ifs.mng.process.bean.MsgLogDropDownListVo;
import com.ruimin.ifs.mng.process.service.BankInformationService;
import com.ruimin.ifs.mng.process.service.MsgLogService;
import com.ruimin.ifs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.List;

@SnowDoc(desc = "报文日志查询")
@ActionResource
@Slf4j
public class MsgLogQueryAction extends SnowAction {
    /**
     *  根据查询条件获取接入系统的信息
     * @param queryBean 前端页面查询条件封装bean
     * @return
     * @throws SnowException
     */
    @Action
    @SnowDoc(desc = "日志查询")
    public PageResult queryMsgLog(QueryParamBean queryBean) throws SnowException {
        String msgStartTime = queryBean.getParameter("msgStartTime");
        String msgEndTime = queryBean.getParameter("msgEndTime");
        if (msgStartTime!=null&&msgEndTime!=null)
        {
            if(!msgStartTime.equals("")&&!msgEndTime.equals("")){
                Date parse_msgStartTime = DateUtil.parse(msgStartTime, DateUtil.PATTERN_YYYYMMDDHHMMSS);
                Date parse_msgEndTime = DateUtil.parse(msgEndTime, DateUtil.PATTERN_YYYYMMDDHHMMSS);
                if (parse_msgStartTime.after(parse_msgEndTime))
                {
                    SnowExceptionUtil.throwErrorException("开始日期不能大于结束日期！");
                }
            }

            msgStartTime=msgStartTime.concat("000");
            msgEndTime=msgEndTime.concat("999");
        }

        String msgNo = queryBean.getParameter("msgNo");
        String fromSys = queryBean.getParameter("fromSys");
        String toSys = queryBean.getParameter("toSys");
        String tradeNum = queryBean.getParameter("tradeNum");
        String msgType = queryBean.getParameter("msgType");
        String dealStatus = queryBean.getParameter("dealStatus");
        //请求账号
        String msgAcc = queryBean.getParameter("msgAcc");
        if (msgAcc!=null&&!"".equals(msgAcc))
        {
            msgAcc=msgAcc.trim();
        }

        return MsgLogService.getInstance().queryListLog(msgStartTime,msgEndTime,msgNo,fromSys,toSys,tradeNum,msgType,dealStatus,msgAcc,queryBean.getPage());
    }
    /**
     * 交易码名称下拉列表查询
     *
     * @param queryBean QueryParamBean查询类的请求参数
     * @return 查询结果，返回类型可以为List/PageResult（包含分页信息）/JavaBean对象
     * @throws SnowException
     */
    // Action注解表示一个组件，查询类不需要事务
    @Action
    @SnowDoc(desc = "交易码名称下拉列表查询")
    public List<MsgLogDropDownListVo> msgLogDropDownList(QueryParamBean queryBean) throws SnowException {

        log.info("交易码名称下拉列表查询开始");

        // 调用服务层
        List<MsgLogDropDownListVo> list = MsgLogService.getInstance().msgLogDropDownList();

        log.info("交易码名称下拉列表查询结果：" + list.toString());

        log.info("交易码名称下拉列表查询结束");

        // 返回结果集
        return list;
    }

}
