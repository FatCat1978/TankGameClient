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
	//TurretObject theTurret;
	private short cameraZoomValue = 0;
	private MyGdxGame game;
	private short tankTypeID = 1; //variable responsible for selecting the type of tank the player wishes to use
	private short tankControlID = 0; //variable that will be used to dictate how the player controls the tank
	
	private World world;
	
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
		
		world = new World(new Vector2(0,0), true);
		
		theTank = new Tank(game, (short) tankTypeID, (short) tankControlID);
		
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
	public void resize(int width, int height) {
        game.viewport.update(width, height);
        game.camera.update();
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
		
		batch.begin();
		if (InLobby == true) {
			
			Gdx.gl.glClearColor(50/255f, 70/255f, 90/255f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			//game.viewport.update(game.WIDTH, game.HEIGHT);
			batch.draw(game.manager.get("Main_Menu_Screen.png", Texture.class), 0 , 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
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
			
			theTank.TankAnimationTimer(Gdx.graphics.getDeltaTime(),0.25f);
			
			//batch.draw(theTank.getTankBodyTexture(), theTank.getTankPositionY(), theTank.getTankPositionX());
					
			
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
			
			
			handlePlayerMovement(delta);
			Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			game.viewport.update(game.WIDTH, game.HEIGHT);
			game.camera.update();
			game.renderer.setView(game.camera);
			game.renderer.render();
					
			theTank.draw(batch);
			
			
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
		//=======================================================================
		//playerInfo MOVEMENT CONTROL
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			theTank.turnTankLeft(true);
			theTank.DrawParticle = true;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			theTank.turnTankRight(true);
			theTank.DrawParticle = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			theTank.moveTankForward(true);
			theTank.DrawParticle = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			theTank.moveTankBackward(true);
			theTank.DrawParticle = true;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			theTank.turnTurretLeft(true);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			theTank.turnTurretRight(true);
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
		world.dispose();
		game.manager.dispose();
	}
}
