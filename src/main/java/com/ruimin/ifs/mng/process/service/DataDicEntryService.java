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
import com.ruimin.ifs.po.TblDataDic;

//Service注解表示该类为服务，必须有此注解才能使用ContextUtil获取
@Service
public class DataDicEntryService extends SnowService {// 继承自SnowService

  /**
   * 
   * @return 服务实例，单例
   * @throws SnowException
   */
  public static DataDicEntryService getInstance() throws SnowException {
    // 根据class获取服务实例
    return ContextUtil.getSingleService(DataDicEntryService.class);
  }

  // 获取数据
  public PageResult queryList(String queryDataTypeNo, String queryDataNo, String queryDataName, String queryDataTypeName, Page page)
          throws SnowException {
    // 获取一个DAO对象，它会当前默认的数据源
    DBDao dao = DBDaos.newInstance();
    // 调用查询方法，key为RQL配置文件的包名+<select>节点的id
    return dao.selectList(
            "com.ruimin.ifs.mng.rql.sysmng.queryDataDic",
            RqlParam.map().set("queryDataTypeNo", queryDataTypeNo==null?"":"%" + queryDataTypeNo + "%").set("queryDataNo", queryDataNo==null?"":"%" + queryDataNo + "%")
                    .set("queryDataName", queryDataName==null?"":"%" + queryDataName + "%").set("queryDataTypeName", queryDataTypeName==null?"":"%" + queryDataTypeName + "%"), page);
  }

  public void saveDataDicEntry(TblDataDic tblDataDic) throws SnowException {
    DBDao dao = DBDaos.newInstance();
    dao.insert(tblDataDic);
  }

  public void updateDataDicEntry(TblDataDic tblDataDic) throws SnowException {
    DBDao dao = DBDaos.newInstance();
    dao.update(tblDataDic);
  }

  public void deletDataDicEntry(TblDataDic tblDataDic) throws SnowException {
    DBDao dao = DBDaos.newInstance();
    dao.delete(tblDataDic);
  }

  public void deleteDataDicEntryById(String id) throws SnowException {
    DBDao dao = DBDaos.newInstance();
    dao.delete(TblDataDic.class, id);
  }
}
