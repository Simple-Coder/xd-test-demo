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
import com.ruimin.ifs.mng.process.service.BankInterService;
import com.ruimin.ifs.po.TblBeBankInter;
import com.ruimin.ifs.util.XmlPurifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import java.util.List;
import java.util.Map;

//ActionResource注解表示该类是个组件资源包
@SnowDoc(desc = "银行接口")
@ActionResource
@Slf4j
public class BankInterAction extends SnowAction {// 继承SnowAction

    /**
     * 根据请求中的银行代码，银行交易吗，接口开放时间，接口关闭时间，查询相对应的银行接口，若请求信息为空，则查询所有
     *
     * @param queryBean QueryParamBean查询类的请求参数
     * @return 查询结果，返回类型可以为List/PageResult（包含分页信息）/JavaBean对象
     * @throws SnowException
     */
    // Action注解表示一个组件，查询类不需要事务
    @Action
    @SnowDoc(desc = "银行接口查询")
    public PageResult queryBankInter(QueryParamBean queryBean) throws SnowException {

        log.info("银行接口查询开始");

        // 获取查询参数，分页信息已经封装在QueryParamBean对象中了
        String qBankCode = queryBean.getParameter("qBankCode");
        // 还可以使用 getParameter(String key,String defaultValue)来设置空值的默认值
        String qInterCode = queryBean.getParameter("qInterCode");
        String qOpenTime = queryBean.getParameter("qOpenTime");
        String qCloseTime = queryBean.getParameter("qCloseTime");

        log.info("银行接口查询，传入参数qBankCode：" + qBankCode + "；qInterCode:" + qInterCode + "；qOpenTime:" + qOpenTime + "；qCloseTime:" + qCloseTime);

        // 调用服务层
        PageResult result = BankInterService.getInstance().queryBankInterList(qBankCode, qInterCode, qOpenTime, qCloseTime, queryBean.getPage());
        if (result != null && result.getTotalCount() > 0) {
            List<TblBeBankInter> list = (List<TblBeBankInter>) result.getQueryResult();
            for (TblBeBankInter bankInter : list) {
                bankInter.setCreateTime(StringUtils.isEmpty(bankInter.getCreateTime()) ? "" : DateUtil.convertStringToTimeString(bankInter.getCreateTime()));
                bankInter.setLastUpdateTime(StringUtils.isEmpty(bankInter.getCreateTime()) ? "" : DateUtil.convertStringToTimeString(bankInter.getLastUpdateTime()));
            }
        }
        log.info("银行接口查询结束");
        List<?> queryResult = result.getQueryResult();
        for (Object temp : queryResult)
        {
            TblBeBankInter temp1 = (TblBeBankInter) temp;
            String requestTemplate = temp1.getRequestTemplate();
            temp1.setRequestTemplate(XmlPurifyUtil.purifyXml(requestTemplate));
        }
        // 返回结果集
        return result;
    }

    /**
     * 银行信息新增
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "银行接口新增")
    public void insertBankInter(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("银行接口新增开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("bankInter");

        log.info("银行接口新增，传入参数reqBean：" + reqBean.getTotalList());

        // 接入银行接口表实体类
        TblBeBankInter tblBeBankInter = new TblBeBankInter();
        // 将传入参数赋值给银行实体类
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeBankInter);
        }
        // 参数校验
        if (StringUtils.isEmpty(tblBeBankInter.getBankCode()) || tblBeBankInter.getBankCode().length() > 10) {
            log.info("银行接口新增，银行代码：" + tblBeBankInter.getBankCode() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "银行代码不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBankInter.getInterCode()) || tblBeBankInter.getInterCode().length() > 10) {
            log.info("银行接口新增，银行交易码：" + tblBeBankInter.getInterCode() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "银行交易码不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBankInter.getInterCode()) || tblBeBankInter.getInterCode().length() > 10) {
            log.info("银行接口新增，接口名称：" + tblBeBankInter.getInterCode() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "接口名称不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBankInter.getOpenTime()) || tblBeBankInter.getOpenTime().length() > 6) {
            log.info("银行接口新增，接口开放时间：" + tblBeBankInter.getOpenTime() + "为空或长度不能超过6位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "接口开放时间不能为空且长度不能超过6位");
        }
        if (StringUtils.isEmpty(tblBeBankInter.getCloseTime()) || tblBeBankInter.getCloseTime().length() > 6) {
            log.info("银行接口新增，接口关闭时间：" + tblBeBankInter.getCloseTime() + "为空或长度不能超过6位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "接口关闭时间不能为空且长度不能超过6位");
        }

        // 根据银行代码和接口功能码查询银行接口是否存在
        int countCode = BankInterService.getInstance().queryBankInterIsExist(tblBeBankInter.getBankCode(), tblBeBankInter.getInterCode());
        if (countCode > 0) {
            log.info("银行接口新增，银行代码：" + tblBeBankInter.getBankCode() + "，接口功能码" + tblBeBankInter.getInterCode() + "，该银行接口已存在");
            SnowExceptionUtil.throwWarnException("WEB_E0003", "该银行接口已存在");
        }

        BankInterService.getInstance().insertBankInter(tblBeBankInter);

        log.info("银行接口新增结束");

    }

    /**
     * 银行接口修改
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "银行接口修改")
    public void updateBankInter(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("银行接口修改开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("bankInter");

        log.info("银行接口修改，传入参数reqBean：" + reqBean.getTotalList());
        //"requestTemplate" -> "<?xml version="1.0" encoding="gbk"?>
        //
        //<BEDC>
        //  <Message>
        //    <commHead>
        //      <tranCode>${tranCode}</tranCode>
        //      <cifMaster>${cifMaster}</cifMaster>
        //      <entSeqNo>${entSeqNo}</entSeqNo>                    
        //      <tranDate>${tranDate}</tranDate>                    
        //      <tranTime>${tranTime}</tranTime>                    
        //      <retCode>${retCode}</retCode>
        //      <entUserId>${entUserId}</entUserId>
        //      <password>${password}</password>
        //    </commHead>
        //    <Body>
        //      <account>${account}</account>
        //      <beginDate>${beginDate}</beginDate>
        //      <endDate>${endDate}</endDate>
        //      <queryType>${queryType}</queryType>
        //      <beginRecTranDate>${beginRecTranDate}</beginRecTranDate>
        //      <beginRecTranCode>${beginRecTranCode}</beginRecTranCode>
        //      <beginRecTranSeq>${beginRecTranSeq}</beginRecTranSeq>
        //      <queryNumber>${queryNumber}</queryNumber>
        //      <reserve1>${reserve1}</reserve1>
        //      <reserve2>${reserve2}</reserve2>
        //    </Body>
        //  </Message>
        //</BEDC>"
        List<Map<String, String>> totalList = reqBean.getTotalList();
        String requestTemplate="";
        if (totalList!=null&&totalList.size()>0)
        {
            Map<String, String> temp = totalList.get(0);
            requestTemplate=temp.get("requestTemplate").replaceAll("\n","");
        }
        // 接入银行接口表实体类
        TblBeBankInter tblBeBankInter = new TblBeBankInter();

        // 将传入参数赋值给银行实体类
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeBankInter);
        }
        tblBeBankInter.setRequestTemplate(requestTemplate);
        // 参数校验
        if (StringUtils.isEmpty(tblBeBankInter.getBankCode()) || tblBeBankInter.getBankCode().length() > 10) {
            log.info("银行接口新增，银行代码：" + tblBeBankInter.getBankCode() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "银行代码不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBankInter.getInterCode()) || tblBeBankInter.getInterCode().length() > 10) {
            log.info("银行接口新增，银行交易码：" + tblBeBankInter.getInterCode() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "银行交易码不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBankInter.getInterCode()) || tblBeBankInter.getInterCode().length() > 10) {
            log.info("银行接口新增，接口名称：" + tblBeBankInter.getInterCode() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "接口名称不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBankInter.getOpenTime()) || tblBeBankInter.getOpenTime().length() > 6) {
            log.info("银行接口新增，接口开放时间：" + tblBeBankInter.getOpenTime() + "为空或长度不能超过6位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "接口开放时间不能为空且长度不能超过6位");
        }
        if (StringUtils.isEmpty(tblBeBankInter.getCloseTime()) || tblBeBankInter.getCloseTime().length() > 6) {
            log.info("银行接口新增，接口关闭时间：" + tblBeBankInter.getCloseTime() + "为空或长度不能超过6位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "接口关闭时间不能为空且长度不能超过6位");
        }

        // 判断是否修改银行代码或接口功能码
        TblBeBankInter bankInterInfo = BankInterService.getInstance().queryBankInterById(tblBeBankInter.getUuid());
        if (!tblBeBankInter.getBankCode().equals(bankInterInfo.getBankCode()) || !tblBeBankInter.getInterCode().equals(bankInterInfo.getInterCode())) {
            // 根据银行代码和接口功能码查询银行接口是否存在
            int countCode = BankInterService.getInstance().queryBankInterIsExist(tblBeBankInter.getBankCode(), tblBeBankInter.getInterCode());
            if (countCode > 0) {
                log.info("银行接口修改，银行代码：" + tblBeBankInter.getBankCode() + "，接口功能码" + tblBeBankInter.getInterCode() + "，该银行接口已存在");
                SnowExceptionUtil.throwWarnException("WEB_E0003", "该银行接口已存在");
            }
        }

        // 执行sql
        BankInterService.getInstance().updateBankInter(tblBeBankInter);

        log.info("银行接口修改结束");

    }

    /**
     * 银行接口删除
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "银行接口删除")
    public void deleteBankInter(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("银行接口删除开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("bankInter");

        log.info("银行接口删除，传入参数reqBean：" + reqBean.getTotalList());

        // 接入银行接口表实体类
        TblBeBankInter tblBeBankInter = new TblBeBankInter();
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeBankInter);
        }

        BankInterService.getInstance().deleteBankInter(tblBeBankInter);

        log.info("银行接口删除结束");
    }
}
