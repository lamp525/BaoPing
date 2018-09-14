package com.ncgroup.bp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: ���ڴ�����
 * @author: Lin
 * @created: 2018��9��13�� ����4:45:11
 * @version: 1.0
 */
public class DateHelper {

	/**
	 * @description: ��ʽ��ʱ��
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getFormatTime(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * ��ȡ��ǰʱ�� ��Ĭ�ϸ�ʽ "yyyy-MM-dd HH:mm:ss"��
	 * 
	 */
	public static String now() {
		return now("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ��ȡ��ǰʱ��
	 * 
	 * @param pattern "yyyy-MM-dd HH:mm:ss"
	 */
	public static String now(String pattern) {
		Calendar calendar = Calendar.getInstance();
		return new SimpleDateFormat(pattern).format(calendar.getTime());
	}

	/**
	 * �жϵ�ǰʱ���Ƿ�Ϊ������
	 */
	public static Boolean isTradingDay() {
		int wd = Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
		if (wd == 6 || wd == 7)
			return false;
		else
			return true;
	}

	/**
	 * ��ȡ��ǰʱ�������
	 */
	public static Integer getWeekDayByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
	}

	/**
	 * ��ȡ��ǰʱ�����
	 */
	public static Integer getYearByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}

	/**
	 * ��ȡ��ǰʱ�����
	 */
	public static Integer getMonthByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.MONTH)) + 1;
	}

	/**
	 * ��ȡ��ǰʱ�����
	 */
	public static Integer getDayByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * ��ȡ��ǰʱ���Сʱ
	 */
	public static Integer getHourByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
	}

	/**
	 * ��ȡ��ǰʱ���Сʱ
	 * 
	 * @param date
	 */
	public static Integer getHourByCurrentTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return Integer.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	}

	/**
	 * ��ȡ��ǰʱ��ķ���
	 * 
	 * @param date
	 */
	public static Integer getMinuteByCurrentTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return Integer.valueOf(calendar.get(Calendar.MINUTE));
	}
	
	/**
	 * ��ȡ��ǰʱ��ķ���
	 * 
	 * @param date
	 */
	public static Integer getMinuteByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
	}

	/**
	 * ��ȡ��ǰʱ��֮ǰ��֮���� year
	 */
	public static String getTimeByYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, year);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * ��ȡ��ǰʱ��֮ǰ��֮���� month
	 */
	public static String getTimeByMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * ��ȡ��ǰʱ��֮ǰ��֮���� day
	 */
	public static String getTimeByDay(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * ��ȡ��ǰʱ��֮ǰ��֮��Сʱ hour
	 */
	public static String getTimeByHour(int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

	}

	/**
	 * ��ȡ��ǰʱ��֮ǰ��֮�󼸷��� minute
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
	 * ��ȡ��ǰʱ��֮ǰ��֮�󼸷��� minute
	 */
	public static String getTimeByMinute(int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minute);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * ��ȡָ��HH:ssʱ��֮ǰ��֮�󼸷��� minute
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
