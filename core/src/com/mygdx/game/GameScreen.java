package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	
	private boolean InLobby = true;
	private boolean InSelect = false;
	private boolean InGame = false;
	
	private boolean AT82_IsSelected;
	private boolean MT1982_IsSelected;
	private boolean RT76_IsSelected;
	
	private int mouseX;
	private int mouseY;
	
	private BitmapFont Font = new BitmapFont();
	
	private CharSequence[] TestArray = {"Joe Mama","Joe Sister","Joe Papa","Joe Brother"};
	private int TestNumber;
	private int YIncrament = 0;
	
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
		
		TestNumber = 0;
		
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
		
		Font.getData().setScale(5);
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
		//|						MAR 19 2022 J. Smith	added if statements to handle different screens
		//=======================================================================
		/*
		timeInSeconds += Gdx.graphics.getDeltaTime();	//timeInSeconds gets added to it's current amount in delta time 
														//with every call of the render (60fps)
		
		if (timeInSeconds > 0.05f) {	//if timeInSeconds is greater than 1/20 of a second
			timeInSeconds -= 0.05f;	//Subtract 1/20 of a second of the timer
			animateRover();	//Call the animateRover function
		}
		*/
		
		batch.begin();
		if (InLobby == true) {
			Gdx.gl.glClearColor(50/255f, 70/255f, 90/255f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			game.viewport.update(game.WIDTH, game.HEIGHT);
			batch.draw(game.manager.get("Main_Menu_Screen.png", Texture.class), 0 , 0);
			Font.draw(batch, "Play", 1920/2 - 50 , 1080/2);
			Font.draw(batch, "Player's In Lobby:", 25 , 1000 - YIncrament);
			YIncrament += 100;
			for (TestNumber = 0;TestNumber < TestArray.length; TestNumber++) {
				Font.draw(batch, TestArray[TestNumber], 25 , 1000 - YIncrament);
				YIncrament += 100;
			}
			YIncrament = 0;

		}
		
		if (InSelect == true){
			Gdx.gl.glClearColor(50/255f, 70/255f, 90/255f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			game.viewport.update(game.WIDTH, game.HEIGHT);
			batch.draw(game.manager.get("AT82_Select_Card.png", Texture.class), 0 , 632);
			batch.draw(game.manager.get("MT-1984_Select_Card.png", Texture.class), 1408 , 632);
			batch.draw(game.manager.get("RT-76_Select_Card.png", Texture.class), 1902/2-250 ,0);
			
			if (AT82_IsSelected) {
				DrawSelectBox(0 , 632);
				Font.draw(batch, "Confirm?", 1920/2 - 150 , 1080/2 + 200);
			}
			
			else if(MT1982_IsSelected) {
				DrawSelectBox(1408 , 632);
				Font.draw(batch, "Confirm?", 1920/2 - 150 , 1080/2 + 200);
			}
			
			else if(RT76_IsSelected) {
				DrawSelectBox(1902/2-250 ,0);
				Font.draw(batch, "Confirm?", 1920/2 - 150 , 1080/2 + 200);
			}
			
			Font.draw(batch, "Please Select A Tank", 1920/2 - 330 , 1080/2 + 100);
			
		}
		
		handlePlayerMouse();
		
		if (InGame == true) {
			handlePlayerMovement(delta);
			Gdx.gl.glClearColor(100/255f, 100/255f, 100/255f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			game.viewport.update(game.WIDTH, game.HEIGHT);
			game.camera.update();
			game.renderer.setView(game.camera);
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
		}

		batch.end();
		
		if ((Gdx.input.isKeyPressed(Input.Keys.ESCAPE))) {
			System.exit(0);
		};
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

	public void handlePlayerMouse() {
		//=======================================================================
		//|Method : handlePlayerMovement()
		//|
		//|Method parameters:
		//|
		//|What it does : handles the players mouse inputs
		//|
		//|
		//|Change log : Date Creator Notes
		//| =========== ======== =============
		//| MAR 18 2021 J. Smith Initial setup
		//=======================================================================
		mouseX = Gdx.input.getX();
		mouseY = Gdx.input.getY();
		
		//System.out.println("X: " + Gdx.input.getX() +"\nY: " + Gdx.input.getY());
		
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
			System.out.println("Mouse is clicked\n X: " + Gdx.input.getX() +"\n Y: " + Gdx.input.getY());
			if (InLobby == true &&
				mouseX > 910 && mouseX < 1050 && 
				mouseY > 540 && mouseY < 600) {
				
				InLobby = false;
				InSelect = true;
			}
		}
		
		if (InSelect == true) {
			if (mouseX > 0 && mouseX < 512 && 
				mouseY > 0 && mouseY < 448) {
				if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
					AT82_IsSelected = true;
					MT1982_IsSelected = false;
					RT76_IsSelected = false;
				}
				DrawSelectBox(0 ,632);
			}
			
			if (mouseX > 1408 && mouseX < 1920 && 
				mouseY > 0 && mouseY < 448) {
				if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
					AT82_IsSelected = false;
					MT1982_IsSelected = true;
					RT76_IsSelected = false;
				}
				DrawSelectBox(1408 ,632);
			}
			
			if (mouseX > 701 && mouseX < 1213 && 
				mouseY > 632 && mouseY < 1080) {
				if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
					AT82_IsSelected = false;
					MT1982_IsSelected = false;
					RT76_IsSelected = true;	
				}
				DrawSelectBox(1902/2-250 ,0);
			}
			
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) &&
			   (AT82_IsSelected || MT1982_IsSelected || RT76_IsSelected) &&
			    mouseX > 800 && mouseX < 1095 && 
			    mouseY > 340 && mouseY < 400){
				InSelect = false;
				InGame = true;
			}
		}
	}

	public void DrawSelectBox(int x, int y) {
		//=======================================================================
		//|Method : DrawSelectBox(int x, int y) ()
		//|
		//|Method parameters: (int x, int y)
		//|
		//|What it does : Dynamically draws the select box based off of the paramaters
		//|
		//|
		//|Change log : Date Creator Notes
		//| =========== ======== =============
		//| MAR 28 2021 J. Smith Initial setup
		//=======================================================================
		batch.draw(game.manager.get("Card_Selected.png", Texture.class), x , y);
	}
	
	public void hide() {
	}
	
	@Override
	public void dispose () {
		game.manager.dispose();
	}
}
