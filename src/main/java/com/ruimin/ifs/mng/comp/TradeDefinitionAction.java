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
import com.ruimin.ifs.mng.process.service.TradeDefinitionService;
import com.ruimin.ifs.po.TblBeTradeDef;
import com.ruimin.ifs.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import java.util.List;
import java.util.Map;

//ActionResource注解表示该类是个组件资源包
@SnowDoc(desc = "交易定义管理")
@ActionResource
@Slf4j
public class TradeDefinitionAction extends SnowAction {// 继承SnowAction

    /**
     * 交易定义管理查询
     */
    // Action注解表示一个组件，查询类不需要事务
    @Action
    @SnowDoc(desc = "交易定义管理查询")
    public PageResult queryTradeDefinition(QueryParamBean queryBean) throws SnowException {

        log.info("交易定义管理查询开始");

        // 获取查询参数，分页信息已经封装在QueryParamBean对象中了
        String queryTradeName = queryBean.getParameter("queryTradeName");
        // 还可以使用 getParameter(String key,String defaultValue)来设置空值的默认值
        String queryTradeCode = queryBean.getParameter("queryTradeCode");

        log.info("交易定义管理查询，传入参数queryTradeName：" + queryTradeName + "；queryTradeCode:" + queryTradeCode);

        // 调用服务层
        PageResult result = TradeDefinitionService.getInstance().queryTradeDefinitionList(queryTradeName, queryTradeCode, queryBean.getPage());
        if (result != null && result.getTotalCount() > 0) {
            List<TblBeTradeDef> list = (List<TblBeTradeDef>) result.getQueryResult();
            for (TblBeTradeDef tradeDef : list) {
                tradeDef.setCreateDate(StringUtils.isEmpty(tradeDef.getCreateDate()) ? "" : DateUtil.convertStringToTimeString(tradeDef.getCreateDate()));
                tradeDef.setLastUpdateTime(StringUtils.isEmpty(tradeDef.getLastUpdateTime()) ? "" : DateUtil.convertStringToTimeString(tradeDef.getLastUpdateTime()));
            }
        }
        log.info("交易定义管理查询结束");

        // 返回结果集
        return result;
    }

    /**
     * 交易定义管理新增
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "交易定义管理新增")
    public void addTradeDefinition(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("交易定义管理增开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("tradeDefinition");

        log.info("交易定义管理新增，传入参数reqBean：" + reqBean.getTotalList());

        // 实体类
        TblBeTradeDef tblBeTradeDef = new TblBeTradeDef();
        // 将传入参数赋值给实体类
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeTradeDef);
        }
        // 参数校验
        if (StringUtils.isEmpty(tblBeTradeDef.getTradeName()) || tblBeTradeDef.getTradeName().length() > 30) {
            log.info("交易定义管理新增，交易名称：" + tblBeTradeDef.getTradeName() + "为空或长度超过30位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "交易名称不能为空且长度不能超过30位");
        }
        if (StringUtils.isEmpty(tblBeTradeDef.getTradeCode()) || tblBeTradeDef.getTradeCode().length() > 10) {
            log.info("交易定义管理新增，交易代码：" + tblBeTradeDef.getTradeCode() + "为空或长度超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0002", "交易代码不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeTradeDef.getTradeOpenTime()) || tblBeTradeDef.getTradeOpenTime().length() > 6) {
            log.info("交易定义管理新增，交易开始时间：" + tblBeTradeDef.getTradeOpenTime() + "为空或长度超过6位");
            SnowExceptionUtil.throwWarnException("WEB_E0003", "银行联行号不能为空且长度不能超过6位");
        }
        if (StringUtils.isEmpty(tblBeTradeDef.getTradeCloseTime()) || tblBeTradeDef.getTradeCloseTime().length() > 20) {
            log.info("交易定义管理新增，交易结束时间：" + tblBeTradeDef.getTradeCloseTime() + "为空或长度不能超过6位");
            SnowExceptionUtil.throwWarnException("WEB_E0004", "交易结束时间不能为空且长度不能超过6位");
        }
        // 检查交易代码和交易名称是否已经存在
        int countCode = TradeDefinitionService.getInstance().queryTradeDefinition(tblBeTradeDef.getTradeName(), tblBeTradeDef.getTradeCode());
        if (countCode > 0) {
            log.info("交易定义管理新增，交易名称：" + tblBeTradeDef.getTradeName() + "，交易码：" + tblBeTradeDef.getTradeCode() + " ,该银行信息已存在");
            SnowExceptionUtil.throwWarnException("WEB_E0017", "该交易已存在");
        }

        TradeDefinitionService.getInstance().insertTradeDefinition(tblBeTradeDef);

        log.info("交易定义管理新增结束");

    }

    /**
     * 交易定义管理修改
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "交易定义管理修改")
    public void updateTradeDefinition(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("交易定义管理修改开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("tradeDefinition");

        log.info("交易定义管理修改，传入参数reqBean：" + reqBean.getTotalList());

        // 实体类
        TblBeTradeDef tblBeTradeDef = new TblBeTradeDef();

        // 将传入参数赋值给实体类
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeTradeDef);
        }
        // 参数校验
        if (StringUtils.isEmpty(tblBeTradeDef.getTradeName()) || tblBeTradeDef.getTradeName().length() > 30) {
            log.info("交易定义管理新增，交易名称：" + tblBeTradeDef.getTradeName() + "为空或长度超过30位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "交易名称不能为空且长度不能超过30位");
        }
        if (StringUtils.isEmpty(tblBeTradeDef.getTradeCode()) || tblBeTradeDef.getTradeCode().length() > 10) {
            log.info("交易定义管理新增，交易代码：" + tblBeTradeDef.getTradeCode() + "为空或长度超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0002", "交易代码不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeTradeDef.getTradeOpenTime()) || tblBeTradeDef.getTradeOpenTime().length() > 6) {
            log.info("交易定义管理新增，交易开始时间：" + tblBeTradeDef.getTradeOpenTime() + "为空或长度超过6位");
            SnowExceptionUtil.throwWarnException("WEB_E0003", "银行联行号不能为空且长度不能超过6位");
        }
        if (StringUtils.isEmpty(tblBeTradeDef.getTradeCloseTime()) || tblBeTradeDef.getTradeCloseTime().length() > 20) {
            log.info("交易定义管理新增，交易结束时间：" + tblBeTradeDef.getTradeCloseTime() + "为空或长度不能超过6位");
            SnowExceptionUtil.throwWarnException("WEB_E0004", "交易结束时间不能为空且长度不能超过6位");
        }
        // 检查交易代码和交易名称是否已经存在
        TblBeTradeDef tradeDef = TradeDefinitionService.getInstance().queryTradeDefinitionById(tblBeTradeDef.getUuid());
        if(!tradeDef.getTradeCode().equals(tblBeTradeDef.getTradeCode())||!tradeDef.getTradeName().equals(tblBeTradeDef.getTradeName())){
            int countCode = TradeDefinitionService.getInstance().queryTradeDefinition(tblBeTradeDef.getTradeName(), tblBeTradeDef.getTradeCode());
            if (countCode > 0) {
                log.info("交易定义管理新增，交易名称：" + tblBeTradeDef.getTradeName() + "，交易码：" + tblBeTradeDef.getTradeCode() + " ,该银行信息已存在");
                SnowExceptionUtil.throwWarnException("WEB_E0005", "该交易已存在");
            }
        }

        // 执行sql
        TradeDefinitionService.getInstance().updateTradeDefinition(tblBeTradeDef);

        // 删除redis中交易管理的信息
        //be:tradeDefinition:$tradeCode
        String key = "be:tradeDefinition:".concat(tblBeTradeDef.getTradeCode());
        RedisUtil redisUtil = RedisUtil.getInstance();
        redisUtil.new Keys().del(key);

        log.info("交易定义管理修改结束");

    }

    /**
     * 交易定义管理删除
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "交易定义管理删除")
    public void deleteTradeDefinition(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("交易定义管理删除开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("tradeDefinition");

        log.info("交易定义管理删除，传入参数reqBean：" + reqBean.getTotalList());

        // 接入银行表实体类
        TblBeTradeDef tblBeTradeDef = new TblBeTradeDef();
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeTradeDef);
        }

        TradeDefinitionService.getInstance().deleteTradeDefinition(tblBeTradeDef);

        // 删除redis中交易管理的信息
        //be:tradeDefinition:$tradeCode
        String key = "be:tradeDefinition:".concat(tblBeTradeDef.getTradeCode());
        RedisUtil redisUtil = RedisUtil.getInstance();
        redisUtil.new Keys().del(key);

        log.info("交易定义管理删除结束");
    }
}
