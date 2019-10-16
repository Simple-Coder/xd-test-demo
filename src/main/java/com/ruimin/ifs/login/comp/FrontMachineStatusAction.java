package com.ruimin.ifs.login.comp;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.login.process.FrontMachineStatusService;
import com.ruimin.ifs.login.process.bean.FrontMachine;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

//ActionResource注解表示该类是个组件资源包
@SnowDoc(desc = "前置机状态信息")
@ActionResource
@Slf4j
public class FrontMachineStatusAction extends SnowAction {// 继承SnowAction

    // Action注解表示一个组件，查询类不需要事务
    @Action
    @SnowDoc(desc = "前置机状态信息查询")
    public PageResult queryFrontMachineStatus(QueryParamBean queryBean) throws SnowException {

        log.info("前置机状态信息开始");

        // 调用服务层
        PageResult result = FrontMachineStatusService.getInstance().queryFrontMachineStatus(queryBean.getPage());
        if (result != null && result.getTotalCount() > 0) {
            List<FrontMachine> list = (List<FrontMachine>) result.getQueryResult();
            for (FrontMachine frontMachine : list) {
                String ip = frontMachine.getBankIpHost();
                String host = ip.substring(0, ip.indexOf(":"));
                int port = Integer.parseInt(ip.substring(ip.indexOf(":")+1));
                if(this.isHostConnectable(host,port)){
                    frontMachine.setStatus("连接成功");
                }else {
                    frontMachine.setStatus("连接失败");
                }
            }
        }
        log.info("前置机状态信息结束");

        // 返回结果集
        return result;
    }

    public boolean isHostConnectable(String host, int port) {
        Socket connect = new Socket();
        try {
            connect.connect(new InetSocketAddress(host, port), 100);//建立连接
            boolean res = connect.isConnected();//通过现有方法查看连通状态
            return res;//true为连通
        } catch (IOException e) {
            return false;//当连不通时，直接抛异常，异常捕获即可
        } finally {
            try {
                connect.close();
            } catch (IOException e) {
                return false;
            }
        }
    }
}
