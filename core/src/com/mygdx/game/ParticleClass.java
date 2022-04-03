package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParticleClass {

	private float X;
	private float Y;
	private float Angle;
	
	private float Timer;
	
	private Texture ParticleTexture;
	
	private int IndexCounter = 0;
	
	ArrayList<Texture> ParticleTextureList = new ArrayList<Texture>(); // Create an ArrayList object

	public ParticleClass(float X, float Y) {
		this.X = X;
		this.Y = Y;
	}
	
	public void AddTexture(Texture ParticleTexture, boolean Animated) {
		//=======================================================================
		//|Method			:	AddTexture(Texture ParticleTexture, boolean Animated)				   			
		//|																   		
		//|Method parameters:	(Texture ParticleTexture, boolean Animated)								   		
		//|																   		
		//|What it does		:	Adds a texture to the particle object, and has the 
		//|						option to add to a list of textures 
		//|						
		//|																	    
		//|Change log		:	Date		Creator    	Notes			    
		//|						===========	========   	=============	    
		//|						MAR 30 2022	J. Smith 	Initial setup   
		//=======================================================================
		if (Animated == true) {
			ParticleTextureList.add(ParticleTexture);
		}
		else {
			this.ParticleTexture = ParticleTexture;
		}
	}
	
	public void DrawTexture(SpriteBatch Batch, boolean Animated) {
		//=======================================================================
		//|Method			:	DrawTexture(SpriteBatch Batch, boolean Animated)			   			
		//|																   		
		//|Method parameters:	(SpriteBatch Batch, boolean Animated)							   		
		//|																   		
		//|What it does		:	Draws the textures, and based off of the Animated boolean,
		//|						will draw from a list of textures instead
		//|						
		//|																	    
		//|Change log		:	Date		Creator    	Notes			    
		//|						===========	========   	=============	    
		//|						MAR 30 2022	J. Smith 	Initial setup   
		//=======================================================================
		if (Animated == true) {
			Batch.draw(ParticleTextureList.get(IndexCounter), Y, X);
		}
		else {
			Batch.draw(ParticleTexture, Y, X);
		}
	}
	
	public void UpdateListIndex(float seconds, float MaxTime) {
		//=======================================================================
		//|Method			:	UpdateListIndex(float seconds, float MaxTime)		   			
		//|																   		
		//|Method parameters:	(float seconds, float MaxTime)							   		
		//|																   		
		//|What it does		:	Updates the IndexCounter for the ParticleTextureList
		//|						based off of a timer
		//|						
		//|																	    
		//|Change log		:	Date		Creator    	Notes			    
		//|						===========	========   	=============	    
		//|						MAR 30 2022	J. Smith 	Initial setup   
		//=======================================================================
		Timer += seconds;

		if (Timer > MaxTime) {
			Timer = 0;
			IndexCounter++;
			if (IndexCounter >  ParticleTextureList.size()-1) {
				IndexCounter = 0;
			}
		}
	}
}
