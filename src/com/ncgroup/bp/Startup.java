package com.ncgroup.bp;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;

/**
 * @description: 启动程序
 * @author: Lin
 * @created: 2018年9月13日 下午4:44:28
 * @version: 1.0
 */
public class Startup {
	private static final String MY_APPID = "5b8508fa";

	/**
	 * @Description: 主程序入口函数
	 * @param args
	 */
	public static void main(String[] args) {
		Log.info("程序启动！");
		// 网络时间校准
		TimeCalibrate.run();

		try {
			// 初始化讯飞语音合成SDK
			SpeechUtility.createUtility(SpeechConstant.APPID + "=" + MY_APPID);

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// 定时任务管理器实例
						TimerManager tm = TimerManager.getInstance();
						int weekDay = -1;
						while (true) {
							weekDay = DateHelper.getWeekDayByCurrentTime();
							// 周末停止任务
							if (weekDay == 7 || weekDay == 1) {
								if (!tm.isTimerTaskStopped())
									tm.stopTimerTask();
								// 工作日开始任务
							} else {
								if (tm.isTimerTaskStopped())
									tm.startTimerTask();
							}
							// 工作线程休眠4小时
							ThreadUtil.threadSleep(4 * 60 * 60 * 1000);
						}
					} catch (Exception e) {
						Log.error("工作线程异常！", e);
					}
				}
			}).start();

		} catch (Exception e) {
			Log.error("主程序异常！", e);
		}
	}

}
