package com.ncgroup.bp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

/**
 * @description: 数据播报任务
 * @author: Lin
 * @created: 2018年9月13日 下午4:43:14
 * @version: 1.0
 */
public class BroadcastTask extends TimerTask {
	private Speech _speech = new Speech();
	private final String COMMA = "，";
	private final String PERIOD = "。";
	private final BigDecimal HUNDRED = new BigDecimal("100");
	private String _lastTime = "";

	/**
	 * 启动播报任务
	 */
	@Override
	public void run() {
		try {
			Date now = new Date();
			String curTime = DateHelper.getFormatTime(now, "HH:mm");
			int iTime = DateHelper.getHourByCurrentTime(now) * 100 + DateHelper.getMinuteByCurrentTime(now);

			// 交易提示信息
			if (iTime >= 915 && iTime <= 1500)
				notice(curTime);

			// 1分钟数据播报
			if (iTime >= 925 && iTime <= 934) {
				if (DataSource.checkUpdate1M())
					tradeRemind1M();
			}

			// 5分钟数据播报
			if ((iTime >= 935 && iTime < 1132) || (iTime >= 1305 && iTime < 1503)) {
				if (DataSource.checkUpdate5M())
					tradeRemind5M();
			}
		} catch (Exception e) {
			Log.error("数据播报任务异常！", e);
		}
	}

	/**
	 * @description: 交易时间节点信息提醒
	 * @param time
	 */
	private void notice(String time) {
		if (!time.equals(_lastTime)) {
			_lastTime = time;
			String content = "";

			switch (time) {
			case "09:15": {
				content = "集合竞价开始";
				break;
			}
			case "09:24": {
				content = "集合竞价1分钟倒计时" + COMMA
						+ "[p4000]50秒[p4000]45秒[p4000]40秒[p4000]35秒[p4000]30秒[p4000]25秒[p4000]20秒[p4000]15秒[p4000]10秒[p4000]5秒[p4000]"
						+ "集合竞价结束";
				break;
			}
			case "09:30": {
				content = " 开盘时间到";
				break;
			}
			case "11:30": {
				content = "上午收盘";
				break;
			}
			case "13:00": {
				content = "下午开盘";
				break;
			}
			case "14:57": {
				content = "集合竞价开始";
				break;
			}
			case "15:00": {
				content = "下午收盘";
				break;
			}
			default: {
				content = "";
				break;
			}
			}

			if (!content.equals("")) {
				content = time + COMMA + content;
				_speech.play(content);
			}
		}
	}

	/**
	 * @description: 1分钟量、金额信息播报
	 *
	 */
	private void tradeRemind1M() {
		String time = "";
		String content = "";
		String indexCode = "SZ";

		ArrayList<String> data = DataSource.getResult1M(indexCode);
		if (data != null && data.size() > 0) {
			time = data.get(0);
			BigDecimal volRate = setBigDecimal((new BigDecimal(data.get(1))).multiply(HUNDRED), 2);
			BigDecimal accVolRate = setBigDecimal((new BigDecimal(data.get(2))).multiply(HUNDRED), 2);
			BigDecimal amountRate = setBigDecimal((new BigDecimal(data.get(3))).multiply(HUNDRED), 2);
			BigDecimal accAmountRate = setBigDecimal((new BigDecimal(data.get(4))).multiply(HUNDRED), 2);

			String volStatus = volRate.doubleValue() >= 0 ? "增量百分之" : "缩量百分之";
			String accVolStatus = accVolRate.doubleValue() >= 0 ? "累计增量百分之" : "累计缩量百分之";
			String amountStatus = amountRate.doubleValue() >= 0 ? "金额增量百分之" : "金额缩量百分之";
			String accAmountStatus = accAmountRate.doubleValue() >= 0 ? "累计金额增量百分之" : "累计金额缩量百分之";

			if (time.equals("09:25")) {
				content = "上证集合竞价" + volStatus + volRate.abs().toString() + COMMA + amountStatus
						+ amountRate.abs().toString();
			} else {
				content = "上证1分钟" + volStatus + volRate.abs().toString() + COMMA + accVolStatus
						+ accVolRate.abs().toString() + COMMA + amountStatus + amountRate.abs().toString() + COMMA
						+ accAmountStatus + accAmountRate.abs().toString();
			}
		}
		if (content.equals(""))
			Log.info("1分钟数据异常！");
		else {
			if (!time.equals("11:30") && !time.equals("14:57")) {
				String curTime = "";
				if (time.equals("09:25") || time.equals("15:00")) {
					curTime = time;
				} else {
					curTime = DateHelper.getTimeByMinute(time, 1);
				}
				_speech.play(curTime + COMMA + content);
			}
		}
	}

	/**
	 * @description: 5分钟量、金额信息播报
	 *
	 */
	private void tradeRemind5M() {
		String time = "";
		String contentSZ = "";
		String contentZX = "";
		String contentCY = "";

		ArrayList<String> data = null;
		ArrayList<String> arrIndex = new ArrayList<String>();
		arrIndex.add("SZ");
		arrIndex.add("ZX");
		arrIndex.add("CY");
		for (String indexCode : arrIndex) {
			data = DataSource.getResult5M(indexCode);
			if (data != null && data.size() > 0) {
				time = data.get(0);
				BigDecimal amountRate = setBigDecimal((new BigDecimal(data.get(1))).multiply(HUNDRED), 2);
				BigDecimal accAmountRate = setBigDecimal((new BigDecimal(data.get(2))).multiply(HUNDRED), 2);

				String amountStatus = amountRate.doubleValue() >= 0 ? "金额增量百分之" : "金额缩量百分之";
				String accAmountStatus = accAmountRate.doubleValue() >= 0 ? "累计金额增量百分之" : "累计金额缩量百分之";
				String content = "5分钟" + amountStatus + amountRate.abs().toString() + COMMA;
				if (!time.equals("09:35")) {
					content += accAmountStatus + accAmountRate.abs().toString() + PERIOD;
				}
				if (indexCode.equals("SZ")) {
					contentSZ = "上证" + content;
				} else if (indexCode.equals("ZX")) {
					contentZX = "中小板" + content;
				} else if (indexCode.equals("CY")) {
					contentCY = "创业板" + content;
				}
			}
		}

		String result = contentSZ + contentZX + contentCY;
		if (result.equals(""))
			Log.info("5分钟数据异常！");
		else {
			result = time + COMMA + result;
			switch (time) {
			case "09:45": {
				result = result.replace("累计", "15分钟");
				break;
			}
			case "10:00": {
				result = result.replace("累计", "30分钟");
				break;
			}
			case "10:30": {
				result = result.replace("累计", "1小时");
				break;
			}

			default: {
				break;
			}
			}
			_speech.play(result);
		}
	}

	/**
	 * @description: 设置BigDecimal的精度
	 * @param val
	 * @param precision
	 * @return
	 */
	private BigDecimal setBigDecimal(BigDecimal val, int precision) {
		return val.setScale(precision, BigDecimal.ROUND_HALF_UP);
	}

}
