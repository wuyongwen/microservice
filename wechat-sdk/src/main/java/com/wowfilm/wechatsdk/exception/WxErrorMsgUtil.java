package com.wowfilm.wechatsdk.exception;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wowfilm.wechatsdk.common.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WxErrorMsgUtil {
	private static final Logger log = Logger.getLogger(WxErrorMsgUtil.class);
	private static final Map<Integer, String> errorMsg = new HashMap<>();
	private static final Gson gson = new Gson();
	static {
		log.info("加载error.json");
		initMap();
	}

	public static String getMsg(int code) {
		String msg = errorMsg.get(code);
		msg = StringUtils.isEmpty(msg)?"":msg;
		return msg;
	}
	public static void initMap(){
		String jsonContext  = readFile();
		//System.out.println(jsonContext);
		List<WxErrMsg> list = gson.fromJson(jsonContext, new TypeToken<List<WxErrMsg>>(){}.getType());
		if(list!=null&&list.size()>0){
			for(WxErrMsg e:list){
				errorMsg.put(e.getKey(), e.getVal());
			}
		}
	}

	private static String readFile() {
		
		BufferedReader reader = null;
		String laststr = "";
		try {
			//Thread.currentThread().getContextClassLoader().getClass().getResourceAsStream("/me/chanjar/weixin/common/bean/result/error.json");   
			InputStream is = WxErrorMsgUtil.class.getResourceAsStream("error.json");
			if(is==null) return "";
	        log.info("读取error.json文件");
			BufferedReader br=new BufferedReader(new InputStreamReader(is));  
			reader = new BufferedReader(br);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			log.error("读取文件错误!",e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}
}
