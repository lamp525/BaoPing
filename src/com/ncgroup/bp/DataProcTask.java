package com.ncgroup.bp;

import java.util.Date;
import java.util.TimerTask;

/**
 * @Description: ���ݴ�������
 * @author: Lin
 * @version:
 * @date: 2018��9��12�� ����4:42:22
 */
public class DataProcTask extends TimerTask {

	/**
	 * ���ݴ������
	 */
	@Override
	public void run() {
		try {
			Date now = new Date();
			int iTime = DateHelper.getHourByCurrentTime(now) * 100 + DateHelper.getMinuteByCurrentTime(now);

			// 1�������ݴ���
			if (iTime >= 925 && iTime <= 934) {
				DataSource.dataProc1M();
			}
			// 5�������ݴ���
			else if ((iTime >= 935 && iTime < 1132) || (iTime >= 1305 && iTime < 1503)) {
				DataSource.dataProc5M();
			}

		} catch (Exception e) {
			Log.error("���ݴ��������쳣��", e);
		}
	}

}
