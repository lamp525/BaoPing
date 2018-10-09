package com.ncgroup.bp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * @description: ����ʱ��У׼
 * @author: Lin
 * @create: 2018��9��20�� ����3:54:20
 * @version: 1.0
 */
public final class TimeCalibrate {

	private final static String URL = "http://www.baidu.com";

	/**
	 * @description: ��ֹʵ����
	 * 
	 */
	private TimeCalibrate() {
		// TODO Auto-generated constructor stub
	}

	public static boolean run() {
		boolean ret = false;
		try {
			Log.info(" ����ϵͳʱ�䣺" + DateHelper.now());

			Date date = getNetworkTime(URL);
			if (date != null) {
				Date newDate = DateHelper.getTimeBySecond(date, 5);
				String strTime = DateHelper.getFormatTime(newDate, "HH:mm:ss");
				String strDate = DateHelper.getFormatTime(newDate, "yyyy-MM-dd");
				Log.info("����ʱ�䣺" + strDate + " " + strTime);

				Runtime runTime = Runtime.getRuntime();
				runTime.exec("cmd /c time " + strTime);
				runTime.exec("cmd /c date " + strDate);

				Thread.sleep(3 * 1000);
				Log.info(" ����ϵͳʱ�䣺" + DateHelper.now());
			}
		} catch (Exception e) {
			Log.error("", e);
		}
		return ret;

	}

	public static Date getNetworkTime(String strUrl) {
		Date date = null;
		try {
			URL url = new URL(strUrl);
			URLConnection urlc = url.openConnection();
			urlc.connect();
			long time = urlc.getDate();
			date = new Date(time);
		} catch (MalformedURLException e) {
			Log.error("��������ַ��������", e);
		} catch (IOException e) {
			Log.error("У�����������ʧ�ܣ�", e);
		}
		return date;
	}

}
