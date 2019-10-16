package com.ruimin.ifs.login.comp;

import java.io.InputStream;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.channel.server.http.bean.HttpAttrBean;
import com.ruimin.ifs.channel.server.servlet.bean.ServletUtil;
import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.common.bean.DownLoadFileBean;
import com.ruimin.ifs.core.common.bean.UploadFilebean;
import com.ruimin.ifs.core.communication.http.HttpRequestMessage;
import com.ruimin.ifs.core.encrypt.Base64;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.iface.communication.file.IDownLoadFileProcess;
import com.ruimin.ifs.core.iface.communication.file.IUploadFileProcess;
import com.ruimin.ifs.core.util.ContextUtil;
import com.ruimin.ifs.framework.core.SessionUserInfo;
import com.ruimin.ifs.framework.dataobject.FormRequestBean;
import com.ruimin.ifs.framework.dataobject.FormReturnBean;
import com.ruimin.ifs.framework.session.SessionUtil;
import com.ruimin.ifs.login.process.LoginService;
import com.ruimin.ifs.login.process.util.ImgProcUtil;
import com.ruimin.ifs.po.IfsStaffExt;

@ActionResource
@SnowDoc(desc = "首页action")
public class HomeAction extends SnowAction {

	@Action
	@SnowDoc(desc = "首页设置")
	public FormReturnBean pageSetProcess(FormRequestBean request) {
		HttpServletRequest req = request.getRequest();
		HttpAttrBean attrBean = HttpAttrBean.getHttpAttrBean();
		attrBean.setCharset("utf-8");
		attrBean.setMaxMsgLength(new BigDecimal(1100));// 图片不能超过1M
		attrBean.setUploadFileProcess(new IUploadFileProcess() {
			@Override
			public String fileProcess(String fileName, byte[] fileBytes, HttpRequestMessage request) throws Exception {
				// 1.对图片进行压缩
				byte[] imgBytes = ImgProcUtil.resizeFix(fileBytes, 72, 72);
				// 2.对文件流进行编码压缩，返回处理后字符串
				return Base64.encodeBase64String(imgBytes);
			}
		});
		FormReturnBean ret = new FormReturnBean();
		try {
			HttpRequestMessage reqMsg = ServletUtil.httpServerRequestToFsRequest(req, attrBean);
			String email = reqMsg.getuplUploadFilebean("email").getContentMsg();
			String telNo = reqMsg.getuplUploadFilebean("telNo").getContentMsg();
			UploadFilebean imgFile = reqMsg.getuplUploadFilebean("imgFile");
			String filenm = imgFile.getFilename();
			if (StringUtils.isNotBlank(filenm)) {
				if (filenm.indexOf("..") >= 0) {
					throw new Exception("文件不合法");
				}
				String[] strs = new String[] { ".jpg", ".png", ".gif" };
				int idx = filenm.lastIndexOf(".");
				boolean bl = false;
				if (idx > 0) {
					String ext = filenm.substring(idx).toLowerCase();
					for (String string : strs) {
						if (ext.equals(string)) {
							bl = true;
							break;
						}
					}
					if (!bl) {
						throw new Exception("文件不合法");
					}
				} else {
					throw new Exception("文件不合法");
				}
			}
			String imgStr = imgFile.getContentMsg();
			LoginService.getInstance().updateStaffByPageSet(email,telNo, imgStr);
			ret.getReturnAttrMap().put("SUCCESS", "SUCCESS");
		} catch (Exception e) {
			ret.getReturnAttrMap().put("ERROR", e.getMessage());
		}
		ret.setSendUrl("/pages/login/page_set.jsp");
		return ret;
	}

	@Action
	@SnowDoc(desc = "首页个人头像显示")
	public FormReturnBean getPageSetImg(FormRequestBean formRqeust) throws SnowException {
		SessionUserInfo userInf = SessionUtil.getSessionUserInfo(formRqeust.getRequest());
		IfsStaffExt ext = LoginService.getInstance().getTblStaffExt(userInf.getTlrno());
		FormReturnBean retBean = new FormReturnBean();
		DownLoadFileBean df = new DownLoadFileBean();
		df.setFileName(ContextUtil.getUUID());
		df.setFilePath(ext.getHeadImg());// 图像字符串
		retBean.setDownLoadFile(df);
		retBean.setDownLoadFileProcess(new IDownLoadFileProcess() {
			@Override
			public InputStream fileProcessToStream(DownLoadFileBean downLoadFileBean) throws Exception {
				return null;
			}

			@Override
			public byte[] fileProcessToByte(DownLoadFileBean downLoadFileBean) throws Exception {
				String imgContent = downLoadFileBean.getFilePath();
				if (StringUtils.isNotBlank(imgContent)) {
					return Base64.decodeBase64(imgContent);
				}
				return null;
			}
		});
		return retBean;
	}

}
