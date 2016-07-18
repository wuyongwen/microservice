package com.wowfilm.wechatsdk.dto;

import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.common.Castors;
import com.wowfilm.wechatsdk.common.FieldUtils;
import com.wowfilm.wechatsdk.common.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Context {

	private static final Logger log = Logger.getLogger(Context.class);

	private Map<String, Object> map;

	public Context() {

		this.map = new HashMap<>();
	}

	public Context(Map<String, Object> orginalParams) {

		this.map = orginalParams;
	}

	public void setAttribute(String key, Object value) {

		this.map.put(key, value);
	}

	public void addAttribute(String key, Object value) {

		Object previous = this.map.put(key, value);
		if (previous != null)
			log.info(String.format("参数[%s]原值[%s]被[%s]覆盖！", key, previous, value));
	}

	public void removeAttribute(String key) {

		this.map.remove(key);
	}

	public <T> T getAttribute(Class<T> clazz, String key) {

		return (T) getAttribute(key);
	}

	public Object getAttribute(String key) {

		if (key == null)
			return null;
		return this.map.get(key);
	}

	public Map<String, Object> getAttributes() {

		return new HashMap<>(map);
	}

	public void injectField(Object target) {

		for (Field field : FieldUtils.traverseGetFields(target.getClass(), Param.class)) {
			Param param = field.getAnnotation(Param.class);
			if (param.required()) {
				String paramName = param.value();
				// 兼容不指定param名称时采用字段名
				if (StringUtils.isEmpty(paramName))
					paramName = field.getName();
				Object value = this.map.get(paramName);
				try {
					if (value == null && !StringUtils.isEmpty(param.defaultValue()))
						value = param.defaultValue();
					field.set(target, Castors.cast(field.getType(), value));
				} catch (IllegalArgumentException e) {
					throw new RuntimeException("不被支持的注入类型！", e);
				} catch (IllegalAccessException e) {
					// Ignore
				}
			}
		}
	}

	@Override
	public String toString() {
		if (this.map.isEmpty())
			return "this is null!";
		Set<Entry<String, Object>> sets = map.entrySet();
		Iterator it = sets.iterator();
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		while (it.hasNext()) {
			Entry<String, Object> set = (Entry<String, Object>) it.next();
			sb.append(set.getKey() + ":" + set.getValue().toString()+",");
		}
		if(sb.toString().endsWith(",")){
			sb = new StringBuilder(sb.substring(0, sb.length()-1));
		}
		sb.append("}");
		return sb.toString();
	}
}
