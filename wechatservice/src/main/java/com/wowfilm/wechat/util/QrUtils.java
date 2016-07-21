package com.wowfilm.wechat.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


import com.wowfilm.entity.wechat.WxMpApp;
import com.wowfilm.wechatsdk.common.StringUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class QrUtils {
	private static final Logger logger = LoggerFactory.getLogger(QrUtils.class);
	/**
	 * 预览时显示公众号二维码
	 * @param app
	 * @return
	 */
	public static String getHtmlQrButton(WxMpApp app) {
		if (StringUtils.isBlank(app.getQrcodeUrl())||StringUtils.isBlank(app.getLocalqrcodePath())) {
			return "";
		}
		String webRoot = WebAppContextInitFilter.getInitedWebContextFullUrl();
		String html = getImgHtml(webRoot+ app.getLocalqrcodePath());
		return html;
	}

	protected static String getImgHtml(String path) {
		String html = "<p style='text-align: center;'><img src='" + path + "' /><br/>"
				+ " <strong><span style='color: rgb(192, 0, 0);'>长按识别上图中的二维码,关注我们哦~</span></strong></p>";
		return html;
	}

	public static String uploadQrImg(File temp, String appId) {
		String rootPath = ServletUtils.getStaticFileUploadDir();
		String ext = temp.getName().substring(temp.getName().lastIndexOf("."));
		String path = "/upload/qrcodeimg/" + appId + ext;
		String fullPath = rootPath + path;
		try {
			FileUtils.copyFile(temp, new File(fullPath));
		} catch (IOException e) {
			logger.error("上传二维码至本地出错,",e);
		}
		return path;
	}
	
	public static String uploadQrImgByInput(InputStream temp,String fileName, String appId) {
		String rootPath = ServletUtils.getStaticFileUploadDir();
		String ext = fileName.substring(fileName.lastIndexOf("."));
		String path = "/upload/qrcodeimg/temp/" + appId + ext;
		String fullPath = rootPath + path;
		try {
			FileUtils.copyInputStreamToFile(temp, new File(fullPath));
		} catch (IOException e) {
			logger.error("上传二维码至本地出错,",e);
		}
		return path;
	}
	/**
	 * 群发时自动加上公众号二维码
	 * @param content
	 * @param wxMpApp
	 * @return
	 */
	public static String buildContent(String content, WxMpApp wxMpApp) {
		if(StringUtils.isBlank(wxMpApp.getQrcodeUrl())){
			return content;
		}
		StringBuilder sb = new StringBuilder(content);
		sb.append(getImgHtml(wxMpApp.getQrcodeUrl()));
		return sb.toString();
	}
}
