package com.ruimin.ifs.mng.comp;

import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.constant.SnowErrorCode;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.mng.process.service.BizLogService;

@SnowDoc(desc = "系统日志查询")
@ActionResource
public class BizLogQueryAction extends SnowAction{

	@Action
	@SnowDoc(desc = "查询")
	public PageResult queryAll(QueryParamBean queryBean) throws SnowException{
		String oprcode1 = queryBean.getParameter("oprcode1");
		String startDate = queryBean.getParameter("startDate");
		String endDate = queryBean.getParameter("endDate");
		if (StringUtils.isNotBlank(oprcode1)) {
			SnowExceptionUtil.throwErrorException(SnowErrorCode.MESSAGE_SYS_0005,"测试异常");
		}
		return BizLogService.getInstance().queryList(oprcode1, startDate, endDate, SessionUserInfo.getSessionUserInfoNoException().getBrCode(), queryBean.getPage());
	}
	
}
