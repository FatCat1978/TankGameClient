package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TurretObject {
	
	private Texture turretHead;
	private Sprite turretSprite;
	private enum turretTypes { LIGHT, MEDIUM, HEAVY };
	private turretTypes turretType;
	private float turretPositionX;
	private float turretPositionY;
	private float turretAngle;
	private float[] turretStats;
	
	private MyGdxGame game;
	
	public TurretObject(MyGdxGame game, short ID) {
		setTurretStats(ID);
		this.game = game;
		if (ID == 1) {
			turretType = turretType.LIGHT;
			turretHead = game.manager.get("RT-76_Turret_Head.png", Texture.class);
		}
		if (ID == 2) {
			turretType = turretType.MEDIUM;
			turretHead = game.manager.get("MT-1984_Turret_Head.png", Texture.class);
		}
		if (ID == 3) {
			turretType = turretType.HEAVY;
			turretHead = game.manager.get("AT82_Turret_Head.png", Texture.class);
		}
	}

	public Texture getTurretHeadTexture() {
		return turretHead;
	}
	
	public void getTankPosition() {
		
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
}
