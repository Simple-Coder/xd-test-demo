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
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.UUID;

//Service注解表示该类为服务，必须有此注解才能使用ContextUtil获取
@Service
public class BankInformationService extends SnowService {// 继承自SnowService

    /**
     * @return 服务实例，单例
     * @throws SnowException
     */
    public static BankInformationService getInstance() throws SnowException {
        // 根据class获取服务实例
        return ContextUtil.getSingleService(BankInformationService.class);
    }

    /**
     * 银行信息模糊查询
     *
     * @param qbankNumber 银行联行号
     * @param qBankName 银行名称
     * @param page      分页信息
     * @return 银行信息查询结果
     * @throws SnowException
     */
    public PageResult queryBankInformationList(String qbankNumber, String qBankName, Page page)
            throws SnowException {
        // 获取一个DAO对象，它会当前默认的数据源
        DBDao dao = DBDaos.newInstance();
        // 调用查询方法，key为RQL配置文件的包名+<select>节点的id
        return dao.selectList(
                "com.ruimin.ifs.mng.rql.BankInfomation.queryBankInformationList",
                RqlParam.map().set("qbankNumber", StringUtils.isEmpty(qbankNumber) ? "" : qbankNumber).set("qBankName", StringUtils.isEmpty(qBankName) ? "" : "%" + qBankName + "%"), page);
    }

    /**
     * 根据银行代码查询银行信息总条数
     *
     * @param bankCode 银行代码
     * @return 总条数
     * @throws SnowException
     */
    public int queryBankInformationCountCode(String bankCode) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return (Integer) dao.selectOne("com.ruimin.ifs.mng.rql.BankInfomation.queryBankInformationCountCode", RqlParam.map().set("bankCode", bankCode));
    }

    /**
     * 根据银行名称查询银行信息总条数
     *
     * @param bankName 银行名称
     * @return 总条数
     * @throws SnowException
     */
    public int queryBankInformationCountName(String bankName) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return (Integer) dao.selectOne("com.ruimin.ifs.mng.rql.BankInfomation.queryBankInformationCountName", RqlParam.map().set("bankName", bankName));
    }

    /**
     * 银行信息新增数据
     *
     * @param tblBeBank
     * @throws SnowException
     */
    public void insertBankInformation(TblBeBank tblBeBank) throws SnowException {
        // 补全银行信息实体类TblBeBank
        // 生成32位uuid
        tblBeBank.setUuid(UUID.randomUUID().toString().replace("-", ""));
        // 将系统时间生成yyyyMMddHHmmss字符串
        tblBeBank.setCreateDate(DateUtil.getDateAndTm14());
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        tblBeBank.setLastUpdateUser(sessionUserInfo.getTlrno());
        tblBeBank.setLastUpdateTime(DateUtil.getDateAndTm14());
        DBDao dao = DBDaos.newInstance();
        dao.insert(tblBeBank);
    }

    /**
     * 银行信息修改数据
     *
     * @param tblBeBank
     * @throws SnowException
     */
    public void updateBankInformation(TblBeBank tblBeBank) throws SnowException {
        // yyyy-MM-dd HH:mm:ss变成yyyyMMddHHmmss
        tblBeBank.setCreateDate(tblBeBank.getCreateDate().replace("-", "").replace(":", "").replace(" ", ""));
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        tblBeBank.setLastUpdateUser(sessionUserInfo.getTlrno());
        // 将系统时间生成yyyyMMddHHmmss字符串
        tblBeBank.setLastUpdateTime(DateUtil.getDateAndTm14());
        DBDao dao = DBDaos.newInstance();
        dao.update(tblBeBank);
    }

    /**
     * 银行信息删除数据
     *
     * @param tblBeBank
     * @throws SnowException
     */
    public void deleteBankInformation(TblBeBank tblBeBank) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        dao.delete(tblBeBank);
    }

    /**
     * 银行信息下拉列表查询
     *
     * @return
     * @throws SnowException
     */
    public List<BankDropDpwnListVo> bankDropDownList() throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return dao.queryAll("select bank_code,bank_name from tbl_be_bank", BankDropDpwnListVo.class);
    }

    /**
     * 根据bankCode获取银行名称bankName
     *
     * @param bankCode
     * @return
     * @throws SnowException
     */
    public TblBeBank queryBankByBankCode(String bankCode) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        TblBeBank tblBeBank = (TblBeBank) dao.selectOne("com.ruimin.ifs.mng.rql.BankInfomation.queryBankByBankCode", RqlParam.map().set("bankCode", bankCode));
        return tblBeBank;
    }

    /**
     * 根据主键获取银行信息
     *
     * @param uuid
     * @return
     * @throws SnowException
     */
    public TblBeBank queryBankById(String uuid) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return dao.select(TblBeBank.class, uuid);
    }

    public TblBeBank queryBankByUuid(String uuid) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        TblBeBank tblBeBank = (TblBeBank) dao.selectOne("com.ruimin.ifs.mng.rql.BankInfomation.queryBankByBankCode", RqlParam.map().set("uuid", uuid));
        return tblBeBank;
    }
}
