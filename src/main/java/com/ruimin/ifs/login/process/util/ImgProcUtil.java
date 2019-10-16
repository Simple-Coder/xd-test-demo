package com.ruimin.ifs.login.process.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.exception.SnowExceptionUtil;
import com.ruimin.ifs.core.util.constant.SnowErrorCode;

/**
 * 图片压缩处理
 * 
 * @author ningpeng
 *
 */
public class ImgProcUtil {

	/**
	 * 图片压缩处理
	 * 
	 * @param imgBytes
	 * @param w
	 *            压缩后宽度（如果小于等于0表示按高度计算）
	 * @param h
	 *            压缩后高度 （如果小于等于0表示按宽度计算）
	 * @return
	 * @throws SnowException
	 */
	public static byte[] resizeFix(byte[] imgBytes, int w, int h) throws SnowException {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		if (w <= 0 && h <= 0) {
			SnowExceptionUtil.throwWarnException(SnowErrorCode.DEF_ERROR_CODE, "请设置压缩图片宽度或高度");
		}
		try {
			in = new ByteArrayInputStream(imgBytes);
			Image img = ImageIO.read(in);
			int imgWidth = img.getWidth(null); // 得到源图宽
			int imgHeight = img.getHeight(null); // 得到源图长
			if (w <= 0) {
				w = (int) (imgWidth * h / imgHeight);
			} else if (h <= 0) {
				h = (int) (imgHeight * w / imgWidth);
			}
			if (imgWidth / imgHeight > w / h) {
				h = (int) (imgHeight * w / imgWidth);
			} else {
				w = (int) (imgWidth * h / imgHeight);
			}
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
			out = new ByteArrayOutputStream();
			// 可以正常实现bmp、png、gif转jpg
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(image); // JPEG编码
			ImageIO.write(image,  "jpeg" , out); 
			image.flush();
			out.flush();
			return out.toByteArray();
		} catch (Exception e) {
			throw SnowExceptionUtil.wrapException(e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
		}
	}
}
