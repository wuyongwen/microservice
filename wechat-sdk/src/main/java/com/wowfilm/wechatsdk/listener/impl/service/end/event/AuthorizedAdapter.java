package com.wowfilm.wechatsdk.listener.impl.service.end.event;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.impl.service.route.EventRouter;
import com.wowfilm.wechatsdk.listener.impl.service.route.PlatFormEventRouter;
import org.apache.log4j.Logger;

@Node(parents = { PlatFormEventRouter.class, EventRouter.class }, value = "authorized")
public class AuthorizedAdapter implements Service {
	protected Logger log = Logger.getLogger(AuthorizedAdapter.class);
	@Param
	protected String AppId;
	@Param
	protected String CreateTime;
	@Param
	protected String AuthorizerAppid;
	@Param
	protected String AuthorizationCode;
	@Param
	protected String AuthorizationCodeExpiredTime;

	@Override
	public String doService(Context context) throws Exception {
		log.debug(String.format("收到授权事件，公众号ID： %s", AuthorizerAppid));
		return DEFAULT_RETURN;
	}

}
