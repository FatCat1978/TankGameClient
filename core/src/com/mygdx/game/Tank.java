package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Tank {

	private enum tankControl { ALL , TANK, TURRET }; //an enum that holds values for the control types for a player
	private tankControl controlSetting; //a variable for an enum that will be assigned a value from the enum dealing with control types
	private TurretObject theTurret; //an uninstantiated instance of the TurretObject class
	private Bullet theBullet;
	private TankObject theTankBody; //an uninstantiated instance of the TankObject class
	private Sprite tankBodySprite; //a sprite variable that will be the visual representation of the tank
	private Sprite bulletSprite;
	private Sprite turretSprite; //a sprite variable that will be the visual representation of the turret
	private Vector2 tankPosition; //a vector2 variable that is responsible for the position of the tank body an by extension the turret
	private Vector2 tankDirection; //a vector2 variable that is responsible for the direction the tank body should be headed in
	private Vector2 turretPosition; //a vector2 variable that is repsonsible for the turret's position (should be the same x and y as the tank, rotation is different)
	private Vector2 turretDirection;
	private Vector2 bulletPosition;
	private Vector2 bulletDirection;
	private Vector2 bulletVelocity; 
	private float tankAngle;
	private float turretAngle;
	private float[] tankStats; //array that holds stats for the tank
	private float[] turretStats; //array that holds stats for the turret
	private ArrayList<Tank> arrayOfTanks; //an arrayList to be used to hold tank objects
	private ArrayList<BulletObject> bulletList;
	private MyGdxGame game;
	
	public Tank(MyGdxGame game, short ID, short controlType) {
		/*
		 * Constructor Name:					Tank
		 * 
		 * Constructor Parameters:				MyGdxGame game, short ID, short controlType
		 * 
		 * Synopsis:							This constructor is the unifying point for the tank body and turret
		 * 										this will only be created once from the GameScreen class. This constructor
		 * 										must never be used for objects that are dynamically created as a tank will only
		 * 										ever be created once and this constructor will never be instantiated again
		 * 
		 * Modifications:						Date:		Name:			Modifications:
		 * 										03/20/22	Jared Shaddick	Initial Setup
		 */
		theTankBody = new TankObject(game, ID); //instantiates a new tank(body)Object
		theTurret = new TurretObject(game, ID); //instantiates a new turretObject
		tankPosition = new Vector2(); //instantiates a new vector2 variable for traking the position of the tank body
		tankDirection = new Vector2(); //instantiates a new vector2 variable for tracking the direction in which the tank is moving
		turretPosition = new Vector2(); //instantiates a new vector2 variable for tracking the turret's angle and keeping the turret's location equal to that of the tank
		turretDirection = new Vector2();
		bulletPosition = new Vector2();
		bulletDirection = new Vector2();
		bulletVelocity = new Vector2();
		tankStats = theTankBody.getTankStats(); //sets the array of stats for the tank from the TankObject class to the tankStats array here
		turretStats = theTurret.getTurretStats(); //sets the array of stats for the tank from the TurretObject class to the turretStats array here
		arrayOfTanks = new ArrayList<Tank>(); //instantiates an arraylist that will hold all the tanks in the game
		//conditionals that will be used to set the enum variable for available controls
		if (controlType == 0) {
			//control over both tank body and turret
			controlSetting = controlSetting.ALL;
		}
		if (controlType == 1) {
			//control over tank body
			controlSetting = controlSetting.TANK;
		}
		if (controlType == 2) {
			//control over turret
			controlSetting = controlSetting.TURRET;
		}
		bulletList = new ArrayList<BulletObject>();
		setRandomSpawnLocation(); //method sets a random spawn loaction
		setTurretPosition(tankPosition.x, tankPosition.y); //method that will ensure that the turret is at that same position as the tank
		setTankAngle(0); //sets the initial angle of the tank body
		setTurretAngle(0); //sets the initial angle of the turret body
		
		tankBodySprite = new Sprite(theTankBody.getTankBodyTexture()); //instantiates a new sprite for the tank body
		tankBodySprite.setRotation(0); //ensures that the tank body sprite is displayed at the proper angle
		
		turretSprite = new Sprite(theTurret.getTurretHeadTexture()); //instantiates a new sprite for the turret body
		turretSprite.setRotation(0); //ensures that the turret sprite is displayed at the proper angle
		
		this.game = game; //ensures that all data from the class MyGdxGame is identical to the data of the same type here
		addTankToArray(); //calls the method that adds the tank to an arraylist
	}
	
	private void addTankToArray() {
		arrayOfTanks.add(this);
	}
	
	private void setRandomSpawnLocation() {
		/*
		 * Method Name:					setRandomSpawnLocation
		 * 
		 * Method Parameters:			None
		 * 
		 * Method Return:				void
		 * 
		 * Synopsis:					This method will place a tank in a random 
		 * 								location within the bounds of the map
		 * 
		 * Modifications:				Date:		Names:			Modifications:
		 * 								03/20/22	Jared Shaddick	Initial Setup
		 */
		float spawnX = 0; //temp variable used to get a random position on the X axis
		float spawnY = 0; //temp variable used to get a random position on the Y axis
		
		spawnX = (float) Math.random() * 3200; //set a random X position within the map's size
		spawnY = (float) Math.random() * 3200; //set a random Y position within the map's size
		
		tankPosition.x = spawnX; //set the X position of the actual tank
		tankPosition.y = spawnY; //set the Y position of the actual tank
	}
	
	private Vector2 getTankPosition() {
		return tankPosition;
	}
	
	private void setTurretPosition() {
		turretPosition = tankPosition;
	}
	
	private void setTankAngle(float rotation) {
		tankAngle += rotation;
	}
	
	public float getTankAngle() {
		return tankAngle;
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
	
	public void moveTankForward(boolean keyPressed) {
		if (controlSetting.equals(controlSetting.ALL) || controlSetting.equals(controlSetting.TANK)) {
			float radians = (float) (-getTankAngle() * (Math.PI / 180.f));
			tankDirection.x = (float) Math.cos(Math.toDegrees(radians));
			tankDirection.y = (float) Math.sin(Math.toDegrees(radians));
			
			tankDirection.x *= ((1/60.f) * tankStats[1]);
			tankDirection.y *= ((1/60.f) * tankStats[1]);
			
			tankPosition.x += tankDirection.x;
			tankPosition.y += tankDirection.y;
			
			turretPosition.x = tankPosition.x;
			turretPosition.y = tankPosition.y;
		}
	}
	
	public void moveTankBackward(boolean keyPressed) {
		if (controlSetting.equals(controlSetting.ALL) || controlSetting.equals(controlSetting.TANK)) {
			float radians = (float) (-getTankAngle() * (Math.PI / 180.f));
			tankDirection.x = (float) Math.cos(Math.toDegrees(radians));
			tankDirection.y = (float) Math.sin(Math.toDegrees(radians));
			
			tankDirection.x *= ((1/60.f) * theTankBody.getTankStats()[2]);
			tankDirection.y *= ((1/60.f) * theTankBody.getTankStats()[2]);
			
			tankPosition.x -= tankDirection.x;
			tankPosition.y -= tankDirection.y;
			
			turretPosition.x = tankPosition.x;
			turretPosition.y = tankPosition.y;
		}
	}
	
	public void turnTankLeft(boolean keyPressed) {
		if (controlSetting.equals(controlSetting.ALL) || controlSetting.equals(controlSetting.TANK)) {
			tankBodySprite.rotate(tankStats[3]);
			setTankAngle(tankStats[3] * (1/60.f));
		}
	}
	
	public void turnTankRight(boolean keyPressed) {
		if (controlSetting.equals(controlSetting.ALL) || controlSetting.equals(controlSetting.TANK)) {
			tankBodySprite.rotate(-tankStats[3]);
			setTankAngle(-tankStats[3] * (1/60.f));
		}
	}
	
	public void turnTurretLeft(boolean keyPressed) {
		if (controlSetting.equals(controlSetting.ALL) || controlSetting.equals(controlSetting.TURRET)) {
			turretSprite.rotate(turretStats[1]);
			setTurretAngle(turretStats[1] * (1/60.f));
		}
	}
	
	public void turnTurretRight(boolean keyPressed) {
		if (controlSetting.equals(controlSetting.ALL) || controlSetting.equals(controlSetting.TURRET)) {
			turretSprite.rotate(-turretStats[1]);
			setTurretAngle(-turretStats[1] * (1/60.f));
		}
	}
	
	public void shootBullet(boolean keyPressed) {
		float turretAngle = getTurretAngle();
		bulletPosition = turretPosition;
		theBullet = new Bullet(game, bulletPosition, turretAngle);
	}
	
	public void draw(SpriteBatch batch) {
		tankBodySprite.setPosition(tankPosition.y, tankPosition.x);
		tankBodySprite.draw(batch);
		
		turretSprite.setPosition(turretPosition.y, turretPosition.x);
		turretSprite.draw(batch);
		
		if (theBullet != null) {
			theBullet.Draw(batch);
		}
	}
}
