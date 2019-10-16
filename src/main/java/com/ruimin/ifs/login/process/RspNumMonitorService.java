package com.ruimin.ifs.login.process;

import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.iface.service.SnowService;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.framework.process.query.Page;

//Service注解表示该类为服务，必须有此注解才能使用ContextUtil获取
@Service
public class RspNumMonitorService extends SnowService {// 继承自SnowService

    /**
     * @return 服务实例，单例
     * @throws SnowException
     */
    public static RspNumMonitorService getInstance() throws SnowException {
        // 根据class获取服务实例
        return ContextUtil.getSingleService(RspNumMonitorService.class);
    }

    public PageResult queryRspNumInfo(String req_time,String resp_time,Page page)
            throws SnowException
    {
        DBDao dao = DBDaos.newInstance();
        PageResult pageResult = dao.selectList("com.ruimin.ifs.login.rql.RspCodeMonitor.queryRspCode",
                RqlParam.map().set("req_time", req_time == null || req_time.equals("") ? "" : req_time)
                        .set("resp_time", resp_time == null || resp_time.equals("") ? "" : resp_time)
                , page);
        return pageResult;
    }
}
