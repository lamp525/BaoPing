package com.ncgroup.bp;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizerListener;

public class Speech {

	private static SpeechSynthesizer _ss = null;

	/**
	 * @Description: ʵ����
	 */
	public Speech() {
		getInstance();
	}

	/**
	 * �����ı�
	 * 
	 * @param content
	 */
	public void play(String content) {
		try {
			getInstance();
			while (_ss.isSpeaking()) {
				ThreadUtil.threadSleep(3 * 1000);
			}
			Log.info(content);

			// ������ʾ��
			AudioPlayer.ding();

			// �ϳɲ������ı�
			synthesis(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ����
	 */
	private void getInstance() {
		if (_ss == null) {
			// 1.��ȡSpeechSynthesizer����
			_ss = SpeechSynthesizer.createSynthesizer();

			// �ϳɲ�������
			_ss.setParameter(SpeechConstant.VOICE_NAME, "vixq");// ���÷�����
			_ss.setParameter(SpeechConstant.SPEED, "60");// ��������
			_ss.setParameter(SpeechConstant.VOLUME, "100");// ������������Χ0~100

			// ���úϳ���Ƶ����λ�ã����Զ��屣��λ�ã��������ڡ�./iflytek.pcm��
			// �������Ҫ����ϳ���Ƶ��ע�͸��д���
			// _ss.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./iflytek.pcm");
		}
	}

	/**
	 * �����ϳ�
	 * 
	 */
	private void synthesis(String content) {
		// ��ȡSpeechSynthesizer����
		getInstance();

		// ��ʼ�ϳ���������
		_ss.startSpeaking(content, mSynListener);
	}

	private static SynthesizerListener mSynListener = new SynthesizerListener() {

		@Override
		public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onCompleted(SpeechError arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4, Object arg5) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSpeakBegin() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSpeakPaused() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSpeakProgress(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSpeakResumed() {
			// TODO Auto-generated method stub

		}
	};
}
