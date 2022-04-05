package com.mygdx.game;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;


public class GameScreen extends ScreenAdapter {
	//private short cameraZoomValue = 0;
	//private MyGdxGame game;
	private Cell usedLayerCell;
	//private PathFindingClass PathFindingInfo;
	private short animateRoverCounter = -1;
	
	private float timeInSeconds = 0f;
	
	private ArrayList<Short> cellArrayX;
	private ArrayList<Short> cellArrayY;
	

	SpriteBatch batch;
	Tank theTank;
	
	private tankAppClient theClient;
	PowerUp thepowerUp;
	
	//private Box2DDebugRenderer b2dr;
	private World world;
	
	private enum gameStatus { InLobby, GamePlay };
	private gameStatus gameScreen = gameStatus.InLobby;
	
	//TurretObject theTurret;
	private short cameraZoomValue = 0;
	private MyGdxGame game;
	private short tankTypeID; //variable responsible for selecting the type of tank the player wishes to use
	private short tankControlID = 0; //variable that will be used to dictate how the player controls the tank
	
	
	
	//Constructor Method
	public GameScreen(MyGdxGame game) {
		batch = new SpriteBatch();
		theClient = game.getClient();
		getIP();
		theClient.connectingToServer();
		getTankID();
		this.world = new World(new Vector2(0,0), false);
		this.world.setContactListener(new WorldContactListener());
		//this.b2dr = new Box2DDebugRenderer();
		
		theTank = new Tank(game, world, theClient, (short) tankTypeID, (short) tankControlID);
		
		
		thepowerUp = new PowerUp(game, world);
		
		
		
		this.game = game;
		loadAssetsNStuff();
		gameScreen = gameStatus.GamePlay;
		theClient.setScreen("GamePlay");
	}
	
	public void getIP() {
		String IP = "";
		IP = JOptionPane.showInputDialog(null, "Please indicate the IP address of the host!!");
		theClient.getUserIP(IP);
	}
	
	public void getTankID() {
		String tankID;
		tankID = JOptionPane.showInputDialog(null, "Please indicate the tank class you wish to use\n1 = Light, 2 = Medium, >=3 = Heavy");
		tankTypeID = (short) Integer.parseInt(tankID);
		theClient.setTankChoice(tankID);
	}
	
	public void loadAssetsNStuff(){
		game.manager.load("RT-76_Body.png", Texture.class);
		game.manager.load("MT82_Body.png", Texture.class);
		game.manager.load("MT-1984_Body.png", Texture.class);
		game.manager.load("RT-76_Turret_Head.png", Texture.class);
		game.manager.load("MT82_Turret_Head.png", Texture.class);
		game.manager.load("MT-1984_Turret_Head.png", Texture.class);
		game.manager.load("Crate_Health.png", Texture.class);
		game.manager.load("Crate_Sniper.png", Texture.class);
		game.manager.load("Crate_Para.png", Texture.class);
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
		update(delta);
		handlePlayerMovement(delta);
		batch.begin();
		Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.viewport.update(game.WIDTH, game.HEIGHT);
		game.camera.update();
		game.renderer.setView(game.camera);
		game.manager.load("Tank_Bullet.png", Texture.class);
		game.manager.load("RT-76_Body.png", Texture.class);
		game.manager.load("MT82_Body.png", Texture.class);
		game.manager.load("MT-1984_Body.png", Texture.class);
		game.manager.load("RT-76_Turret_Head.png", Texture.class);
		game.manager.load("MT82_Turret_Head.png", Texture.class);
		game.manager.load("MT-1984_Turret_Head.png", Texture.class);
		game.manager.load("Crate_Health.png", Texture.class);
		game.manager.load("Crate_Sniper.png", Texture.class);
		game.manager.load("Crate_Para.png", Texture.class);
		game.renderer.render();
		
		//b2dr.render(world, game.camera.combined);
		
		//theClient.draw(batch);
		
		theTank.draw(batch);
		
		thepowerUp.draw(batch);
		
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
			theTank.turnTankLeft(true);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			theTank.turnTankRight(true);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			theTank.moveTankForward(true);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			theTank.moveTankBackward(true);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			theTank.turnTurretLeft(true);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			theTank.turnTurretRight(true);
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			theTank.spacePressed(true);
		}
	}
	
	public void update(float delta)
	{
		
		world.step(delta, 6, 2);
		batch.setProjectionMatrix(game.camera.combined);
		
	}
	

	public void hide() {
	}
	
	@Override
	public void dispose () {
		//b2dr.dispose();
		world.dispose();
		game.manager.dispose();
	}
}
