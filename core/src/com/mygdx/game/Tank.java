package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Tank {

	private enum tankControl { ALL , TANK, TURRET };
	private tankControl controlSetting;
	private MyGdxGame game;
	private TurretObject theTurret;
	private TankObject theTankBody;
	private Sprite tankBodySprite;
	private Sprite turretSprite;
	private Vector2 tankPosition;
	private Vector2 tankDirection;
	private Vector2 turretPosition;
	private float tankAngle;
	private float turretAngle;
	private float[] tankStats;
	private float[] turretStats;
	private ArrayList<Tank> arrayOfTanks;
	
	public Tank(MyGdxGame game, short ID, short controlType) {
		theTankBody = new TankObject(game, ID);
		theTurret = new TurretObject(game, ID);
		tankPosition = new Vector2();
		tankDirection = new Vector2();
		turretPosition = new Vector2();
		tankStats = theTankBody.getTankStats();
		turretStats = theTurret.getTurretStats();
		arrayOfTanks = new ArrayList<Tank>();
		if (controlType == 0) {
			controlSetting = controlSetting.ALL;
		}
		if (controlType == 1) {
			controlSetting = controlSetting.TANK;
		}
		if (controlType == 2) {
			controlSetting = controlSetting.TURRET;
		}
		
		setRandomSpawnLocation();
		setTurretPosition();
		setTankAngle(0);
		setTurretAngle(0);
		
		tankBodySprite = new Sprite(theTankBody.getTankBodyTexture());
		tankBodySprite.setRotation(0);
		
		turretSprite = new Sprite(theTurret.getTurretHeadTexture());
		turretSprite.setRotation(0);
		
		this.game = game;
		addTankToArray();
	}
	
	private void addTankToArray() {
		arrayOfTanks.add(this);
	}
	
	private void setRandomSpawnLocation() {
		float spawnX = 0;
		float spawnY = 0;
		
		spawnX = (float) Math.random() * 1600;
		spawnY = (float) Math.random() * 1600;
		
		tankPosition.x = spawnX;
		tankPosition.y = spawnY;
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
	
	public void draw(SpriteBatch batch) {
		tankBodySprite.setPosition(tankPosition.y, tankPosition.x);
		tankBodySprite.draw(batch);
		
		turretSprite.setPosition(turretPosition.y, turretPosition.x);
		turretSprite.draw(batch);
	}
}
