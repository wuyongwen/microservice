package com.wowfilm.wechatsdk.exception;


import com.wowfilm.wechatsdk.entity.BasicResult;

public class ErrorUtils {

	public static void checkWXError(BasicResult result) throws WechatErrorException {
		if (!result.hasError())
			return;
		ErrorMsg msg = ERROR.WEERR.buildErrorMsg(result);
		throw new WechatErrorException(msg);
	}
}
