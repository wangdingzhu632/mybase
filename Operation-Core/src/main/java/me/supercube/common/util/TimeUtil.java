package me.supercube.common.util;


import org.springframework.util.StringUtils;


public class TimeUtil {


	/**
	 * 转换分钟数小时分钟的格式
	 * */
	public static String getHhmmFromMinute(int minute) {
		if(minute==0) {
			return "00:00";
		}
		StringBuilder sb = new StringBuilder();
		int h = minute / 60;
		int m = minute % 60;

		sb.append(String.format("%02d", h));
		sb.append(":");
		sb.append(String.format("%02d", m));

		return sb.toString();
	}

	/**
	 * 转换秒数为小时分钟秒的格式
	 * */
	public static String getHhmmssFromMinute(int second) {
		if(second==0) {
			return "00:00:00";
		}
		StringBuilder sb = new StringBuilder();
		int h = second / 3600;
		int m = (second - h*3600) / 60;
		int s = second % 60;

		sb.append(String.format("%02d", h));
		sb.append(":");
		sb.append(String.format("%02d", m));
		sb.append(":");
		sb.append(String.format("%02d", s));
		return sb.toString();
	}


//	public static int getSecondFromHHmmss(String time) throws Exception {
//		if(StringUtils.hasLength(time)) {
//			try {
//				LocalDateTimeTimeFormatter dtf = LocalDateTimeTimeFormat.forPattern("HH:mm:ss");
//				LocalDateTimeTime datetime = dtf.parseLocalDateTimeTime(time);
//				return datetime.getSecondOfDay();
//			}catch(Exception e) {
//				throw new Exception(String.format("时间(%s)转换失败,请检查源数据。", time));
//			}
//		} else {
//			return 0;
//		}
//	}
//
//
//	public static int getMinuteFromHHmm(String time) throws Exception {
//		if(StringUtils.hasLength(time)) {
//			try {
//				LocalDateTimeTimeFormatter dtf = LocalDateTimeTimeFormat.forPattern("HH:mm");
//				LocalDateTimeTime datetime = dtf.parseLocalDateTimeTime(time);
//				return datetime.getMinuteOfDay();
//			}catch(Exception e) {
//				throw new Exception(String.format("时间(%s)转换失败,请检查源数据。", time));
//			}
//		} else {
//			return 0;
//		}
//	}

//	public static void main(String[] args) {
//
//		int a = 510,b=1380;
//		System.out.println(getHhmmFromMinute(a));
//		System.out.println(getHhmmFromMinute(b));
//
//		String time = "17:09";
//
//		int c;
//		try {
//			c = getMinuteFromHHmm(time);
//			System.out.println(c);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//	}

}
