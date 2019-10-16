package com.ruimin.ifs.mng.process.service;

import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.iface.service.SnowService;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.framework.process.query.Page;
import com.ruimin.ifs.framework.utils.ValueUtil;

@Service
public class BizLogService extends SnowService {

	public static BizLogService getInstance() throws SnowException {
		return ContextUtil.getSingleService(BizLogService.class);
	}

	public PageResult queryList(String oprcode, String startDate, String endDate, String brno, Page page)
			throws SnowException {
		DBDao dao = DBDaos.newInstance();
		return dao.selectList(
				"com.ruimin.ifs.mng.rql.sysmng.queryBizLog",
				RqlParam.map().set("brno", ValueUtil.getRqlParamValue(brno, ""))
						.set("oprcode", ValueUtil.getRqlParamValue(oprcode, "", "%" + oprcode + "%"))
						.set("startDate", ValueUtil.getRqlParamValue(startDate, ""))
						.set("endDate", ValueUtil.getRqlParamValue(endDate, "")), page);
	}

}
