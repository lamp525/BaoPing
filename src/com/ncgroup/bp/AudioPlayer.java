package com.ncgroup.bp;

import java.io.FileInputStream;
import java.io.InputStream;

import javazoom.jl.player.Player;

public class AudioPlayer {

	private static Player _player;

	public static void play(String fileName) {
		try {
			InputStream fis = new FileInputStream(fileName);
			_player = new Player(fis);
			_player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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