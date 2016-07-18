package com.wowfilm.wechatsdk.api.wechatmp;

import com.wowfilm.wechatsdk.api.http.RequestExecutor;
import com.wowfilm.wechatsdk.api.http.SimpleGetRequestExecutor;
import com.wowfilm.wechatsdk.api.http.SimplePostRequestExecutor;
import com.wowfilm.wechatsdk.exception.ErrorMsg;
import com.wowfilm.wechatsdk.exception.WechatErrorException;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

import java.io.IOException;


/**
 * The type Wx service executor.
 * 微信接口调用
 * @author warden
 * @date Created on 2016-07-13 19:51:32
 */
public class WxServiceExecutor {
    private static final Logger log = Logger.getLogger(WxServiceExecutor.class);
    private int retrySleepMillis = 1000;

    private int maxRetryTimes = 5;

    private TokenAccessor accessor;

    public TokenAccessor getAccessor() {
        return accessor;
    }

    public void setAccessor(TokenAccessor accessor) {
        this.accessor = accessor;
    }

    public String get(String url, String queryParam) {
        return execute(new SimpleGetRequestExecutor(), url, queryParam);
    }

    public String post(String url, String postData) {
        return execute(new SimplePostRequestExecutor(), url, postData);
    }

    /**
     * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
     *
     * @param executor
     * @param uri
     * @param data
     * @return
     */
    public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) {
        int retryTimes = 0;
        do {
            try {
                return executeInternal(executor, uri, data);
            } catch (WechatErrorException e) {
                ErrorMsg error = e.getError();
                /**
                 * -1 系统繁忙, 1000ms后重试
                 */
                if (error.getErrorcode() == -1) {
                    int sleepMillis = retrySleepMillis * (1 << retryTimes);
                    try {
                        log.debug("微信系统繁忙，" + sleepMillis + "ms 后重试(第" + retryTimes + 1 + "次)");
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                } else {
                    throw e;
                }
            }
        } while (++retryTimes < maxRetryTimes);

        throw new RuntimeException("微信服务端异常，超出重试次数");
    }

    protected synchronized <T, E> T executeInternal(RequestExecutor<T, E> executor, String uri, E data) {
        if (uri.indexOf("access_token=") != -1) {
            throw new IllegalArgumentException("uri参数中不允许有access_token: " + uri);
        }
        String accessToken = accessor.getAccessToken(false);

        String uriWithAccessToken = uri;
        uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token=" + accessToken : "&access_token=" + accessToken;

        try {
            return executor.execute(getHttpclient(), getHttpProxy(), uriWithAccessToken, data);
        } catch (WechatErrorException e) {
            ErrorMsg error = e.getError();
      /*
       * 发生以下情况时尝试刷新access_token
       * 40001 获取access_token时AppSecret错误，或者access_token无效
       * 42001 access_token超时
       */
            if (error.getErrorcode() == 42001 || error.getErrorcode() == 40001) {
                // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
                accessor.expireAccessToken();
                return execute(executor, uri, data);
            }
            if (error.getErrorcode() != 0) {
                throw e;
            }
            return null;
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpHost getHttpProxy() {
        return accessor.getHttpProxy();
    }

    private CloseableHttpClient getHttpclient() {
        return accessor.getHttpclient();
    }
}
