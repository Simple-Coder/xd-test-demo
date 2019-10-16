package com.ruimin.ifs.mng.process.service;

import com.alibaba.fastjson.JSONObject;
import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.service.SnowService;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.login.process.bean.MsgQueryBean;
import com.ruimin.ifs.login.process.bean.SweepMsgQueryBean;
import com.ruimin.ifs.util.RedisUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class HighChartsService extends SnowService {
    public static HighChartsService getInstance() throws SnowException {
        return ContextUtil.getSingleService(HighChartsService.class);
    }

    /**
     * 获取首页监控数据
     * @param current_time
     * @param tradeEndTime
     * @param bankSelect
     * @return
     * @throws SnowException
     */
    public List<Object> queryListAll(String current_time,String tradeEndTime,String bankSelect) throws SnowException{
        DBDao dao = DBDaos.newInstance();
        return dao.selectList("com.ruimin.ifs.mng.rql.TradeNumQuery.getHighchartsData",
                RqlParam.map().set("tradeTimeStart",current_time)
                        .set("tradeEndTime",tradeEndTime)
                        .set("bankSelect", bankSelect));
    }

    public String getTrancode(String uuid){
        DBDao dao = DBDaos.newInstance();
        return ""+dao.selectOne("com.ruimin.ifs.mng.rql.TradeNumQuery.queryTrancodeByuuid",RqlParam.map().set("uuid",uuid));
    }

    public String getTradeName(String tradeCode){
        DBDao dao = DBDaos.newInstance();
        return ""+dao.selectOne("com.ruimin.ifs.mng.rql.tradecfg.queryTranName",RqlParam.map().set("tradeCode",tradeCode));
    }

    /**
     * 获取首页归集监控的数据
     * @return
     * @throws SnowException
     */
    public List<SweepMsgQueryBean> querySweepMonitorData() throws SnowException{
        //TODO:需要sql？？？？
        // 从redis中获取当日支付信息
        // 0000:TodayPaymentList
        String key = "0000:TodaySweepList";
        RedisUtil redisUtil = RedisUtil.getInstance();
        List<SweepMsgQueryBean> msgQueryBeanList = new ArrayList<>();
        if(redisUtil.new Keys().exists(key))
        {
            String a = redisUtil.new Strings().get(key).replace("\\", "");
            String list = a.substring(a.indexOf('"') + 1, a.lastIndexOf('"'));
            msgQueryBeanList = JSONObject.parseArray(list, SweepMsgQueryBean.class);
            if (msgQueryBeanList.size()==0)
            {
                return this.buildMsgQueryBeanList();
            }
            return msgQueryBeanList;
        }
        return this.buildMsgQueryBeanList();
    }

    private List<SweepMsgQueryBean> buildMsgQueryBeanList() {
        List<SweepMsgQueryBean> msgQueryBeanList = new ArrayList<>();
        SweepMsgQueryBean sweepMsgQueryBean = new SweepMsgQueryBean();
        sweepMsgQueryBean.setShortname("系统暂无数据（请检查任务！）");
        sweepMsgQueryBean.setBankcode("0");
        sweepMsgQueryBean.setSweepSuccess("0");
        sweepMsgQueryBean.setSweepOther("0");
        sweepMsgQueryBean.setSweepFailure("0");
        sweepMsgQueryBean.setEntrySuccess("0");
        sweepMsgQueryBean.setEntryFailure("0");
        msgQueryBeanList.add(sweepMsgQueryBean);
        return msgQueryBeanList;
    }

}
