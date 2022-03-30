package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class TankObject  {

	private enum tankTypes { LIGHT, MEDIUM, HEAVY }; //an enum containing tank classes
	private tankTypes tankType; //a variable for the enum tankTypes that will be used to share the type of tank being used
	private float[] tankStats; //array of tank stats
	private MyGdxGame game;
	private Texture tankBody; //a texture for the tank's body
	
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
		/*
		 * Constructor:					TankObject
		 * 
		 * Constructor Parameters:		MyGdxGame game, short ID
		 * 
		 * Synopsis:					This constructor serves as the template
		 * 								from which the tank's body will be created
		 * 
		 * Modifications:				Date:		Name:			Modifications:
		 * 								03/14/22	Jared Shaddick	Initial Setup
		 * 								03/20/22	Jared Shaddick	Moved to a Separate Class
		 */
		setTankStats(ID); //calls the method setTankStats using the TankID parameter passed from the GameScreen class
		this.game = game; //ensures that all data from the class MyGdxGame is identical to the data of the same type here
		//conditional statement that determines the tank class and texture for the tank body
		if (ID == 1) {
			//light tank
			tankType = tankType.LIGHT;
			tankBody = game.manager.get("RT-76_Body.png", Texture.class);
		}
		if (ID == 2) {
			//medium tank
			tankType = tankType.MEDIUM;
			tankBody = game.manager.get("MT-1984_Body.png", Texture.class);
		}
		if (ID == 3) {
			//heavy tank
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
		/*
		 * Method Name:					getTankBodyTexture
		 * 
		 * Method Parameters:			None
		 * 
		 * Method Return:				Texture
		 * 
		 * Synopsis:					This method returns the texture
		 * 								associated with the chosen tank
		 * 
		 * Modifications:				Date:		Name:			Modification:
		 * 								03/16/22	Jared Shaddick	Initial Setup
		 */
		return tankBody;
	}
	

}
