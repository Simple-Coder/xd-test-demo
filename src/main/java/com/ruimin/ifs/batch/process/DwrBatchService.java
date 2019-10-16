package com.ruimin.ifs.batch.process;

import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.po.TblSysInf;
import com.ruimin.ifs.util.SnowConstant;

public class DwrBatchService {
	
	/**
	 * 获取系统类型
	 * @Title  getSystemType  
	 * @Description TODO  
	 * @return
	 * @throws SnowException
	 */
	public String getSystemType() throws SnowException {
		DBDao dao = DBDaos.newInstance();
		TblSysInf sysInf = dao.select(TblSysInf.class, SnowConstant.TABLE_SYS_INFO_ID);
		return sysInf.getSystemType();
	}
}
