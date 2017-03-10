package me.supercube.common.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.UnsupportedEncodingException;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.split;

public class StringUtils {
	/**
	 * 填充字符串
	 * <p>{@code fillString(6,1); return "000001"}</p>
	 * @param length	填充字符要求长度
	 * @param value 	当前数值
	 *
	 * @return 返回填充后的字符串
	 * */
	public static String fillString(int length, long value) {
        String result = (new Long(value)).toString();
        while (length > result.length()) {
            result = "0" + result;
        }
        return result;
    }

	/**
	 * 解析子字符串
	 * <p> example: <br />{@code parseSubstring("11001105",2);return 05;} </p>
	 * @param target	目标字符串
	 * @param parseLength	解析字符长度
	 *
	 * @return 解析后的字符串
	 * */
	public static long parseSubstring(String target,int parseLength) {
		long result = 0;
		if(target.length()>=parseLength) {
			String strNo = target.substring(target.length()-parseLength);
			if(NumberUtils.isNumber(strNo)) {
				result = Long.parseLong(strNo);
			}
		}
		return result;
	}

	/**
	 * 获取转码后的字符串
	 * @param str 目标字符
	 *
	 * @return 结果字符串
	 * */
	public static String getEncoding(String str) {

		String encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }

        encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }

        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

	 /**
     * 将字符串编码格式转成UTF-8
     *
     * @param str
     * @return
     */
    public static String EncodeToUTF8(String str) {
        try {
            String strEncode = StringUtils.getEncoding(str);
            String temp = new String(str.getBytes(strEncode), "UTF-8");
            return temp;
        } catch (java.io.IOException ex) {
            return null;
        }
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)){
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 缩略字符串（不区分中英文字符）
     * @param str 目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     * @param objectString 对象串
     *   例如：row.user.id
     *   返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString){
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");
        for (int i=0; i<vals.length; i++){
            val.append("." + vals[i]);
            result.append("!"+(val.substring(1))+"?'':");
        }
        result.append(val.substring(1));
        return result.toString();
    }

}
