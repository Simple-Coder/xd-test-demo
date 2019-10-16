package com.ruimin.ifs.mng.process.service;

import java.util.List;
import java.util.Map;

import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.iface.service.SnowService;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DateUtil;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.process.query.Page;
import com.ruimin.ifs.po.TblBctl;
import com.ruimin.ifs.util.SeqNoGenUtil;
@SnowDoc(desc="机构操作")
public class OrgService extends SnowService{
	
	public static OrgService getInstance() throws SnowException {
		return ContextUtil.getSingleService(OrgService.class);
	}
	
	/**
	 * 查询所有机构
	 * @return
	 * @throws SnowException
	 */
	public List<TblBctl> listAll() throws SnowException{
		DBDao dao = DBDaos.newInstance();
		List<TblBctl> list = dao.selectAll(TblBctl.class);
		return list;
	}
	/**
	 * 分页查询下级机构
	 * @param upBrCode
	 * @param page
	 * @return
	 * @throws SnowException
	 */
	public PageResult listChildOrg(Map<String, String> queryMap,Page page) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		PageResult result = dao.selectList("com.ruimin.ifs.mng.rql.sysmng.queryOrgChild", queryMap, page);
		return result;
	}
	/**
	 * 根据主键获取机构
	 * @param brCode
	 * @return
	 * @throws SnowException
	 */
	public TblBctl queryOrgById(String brCode) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		return dao.select(TblBctl.class, brCode);
	}
	/**
	 * 根据机构编号查询
	 * @param brno
	 * @return
	 * @throws SnowException
	 */
	public int queryOrgByBrNo(String brno,String brcode) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		return (Integer) dao.selectOne("com.ruimin.ifs.mng.rql.sysmng.queryOrgByBrNo",RqlParam.map().set("brno",brno).set("brcode", brcode));
	}
	
	public int queryOrgByBrClass() throws SnowException{
		DBDao dao = DBDaos.newInstance();
		return (Integer) dao.selectOne("com.ruimin.ifs.mng.rql.sysmng.queryOrgByBrClass");
	}
	
	
	/**
	 * 新增机构
	 * @param bctl
	 * @throws SnowException
	 */
	public void addOrg(TblBctl bctl) throws SnowException{
		//生成机构code
		bctl.setBrcode(SeqNoGenUtil.genOrgBrCode());
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		bctl.setLastUpdDate(DateUtil.getCurrDate());
		bctl.setLastUpdTlr(sessionUserInfo.getTlrno());
		bctl.setStatus("1");
		DBDao dao = DBDaos.newInstance();
		dao.insert(bctl);
		getLogger().info("添加新机构信息:"+bctl.getBrcode());
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"添加新机构:brcode="+bctl.getBrcode()});
	}
	
	/**
	 * 机构信息修改
	 * @param bctl
	 * @throws SnowException
	 */
	public void updateOrg(TblBctl bctl) throws SnowException{
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		bctl.setLastUpdDate(DateUtil.getCurrDate());
		bctl.setLastUpdTlr(sessionUserInfo.getTlrno());
		bctl.setStatus("1");
		DBDao dao = DBDaos.newInstance();
		dao.update(bctl);
		getLogger().info("修改机构信息:"+bctl.getBrcode());
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"修改机构信息:brcode="+bctl.getBrcode()});
	}
	
	/**
	 * 设置机构有效性
	 * @param bctl
	 * @throws SnowException
	 */
	public void updateOrgStatus(String brcode,String status) throws SnowException{
		SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
		TblBctl bctl = queryOrgById(brcode);
		bctl.setStatus(status);
		bctl.setLastUpdDate(DateUtil.getCurrDate());
		bctl.setLastUpdTlr(sessionUserInfo.getTlrno());
		DBDao dao = DBDaos.newInstance();
		dao.update(bctl);
		getLogger().info("设置机构["+bctl.getBrcode()+"]有效性为:"+bctl.getStatus());
		sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),"设置机构["+bctl.getBrcode()+"]有效性为:"+bctl.getStatus()});
	}
}
