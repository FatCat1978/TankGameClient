package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;



// Within the tank have a shot management. That is where an arraylist of bullets can be saved
// Delete the bullet in the arraylist when it collides OR goes beyond bullet life

public class BulletObject {
    
    private Texture bulletTexture;            // Instantiate the texture
    
    private float bulletLife = 5f;            // Instantiate the bulletLife float variable
    private static float speed = 500.f;
    
    private MyGdxGame game;
    
    public BulletObject(MyGdxGame game) {
        /*
         * Constructor Name:                bulletObject
         *     
         * Constructor Parameters:            MyGdxGame game, Vector2 position, Vector2 direction
         * 
         * Synopsis:                        This constructor serves as the template
         *                                     from which bullets will be created
         * 
         * Modifications:                    Date:        Name:            Modifications:
         *                                     03/31/22    Quentin G.        Initial Setup
         */
        
        this.game = game; //ensures that all data from the class MyGdxGame is identical to the data of the same type here
        bulletTexture = game.manager.get("Tank_Bullet.png", Texture.class);
        
    }
    
    public float getBulletSpeed() {
    	return speed;
    }

    public Texture getBulletTexture() {
    	/*
    	 * 
    	 * Jared Shaddick
    	 */
    	return bulletTexture;
    }

    
    // This would be a method in the tank
    public void bulletShot()
    {
        //build a vector from the ship angle
        
    }
    
}