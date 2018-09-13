package com.ncgroup.bp;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;

/**
 * @Description: 启动程序
 * @author: Lin
 * @version:
 * @date: 2018年9月12日 下午4:14:16
 */
public class Startup {
	private static final String MY_APPID = "5b8508fa";

	/**
	 * @Description: 主程序入口函数
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Log.info("程序启动！");

			// 初始化讯飞语音合成SDK
			SpeechUtility.createUtility(SpeechConstant.APPID + "=" + MY_APPID);
			Speech s = new Speech();
			s.play("语音合成播放测试");	

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
							if (weekDay == 6 || weekDay == 1) {
								if (!tm.isTimerTaskStopped())
									tm.stopTimerTask();
								// 工作日开始任务
							} else {
								if (tm.isTimerTaskStopped())
									tm.startTimerTask();
							}
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
