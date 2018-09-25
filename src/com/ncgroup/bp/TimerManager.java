package com.ncgroup.bp;

import java.util.Timer;

/**
 * @description: ��ʱ���������
 * @author: Lin
 * @created: 2018��9��13�� ����4:43:59
 * @version: 1.0
 */
public class TimerManager {
	private static TimerManager _tm = null;
	private Timer _timer = new Timer();
	private BroadcastTask _broadcastTask = null;
	private DataProcTask _dataProcTask = null;
	private final int PLAY_PERIOD = 2 * 1000;
	private final int DATA_PERIOD = 9 * 1000;
	private boolean _isStopped = true;

	private TimerManager() {
	}

	/**
	 * @Description: ����ģʽ
	 */
	public static TimerManager getInstance() {
		if (_tm == null) {
			_tm = new TimerManager();
		}
		return _tm;
	}

	/**
	 * @Description: ��ʱ�����Ƿ�ֹͣ
	 * @return
	 */
	public boolean isTimerTaskStopped() {
		return _isStopped;
	}

	/**
	 * @Description: ������ʱ����
	 *
	 */
	public void startTimerTask() {
		_timer.purge();
		if (_broadcastTask == null)
			_broadcastTask = new BroadcastTask();

		if (_dataProcTask == null)
			_dataProcTask = new DataProcTask();

		_timer.schedule(_dataProcTask, 0, DATA_PERIOD);
		Log.info("��ʼִ�����ݴ�������");

		_timer.schedule(_broadcastTask, 0, PLAY_PERIOD);
		Log.info("��ʼִ�����ݲ�������");

		_isStopped = false;
	}

	/**
	 * @Description: ȡ����ʱ����
	 *
	 */
	public void stopTimerTask() {
		_broadcastTask.cancel();
		_broadcastTask = null;
		Log.info("ȡ�����ݴ�������");

		_dataProcTask.cancel();
		_dataProcTask = null;
		Log.info("ȡ�����ݲ�������");

		_isStopped = true;
	}
}
