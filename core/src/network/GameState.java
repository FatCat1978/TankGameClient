package network;

//the big cheese.
//the head honcho. 
//this controls the gamestate, ae: where everything is.

//synched with the server at regular intervals.


import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.google.gson.Gson;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tank;

import tank.Bullet;
import tank.Tank2;
import tank.Tank2.tankTypes;
//controls the location of all the tanks , and the updating of those positions via the server/
public class GameState extends Thread
{
	
	static boolean doUpdate = true; //do we even bother to update? only false when closing.
	
	static HashMap<String,Tank2> tankHash = new HashMap<String,Tank2>(); //hashmap of all the tanks - paired with a key, which is less relevant now
	//it's effectively just a list right now for the client side, just a hash because of what it is on the server.
	
	static Tank2 ourTank; //shortcut reference to our tank - it's independant of the hash for updating ease
	//because if we updated it with the hash (like we used to), it'll cause unusuable amounts of stuttering.
	
	
	public static MyGdxGame game; //ref to the game, used for drawing.
	
	final static int desiredTickRate = 30; //how many ticks per second are we aiming for?
	
	static long lastTick; //when was the last tick? used for determining deltaTime
	
	
	String serverName = "localhost"; //where are we connecting to?
	int port = 6066;//port.
	
	public static boolean activelyConnected = false;
	
	public static String clientKey = "";
	private Gson converter = new Gson();
	//Server/Client communication stuff
	private	static Socket client;
	private static OutputStream outToServer;
	private static InputStream inFromServer;
	private static DataOutputStream out;
	private static DataInputStream in;
	
	public static LobbyInfo LI = new LobbyInfo();
	static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	
	public static boolean offlineMode = false; //debug!
	public static boolean serverResponse = false;
	
	public static enum allGameStates { IN_LOBBY, IN_SELECT, IN_GAME, WAITING_FOR_SERVER}; //every state the lobby can possibly be in 
	public static allGameStates currentGameState = allGameStates.IN_LOBBY;
	
	public static enum allTankPicks {TANK_LARGE,TANK_MEDIUM,TANK_SMALL,NO_SELECTION};
	public static allTankPicks currentTankPick = allTankPicks.NO_SELECTION;
	
	ScheduledExecutorService TickExecutor;
	
	Bullet theBullet;
	
	public void passBullet(Bullet theBullet) {
		this.theBullet = theBullet;
		bulletList.add(this.theBullet);
	}
	
	public static void Draw(SpriteBatch spriteBatch, float delta)
	{
	update(delta);
		if(ourTank != null)
			ourTank.draw(spriteBatch);
		if(tankHash.keySet().size() == 0)
			return; //HACK FIX
		ArrayList<Tank2> allTanks = new ArrayList<Tank2>(tankHash.values());
		for(Tank2 toDraw : allTanks)
		{
			toDraw.draw(spriteBatch);
		}
		for(Bullet toDraw : bulletList)
		{
			toDraw.Draw(spriteBatch);
		}
	}
	
	private static void update(float delta) {
		for(Bullet toUpdate : bulletList)
		{
			toUpdate.Update(delta);
		}
	}
	void UpdateFromJSON(String Packet)
		{
			ClientToServerPacket serverComms = converter.fromJson(Packet, ClientToServerPacket.class); //= new Gson().fromJson(Packet, ClientToServerPacket.class);
			if(serverComms.packetType.equals("lobby"))
			{
				JSONupdateLobby(serverComms.packetInfo);
				
			}
			if(serverComms.packetType.equals("ingame"))
			{
				JSONupdateGame(serverComms.packetInfo);
			}
		}
	
	void JSONupdateLobby(String Packet)
	{
			LI = new Gson().fromJson(Packet, LobbyInfo.class);
			if(LI.StopExistingInLobby && currentGameState == allGameStates.WAITING_FOR_SERVER)//it's time to leave.
				currentGameState = allGameStates.IN_GAME;
	}
	
	@SuppressWarnings("unused")
	void JSONupdateGame(String Packet)
	{
			//we get a hashmap of junk from this
			
		Type type = new TypeToken<HashMap<String, TankInfoPacket>>(){}.getType();
		HashMap<String, TankInfoPacket> tankPacket= new Gson().fromJson(Packet, type);
			//we get a hashmap of every tank - as TankInfoPackets, though.
		
		//first off, see if our current hash can even compare.
		boolean keysetsMatch = false; //we do this by comparing
		//key verification
	//	if(tankPacket.keySet().containsAll(tankHash.keySet()))
		//	keysetsMatch = true;
		
		if(ourTank == null)
		{
			TankInfoPacket tankInfoFrom = tankPacket.get(LI.yourKey);
			ourTank = new Tank2(StrToTankType(tankInfoFrom.size), new Vector2(tankInfoFrom.x, tankInfoFrom.y), game);
			ourTank.TankRotation = tankInfoFrom.tankAngle;
			ourTank.TurretRotation = tankInfoFrom.turretAngle;
			ourTank.health = tankInfoFrom.health;
			ourTank.maxHealth = tankInfoFrom.healthmax;
			
			
		}
		
		if(!keysetsMatch)//tankPacket.size() != tankHash.size())
		{//if not, nuke it.
			//tankHash.clear();
			
			for(String packetKey : tankPacket.keySet())
			{
				if(packetKey.equals(LI.yourKey))
					continue; //sorry stephen.
				
				TankInfoPacket tankInfoFrom = tankPacket.get(packetKey);
				Tank2 newTank = new Tank2(StrToTankType(tankInfoFrom.size), new Vector2(tankInfoFrom.x, tankInfoFrom.y), game);
				newTank.TankRotation = tankInfoFrom.tankAngle;
				newTank.TurretRotation = tankInfoFrom.turretAngle;
				newTank.health = tankInfoFrom.health;
				newTank.maxHealth = tankInfoFrom.healthmax;
				tankHash.put(packetKey, newTank);
			}
			
		}
		else //if the keysets line up, we can just update everything without initializing anything. cheaper? marginally.
		{
			for(String packetKey : tankPacket.keySet())
			{
				TankInfoPacket fromPacket = tankPacket.get(packetKey);
				Tank2 updated = tankHash.get(packetKey);
				updated.TankRotation = fromPacket.tankAngle;
				updated.TurretRotation = fromPacket.turretAngle;
				updated.health = fromPacket.health;
				updated.maxHealth = fromPacket.healthmax;
				updated.TankPos = new Vector2(fromPacket.x,fromPacket.y);
				tankHash.put(packetKey, updated);
				
			}
		}
	}
	private tankTypes StrToTankType(String size) {
		// TODO Auto-generated method stub
		if(size.equals("TANK_SMALL"))
				return tankTypes.LIGHT;
		
		if(size.equals("TANK_LARGE"))
			return tankTypes.HEAVY;
		
		if(size.equals("medium"))
			return tankTypes.MEDIUM;

		return tankTypes.MEDIUM;
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
					System.out.println("Exception caught when trying to connect to server!");
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
			LobbyInfo temp = new LobbyInfo();
			temp.chosenTankType = currentTankPick.toString();
			ClientToServerPacket outGoing = new ClientToServerPacket();
			outGoing.packetInfo = converter.toJson(temp);
			outGoing.packetType = "lobby";
			return converter.toJson(outGoing);
	}
	private String ConstructGamePacket() //sends actual game info over.
	{
			ClientToServerPacket C2S = new ClientToServerPacket();
			TankInfoPacket TP = new TankInfoPacket();
			Tank2 ourTank = GameState.ourTank;
			if(ourTank != null)
			{
				TP.x = ourTank.TankPos.x;
				TP.y = ourTank.TankPos.y;
				TP.health = ourTank.health;
				TP.healthmax = ourTank.maxHealth;
				TP.tankAngle = ourTank.TankRotation;
				TP.turretAngle = ourTank.TurretRotation;
				TP.size = ourTank.currentType.toString();//TODO;
				
			}
			C2S.packetType = "ingame";
			C2S.packetInfo = converter.toJson(TP);
			return  converter.toJson(C2S);
	}
	
	private void init_offline() //if this happens we don't even try to communicate with the server.
	{
			offlineMode = true;
			System.out.println("We are now in offline mode!");
			clientKey = "offline!";
			LI.yourKey = "offline!";
			ourTank = new Tank2(Tank2.tankTypes.MEDIUM, new Vector2(500,500), game);
			currentGameState = allGameStates.IN_LOBBY;
	}
	
	public void run()
	{
			System.out.println("Attempting to connect to server!");
			if(offlineMode)
			{
				init_offline();
				return;
			}
			//we're making a new connection..
			try
			{
				System.out.println("Making new connection to:" + serverName + ":" + port);
				client = new Socket(serverName, port);
				System.out.println("Connection Established to:" + client.isConnected() + " ," + client.toString());
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
			
			TickExecutor = Executors.newScheduledThreadPool(1);
			TickExecutor.scheduleAtFixedRate(TankUpdateRunnable, 0, 1000/desiredTickRate, TimeUnit.MILLISECONDS);
	}
	
	Runnable TankUpdateRunnable = new Runnable() {
		    public void run() {
		    		try {
		    			if(doUpdate)
		    				Update();
		    		}
		    		catch(Exception e)
		    			{
		    			System.out.println( "ERROR - unexpected exception" );
		    			}

		}};

		public static void do_input(float delta, boolean w, boolean a, boolean s, boolean d, boolean q, boolean e, boolean space)
		{
			// TODO Auto-generated method stub
			if(ourTank!=null)
				ourTank.do_input(delta, w, a, s, d, q, e, space);
			else
				System.out.println("Tank is null with key of " + LI.yourKey + " . TankArray has:" + tankHash.size());
		}


	public static Tank2 get_client_tank()
		{
			return ourTank;
			// TODO Auto-generated method stub
		}

	public static void dispose() {
		doUpdate = false;
		// TODO Auto-generated method stub
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
		
	

}


	
