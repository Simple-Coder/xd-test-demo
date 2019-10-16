package com.ruimin.ifs.interceptor;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;

import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.intercept.AbsDaoInterceptor;
import com.ruimin.ifs.core.log.SnowLog;
import com.ruimin.ifs.core.util.DateUtil;
import com.ruimin.ifs.framework.core.SessionUserInfo;

public class TestUpdateDaoInterceptor extends AbsDaoInterceptor{
	private Logger logger = SnowLog.getLogger(this.getClass());
	@Override
	public void execute(Object obj) throws SnowException {
		try {
			PropertyUtils.setNestedProperty(obj, "lastUpdDate", DateUtil.get8Date());
			SessionUserInfo user = SessionUserInfo.getSessionUserInfoNoException();
			if (user!=null) {
				PropertyUtils.setNestedProperty(obj, "lastUpdTlr", user.getTlrno());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

}
