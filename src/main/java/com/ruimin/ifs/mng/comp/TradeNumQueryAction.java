package com.ruimin.ifs.mng.comp;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.mng.process.service.TradeNumQueryService;
import lombok.extern.slf4j.Slf4j;

/**
 * 交易量统计
 * xiedong
 */
@SnowDoc(desc = "交易量统计")
@ActionResource
@Slf4j
public class TradeNumQueryAction extends SnowAction {
    /**
     * 根据查询条件获取接入系统的信息
     *
     * @param queryBean 前端页面查询条件封装bean
     * @return
     * @throws SnowException
     */
    @Action
    @SnowDoc(desc = "交易量统计")
    public PageResult queryTradeNum(QueryParamBean queryBean) throws SnowException {
        String tradeTimeStart = queryBean.getParameter("tradeTimeStart");
        String tradeTimeEnd = queryBean.getParameter("tradeTimeEnd");
        String queryDimension = queryBean.getParameter("queryDimension");
        //定义交易维度
        String respSys = "";
        String tranCode = "";
        if (queryDimension!=null&&queryDimension.equals("respSys"))
        {
            //说明只是银行维度
            respSys=queryDimension;
        }
        else if (queryDimension!=null&&queryDimension.startsWith("tranCode"))
        {
            //说明只是交易类型维度
            tranCode=queryDimension;
        }
         else if (queryDimension!=null&&queryDimension.equals("respSys,tranCode"))
        {
            //说明复选框为两项
            String[] temps = queryDimension.split(",");
            respSys = temps[0];
            tranCode = temps[1];
        }
        else if (queryDimension==null||queryDimension.equals(""))
        {
            //说明为起始全部为null
            respSys="respSys";
            tranCode = "tranCode";
        }
        return TradeNumQueryService.getInstance().queryTradeNum(tradeTimeStart, tradeTimeEnd, respSys,tranCode, queryBean.getPage());
    }
}
