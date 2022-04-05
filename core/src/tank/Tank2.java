package tank;

import java.util.ArrayList;
import java.util.Date;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

import network.GameState;

//rewrite/improvement of the Tank family of classes. functionally the exact same. 
public class Tank2
{
	//medium tank defaults.
	//all of these are measured in either pixels per second, or degrees per second
	//yes, this uses deltatime.
	private static final float baseForwardSpeed = 64;
	private static final float baseBackwardSpeed = 48;
	private static final float baseRotationSpeed = 90;
	private static final float baseTurretRotationSpeed = 45;
	
	private long lastShot = 0;

	boolean youDied = false; //when true, we pan across the map.
	
	public static final int TILE_SIZE = 64;
	public static final int MAP_SIZE = 20; //square. frankly a brain dead spot to put it.
	//I stopped caring. team codes a character, I code a line. that's why I shitcode on group project time.
	
	public static enum tankTypes { LIGHT, MEDIUM, HEAVY }; 
	
	public static enum tankControl { ALL , TANK, TURRET }; //tank control types.
	
	//universal junk
	public Vector2 TankPos; //where are we?
	
	public tankTypes currentType = tankTypes.MEDIUM; //how chunky are we?
	public float TankRotation; //where are we facing?
	public float TurretRotation; //where are we aiming?
	public int maxHealth = 1450; //how much health can we have?
	public int health = 1450; //how much health DO we have? Doesn't matter anymore lol projectile moment
	
	private Texture tankBodyTexture; //texture for making the sprites.
	private Texture tankTurretTexture;
	
	private Sprite tankBodySprite; //a sprite variable that will be the visual representation of the tank
	private Sprite turretSprite; //a sprite variable that will be the visual representation of the turret
	

	//stats.
	float turnSpeed = baseRotationSpeed; //degrees per second.
	float forwardSpeed = baseForwardSpeed;//pixels per second
	float backwardSpeed = baseBackwardSpeed; //pixels per second.
	
	float fireRate = 4; //cooldown between shots. in seconds?
	
	long lastFired = 0; //when did we last shoot?
	
	tankControl currentControlMode = tankControl.ALL;
	
	private GameState gameState = new GameState();
	private MyGdxGame game;
	public Tank2(tankTypes type, Vector2 position, MyGdxGame game) //constructor. where we init the textures and spritesd.
	{
			currentType = type;
			TankPos = position;
			//set the stats.
			switch(type) //modify based on size, heavy, light, medium. etc.
			{
			case HEAVY: //health is irrelevant. 
				health = 2000; 
				forwardSpeed *= 2.3;
				backwardSpeed *= 2.3; //fastest, horrible at turning.
				turnSpeed *= 0.6;
				tankBodyTexture = game.manager.get("AT82_Body.png", Texture.class);
				tankTurretTexture = game.manager.get("AT82_Turret_Head.png", Texture.class);
				break;
			
			case LIGHT: //all around nimble.
				health = 750;
				forwardSpeed *= 1;
				backwardSpeed *= 6; //comically fast backwards.
				turnSpeed *= 3;
				tankBodyTexture = game.manager.get("RT-76_Body.png", Texture.class);
				tankTurretTexture =  game.manager.get("RT-76_Turret_Head.png", Texture.class);
				break;
				
			case MEDIUM:
				tankBodyTexture = game.manager.get("MT-1984_Body.png", Texture.class);
				tankTurretTexture = game.manager.get("MT-1984_Turret_Head.png", Texture.class);
				break;
				
			default: //saftey! medium tank. if you want to add a "I AM ERROR" tank, this is where you'd do it.
				tankBodyTexture = game.manager.get("MT-1984_Body.png", Texture.class); //sanity check.
				tankTurretTexture = game.manager.get("MT-1984_Turret_Head.png", Texture.class);
				break;
			
			}
			maxHealth = health;
			tankBodySprite = new Sprite(tankBodyTexture); //instantiates a new sprite for the tank body
			tankBodySprite.setRotation(0); //ensures that the tank body sprite is displayed at the proper angle
			
			turretSprite = new Sprite(tankTurretTexture); //instantiates a new sprite for the turret body
			turretSprite.setRotation(0); //ensures that the turret sprite is displayed at the proper angle
			
			this.game = game;
	}
	//Who: Connor Moffatt.
	//what: General input wrapper method. 
	//where: This is called every frame in the gamescreen class. 
	//why: Cleaner than just passing it directly onto the tank, and allows easy changing ofbehaviour. eg, "turret only" mode, which isn't implemented yet.
	
	public void do_input(float deltaTime, boolean W, boolean A, boolean S, boolean D, boolean Q, boolean E, boolean SPACE)
	{
		float rotation = 0;
		if(A)
			rotation += turnSpeed;
		if(D) //will cancel out.
			rotation -= turnSpeed;
		//TODO: control perms, ae, turret, all, tank. should be easy enough.
		if(E)
			TurretRotation -= baseTurretRotationSpeed*deltaTime;
		if(Q)
			TurretRotation += baseTurretRotationSpeed*deltaTime;
		
		TankRotation += rotation*deltaTime;
		
		float speed = 0;
		
		if(W)	
			speed += forwardSpeed*deltaTime;
		if(S)
			speed -= backwardSpeed*deltaTime;
			/*
		
			 */
		double rotationRadians = TankRotation*Math.PI/180; //Jared math. works, which I'm kinda proud of.
		Vector2 MovementVector = new Vector2((float)Math.cos(rotationRadians),(float)Math.sin(rotationRadians));
		TankPos.mulAdd(MovementVector, speed); //muladd multiplies the former arg by the second. in this case the speed is "pixels per second", multiplyed by jared's heading vector.
		if(TankPos.x > TILE_SIZE*(MAP_SIZE-1)) //alright, I'm blaming jared for the X and Y being inverted. //fixed.
		{
			TankPos.x = TILE_SIZE*(MAP_SIZE-1)-5;
		}
		if(TankPos.x < 0)
		{
			TankPos.x = 5;
		}
		if(TankPos.y > TILE_SIZE*(MAP_SIZE-1))
		{
			TankPos.y = TILE_SIZE*(MAP_SIZE-1)-5;
		}
		if(TankPos.y < 0)
		{
			TankPos.y = 5;
		}
		//TODO: limit to map boundries.
		//TODO: tank death effect. spawn a grave thing?
	}
	
	public void shoot() {
		return; //not needed anymore. not removing it.
	}
	
	
	public void update()
	{ //unused! here for when it may or may not ever not maybe potentially used.
			
	}
	
	//Draw method. draws both the tank and turret. self explainatory. 
	public void draw(SpriteBatch spriteBatch)
		{
			tankBodySprite.setRotation(TankRotation);
			tankBodySprite.setPosition(TankPos.x, TankPos.y);
			tankBodySprite.draw(spriteBatch);
			
		//	turretSprite.setRotation(TurretRotation); //we can just not draw it. it's more of a pain than not to rip it out 
		//	turretSprite.setPosition(TankPos.y,TankPos.x);
		//	turretSprite.draw(spriteBatch);				
		}

	
	
}
