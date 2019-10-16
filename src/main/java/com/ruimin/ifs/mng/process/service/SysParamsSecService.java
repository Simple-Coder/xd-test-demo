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
import com.ruimin.ifs.framework.utils.SysParamUtil;
import com.ruimin.ifs.po.TblSysParam;


/*
 * 系统参数service
 *
 */
@Service
public class SysParamsSecService extends SnowService {
	
	public static synchronized SysParamsSecService getInstance() throws SnowException {
		return ContextUtil.getSingleService(SysParamsSecService.class);
	}
	
	public PageResult queryList(String queryParamId,String queryOprcode1, Page page) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		return dao.selectList("com.ruimin.ifs.mng.rql.sysmng.querySysParam", 
			RqlParam.map().set("queryParamId", queryParamId==null?"":"%"+queryParamId.toUpperCase()+"%")
				.set("queryOprcode1", queryOprcode1==null?"":"%"+queryOprcode1.toUpperCase()+"%"),
				page);
	}
	
	public void updateSysParamsSec(TblSysParam tblSysParam) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		dao.update(tblSysParam);
		/**
		 * 韩伟成
		 * 2019年9月12日10点23分
		 * 注释掉这句话是因为在db2环境下修改语句会报错
		 */
		//SysParamUtil.getInstance().update(tblSysParam.getMagicId(), tblSysParam.getParamId(), tblSysParam.getParamValueTx());
	}
}
