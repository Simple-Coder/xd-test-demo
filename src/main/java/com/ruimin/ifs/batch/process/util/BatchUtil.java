package com.ruimin.ifs.batch.process.util;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.ruimin.ifs.batch.bean.vo.Holiday;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.util.DateUtil;
import com.ruimin.ifs.core.util.SystemConfigUtil;
import com.ruimin.ifs.core.util.constant.SnowErrorCode;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.po.TblSysInf;

public class BatchUtil {
	/**
	 * 获取系统信息
	 * 
	 * @return
	 * @throws SnowException
	 */
	public static TblSysInf getIfsSysInf() throws SnowException {
		List<TblSysInf> list = DBDaos.newInstance().selectAll(TblSysInf.class);
		if (null == list || list.size() <= 0)
			SnowExceptionUtil.throwErrorException(SnowErrorCode.MESSAGE_SYS_0005, new String[] { "获取系统信息异常!" });
		return list.get(0);
	}

	public static String getJobParamKey(String magicId) {
		String[] split = magicId.split(BatchJobConstant.BATCH_TIME_MAGIC_ID + "\\[|\\]");
		String ret = magicId;
		for (String string : split) {
			if (StringUtils.isNotBlank(string)) {
				ret = string;
				break;
			}
		}
		return ret;
	}

	public static int[] getBatchRunHourAndMinute(String runTm) {
		int[] hourAndMinute = new int[2];
		if (StringUtils.isNotBlank(runTm)) {
			String[] split = runTm.split(" |\\*");
			if (split.length > 1) {
				hourAndMinute[1] = NumberUtils.toInt(split[0]);// 分钟
				hourAndMinute[0] = NumberUtils.toInt(split[1]);// 小时
			}
		}
		return hourAndMinute;
	}

	public static String getBatchRunTime(int hour, int minute) {
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.leftPad(String.valueOf(minute), 2, '0')).append(" ");
		builder.append(StringUtils.leftPad(String.valueOf(hour), 2, '0')).append(" ");
		builder.append("* * *");
		return builder.toString();
	}

	public static String getBatchMagicId(String jobCode) {
		StringBuilder builder = new StringBuilder();
		builder.append(BatchJobConstant.BATCH_TIME_MAGIC_ID);
		builder.append("[").append(jobCode).append("]");
		return builder.toString();
	}

	public static String getRunTmShow(String bhDate, String runTm) throws SnowException {
		if (StringUtils.isNotBlank(runTm)) {
			String afterDay = DateUtil.getAfterDay(bhDate, 1);
			String[] split = StringUtils.split(runTm, " ");
			ArrayUtils.reverse(split);
			StringBuilder builder = new StringBuilder();
			List<String> ls = new ArrayList<>();
			for (String string : split) {
				if (StringUtils.isNotBlank(string) && !StringUtils.equals(string, "*")) {
					ls.add(string);
				}
			}
			int size = ls.size();
			if (size > 1) {
				builder.append(afterDay);
				builder.append(ls.get(0));
				builder.append(ls.get(1));
				builder.append("00");
			}
			try {
				String dtm = builder.toString();
				if (StringUtils.isNotBlank(dtm)) {
					Date parseDate = DateUtils.parseDate(dtm, new String[] { "yyyyMMddHHmmss" });
					dtm = DateFormatUtils.format(parseDate, "yyyy-MM-dd HH:mm:ss");
				}
				return dtm;
			} catch (Exception e) {
				return builder.toString();
			}
		}
		return runTm;
	}

	/**
	 * 根据批量日期获取可运行时间集合
	 * 
	 * @Title getRunTimesByBhDate
	 * @Description TODO
	 * @param bhDate
	 * @return
	 * @throws SnowException
	 */
	public static List<String> getRunTimesByBhDate(String bhDate) throws SnowException {
		Holiday holiday = new Holiday(bhDate);
		List<String> runtimes = new ArrayList<String>();
		runtimes.add(BatchJobConstant.SUB_STEP_RUN_TIME_DAILY);// 添加日运行
		runtimes.add(holiday.getDay_of_month() + "");
		int day_of_week = holiday.getDay_of_week();
		switch (day_of_week) {
		case 1:
			runtimes.add(BatchJobConstant.WEEKDAY_MONDAY);
			break;
		case 2:
			runtimes.add(BatchJobConstant.WEEKDAY_TUESDAY);
			break;
		case 3:
			runtimes.add(BatchJobConstant.WEEKDAY_WEDNESDAY);
			break;
		case 4:
			runtimes.add(BatchJobConstant.WEEKDAY_THURSDAY);
			break;
		case 5:
			runtimes.add(BatchJobConstant.WEEKDAY_FRIDAY);
			break;
		case 6:
			runtimes.add(BatchJobConstant.WEEKDAY_SATURDAY);
			break;
		case 7:
			runtimes.add(BatchJobConstant.WEEKDAY_SUNDAY);
			break;
		default:
			break;
		}
		if (holiday.isaPeriodOfTenDays()) {// 每旬
			runtimes.add(BatchJobConstant.SUB_STEP_RUN_TIME_EVERY_TEN_DAYS);
		} else if (holiday.isHalfYearEnd()) {// 半年
			runtimes.add(BatchJobConstant.SUB_STEP_RUN_TIME_EVERY_HALF_YEAR);
		} else if (holiday.isMonthEnd()) {// 月末
			runtimes.add(BatchJobConstant.SUB_STEP_RUN_TIME_MONTHLY);
		} else if (holiday.isSeasonEnd()) {// 季末
			runtimes.add(BatchJobConstant.SUB_STEP_RUN_TIME_EVERY_SEASON);
		} else if (holiday.isYearEnd()) {// 年末
			runtimes.add(BatchJobConstant.SUB_STEP_RUN_TIME_YEARLY);
		}
		return runtimes;
	}

	private static final String _HOSTNAME;

	static {
		java.net.InetAddress addr;
		String hostname = "ifs-snowweb";
		try {
			addr = java.net.InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (UnknownHostException e) {
		} finally {
			_HOSTNAME = hostname;
		}
	}

	/**
	 * 通讯超时(毫秒)
	 * 
	 * @return
	 */
	public static int getConnectionTimeOut() {
		return SystemConfigUtil.getIntegerConfValue("batch.connect.timeout", 30000);
	}

	/**
	 * 读取超时(毫秒)
	 * 
	 * @Title getReadTimeOut
	 * @Description TODO
	 * @return
	 */
	public static int getReadTimeOut() {
		return SystemConfigUtil.getIntegerConfValue("batch.connect.timeout", 30000);
	}

	/**
	 * 服务名称
	 * 
	 * @return
	 */
	public static String getServerNm() {
		return _HOSTNAME;
	}

	/**
	 * 批量服务地址
	 * 
	 * @return
	 */
	public static String getBtchServerUrl() {
		return SystemConfigUtil.getConfValue("batch.server.url");
	}

}
