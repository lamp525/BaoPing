package com.ncgroup.bp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * @description: 网络时间校准
 * @author: Lin
 * @create: 2018年9月20日 下午3:54:20
 * @version: 1.0
 */
public final class TimeCalibrate {

	private final static String URL = "http://www.baidu.com";

	/**
	 * @description: 禁止实例化
	 * 
	 */
	private TimeCalibrate() {
		// TODO Auto-generated constructor stub
	}

	public static boolean run() {
		boolean ret = false;
		try {
			Log.info(" 本地系统时间：" + DateHelper.now());

			Date date = getNetworkTime(URL);
			if (date != null) {
				Date newDate = DateHelper.getTimeBySecond(date, 5);
				String strTime = DateHelper.getFormatTime(newDate, "HH:mm:ss");
				String strDate = DateHelper.getFormatTime(newDate, "yyyy-MM-dd");
				Log.info("北京时间：" + strDate + " " + strTime);

				Runtime runTime = Runtime.getRuntime();
				runTime.exec("cmd /c time " + strTime);
				runTime.exec("cmd /c date " + strDate);

				Thread.sleep(3 * 1000);
				Log.info(" 本地系统时间：" + DateHelper.now());
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
			Log.error("服务器地址设置有误！", e);
		} catch (IOException e) {
			Log.error("校验服务器连接失败！", e);
		}
		return date;
	}

}
