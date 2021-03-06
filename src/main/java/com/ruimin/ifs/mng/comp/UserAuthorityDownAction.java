package com.ruimin.ifs.mng.comp;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.framework.dataobject.FormRequestBean;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.mng.process.service.UserAuthorityDownService;

@SnowDoc(desc = "人员岗位权限查询")
@ActionResource
public class UserAuthorityDownAction extends SnowAction {
	
	@Action
	@SnowDoc(desc = "日志查询")
	public List<Object> queryAll(QueryParamBean queryBean) throws SnowException{
		String ptrlno = queryBean.getParameter("ptrlno");
		String ptrlname = queryBean.getParameter("ptrlname");
		return UserAuthorityDownService.getInstance().queryList(ptrlno, ptrlname);
	}
	
	@Action(propagation = Propagation.REQUIRED)
	@SnowDoc(desc="excel导出")
	public void exportExcel(FormRequestBean formRequestBean){
		try {
			UserAuthorityDownService.getInstance().export(formRequestBean.getRequest(), formRequestBean.getResponse());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SnowException e) {
			e.printStackTrace();
		}
	}
	
}
