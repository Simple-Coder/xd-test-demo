package com.ruimin.ifs.mng.comp;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.rql.PageResult;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.core.util.DataObjectUtils;
import com.ruimin.ifs.core.util.DateUtil;
import com.ruimin.ifs.core.util.constant.SnowEnum;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;
import com.ruimin.ifs.mng.process.service.TblBeChannelService;
import com.ruimin.ifs.po.TblBeChannel;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

/**
 *
 * @project be-delivery-admin
 * @description  接入系统管理 action服务类。
 * @version 1.0.0
 * @errorcode
 *           错误码: 错误描述 <br>
 *
 * @author
 *        2019-07-17 liuxj Create 1.0 <br>
 *
 * @copyright     ©2018-2019   上海睿民，版权所有。
 */
@SnowDoc(desc = "接入系统管理")
@ActionResource
@Slf4j
public class TblBeChannelAction extends SnowAction {

    /**
     *  根据查询条件获取接入系统的信息
     * @param queryBean 前端页面查询条件封账bean
     * @return
     * @throws SnowException
     */
        @Action
        @SnowDoc(desc = "接入系统管理查询")
        public PageResult queryAll(QueryParamBean queryBean) throws SnowException {
        String queryChannelCode = queryBean.getParameter("channelCode");
        String queryChannelName = queryBean.getParameter("channelName");
        log.debug("查询条件是： queryChannelCode {},queryChannelName {}",queryChannelCode,queryChannelName);
        return TblBeChannelService.getInstance().queryList(queryChannelCode, queryChannelName, queryBean.getPage());
    }

    /**
     *  更新接入系统信息
     * @param updateMap
     * @throws SnowException
     */
        @Action(propagation= SnowEnum.Propagation.REQUIRED)
        @SnowDoc(desc="接入系统管理修改")
        public void update(Map<String, UpdateRequestBean > updateMap) throws SnowException{
        UpdateRequestBean reqBean = updateMap.get("TblBeChannel");
            TblBeChannel tlp = new TblBeChannel();
            TblBeChannelService sps = ContextUtil.getSingleService(TblBeChannelService.class);
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tlp);
        }
            tlp.setLastUpdateTime(DateUtil.get14Date());
            // 从session中获取登录用户信息
            SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
            tlp.setLastUpdateUser(sessionUserInfo.getTlrno());
          log.debug("待修改的记录 的 uuid 为 {}" + tlp.getUuid());
          sps.updateTblBeChannel(tlp);
        sessionUserInfo.addBizLog("update.log",new String[]{sessionUserInfo.getTlrno(),sessionUserInfo.getBrCode(),
                                  "接入系统管理:将接入系统代码"+tlp.getChannelCode()+" | "+tlp.getChannelName()+"修改为:{"+tlp.getChannelName()+"}"});
    }

    /**
     *  新增接入系统信息
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "接入系统新增")
    public void save(Map<String, UpdateRequestBean> updateMap) throws SnowException {
        UpdateRequestBean reqBean = updateMap.get("TblBeChannel");
        TblBeChannel tlp = new TblBeChannel();
        while (reqBean.hasNext()) {
            DataObjectUtils.map2bean(reqBean.next(), tlp);
        }
        tlp.setUuid(ContextUtil.getUUID());
        tlp.setCreateDate(DateUtil.get14Date());
        tlp.setLastUpdateTime(DateUtil.get14Date());
        // 从session中获取登录用户信息
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        tlp.setLastUpdateUser(sessionUserInfo.getTlrno());
        TblBeChannelService.getInstance().saveTblBeChannel(tlp);
        sessionUserInfo.addBizLog("update.log",
                new String[] { sessionUserInfo.getTlrno(), sessionUserInfo.getBrCode(), "接入系统新增:id=" + tlp.getUuid()});
    }

    /**
     * 根据uuid 删除接入系统新
     * @param updateMap
     * @throws SnowException
     */
    @Action(propagation = SnowEnum.Propagation.REQUIRED)
    @SnowDoc(desc = "接入系统删除")
    public void delete(Map<String, UpdateRequestBean> updateMap) throws SnowException {
        UpdateRequestBean reqBean = updateMap.get("TblBeChannel");
        String uuid = reqBean.getParameter("uuid");
        log.debug("待删除的uuid 为{}",uuid);
        TblBeChannelService.getInstance().deleteTblBeChannel(uuid);
        SessionUserInfo sessionUserInfo = SessionUserInfo.getSessionUserInfo();
        sessionUserInfo.addBizLog("update.log",
                new String[] { sessionUserInfo.getTlrno(), sessionUserInfo.getBrCode(), "接入系统删除:id=" + uuid });
    }
}
