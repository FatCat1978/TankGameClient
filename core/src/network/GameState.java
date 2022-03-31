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
import com.google.gson.Gson;
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
	private static DataOutputStream out;
	private static DataInputStream in;
	
	public static LobbyInfo LI;
	
	
	public static boolean offlineMode = false; //debug!
	public static boolean serverResponse = false;
	
	public static enum allGameStates { IN_LOBBY, IN_SELECT, IN_GAME, WAITING_FOR_SERVER}; //an enum containing tank classes
	public static allGameStates currentGameState = allGameStates.IN_LOBBY;
	
	public static enum allTankPicks {TANK_LARGE,TANK_MEDIUM,TANK_SMALL};
	public static allTankPicks currentTankPick;
	
	public static void Draw(SpriteBatch spriteBatch)
		{
			for(Tank2 toDraw : tankHash.values())
			{
				toDraw.draw(spriteBatch);
			}
		}
	
	void UpdateFromJSON(String Packet)
		{
			ClientToServerPacket serverComms = new Gson().fromJson(Packet, ClientToServerPacket.class);
			if(serverComms.packetType == "lobby")
			{
				JSONupdateLobby(serverComms.packetInfo);
				
			}
			if(serverComms.packetType == "game")
			{
				JSONupdateGame(serverComms.packetInfo);
			}
		}
	
	void JSONupdateLobby(String Packet)
	{
			LI = new Gson().fromJson(Packet, LobbyInfo.class);
	}
	
	void JSONupdateGame(String Packet)
	{
			
	}
	private void Update()
	{ //the heartbeat. asks the server for the current gameInfo! Sends current!
			
			try
				{
				String sendOut = ConstructPacket();
				if(sendOut == null)
				{
					System.out.println("ATTEMPTED TO SEND A NULL PACKET! REGRET. REGRET.");
					return;
				}
				out.writeUTF(sendOut);
				String response = in.readUTF();
				UpdateFromJSON(response);
				} catch(Exception e)
				{
					
				}
		
	}
	
	private String ConstructPacket()
		{
			switch(currentGameState)
			{
			case IN_GAME:
				return ConstructGamePacket();
			case IN_LOBBY:
				return ConstructLobbyPacket();
			case IN_SELECT:
				return ConstructLobbyPacket();
			case WAITING_FOR_SERVER:
				return ConstructLobbyPacket();
			default:
				break;
			
			}
			// TODO Auto-generated method stub
			return null;
		}

	private String ConstructLobbyPacket() //sends lobby info over, like what we've chosen, etc etc.
	{
			return "";
	}
	private String ConstructGamePacket() //sends actual game info over.
	{
			return "";
	}
	
	private void init_offline() //if this happens we don't even try to communicate with the server.
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
				
				in = new DataInputStream(inFromServer);
				out = new DataOutputStream(outToServer);
				
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
