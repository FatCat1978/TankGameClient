package tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

//rewrite/improvement of the Tank family of classes. 
public class Tank2
{
	//medium tank defaults.
	private static final float baseForwardSpeed = 64;
	private static final float baseBackwardSpeed = 48;
	private static final float baseRotationSpeed = 90;
	private static final float baseTurretRotationSpeed = 45;
	
	
	
	
	public static enum tankTypes { LIGHT, MEDIUM, HEAVY }; //an enum containing tank classes
	
	public static enum tankControl { ALL , TANK, TURRET }; //an enum that holds values for the control types for a player
	
	//universal junk
	public Vector2 TankPos;
	
	public tankTypes currentType = tankTypes.MEDIUM;
	public float TankRotation;
	public float TurretRotation;
	public int maxHealth = 1450;
	public int health = 1450;
	
	private Texture tankBodyTexture;
	private Texture tankTurretTexture;
	
	private Sprite tankBodySprite; //a sprite variable that will be the visual representation of the tank
	private Sprite turretSprite; //a sprite variable that will be the visual representation of the turret
	

	//stats.
	float turnSpeed = baseRotationSpeed; //degrees per second.
	float forwardSpeed = baseForwardSpeed;//pixels per second
	float backwardSpeed = baseBackwardSpeed; //pixels per second.
	
	float fireRate = 4; //cooldown between shots.
	
	long lastFired = 0; //when did we last shoot?
	
	tankControl currentControlMode = tankControl.ALL;
	
	
	
	public Tank2(tankTypes type, Vector2 position, MyGdxGame game)
	{
			currentType = type;
			TankPos = position;
			//set the stats.
			switch(type)
			{
			case HEAVY:
				health = 2000;
				forwardSpeed *= 0.6;
				backwardSpeed *= 0.6;
				turnSpeed *= 0.6;
				tankBodyTexture = game.manager.get("AT82_Body.png", Texture.class);
				tankTurretTexture = game.manager.get("AT82_Turret_Head.png", Texture.class);
				break;
			
			case LIGHT:
				health = 750;
				forwardSpeed *= 1.6;
				backwardSpeed *= 1.6;
				turnSpeed *= 1.6;
				tankBodyTexture = game.manager.get("RT-76_Body.png", Texture.class);
				tankTurretTexture =  game.manager.get("RT-76_Turret_Head.png", Texture.class);
				break;
				
			case MEDIUM:
				tankBodyTexture = game.manager.get("MT-1984_Body.png", Texture.class);
				tankTurretTexture = game.manager.get("MT-1984_Turret_Head.png", Texture.class);
				break;
				
			default:
				tankBodyTexture = game.manager.get("MT-1984_Body.png", Texture.class); //sanity check.
				tankTurretTexture = game.manager.get("MT-1984_Turret_Head.png", Texture.class);
				break;
			
			}
			maxHealth = health;
			tankBodySprite = new Sprite(tankBodyTexture); //instantiates a new sprite for the tank body
			tankBodySprite.setRotation(0); //ensures that the tank body sprite is displayed at the proper angle
			
			turretSprite = new Sprite(tankTurretTexture); //instantiates a new sprite for the turret body
			turretSprite.setRotation(0); //ensures that the turret sprite is displayed at the proper angle
	}
	
	public void do_input(float deltaTime, boolean W, boolean A, boolean S, boolean D, boolean Q, boolean E)
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
			 * 			float radians = (float) (-getTankAngle() * (Math.PI / 180.f));
			tankDirection.x = (float) Math.cos(Math.toDegrees(radians));
			tankDirection.y = (float) Math.sin(Math.toDegrees(radians));
			 */
		double rotationRadians = -TankRotation*Math.PI/180;
		Vector2 MovementVector = new Vector2((float)Math.cos(rotationRadians),(float)Math.sin(rotationRadians));
		TankPos.mulAdd(MovementVector, speed);
	}
	
	public void update()
	{
			
	}
	public void draw(SpriteBatch spriteBatch)
		{
			tankBodySprite.setRotation(TankRotation);
			tankBodySprite.setPosition(TankPos.y, TankPos.x);
			tankBodySprite.draw(spriteBatch);
			
			turretSprite.setRotation(TurretRotation);
			turretSprite.setPosition(TankPos.y,TankPos.x);
			turretSprite.draw(spriteBatch);				
	}

	
	
}
