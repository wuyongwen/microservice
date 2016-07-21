package com.wowfilm.wechat.util;

import java.io.InputStream;

import com.thoughtworks.xstream.XStream;
import com.wowfilm.wechatsdk.entity.wxpay.xml.XStreamInitializer;


public class XmlUtil {
	public static <T> T fromXml(Class<T> clazz, InputStream is) {
		XStream xstream = XStreamInitializer.getInstance();
		xstream.alias("xml", clazz);
		xstream.processAnnotations(clazz);
		return (T) xstream.fromXML(is);
	}
}
