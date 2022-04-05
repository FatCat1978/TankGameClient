package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MyGdxGame extends Game {
	private tankAppClient theClient = new tankAppClient();
	SpriteBatch batch;
	Texture img;
	
	final static short WIDTH = 1920;
	final static short HEIGHT = 1080;
	
	public AssetManager manager;
	
	TiledMap selectedTiledMap;
	TiledMapTileSet tileSet;
	
	OrthogonalTiledMapRenderer renderer;
	OrthographicCamera camera;
	StretchViewport viewport;
	
	int tileWidth, tileHeight, 
	mapWidthInTiles, mapHeightInTiles,
	mapWidthInPixels, mapHeightInPixels;
	
	@Override
	public void create () {
		//=======================================================================
		//|Method			:	create()					   			
		//|																   		
		//|Method parameters:		  									   		
		//|																   		
		//|What it does		:	Creates the needed assets and classes at the launch of the program
		//|						
		//|																	    
		//|Change log		:	Date		Creator    	Notes			    
		//|						===========	========   	=============	    
		//|						FEB 10 2021	J. Smith 	This method is automatically called on startup
		//|												and is meant to place anything in that you want
		//|												used later
		//=======================================================================
		//Create an assets manager and assigns the map
		manager = new AssetManager();
		manager.setLoader(TiledMap.class, new TmxMapLoader());
		manager.load("Tank_Bullet_V2.png", Texture.class);
		manager.load("TankBattlefield1.tmx", TiledMap.class);
		manager.load("RT-76_Body.png", Texture.class);
		manager.load("RT-76_Turret_Head.png", Texture.class);
		manager.load("MT-1984_Select_Card.png", Texture.class);
		manager.load("AT82_Select_Card.png", Texture.class);
		manager.load("RT-76_Select_Card.png", Texture.class);
		manager.load("Card_Selected.png", Texture.class);
		manager.load("AT82_Body.png", Texture.class);
		manager.load("AT82_Turret_Head.png", Texture.class);
		manager.load("MT-1984_Body.png", Texture.class);
		manager.load("MT-1984_Turret_Head.png", Texture.class);
		manager.load("Crate_Health.png", Texture.class);
		manager.load("Crate_Sniper.png", Texture.class);
		manager.load("Crate_Para.png", Texture.class);
		manager.finishLoading();
		selectedTiledMap = manager.get("TankBattlefield1.tmx", TiledMap.class);
		
		
		//Gets the properties of the TiledMap
		MapProperties properties = selectedTiledMap.getProperties();
		tileWidth = properties.get("tilewidth", Integer.class);
		tileHeight = properties.get("tileheight", Integer.class);
		mapWidthInTiles = properties.get("width", Integer.class);
		mapHeightInTiles = properties.get("height", Integer.class);
		mapWidthInPixels = tileWidth * mapWidthInTiles;
		mapHeightInPixels = tileHeight * mapHeightInTiles;
		
		//Creates The Camera
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		viewport = new StretchViewport (WIDTH, HEIGHT, camera);
		viewport.apply();
		camera.zoom = 0.1f;
		camera.position.set(WIDTH/2, HEIGHT/2, 0);
		camera.update();
		
		tileSet = selectedTiledMap.getTileSets().getTileSet(0);
		
		renderer = new OrthogonalTiledMapRenderer(selectedTiledMap);
		
		this.setScreen(new GameScreen(this));

	}
	
	public tankAppClient getClient() {
		return theClient;
	}

	@Override
	public void dispose () {
		manager.dispose();
	}
}
