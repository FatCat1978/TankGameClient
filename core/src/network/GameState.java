package network;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tank;

import tank.Tank2;
//controls the location of all the tanks , and the updating of those positions via the server/
public class GameState extends Thread
{
	Integer controlledTank;
	static HashMap<String,Tank2> tankHash = new HashMap<String,Tank2>();
	//hashmap of all the tanks. id to 
	
	public static MyGdxGame game;
	
	final static int desiredTickRate = 30; //how many ticks per second are we aiming for?
	
	static long lastTick; //when was the last tick? used for determining deltaTime
	
	
	String serverName = "localhost";
	int port = 6066;
	
	public static boolean activelyConnected = false;
	
	public static String clientKey = "";
	
	//Server/Client communication stuff
	private	static Socket client;
	private static OutputStream outToServer;
	private static InputStream inFromServer;
	public static boolean offlineMode = false; //debug!
	public static boolean serverResponse = false;
	
	public static enum allGameStates { IN_LOBBY, IN_SELECT, IN_GAME, WAITING_FOR_SERVER}; //an enum containing tank classes
	public static allGameStates currentGameState = allGameStates.IN_LOBBY;
	
	public static void Draw(SpriteBatch spriteBatch)
		{
			for(Tank2 toDraw : tankHash.values())
			{
				toDraw.draw(spriteBatch);
			}
		}
	
	void UpdateFromJSON()
		{
			
		}
	
	private void Update()
	{ //the heartbeat. asks the server for the current gameInfo! Sends current!
			
	}
	
	private void init_offline()
	{
			offlineMode = true;
			System.out.println("We are now in offline mode!");
			clientKey = "offline!";
			tankHash.put(clientKey, new Tank2(Tank2.tankTypes.MEDIUM, new Vector2(500,500), game));
			currentGameState = allGameStates.IN_GAME;
	}
	
	public void run()
	{
			if(offlineMode)
			{
				init_offline();
				return;
			}
			//we're making a new connection..
			try
			{
				client = new Socket(serverName, port);
				outToServer = client.getOutputStream();
				inFromServer = client.getInputStream();
			} catch (Exception e)
			{
				//jank
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("No server response! forcing offline mode!");
				init_offline();
				return;				
			}
			
			ScheduledExecutorService TickExecutor = Executors.newScheduledThreadPool(1);
			TickExecutor.scheduleAtFixedRate(TankUpdateRunnable, 0, 1000/desiredTickRate, TimeUnit.MILLISECONDS);
	}
	
	Runnable TankUpdateRunnable = new Runnable() {
		    public void run() {
		        Update();
		    }
		};

	public static void do_input(float delta, boolean w, boolean a, boolean s, boolean d, boolean q, boolean e)
		{
			// TODO Auto-generated method stub
			Tank2 userTank = tankHash.get(clientKey);
			userTank.do_input(delta, w, a, s, d, q, e);
		}

	public static Tank2 get_client_tank()
		{
			return tankHash.get(clientKey);
			// TODO Auto-generated method stub
		}
	
		
	
}
