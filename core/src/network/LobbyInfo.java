package network;

import com.google.gson.Gson;


public class LobbyInfo
{
	public String chosenTankType = ""; //sent TO the server
	public String connectedPlayers = ""; //gotten FROM the server
	public String yourKey = ""; //FROM the server.
	public boolean GameInProgress = false; //From the server
	public int RequiredPlayers = 0; //from the server. 
		
}