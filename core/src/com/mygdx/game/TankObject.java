package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class TankObject  {

	private enum tankTypes { LIGHT, MEDIUM, HEAVY };
	private tankTypes tankType;
	private float[] tankStats;
	private MyGdxGame game;
	private Texture tankBody;
	
	
	private Sprite tankSprite;
	
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
		this.game = game;
		if (ID == 1) {
			tankType = tankType.LIGHT;
			tankBody = game.manager.get("RT-76_Body.png", Texture.class);
		}
		if (ID == 2) {
			tankType = tankType.MEDIUM;
			tankBody = game.manager.get("MT-1984_Body.png", Texture.class);
		}
		if (ID == 3) {
			tankType = tankType.HEAVY;
			tankBody = game.manager.get("AT82_Body.png", Texture.class);
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
	
	public Texture getTankBodyTexture() {
		return tankBody;
	}
	

}
