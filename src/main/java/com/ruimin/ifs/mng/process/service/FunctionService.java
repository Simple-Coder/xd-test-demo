package com.ruimin.ifs.mng.process.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.service.SnowService;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.constant.SnowErrorCode;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.po.TblFunction;

@Service
public class FunctionService extends SnowService {

	public static FunctionService getInstance() throws SnowException {
		return ContextUtil.getSingleService(FunctionService.class);
	}

	public List<TblFunction> listAll() throws SnowException {
		DBDao dao = DBDaos.newInstance();
		List<Object> list = dao.selectList("com.ruimin.ifs.mng.rql.sysmng.findAllFunctions");
		List<TblFunction> result = new ArrayList<TblFunction>();
		for (Object obj : list) {
			result.add((TblFunction) obj);
		}
		return result;
	}

	public void insert(TblFunction tblFunction) throws SnowException {
		DBDao dao = DBDaos.newInstance();
		String parentid = tblFunction.getLastdirectory();
		if (StringUtils.isBlank(parentid)) {
			parentid = null;
		}
		String maxid = (String) dao.selectOne("com.ruimin.ifs.mng.rql.sysmng.findFunctionMaxId", RqlParam.map().set("param", parentid));
		if (StringUtils.isBlank(maxid)) {
			maxid = parentid == null ? "1000" : parentid + StringUtils.leftPad("1", 2, "0");
		} else {
			if (StringUtils.isBlank(parentid)) {
				int lmaxid = 0;
				try {
					lmaxid = Integer.valueOf(maxid) + 1;
				} catch (Exception e) {
					getLogger().warn("funcid must be digits characters. ", e);
					lmaxid = 1000;
				}
				maxid = StringUtils.leftPad(String.valueOf(lmaxid),4,"0");
			} else {
				String subSeq = maxid.substring(parentid.length());
				int lmaxid = 0;
				try {
					lmaxid = Integer.valueOf(subSeq) + 1;
				} catch (Exception e) {
					getLogger().warn("funcid must be digits characters. ", e);
					lmaxid = 1;
				}
				maxid = parentid + StringUtils.leftPad(String.valueOf(lmaxid), subSeq.length(), "0");
			}
		}
		tblFunction.setFuncid(maxid);
		dao.insert(tblFunction);

	}

	public void update(TblFunction tblFunction) throws SnowException {
		DBDao dao = DBDaos.newInstance();
		dao.update(tblFunction);
	}

	public void delete(String funcid) throws SnowException {
		DBDao dao = DBDaos.newInstance();
		Long haschild = (Long) dao.selectOne("com.ruimin.ifs.mng.rql.sysmng.checkFunctionHaschild", funcid);
		if (haschild > 0) {
			SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_WEB_0035);
		}
		Long hasusing = (Long) dao.selectOne("com.ruimin.ifs.mng.rql.sysmng.checkFunctionHasusing", funcid);
		if (hasusing > 0) {
			SnowExceptionUtil.throwWarnException(SnowErrorCode.MESSAGE_WEB_0036);
		}

		dao.delete(TblFunction.class, funcid);

	}

}
