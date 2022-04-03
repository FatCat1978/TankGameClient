package network;
public class ClientToServerPacket
{
	String packetType = "lobby";
	String packetInfo = "";
}
//I don't need to explain this. every other packet works the same way. we convert to json and send it