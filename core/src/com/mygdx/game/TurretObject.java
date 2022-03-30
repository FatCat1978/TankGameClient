package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TurretObject {
	
	private Texture turretHead; //Texture for the turret
	private enum turretTypes { LIGHT, MEDIUM, HEAVY }; //enum that contains the turret classes
	private turretTypes turretType; //enum variable that will be used to indicate the class of turret to use
	private float[] turretStats; //array of turret stats
	
	private MyGdxGame game;
	
	public TurretObject(MyGdxGame game, short ID) {
		/*
		 * Constructor Name:				TurretObject
		 * 	
		 * Constructor Parameters:			MyGdxGame game, short ID
		 * 
		 * Synopsis:						This constructor serves as the template
		 * 									from which the turret will be created
		 * 
		 * Modifications:					Date:		Name:			Modifications:
		 * 									03/16/22	Jonathan Gregan	Initial Setup
		 * 									03/20/22	Jared Shaddick	Changed Code to Match
		 * 																the Rest of the Tank Related
		 * 																Code
		 */
		setTurretStats(ID); //calls the method setTurretStats using the parameter passed from the GameScreen class 
		this.game = game; //ensures that all data from the class MyGdxGame is identical to the data of the same type here
		//conditional statement used to determine the enum and texture to use for the turret
		if (ID == 1) {
			//light tank turret
			turretType = turretType.LIGHT;
			turretHead = game.manager.get("RT-76_Turret_Head.png", Texture.class);
		}
		if (ID == 2) {
			//medium tank turret
			turretType = turretType.MEDIUM;
			turretHead = game.manager.get("MT-1984_Turret_Head.png", Texture.class);
		}
		if (ID == 3) {
			//heavy tank turret
			turretType = turretType.HEAVY;
			turretHead = game.manager.get("AT82_Turret_Head.png", Texture.class);
		}
	}

	public Texture getTurretHeadTexture() {
		/*
		 * Method Name:					getTurretHeadTexture
		 * 
		 * Method Parameters:			None
		 * 
		 * Method Return:				Texture
		 * 
		 * Synopsis:					This method returns the texture
		 * 								associated with the chosen turret
		 * 
		 * Modifications:				Date:		Name:			Modifications:
		 * 								03/16/22	Jonathan Gregan	Initial Setup
		 */
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
}
