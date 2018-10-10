
package com.ncgroup.bp;

/**
 * @description: �̴߳���ͨ����
 * @author: Lin
 * @create: 2018��9��14��
 * @version: 1.0
 */
public class ThreadUtil {

	/**
	 * @description: ��ֹʵ����
	 * 
	 */
	private ThreadUtil() {
	}

	/**
	 * @description: �߳�����
	 * @param name (�����߳�����)
	 * @param ms   ����λ���룩
	 * @throws InterruptedException
	 */
	public static void threadSleep(String name, long ms) throws InterruptedException {
		Log.info("�߳�[" + name + "] �����ߣ�" + DateHelper.formatTime(ms) + "��");
		Thread.sleep(ms);
		Log.info("�߳�[" + name + "]����ִ�У�");
	}

}
