package com.ruimin.ifs.dwr;

import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.login.process.bean.SweepMonitorBean;
import com.ruimin.ifs.login.process.bean.SweepMsgQueryBean;
import com.ruimin.ifs.mng.process.bean.TradeQueryVo;
import com.ruimin.ifs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: be-delivery-admin
 * @Package: com.ruimin.ifs.dwr
 * @ClassName: HighChartsService
 * @Author: dong
 * @Description: ${description}
 * @Date: 2019/9/1 18:05
 * @Version: 1.0
 */
@Slf4j
public class HighChartsService
{
    /**
     * 系统监控后台逻辑
     * @param requestBean
     * @param request
     * @param response
     * @return
     * @throws SnowException
     */
    public HighChartsResponseBean getDatas(HighChartsRequestBean requestBean, HttpServletRequest request, HttpServletResponse response) throws SnowException {
        //获取前端页面
        String tradeStartTime = requestBean.getTradeStartTime();
        String tradeEndTime = requestBean.getTradeEndTime();
        String bankSelect = requestBean.getBankSelect();
        log.info("开始日期：【{}】，结束日期：【{}】,接入行选择：【{}】", tradeStartTime, tradeEndTime, bankSelect);
        //校验日期
        boolean flag=checkDate(tradeStartTime,tradeEndTime);
        if (!flag)
        {
            SnowExceptionUtil.throwErrorException("开始日期或结束日期格式不正确！");
        }
        //处理开始时间、结束时间、接入行
        if (tradeStartTime != null&&!tradeStartTime.equals("") && tradeEndTime != null&&!tradeEndTime.equals("")) {
            //开始日期不能大于结束日期
            Date parse_start_time = DateUtil.parse(tradeStartTime, DateUtil.PATTERN_YYYY_MM_DD_HHMMSS);
            Date parse_end_time = DateUtil.parse(tradeEndTime, DateUtil.PATTERN_YYYY_MM_DD_HHMMSS);

            if (parse_start_time.compareTo(parse_end_time) > 0) {
                SnowExceptionUtil.throwErrorException("开始日期不能大于结束日期！");
            }
            tradeStartTime = DateUtil.getYyyyMMddHHmmss(parse_start_time);
            tradeEndTime = DateUtil.getYyyyMMddHHmmss(parse_end_time);
        }
        if ((tradeStartTime == null && tradeEndTime == null && bankSelect == null)||("".equals(tradeStartTime)&&"".equals(tradeEndTime)&&"".equals(bankSelect))) {
            //说明是首次进入首页
            log.info("开始日期未指定，默认当天：【{}】", DateUtil.getYYYYMMDD(new Date()).concat("000000000"));
            log.info("结束日期未指定，默认当天：【{}】", DateUtil.getYYYYMMDD(new Date()).concat("999999999"));
            log.info("接入行未选择，默认所有接入行：【{}】", bankSelect);
            tradeStartTime = DateUtil.getYYYYMMDD(new Date()).concat("000000000");
            tradeEndTime = DateUtil.getYYYYMMDD(new Date()).concat("999999999");
            bankSelect = "";
        }
        //如果只选择接入行，
        if (bankSelect!=null&&!bankSelect.equals("")&&"".equals(tradeStartTime)&&"".equals(tradeEndTime))
        {
            tradeStartTime = DateUtil.getYYYYMMDD(new Date()).concat("000000000");
            tradeEndTime = DateUtil.getYYYYMMDD(new Date()).concat("999999999");
        }

        if (tradeStartTime == null) {
            tradeStartTime = "";
        }
        if (tradeEndTime == null) {
            tradeEndTime = "";
        }
        if (bankSelect == null) {
            bankSelect = "";
        }

        //声明x轴
        List<String> x = new ArrayList<>();
        //成功量y轴
        List<Integer> ysuccess = new ArrayList<>();
        //失败量y轴
        List<Integer> yfail = new ArrayList<>();
        //超时量y轴
        List<Integer> ytimeout=new ArrayList<>();
        //总量y轴
        List<Integer> ytotal = new ArrayList<>();


        //饼状图
        //成功量
        int piesuccess = 0;
        //失败量
        int piefail = 0;
        //超时量
        int pietimeout=0;

        //响应实体bean
        HighChartsResponseBean rsp = new HighChartsResponseBean();
        //查询结果
        List<Object> objects = com.ruimin.ifs.mng.process.service.HighChartsService.getInstance().queryListAll(tradeStartTime, tradeEndTime, bankSelect);
        //判断结果
        if (objects == null || objects.size() == 0) {
            log.error("暂无可用数据");
            SnowExceptionUtil.throwErrorException("系统日志暂无可用数据");
            return null;
        }
        //遍历查询结果
        for (Object object : objects) {
            TradeQueryVo vo = (TradeQueryVo) object;
            //x轴数据添加
//            x.add(vo.getTranCode());
//            String trancode = com.ruimin.ifs.mng.process.service.HighChartsService.getInstance().getTrancode(vo.getUuid());
            String tradeName = com.ruimin.ifs.mng.process.service.HighChartsService.getInstance().getTradeName(vo.getTranCode());
            x.add(tradeName);
            //y轴成功量添加
            ysuccess.add(Integer.parseInt(vo.getSuccessNum() + ""));
            piesuccess += Integer.parseInt(vo.getSuccessNum() + "");
            //y轴失败量添加
            yfail.add(Integer.parseInt(vo.getFailureNum() + ""));
            piefail += Integer.parseInt(vo.getFailureNum() + "");
            //y轴超时量
            ytimeout.add(Integer.parseInt(vo.getTimeOutNum()+""));
            pietimeout+=Integer.parseInt(vo.getTimeOutNum()+"");
            //y轴总量添加
            ytotal.add(Integer.parseInt(vo.getTotalNum()));
        }

        int temp_totalNum_x = piefail + piesuccess;
        String success_per = "0";
        String fail_per = "0";
        String timeout_per = "0";
        if (temp_totalNum_x != 0) {
            //总量
            BigDecimal temp_totalNum = new BigDecimal(temp_totalNum_x);
            //成功量
            BigDecimal temp_success = new BigDecimal(piesuccess);
            //失败量
            BigDecimal temp_fail = new BigDecimal(piefail);
            //超时量
            BigDecimal temp_timeout=new BigDecimal(pietimeout);


            //计算成功率
            success_per = (temp_success.multiply(new BigDecimal(100))).divide(temp_totalNum, 2, BigDecimal.ROUND_HALF_UP).toString();
            //计算失败率
            fail_per = (temp_fail.multiply(new BigDecimal(100))).divide(temp_totalNum, 2, BigDecimal.ROUND_HALF_UP).toString();
            //计算超时率
            timeout_per = (temp_timeout.multiply(new BigDecimal(100))).divide(temp_totalNum, 2, BigDecimal.ROUND_HALF_UP).toString();

        }

        //x轴名称
        rsp.setXtext(x);
        //柱状图成功量
        rsp.setYsuccess(ysuccess);
        //柱状图失败量
        rsp.setYfail(yfail);
        //柱状图超时量
        rsp.setYtimeout(ytimeout);
        //总量
        rsp.setYtotal(ytotal);
        //饼图成功率
        rsp.setPiesuccess(success_per);
        //饼图失败率
        rsp.setPiefail(fail_per);
        //饼图超时率
        rsp.setPietimeout(timeout_per);
        return rsp;
    }

    private boolean checkDate(String tradeStartTime, String tradeEndTime) throws SnowException {
        if ((tradeEndTime != null&&!tradeEndTime.equals(""))&&tradeStartTime==null)
        {
            SnowExceptionUtil.throwErrorException("开始日期不能为空!");
            return false;
        }
        if ((tradeEndTime != null&&!tradeEndTime.equals(""))&&tradeStartTime.equals(""))
        {
            SnowExceptionUtil.throwErrorException("开始日期不能为空!");
            return false;
        }

        if ((tradeStartTime != null&&!tradeStartTime.equals(""))&&tradeEndTime==null)
        {
            SnowExceptionUtil.throwErrorException("结束日期不能为空!");
            return false;
        }
        if ((tradeStartTime != null&&!tradeStartTime.equals(""))&&tradeEndTime.equals(""))
        {
            SnowExceptionUtil.throwErrorException("结束日期不能为空!");
            return false;
        }
        return true;
    }

    /**
     * 首页支付监控后台逻辑
     * @param requestBean
     * @param request
     * @param response
     * @return
     * @throws SnowException
     */
    public SweepMonitorResponseBean getSweepDatas(SweepMonitorRequestBean requestBean,HttpServletRequest request,HttpServletResponse response)throws SnowException
    {
        String sex = requestBean.getSex();
        //响应实体bean
        SweepMonitorResponseBean rsp = new SweepMonitorResponseBean();
        //y轴（各家银行）
        ArrayList<String> yText = new ArrayList<>();
        //归集成功
        ArrayList<Integer> sweepSuccess = new ArrayList<>();
        //提交归集
        ArrayList<Integer> sweepOther = new ArrayList<>();
        //归集失败
        ArrayList<Integer> sweepFailure = new ArrayList<>();
        //入账成功
        ArrayList<Integer> entrySuccess = new ArrayList<>();
        //入账失败
        ArrayList<Integer> entryFailure = new ArrayList<>();
        //查询数据
        List<SweepMsgQueryBean> objects = com.ruimin.ifs.mng.process.service.HighChartsService.getInstance().querySweepMonitorData();
        //判断结果
        if (objects == null || objects.size() == 0)
        {
            log.error("暂无可用数据");
            SnowExceptionUtil.throwErrorException("系统日志暂无可用数据");
            return null;
        }
        for (SweepMsgQueryBean temp_sweep_bean : objects)
        {
//            SweepMonitorBean temp_sweep_bean = (SweepMonitorBean) object;
            //y轴银行名称
            yText.add(temp_sweep_bean.getShortname());
            //归集成功
            sweepSuccess.add(Integer.parseInt(temp_sweep_bean.getSweepSuccess()));
            //提交归集
            sweepOther.add(Integer.parseInt(temp_sweep_bean.getSweepOther()));
            //归集失败
            sweepFailure.add(Integer.parseInt(temp_sweep_bean.getSweepFailure()));
            //入账成功
            entrySuccess.add(Integer.parseInt(temp_sweep_bean.getEntrySuccess()));
            //入账失败
            entryFailure.add(Integer.parseInt(temp_sweep_bean.getEntryFailure()));
        }
        //y轴各家银行
        rsp.setYtext(yText);
        //归集成功
        rsp.setSweepSuccess(sweepSuccess);
        //提交归集
        rsp.setSweepOther(sweepOther);
        //归集失败
        rsp.setSweepFailure(sweepFailure);
        //入账成功
        rsp.setEntrySuccess(entrySuccess);
        //入账失败
        rsp.setEntryFailure(entryFailure);
        return rsp;
    }
}

