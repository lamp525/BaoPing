package com.ncgroup.bp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

/**
 * @description: ���ݲ�������
 * @author: Lin
 * @created: 2018��9��13�� ����4:43:14
 * @version: 1.0
 */
public class BroadcastTask extends TimerTask {
	private Speech _speech = new Speech();
	private final String COMMA = "��";
	private final String PERIOD = "��";
	private final BigDecimal HUNDRED = new BigDecimal("100");
	private String _lastTime = "";

	/**
	 * ������������
	 */
	@Override
	public void run() {
		try {
			Date now = new Date();
			String curTime = DateHelper.getFormatTime(now, "HH:mm");
			int iTime = DateHelper.getHourByCurrentTime(now) * 100 + DateHelper.getMinuteByCurrentTime(now);

			// ������ʾ��Ϣ
			if (iTime >= 915 && iTime <= 1500)
				notice(curTime);

			// 1�������ݲ���
			if (iTime >= 925 && iTime <= 934) {
				if (DataSource.checkUpdate1M())
					tradeRemind1M();
			}

			// 5�������ݲ���
			if ((iTime >= 935 && iTime < 1132) || (iTime >= 1305 && iTime < 1503)) {
				if (DataSource.checkUpdate5M())
					tradeRemind5M();
			}
		} catch (Exception e) {
			Log.error("���ݲ��������쳣��", e);
		}
	}

	/**
	 * @description: ����ʱ��ڵ���Ϣ����
	 * @param time
	 */
	private void notice(String time) {
		if (!time.equals(_lastTime)) {
			_lastTime = time;
			String content = "";

			switch (time) {
			case "09:15": {
				content = "���Ͼ��ۿ�ʼ";
				break;
			}
			case "09:24": {
				content = "���Ͼ���1���ӵ���ʱ" + COMMA
						+ "[p4000]50��[p4000]45��[p4000]40��[p4000]35��[p4000]30��[p4000]25��[p4000]20��[p4000]15��[p4000]10��[p4000]5��[p4000]"
						+ "���Ͼ��۽���";
				break;
			}
			case "09:30": {
				content = " ����ʱ�䵽";
				break;
			}
			case "11:30": {
				content = "��������";
				break;
			}
			case "13:00": {
				content = "���翪��";
				break;
			}
			case "14:57": {
				content = "���Ͼ��ۿ�ʼ";
				break;
			}
			case "15:00": {
				content = "��������";
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
	 * @description: 1�������������Ϣ����
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

			String volStatus = volRate.doubleValue() >= 0 ? "�����ٷ�֮" : "�����ٷ�֮";
			String accVolStatus = accVolRate.doubleValue() >= 0 ? "�ۼ������ٷ�֮" : "�ۼ������ٷ�֮";
			String amountStatus = amountRate.doubleValue() >= 0 ? "��������ٷ�֮" : "��������ٷ�֮";
			String accAmountStatus = accAmountRate.doubleValue() >= 0 ? "�ۼƽ�������ٷ�֮" : "�ۼƽ�������ٷ�֮";

			if (time.equals("09:25")) {
				content = "��֤���Ͼ���" + volStatus + volRate.abs().toString() + COMMA + amountStatus
						+ amountRate.abs().toString();
			} else {
				content = "��֤1����" + volStatus + volRate.abs().toString() + COMMA + accVolStatus
						+ accVolRate.abs().toString() + COMMA + amountStatus + amountRate.abs().toString() + COMMA
						+ accAmountStatus + accAmountRate.abs().toString();
			}
		}
		if (content.equals(""))
			Log.info("1���������쳣��");
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
	 * @description: 5�������������Ϣ����
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

				String amountStatus = amountRate.doubleValue() >= 0 ? "��������ٷ�֮" : "��������ٷ�֮";
				String accAmountStatus = accAmountRate.doubleValue() >= 0 ? "�ۼƽ�������ٷ�֮" : "�ۼƽ�������ٷ�֮";
				String content = "5����" + amountStatus + amountRate.abs().toString() + COMMA;
				if (!time.equals("09:35")) {
					content += accAmountStatus + accAmountRate.abs().toString() + PERIOD;
				}
				if (indexCode.equals("SZ")) {
					contentSZ = "��֤" + content;
				} else if (indexCode.equals("ZX")) {
					contentZX = "��С��" + content;
				} else if (indexCode.equals("CY")) {
					contentCY = "��ҵ��" + content;
				}
			}
		}

		String result = contentSZ + contentZX + contentCY;
		if (result.equals(""))
			Log.info("5���������쳣��");
		else {
			result = time + COMMA + result;
			switch (time) {
			case "09:45": {
				result = result.replace("�ۼ�", "15����");
				break;
			}
			case "10:00": {
				result = result.replace("�ۼ�", "30����");
				break;
			}
			case "10:30": {
				result = result.replace("�ۼ�", "1Сʱ");
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
	 * @description: ����BigDecimal�ľ���
	 * @param val
	 * @param precision
	 * @return
	 */
	private BigDecimal setBigDecimal(BigDecimal val, int precision) {
		return val.setScale(precision, BigDecimal.ROUND_HALF_UP);
	}

}
