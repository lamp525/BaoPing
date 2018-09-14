
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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @description: �߳�����
	 * @param ms (��λ������)
	 * @throws InterruptedException
	 */
	public static void threadSleep(long ms) throws InterruptedException {
		Log.info("��ǰ�߳̽����ߣ�" + DateHelper.formatTime(ms) + "��");
		Thread.sleep(ms);
		Log.info("��ǰ�̼߳���ִ�У�");
	}

}
