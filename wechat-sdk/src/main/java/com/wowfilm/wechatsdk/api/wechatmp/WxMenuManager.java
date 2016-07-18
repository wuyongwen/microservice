package com.wowfilm.wechatsdk.api.wechatmp;

import com.wowfilm.wechatsdk.api.http.SimpleGetRequestExecutor;
import com.wowfilm.wechatsdk.api.http.SimplePostRequestExecutor;
import com.wowfilm.wechatsdk.entity.wxmp.WxMenu;
import com.wowfilm.wechatsdk.exception.WechatErrorException;

/**
 * Created by wen on 2016/7/13 10:26.
 */
public class WxMenuManager extends WxBaseService {
    /**
     * <pre>
     * 自定义菜单创建接口
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
     * 如果要创建个性化菜单，请设置matchrule属性
     * 详情请见:http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
     * </pre>
     *
     * @param menu
     */
    public void menuCreate(WxMenu menu) {
        if (menu.getMatchRule() != null) {
            String url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional";
            executor.execute(new SimplePostRequestExecutor(), url, menu.toJson());
        } else {
            String url = "https://api.weixin.qq.com/cgi-bin/menu/create";
            executor.execute(new SimplePostRequestExecutor(), url, menu.toJson());
        }
    }

    /**
     * <pre>
     * 自定义菜单删除接口
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
     * </pre>
     */
    public void menuDelete() {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete";
        executor.execute(new SimpleGetRequestExecutor(), url, null);
    }

    /**
     * <pre>
     * 删除个性化菜单接口
     * 详情请见: http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
     * </pre>
     *
     * @param menuid
     */
    public void menuDelete(String menuid) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delconditional";
        executor.execute(new SimpleGetRequestExecutor(), url, "menuid=" + menuid);
    }

    /**
     * <pre>
     * 自定义菜单查询接口
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
     * </pre>
     *
     * @return
     */
    public WxMenu menuGet() {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get";
        try {
            String resultContent = executor.execute(new SimpleGetRequestExecutor(), url, null);
            return WxMenu.fromJson(resultContent);
        } catch (WechatErrorException e) {
            // 46003 不存在的菜单数据
            if (e.getError().getErrorcode() == 46003) {
                return null;
            }
            throw e;
        }
    }

    /**
     * 获取全局菜单
     * @return
     */
    public String selfMenuGet() {
        String url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info";
        String resultContent = executor.execute(new SimpleGetRequestExecutor(), url, null);
        return resultContent;
    }

    /**
     * <pre>
     * 测试个性化菜单匹配结果
     * 详情请见: http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
     * </pre>
     *
     * @param userid 可以是粉丝的OpenID，也可以是粉丝的微信号。
     */
    public WxMenu menuTryMatch(String userid) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/trymatch";
        try {
            String resultContent = executor.execute(new SimpleGetRequestExecutor(), url, "user_id=" + userid);
            return WxMenu.fromJson(resultContent);
        } catch (WechatErrorException e) {
            // 46003 不存在的菜单数据     46002 不存在的菜单版本
            if (e.getError().getErrorcode() == 46003 || e.getError().getErrorcode() == 46002) {
                return null;
            }
            throw e;
        }
    }
}
