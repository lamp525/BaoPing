package com.ncgroup.bp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: 日期处理类
 * @author: Lin
 * @created: 2018年9月13日 下午4:45:11
 * @version: 1.0
 */
public class DateHelper {

	/**
	 * @description: 格式化时间
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getFormatTime(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 获取当前时间 （默认格式 "yyyy-MM-dd HH:mm:ss"）
	 * 
	 */
	public static String now() {
		return now("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取当前时间
	 * 
	 * @param pattern "yyyy-MM-dd HH:mm:ss"
	 */
	public static String now(String pattern) {
		Calendar calendar = Calendar.getInstance();
		return new SimpleDateFormat(pattern).format(calendar.getTime());
	}

	/**
	 * 判断当前时间是否为交易日
	 */
	public static Boolean isTradingDay() {
		int wd = Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
		if (wd == 6 || wd == 7)
			return false;
		else
			return true;
	}

	/**
	 * 获取当前时间的星期
	 */
	public static Integer getWeekDayByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
	}

	/**
	 * 获取当前时间的年
	 */
	public static Integer getYearByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}

	/**
	 * 获取当前时间的月
	 */
	public static Integer getMonthByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.MONTH)) + 1;
	}

	/**
	 * 获取当前时间的日
	 */
	public static Integer getDayByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 获取当前时间的小时
	 */
	public static Integer getHourByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
	}

	/**
	 * 获取当前时间的小时
	 * 
	 * @param date
	 */
	public static Integer getHourByCurrentTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return Integer.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	}

	/**
	 * 获取当前时间的分钟
	 * 
	 * @param date
	 */
	public static Integer getMinuteByCurrentTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return Integer.valueOf(calendar.get(Calendar.MINUTE));
	}
	
	/**
	 * 获取当前时间的分钟
	 * 
	 * @param date
	 */
	public static Integer getMinuteByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
	}

	/**
	 * 获取当前时间之前或之后几年 year
	 */
	public static String getTimeByYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, year);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * 获取当前时间之前或之后几月 month
	 */
	public static String getTimeByMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * 获取当前时间之前或之后几天 day
	 */
	public static String getTimeByDay(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * 获取当前时间之前或之后几小时 hour
	 */
	public static String getTimeByHour(int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

	}

	/**
	 * 获取当前时间之前或之后几分钟 minute
	 */
	public static String getTimeByMinute(Date date, int minute, String pattern) {
		if ((pattern == null) || (pattern.length() == 0)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return new SimpleDateFormat(pattern).format(calendar.getTime());
	}

	/**
	 * 获取当前时间之前或之后几分钟 minute
	 */
	public static String getTimeByMinute(int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minute);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * 获取指定HH:ss时间之前或之后几分钟 minute
	 */
	public static String getTimeByMinute(String curTime, int minute) {
		if (curTime != null && curTime.length() == 5) {
			Calendar calendar = Calendar.getInstance();
			int curHour = Integer.parseInt(curTime.split(":")[0].trim());
			int curMin = Integer.parseInt(curTime.split(":")[1].trim());
			calendar.set(Calendar.HOUR_OF_DAY, curHour);
			calendar.set(Calendar.MINUTE, curMin);
			calendar.add(Calendar.MINUTE, minute);

			return new SimpleDateFormat("HH:mm").format(calendar.getTime());
		} else
			return "";
	}

}
