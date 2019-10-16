package com.ruimin.ifs.mng.comp;

import java.util.List;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.framework.dataobject.PageQueryResult;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.servlet.CommQueryServletRequest;
import com.ruimin.ifs.mng.process.service.TlrLoginLogService;

@SnowDoc(desc = "登录日志查询")
@ActionResource
public class LoginLogQueryAction extends SnowAction {
	
	@Action
	@SnowDoc(desc = "日志查询")
	public PageResult queryAll(QueryParamBean queryBean) throws SnowException{
		String queryTlrno = queryBean.getParameter("qtlrNo");
		String queryBrcode = queryBean.getParameter("qbrNo");
		return TlrLoginLogService.getInstance().queryList(queryTlrno, queryBrcode, queryBean.getPage());
	}
	
	@Action
	@SnowDoc(desc = "登录详情查询")
	public PageQueryResult queryAllList(QueryParamBean queryBean) throws SnowException{
		PageQueryResult result = new PageQueryResult();
		CommQueryServletRequest commQueryServletRequest = queryBean.getCqRequest();
		String queryTlrno = commQueryServletRequest.getParameter("qtlrNo");
		String queryBrcode = commQueryServletRequest.getParameter("qbrCode");
		String queryLastaccesstm = commQueryServletRequest.getParameter("queryLastaccesstm");
		String queryLastlogouttm = commQueryServletRequest.getParameter("queryLastlogouttm");
		List<Object> list = TlrLoginLogService.getInstance().queryListAll(queryTlrno, queryBrcode, queryLastaccesstm, queryLastlogouttm);
		if(null != list && list.size() > 0){
			result.setTotalCount(list.size());
			result.setQueryResult(list);
		}else{
			result.setTotalCount(0);
			result.setQueryResult(null);
		}
		return result;
	}
	
}
