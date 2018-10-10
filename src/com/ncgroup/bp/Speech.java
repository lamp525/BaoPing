package com.ncgroup.bp;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizerListener;

/**
 * @description: 讯飞在线语音合成
 * @author: Lin
 * @create: 2018年9月14日
 * @version: 1.0
 */
public class Speech {

	private static SpeechSynthesizer _ss = null;

	/**
	 * @description: 实例化
	 */
	public Speech() {
		getInstance();
	}

	/**
	 * @description: 播放文本
	 * @param content
	 */
	public void play(String content) {
		try {
			getInstance();
			while (_ss.isSpeaking()) {
				ThreadUtil.threadSleep(Thread.currentThread().getName(), 3 * 1000);
			}
			Log.info(content);

			// 播放提示音
			AudioPlayer.ding();

			// 合成并播报文本
			synthesis(content);
		} catch (Exception e) {
			Log.error("文本播放异常", e);
		}
	}

	/**
	 * @description: 获取对象
	 *
	 */
	private void getInstance() {
		if (_ss == null) {
			// 1.获取SpeechSynthesizer对象
			_ss = SpeechSynthesizer.createSynthesizer();

			// 合成参数设置
			_ss.setParameter(SpeechConstant.VOICE_NAME, "vixq");// 设置发音人
			_ss.setParameter(SpeechConstant.SPEED, "60");// 设置语速
			_ss.setParameter(SpeechConstant.VOLUME, "100");// 设置音量，范围0~100

			// 设置合成音频保存位置（可自定义保存位置），保存在“./iflytek.pcm”
			// 如果不需要保存合成音频，注释该行代码
			// _ss.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./iflytek.pcm");
		}
	}

	/**
	 * @description: 语音合成
	 * 
	 */
	private void synthesis(String content) {
		// 获取SpeechSynthesizer对象
		getInstance();

		// 开始合成语音播报
		_ss.startSpeaking(content, _synListener);
	}

	/**
	 * 语音合成监听器对象
	 */
	private static SynthesizerListener _synListener = new SynthesizerListener() {

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
