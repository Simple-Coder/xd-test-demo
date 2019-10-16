package com.ruimin.ifs.util;

public class SnowConstant {
	/**
	 * 机构级别-BCTL.BRCLASS
	 */
	public static final String BRCODE_CLASS_HEAD = "1"; // 1-总行
	public static final String BRCODE_CLASS_BRANCH = "2"; // 2-分行
	public static final String BRCODE_CLASS_SUBBRANCH = "5"; // 5-支行
	public static final String BRCODE_CLASS_MNGBRANCH = "3"; // 3-管理行
	public static final String BRCODE_CLASS_PL_CENTER = "3"; // 个贷中心
	public static final String BRCODE_CLASS_SUB_PL_CENTER = "5"; // 个贷分中心

	/**{0}不能为空***/
	public static final String WEB_ERROR_CODE_E0040 = "WEB_E0040";
	/***不能为初始密码**/
	public static final String WEB_ERROR_CODE_E0045 = "WEB_E0045";
	
	
	/**
	 * SYS_INFO表ID
	 */
	public static final int TABLE_SYS_INFO_ID = 1;
	
	/**
	 * 批量状态
	 */
	public static final String SYS_INFO_STATE_ONLINE = "0"; // 联机状态
	public static final String SYS_INFO_STATE_BATCH = "1"; // 批量状态
	
	/**
	 * session用户扩展信息
	 */
	public static final String USER_EXT_INF_KEY = "USER_EXT_INF_KEY";
}
