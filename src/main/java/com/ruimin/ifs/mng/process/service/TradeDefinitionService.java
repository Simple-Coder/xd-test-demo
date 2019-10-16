package com.ruimin.ifs.mng.process.service;

import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.iface.service.SnowService;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DateUtil;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.process.query.Page;
import com.ruimin.ifs.mng.process.bean.BankDropDpwnListVo;
import com.ruimin.ifs.po.TblBeBank;
import com.ruimin.ifs.po.TblBeTradeDef;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.UUID;

//Service注解表示该类为服务，必须有此注解才能使用ContextUtil获取
@Service
public class TradeDefinitionService extends SnowService {// 继承自SnowService

    /**
     * @return 服务实例，单例
     * @throws SnowException
     */
    public static TradeDefinitionService getInstance() throws SnowException {
        // 根据class获取服务实例
        return ContextUtil.getSingleService(TradeDefinitionService.class);
    }

    /**
     * 交易定义管理模糊查询
     *
     * @param queryTradeName 交易名称
     * @param queryTradeCode 交易码
     * @param page           分页信息
     * @return 交易定义管理查询结果
     * @throws SnowException
     */
    public PageResult queryTradeDefinitionList(String queryTradeName, String queryTradeCode, Page page)
            throws SnowException {
        // 获取一个DAO对象，它会当前默认的数据源
        DBDao dao = DBDaos.newInstance();
        // 调用查询方法，key为RQL配置文件的包名+<select>节点的id
        return dao.selectList(
                "com.ruimin.ifs.mng.rql.TradeDefinition.queryTradeDefinitionList",
                RqlParam.map().set("queryTradeName", StringUtils.isEmpty(queryTradeName) ? "" : "%" + queryTradeName + "%").set("queryTradeCode", StringUtils.isEmpty(queryTradeCode) ? "" : queryTradeCode), page);
    }

    /**
     * 交易定义管理查询（新增时校验交易名称，交易代码是否已经存在）
     *
     * @param queryTradeName 交易名称
     * @param queryTradeCode 交易码
     * @return 交易定义管理查询结果
     * @throws SnowException
     */
    public int queryTradeDefinition(String queryTradeName, String queryTradeCode) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return (Integer) dao.selectOne("com.ruimin.ifs.mng.rql.TradeDefinition.queryTradeDefinition", RqlParam.map().set("queryTradeName", queryTradeName).set("queryTradeCode", queryTradeCode));
    }

    /**
     * 新增交易定义管理
     *
     * @param tblBeTradeDef
     * @throws SnowException
     */
    public void insertTradeDefinition(TblBeTradeDef tblBeTradeDef) throws SnowException {
        // 补全银行信息实体类TblBeBank
        // 生成32位uuid
        tblBeTradeDef.setUuid(UUID.randomUUID().toString().replace("-", ""));
        // 将系统时间生成yyyyMMddHHmmss字符串
        tblBeTradeDef.setCreateDate(DateUtil.getDateAndTm14());
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        tblBeTradeDef.setLastUpdateUser(sessionUserInfo.getTlrno());
        tblBeTradeDef.setLastUpdateTime(DateUtil.getDateAndTm14());
        DBDao dao = DBDaos.newInstance();
        dao.insert(tblBeTradeDef);
    }

    /**
     * 修改交易定义管理
     *
     * @param tblBeTradeDef
     * @throws SnowException
     */
    public void updateTradeDefinition(TblBeTradeDef tblBeTradeDef) throws SnowException {
        // yyyy-MM-dd HH:mm:ss变成yyyyMMddHHmmss
        tblBeTradeDef.setCreateDate(tblBeTradeDef.getCreateDate().replace("-", "").replace(":", "").replace(" ", ""));
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        tblBeTradeDef.setLastUpdateUser(sessionUserInfo.getTlrno());
        // 将系统时间生成yyyyMMddHHmmss字符串
        tblBeTradeDef.setLastUpdateTime(DateUtil.getDateAndTm14());
        DBDao dao = DBDaos.newInstance();
        dao.update(tblBeTradeDef);
    }

    /**
     * 删除交易定义管理
     *
     * @param tblBeTradeDef
     * @throws SnowException
     */
    public void deleteTradeDefinition(TblBeTradeDef tblBeTradeDef) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        dao.delete(tblBeTradeDef);
    }

    /**
     * 根据主键获取交易定义管理
     *
     * @param uuid
     * @return
     * @throws SnowException
     */
    public TblBeTradeDef queryTradeDefinitionById(String uuid) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return dao.select(TblBeTradeDef.class, uuid);
    }
}
