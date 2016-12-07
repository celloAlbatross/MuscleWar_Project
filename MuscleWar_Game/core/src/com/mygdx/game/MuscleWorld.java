
package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MuscleWorld {
    Player playerI;
    Player playerII;
    
    SpriteBatch batch;
    
    private final float positionPlayerI = 250;
    private final float positionPlayerII = 1030;

    public MuscleWorld(SpriteBatch batch) {
    	this.batch = batch;
        playerI = new Player(batch, positionPlayerI);
        playerII = new Player(batch, positionPlayerII);
    }
    
    public void update (float delta) {
    	
    }
    
    
}
