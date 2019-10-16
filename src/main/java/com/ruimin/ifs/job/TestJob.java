package com.ruimin.ifs.job;

import org.quartz.JobDataMap;
import org.slf4j.Logger;

import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.log.SnowLog;
import com.ruimin.ifs.framework.quartz.BaseJobDetail;
import com.ruimin.ifs.framework.quartz.bean.JobConstant;

public class TestJob extends BaseJobDetail{
	private Logger logger = SnowLog.getLogger(this.getClass());
	
	
	@Override
	public String executeJob(JobDataMap jobParameters) throws SnowException {
		logger.info("execute job....");
		return JobConstant.JOB_EXECUTE_RESULT_SUCCESS;
	}

	@Override
	public void afterExeSuccess(JobDataMap jobParameters) throws SnowException {
		//是否开启事务，在自定义参数中配置_transaction_flag_=false(false为关闭事务，true为打开，默认为true)
		//jobNo
		int jobNo = this.getJobNo(jobParameters);
		//自定义参数
		String a = this.getJobParamValue(jobParameters, "a");
		logger.info(jobNo+"===execute job success process["+a+"]....");
	}

	@Override
	public void afterExeFail(JobDataMap jobParameters) throws SnowException {
		logger.info("execute job fail process....");
	}

}
