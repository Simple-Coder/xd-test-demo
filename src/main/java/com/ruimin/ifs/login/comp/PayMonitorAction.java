package com.ruimin.ifs.login.comp;

import com.alibaba.fastjson.JSONObject;
import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.login.process.bean.MsgQueryBean;
import com.ruimin.ifs.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//ActionResource注解表示该类是个组件资源包
@SnowDoc(desc = "支付监控信息")
@ActionResource
@Slf4j
public class PayMonitorAction extends SnowAction {// 继承SnowAction

    // Action注解表示一个组件，查询类不需要事务
    @Action
    @SnowDoc(desc = "支付监控信息查询")
    public List<MsgQueryBean> queryPayMonitorAction(QueryParamBean queryBean) throws SnowException {

        log.info("支付监控信息开始");

        // 从redis中获取当日支付信息
        // 0000:TodayPaymentList
        String key = "0000:TodayPaymentList";
        RedisUtil redisUtil = RedisUtil.getInstance();
        List<MsgQueryBean> msgQueryBeanList = new ArrayList<>();
        if(redisUtil.new Keys().exists(key)){
            String a = redisUtil.new Strings().get(key).replace("\\", "");
            String list = a.substring(a.indexOf('"') + 1, a.lastIndexOf('"'));
            msgQueryBeanList = JSONObject.parseArray(list, MsgQueryBean.class);
        }
        log.info("前置机状态信息结束");

        // 返回结果集
        return msgQueryBeanList;
    }
}
