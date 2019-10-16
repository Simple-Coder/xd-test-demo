package com.ruimin.ifs.mng.comp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DateUtil;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.dao.DBDao;
import com.ruimin.ifs.dao.DBDaos;
import com.ruimin.ifs.dao.param.RqlParam;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.PageQueryResult;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.mng.process.bean.CalInfoVo;
import com.ruimin.ifs.po.TblCalInf;
import com.ruimin.ifs.util.SnowConstant;
@SnowDoc(desc="工作日历")
@ActionResource
public class HolidayAction extends SnowAction{
	
	private CalInfoVo TblCalInf2Vo(TblCalInf tblcal){
		CalInfoVo info = new CalInfoVo();
		info.setId(tblcal.getId());
		info.setYear(tblcal.getYear());
		info.setDef(tblcal.getHolidayDef());
		int workDay = 0;
		int holidayDay = 0;
		char[] holidayDefCharAry = tblcal.getHolidayDef().toCharArray();
		for(int i=0;i<holidayDefCharAry.length;i++){
			if(holidayDefCharAry[i]=='1'){
				workDay++;
			}else{
				holidayDay++;
			}
		}
		info.setSunWorkDay(workDay);
		info.setSunHoliDay(holidayDay);
		return info;
	}
	
	
	@Action
	@SnowDoc(desc="日历查询")
	public PageQueryResult listHoliday(QueryParamBean queryParam) throws SnowException{
		DBDao dao = DBDaos.newInstance();
		PageResult result = dao.selectList("com.ruimin.ifs.mng.rql.sysmng.queryHoliday",RqlParam.map().set("year", queryParam.getParameter("year","")), queryParam.getPage());
		List<?> calInfoList = result.getQueryResult();
		List<CalInfoVo> voList = new ArrayList<CalInfoVo>();
		for (int i = 0; i < calInfoList.size(); i++) {
			TblCalInf tblcal = (TblCalInf) calInfoList.get(i);
			voList.add(this.TblCalInf2Vo(tblcal));
		}
		PageQueryResult retResult = new PageQueryResult();
		retResult.setTotalCount(result.getTotalCount());
		retResult.setQueryResult(voList);
		return retResult;
	}
	
	@Action
	@SnowDoc(desc="日历查询")
	public CalInfoVo getTblCalInf(QueryParamBean queryParam) throws SnowException{
		String id = queryParam.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			DBDao dao = DBDaos.newInstance();
			TblCalInf info = dao.select(TblCalInf.class, id);
			return this.TblCalInf2Vo(info);
		}else{
			CalInfoVo info = new CalInfoVo();
			info.setYear(Integer.parseInt(queryParam.getParameter("year")));
			return info;
		}
	}
	
	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="工作日期保存或更新")
	public void saveOrUpdateHoliday(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean requestBean = updateMap.get("holidayDetail");
		SessionUserInfo userInfo = SessionUserInfo.getSessionUserInfo();
		if (requestBean.hasNext()) {
			Map<String, String> reqMap = requestBean.next();
			String id = reqMap.get("id");
			String year = reqMap.get("year");
			String def = reqMap.get("def");
			DBDao dao = DBDaos.newInstance();
			TblCalInf calinfo = null;
			if(StringUtils.isNotBlank(id)){
				calinfo = dao.select(TblCalInf.class, id);
				calinfo.setHolidayDef(def);
				calinfo.setLastUpdOperId(userInfo.getTlrno());
				calinfo.setLastUpdTime(DateUtil.getDateAndTm14());
				dao.update(calinfo);
			}else{
				calinfo = new TblCalInf();
				calinfo.setId(ContextUtil.getUUID());
				calinfo.setYear(Integer.parseInt(year));
				calinfo.setHolidayDef(def);
				calinfo.setLastUpdOperId(userInfo.getTlrno());
				calinfo.setLastUpdTime(DateUtil.getDateAndTm14());
				dao.insert(calinfo);
			}
		}
	}
	
	
	@Action
	@SnowDoc(desc="根据年查询日历信息")
	public Map<String, String> getTblCalInfByYear(Map<String, UpdateRequestBean> updateMap) throws SnowException{
		UpdateRequestBean requestBean = updateMap.get("holidayDs");
		String year = null;
		if (requestBean.hasNext()) {
			year = requestBean.next().get("year");
		}
		if (StringUtils.isBlank(year)) {
			SnowExceptionUtil.throwWarnException(SnowConstant.WEB_ERROR_CODE_E0040, "年份");
		}
		Map<String, String> retMap = new HashMap<String, String>();
		DBDao dao = DBDaos.newInstance();
		TblCalInf info = (TblCalInf) dao.selectOne("com.ruimin.ifs.mng.rql.sysmng.queryHoliday",RqlParam.map().set("year", year));
		if (info!=null) {
			retMap.put("id", info.getId());
			retMap.put("year", String.valueOf(info.getYear()));
		}else{
			retMap.put("id", "");
			retMap.put("year", year);
		}
		return retMap;
	}
}
