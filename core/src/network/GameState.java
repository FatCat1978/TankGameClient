package network;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.mygdx.game.Tank;

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
	
	
	
	//Server/Client communication stuff
	private	Socket client;
	private OutputStream outToServer;
	private InputStream inFromServer;

	
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
			
			//we're making a new connection..
			try
			{
				client = new Socket(serverName, port);
				outToServer = client.getOutputStream();
				inFromServer = client.getInputStream();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			
			ScheduledExecutorService TickExecutor = Executors.newScheduledThreadPool(1);
			TickExecutor.scheduleAtFixedRate(TankUpdateRunnable, 0, 1000/desiredTickRate, TimeUnit.MILLISECONDS);
	}
	
	Runnable TankUpdateRunnable = new Runnable() {
		    public void run() {
		        Update();
		    }
		};
	
		
	
}
