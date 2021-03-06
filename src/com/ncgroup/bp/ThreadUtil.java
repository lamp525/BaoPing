
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
	}

	/**
	 * @description: 线程休眠
	 * @param name (调用线程名称)
	 * @param ms   （单位毫秒）
	 * @throws InterruptedException
	 */
	public static void threadSleep(String name, long ms) throws InterruptedException {
		Log.info("线程[" + name + "] 将休眠：" + DateHelper.formatTime(ms) + "！");
		Thread.sleep(ms);
		Log.info("线程[" + name + "]继续执行！");
	}

}
