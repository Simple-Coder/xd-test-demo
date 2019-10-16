package com.ruimin.ifs.batch.process;

import org.slf4j.Logger;

import com.ruimin.ifs.batch.bean.msg._RequestBean;
import com.ruimin.ifs.batch.bean.msg._ResponseBean;
import com.ruimin.ifs.batch.process.util.BatchUtil;
import com.ruimin.ifs.batch.process.util.HttpClientUtil;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.service.SnowService;
import com.ruimin.ifs.core.log.SnowLog;

/**
 * 与批量服务交互
 * 
 * @author wangpb
 *
 */
public abstract class AbsBatchClientProc<ReqBean extends _RequestBean, RetBean extends _ResponseBean> extends SnowService{
	protected final Logger logger = SnowLog.getLogger(this.getClass());

	/**
	 * 获取请求地址
	 * 
	 * @return
	 */
	public abstract String getReqUrl();

	/**
	 * 执行前通用检查
	 * 
	 * @param reqBean
	 * @throws SnowException
	 */
	public abstract void preProcess(ReqBean reqBean) throws SnowException;

	/**
	 * 将返回业务信息赋值到bean
	 * 
	 * @throws SnowException
	 */
	public abstract RetBean setRetMsgToBean(String retMsg) throws SnowException;

	/**
	 * 执行交易处理
	 * 
	 * @param txnIo
	 * @return
	 * @throws SnowException
	 */
	public RetBean execProcess(ReqBean req) throws SnowException {
		this.preProcess(req);
		RetBean retBean = null;
		logger.info("process " + req.getTxnCd() + " start ...");
		try {
			String reqUrl = BatchUtil.getBtchServerUrl()+this.getReqUrl();
			logger.info("request URL "+reqUrl);
			// 1.将业务javabean转为报文
			logger.debug("request bean convert to json...");
			String jsonMsg = req.toJson();
			// 2.发送请求
			String retMessage = HttpClientUtil.requestPost(reqUrl, jsonMsg);
			// 3.处理返回结果
			retBean = this.setRetMsgToBean(retMessage);
			logger.info("process " + req.getTxnCd() + " end...");
		} catch (SnowException be) {
			logger.error(be.getMessage(), be);
			throw be;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw SnowExceptionUtil.wrapException(ex);
		}
		return retBean;
	}

}
