package com.ncgroup.bp;

import java.util.Date;
import java.util.TimerTask;

/**
 * @description: 数据处理任务
 * @author: Lin
 * @created: 2018年9月13日 下午3:18:24
 * @version: 1.0
 */
public class DataProcTask extends TimerTask {
	private boolean oneTimeFlag = false;

	/**
	 * 启动数据处理任务
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
			// 盘后数据处理
			else if (iTime > 1540 && iTime < 1730) {
				if (oneTimeFlag == false) {
					Log.info("执行盘后数据处理！");
					DataSource.closeHourProc1M();
					DataSource.closeHourProc5M();
					oneTimeFlag = true;
					Log.info("盘后数据处理完成！");
				}
				// 重置盘后数据处理标志
			} else {
				if (oneTimeFlag == true)
					oneTimeFlag = false;
			}

		} catch (Exception e) {
			Log.error("数据处理任务异常！", e);
		}
	}

}
