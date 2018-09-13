package com.ncgroup.bp;

import java.util.Date;
import java.util.TimerTask;

/**
 * @Description: 数据处理任务
 * @author: Lin
 * @version:
 * @date: 2018年9月12日 下午4:42:22
 */
public class DataProcTask extends TimerTask {

	/**
	 * 数据处理入口
	 */
	@Override
	public void run() {
		try {
			Date now = new Date();
			int iTime = DateHelper.getHourByCurrentTime(now) * 100 + DateHelper.getMinuteByCurrentTime(now);

			// 1分钟数据处理
			if (iTime >= 925 && iTime <= 934) {
				DataSource.dataProc1M();
			}
			// 5分钟数据处理
			else if ((iTime >= 935 && iTime < 1132) || (iTime >= 1305 && iTime < 1503)) {
				DataSource.dataProc5M();
			}

		} catch (Exception e) {
			Log.error("数据处理任务异常！", e);
		}
	}

}
