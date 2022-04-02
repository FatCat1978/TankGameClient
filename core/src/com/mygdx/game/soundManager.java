
package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;



public class soundManager {

    // This is where we create all the sound effects used for the game
    private Sound tankShoots;
    private Sound tankGetsHit;
    private Sound tankMoves;
    private Sound turretRotates;
    private Sound powerupSpawned;
    private Sound powerupCollected;
    private Sound tankDestroyed;
    private Sound buildingHit;
    private Sound buildingDestroyed;
    private Sound lobbyTransition;

    
    public void setTheSounds()
    {
    	// Once there are sounds, import them into the assets and then put the correct file names below
        tankShoots = Gdx.audio.newSound(Gdx.files.internal("gunShot.mp3"));
        tankGetsHit = Gdx.audio.newSound(Gdx.files.internal("tankHit.wav"));
        tankMoves = Gdx.audio.newSound(Gdx.files.internal("engine.mp3"));
        turretRotates = Gdx.audio.newSound(Gdx.files.internal("turretRotate.mp3"));
        powerupSpawned = Gdx.audio.newSound(Gdx.files.internal("collectPower.mp3"));
        powerupCollected = Gdx.audio.newSound(Gdx.files.internal("collectPower.mp3"));
        tankDestroyed = Gdx.audio.newSound(Gdx.files.internal("tankHit.wav"));
        buildingHit = Gdx.audio.newSound(Gdx.files.internal("buildingHit.wav"));
        buildingDestroyed = Gdx.audio.newSound(Gdx.files.internal("buildingHit.wav"));
        lobbyTransition = Gdx.audio.newSound(Gdx.files.internal("bassTransition.mp3"));

        // Needed for the looping sounds
        turretRotates.play(0.8f);// put the volume here. 0 = mute, 1 = full
        turretRotates.loop();
        turretRotates.pause();
        
        tankMoves.play(0.8f);// put the volume here. 0 = mute, 1 = full
        tankMoves.loop();
        tankMoves.pause();
    }
    
    public void playTankShot()
    {
        // Call this method to play the tank shot
        tankShoots.play(0.8f);	// put the volume here. 0 = mute, 1 = full
    }
    
    public void playTankHit()
    {
        // Call this method to play the tank shot
    	tankGetsHit.play(0.8f);	// put the volume here. 0 = mute, 1 = full
    }
    
    public void playTankMoves(boolean doLoop)
    {
        // This should play the turret rotating sound on loop if doLoop is true
        if (doLoop)
        {
            turretRotates.resume();
        }
        
        // When doLoop is false it stops playing the sound
        if(!doLoop)
        {
            turretRotates.pause();
        }
    
    }
    
    public void playTurretRotates(boolean doLoop)
    {
        // This should play the turret rotating sound on loop if doLoop is true
        if (doLoop)
        {
            turretRotates.resume();
        }
        
        // When doLoop is false it stops playing the sound
        if(!doLoop)
        {
            turretRotates.pause();
        }
    }
    
    public void playPowerupSpawn()
    {
        // Call this method to play the tank shot
    	powerupSpawned.play(0.8f);	// put the volume here. 0 = mute, 1 = full
    }
    
    public void playPowerupGot()
    {
        // Call this method to play the tank shot
    	powerupCollected.play(0.8f);	// put the volume here. 0 = mute, 1 = full
    }
    
    public void playTankDestroyed()
    {
        // Call this method to play the tank shot
    	tankDestroyed.play(0.8f);	// put the volume here. 0 = mute, 1 = full
    }
    
    public void playBuildingHit()
    {
        // Call this method to play the tank shot
    	buildingHit.play(0.8f);	// put the volume here. 0 = mute, 1 = full
    }
    
    public void playBuildingDestroyed()
    {
        // Call this method to play the tank shot
    	buildingDestroyed.play(0.8f);	// put the volume here. 0 = mute, 1 = full
    }
    
    public void playTransitionNoise()
    {
        // Call this method to play the tank shot
    	lobbyTransition.play(0.8f);	// put the volume here. 0 = mute, 1 = full
    }
    
    public void dispose()
    {
    	// Dispose of all the audio
        tankShoots.dispose();
        tankGetsHit.dispose();
        tankMoves.dispose();
        turretRotates.dispose();
        powerupSpawned.dispose();
        powerupCollected.dispose();
        tankDestroyed.dispose();
        buildingHit.dispose();
        buildingDestroyed.dispose();
        lobbyTransition.dispose();
    	
    }

}
