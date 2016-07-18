package com.wowfilm.wechat.controller;

import com.wowfilm.wechatsdk.MessageHandler;
import com.wowfilm.wechatsdk.common.HttpUtils;
import com.wowfilm.wechatsdk.common.IOUtils;
import com.wowfilm.wechatsdk.common.StringUtils;
import com.wowfilm.wechatsdk.dto.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 接受微信消息
 * 1. 接受微信推送的component_verify_ticket
 * 2. 接受微信公众号信息
 * 3. 接受授权信息
 * @author wuyongwen
 * @date 2016年3月30日上午10:12:33
 */
@Controller
public class WechatPlatformMessageController {
	private static final Logger log = LoggerFactory.getLogger(WechatPlatformMessageController.class);
	@Autowired
	MessageHandler messageHandler;
	/**
	 * 微信平台推送的消息处理
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = { "/wx"})
	public void wechat(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

        process(req, resp ,null);
	}

    private void process(HttpServletRequest req, HttpServletResponse resp,String appId) throws IOException {
        Context context = new Context(HttpUtils.decodeParams(req));
        context.setAttribute("method", req.getMethod());
        if (req.getMethod().equalsIgnoreCase("POST"))
            context.setAttribute("xmlContent", HttpUtils.read(req));
        OutputStream os = resp.getOutputStream();
        try {
            String responseString = messageHandler.process(context);
            os.write(StringUtils.getBytesUtf8(responseString));
        } catch (Exception e) {
            log.error("消息处理失败", e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    /**
	 * 绑定微信公众号后,公众号的消息推送地址
	 * @param appId
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping("/wx/{appId}/wxmsg")
	public void singleWechat(@PathVariable String appId,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
        process(req, resp ,appId);
	}
}
