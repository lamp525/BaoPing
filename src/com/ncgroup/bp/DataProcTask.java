package com.ncgroup.bp;

import java.util.Date;
import java.util.TimerTask;

/**
 * @description: ���ݴ�������
 * @author: Lin
 * @created: 2018��9��13�� ����3:18:24
 * @version: 1.0
 */
public class DataProcTask extends TimerTask {
	private boolean oneTimeFlag = false;

	/**
	 * �������ݴ�������
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
			// �̺����ݴ���
			else if (iTime > 1540 && iTime < 1730) {
				if (oneTimeFlag == false) {
					Log.info("ִ���̺����ݴ���");
					DataSource.closeHourProc1M();
					DataSource.closeHourProc5M();
					oneTimeFlag = true;
					Log.info("�̺����ݴ�����ɣ�");
				}
				// �����̺����ݴ����־
			} else {
				if (oneTimeFlag == true)
					oneTimeFlag = false;
			}

		} catch (Exception e) {
			Log.error("���ݴ��������쳣��", e);
		}
	}

}
