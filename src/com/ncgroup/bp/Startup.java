package com.ncgroup.bp;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;

/**
 * @description: ��������
 * @author: Lin
 * @created: 2018��9��13�� ����4:44:28
 * @version: 1.0
 */
public class Startup {
	private static final String MY_APPID = "5b8508fa";

	/**
	 * @Description: ��������ں���
	 * @param args
	 */
	public static void main(String[] args) {
		Log.info("����������");
		// ����ʱ��У׼
		TimeCalibrate.run();

		try {
			// ��ʼ��Ѷ�������ϳ�SDK
			SpeechUtility.createUtility(SpeechConstant.APPID + "=" + MY_APPID);

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// ��ʱ���������ʵ��
						TimerManager tm = TimerManager.getInstance();
						int weekDay = -1;
						while (true) {
							weekDay = DateHelper.getWeekDayByCurrentTime();
							// ��ĩֹͣ����
							if (weekDay == 7 || weekDay == 1) {
								if (!tm.isTimerTaskStopped())
									tm.stopTimerTask();
								// �����տ�ʼ����
							} else {
								if (tm.isTimerTaskStopped())
									tm.startTimerTask();
							}
							// �����߳�����4Сʱ
							ThreadUtil.threadSleep(4 * 60 * 60 * 1000);
						}
					} catch (Exception e) {
						Log.error("�����߳��쳣��", e);
					}
				}
			}).start();

		} catch (Exception e) {
			Log.error("�������쳣��", e);
		}
	}

}
