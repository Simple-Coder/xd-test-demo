package com.ruimin.ifs.login.comp;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.login.process.FrontMachineStatusService;
import com.ruimin.ifs.login.process.RspTimeActuatorService;
import com.ruimin.ifs.login.process.bean.FrontMachine;
import com.ruimin.ifs.login.process.bean.RspTimeBean;
import com.ruimin.ifs.util.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.List;

//ActionResource注解表示该类是个组件资源包
@SnowDoc(desc = "响应时长统计信息")
@ActionResource
@Slf4j
public class RspTimeMonitorAction extends SnowAction {// 继承SnowAction

    // Action注解表示一个组件，查询类不需要事务
    @Action
    @SnowDoc(desc = "响应时长信息查询")
    public PageResult queryRspTimeInfo(QueryParamBean queryBean) throws SnowException {

        log.info("响应时长信息开始");
        //获取当前时间
        Date date = new Date();
        String req_time = DateUtil.getYYYYMMDD(date).concat("000000").concat("000");
        String resp_time = DateUtil.getYYYYMMDD(date).concat("999999").concat("999");
        // 调用服务层
        PageResult result = RspTimeActuatorService.getInstance().queryRspTimeInfo(req_time,resp_time,queryBean.getPage());
        log.info("响应时长信息结束");
        List<?> queryResult = result.getQueryResult();
        for (Object o : queryResult)
        {
            RspTimeBean tempVo =(RspTimeBean)o;
            String avgtime = tempVo.getAvgtime();
            if (avgtime!=null&&avgtime.contains("."))
            {
                tempVo.setAvgtime(avgtime.substring(0,avgtime.indexOf(".")));
            }
        }
        // 返回结果集
        return result;
    }


}
