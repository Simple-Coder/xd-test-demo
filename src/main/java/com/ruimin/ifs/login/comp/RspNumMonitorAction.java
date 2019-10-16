package com.ruimin.ifs.login.comp;

import com.alibaba.fastjson.JSONObject;
import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.login.process.RspNumMonitorService;
import com.ruimin.ifs.login.process.bean.MsgQueryBean;
import com.ruimin.ifs.login.process.bean.RspCodeBean;
import com.ruimin.ifs.login.process.bean.RspCodeVo;
import com.ruimin.ifs.mng.process.service.BeBankReturnCodeService;
import com.ruimin.ifs.po.TblBeBankReturnCode;
import com.ruimin.ifs.util.DateUtil;
import com.ruimin.ifs.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//ActionResource注解表示该类是个组件资源包
@SnowDoc(desc = "响应码信息")
@ActionResource
@Slf4j
public class RspNumMonitorAction extends SnowAction {// 继承SnowAction

    // Action注解表示一个组件，查询类不需要事务
    @Action
    @SnowDoc(desc = "响应码信息查询")
    public List<RspCodeBean> queryRspNumData(QueryParamBean queryBean) throws SnowException {

        log.info("响应码信息开始");
        //获取当前时间
        Date date = new Date();
        String req_time = DateUtil.getYYYYMMDD(date).concat("000000").concat("000");
        String resp_time = DateUtil.getYYYYMMDD(date).concat("999999").concat("999");
        PageResult pageResult = RspNumMonitorService.getInstance().queryRspNumInfo(req_time, resp_time, queryBean.getPage());
        log.info("响应时长信息结束");
        //声明页面所需集合
        ArrayList<RspCodeBean> rspCodeBeans = new ArrayList<>();
        List<?> tempVos = pageResult.getQueryResult();
        //计算总量，用于求比例
        BigDecimal all_total_num = new BigDecimal("0");
        for (Object tempVo : tempVos)
        {
            RspCodeVo temp_rsp_code=(RspCodeVo)tempVo;
            String totalNum = temp_rsp_code.getTotalNum();
            if (totalNum!=null&&!totalNum.equals("0"))
            {
                all_total_num=all_total_num.add(new BigDecimal(totalNum));
            }
            else
            {
                    all_total_num=all_total_num.add(new BigDecimal("0"));
            }
        }

        PageResult returnCodes = BeBankReturnCodeService.getInstance().queryReturnCodeList(null, null, queryBean.getPage());
        List<?> tempCodeVos = returnCodes.getQueryResult();
        for (Object tempCodeVo : tempCodeVos)
        {
            TblBeBankReturnCode temp=(TblBeBankReturnCode)tempCodeVo;
            //获取银企平台返回码
            String beReturnCode = temp.getBeReturnCode();
            //获取接入行返回码
            String returnCode = temp.getReturnCode();
            String returnCodeDesc = temp.getReturnCodeDesc();
            for (Object tempVo : tempVos)
            {
                RspCodeVo temp_rsp_code=(RspCodeVo)tempVo;
                String temp_respCode = temp_rsp_code.getRespCode();
                String totalNum = temp_rsp_code.getTotalNum();
                BigDecimal curr_num = new BigDecimal(totalNum);
                if (temp_respCode!=null&&temp_respCode.equals(returnCode))
                {
                    RspCodeBean rspCodeBean = new RspCodeBean();
                    rspCodeBean.setRespCode(beReturnCode);
                    rspCodeBean.setRespName(returnCodeDesc);
                    rspCodeBean.setTotalNum(totalNum);
                    //计算比例
                    String per=(curr_num.multiply(new BigDecimal(100))).divide(all_total_num, 2, BigDecimal.ROUND_HALF_UP).toString();
                    rspCodeBean.setTotalPer(per.concat("%"));
                    rspCodeBeans.add(rspCodeBean);
                }
            }
        }

        // 返回结果集
        return rspCodeBeans;
    }
}
