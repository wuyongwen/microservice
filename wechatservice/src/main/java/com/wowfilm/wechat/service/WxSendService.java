package com.wowfilm.wechat.service;


import biz.entgroup.framework.db.MessageResponse;
import com.wowfilm.entity.wechat.WxMpApp;
import org.springframework.stereotype.Service;

/**
 * The type Wx send service.
 *
 * @param <T> the type parameter
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-28 11:38:10
 */
public class WxSendService<T> {
    public <E> MessageResponse<E> sendMsg(T t, WxMpApp app){
        /*MessageResponse<E> response = null;
        Object obj = null;
        if(t instanceof Wxtext){
            obj = (Wxtext)t;
        }else if(t instanceof Wximage){
            obj = (Wximage)t;
        }else if(t instanceof Wxarticlegroup){
            obj = (Wxarticlegroup)t;
        }
        obj.*/
        return null;
    }
}
