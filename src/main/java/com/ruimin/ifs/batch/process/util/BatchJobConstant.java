package com.ruimin.ifs.batch.process.util;

public class BatchJobConstant {
	//批量参数KEY
	public static final String BATCH_PARAM_ID="BATH";
	//运行模式
	public static final String BATCH_RUNMODE_MAGIC_ID="RUN_MODE";
	
	public static final String BATCH_TIME_MAGIC_ID="JOB";
	
	public static final String PARAM_BATCH_RUNMODE_KEY = BATCH_PARAM_ID+"."+BATCH_RUNMODE_MAGIC_ID;
	
	//调用批量错误:{0}
	public static final String BATCH_CLIENT_ERROR_CODE = "BTCH_CLIENT_0001";
	//批量运行模式-自动
	public static final String BATCH_RUNMODE_AUTO = "1";
	
	//批量运行模式-人工触发
	public static final String BATCH_RUNMODE_HAND = "0";
	
	/**
	 * 批量类型
	 * 项目名称  ifs-snowweb       
	 * 类描述  
	 * 类名称    com.ruimin.ifs.batch.process.util.BATCH_TYPE       
	 * 创建人    ningpeng
	 * 创建时间  Jan 18, 2019 3:13:59 PM     
	 * 修改人  	ningpeng
	 * 修改时间  Jan 18, 2019 3:13:59 PM     
	 * 修改备注    
	 * @version  1.0
	 */
	public enum BATCH_TYPE{
		//SD:日切批量(批量开始执行，IFS_SYS_INF表中批量日期切换为下一日，状态修改为批量)
		//SO:联机批量（批量任务结束，IFS_SYS_INF表中状态修改为联机
		DAY_SWITCH("SD","日切批量"),ONLINE("SO","联机批量");
		public String type;
		public String name;
		
		private BATCH_TYPE(String type,String name) {
			this.type = type;
			this.name = name;
		}
		
		public String getParamKey() {
			return BATCH_PARAM_ID+"."+BATCH_TIME_MAGIC_ID+"["+type+"]";
		}
	}
	
	public static final String DATE_PARTTEN = "yyyyMMdd"; // 统一的日期格式
	
	/**
	 * 批量子步骤运行时间：不跑
	 */
	public static final String SUB_STEP_RUN_TIME_NONE = "9N";
	/**
	 * 批量子步骤运行时间：每日
	 */
	public static final String SUB_STEP_RUN_TIME_DAILY = "90";
	/**
	 * 批量子步骤运行时间：每旬（固定在周五批量）
	 */
	public static final String SUB_STEP_RUN_TIME_EVERY_TEN_DAYS = "91";
	/**
	 * 批量子步骤运行时间：每月
	 */
	public static final String SUB_STEP_RUN_TIME_MONTHLY = "93";
	/**
	 * 批量子步骤运行时间：每季
	 */
	public static final String SUB_STEP_RUN_TIME_EVERY_SEASON = "94";
	/**
	 * 批量子步骤运行时间：每半年
	 */
	public static final String SUB_STEP_RUN_TIME_EVERY_HALF_YEAR = "95";
	/**
	 * 批量子步骤运行时间：每年
	 */
	public static final String SUB_STEP_RUN_TIME_YEARLY = "96";

	/**
	 * 系统设定常量：星期一
	 */
	public static final String WEEKDAY_MONDAY = "41";
	/**
	 * 系统设定常量：星期二
	 */
	public static final String WEEKDAY_TUESDAY = "42";
	/**
	 * 系统设定常量：星期三
	 */
	public static final String WEEKDAY_WEDNESDAY = "43";
	/**
	 * 系统设定常量：星期四
	 */
	public static final String WEEKDAY_THURSDAY = "44";
	/**
	 * 系统设定常量：星期五
	 */
	public static final String WEEKDAY_FRIDAY = "45";
	/**
	 * 系统设定常量：星期六
	 */
	public static final String WEEKDAY_SATURDAY = "46";
	/**
	 * 系统设定常量：星期日
	 */
	public static final String WEEKDAY_SUNDAY = "47";
	
	/**
	 * 线程运行状态：未运行
	 */
	public final static String THREAD_STATUS_OPEN = "O";
	/**
	 * 线程运行状态：运行中
	 */
	public final static String THREAD_STATUS_RUNNING = "R";
	/**
	 * 线程运行状态：已出错
	 */
	public final static String THREAD_STATUS_ERROR = "E";
	/**
	 * 线程运行状态：已完成
	 */
	public final static String THREAD_STATUS_FINISHED = "F";
	/**
	 * 线程运行状态: 严重错误线程未能清理现场
	 */
	public final static String THREAD_STATUS_UNKNOWE_ERROR = "UE";
	
	public static final String MERGEINFO_FLAG_FINISHED = "F"; // 处理成功
	public static final String MERGEINFO_FLAG_FAILED = "E"; // 处理失败
	public static final String MERGEINFO_FLAG_RUNING = "R"; // 正在处理
	public static final String MERGEINFO_FLAG_UNPROCESSED = "N"; // 未处理
}
