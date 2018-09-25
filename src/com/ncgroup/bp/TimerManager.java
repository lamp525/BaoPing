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

	public static enum ExecMode {
		SERVER, CLIENT, ALL
	}

	private ExecMode _execMode;

	/**
	 * @description: �趨����ģʽ
	 * @param mode
	 */
	public void setExecMode(ExecMode mode) {
		_execMode = mode;
	}

	/**
	 * @description: ��ȡ����ģʽ
	 * @return the _execMode
	 */
	@SuppressWarnings("unused")
	private ExecMode getExecMode() {
		return _execMode;
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

		switch (_execMode) {
		case SERVER: {
			if (_dataProcTask == null)
				_dataProcTask = new DataProcTask();
			_timer.schedule(_dataProcTask, 0, DATA_PERIOD);
			Log.info("��ʼִ�����ݴ�������");

			break;
		}
		case CLIENT: {
			if (_broadcastTask == null)
				_broadcastTask = new BroadcastTask();

			_timer.schedule(_broadcastTask, 0, PLAY_PERIOD);
			Log.info("��ʼִ�����ݲ�������");

			break;
		}
		case ALL:
		default: {
			if (_dataProcTask == null)
				_dataProcTask = new DataProcTask();
			_timer.schedule(_dataProcTask, 0, DATA_PERIOD);
			Log.info("��ʼִ�����ݴ�������");

			if (_broadcastTask == null)
				_broadcastTask = new BroadcastTask();

			_timer.schedule(_broadcastTask, 0, PLAY_PERIOD);
			Log.info("��ʼִ�����ݲ�������");
			break;
		}
		}

		_isStopped = false;
	}

	/**
	 * @Description: ȡ����ʱ����
	 *
	 */
	public void stopTimerTask() {
		_isStopped = false;

		switch (_execMode) {
		case SERVER: {
			_dataProcTask.cancel();
			_dataProcTask = null;
			Log.info("ȡ�����ݴ�������");

			break;
		}
		case CLIENT: {
			_broadcastTask.cancel();
			_broadcastTask = null;
			Log.info("ȡ�����ݲ�������");

			break;
		}
		case ALL:
		default: {
			_dataProcTask.cancel();
			_dataProcTask = null;
			Log.info("ȡ�����ݴ�������");

			_broadcastTask.cancel();
			_broadcastTask = null;
			Log.info("ȡ�����ݲ�������");

			break;
		}
		}

		_isStopped = true;
	}

}
