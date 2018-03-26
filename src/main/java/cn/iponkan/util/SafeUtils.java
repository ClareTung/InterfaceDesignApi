package cn.iponkan.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * SafeUtils
 * 
 * @author zhuf
 * @date 2014-12-16
 */
public class SafeUtils {
	private static Log log = LogFactory.getLog(SafeUtils.class.getName());
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT1 = "yyyyMMdd";
	public static final String DATE_FORMAT2 = "yyyy年MM月dd日";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATE_TIME_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT2 = "yyyyMMddHHmmss";

	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	private static Calendar ca = Calendar.getInstance();

	public static String JSON = "JSON";
	public static String TEXT = "TEXT";

	public static String generateGUID() {
		UUID tmpuuid = UUID.randomUUID();
		String tmpstr = tmpuuid.toString();
		tmpstr = tmpstr.replaceAll("-", "");
		return tmpstr;
	}

	public static int getInt(Object obj) {
		int tmpret = 0;
		if (obj != null) {
			try {
				tmpret = Integer.parseInt(obj.toString());
			} catch (Exception ex) {

			}
		}
		return tmpret;
	}

	public static Long getLong(Object obj, Long defvalue) {
		Long tmpret = defvalue;
		if (obj != null) {
			try {
				tmpret = Long.parseLong(obj.toString());
			} catch (Exception ex) {
			}
		}
		return tmpret;
	}

	public static Long getLong(Object obj) {
		Long tmpret = 0L;
		if (obj != null) {
			try {
				if (!obj.toString().equals(""))
					tmpret = Long.parseLong(obj.toString());
			} catch (Exception ex) {
			}
		}
		return tmpret;
	}

	public static int getInt(Object obj, int defvalue) {
		int tmpret = defvalue;
		if (obj != null) {
			try {
				if (!obj.toString().equals(""))
					tmpret = Integer.parseInt(obj.toString());
			} catch (Exception ex) {

			}
		}
		return tmpret;
	}

	public static Integer getInteger(Object obj) {
		Integer tmpret = new Integer(0);
		if (obj != null) {
			try {
				tmpret = Integer.valueOf(obj.toString());
			} catch (Exception ex) {
				log.error("getInteger:" + ex);
			}
		}
		return tmpret;
	}

	public static Integer getInteger(Object obj, int defvalue) {
		Integer tmpret = new Integer(defvalue);
		if (obj != null) {
			try {
				tmpret = Integer.valueOf(obj.toString());
			} catch (Exception ex) {
				log.error("getInteger:" + ex);
			}
		}
		return tmpret;
	}

	public static double getDouble(Object obj) {
		double tmpvalue = 0;
		try {
			if(null != obj)
				tmpvalue = Double.parseDouble(obj.toString());
		} catch (Exception ex) {
		}
		return tmpvalue;
	}

	public static double getDouble(Object obj, double defvalue) {
		double tmpvalue = defvalue;
		try {
			tmpvalue = Double.parseDouble(obj.toString());
		} catch (Exception ex) {
			log.error("getDouble :" + ex);
		}
		return tmpvalue;
	}

	public static String getString(Object obj) {
		String tmpret = "";
		if (obj != null)
			tmpret = obj.toString();
		return tmpret.trim();
	}

	public static boolean isNull(Object obj) {
		if (obj != null)
			return false;
		return true;
	}

	public static String getStringWithUrlEncode(Object obj) {
		String tmpret = "";
		if (obj != null) {
			try {
				tmpret = URLDecoder.decode(obj.toString(), "utf-8");
			} catch (Exception ex) {
				log.error("getStringWithUrlEncode:" + ex);
			}
		}
		return tmpret.trim();
	}

	public static java.sql.Date getSqlDate(Object obj) {
		java.sql.Date tmpdate = null;
		if (obj != null) {
			try {
				tmpdate = java.sql.Date.valueOf(obj.toString());
			} catch (Exception ex) {
				log.error("getDate :" + ex);
			}
		}
		return tmpdate;
	}

	public static java.sql.Date getSqlDate(String obj) {
		java.sql.Date tmpdate = null;
		if (obj != null) {
			try {
				tmpdate = java.sql.Date.valueOf(obj.toString());
			} catch (Exception ex) {
				log.error("getDate :" + ex);
			}
		}
		return tmpdate;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static int getCurrentUnixTime() {
		int tmpCurrentTime = (int) (System.currentTimeMillis() / 1000);
		return tmpCurrentTime;
	}

	public static Date getUTCDate(long times) {

		// 1、取得本地时间：
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(times * 1000);

		// 2、取得时间偏移量：
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);

		// 3、取得夏令时差：
		int dstOffset = cal.get(Calendar.DST_OFFSET);

		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		// 之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * long 转换为 datestr
	 * 
	 * @param dateTimes
	 * @param format
	 * @return
	 */
	public static String convertLongToString(long dateTimes, String format) {
		if (dateTimes == 0)
			return "";
		else {
			ca.setTimeInMillis(dateTimes);
			SimpleDateFormat s = new SimpleDateFormat(format);
			String dateString = s.format(ca.getTime());
			return dateString;
		}
	}

	/**
	 * sqltimestamp int 转换为 datestr
	 * 
	 * @param dateTimes
	 * @param format
	 * @return
	 */
	public static String convertIntegerToString(long dateTimes, String format) {
		if (dateTimes == 0)
			return "";
		else {
			ca.setTimeInMillis(dateTimes * 1000);
			SimpleDateFormat s = new SimpleDateFormat(format);
			String dateString = s.format(ca.getTime());
			return dateString;
		}
	}

	/**
	 * date 转为 string
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String convertDateToString(Date date, String format) {
		SimpleDateFormat s = new SimpleDateFormat(format);
		String dateString = s.format(date);
		return dateString;
	}

	/**
	 * date str 转为 long
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static long convertStringToLong(String date, String format)
			throws ParseException {
		try {
			SimpleDateFormat s = new SimpleDateFormat(format);
			ca.setTime(s.parse(date));
			return ca.getTimeInMillis() / 1000;
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * date str 转为 Date
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String date, String format)
			throws ParseException {
		try {
			SimpleDateFormat s = new SimpleDateFormat(format);
			ca.setTime(s.parse(date));
			return convertLongToDate(ca.getTimeInMillis() / 1000);
		} catch (Exception e) {
			return new Date();
		}

	}

	/**
	 * long 转换为 date
	 * 
	 * @param dateTimes
	 * @return
	 */
	public static Date convertLongToDate(long dateTimes) {
		if (dateTimes == 0)
			return new Date();
		else {
			ca.setTimeInMillis(Long.parseLong(dateTimes + "000"));
			return ca.getTime();
		}
	}

	/**
	 * 得到某年某周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		week = week - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);

		Calendar cal = (Calendar) calendar.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某周的最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		week = week - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);
		Calendar cal = (Calendar) calendar.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 取得当前日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Sunday
		return calendar.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
		return calendar.getTime();
	}

	/**
	 * 取得当前日期所在周的前一周最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfLastWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getLastDayOfWeek(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.WEEK_OF_YEAR) - 1);
	}

	/**
	 * 返回指定日期的月的第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		return calendar.getTime();
	}

	/**
	 * 返回指定年月的月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		if (year == null) {
			year = calendar.get(Calendar.YEAR);
		}
		if (month == null) {
			month = calendar.get(Calendar.MONTH);
		}
		calendar.set(year, month, 1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回指定年月的月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		if (year == null) {
			year = calendar.get(Calendar.YEAR);
		}
		if (month == null) {
			month = calendar.get(Calendar.MONTH);
		}
		calendar.set(year, month, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的上个月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfLastMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) - 1, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的季的第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfQuarter(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getFirstDayOfQuarter(calendar.get(Calendar.YEAR),
				getQuarterOfYear(date));
	}

	/**
	 * 返回指定年季的季的第一天
	 * 
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getFirstDayOfQuarter(Integer year, Integer quarter) {
		Calendar calendar = Calendar.getInstance();
		Integer month = new Integer(0);
		if (quarter == 1) {
			month = 1 - 1;
		} else if (quarter == 2) {
			month = 4 - 1;
		} else if (quarter == 3) {
			month = 7 - 1;
		} else if (quarter == 4) {
			month = 10 - 1;
		} else {
			month = calendar.get(Calendar.MONTH);
		}
		return getFirstDayOfMonth(year, month);
	}

	/**
	 * 返回指定日期的季的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfQuarter(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getLastDayOfQuarter(calendar.get(Calendar.YEAR),
				getQuarterOfYear(date));
	}

	/**
	 * 返回指定年季的季的最后一天
	 * 
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfQuarter(Integer year, Integer quarter) {
		Calendar calendar = Calendar.getInstance();
		Integer month = new Integer(0);
		if (quarter == 1) {
			month = 3 - 1;
		} else if (quarter == 2) {
			month = 6 - 1;
		} else if (quarter == 3) {
			month = 9 - 1;
		} else if (quarter == 4) {
			month = 12 - 1;
		} else {
			month = calendar.get(Calendar.MONTH);
		}
		return getLastDayOfMonth(year, month);
	}

	/**
	 * 返回指定日期的上一季的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfLastQuarter(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getLastDayOfLastQuarter(calendar.get(Calendar.YEAR),
				getQuarterOfYear(date));
	}

	/**
	 * 返回指定年季的上一季的最后一天
	 * 
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfLastQuarter(Integer year, Integer quarter) {
		Calendar calendar = Calendar.getInstance();
		Integer month = new Integer(0);
		if (quarter == 1) {
			month = 12 - 1;
		} else if (quarter == 2) {
			month = 3 - 1;
		} else if (quarter == 3) {
			month = 6 - 1;
		} else if (quarter == 4) {
			month = 9 - 1;
		} else {
			month = calendar.get(Calendar.MONTH);
		}
		return getLastDayOfMonth(year, month);
	}

	/**
	 * 返回指定日期的季度
	 * 
	 * @param date
	 * @return
	 */
	public static int getQuarterOfYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) / 3 + 1;
	}

	/**
	 * java.sql.Timestamp
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * string转 sql.timestamp
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Timestamp parseTimestamp(String dateStr) {
		return parseTimestamp(dateStr, DATE_TIME_FORMAT1);
	}

	/**
	 * 格式化日期
	 * 
	 * @param dateStr
	 *            字符型日期
	 * @param format
	 *            格式
	 * @return 返回日期
	 */
	public static Date parseDate(String dateStr, String format) {
		Date date = null;
		try {
			DateFormat df = new SimpleDateFormat(format);
			String dt = dateStr.replaceAll("-", "/");
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
						"0");
			}
			date = (Date) df.parse(dt);
		} catch (Exception e) {
		}
		return date;
	}

	public static Timestamp parseTimestamp(String dateStr,
			String format) {
		Date date = parseDate(dateStr, format);
		if (date != null) {
			long t = date.getTime();
			return new Timestamp(t);
		} else
			return null;
	}

	public static Timestamp parseTimestampSimple(String dateStr,
			String format) {
		Format f = new SimpleDateFormat(format);
		Date d;
		try {
			d = (Date) f.parseObject(dateStr);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName) {
		if (fileName == null)
			return "";

		String ext = "";
		String tempFileName = fileName.substring(fileName.lastIndexOf("/") + 1,
				fileName.length());
		int lastIndex = tempFileName.lastIndexOf(".");
		if (lastIndex >= 0) {
			ext = tempFileName.substring(lastIndex + 1).toLowerCase();
		}
		return ext;
	}

//	public static int[] getDisRandomNum(int max, int length) {
//		int N = max;
//		List<Integer> list = new ArrayList<Integer>();
//		for (int i = 0; i < N; i++) {
//			list.add(i + 1);
//		}
//		int count = N;
//		int items[] = new int[N];
//		for (int i = 0; i < length; i++) {
//			//
//			int randomInt = new Random().nextInt(count);
//			items[i] = list.get(randomInt);
//			list.remove(randomInt);
//			count--;
//			// System.out.println(items[i]);
//		}
//
//		return items;
//	}

	/**
	 * 获取星期几
	 * 
	 */
	public static String getWeekday(long date) {
		String result = "";

		Calendar c = Calendar.getInstance();
		c.setTime(new Date(date));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 1:
			result = "星期日";
			break;
		case 2:
			result = "星期一";
			break;
		case 3:
			result = "星期二";
			break;
		case 4:
			result = "星期三";
			break;
		case 5:
			result = "星期四";
			break;
		case 6:
			result = "星期五";
			break;
		case 7:
			result = "星期六";
			break;
		}

		return result;
	}

	public static String getFormatDateTimeFromUnixTime(Integer unixtime,
			String format) {
		String tmpdatestr = "";
		try {
			if ((unixtime != null) && (unixtime != 0)) {
				Timestamp tmpdate = new Timestamp(unixtime * 1000L);
				SimpleDateFormat tmpfmt = new SimpleDateFormat(format);
				tmpdatestr = tmpfmt.format(tmpdate);
			}
		} catch (Exception ex) {

		}
		return tmpdatestr;
	}

	public static String getFormatDateTimeFromUnixTime(int unixtime,
			String format) {
		String tmpdatestr = "";
		try {
			if (unixtime != 0) {
				Timestamp tmpdate = new Timestamp(unixtime * 1000L);
				SimpleDateFormat tmpfmt = new SimpleDateFormat(format);
				tmpdatestr = tmpfmt.format(tmpdate);
			}
		} catch (Exception ex) {

		}
		return tmpdatestr;
	}

	/**
	 * 获取当天前N天
	 * 
	 * @param day
	 * @return
	 */
	public static String getCalendar(int day) {
		Date dNow = new Date(); // 当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = null;
		if (day == 1) {
			d = sdf.format(dNow) + " 00:00:00";
		} else {
			day = day - 1;
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(dNow);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -day); // 设置为前一天
			dBefore = calendar.getTime(); // 得到前一天的时间
			d = sdf.format(dBefore) + " 00:00:00";
		}

		return d;
	}

	public static String getCalendarN(int day) {
		Date dNow = new Date(); // 当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = null;
		if (day == 1) {
			d = sdf.format(dNow);
		} else {
			day = day - 1;
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(dNow);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -day); // 设置为前一天
			dBefore = calendar.getTime(); // 得到前一天的时间
			d = sdf.format(dBefore);
		}

		return d;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getDay() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(now);
	}

	public static String getDataTime(String str) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(now);
	}

	/*
	 * 获取请求头部的类型
	 */
	public static String getContentType(HttpServletRequest request) {
		Enumeration e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = request.getHeader(name);
			if("content-type".equals(name)){
				
				log.info("request type is: " + value);
				
				if(value.contains("text/plain") || value.contains("application/x-www-form-urlencoded")){
					return TEXT;
				}
				else if(value.contains("application/json")){
					return JSON;
				}
					
			}
		}
		return "";
	}
	
	/**
	 * 相差多少天
	 * @param date
	 * @return
	 */
	public static Long time_difference(String date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
		    Date d1 = new Date();
		    Date d2 = df.parse(date);
		    long diff = d1.getTime() - d2.getTime();
		    long days = diff / (1000 * 60 * 60 * 24);
		    return days;
		}catch (Exception e){
			return null;
		}
	}

	public static String getUTF8String(String source) {
		String tmpret = "";
		try {
			if (source.indexOf("\\x") != -1) {
				String sourceArr[] = source.split("\\\\"); // 分割拿到形如 xE9 的16进制数据
				if (sourceArr.length > 1) {
					byte[] byteArr = new byte[sourceArr.length - 1];
					for (int i = 1; i < sourceArr.length; i++) {
						Integer hexInt = Integer.decode("0" + sourceArr[i]);
						byteArr[i - 1] = hexInt.byteValue();
					}
					tmpret = new String(byteArr, "UTF-8");
				} else
					tmpret = source;
			} else
				tmpret = source;
			byte[] utf8 = tmpret.getBytes("UTF-8");
			tmpret = new String(utf8, "UTF-8");
			//System.out.println(tmpret);

		} catch (Exception ex) {
			System.out.println(ex);
		}
		return tmpret;
	}
	
	public static String getRandomStr(int length){
		String[] array = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v"};
		Random rand = new Random();
		for (int i = 30; i > 1; i--) {
			//将数组下标随机排列
		    int index = rand.nextInt(i);
		    String tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		StringBuffer sb = new StringBuffer();
		//循环取出六位数
		for(int i = 0; i < length;i++){
			sb.append(array[i]);
		}
		return sb.toString();
	}
	
	public static String getRandomNum(int length){
		String[] array = {"0","1","2","3","4","5","6","7","8","9"};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			//将数组下标随机排列
		    int index = rand.nextInt(i);
		    String tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		StringBuffer sb = new StringBuffer();
		//循环取出六位数
		for(int i = 0; i < length;i++){
			sb.append(array[i]);
		}
		return sb.toString();
	}
	
	public static String stringFormat(int id) {
		String result = String.format("%04d", id);  
		return result;
	}
	
	public static int[] getDisRandomNum(int max,int length) {
		int[] arr = new int[length];
		
		for (int i = 0; i < length; i++) {
			arr[i] = (int) (Math.random() * max);
			
			for (int j = 0; j < i; j++) {
				if (arr[j] == arr[i]) {
					i--;
					break;
				}
			}
		}
		return arr;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < getDisRandomNum(50, 5).length; i++) {
			System.out.println(getDisRandomNum(50, 5)[i]);
		}
	}
	

}
