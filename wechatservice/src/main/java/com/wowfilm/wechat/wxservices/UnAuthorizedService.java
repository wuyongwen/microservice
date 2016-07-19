package com.wowfilm.wechat.wxservices;


import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.impl.service.end.event.UnAuthorizedAdaptor;
import com.wowfilm.wechatsdk.listener.impl.service.route.EventRouter;
import com.wowfilm.wechatsdk.listener.impl.service.route.PlatFormEventRouter;

/**
 * 公众号取消授权
 * @author wuyongwen
 * @Date 2016年5月27日上午9:50:18
 */
@Node(parents={PlatFormEventRouter.class, EventRouter.class}, value="unauthorized")
public class UnAuthorizedService extends UnAuthorizedAdaptor {
	@Override
	public String doService(Context context) throws Exception {

		return super.doService(context);
	}
}
