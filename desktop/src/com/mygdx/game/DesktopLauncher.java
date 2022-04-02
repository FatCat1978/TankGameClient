package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Joe Mama's Tank Game");
		config.setForegroundFPS(60);
		config.setWindowSizeLimits(	400, 400, 500, 500);
		//config.setFullscreenMode(Gdx.graphics.getDisplayMode());
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
