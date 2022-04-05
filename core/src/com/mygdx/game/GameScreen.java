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
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.game.TankObject;

import network.GameState;
import tank.Tank2;

public class GameScreen extends ScreenAdapter {

	

	SpriteBatch batch;
	Tank2 theTank;
	//TurretObject theTurret;
	private short cameraZoomValue = 0;
	private MyGdxGame game;
	private short tankTypeID = 1; //variable responsible for selecting the type of tank the player wishes to use
	private short tankControlID = 0; //variable that will be used to dictate how the player controls the tank
	
	private World world;

	
	private int mouseX;
	private int mouseY;
	
	private BitmapFont Font = new BitmapFont();
	
	private CharSequence[] TestArray = {"Joe Mama","Joe Sister","Joe Papa","Joe Brother"};
	private int TestNumber;
	private int YIncrament = -100;
		
	
	GameState gameState; 
	
	
	
	//Constructor Method
	public GameScreen(MyGdxGame game) {
		batch = new SpriteBatch();
		
		world = new World(new Vector2(0,0), true);
	
		
		TestNumber = 0;
		
		gameState = new GameState();
		GameState.game = game;
		gameState.run();

		this.game = game;
		loadAssetsNStuff(); //WHY
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
		//System.out.println("STATE:" + GameState.currentGameState);
		batch.begin();
		//TODO: break this out into 3 independent render methods.
		if (GameState.currentGameState == GameState.allGameStates.IN_LOBBY){
			
			Gdx.gl.glClearColor(50/255f, 70/255f, 90/255f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			game.viewport.update(game.WIDTH, game.HEIGHT);
			batch.draw(game.manager.get("Main_Menu_Screen.png", Texture.class), 0 , 0);
			Font.draw(batch, "Play", 1920/2 - 50 , 1080/2);
			
			Font.draw(batch, "Players In Lobby:", 25 , 900); //TODO: update from server?
			YIncrament = 100;
			for (TestNumber = 0;TestNumber < TestArray.length; TestNumber++) {
				Font.draw(batch, TestArray[TestNumber], 25 , 900 - YIncrament);
				YIncrament += 50;
			}
		}
		
		if (GameState.currentGameState == GameState.allGameStates.IN_SELECT){
			Gdx.gl.glClearColor(50/255f, 70/255f, 90/255f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			game.viewport.update(game.WIDTH, game.HEIGHT);
			batch.draw(game.manager.get("AT82_Select_Card.png", Texture.class), 0 , 632);
			batch.draw(game.manager.get("MT-1984_Select_Card.png", Texture.class), 1408 , 632);
			batch.draw(game.manager.get("RT-76_Select_Card.png", Texture.class), 1902/2-250 ,0);
			
			if (GameState.currentTankPick == GameState.allTankPicks.TANK_LARGE) {
				DrawSelectBox(0 , 632);
				Font.draw(batch, "Confirm?", 1920/2 - 150 , 1080/2 + 200);
			}
			
			if(GameState.currentTankPick == GameState.allTankPicks.TANK_MEDIUM) {
				DrawSelectBox(1408 , 632);
				Font.draw(batch, "Confirm?", 1920/2 - 150 , 1080/2 + 200);
			}
			
			if(GameState.currentTankPick == GameState.allTankPicks.TANK_SMALL) {
				DrawSelectBox(1902/2-250 ,0);
				Font.draw(batch, "Confirm?", 1920/2 - 150 , 1080/2 + 200);
			}
			
			Font.draw(batch, "Please Select A Tank", 1920/2 - 330 , 1080/2 + 100);
		}
		
		handlePlayerMouse();
		
		if(GameState.currentGameState == GameState.allGameStates.WAITING_FOR_SERVER)
		{

			
			batch.draw(game.manager.get("Main_Menu_Screen.png", Texture.class), 0 , 0);
			Font.draw(batch, "Waiting for server!", 1920/3 , 1080/2);
			Font.draw(batch, "Players connected:" + GameState.LI.connectedPlayers, 1920/3 , 1080/3 -50);
			Font.draw(batch, "Your Tank:" + GameState.LI.chosenTankType, 1920/3 , 1080/4-25);
			Font.draw(batch, "Players Required:" + GameState.LI.RequiredPlayers, 1920/3 , 1080/5-30);
			if(GameState.offlineMode == true)
			{
				GameState.currentGameState = GameState.allGameStates.IN_GAME;
			}
		}
		
		if (GameState.currentGameState == GameState.allGameStates.IN_GAME) {
			handlePlayerMovement(delta);
			Gdx.gl.glClearColor(100/255f, 100/255f, 100/255f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			game.viewport.update(game.WIDTH, game.HEIGHT);
			game.camera.update();
			game.renderer.setView(game.camera);
			game.renderer.render();
			
			//batch.draw(theTank.getTankBodyTexture(), theTank.getTankPositionY(), theTank.getTankPositionX());
					
			//CAMERA ZOOM FUNTIONS
			if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_SUBTRACT) && cameraZoomValue != 20) {
				game.camera.zoom = game.camera.zoom + 0.1f;	//Lower the number the closer the camera
				cameraZoomValue += 1;
			};
			if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_ADD) && cameraZoomValue != 1) {
				game.camera.zoom = game.camera.zoom - 0.1f;	//Lower the number the closer the camera
				cameraZoomValue -= 1;
			};
			
			
			handlePlayerMovement(delta);
			Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			game.viewport.update(game.WIDTH, game.HEIGHT);
			game.camera.update();
			game.renderer.setView(game.camera);
			game.manager.load("RT-76_Body.png", Texture.class);
			game.manager.load("MT82_Body.png", Texture.class);
			game.manager.load("MT-1984_Body.png", Texture.class);
			game.renderer.render();
					
			GameState.Draw(batch, delta);
			
			
			//CAMERA MOVE CONTROL
			if(GameState.get_client_tank() != null && GameState.deadOrNot != true)
			{
			game.camera.position.x = GameState.get_client_tank().TankPos.x;
			game.camera.position.y = GameState.get_client_tank().TankPos.y; //no clue why these need to be inverted? this is actually kinda scary but it
			}
			//is far too late in the morning for me to hunt for WHY this happens
			
			
			if ((Gdx.input.isKeyPressed(Input.Keys.ESCAPE))) {
				System.exit(0);
			};
			
		}

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
		// Mar 31 - Connor Moffatt -  changed as part of tank refactor
		//=======================================================================
		//playerInfo MOVEMENT CONTROL
			boolean W = Gdx.input.isKeyPressed(Input.Keys.W);
			boolean A = Gdx.input.isKeyPressed(Input.Keys.A);
			boolean S = Gdx.input.isKeyPressed(Input.Keys.S);
			boolean D = Gdx.input.isKeyPressed(Input.Keys.D);
			boolean Q = Gdx.input.isKeyPressed(Input.Keys.Q);
			boolean E = Gdx.input.isKeyPressed(Input.Keys.E);
			boolean SPACE = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
			
			if(GameState.deadOrNot == false && GameState.ourTank != null)
				GameState.do_input(delta, W, A, S, D, Q, E, SPACE);

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
			// mar 31 - C.Moffatt, changes from tank refactor
		//=======================================================================
		mouseX = Gdx.input.getX();
		mouseY = Gdx.input.getY();
		
		//System.out.println("X: " + Gdx.input.getX() +"\nY: " + Gdx.input.getY());
		
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
			//System.out.println("Mouse is clicked\n X: " + Gdx.input.getX() +"\n Y: " + Gdx.input.getY());
			if (GameState.currentGameState == GameState.allGameStates.IN_LOBBY &&
				mouseX > 910 && mouseX < 1050 && 
				mouseY > 540 && mouseY < 600) {
				
				GameState.currentGameState = GameState.allGameStates.IN_SELECT;

			}
		}
		
		if (GameState.currentGameState == GameState.allGameStates.IN_SELECT) {
			if (mouseX > 0 && mouseX < 512 && 
				mouseY > 0 && mouseY < 448) {
				if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
					
					GameState.currentTankPick = GameState.allTankPicks.TANK_LARGE;
				}
				DrawSelectBox(0 ,632);
			}
			
			if (mouseX > 1408 && mouseX < 1920 && 
				mouseY > 0 && mouseY < 448) {
				if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
					GameState.currentTankPick = GameState.allTankPicks.TANK_MEDIUM;
				}
				DrawSelectBox(1408 ,632);
			}
			
			if (mouseX > 701 && mouseX < 1213 && 
				mouseY > 632 && mouseY < 1080) {
				if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
					GameState.currentTankPick = GameState.allTankPicks.TANK_SMALL;
				}
				DrawSelectBox(1902/2-250 ,0);
			}
			
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) &&
			   (GameState.currentTankPick != GameState.allTankPicks.NO_SELECTION) &&
			    mouseX > 800 && mouseX < 1095 && 
			    mouseY > 340 && mouseY < 400){
				GameState.currentGameState = GameState.allGameStates.WAITING_FOR_SERVER;
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
		world.dispose();
		game.manager.dispose();
		GameState.dispose();
	}
}
