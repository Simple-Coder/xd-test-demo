package com.ruimin.ifs.login.comp;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.mng.process.service.HighChartsService;
import com.ruimin.ifs.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * @ProjectName: be-delivery-admin
 * @Package: com.ruimin.ifs.login.comp
 * @ClassName: HighChartsQueryAction
 * @Author: dong
 * @Description: ${description}
 * @Date: 2019/9/10 10:39
 * @Version: 1.0
 */
@SnowDoc(desc = "HighChartsQueryAction")
@ActionResource
public class HighChartsQueryAction {
    @Action
    @SnowDoc(desc = "图表指定日期查询")
    public String queryData(QueryParamBean queryBean) throws SnowException {
        String tradeStartTime = queryBean.getParameter("tradeStartTime");
        String tradeEndTime = queryBean.getParameter("tradeEndTime");
        String bankSelect = queryBean.getParameter("bankSelect");
        tradeStartTime=tradeStartTime==null||"".equals(tradeStartTime)? DateUtil.getYYYYMMDD(new Date()).concat("000000000"):tradeStartTime;
        tradeEndTime=tradeEndTime==null||"".equals(tradeEndTime)? DateUtil.getYYYYMMDD(new Date()).concat("999999999"):tradeEndTime;
        bankSelect=bankSelect==null||"".equals(bankSelect)?"":bankSelect;

//        List<Object> objects = HighChartsService.getInstance().queryListAll(tradeStartTime, tradeEndTime, bankSelect);
        return "11111s";
    }
}
