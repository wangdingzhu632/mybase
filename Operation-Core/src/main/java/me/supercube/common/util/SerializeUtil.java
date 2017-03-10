package me.supercube.common.util;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 序列化工具类
 * */
public class SerializeUtil<T extends java.io.Serializable> {

	private final ObjectMapper mapper = new ObjectMapper();

	private SerializeUtil() {

	}

	private static class SingletonHolder {
		private final static SerializeUtil INSTANCE = new SerializeUtil();
	}

	public static SerializeUtil getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/***
	 * 反序列化字符内容
	 *
	 * @param content
	 *            JSON Content
	 *
	 * @return {@link TagInfo}
	 * */
	public T deserialize(Class<T> entityClass, String content) {
		// 判断首尾字符是否为引号，如果是则移除
		String strJson = "";
		if (content.substring(0, 1).equals("\"")) {
			strJson = content.substring(1, content.length());
		}
		if (content.substring(content.length() - 1, content.length()).equals(
				"\"")) {
			strJson = content.substring(1, content.length() - 1);
		}


		T t = null;
		try {
			t = mapper.readValue(strJson, entityClass);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return t;
	}

}
