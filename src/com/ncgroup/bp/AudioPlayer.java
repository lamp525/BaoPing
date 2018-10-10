package com.ncgroup.bp;

import java.io.FileInputStream;
import java.io.InputStream;

import javazoom.jl.player.Player;

/**
 * @description: 音频播放类
 * @author: Lin
 * @created: 2018年9月13日 下午4:46:27
 * @version: 1.0
 */
public class AudioPlayer {

	private static Player _player;

	/**
	 * @description: 播放语音文件
	 * @param fileName
	 */
	public static void play(String fileName) {
		try {
			InputStream fis = new FileInputStream(fileName);
			_player = new Player(fis);
			_player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description: 播放提示音
	 *
	 */
	public static void ding() {
		try {
			InputStream is = AudioPlayer.class.getClass().getResourceAsStream("/ding.mp3");
			_player = new Player(is);
			_player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}