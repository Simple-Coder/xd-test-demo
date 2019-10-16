package com.ruimin.ifs.mng.comp;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.core.util.DateUtil;
import com.ruimin.ifs.core.util.constant.SnowEnum;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.mng.process.service.BeReturnCodeService;
import com.ruimin.ifs.po.TblBeReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

//ActionResource注解表示该类是个组件资源包
@SnowDoc(desc = "银企平台返回码")
@ActionResource
@Slf4j
public class BeReturnCodeAction extends SnowAction {// 继承SnowAction

    /**
     * 银企平台返回码查询
     */
    // Action注解表示一个组件，查询类不需要事务
    @Action
    @SnowDoc(desc = "银企平台返回码查询")
    public PageResult queryBeReturnCode(QueryParamBean queryBean) throws SnowException {

        log.info("银企平台返回码查询开始");

        // 获取查询参数，分页信息已经封装在QueryParamBean对象中了
        String queryBeReturnCode = queryBean.getParameter("queryBeReturnCode");
        // 还可以使用 getParameter(String key,String defaultValue)来设置空值的默认值
        String queryReturnCodeType = queryBean.getParameter("queryReturnCodeType");

        log.info("银企平台返回码查询，传入参数queryBeReturnCode：" + queryBeReturnCode + "；queryReturnCodeType:" + queryReturnCodeType);

        // 调用服务层
        PageResult result = BeReturnCodeService.getInstance().queryReturnCodeList(queryBeReturnCode, queryReturnCodeType, queryBean.getPage());
        if (result != null && result.getTotalCount() > 0) {
            List<TblBeReturnCode> list = (List<TblBeReturnCode>) result.getQueryResult();
            for (TblBeReturnCode returnCode : list) {
                returnCode.setCreateDate(StringUtils.isEmpty(returnCode.getCreateDate()) ? "" : DateUtil.convertStringToTimeString(returnCode.getCreateDate()));
                returnCode.setLastUpdateTime(StringUtils.isEmpty(returnCode.getLastUpdateTime()) ? "" : DateUtil.convertStringToTimeString(returnCode.getLastUpdateTime()));
            }
        }
        log.info("银企平台返回码查询结束");

        // 返回结果集
        return result;
    }

    /**
     * 银企平台返回码新增
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "银企平台返回码新增")
    public void addBeReturnCode(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("银企平台返回码新增开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("beReturnCodes");

        log.info("银企平台返回码新增，传入参数reqBean：" + reqBean.getTotalList());

        // 实体类
        TblBeReturnCode tblBeBankReturnCode = new TblBeReturnCode();
        // 将传入参数赋值给实体类
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeBankReturnCode);
        }
        // 参数校验
        if (StringUtils.isEmpty(tblBeBankReturnCode.getBeReturnCode()) || tblBeBankReturnCode.getBeReturnCode().length() > 20) {
            log.info("银企平台返回码新增，银企平台返回码：" + tblBeBankReturnCode.getBeReturnCode() + "为空或长度超过20位");
            SnowExceptionUtil.throwWarnException("银企平台返回码不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBankReturnCode.getReturnCodeDesc()) || tblBeBankReturnCode.getReturnCodeDesc().length() > 100) {
            log.info("银企平台返回码新增，银企平台返回码描述：" + tblBeBankReturnCode.getReturnCodeDesc() + "为空或长度超过100位");
            SnowExceptionUtil.throwWarnException("银企平台返回码描述不能为空且长度不能超过100位");
        }
        if (StringUtils.isEmpty(tblBeBankReturnCode.getStatus()) || tblBeBankReturnCode.getStatus().length() > 1) {
            log.info("银企平台返回码新增，状态：" + tblBeBankReturnCode.getStatus() + "为空或长度不能超过1位");
            SnowExceptionUtil.throwWarnException("状态不能为空且长度不能超过1位");
        }
        // 检查是否已经存在
        int countCode = BeReturnCodeService.getInstance().queryBeReturnCode(tblBeBankReturnCode.getBeReturnCode());
        if (countCode > 0) {
            log.info("银企平台返回码新增，银企平台返回码：" + tblBeBankReturnCode.getBeReturnCode() + " ,返回码信息已存在");
            SnowExceptionUtil.throwWarnException("该银企平台返回码已存在");
        }

        BeReturnCodeService.getInstance().insertBeReturnCode(tblBeBankReturnCode);

        log.info("银企平台返回码新增结束");

    }

    /**
     * 银企平台返回码修改
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "银企平台返回码修改")
    public void updateBeReturnCode(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("银企平台返回码修改开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("beReturnCodes");

        log.info("银企平台返回码修改，传入参数reqBean：" + reqBean.getTotalList());

        // 实体类
        TblBeReturnCode tblBeBankReturnCode = new TblBeReturnCode();
        // 将传入参数赋值给实体类
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeBankReturnCode);
        }
        // 参数校验
        if (StringUtils.isEmpty(tblBeBankReturnCode.getBeReturnCode()) || tblBeBankReturnCode.getBeReturnCode().length() > 20) {
            log.info("银企平台返回码修改，银企平台返回码：" + tblBeBankReturnCode.getBeReturnCode() + "为空或长度超过20位");
            SnowExceptionUtil.throwWarnException("银企平台返回码不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBankReturnCode.getReturnCodeDesc()) || tblBeBankReturnCode.getReturnCodeDesc().length() > 100) {
            log.info("银企平台返回码修改，银企平台返回码描述：" + tblBeBankReturnCode.getReturnCodeDesc() + "为空或长度超过100位");
            SnowExceptionUtil.throwWarnException("银企平台返回码描述不能为空且长度不能超过100位");
        }
        if (StringUtils.isEmpty(tblBeBankReturnCode.getStatus()) || tblBeBankReturnCode.getStatus().length() > 1) {
            log.info("银企平台返回码修改，状态：" + tblBeBankReturnCode.getStatus() + "为空或长度不能超过1位");
            SnowExceptionUtil.throwWarnException("状态不能为空且长度不能超过1位");
        }
        // 检查是否已经存在
        TblBeReturnCode beBankReturnCode = BeReturnCodeService.getInstance().queryBeReturnCodeById(tblBeBankReturnCode.getUuid());
        if (!beBankReturnCode.getBeReturnCode().equals(tblBeBankReturnCode.getBeReturnCode())) {
            int countCode = BeReturnCodeService.getInstance().queryBeReturnCode(tblBeBankReturnCode.getBeReturnCode());
            if (countCode > 0) {
                log.info("银企平台返回码修改，银企平台返回码：" + tblBeBankReturnCode.getBeReturnCode() + " ,返回码信息已存在");
                SnowExceptionUtil.throwWarnException("该银企平台返回码已存在");
            }
        }

        // 执行sql
        BeReturnCodeService.getInstance().updateBeReturnCode(tblBeBankReturnCode);

        // 删除redis中交易管理的信息
        //be:tradeDefinition:$tradeCode
        /*String key = "be:tradeDefinition:".concat(tblBeTradeDef.getTradeCode());
        RedisUtil redisUtil = RedisUtil.getInstance();
        redisUtil.new Keys().del(key);*/

        log.info("交易定义管理修改结束");

    }

    /**
     * 银企平台返回码删除
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "银企平台返回码删除")
    public void deleteBeReturnCode(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("银企平台返回码开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("beReturnCodes");

        log.info("银企平台返回码删除，传入参数reqBean：" + reqBean.getTotalList());

        // 银企平台表实体类
        TblBeReturnCode tblBeBankReturnCode = new TblBeReturnCode();
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeBankReturnCode);
        }

        BeReturnCodeService.getInstance().deleteBeReturnCode(tblBeBankReturnCode);

        // 删除redis中交易管理的信息
        //be:tradeDefinition:$tradeCode
        /*String key = "be:tradeDefinition:".concat(tblBeTradeDef.getTradeCode());
        RedisUtil redisUtil = RedisUtil.getInstance();
        redisUtil.new Keys().del(key);*/

        log.info("银企平台返回码结束");
    }
}
