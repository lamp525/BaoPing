package com.ncgroup.bp;

import java.util.Timer;

/**
 * @Description: ��ʱ���������
 * @author: Lin
 * @version:
 * @date: 2018��9��13�� ����1:45:34
 */
public class TimerManager {

	/**
	 * @Description: ����ģʽ
	 */
	private static TimerManager _tm = null;

	private TimerManager() {
	}

	public static TimerManager getInstance() {
		if (_tm == null) {
			_tm = new TimerManager();
		}
		return _tm;
	}

	/**
	 * ��ʱ��
	 */
	private Timer _timer = new Timer();

	/**
	 * ���ݲ�������
	 */
	private BroadcastTask _broadcastTask = null;

	/**
	 * ���ݴ�������
	 */
	private DataProcTask _dataProcTask = null;

	private final int PLAY_PERIOD = 2;
	private final int DATA_PERIOD = 10;
	private boolean _isStopped = true;
	
	public boolean isTimerTaskStopped()
	{
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

		_timer.schedule(_dataProcTask, 0, PLAY_PERIOD);
		Log.info("��ʼִ�����ݴ�������");
		_timer.schedule(_broadcastTask, 0, DATA_PERIOD);
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
