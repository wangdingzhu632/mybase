package me.supercube.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 读取属性值工具类
 *
 * @author CHENPING
 * */
public class PropertyUtil {

	/**
	 * 读取属性值
	 *
	 * @param filePath
	 *            属性文件路径
	 *
	 * @param key
	 *            属性名称
	 *
	 * @return 属性值
	 *
	 * @exception FileNotFoundException
	 * @exception IOException
	 * */
	public static String getPropValue(String filePath, String key) {
		String value = "";
		try {
			Properties prop = new OrderedProperties();
			FileInputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			fis.close();// 关闭流

			for (Enumeration e = prop.propertyNames(); e.hasMoreElements();) {
				if (key.equals(e.nextElement().toString())) {
					value = prop.getProperty(key).toString();
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// 属性文件流
		catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
