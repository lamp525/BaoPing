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
	 * @description: 获取当前时间 （默认格式 "yyyy-MM-dd HH:mm:ss"）
	 * @return
	 */
	public static String now() {
		return now("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @description: 获取当前时间
	 * @param pattern
	 * @return
	 */
	public static String now(String pattern) {
		Calendar calendar = Calendar.getInstance();
		return new SimpleDateFormat(pattern).format(calendar.getTime());
	}

	/**
	 * @description: 判断当前时间是否为交易日
	 * @return
	 */
	public static Boolean isTradingDay() {
		int wd = Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
		if (wd == 1 || wd == 7)
			return false;
		else
			return true;
	}

	/**
	 * @description: 获取当前时间的星期
	 * @return
	 */
	public static Integer getWeekDayByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
	}

	/**
	 * @description: 获取当前时间的年
	 * @return
	 */
	public static Integer getYearByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}

	/**
	 * @description: 获取当前时间的月
	 * @return
	 */
	public static Integer getMonthByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.MONTH)) + 1;
	}

	/**
	 * @description: 获取当前时间的日
	 * @return
	 */
	public static Integer getDayByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * @description: 获取当前时间的小时
	 * @return
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
	 * @description: 获取当前时间之前或之后几年
	 * @param year
	 * @return
	 */
	public static String getTimeByYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, year);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * @description: 获取当前时间之前或之后几月
	 * @param month
	 * @return
	 */
	public static String getTimeByMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * @description: 获取当前时间之前或之后几天
	 * @param day
	 * @return
	 */
	public static String getTimeByDay(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * @description: 获取当前时间之前或之后几小时
	 * @param hour
	 * @return
	 */
	public static String getTimeByHour(int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

	}

	/**
	 * @description: 获取当前时间之前或之后几分钟
	 * @param date
	 * @param minute
	 * @param pattern
	 * @return
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
	 * @description: 获取当前时间之前或之后几分钟
	 * @param minute
	 * @return
	 */
	public static String getTimeByMinute(int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minute);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * @description: 获取指定HH:ss时间之前或之后几分钟
	 * @param curTime
	 * @param minute
	 * @return
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

	/**
	 * @description: 获取当前时间之前或之后几秒
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date getTimeBySecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * @description: 毫秒转化为 天、小时、分钟、秒、毫秒
	 * @param ms
	 * @return
	 */
	public static String formatTime(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day; // 天
		String strHour = hour < 10 ? "0" + hour : "" + hour;// 小时
		String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
		String strSecond = second < 10 ? "0" + second : "" + second;// 秒
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;// 毫秒
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

		return strDay + "天" + strHour + "小时" + strMinute + "分钟" + strSecond + "秒" + strMilliSecond + "毫秒";
	}

}
