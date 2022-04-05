package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();


        if(fixA == null || fixB == null) return;
        if(fixA.getUserData() == null || fixB.getUserData() == null) return;

        System.out.println("A collision happened");          
        
    }

    @Override
    public void endContact(Contact contact) {
    	   Fixture fixA = contact.getFixtureA();
           Fixture fixB = contact.getFixtureB();


           if(fixA == null || fixB == null) return;
           if(fixA.getUserData() == null || fixB.getUserData() == null) return;

           System.out.println("A collision stopped"); 
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}