package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class GameScreen extends ScreenAdapter{
	private short cameraZoomValue = 0;
	private MyGdxGame game;
	private Cell usedLayerCell;
	//private PathFindingClass PathFindingInfo;
	private short animateRoverCounter = -1;
	
	private float timeInSeconds = 0f;
	
	private ArrayList<Short> cellArrayX;
	private ArrayList<Short> cellArrayY;
	
	//Constructor Method
	public GameScreen(MyGdxGame game) {
		this.game = game;
		usedLayerCell = new Cell();
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
		//|						Allows for camera movement
		//|						
		//|																	    
		//|Change log		:	Date		Creator    	Notes			    
		//|						===========	========   	=============	    
		//|						FEB 10 2022	J. Smith 	Initial setup   
		//=======================================================================
		/*
		timeInSeconds += Gdx.graphics.getDeltaTime();	//timeInSeconds gets added to it's current amount in delta time 
														//with every call of the render (60fps)
		
		if (timeInSeconds > 0.05f) {	//if timeInSeconds is greater than 1/20 of a second
			timeInSeconds -= 0.05f;	//Subtract 1/20 of a second of the timer
			animateRover();	//Call the animateRover function
		}
		*/
		Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.viewport.update(game.WIDTH, game.HEIGHT);
		game.camera.update();
		game.renderer.setView(game.camera);
		game.renderer.render();
		
		//CAMERA MOVE CONTROL
		if ((Gdx.input.isKeyPressed(Input.Keys.A))) {
			game.camera.position.x -= 32;
		};
		if ((Gdx.input.isKeyPressed(Input.Keys.D))) {
			game.camera.position.x += 32;
		};
		if ((Gdx.input.isKeyPressed(Input.Keys.W))) {
			game.camera.position.y += 32;
		};
		if ((Gdx.input.isKeyPressed(Input.Keys.S))) {
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
	
	public void hide() {
	}
	
	@Override
	public void dispose () {
		game.manager.dispose();
	}
}
