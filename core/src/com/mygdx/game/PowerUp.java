package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PowerUp {
	private Sprite powerSprite;
	Vector2 powerPosition;
	private MyGdxGame game;
	private Texture powertexture;
	
	public Body body;
	
	public PowerUp (MyGdxGame game, World world)
	{
		this.game = game;
		powerSprite = new Sprite(getPowerTexture());
		powerSprite.setRotation(0);
		
		powerPosition = new Vector2();
		
		setRandomSpawnLocation();
		
		createBoxBody(world, powerPosition.x, powerPosition.y);
		
	}
	
	private void setRandomSpawnLocation() {
		float spawnX = 0;
		float spawnY = 0;
		
		spawnX = (float) Math.random() * 1600;
		spawnY = (float) Math.random() * 1600;
		
		powerPosition.x = spawnX;
		powerPosition.y = spawnY;
	}

	public void draw(SpriteBatch batch) {
		powerSprite.setPosition(powerPosition.x, powerPosition.y);
		powerSprite.draw(batch);
		
	}
	
	private void createBoxBody(World world, float x, float y) 
	{
		BodyDef bdef = new BodyDef();
		bdef.fixedRotation = true;
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set(powerPosition.x + 35, powerPosition.y + 35);
		
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(35, 35);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 1.0f;
		
		this.body = world.createBody(bdef);
		this.body.createFixture(fdef).setUserData(this);
		
		this.body.setTransform(powerPosition.x + 35, powerPosition.y + 35, 0);
		
	}
	
	public Texture getPowerTexture() {
		
		powertexture = game.manager.get("Crate_Health.png", Texture.class);
		return powertexture;
	}

}
