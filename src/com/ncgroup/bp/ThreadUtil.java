
package com.ncgroup.bp;

/**
 * @description: 线程处理通用类
 * @author: Lin
 * @create: 2018年9月14日
 * @version: 1.0
 */
public class ThreadUtil {

	/**
	 * @description: 禁止实例化
	 * 
	 */
	private ThreadUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @description: 线程休眠
	 * @param ms (单位：毫秒)
	 * @throws InterruptedException
	 */
	public static void threadSleep(long ms) throws InterruptedException {
		Log.info("当前线程将休眠：" + DateHelper.formatTime(ms) + "！");
		Thread.sleep(ms);
		Log.info("当前线程继续执行！");
	}

}
