package network;
//think of this as a mini version of the tank2 class. used for sending over, rather than the entire tank2 class with all it's unneeded info attached to it.
public class TankInfoPacket
{
	int health = 0 ;
	int healthmax = 0;
	String key = "";
	String size = "ERROR";
	float x;
	float y;
	
	float turretAngle = 0;
	float tankAngle = 0;

}
