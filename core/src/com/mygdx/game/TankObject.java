package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public class TankObject {

	private enum tankTypes { LIGHT, MEDIUM, HEAVY };
	private tankTypes tankType;
	private float tankPositionX;
	private float tankPositionY;
	private Vector2 tankPosition = new Vector2();
	private float tankAngle;
	private float[] tankStats;
	private MyGdxGame game;
	private Texture tankBody;
	private Texture turretHead;
	
	private enum turretTypes { LIGHT, MEDIUM, HEAVY };
	private turretTypes turretType;
	private float turretPositionX;
	private float turretPositionY;
	private Vector2 turretPosition = new Vector2();
	private float turretAngle;
	private float[] turretStats;
	
	private void setTankStats(short tankID) {
		//This method had to be rebuilt due to loss of data during an operation with github on 12/03/22
		/*
		 * Method:				setTanksStats
		 * 	
		 * Method Parameters:	short tankID
		 * 
		 * Method Return:		void
		 * 
		 * Synopsis:			This method will determine the tank the player wants
		 * 						to use based on the parameter passed. It will take the
		 * 						parameter and use it to apply a preset of stats for the
		 * 						tank.
		 * 
		 * Modifications:		Date:		Name:			Modification:
		 * 						13/03/22	Jared Shaddick	Initial Setup
		 */
		tankStats = new float[4];
		if (tankID == 1) {
			//light tank class
			tankStats[0] = 750.f; //tank health
			tankStats[1] = 35.f; //forward speed
			tankStats[2] = 22.f; //reverse speed
			tankStats[3] = 1.25f; //tank turning speed
		}
		if (tankID == 2) {
			//medium tank class
			tankStats[0] = 1450.f; //tank health
			tankStats[1] = 26.f; //forward speed
			tankStats[2] = 20.f; //reverse speed
			tankStats[3] = 1.f; //tank turning speed
		}
		if (tankID == 3) {
			//heavy tank class
			tankStats[0] = 2000; //tank health
			tankStats[1] = 19.f; //forward speed
			tankStats[2] = 9.f; //reverse speed
			tankStats[3] = 0.50f; //tank turning speed
		}
	}
	
	TankObject(MyGdxGame game, short ID) {
		setTankStats(ID);
		setTurretStats(ID);
		this.game = game;
		if (ID == 1) {
			tankBody = game.manager.get("RT-76_Body.png", Texture.class);
			turretHead = game.manager.get("RT-76_Turret_Head.png", Texture.class);
		}
		if (ID == 2) {
			tankBody = game.manager.get("MT-1984_Body.png", Texture.class);
			turretHead = game.manager.get("MT-1984_Turret_Head.png", Texture.class);
		}
		if (ID == 3) {
			tankBody = game.manager.get("AT82_Body.png", Texture.class);
			turretHead = game.manager.get("AT82_Turret_Head.png", Texture.class);
		}
	}
	
	public tankTypes getTankType() {
		/*
		 * method:				getTankType
		 * 
		 * method Parameters:	none
		 * 
		 * method reutrn:		tankTypes
		 * 
		 * Synopsis:			Accessor method that allows other classes
		 * 						to access the data in this method.
		 * 
		 * Modifictations:		Date:		Name:			Modification:
		 * 						13/03/22	Jared Shaddick	Initial Setup
		 */
		return tankType;
	}
	
	public float[] getTankStats() {
		/*
		 * method:				getTankStats
		 * 
		 * method Parameters:	none
		 * 
		 * method return:		short[]
		 * 
		 * Synopsis:			Accessor method that allows other classes
		 * 						to access the data in this method.
		 * 
		 * Modifications:		Date:		Name:			Modification:
		 * 						13/03/22	Jared Shaddick	Initial Setup
		 */
		return tankStats;
	}
	
	public void setTankPosition(float x, float y) {
		tankPosition.x = x;
		tankPosition.y = y;
	}
	
	public void setTankAngle(float rotation) {
		tankAngle += rotation;
	}
	
	public Vector2 getTankPosition() {
		return tankPosition;
	}
	
	public float getTankAngle() {
		return tankAngle;
	}
	
	
	
	public Texture getTankBodyTexture() {
		return tankBody;
	}
	
	public Texture getTurretHeadTexture() {
		return turretHead;
	}
	
	private void setTurretStats(short turretID) {
		//This method had to be rebuilt due to loss of data during an operation with github on 12/03/22
		/*
		 * Method:				setTanksStats
		 * 	
		 * Method Parameters:	short tankID
		 * 
		 * Method Return:		void
		 * 
		 * Synopsis:			This method will determine the turret the player wants
		 * 						to use based on the parameter passed. It will take the
		 * 						parameter and use it to apply a preset of stats for the
		 * 						turret.
		 * 
		 * Modifications:		Date:		Name:			Modification:
		 * 						14/03/22	Jonathan Gregan	Initial Setup
		 */
		turretStats = new float[2];
		if (turretID == 1) {
			//light tank class
			turretStats[0] = 200.f; //turret damage
			turretStats[1] = 2.25f; //turret turning speed
		}
		if (turretID == 2) {
			//medium tank class
			turretStats[0] = 250.f; //turret damage
			turretStats[1] = 2.f; //turret turning speed
		}
		if (turretID == 3) {
			//heavy tank class
			turretStats[0] = 300.f; //turret damage
			turretStats[1] = 1.5f; //turret turning speed
		}
	}
	
	public turretTypes getTurretType() {
		/*
		 * method:				getTankType
		 * 
		 * method Parameters:	none
		 * 
		 * method reutrn:		tankTypes
		 * 
		 * Synopsis:			Accessor method that allows other classes
		 * 						to access the data in this method.
		 * 
		 * Modifictations:		Date:		Name:			Modification:
		 * 						13/03/22	Jared Shaddick	Initial Setup
		 */
		return turretType;
	}
	
	public float[] getTurretStats() {
		/*
		 * method:				getTankStats
		 * 
		 * method Parameters:	none
		 * 
		 * method reutrn:		short[]
		 * 
		 * Synopsis:			Accessor method that allows other classes
		 * 						to access the data in this method.
		 * 
		 * Modifictations:		Date:		Name:			Modification:
		 * 						13/03/22	Jared Shaddick	Initial Setup
		 */
		return turretStats;
	}
	
	public void setTurretPosition(float x, float y) {
		turretPosition.x = x;
		turretPosition.y = y;
	}
	
	public void setTurretAngle(float rotation) {
		turretAngle += rotation;
	}
	
	public Vector2 getTurretPosition() {
		return turretPosition;
	}
	
	public float getTurretAngle() {
		return turretAngle;
	}
}
