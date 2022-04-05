package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}

//Alright, so the plan was that we meet on the 4th @ 5:30 to discuss the fact that barely anything on the client is done.
//they moved that to 6:30. I needed to be in town by 7, so there wasn't even enough time for me to bother hopping on. I'll note that they changed when it was without any notification 
//Joey was the only reason I knew.

//alright, sure. as long as they get it done by the morning, I'll be up and can get projectiles working!
//they didn't. Unless it's an issue on my end (which it isn't, they screwed up the gradle build, so gson isn't even included anymore), they completely screwed the pooch.
//the ONLY way I could see it being a me issue is if I misread the instructions that weren't there with the ZIP file they sent over discord instead of git.

//I'm sure they'll find a way to try and blame me for this. "oh! connor! your tank2 class is too complicated!!"
//and if that's the case, this was a lost cause from the beginning. I wish this was a solo project.