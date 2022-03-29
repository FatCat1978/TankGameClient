package com.mygdx.game;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameState extends Thread
{
	Integer controlledTank;
	HashMap<Integer,Tank> tankHash = new HashMap<Integer,Tank>();
	//hashmap of all the tanks. id to 
	
	final static int desiredTickRate = 30; //how many ticks per second are we aiming for?
	
	static long lastTick; //when was the last tick? used for determining deltaTime
	
	
	String serverName = "localhost";
	int port = 6066;
	
	boolean activelyConnected = false;
	
	
	void Draw()
		{
			
		}
	
	void UpdateFromJSON()
		{
			
		}
	
	private void Update()
	{
			
	}
	
	public void run()
	{
			ScheduledExecutorService TickExecutor = Executors.newScheduledThreadPool(1);
			TickExecutor.scheduleAtFixedRate(TankUpdateRunnable, 0, 1000/desiredTickRate, TimeUnit.MILLISECONDS);
	}
	
	Runnable TankUpdateRunnable = new Runnable() {
		    public void run() {
		        Update();
		    }
		};
	
		
	
}
