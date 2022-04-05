package network;

import java.util.ArrayList;
import java.util.HashMap;

public class GameStateSend {
	HashMap<String, TankInfoPacket> allGameTanks = new HashMap<String, TankInfoPacket>();
	boolean youDied = false; //if true, client deletes it's tank. spectator mode. no proper lobby system for this because A: no time. B: I stopped caring.
	ArrayList<TankGraveLoc> allTankGraveLocs = new ArrayList<TankGraveLoc>();
	boolean forceUpdateTank = false;
}
