package com.ruimin.ifs.util;

import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.framework.process.generator.SeqNoGenerator;

/**
 * 序列号生成器
 * @author pengning
 * @date 2015-7-2 上午9:21:06 
 * @Description
 */
public class SeqNoGenUtil {
	/**机构编号ValueNo***/
	public static final int VALUE_NO_ORG = 10;
	/**角色岗位ValueNo***/
	public static final int VALUE_NO_ROLE_ID = 11;
	/**批量设置****/
	public static final int VALUE_NO_BHPROCSTEP_ID = 12;
	
	public static String leftPadZero(int value,int length){
		return StringUtils.leftPad(String.valueOf(value), length,'0');
	}
	
	/**
	 * 获取机构编号
	 * @return
	 * @throws SnowException
	 */
	public static String genOrgBrCode() throws SnowException{
		int brcodeNo = SeqNoGenerator.getInstance().getSeqNo(VALUE_NO_ORG);
		return leftPadZero(brcodeNo, 4);
	}
	
	/**
	 * 角色主键
	 * @return
	 * @throws SnowException
	 */
	public static int genRoleId() throws SnowException{
		return SeqNoGenerator.getInstance().getSeqNo(VALUE_NO_ROLE_ID);
	}
	/**
	 * 获取批量ID
	 * @return
	 * @throws SnowException
	 */
	public static int genBhProcStepId() throws SnowException{
		return SeqNoGenerator.getInstance().getSeqNo(VALUE_NO_BHPROCSTEP_ID);
	}
	
}
