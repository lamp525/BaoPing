package com.ncgroup.bp;

import java.util.Timer;

/**
 * @Description: 定时任务管理器
 * @author: Lin
 * @version:
 * @date: 2018年9月13日 下午1:45:34
 */
public class TimerManager {

	/**
	 * @Description: 单例模式
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
	 * 定时器
	 */
	private Timer _timer = new Timer();

	/**
	 * 数据播报任务
	 */
	private BroadcastTask _broadcastTask = null;

	/**
	 * 数据处理任务
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
	 * @Description: 启动定时任务
	 *
	 */
	public void startTimerTask() {
		_timer.purge();
		if (_broadcastTask == null)
			_broadcastTask = new BroadcastTask();
		if (_dataProcTask == null)
			_dataProcTask = new DataProcTask();

		_timer.schedule(_dataProcTask, 0, PLAY_PERIOD);
		Log.info("开始执行数据处理任务！");
		_timer.schedule(_broadcastTask, 0, DATA_PERIOD);
		Log.info("开始执行数据播报任务！");
		
		_isStopped = false;
	}

	/**
	 * @Description: 取消定时任务
	 *
	 */
	public void stopTimerTask() {
		_broadcastTask.cancel();
		_broadcastTask = null;
		Log.info("取消数据处理任务！");
		_dataProcTask.cancel();
		_dataProcTask = null;
		Log.info("取消数据播报任务！");
		
		_isStopped = true;
	}
}
