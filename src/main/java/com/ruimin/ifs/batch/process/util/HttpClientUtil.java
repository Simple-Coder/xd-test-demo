package com.ruimin.ifs.batch.process.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.util.StopWatch;

import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.log.SnowLog;

/**
 * 与批量服务通讯
 * 项目名称  ifs-snowweb       
 * 类描述  
 * 类名称    com.ruimin.ifs.batch.process.util.HttpClientUtil       
 * 创建人    ningpeng
 * 创建时间  Jan 21, 2019 2:29:02 PM     
 * 修改人  	ningpeng
 * 修改时间  Jan 21, 2019 2:29:02 PM     
 * 修改备注    
 * @version  1.0
 */
public class HttpClientUtil {
	private static final Logger logger = SnowLog.getLogger(HttpClientUtil.class);
	private static final String DEF_CHAR_ENCODING = "UTF-8";
	private static final String CONTENT_TYPE_JSON = "application/json";

	/**
	 * post请求
	 * 
	 * @param url
	 * @return
	 * @throws SnowException
	 */
	public static String requestPost(String requstAddr, String message) throws Exception {
		StopWatch sw = new StopWatch("请求批量服务");
		HttpURLConnection urlConnection = null;// 初始化
		Long startTime = System.currentTimeMillis();
		/** 初始化 */
		if (StringUtils.isBlank(requstAddr)) {
			SnowExceptionUtil.throwErrorException("服务器地址为空！");
		}
		if (StringUtils.isBlank(message)) {
			SnowExceptionUtil.throwErrorException("请求报文为空！");
		}
		// 请求报文
		byte[] msgBytes = message.getBytes(DEF_CHAR_ENCODING);
		/** 建立连接 */
		try {
			sw.start("建立连接");
			logger.info("建立连接【开始】...");
			URL url = new URL(requstAddr);
			String requestMehtod = "POST";
			logger.info("url：" + url.toString() + ";请求方式：" + requestMehtod);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod(requestMehtod);
			urlConnection.setConnectTimeout(BatchUtil.getConnectionTimeOut());
			urlConnection.setReadTimeout(BatchUtil.getReadTimeOut());
			urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
			urlConnection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
			urlConnection.setRequestProperty("Content-length", String.valueOf(msgBytes.length));
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
			urlConnection.connect();
			long endTime = System.currentTimeMillis();
			logger.info("建立连接【成功】，耗时：" + (endTime - startTime) + " ms ");
		} catch (Exception e) {
			logger.error("建立连接【失败】，原因：" + e.getMessage());
			if (urlConnection!=null) {
				urlConnection.disconnect();
			}
			SnowExceptionUtil.throwErrorException(e.getMessage());
		}finally {
			sw.stop();
		}
		/** 发送报文 */
		DataOutputStream printout = null;
		try {
			sw.start("发送报文");
			logger.info("发送报文【开始】...");
			logger.info("报文内容：" + message);
			printout = new DataOutputStream(urlConnection.getOutputStream());
			printout.write(msgBytes);// 把报文发送到XX的接口
			printout.flush();
			logger.info("发送报文【成功");
		} catch (Exception e) {
			logger.error("发送报文【失败】，原因：" + e.getMessage());
			if (urlConnection!=null) {
				urlConnection.disconnect();
			}
			SnowExceptionUtil.throwErrorException(e.getMessage());
		} finally {
			IOUtils.closeQuietly(printout);
			sw.stop();
		}
		/** 接收应答 */
		DataInputStream input = null;
		ByteArrayOutputStream out = null;
		try {
			sw.start("接收应答");
			logger.info("读取服务器响应【开始】...");
			input = new DataInputStream(urlConnection.getInputStream());// 获取XX接口的返回信息
			out = new ByteArrayOutputStream();
			byte[] bufferByte = new byte[256];
			int l = -1;
			while ((l = input.read(bufferByte)) > -1) {
				out.write(bufferByte, 0, l);
				out.flush();
			}
			byte[] rResult = out.toByteArray();
			String result = new String(rResult, DEF_CHAR_ENCODING);
			logger.info("读取服务器响应【成功】");
			logger.info("响应内容：" + result);
			return result;
		} catch (Exception e) {
			logger.error("读取服务器响应【失败】，原因：" + e.getMessage());
			SnowExceptionUtil.throwErrorException(e.getMessage());
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(input);
			if (urlConnection!=null) {
				urlConnection.disconnect();
			}
			sw.stop();
		}
		logger.info(sw.prettyPrint());
		return null;
	}

}
