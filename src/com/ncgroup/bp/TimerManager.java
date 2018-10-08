package com.ncgroup.bp;

import java.util.Timer;

/**
 * @description: 定时任务管理器
 * @author: Lin
 * @created: 2018年9月13日 下午4:43:59
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
	 * @description: 设定运行模式
	 * @param mode
	 */
	public void setExecMode(ExecMode mode) {
		_execMode = mode;
	}

	/**
	 * @description: 获取运行模式
	 * @return the _execMode
	 */
	@SuppressWarnings("unused")
	private ExecMode getExecMode() {
		return _execMode;
	}

	/**
	 * @Description: 单例模式
	 */
	public static TimerManager getInstance() {
		if (_tm == null) {
			_tm = new TimerManager();
		}
		return _tm;
	}

	/**
	 * @Description: 定时任务是否停止
	 * @return
	 */
	public boolean isTimerTaskStopped() {
		return _isStopped;
	}

	/**
	 * @Description: 启动定时任务
	 *
	 */
	public void startTimerTask() {
		_timer.purge();
		switch (_execMode) {
		case SERVER:
			startDataProc();
			break;
		case CLIENT:
			startBroadcast();
			break;
		case ALL:
		default:
			startDataProc();
			startBroadcast();
			break;
		}
		_isStopped = false;
	}

	/**
	 * @Description: 取消定时任务
	 *
	 */
	public void stopTimerTask() {
		_isStopped = false;
		switch (_execMode) {
		case SERVER:
			stopDataProc();
			break;
		case CLIENT:
			stopBroadcast();
			break;
		case ALL:
		default:
			stopBroadcast();
			stopDataProc();
			break;
		}
		_isStopped = true;
	}

	/**
	 * @description: 启动数据播报任务
	 *
	 */
	private void startBroadcast() {
		if (_broadcastTask == null)
			_broadcastTask = new BroadcastTask();

		_timer.schedule(_broadcastTask, 0, PLAY_PERIOD);
		Log.info("启动数据播报任务！");
	}

	/**
	 * @description: 启动数据处理任务
	 *
	 */
	private void startDataProc() {
		if (_dataProcTask == null)
			_dataProcTask = new DataProcTask();
		_timer.schedule(_dataProcTask, 0, DATA_PERIOD);
		Log.info("启动数据处理任务！");
	}

	/**
	 * @description: 取消数据播报任务
	 *
	 */
	private void stopBroadcast() {
		if (_broadcastTask != null) {
			_broadcastTask.cancel();
			_broadcastTask = null;
			Log.info("取消数据播报任务！");
		}
	}

	/**
	 * @description: 取消数据播报任务
	 *
	 */
	private void stopDataProc() {
		if (_broadcastTask != null) {
			_broadcastTask.cancel();
			_broadcastTask = null;
			Log.info("取消数据播报任务！");
		}
	}
}
