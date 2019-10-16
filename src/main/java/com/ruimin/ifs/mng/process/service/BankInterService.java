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
import com.ruimin.ifs.po.TblBeBankInter;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

//Service注解表示该类为服务，必须有此注解才能使用ContextUtil获取
@Service
public class BankInterService extends SnowService {// 继承自SnowService

    /**
     * @return 服务实例，单例
     * @throws SnowException
     */
    public static BankInterService getInstance() throws SnowException {
        // 根据class获取服务实例
        return ContextUtil.getSingleService(BankInterService.class);
    }

    /**
     * 银行接口模糊查询
     *
     * @param qBankCode     银行代码
     * @param qInterCode    银行交易码
     * @param qOpenTime     接口开放时间
     * @param qCloseTime    接口关闭时间
     * @param page          分页信息
     * @return 银行接口查询结果
     * @throws SnowException
     */
    public PageResult queryBankInterList(String qBankCode, String qInterCode, String qOpenTime, String qCloseTime, Page page)
            throws SnowException {
        // 获取一个DAO对象，它会当前默认的数据源
        DBDao dao = DBDaos.newInstance();
        // 调用查询方法，key为RQL配置文件的包名+<select>节点的id
        return dao.selectList(
                "com.ruimin.ifs.mng.rql.BankInter.queryBankInterList",
                RqlParam.map().set("qBankCode", StringUtils.isEmpty(qBankCode) ? "" : qBankCode).set("qInterCode", StringUtils.isEmpty(qInterCode) ? "" : qInterCode)
                        .set("qOpenTime", StringUtils.isEmpty(qOpenTime) ? "" : qOpenTime).set("qCloseTime", StringUtils.isEmpty(qCloseTime) ? "" : qCloseTime), page);
    }

    /**
     * 根据银行代码和接口功能码查询银行接口是否存在
     *
     * @param bankCode  银行代码
     * @param interCode 接口功能码
     * @return 总条数
     * @throws SnowException
     */
    public int queryBankInterIsExist(String bankCode, String interCode) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return (Integer) dao.selectOne("com.ruimin.ifs.mng.rql.BankInter.queryBankInterIsExist", RqlParam.map().set("bankCode", bankCode).set("interCode", interCode));
    }

    /**
     * 银行接口新增数据
     *
     * @param tblBeBankInter
     * @throws SnowException
     */
    public void insertBankInter(TblBeBankInter tblBeBankInter) throws SnowException {
        // 补全银行接口实体类tblBeBankInter
        // 生成32位uuid
        tblBeBankInter.setUuid(UUID.randomUUID().toString().replace("-", ""));
        // 将系统时间生成yyyyMMddHHmmss字符串
        tblBeBankInter.setCreateTime(DateUtil.getDateAndTm14());
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        tblBeBankInter.setLastUpdateUser(sessionUserInfo.getTlrno());
        tblBeBankInter.setLastUpdateTime(DateUtil.getDateAndTm14());
        DBDao dao = DBDaos.newInstance();
        dao.insert(tblBeBankInter);
    }

    /**
     * 银行接口修改数据
     *
     * @param tblBeBankInter
     * @throws SnowException
     */
    public void updateBankInter(TblBeBankInter tblBeBankInter) throws SnowException {
        // yyyy-MM-dd HH:mm:ss变成yyyyMMddHHmmss
        tblBeBankInter.setCreateTime(tblBeBankInter.getCreateTime().replace("-", "").replace(":", "").replace(" ", ""));
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        tblBeBankInter.setLastUpdateUser(sessionUserInfo.getTlrno());
        // 将系统时间生成yyyyMMddHHmmss字符串
        tblBeBankInter.setLastUpdateTime(DateUtil.getDateAndTm14());
        DBDao dao = DBDaos.newInstance();
        dao.update(tblBeBankInter);
    }

    /**
     * 银行接口删除数据
     *
     * @param tblBeBankInter
     * @throws SnowException
     */
    public void deleteBankInter(TblBeBankInter tblBeBankInter) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        dao.delete(tblBeBankInter);
    }

    /**
     * 根据主键获取银行信息
     * @param uuid
     * @return
     * @throws SnowException
     */
    public TblBeBankInter queryBankInterById(String uuid) throws SnowException{
        DBDao dao = DBDaos.newInstance();
        return dao.select(TblBeBankInter.class, uuid);
    }
}
