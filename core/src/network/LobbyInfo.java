package network;

//this is converted to json and put into the packet's info slot.

//basically just a fancy holder to make code happy organize :)

public class LobbyInfo
{
	public boolean Ready = false; //to
	public String chosenTankType = ""; //sent TO the server
	public String connectedPlayers = ""; //gotten FROM the server
	public String yourKey = ""; //FROM the server.
	public boolean GameInProgress = false; //From the server
	public int RequiredPlayers = 0; //from the server. 
	public boolean StopExistingInLobby = false; //tells the client we're shifting to a REAL BOY GAME
}