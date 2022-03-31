package network;


public class LobbyInfo
{
	public String chosenTankType = ""; //sent TO the server
	public String connectedPlayers = ""; //gotten FROM the server
	public String yourKey = ""; //FROM the server.
	public boolean GameInProgress = false; //From the server
	public int RequiredPlayers = 0; //from the server. 
	public boolean StopExistingInLobby = false; //tells the client we're shifting to a REAL BOY GAME
}