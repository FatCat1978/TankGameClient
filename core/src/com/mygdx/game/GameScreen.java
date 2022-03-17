package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.game.TankObject;

public class GameScreen extends ScreenAdapter{

	//private short cameraZoomValue = 0;
	//private MyGdxGame game;
	private Cell usedLayerCell;
	//private PathFindingClass PathFindingInfo;
	private short animateRoverCounter = -1;
	
	private float timeInSeconds = 0f;
	
	private ArrayList<Short> cellArrayX;
	private ArrayList<Short> cellArrayY;
	

	SpriteBatch batch;
	Sprite tankSprite;
	Sprite turretSprite;
	TankObject theTank;
	TankObject theTurret;
	private short cameraZoomValue = 0;
	private MyGdxGame game;
	private short tankTypeID = 1; //variable responsible for selecting the type of tank the player wishes to use
	private float[] tankStats; //variable that holds the stats for a specific tank
	private float tankHealth; //the tank's health
	private float forwardSpeed; //the tank's forward speed
	private float reverseSpeed;	//the tank's reverse speed
	private float tankTurningSpeed; //the speed at which the tank turns
	private short turretTypeID = 1; //variable responsible for selecting the type of turret the player wishes to use
	private float[] turretStats; //variable that holds the stats for a specific turret
	private float turretDamage; //the turret's damage
	private float turretTurningSpeed; //the speed at which the turret turns
	
	private float spriteRotation;
	private float spriteRotation2;
	
	private Vector2 tankPosition = new Vector2();
	private Vector2 tankDirection = new Vector2();
	private Vector2 turretPosition = new Vector2();
	private Vector2 turretDirection = new Vector2();
	
	//Constructor Method
	public GameScreen(MyGdxGame game) {
		batch = new SpriteBatch();
		
		theTank = new TankObject(game, (short) tankTypeID);
		
		theTank.setTankPosition(455.f, 235.f);
		
		theTurret = new TankObject(game, (short) turretTypeID);
		
		theTurret.setTurretPosition(455.f, 235.f);
		
		tankPosition.x = theTank.getTankPosition().x;
		tankPosition.y = theTank.getTankPosition().y;
		
		turretPosition.x = theTurret.getTurretPosition().x;
		turretPosition.y = theTurret.getTurretPosition().y;
		
		
		tankSprite = new Sprite(theTank.getTankBodyTexture());
		tankSprite.setRotation(-90);
		
		turretSprite = new Sprite(theTurret.getTurretHeadTexture());
		turretSprite.setRotation(-90);
		
		
		tankStats = new float[4];
		tankStats = theTank.getTankStats();
		tankHealth = tankStats[0];
		forwardSpeed = tankStats[1];
		reverseSpeed = tankStats[2];
		tankTurningSpeed = tankStats[3];
		
		turretStats = new float[2];
		turretStats = theTurret.getTurretStats();
		turretDamage = turretStats[0];
		turretTurningSpeed = turretStats[1];
		
		this.game = game;
		loadAssetsNStuff();
	}
	
	public void loadAssetsNStuff(){
		game.manager.load("RT-76_Body.png", Texture.class);
		game.manager.load("MT82_Body.png", Texture.class);
		game.manager.load("MT-1984_Body.png", Texture.class);
		game.manager.load("RT-76_Turret_Head.png", Texture.class);
		game.manager.load("MT82_Turret_Head.png", Texture.class);
		game.manager.load("MT-1984_Turret_Head.png", Texture.class);
	}
	
	@Override
	public void show() {	
	}
	
	@Override
	public void render(float delta) {
		//=======================================================================
		//|Method			:	render				   			
		//|																   		
		//|Method parameters:										   		
		//|																   		
		//|What it does		:	Renders/executes everything in the method every frame.
		//|						Allows for camera movement, drawing of the tank, and
		//|						any movement associated with the tank.
		//|						
		//|																	    
		//|Change log		:	Date		Creator    	Notes			    
		//|						===========	========   	=============	    
		//|						FEB 10 2022	J. Smith 	Initial setup   
		//|						MAR 15 2022 J. Shaddick	Added a tank body
		//|												with ability to
		//|												move around
		//=======================================================================
		/*
		timeInSeconds += Gdx.graphics.getDeltaTime();	//timeInSeconds gets added to it's current amount in delta time 
														//with every call of the render (60fps)
		
		if (timeInSeconds > 0.05f) {	//if timeInSeconds is greater than 1/20 of a second
			timeInSeconds -= 0.05f;	//Subtract 1/20 of a second of the timer
			animateRover();	//Call the animateRover function
		}
		*/
		handlePlayerMovement(delta);
		batch.begin();
		Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.viewport.update(game.WIDTH, game.HEIGHT);
		game.camera.update();
		game.renderer.setView(game.camera);
		game.manager.load("RT-76_Body.png", Texture.class);
		game.manager.load("MT82_Body.png", Texture.class);
		game.manager.load("MT-1984_Body.png", Texture.class);
		game.renderer.render();
		
		//batch.draw(theTank.getTankBodyTexture(), theTank.getTankPositionY(), theTank.getTankPositionX());
				
		tankSprite.setPosition(tankPosition.x, tankPosition.y);
		tankSprite.draw(batch);
		
		//CAMERA MOVE CONTROL
		if ((Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
			game.camera.position.x -= 32;
		};
		if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
			game.camera.position.x += 32;
		};
		if ((Gdx.input.isKeyPressed(Input.Keys.UP))) {
			game.camera.position.y += 32;
		};
		if ((Gdx.input.isKeyPressed(Input.Keys.DOWN))) {
			game.camera.position.y -= 32;
		};
		
		//CAMERA ZOOM FUNTIONS
		if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_SUBTRACT) && cameraZoomValue != 20) {
			game.camera.zoom = game.camera.zoom + 0.1f;	//Lower the number the closer the camera
			cameraZoomValue += 1;
		};
		if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_ADD) && cameraZoomValue != 1) {
			game.camera.zoom = game.camera.zoom - 0.1f;	//Lower the number the closer the camera
			cameraZoomValue -= 1;
		};
		batch.end();
	}
	
	public void handlePlayerMovement(float delta) {
		//=======================================================================
		//|Method : handlePlayerMovement()
		//|
		//|Method parameters:
		//|
		//|What it does : handles the players movement
		//|
		//|
		//|Change log : Date Creator Notes
		//| =========== ======== =============
		//| Nov 15 2021 J. Smith Initial setup
		//| Mar 15 2022 J. Smith Adapted code for use
		//|						 for moving sprites
		//=======================================================================
		//playerInfo MOVEMENT CONTROL
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			System.out.println("A Key pressed");
			tankSprite.rotate(tankTurningSpeed);
			spriteRotation += tankTurningSpeed * (1/60.f);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			System.out.println("D Key pressed");
			tankSprite.rotate(-tankTurningSpeed);
			spriteRotation -= tankTurningSpeed * (1/60.f);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			System.out.println("W Key pressed");
			float radians = (float) (spriteRotation * (Math.PI / 180.f));
			tankDirection.x = (float) Math.cos(Math.toDegrees(radians));
			tankDirection.y = (float) Math.sin(Math.toDegrees(radians));
			
			tankDirection.x *= ((1/60.f) * forwardSpeed);
			tankDirection.y *= ((1/60.f) * forwardSpeed);
			
			tankPosition.x += tankDirection.x;
			tankPosition.y += tankDirection.y;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			System.out.println("S Key pressed" + reverseSpeed);
			float radians = (float) (spriteRotation * (Math.PI / 180.f));
			tankDirection.x = (float) Math.cos(Math.toDegrees(radians));
			tankDirection.y = (float) Math.sin(Math.toDegrees(radians));
			
			tankDirection.x *= ((1/60.f) * reverseSpeed);
			tankDirection.y *= ((1/60.f) * reverseSpeed);
			
			tankPosition.x -= tankDirection.x;
			tankPosition.y -= tankDirection.y;
		}
	}


	public void hide() {
	}
	
	@Override
	public void dispose () {
		game.manager.dispose();
	}
}
