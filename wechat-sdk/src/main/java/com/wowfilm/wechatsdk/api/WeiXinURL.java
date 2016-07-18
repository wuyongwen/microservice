package com.wowfilm.wechatsdk.api;

public interface WeiXinURL {


    String GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${appId}&secret=${appSecret}";
    String SEND_SERVICE = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=${accessToken}";

    String POST_FILE = "http://api.weixin.qq.com/cgi-bin/media/upload?access_token=${accessToken}&type=${type}";
    String GET_FILE = "http://api.weixin.qq.com/cgi-bin/media/get?access_token=${accessToken}&media_id=${mediaId}";

    String CREATE_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=${accessToken}";
    String QUERY_GROUPS = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=${accessToken}";
    String QUERY_USER_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=${accessToken}";
    String MODIFY_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=${accessToken}";
    String MODIFY_USER_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=${accessToken}";

    String REMARK_USER = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=${accessToken}";
    String QUERY_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=${accessToken}&openid=${openid}&lang=zh_CN";
    String QUERY_FOLLOWER = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=${accessToken}&next_openid=${next_openid}";

    String CREATE_BUTTONS = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=${accessToken}";
    String QUERY_MENU = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=${accessToken}";
    String DELETE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=${accessToken}";

    String SHORT_URL = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=${accessToken}";

    String PLATFORM_GET_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
    String PLATFORM_GET_PRE_AUTHCODE = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=${component_access_token}";
    String PLATFORM_GET_AUTHINFO = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=${component_access_token}";
    String PLATFORM_GET_AUTHACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=${component_access_token}";
    String PLATFORM_GET_AUTHORIZER_INFO = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=${component_access_token}";
    String PLATFORM_GET_AUTHORIZER_OPTION = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_option?component_access_token=${component_access_token}";
    String PLATFORM_SET_AUTHORIZER_OPTION = "https://api.weixin.qq.com/cgi-bin/component/ api_set_authorizer_option?component_access_token=${component_access_token}";
    String PLATFORM_LOGINURL = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=${component_appid}&pre_auth_code=${pre_auth_code}&redirect_uri=${loogined_redirect_url}";
}