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
import com.ruimin.ifs.po.TblBeBankReturnCode;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

//Service注解表示该类为服务，必须有此注解才能使用ContextUtil获取
@Service
public class SMSService extends SnowService {// 继承自SnowService

    /**
     * @return 服务实例，单例
     * @throws SnowException
     */
    public static SMSService getInstance() throws SnowException {
        // 根据class获取服务实例
        return ContextUtil.getSingleService(SMSService.class);
    }

    /**
     * 短信模糊查询
     *
     * @param querySmsType      短信类型
     * @param queryStatus       短信状态
     * @param page              分页信息
     * @return 交易定义管理查询结果
     * @throws SnowException
     */
    public PageResult querySMSList(String querySmsType, String queryStatus, Page page)
            throws SnowException {
        // 获取一个DAO对象，它会当前默认的数据源
        DBDao dao = DBDaos.newInstance();
        // 调用查询方法，key为RQL配置文件的包名+<select>节点的id
        return dao.selectList(
                "com.ruimin.ifs.mng.rql.SMS.querySMSList",
                RqlParam.map().set("querySmsType", StringUtils.isEmpty(querySmsType) ? "" : querySmsType).set("queryStatus", StringUtils.isEmpty(queryStatus) ? "" : queryStatus), page);
    }

    /**
     * 接入银行返回码查询（新增时校验）
     *
     * @param bankCode      银行代码
     * @param beReturnCode 银企平台返回码
     * @param returnCode   银行返回码
     * @return 交易定义管理查询结果
     * @throws SnowException
     */
    public int queryBeReturnCode(String bankCode, String beReturnCode, String returnCode) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return (Integer) dao.selectOne("com.ruimin.ifs.mng.rql.BeReturnCode.queryBeReturnCode", RqlParam.map().set("bankCode", bankCode).set("beReturnCode", beReturnCode).set("returnCode", returnCode));
    }

    /**
     * 新增接入银行返回码
     *
     * @param tblBeBankReturnCode
     * @throws SnowException
     */
    public void insertBeReturnCode(TblBeBankReturnCode tblBeBankReturnCode) throws SnowException {
        // 补全接入银行返回码实体类tblBeBankReturnCode
        // 生成32位uuid
        tblBeBankReturnCode.setUuid(UUID.randomUUID().toString().replace("-", ""));
        // 将系统时间生成yyyyMMddHHmmss字符串
        tblBeBankReturnCode.setCreateDate(DateUtil.getDateAndTm14());
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        tblBeBankReturnCode.setLastUpdateUser(sessionUserInfo.getTlrno());
        tblBeBankReturnCode.setLastUpdateTime(DateUtil.getDateAndTm14());
        DBDao dao = DBDaos.newInstance();
        dao.insert(tblBeBankReturnCode);
    }

    /**
     * 修改接入银行返回码
     *
     * @param tblBeBankReturnCode
     * @throws SnowException
     */
    public void updateBeReturnCode(TblBeBankReturnCode tblBeBankReturnCode) throws SnowException {
        // yyyy-MM-dd HH:mm:ss变成yyyyMMddHHmmss
        tblBeBankReturnCode.setCreateDate(tblBeBankReturnCode.getCreateDate().replace("-", "").replace(":", "").replace(" ", ""));
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        tblBeBankReturnCode.setLastUpdateUser(sessionUserInfo.getTlrno());
        // 将系统时间生成yyyyMMddHHmmss字符串
        tblBeBankReturnCode.setLastUpdateTime(DateUtil.getDateAndTm14());
        DBDao dao = DBDaos.newInstance();
        dao.update(tblBeBankReturnCode);
    }

    /**
     * 删除接入银行返回码
     *
     * @param tblBeBankReturnCode
     * @throws SnowException
     */
    public void deleteBeReturnCode(TblBeBankReturnCode tblBeBankReturnCode) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        dao.delete(tblBeBankReturnCode);
    }

    /**
     * 根据主键获取接入银行返回码
     *
     * @param uuid
     * @return
     * @throws SnowException
     */
    public TblBeBankReturnCode queryBeReturnCodeById(String uuid) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return dao.select(TblBeBankReturnCode.class, uuid);
    }
}
