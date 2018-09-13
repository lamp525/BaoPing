package com.ncgroup.bp;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;

/**
 * @Description: ��������
 * @author: Lin
 * @version:
 * @date: 2018��9��12�� ����4:14:16
 */
public class Startup {
	private static final String MY_APPID = "5b8508fa";

	/**
	 * @Description: ��������ں���
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Log.info("����������");

			// ��ʼ��Ѷ�������ϳ�SDK
			SpeechUtility.createUtility(SpeechConstant.APPID + "=" + MY_APPID);
			Speech s = new Speech();
			s.play("�����ϳɲ��Ų���");	
		} catch (Exception e) {
			Log.error("�������쳣��", e);
		}
	}

}
