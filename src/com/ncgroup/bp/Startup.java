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
		// ����ʱ��У׼����ǰ�û�Ϊϵͳ����Ա����Ч��
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
						/* ���ó�������ģʽ */
						TimerManager.ExecMode mode = null;
						if (args.length == 0)
							mode = TimerManager.ExecMode.ALL;
						else {
							if (args[0].equals("c") || args[0].equals("C"))
								mode = TimerManager.ExecMode.CLIENT;
							else if (args[0].equals("s") || args[0].equals("S"))
								mode = TimerManager.ExecMode.SERVER;
							else
								mode = TimerManager.ExecMode.ALL;
						}
						tm.setExecMode(mode);
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
