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
import com.ruimin.ifs.framework.dataset.field.FieldBean;
import com.ruimin.ifs.mng.process.bean.BankDropDpwnListVo;
import com.ruimin.ifs.mng.process.service.BankInformationService;
import com.ruimin.ifs.po.TblBeBank;
import com.ruimin.ifs.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

//ActionResource注解表示该类是个组件资源包
@SnowDoc(desc = "银行信息")
@ActionResource
@Slf4j
public class BankInformationAction extends SnowAction {// 继承SnowAction

    /**
     * 根据请求中的银行代码和银行名称查询相对应的银行信息，若请求信息中银行代码和银行名称为空，则查询所有
     *
     * @param queryBean QueryParamBean查询类的请求参数
     * @return 查询结果，返回类型可以为List/PageResult（包含分页信息）/JavaBean对象
     * @throws SnowException
     */
    // Action注解表示一个组件，查询类不需要事务
    @Action
    @SnowDoc(desc = "银行信息查询")
    public PageResult queryBankInformation(QueryParamBean queryBean) throws SnowException {

        log.info("银行信息查询开始");

        // 获取查询参数，分页信息已经封装在QueryParamBean对象中了
        String qbankNumber = queryBean.getParameter("qbankNumber");
        // 还可以使用 getParameter(String key,String defaultValue)来设置空值的默认值
        String qBankName = queryBean.getParameter("qBankName");

        log.info("银行信息查询，传入参数qbankNumber：" + qbankNumber + "；qBankName:" + qBankName);

        // 调用服务层
        PageResult result = BankInformationService.getInstance().queryBankInformationList(qbankNumber, qBankName, queryBean.getPage());
        if (result != null && result.getTotalCount() > 0) {
            List<TblBeBank> list = (List<TblBeBank>) result.getQueryResult();
            for (TblBeBank bank : list) {
                bank.setCreateDate(StringUtils.isEmpty(bank.getCreateDate()) ? "" : DateUtil.convertStringToTimeString(bank.getCreateDate()));
                bank.setLastUpdateTime(StringUtils.isEmpty(bank.getLastUpdateTime()) ? "" : DateUtil.convertStringToTimeString(bank.getLastUpdateTime()));
            }
        }
        log.info("银行信息查询结束");

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
    @SnowDoc(desc = "银行信息新增")
    public void insertBankInformation(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("银行信息新增开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("bankInformation");

        log.info("银行信息新增，传入参数reqBean：" + reqBean.getTotalList());

        // 接入银行表实体类
        TblBeBank tblBeBank = new TblBeBank();
        // 将传入参数赋值给银行实体类
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeBank);
        }
        // 参数校验
        if (StringUtils.isEmpty(tblBeBank.getBankName()) || tblBeBank.getBankName().length() > 30) {
            log.info("银行信息新增，银行名称：" + tblBeBank.getBankName() + "为空或长度不能超过30位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "银行名称不能为空且长度不能超过30位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankCode()) || tblBeBank.getBankCode().length() > 10) {
            log.info("银行信息新增，行别代码：" + tblBeBank.getBankCode() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0002", "行别代码不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankNumber()) || tblBeBank.getBankNumber().length() > 20) {
            log.info("银行信息新增，银行联行号：" + tblBeBank.getBankNumber() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0003", "银行联行号不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankClientCode()) || tblBeBank.getBankClientCode().length() > 20) {
            log.info("银行信息新增，银行客户号：" + tblBeBank.getBankClientCode() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0004", "银行客户号不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankAccount()) || tblBeBank.getBankAccount().length() > 20) {
            log.info("银行信息新增，银行账号：" + tblBeBank.getBankAccount() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0005", "银行账号不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankInternalAccount()) || tblBeBank.getBankInternalAccount().length() > 20) {
            log.info("银行信息新增，内部账号：" + tblBeBank.getBankInternalAccount() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0006", "内部账号不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankAccountName()) || tblBeBank.getBankAccountName().length() > 100) {
            log.info("银行信息新增，银行户名：" + tblBeBank.getBankAccountName() + "为空或长度不能超过100位");
            SnowExceptionUtil.throwWarnException("WEB_E0007", "银行户名不能为空且长度不能超过100位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankAddress()) || tblBeBank.getBankAddress().length() > 200) {
            log.info("银行信息新增，开户行地址：" + tblBeBank.getBankAddress() + "为空或长度不能超过200位");
            SnowExceptionUtil.throwWarnException("WEB_E0008", "开户行地址不能为空且长度不能超过200位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankStatus()) || tblBeBank.getBankStatus().length() > 10) {
            log.info("银行信息新增，银行状态：" + tblBeBank.getBankStatus() + "为空或长度不能超过1位");
            SnowExceptionUtil.throwWarnException("WEB_E0009", "银行状态不能为空且长度不能超过1位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankSigningDate()) || tblBeBank.getBankSigningDate().length() > 10) {
            log.info("银行信息新增，签约日期：" + tblBeBank.getBankSigningDate() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0010", "签约日期不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankInterfaceAddress()) || tblBeBank.getBankInterfaceAddress().length() > 100) {
            log.info("银行信息新增，接口地址：" + tblBeBank.getBankInterfaceAddress() + "为空或长度不能超过100位");
            SnowExceptionUtil.throwWarnException("WEB_E0011", "接口地址不能为空且长度不能超过100位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankMessageType()) || tblBeBank.getBankMessageType().length() > 10) {
            log.info("银行信息新增，报文类型：" + tblBeBank.getBankMessageType() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0012", "报文类型不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankOperator()) || tblBeBank.getBankOperator().length() > 20) {
            log.info("银行信息新增，操作员：" + tblBeBank.getBankOperator() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0013", "操作员不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankOperatorPwd()) || tblBeBank.getBankOperatorPwd().length() > 20) {
            log.info("银行信息新增，操作员密码：" + tblBeBank.getBankOperatorPwd() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0014", "操作员密码不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankWarningThreshold()) || tblBeBank.getBankWarningThreshold().length() > 10) {
            log.info("银行信息新增，异常告警阈值：" + tblBeBank.getBankWarningThreshold() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0015", "异常告警阈值不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankTerminationThreshold()) || tblBeBank.getBankTerminationThreshold().length() > 10) {
            log.info("银行信息新增，终止服务阈值：" + tblBeBank.getBankTerminationThreshold() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0016", "终止服务阈值不能为空且长度不能超过10位");
        }
        // 检查行别代码和银行名称是否已经存在
        int countCode = BankInformationService.getInstance().queryBankInformationCountCode(tblBeBank.getBankCode());
        if (countCode > 0) {
            log.info("银行信息新增，行别代码：" + tblBeBank.getBankCode() + "，该银行信息已存在");
            SnowExceptionUtil.throwWarnException("WEB_E0017", "该行别代码已存在");
        }
        int countName = BankInformationService.getInstance().queryBankInformationCountName(tblBeBank.getBankName());
        if (countName > 0) {
            log.info("银行信息新增，银行名称：" + tblBeBank.getBankName() + "，该银行信息已存在");
            SnowExceptionUtil.throwWarnException("WEB_E0018", "该银行名称已存在");
        }

        BankInformationService.getInstance().insertBankInformation(tblBeBank);

        log.info("银行信息新增结束");

    }

    /**
     * 银行信息修改
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "银行信息修改")
    public void updateBankInformation(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("银行信息修改开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("bankInformation");

        log.info("银行信息修改，传入参数reqBean：" + reqBean.getTotalList());

        // 接入银行表实体类
        TblBeBank tblBeBank = new TblBeBank();
        // 将传入参数赋值给银行实体类
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeBank);
        }
        // 参数校验
        if (StringUtils.isEmpty(tblBeBank.getBankName()) || tblBeBank.getBankName().length() > 30) {
            log.info("银行信息新增，银行名称：" + tblBeBank.getBankName() + "为空或长度不能超过30位");
            SnowExceptionUtil.throwWarnException("WEB_E0001", "银行名称不能为空且长度不能超过30位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankCode()) || tblBeBank.getBankCode().length() > 10) {
            log.info("银行信息新增，行别代码：" + tblBeBank.getBankCode() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0002", "行别代码不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankNumber()) || tblBeBank.getBankNumber().length() > 20) {
            log.info("银行信息新增，银行联行号：" + tblBeBank.getBankNumber() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0003", "银行联行号不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankClientCode()) || tblBeBank.getBankClientCode().length() > 20) {
            log.info("银行信息新增，银行客户号：" + tblBeBank.getBankClientCode() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0004", "银行客户号不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankAccount()) || tblBeBank.getBankAccount().length() > 20) {
            log.info("银行信息新增，银行账号：" + tblBeBank.getBankAccount() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0005", "银行账号不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankInternalAccount()) || tblBeBank.getBankInternalAccount().length() > 20) {
            log.info("银行信息新增，内部账号：" + tblBeBank.getBankInternalAccount() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0006", "内部账号不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankAccountName()) || tblBeBank.getBankAccountName().length() > 100) {
            log.info("银行信息新增，银行户名：" + tblBeBank.getBankAccountName() + "为空或长度不能超过100位");
            SnowExceptionUtil.throwWarnException("WEB_E0007", "银行户名不能为空且长度不能超过100位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankAddress()) || tblBeBank.getBankAddress().length() > 200) {
            log.info("银行信息新增，开户行地址：" + tblBeBank.getBankAddress() + "为空或长度不能超过200位");
            SnowExceptionUtil.throwWarnException("WEB_E0008", "开户行地址不能为空且长度不能超过200位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankStatus()) || tblBeBank.getBankStatus().length() > 10) {
            log.info("银行信息新增，银行状态：" + tblBeBank.getBankStatus() + "为空或长度不能超过1位");
            SnowExceptionUtil.throwWarnException("WEB_E0009", "银行状态不能为空且长度不能超过1位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankSigningDate()) || tblBeBank.getBankSigningDate().length() > 10) {
            log.info("银行信息新增，签约日期：" + tblBeBank.getBankSigningDate() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0010", "签约日期不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankInterfaceAddress()) || tblBeBank.getBankInterfaceAddress().length() > 100) {
            log.info("银行信息新增，接口地址：" + tblBeBank.getBankInterfaceAddress() + "为空或长度不能超过100位");
            SnowExceptionUtil.throwWarnException("WEB_E0011", "接口地址不能为空且长度不能超过100位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankMessageType()) || tblBeBank.getBankMessageType().length() > 10) {
            log.info("银行信息新增，报文类型：" + tblBeBank.getBankMessageType() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0012", "报文类型不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankOperator()) || tblBeBank.getBankOperator().length() > 20) {
            log.info("银行信息新增，操作员：" + tblBeBank.getBankOperator() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0013", "操作员不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankOperatorPwd()) || tblBeBank.getBankOperatorPwd().length() > 20) {
            log.info("银行信息新增，操作员密码：" + tblBeBank.getBankOperatorPwd() + "为空或长度不能超过20位");
            SnowExceptionUtil.throwWarnException("WEB_E0014", "操作员密码不能为空且长度不能超过20位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankWarningThreshold()) || tblBeBank.getBankWarningThreshold().length() > 10) {
            log.info("银行信息新增，异常告警阈值：" + tblBeBank.getBankWarningThreshold() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0015", "异常告警阈值不能为空且长度不能超过10位");
        }
        if (StringUtils.isEmpty(tblBeBank.getBankTerminationThreshold()) || tblBeBank.getBankTerminationThreshold().length() > 10) {
            log.info("银行信息新增，终止服务阈值：" + tblBeBank.getBankTerminationThreshold() + "为空或长度不能超过10位");
            SnowExceptionUtil.throwWarnException("WEB_E0016", "终止服务阈值不能为空且长度不能超过10位");
        }
        // 检查是否修改银行名称或行别代码
        TblBeBank bankInfo = BankInformationService.getInstance().queryBankById(tblBeBank.getUuid());
        if (!tblBeBank.getBankCode().equals(bankInfo.getBankCode())) {
            // 检查银行代码是否已经存
            int countCode = BankInformationService.getInstance().queryBankInformationCountCode(tblBeBank.getBankCode());
            if (countCode > 0) {
                log.info("银行信息修改，行别代码：" + tblBeBank.getBankCode() + "，该银行信息已存在");
                SnowExceptionUtil.throwWarnException("WEB_E0017", "该行别代码已存在");
            }
        } else if (!tblBeBank.getBankName().equals(bankInfo.getBankName())) {
            // 检查银行名称是否已经存
            int countName = BankInformationService.getInstance().queryBankInformationCountName(tblBeBank.getBankName());
            if (countName > 0) {
                log.info("银行信息修改，银行名称：" + tblBeBank.getBankName() + "，该银行信息已存在");
                SnowExceptionUtil.throwWarnException("WEB_E0018", "该银行名称已存在");
            }
        }

        // 执行sql
        BankInformationService.getInstance().updateBankInformation(tblBeBank);

        // 删除redis中交易管理的信息
        //be:TblBeBank:$bankCode
        String key = "be:TblBeBank:".concat(tblBeBank.getBankCode());
        RedisUtil redisUtil = RedisUtil.getInstance();
        redisUtil.new Keys().del(key);
        log.info("银行信息修改结束");

    }

    /**
     * 银行信息删除
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "银行信息删除")
    public void deleteBankInformation(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("银行信息删除开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("bankInformation");

        log.info("银行信息删除，传入参数reqBean：" + reqBean.getTotalList());

        // 接入银行表实体类
        TblBeBank tblBeBank = new TblBeBank();
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeBank);
        }

        BankInformationService.getInstance().deleteBankInformation(tblBeBank);

        // 删除redis中交易管理的信息
        //be:TblBeBank:$bankCode
        String key = "be:TblBeBank:".concat(tblBeBank.getBankCode());
        RedisUtil redisUtil = RedisUtil.getInstance();
        redisUtil.new Keys().del(key);
        log.info("银行信息删除结束");
    }

    /**
     * 银行信息下拉列表查询
     *
     * @param queryBean QueryParamBean查询类的请求参数
     * @return 查询结果，返回类型可以为List/PageResult（包含分页信息）/JavaBean对象
     * @throws SnowException
     */
    // Action注解表示一个组件，查询类不需要事务
    @Action
    @SnowDoc(desc = "银行信息下拉列表查询")
    public List<BankDropDpwnListVo> bankDropDownList(QueryParamBean queryBean) throws SnowException {

        log.info("银行信息下拉列表查询开始");

        // 调用服务层
        List<BankDropDpwnListVo> list = BankInformationService.getInstance().bankDropDownList();

        log.info("银行信息下拉列表查询结果：" + list.toString());

        log.info("银行信息下拉列表查询结束");

        // 返回结果集
        return list;
    }

    /**
     * 根据bankCode获取银行名称bankName
     *
     * @param bean
     * @param key
     * @param request
     * @return
     */
    public static String getBankName(FieldBean bean, String key, ServletRequest request) throws SnowException {
        if (StringUtils.isNotBlank(key)) {
            TblBeBank bank = BankInformationService.getInstance().queryBankByBankCode(key);
            if (bank != null) {
                return bank.getBankName();
            }
        }
        return key;
    }


    /**
     * 银行状态启用
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "银行状态启用")
    public void startBankStatus(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("银行信息删除开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("bankInformation");

        log.info("银行信息修改，传入参数reqBean：" + reqBean.getTotalList());

        // 接入银行表实体类1
        TblBeBank tblBeBank = new TblBeBank();
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeBank);
        }
        //
        if ("1".equals(tblBeBank.getBankStatus()))
        {
            SnowExceptionUtil.throwErrorException("该银行状态已为启用！");
        }

        //修改状态为启用
        // 执行sql
        tblBeBank.setBankStatus("1");
        BankInformationService.getInstance().updateBankInformation(tblBeBank);


        // 删除redis中交易管理的信息
        //be:TblBeBank:$bankCode
        String key = "be:TblBeBank:".concat(tblBeBank.getBankCode());
        RedisUtil redisUtil = RedisUtil.getInstance();
        redisUtil.new Keys().del(key);
        log.info("银行状态启用结束");
    }

    /**
     * 银行状态停用
     *
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "银行状态启用")
    public void stopBankStatus(Map<String, UpdateRequestBean> updateMap) throws SnowException {

        log.info("银行状态停用开始");

        // 获取请求信息
        UpdateRequestBean reqBean = updateMap.get("bankInformation");

        log.info("银行信息修改，传入参数reqBean：" + reqBean.getTotalList());

        // 接入银行表实体类1
        TblBeBank tblBeBank = new TblBeBank();
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tblBeBank);
        }
        //
        if ("0".equals(tblBeBank.getBankStatus()))
        {
            SnowExceptionUtil.throwErrorException("该银行状态已为停用！");
        }
        //修改银行状态为停用
        // 执行sql
        tblBeBank.setBankStatus("0");
        BankInformationService.getInstance().updateBankInformation(tblBeBank);

        // 删除redis中交易管理的信息
        //be:TblBeBank:$bankCode
        String key = "be:TblBeBank:".concat(tblBeBank.getBankCode());
        RedisUtil redisUtil = RedisUtil.getInstance();
        redisUtil.new Keys().del(key);
        log.info("银行状态停用结束");
    }
}
