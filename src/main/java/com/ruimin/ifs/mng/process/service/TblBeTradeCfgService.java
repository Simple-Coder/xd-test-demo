package com.ruimin.ifs.mng.process.service;

import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.iface.service.SnowService;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.framework.process.query.Page;
import com.ruimin.ifs.po.TblBeTradeCfg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author 2019-07-17 liuxj Create 1.0 <br>
 * @version 1.0.0
 * @project be-delivery-admin
 * @description 交易代码配置服务类。
 * @errorcode 错误码: 错误描述 <br>
 * @copyright ©2018-2019   上海睿民，版权所有。
 */
@Service
@Slf4j
public class TblBeTradeCfgService extends SnowService {


    /**
     * 获取 交易代码配置信息服务类
     *
     * @return
     * @throws SnowException
     */
    public static synchronized TblBeTradeCfgService getInstance() throws SnowException {
        return ContextUtil.getSingleService(TblBeTradeCfgService.class);
    }

    /**
     * 根据 查询条件获取 交易代码配置信息
     *
     * @param qBankCode      银行名称
     * @param queryTradeName 交易名称
     * @param page           通用分页封装类
     * @return pageResult
     * @throws SnowException
     */
    public PageResult queryList(String qBankCode, String queryTradeName, Page page) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return dao.selectList("com.ruimin.ifs.mng.rql.tradecfg.queryTradeConfList",
                RqlParam.map().set("qBankCode", qBankCode == null ? "" : "" + qBankCode.toUpperCase() + "")
                        .set("tradeName", queryTradeName == null ? "" : "%" + queryTradeName.toUpperCase() + "%"), page);
    }

    /**
     * 更新交易代码配置信息
     *
     * @param tblBeTradeCfg
     * @throws SnowException
     */
    public void updateTblBeTradeCfg(TblBeTradeCfg tblBeTradeCfg) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        dao.update(tblBeTradeCfg);
    }

    /**
     * 新增交易代码配置信息
     *
     * @param tblBeTradeCfg
     * @throws SnowException
     */
    public void saveTblBeTradeCfg(TblBeTradeCfg tblBeTradeCfg) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        dao.insert(tblBeTradeCfg);

    }

    /**
     * 根据主键uuid删除 交易代码配置信息
     *
     * @param uuid
     * @throws SnowException
     */
    public void deleteTblBeTradeCfg(String uuid) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        dao.delete(TblBeTradeCfg.class, uuid);
    }

    /**
     * 根据交易码查询该交易总条数
     *
     * @param tradeCode 交易码
     * @return 总条数
     * @throws SnowException
     */
    public int queryCountTradeCode(String tradeCode) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return (Integer) dao.selectOne("com.ruimin.ifs.mng.rql.tradecfg.queryCountTradeCode", RqlParam.map().set("tradeCode", tradeCode));
    }

    /**
     * 根据主键获取交易配置信息
     *
     * @param uuid
     * @return
     * @throws SnowException
     */
    public TblBeTradeCfg queryTradeCfgById(String uuid) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return dao.select(TblBeTradeCfg.class, uuid);
    }

}
