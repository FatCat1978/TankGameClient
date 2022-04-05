package com.mygdx.game;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;


public class tankAppClient 
{
	// NOTE: currently a JFRAME. Will have to remove that when implementing into tank-game
	
	
	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	// Method				:	void
	//
	// Method parameters	:	void
	//
	// Method return		:	void
	//
	// Synopsis				: 	The tank server is one half of the networking program that allows for players to 
	//							play the group tank game through the libGDX client. It collects information from 
	//							the client to help keep an updated display of what the client should be displaying.
	//							
	//							This particular class handles the network connection and communication between the 
	//							client and the server. (Client Side)
	//												
	//
	// Modifications		:
	//												Date			Developer				Notes
	//												----			---------				-----
	//												2022-04-01		Quentin G.				Comments
	//
	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	
	// All global variables go here
	
	private static String theCurrentScreen = "null";					// Instantiate string to hold info
	private static String thePlayerID = "null";							// Instantiate string to hold info
	
	private static String tankChoice = "null";							// Instantiate string to hold info
	private static String tankHealth = "null";							// Instantiate string to hold info
	
	private static String tankPosX = "null";							// Instantiate string to hold info
	private static String tankPosY = "null";							// Instantiate string to hold info
	private static String tankAngle = "null";							// Instantiate string to hold info
	
	private static String turretAngle = "null";							// Instantiate string to hold info
	
	private static String isBulletShot = "null";						// Instantiate string to hold info
	private static String bulletStartX = "null";						// Instantiate string to hold info
	private static String bulletStartY = "null";						// Instantiate string to hold info
	private static String bulletAngle = "null";							// Instantiate string to hold info
	
	private static String tempString = "null";							// Instantiate string to hold info
	private static String fromServer = "null";							// Instantiate string to hold info
	private String IP = "";
	
	// Frame/Window functionality
	private JFrame frame;												// Instantiate a JFrame -> REMOVE THIS LATER
	// We will need a list of tanks
	// NOTE: tanks need to have an ID/key for the below to work. This would only tank adding "String tankID" into the current tank class
	
	// We will need a list of bullets

	public tankAppClient() {
		initialize();													// Initializes the client-side network connection
	}
   
	
	public void connectingToServer()
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	connectingToServer()
		//
		// Method parameters	:	(String theMessage, String theColour, String newLineLength)
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method contains everything needed for the initial connection, and the sending
		//							and receiving of information between the Client (this) and the Server. It starts
		//							with (trying to) connect with the server, and if it doesn't within a time period
		//							it cannot continue the application. After it handles the input and output of variables
		//							for network communication purposes.
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-01		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
	      String serverName = IP;								// Establish the local machine as the
	      																// server
	      int port = 6066;												// Hard-code port for connection
	      try
	      {
	         System.out.println("Connecting to " + serverName
	                             + " on port " + port);					// Informs user of important information
	         
	         
	         
	         
	         Socket client = new Socket(serverName, port);				// Instantiates the socket (which allows for networking)
	         
	         System.out.println("Just connected to "
	                      + client.getRemoteSocketAddress());			// A success message to tell the player they've connected to server
	         	     
	         
	         
	 		// Instead of having it here, it's in its own method (encapsulation and simplicity)
	         sendServerInformation(client);	// Sends the server information
	                  
	 		// Instead of having it here, it's in its own method (encapsulation and simplicity)
	         getServerInput(client);									// Retrieves information from server

	         
	         
	         client.close();											// Ends communication with server (NOT continuous)

	      }catch(IOException e)											// Try catch in case something goes wrong
	      {
	         e.printStackTrace();
	      }
	}
	
	public void getUserIP(String IP) {
		this.IP = IP;
	}
	
	// Read from server method
	private static void getServerInput(Socket theClient) throws IOException
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	getServerInput(Socket theClient) throws IOException
		//
		// Method parameters	:	(Socket theClient)
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method contains everything needed for the retrieving of information from
		//							the server. It instantiates a DataInputStream and gets a string of information from
		//							the server. Then it triggers another method that further handles server input
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-01		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		DataInputStream in = 
				new DataInputStream(theClient.getInputStream());		// Instantiate the DataInputStream
		
		fromServer = in.readUTF();										// Collect string information from server
		if (fromServer.contains("GamePlay"))										// Checks if client is in gameplay, if so triggers below
        {
        	// Break down the input so we know what to send
        	breakDownServerInformation(fromServer);								// Call method to break down the client input
        	
        	// this means that the client is connected, but there is no gameplay happening
        	//out.writeUTF("Connected");
        	// HERE WE HAVE TO SEND BACK ALL INFORMATION THE CLIENT NEEDS

        } 
        
	}
	
	
	// Send information to server method
	private static void sendServerInformation(Socket theClient) throws IOException
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	sendServerInformation(Socket theClient, String buttonType, String theColour, String theLineLength) throws IOException
		//
		// Method parameters	:	(Socket theClient, String buttonType, String theColour, String theLineLength)
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method contains everything needed for the sending information to
		//							the server. It instantiates a DataOutputStream and sends out three messages,
		//							one relating to the button that was pressed, another containing the current
		//							point's associated colour, and the last being the length of the line to be drawn.
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-01		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
        OutputStream outToServer = theClient.getOutputStream();	
        DataOutputStream out =
                      new DataOutputStream(outToServer);				// Instantiate DataOutputStream
        
    
        
        if (theCurrentScreen.matches("GamePlay"))
        {
        	// this will only trigger when player is in game
        	tempString = compileInfoForServer();
        	out.writeUTF(tempString);
        }
        
        else
        {	
        	tempString = "Connected";
        	out.writeUTF(tempString);									// triggered if there's nothing
        }

        
	}
	
	// GET THE CURRENT GAME SCREEN
	public void setScreen(String newScreen)
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	setScreen(String newScreen)
		//
		// Method parameters	:	String newScreen
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method can be called to change the current screen that the client is on
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-03		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		theCurrentScreen = newScreen;
		
	}
	
	public void setTankChoice(String newTank)
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	setTankChoice(String newTank)
		//
		// Method parameters	:	String newTank
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method can be called to change to what type of tank the player is using
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-03		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		tankChoice = newTank;
	}
	
	public void setTankHealth(float health)
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	setTankHealth(float health)
		//
		// Method parameters	:	float health
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method can be called to change the tank health the client sends
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-03		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		tankHealth = Float.toString(health);
	}
	
	public void setTankandTurretData(float x, float y, float angle1, float angle2)
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	setTankandTurretData(float x, float y, float angle1, float angle2)
		//
		// Method parameters	:	float x, float y, float angle1, float angle2
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method can be called to change the tank position, angle, and turret angle
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-03		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		tankPosX = Float.toString(x);
		tankPosY = Float.toString(y);
		tankAngle = Float.toString(angle1);
		
		turretAngle = Float.toString(angle2);
	}
	
	
	public void setBulletData(boolean didPlayerShoot, float x, float y, float angle)
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	setBulletData(boolean didPlayerShoot, float x, float y, float angle)
		//
		// Method parameters	:	boolean didPlayerShoot, float x, float y, float angle
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method can be called to allow server to know if a bullet was fired. It also
		//							sets information about where the bullet was created (position), and the angle
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-03		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		isBulletShot = Boolean.toString(didPlayerShoot);
		bulletStartX = Float.toString(x);
		bulletStartY = Float.toString(y);
		bulletAngle = Float.toString(angle);
	}
	
	private static String compileInfoForServer()
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	compileInfoForServer()
		//
		// Method parameters	:	void
		//
		// Method return		:	String
		//
		// Synopsis				: 	This method packages all the information together to send to server
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-03		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		// Sends information needed during gameplay to the server
		// In order, the current game screen, tank health, tank choice, the tank position and angle, turret angle, and bullet start and angle
		String allTheInfo = theCurrentScreen + "\n" + thePlayerID + "\n" +
							tankHealth + "\n" + tankChoice + "\n" +
							tankPosX + "," + tankPosY + "\n" + 
							tankAngle + "\n" + turretAngle + "\n" +
							isBulletShot + "\n" +
							bulletStartX + "," + bulletStartY + "\n" + bulletAngle;
		
		return allTheInfo;
	}
	

	private static void breakDownServerInformation(String infoFromServer)
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	breakDownServerInformation(String infoFromServer)
		//
		// Method parameters	:	String infoFromServer
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method breaks down the information from the server for the client to use.
		//							It loops through the information for the amount of tanks the server said exists,
		//							getting all the information until it either adds tank into screen or updates 
		//							tanks that already exist. Does the same for bulelts
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-03		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		// We need to break down all the information from the server. 
		// This includes the current screen, health, tank choice, tank positions and angle, turret angle,
		// and where to spawn new bullets (position and angle)
		
		String theLines[] = infoFromServer.split("\n");					// Split the string into a list of strings
		
		int numberOfTanks = 0; 
		
		
		// These are all used in the break down below. 
		String newTankID = "null";
		String newTankType = "null";
		
		float newTankHealth = 0;
		float newTankPosX = 0;
		float newTankPosY = 0;
		float newTankAngle = 0;
		float newTurretAngle = 0;
		
		boolean isBulletPresent = false;
		
		float newBulletPosX = 0;
		float newBulletPosY = 0;
		float newBulletAngle = 0;
		
		// This is used to track if the information is valid. If it isn't, then it doesn't try to apply it to the tank
		boolean isInfoValid = true;
		
		
		// This gets the number of tanks from the server information.
        try{
    		numberOfTanks = Integer.parseInt(theLines[0]);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
		
        
        // Used make sure the right lines are called in break down
        int temp = 0;
        
		for(int counter = 0; counter < numberOfTanks; ++counter)
		{
			// This makes sure the right lines are being processed
			temp = counter * 11;
			
			// reset these each time it loops.
			isInfoValid = true;
			isBulletPresent = false;
			
			// The below will be broken down ONLY if it is not from the player.
			// If the IDs match, then it will not parse the information
			// Therefore, the number of tanks will always actually be 1 under the number the server sends back
			if (theLines[1 + temp].contains(thePlayerID) == false)
			{
				// we don't remove any of the information from the arraylist or it would mess up the math
				
				// First we get the ID
				newTankID = theLines[1 + temp];
				
				// Get the tank health
				if(isFloatValid(theLines[2 + temp])) 
					newTankHealth = Float.parseFloat(theLines[2 + temp]);
				else
					isInfoValid = false;
				
				// get the tank choice
				newTankType = theLines[3 + temp];
		        
				// get the tank positions
				if(isFloatValid(theLines[4 + temp])) 
					newTankPosX = Float.parseFloat(theLines[4 + temp]);
				else
					isInfoValid = false;
				
				if(isFloatValid(theLines[5 + temp])) 
					newTankPosY = Float.parseFloat(theLines[5 + temp]);
				else
					isInfoValid = false;
				
				// get the tank angle
				if(isFloatValid(theLines[6 + temp])) 
					newTankAngle = Float.parseFloat(theLines[6 + temp]);
				else
					isInfoValid = false;
		        
				// get the turret angle
				if(isFloatValid(theLines[7 + temp])) 
					newTurretAngle = Float.parseFloat(theLines[7 + temp]);
				else
					isInfoValid = false;
		        
				// get boolean to check if player has shot a bullet
				isBulletPresent = Boolean.parseBoolean(theLines[8 + temp]);
		        
				// apply to the game screen
		        if (isInfoValid == true)
		        {
		        	// apply here
		        	
		        	// NOTE: the below will have to change.
		        	
		        	/*
		        	if(listOfTanks = null)
		        		createTankFromInfo(numberOfTanks - 1, newTankID, newTankType, 
		        							newTankPosX, newTankPosY, newTankAngle, newTurretAngle);
		        	else
		        		applyToTanks(newTankID, newTankType, 
		        						newTankPosX, newTankPosY, newTankAngle, newTurretAngle);
		        	
		        	*/

		        }
		        
		        // If the info isn't valid, then it ends and doesn't apply/use information
			}
			
			// Create bullets
			if (isBulletPresent == true)
			{
				// get bullet position and angle
				// get bullet position x
				if(isFloatValid(theLines[9 + temp])) 
					newTankPosX = Float.parseFloat(theLines[9 + temp]);
				else
					isInfoValid = false;
				
				// get bullet position y
				if(isFloatValid(theLines[10 + temp])) 
					newTankPosY = Float.parseFloat(theLines[10 + temp]);
				else
					isInfoValid = false;
				
				// get bullet angle
				if(isFloatValid(theLines[11 + temp])) 
					newTurretAngle = Float.parseFloat(theLines[11 + temp]);
				else
					isInfoValid = false;
				
				// Create bullet on screen
			}
			
		}
		
		

	}
	
	private static boolean isFloatValid(String toCheck)
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	isFloatValid(String toCheck)
		//
		// Method parameters	:	String toCheck
		//
		// Method return		:	boolean
		//
		// Synopsis				: 	This method checks if a string can be converted to a float. 
		//							Used for organizational purposes
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-03		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		float temp = 0;
		
        try{														// Try catch in case information is not valid
        	temp = Float.parseFloat(toCheck);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            return false;
        }
        
		return true;
	}
	
	
	private static void createTankFromInfo(String ID, String theType, float X, float Y, float angle1, float angle2)
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	applyToTanksIfNone(String ID, String theType, float X, float Y, float angle1, float angle2)
		//
		// Method parameters	:	String ID, String theType, float X, float Y, float angle1, float angle2
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method creates a new tank based off of information passed in.
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-03		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		// Create new tanks to put on screen
		/*
			
		// passes in a tank ID, tank type, the x and y positions, tank angle and turret angle
		Tank newTank(ID, Type, X, Y, angle1, angle2);
			
		listOfTanks.add(newTank);
		*/
		
	}
	
	
	private static void applyToTanks(String ID, String theType, float X, float Y, float angle1, float angle2)
	{
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	applyToTanks(String ID, String theType, float X, float Y, float angle1, float angle2)
		//
		// Method parameters	:	String ID, String theType, float X, float Y, float angle1, float angle2
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method updates the tanks currently on screen with the information passed in.
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-04-03		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		
		
		// This is theoretical, might be different once applied in LibGDX
		
		// loop over tanks
		
		/*
		if (listOfTanks != null)
		{
			for(tankObjects theTanks : listOfTanks)
			{
				// check if the ID matches, if it doesn't this will skip
				if (theTank.ID == ID)
				{
					theTank.positionX = X;
					theTank.positionY = Y;
					theTank.tankAngle = angle1;
					theTank.turretAngle = angle2;
				}
			}	
		}
			
			
		*/
	}
	
	
	/*
	 
	// NOTE: THESE METHODS WILL ONLY WORK ONCE IMPLEMENTED INTO LIBGDX 
	
	public void passBullet(Bullet theBullet) 
	{
		bulletList.add(this.theBullet);
	}
	
	public static void Draw(SpriteBatch spriteBatch, float delta)
	{
		// update the world/anything that needs to be updated
			
		// Draw the player's tank
		if(ourTank != null)	
			ourTank.draw(spriteBatch);	
		
		// This draws all the tanks in the list
		if (listOfTanks != null)
		{
			for(tankObjects drawTheTanks : listOfTanks)
				drawTheTanks.draw(spriteBatch);
		}

		// This draws all the bullets in the list
		
		// NOTE: if the bullets are currently being drawn in the TANK class, remove below (above may update both tank and bullets(?)
		
		if (bulletList != null)
		{
			for(Bullet drawTheBullets : bulletList)
				drawTheBullets.draw(spriteBatch);
		}
		

	}
	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	Initialize()
		//
		// Method parameters	:	void
		//
		// Method return		:	void
		//
		// Synopsis				: 	This method contains everything required to have the window for the client-side of
		//							the application to function properly. It also contains the buttons, which trigger
		//							other events to happen.
		//												
		//
		// Modifications		:
		//												Date			Developer				Notes
		//												----			---------				-----
		//												2022-01-25		Quentin G.				Comments
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		frame = new JFrame();										// Initialize jframe
		frame.setBounds(100, 100, 550, 433);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();		// Scheduled thread to update the message list
        ses.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {

				connectingToServer();							// Connects to the server to update list

            }
            
            // NOTE: Change time so it updates at a quicker pace
        }, 30, 20, TimeUnit.SECONDS);							// Seconds count down until next refresh/update
		
	}
}

