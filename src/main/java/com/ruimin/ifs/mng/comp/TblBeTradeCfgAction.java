package com.ruimin.ifs.mng.comp;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.core.util.DateUtil;
import com.ruimin.ifs.core.util.constant.SnowEnum;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.mng.process.service.TblBeTradeCfgService;
import com.ruimin.ifs.po.TblBeTradeCfg;
import com.ruimin.ifs.util.ObjectUtil;
import com.ruimin.ifs.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ruimin.ifs.gov.util.StringUtils.*;

/**
 * @author 2019-07-17 liuxj Create 1.0 <br>
 * @version 1.0.0
 * @project be-delivery-admin
 * @description 交易代码配置 action服务类。
 * @errorcode 错误码: 错误描述 <br>
 * @copyright ©2018-2019   上海睿民，版权所有。
 */

@SnowDoc(desc = "交易代码配置")
@ActionResource
@Slf4j
public class TblBeTradeCfgAction extends SnowAction {

    /**
     * 根据查询条件获取交易代码配置信息
     *
     * @param queryBean 前端页面查询条件封账bean
     * @return
     * @throws SnowException
     */
    @Action
    @SnowDoc(desc = "交易代码配置信息查询")
    public PageResult queryAll(QueryParamBean queryBean) throws SnowException {

        log.info("交易管理查询开始");

        // 获取传入参数
        String qBankCode = queryBean.getParameter("qBankCode");
        String queryTradeName = queryBean.getParameter("queryTradeName");

        log.info("查询条件是： qBankCode {},queryTradeName {}", qBankCode, queryTradeName);

        // 调用服务层
        PageResult result = TblBeTradeCfgService.getInstance().queryList(qBankCode, queryTradeName, queryBean.getPage());

        log.info("交易管理查询结束");

        //返回查询信息
        return result;
    }

    /**
     * 更新交易代码配置信息
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "交易代码配置信息修改")
    public void update(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("交易管理修改开始");

        // 或缺传入参数
        UpdateRequestBean reqBean = updateMap.get("TblBeTradeCfg");

        log.info("交易管理修改，传入参数reqBean：" + reqBean.getTotalList());

        // 申明实体类和Service
        TblBeTradeCfg tradeCfg = new TblBeTradeCfg();
        TblBeTradeCfgService sps = ContextUtil.getSingleService(TblBeTradeCfgService.class);

        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tradeCfg);
        }

        //数据校验
        String tradeCode = tradeCfg.getTradeCode();
        if (isEmpty(tradeCode)) {
            SnowExceptionUtil.throwErrorException("E003", "更新数据，交易代码不能为空!");
        }

        //根据主键获取修改交易配置信息
        TblBeTradeCfg tblBeTradeCfgInfo = TblBeTradeCfgService.getInstance().queryTradeCfgById(tradeCfg.getUuid());

        // 检查交易码是否已经存在
        if (!tblBeTradeCfgInfo.getTradeCode().equals(tradeCfg.getTradeCode())) {
            int countCode = TblBeTradeCfgService.getInstance().queryCountTradeCode(tradeCfg.getTradeCode());
            if (countCode > 0) {
                log.info("交易配置信息修改，交易码：" + tradeCfg.getTradeCode() + "，该交易配置信息已存在");
                SnowExceptionUtil.throwWarnException("WEB_E0017", "该交易码已存在");
            }
        }

        tradeCfg.setLastUpdateTime(DateUtil.get14Date());
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        tradeCfg.setLastUpdateUser(sessionUserInfo.getTlrno());
        log.debug("待修改的记录 的 uuid 为 {}" + tradeCfg.getUuid());

        // 执行sql
        sps.updateTblBeTradeCfg(tradeCfg);

        // 删除redis中交易管理的信息
        //be:TradeCfg:$bankCode:$tradeCode
        String key = "be:TradeCfg:".concat(tradeCfg.getBankCode()).concat(":").concat(tradeCode);
        RedisUtil redisUtil = RedisUtil.getInstance();
        redisUtil.new Keys().del(key);

        log.info("交易管理修改结束");

    }

    /**
     * 新增交易代码配置信息
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "交易代码配置信息新增")
    public void save(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("交易管理新增开始");

        // 获取传入参数
        UpdateRequestBean reqBean = updateMap.get("TblBeTradeCfg");

        log.info("交易管理新增，传入参数reqBean：" + reqBean.getTotalList());

        // 申明实体类
        TblBeTradeCfg tradeCfg = new TblBeTradeCfg();

        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tradeCfg);
        }

        //数据校验
        String tradeCode = tradeCfg.getTradeCode();
        String bankCode = tradeCfg.getBankCode();
        if (isEmpty(tradeCode)) {
            SnowExceptionUtil.throwErrorException("E001", "交易代码不能为空!");
        }

        if (isEmpty(bankCode)) {
            SnowExceptionUtil.throwErrorException("E005", "银行名称不能为空!");
        }

        // 检查交易码是否已经存在
        int countCode = TblBeTradeCfgService.getInstance().queryCountTradeCode(tradeCfg.getTradeCode());
        if (countCode > 0) {
            log.info("交易配置信息修改，交易码：" + tradeCfg.getTradeCode() + "，该交易配置信息已存在");
            SnowExceptionUtil.throwErrorException("WEB_E0017", "该交易码已存在");
        }
        tradeCfg.setUuid(ContextUtil.getUUID());
        tradeCfg.setCreateDate(DateUtil.get14Date());
        tradeCfg.setLastUpdateTime(DateUtil.get14Date());
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        tradeCfg.setLastUpdateUser(sessionUserInfo.getTlrno());

        // 执行sql
        TblBeTradeCfgService.getInstance().saveTblBeTradeCfg(tradeCfg);

        log.info("交易管理新增结束");
    }

    /**
     * 根据uuid 删除交易代码配置信息
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "交易代码配置信息删除")
    public void delete(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("交易管理删除开始");

        // 获取传入参数
        UpdateRequestBean reqBean = updateMap.get("TblBeTradeCfg");
        // 申明实体类
        TblBeTradeCfg tradeCfg = new TblBeTradeCfg();
        while (reqBean.hasNext())
        {
            DataObjectUtils.map2bean(reqBean.next(), tradeCfg);
        }
        String uuid = tradeCfg.getUuid();
        log.debug("待删除的uuid 为{}", uuid);

        //数据校验
        if (isEmpty(uuid)) {
            SnowExceptionUtil.throwErrorException("E002", "删除交易代码信息uuid不能为空!");
        }

        // 执行sql
        TblBeTradeCfgService.getInstance().deleteTblBeTradeCfg(uuid);

        // 删除redis中交易管理的信息
        //be:TradeCfg:$bankCode:$tradeCode
        String key = "be:TradeCfg:".concat(tradeCfg.getBankCode()).concat(":").concat(tradeCfg.getTradeCode());
        RedisUtil redisUtil = RedisUtil.getInstance();
        redisUtil.new Keys().del(key);
        log.info("交易管理删除结束");
    }

    /**
     *
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "交易配置输入重跑查询日期")
    public void setRetryQueryDate(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        RedisUtil redisUtil = RedisUtil.getInstance();
        UpdateRequestBean reqBean = updateMap.get("TblBeTradeCfg");
        // 申明实体类
        TblBeTradeCfg tradeCfg = new TblBeTradeCfg();
        while (reqBean.hasNext())
        {
            DataObjectUtils.map2bean(reqBean.next(), tradeCfg);
        }
        //获取开始和结束日期
        List<Map<String, String>> params = reqBean.getTotalList();
        if (params==null||params.size()<=0)
        {
            SnowExceptionUtil.throwErrorException("日期获取异常【list】!");
        }
        Map<String, String> paramsMap = params.get(0);
        if (paramsMap==null||paramsMap.size()<=0)
        {
            SnowExceptionUtil.throwErrorException("日期获取异常【map】!");
        }
        String beginDate = paramsMap.get("tradeOpenDateRetry");
        String endDate = paramsMap.get("tradeCloseDateRetry");
        if(beginDate==null||"".equals(beginDate))
        {
            SnowExceptionUtil.throwErrorException("开始日期不能为空或格式不正确！");
        }
        if(endDate==null||"".equals(endDate))
        {
            SnowExceptionUtil.throwErrorException("结束日期不能为空或格式不正确！");
        }
        //判断结束日期是否大于开始日期
        if(!checkDate(beginDate, endDate,tradeCfg.getTradeCode()))
        {
            SnowExceptionUtil.throwErrorException("结束日期不能小于开始日期！");
        }

        //根据交易码判断是否放入redis缓存（非历史余额查询或账户历史明细查询不放缓存）
        String tradeCode = tradeCfg.getTradeCode();
        if ("0004".equals(tradeCode)||"0011".equals(tradeCode))
        {
            // 开始日期key为$ bankCode:tranCode:tradeOpenTime
            String startTimekey = tradeCfg.getBankCode().concat(":").concat(tradeCfg.getTradeCode()).concat(":").concat("tradeOpenDateRetry");

            // 结束日期key为$ bankCode:tranCode:tradeCloseTime
            String endTimekey = tradeCfg.getBankCode().concat(":").concat(tradeCfg.getTradeCode()).concat(":").concat("tradeCloseDateRetry");

            //开始日期、结束日期放入redis
            redisUtil.new Lists().lpush(startTimekey, ObjectUtil.Serialize(beginDate));
            redisUtil.new Lists().lpush(endTimekey,ObjectUtil.Serialize(endDate));

            log.info("开始日期已放redis,key:【{}】与value【{}】",startTimekey,beginDate);
            log.info("结束日期已放redis,key:【{}】与value【{}】",startTimekey,endDate);
        }
        else
        {
            log.debug("当前交易：【{}】，无需日期",tradeCode);
        }
    }

    /**
     * 判断结束日期是否大于开始日期
     * @param beginDate
     * @param endDate
     * @return
     * @throws SnowException
     */
    private boolean checkDate(String beginDate, String endDate,String tranCode) throws SnowException {
        //交易码不为0004账户历史明细查询或历史余额查询的话，直接通过校验
        if (!"0004".equals(tranCode)&&!"0011".equals(tranCode)){
            return true;
        }
        Date date = new Date();
        Date parseBeginDate = com.ruimin.ifs.util.DateUtil.parse(beginDate, com.ruimin.ifs.util.DateUtil.PATTERN_YYYYMMDD);
        Date parseEndDate = com.ruimin.ifs.util.DateUtil.parse(endDate, com.ruimin.ifs.util.DateUtil.PATTERN_YYYYMMDD);
        if (parseBeginDate.compareTo(parseEndDate) > 0) {
            return false;
        }
        if (parseBeginDate.compareTo(date) > 0 || parseEndDate.compareTo(date) > 0) {
            SnowExceptionUtil.throwErrorException("日期选择不在当前系统日期之内！");
            return false;
        }
        if ("0011".equals(tranCode))
        {
            //历史余额查询的情况下，选择的日期必须为当前日期-1
            //获取历史余额可以查询的最后日期：
            String lastQueryDateStr = com.ruimin.ifs.util.DateUtil.getDateBeforeYYYYMMDD(date, -1);
            Date lastQueryDateFormat = com.ruimin.ifs.util.DateUtil.parse(lastQueryDateStr, com.ruimin.ifs.util.DateUtil.PATTERN_YYYYMMDD);
            if (parseBeginDate.compareTo(lastQueryDateFormat) > 0 || parseEndDate.compareTo(lastQueryDateFormat) > 0)
            {
                //选择日期必须小于最后查询日期
                SnowExceptionUtil.throwErrorException("历史余额查询最后业务日必须为系统日期的前1天！");
                return false;
            }
        }
        return true;
    }
}
