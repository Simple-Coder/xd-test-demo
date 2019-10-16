package com.ruimin.ifs.example.comp;

import javax.servlet.http.HttpServletRequest;

import com.ruimin.ifs.channel.server.http.bean.HttpAttrBean;
import com.ruimin.ifs.channel.server.http.handler.DefUploadFileProcess;
import com.ruimin.ifs.channel.server.servlet.bean.ServletUtil;
import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.communication.http.HttpRequestMessage;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.framework.dataobject.FormRequestBean;
import com.ruimin.ifs.framework.dataobject.FormReturnBean;
@ActionResource
public class FileUploadAction extends SnowAction{

	@Action
	@SnowDoc(desc="文件导入处理")
	public FormReturnBean uploadFileProcess(FormRequestBean request) throws SnowException{
		HttpServletRequest req = request.getRequest();
		HttpAttrBean attrBean = HttpAttrBean.getHttpAttrBean();
		attrBean.setCharset("utf-8");
		attrBean.setUploadFileProcess(new DefUploadFileProcess());//默认将文件存储至临时文件夹
		FormReturnBean ret = new FormReturnBean();
		try {
			HttpRequestMessage reqMsg = ServletUtil.httpServerRequestToFsRequest(req, attrBean);
			System.out.println(reqMsg.getMessage());
			System.out.println(reqMsg.getuplUploadFilebean("uf").getContentMsg());
			ret.getReturnAttrMap().put("uploadFilePath", reqMsg.getuplUploadFilebean("uf").getContentMsg());
			ret.setSendUrl("/pages/example/jsp/fileUpload.jsp");
		} catch (Exception e) {
			throw SnowExceptionUtil.wrapException(e);
		}
		return ret;
	}
	
	
	
}
