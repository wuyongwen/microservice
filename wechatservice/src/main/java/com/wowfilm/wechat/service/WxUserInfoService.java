package com.wowfilm.wechat.service;

import biz.entgroup.framework.entity.Userinfo;
import com.wowfilm.wechat.mapper.WxUserMapper;
import com.wowfilm.wechat.wxextend.WechatServiceFactory;
import com.wowfilm.wechatsdk.api.wechatmp.WxOauth2Service;
import com.wowfilm.wechatsdk.common.StringUtils;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpOAuth2AccessToken;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * The type Wx user info service.
 * 微信用户登录授权
 *
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-22 23:45:22
 */
@Service
public class WxUserInfoService {
    @Autowired
    private WxUserMapper userinfoMapper;
    @Autowired
    private WechatServiceFactory factory;
    public Userinfo getUserInfoById(Integer userId){
        return userinfoMapper.selectByPrimaryKey(userId);
    }

    /**
     * Get oauth url string.
     * @param redirectUrl the redirect url
     * @param state       the state
     * @param scope       the scope
     * @return the string
     */
    public String getOauthUrl(String redirectUrl,String state,String scope){
        WxOauth2Service oauth2Service = factory.getSelfAppBuilder().OAUTH2();
        String url = "";
        if(StringUtils.isNotBlank(redirectUrl)){
            url = oauth2Service.oauth2buildAuthorizationUrl(redirectUrl,scope,state);
        }else{
            url = oauth2Service.oauth2buildAuthorizationUrl(scope,state);
        }
        return url;
    }

    /**
     * do auth
     * @param code the code
     * @return the integer
     */
    public Integer doAuth(String code) {
        WxOauth2Service oauth2Service = factory.getSelfAppBuilder().OAUTH2();
        WxMpOAuth2AccessToken accessToken = oauth2Service.oauth2getAccessToken(code);
        WxMpUser user = oauth2Service.oauth2getUserInfo(accessToken, null);
        //TODO acess_token,refreesh_token 处理
        Userinfo userinfo = userinfoMapper.findByOpenId(accessToken.getOpenId());
        if (userinfo == null) {
            userinfo = new Userinfo();
        }
        putInfo(user, userinfo);
        return saveOrUpdate(userinfo);
    }
    public int saveOrUpdate(Userinfo userInfo){
        if(userInfo.getUserid()==null || userInfo.getUserid() ==0){
            return userinfoMapper.insert(userInfo);
        }else{
            return userinfoMapper.updateByPrimaryKeySelective(userInfo);
        }
    }
    private void putInfo(WxMpUser user, Userinfo userinfo) {
        userinfo.setNickname(user.getNickname());
        userinfo.setOpenid(user.getOpenId());
        userinfo.setCity(user.getCity());
        userinfo.setCountry(user.getCountry());
        userinfo.setProvince(user.getProvince());
        userinfo.setHeadimg(user.getHeadImgUrl());
        userinfo.setSex(Integer.parseInt(user.getSex()));
    }
}
