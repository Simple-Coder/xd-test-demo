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
import com.ruimin.ifs.po.TblBeReturnCode;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

//Service注解表示该类为服务，必须有此注解才能使用ContextUtil获取
@Service
public class BeReturnCodeService extends SnowService {// 继承自SnowService

    /**
     * @return 服务实例，单例
     * @throws SnowException
     */
    public static BeReturnCodeService getInstance() throws SnowException {
        // 根据class获取服务实例
        return ContextUtil.getSingleService(BeReturnCodeService.class);
    }

    /**
     * 银企平台返回码模糊查询
     *
     * @param queryReturnCodeType     银企平台返回码类型
     * @param queryBeReturnCode 银企平台返回码
     * @param page              分页信息
     * @return 交易定义管理查询结果
     * @throws SnowException
     */
    public PageResult queryReturnCodeList(String queryBeReturnCode, String queryReturnCodeType, Page page)
            throws SnowException {
        // 获取一个DAO对象，它会当前默认的数据源
        DBDao dao = DBDaos.newInstance();
        // 调用查询方法，key为RQL配置文件的包名+<select>节点的id
        return dao.selectList(
                "com.ruimin.ifs.mng.rql.BeReturnCode.queryReturnCodeList",
                RqlParam.map().set("queryBeReturnCode", StringUtils.isEmpty(queryBeReturnCode) ? "" : queryBeReturnCode).set("queryReturnCodeType", StringUtils.isEmpty(queryReturnCodeType) ? "" : queryReturnCodeType), page);
    }

    /**
     * 银企平台返回码查询（新增时校验）
     *
     * @param beReturnCode 银企平台返回码
     * @return 交易定义管理查询结果
     * @throws SnowException
     */
    public int queryBeReturnCode(String beReturnCode) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return (Integer) dao.selectOne("com.ruimin.ifs.mng.rql.BeReturnCode.queryBeReturnCode", RqlParam.map().set("beReturnCode", beReturnCode));
    }

    /**
     * 新增银企平台返回码
     *
     * @param TblBeReturnCode
     * @throws SnowException
     */
    public void insertBeReturnCode(TblBeReturnCode TblBeReturnCode) throws SnowException {
        // 补全银企平台返回码实体类TblBeReturnCode
        // 生成32位uuid
        TblBeReturnCode.setUuid(UUID.randomUUID().toString().replace("-", ""));
        // 将系统时间生成yyyyMMddHHmmss字符串
        TblBeReturnCode.setCreateDate(DateUtil.getDateAndTm14());
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        TblBeReturnCode.setLastUpdateUser(sessionUserInfo.getTlrno());
        TblBeReturnCode.setLastUpdateTime(DateUtil.getDateAndTm14());
        DBDao dao = DBDaos.newInstance();
        dao.insert(TblBeReturnCode);
    }

    /**
     * 修改银企平台返回码
     *
     * @param TblBeReturnCode
     * @throws SnowException
     */
    public void updateBeReturnCode(TblBeReturnCode TblBeReturnCode) throws SnowException {
        // yyyy-MM-dd HH:mm:ss变成yyyyMMddHHmmss
        TblBeReturnCode.setCreateDate(TblBeReturnCode.getCreateDate().replace("-", "").replace(":", "").replace(" ", ""));
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        TblBeReturnCode.setLastUpdateUser(sessionUserInfo.getTlrno());
        // 将系统时间生成yyyyMMddHHmmss字符串
        TblBeReturnCode.setLastUpdateTime(DateUtil.getDateAndTm14());
        DBDao dao = DBDaos.newInstance();
        dao.update(TblBeReturnCode);
    }

    /**
     * 删除银企平台返回码
     *
     * @param TblBeReturnCode
     * @throws SnowException
     */
    public void deleteBeReturnCode(TblBeReturnCode TblBeReturnCode) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        dao.delete(TblBeReturnCode);
    }

    /**
     * 根据主键获取银企平台返回码
     *
     * @param uuid
     * @return
     * @throws SnowException
     */
    public TblBeReturnCode queryBeReturnCodeById(String uuid) throws SnowException {
        DBDao dao = DBDaos.newInstance();
        return dao.select(TblBeReturnCode.class, uuid);
    }
}
