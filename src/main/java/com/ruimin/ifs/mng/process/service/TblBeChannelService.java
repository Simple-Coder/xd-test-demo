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
import com.ruimin.ifs.po.TblBeChannel;

/**
 *
 * @project be-delivery-admin
 * @description  接入系统管理服务类类。
 * @version 1.0.0
 * @errorcode
 *           错误码: 错误描述 <br>
 *
 * @author
 *        2019-07-15 liuxj Create 1.0 <br>
 *
 * @copyright     ©2018-2019   上海睿民，版权所有。
 */
@Service
public class TblBeChannelService extends SnowService {


    /**
     *  获取 接入系统管理服务类
     * @return
     * @throws SnowException
     */
    public static synchronized TblBeChannelService getInstance() throws SnowException {
        return ContextUtil.getSingleService(TblBeChannelService.class);
    }

    /**
     *  根据 查询条件获取 接入系统列表
     * @param queryChannelCode 接入系统代码
     * @param queryChannelName 接入系统名称
     * @param page  通用分页封装类
     * @return   pageResult
     * @throws SnowException
     */
    public PageResult queryList(String queryChannelCode, String queryChannelName, Page page) throws SnowException{
        DBDao dao = DBDaos.newInstance();
        return dao.selectList("com.ruimin.ifs.mng.rql.sysmng.queryTblBeChannel",
                RqlParam.map().set("channelCode", queryChannelCode==null?"":"%"+queryChannelCode.toUpperCase()+"%")
                        .set("channelName", queryChannelName==null?"":"%"+queryChannelName.toUpperCase()+"%"),
                page);
    }

    /**
     *  更新接入系统管理信息
     * @param tblBeChannel
     * @throws SnowException
     */
    public void updateTblBeChannel(TblBeChannel tblBeChannel) throws SnowException{
        DBDao dao = DBDaos.newInstance();
        dao.update(tblBeChannel);
    }

    /**
     *  新增接入系统管理
     * @param tblBeChannel
     * @throws SnowException
     */
    public void saveTblBeChannel(TblBeChannel tblBeChannel) throws SnowException{
        DBDao dao = DBDaos.newInstance();
        dao.insert(tblBeChannel);

    }

    /**
     * 根据主键uuid删除 接入系统管理信息
     * @param uuid
     * @throws SnowException
     */
    public void deleteTblBeChannel(String  uuid) throws SnowException{
        DBDao dao = DBDaos.newInstance();
        dao.delete(TblBeChannel.class,uuid);
    }
}
