package tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;


// Within the tank have a shot management. That is where an arraylist of bullets can be saved
// Delete the bullet in the arraylist when it collides OR goes beyond bullet life

public class Bullet {
	
	private Texture bulletTexture;
	private Sprite bulletSprite;
	
	public Vector2 position;
	public Vector2 direction;
	
	private float bulletAngle;
	private float bulletSpeed = 64*2;	
	private float bulletLifeTime = 2f;
	//public Vector2 theDir;
	
	private MyGdxGame game;
	
	public Bullet(MyGdxGame game, Vector2 newPos, float theAngle) 	// Create/instantiate 
	{
		position = new Vector2();
		direction = new Vector2();
		this.game = game; //ensures that all data from the class MyGdxGame is identical to the data of the same type here
		bulletTexture = game.manager.get("Tank_Bullet.png", Texture.class);
		bulletSprite = new Sprite(bulletTexture);
		
		setBulletPosition(newPos.x, newPos.y);
		setBulletAngle(theAngle);
		
	}
	
	// The DRAW method
	public void Draw(SpriteBatch batch) 	// The draw
	{
		bulletSprite.setPosition(position.y, position.x);
		bulletSprite.draw(batch);
	}
	
	public void setBulletAngle(float rotation) {
		bulletAngle -= rotation;
	}
	
	public void setBulletPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	public void Update(float delta)	// delta is the equivalent of delta time in this situation (AKA seconds)
	{
		// A counter here. If it's been more than the bullet life, delete it (object management)
		bulletLifeTime -= delta;
		
		if (bulletLifeTime <= 0)
		{
			// delete the bullet
		}
		
		
		// this should add to the x, therefore moving the bullet (TEMP)
		//position.x += 2;
		float radians = (float) (bulletAngle * (Math.PI / 180.f));
		direction.x = (float) Math.cos(Math.toDegrees(radians));
		direction.y = (float) Math.sin(Math.toDegrees(radians));
		
		position.x += direction.x * bulletSpeed * delta;
        position.y += direction.y * bulletSpeed * delta;
	}

}