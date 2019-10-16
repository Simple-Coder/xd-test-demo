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
import com.ruimin.ifs.mng.process.bean.TradeQueryVo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 2019-07-18 xiedong Create 1.0 <br>
 * @version 1.0.0
 * @project be-delivery-admin
 * @description 交易量统计服务类。
 * @errorcode 错误码: 错误描述 <br>
 * @copyright ©2018-2019   上海睿民，版权所有。
 */
@Service
public class TradeNumQueryService extends SnowService {
    /**
     * 获取 交易量统计服务类
     *
     * @return
     * @throws SnowException
     */
    public static TradeNumQueryService getInstance() throws SnowException {
        return ContextUtil.getSingleService(TradeNumQueryService.class);
    }

    /**
     * 根据 查询条件获取 接入系统列表
     *
     * @param tradeTimeStart 交易开始时间
     * @param tradeTimeEnd   交易结束时间
     * @param respSys        银行维度
     * @param tranCode       交易类型维度
     * @param page           通用分页封装类
     * @return pageResult
     * @throws SnowException
     */
    public PageResult queryTradeNum(String tradeTimeStart, String tradeTimeEnd, String respSys, String tranCode, Page page) {
        DBDao dao = DBDaos.newInstance();
        PageResult pageResult = dao.selectList("com.ruimin.ifs.mng.rql.TradeNumQuery.queryTradeNum",
                RqlParam.map().set("tradeTimeStart", tradeTimeStart == null || tradeTimeStart.equals("") ? "" : tradeTimeStart)
                        .set("tradeTimeEnd", tradeTimeEnd == null || tradeTimeEnd.equals("") ? "" : tradeTimeEnd)
                        .set("respSys", respSys == null || respSys.equals("") ? "" : respSys)
                        .set("tranCode", tranCode == null || tranCode.equals("") ? "" : tranCode)
                , page);

        List<?> queryResult = pageResult.getQueryResult();
        if (queryResult != null || queryResult.size() > 0) {
            for (Object o : queryResult) {
                TradeQueryVo temp = (TradeQueryVo) o;
                //获取总量
                String totalNum1 = temp.getTotalNum();
                if (totalNum1 != null && totalNum1.equals("0")) {
                    totalNum1 = "1";
                }
                BigDecimal totalNum = new BigDecimal(totalNum1);
                //获取成功量
                BigDecimal successNum = new BigDecimal(temp.getSuccessNum() + "");
                //获取失败量
                BigDecimal failureNum = new BigDecimal(temp.getFailureNum() + "");

                //计算成功率

                temp.setSuccessPer((successNum.multiply(new BigDecimal("100"))).divide(totalNum, 2, BigDecimal.ROUND_HALF_UP).toString().concat("%"));
                //计算失败率
                temp.setFailurePer((failureNum.multiply(new BigDecimal("100"))).divide(totalNum, 2, BigDecimal.ROUND_HALF_UP).toString().concat("%"));
            }
        }
        return pageResult;
    }
}
