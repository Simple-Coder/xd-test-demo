package com.ruimin.ifs.mng.process.service;

import java.util.List;

import com.ruimin.ifs.core.annotation.Service;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.iface.service.SnowService;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.framework.process.query.Page;
import com.ruimin.ifs.po.TblStaff;
import com.ruimin.ifs.po.TblStaffRoleRel;

/**
 * 用户管理sevice
 * @author shaoqin
 *
 */
@Service
public class OperMngEntryService extends SnowService {
	public static OperMngEntryService getInstance() throws SnowException {
		return ContextUtil.getSingleService(OperMngEntryService.class);
	}
	
	public PageResult queryAll(String upbrcode, String tlrno, String tlrName, Page page) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		return dao.selectList("com.ruimin.ifs.mng.rql.sysmng.queryUserInfo", 
			RqlParam.map()
				.set("upbrcode", upbrcode==null?"":upbrcode)
				.set("tlrno", tlrno==null?"":"%"+tlrno+"%")
				.set("tlrName", tlrName==null?"":"%"+tlrName+"%"),
				page);
	}
	
	public TblStaff checkId(String id) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		return dao.select(TblStaff.class, id);
	}
	
	public void saveOperMngEntry(TblStaff tblStaff) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		dao.insert(tblStaff);
	}
	
	public void updateOperMngEntry(TblStaff tblStaff) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		dao.update(tblStaff);
	}
	
	public void deletOperMngEntry(TblStaff tblStaff) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		dao.delete(tblStaff);
	}
	
	public void deleteOperMngEntryById(String id) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		dao.delete(TblStaff.class, id);
	}
	
	public void insertStaffRoleRel(List<TblStaffRoleRel> list) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		dao.insert(list);
	}
	
	public int deleteStaffRoleRel(String tlrno) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		return dao.executeUpdate("com.ruimin.ifs.mng.rql.sysmng.deleteRoleResInfByTlrno", 
				RqlParam.map().set("tlrno", tlrno==null?"":tlrno));
	}
	
	public List<Object> querySelected(String tlrno){
		DBDao dao = DBDaos.newInstance();
		return dao.selectList("com.ruimin.ifs.mng.rql.sysmng.querySelected", 
				RqlParam.map().set("tlrno", tlrno==null?"":tlrno));
	}

}
