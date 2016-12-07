
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MuscleWorld {
    Player playerI;
    Player playerII;
    
    Texture muscleI = new Texture("muscle1.png");
    Texture muscleII = new Texture("muscle2.png");
 
    
    SpriteBatch batch;
    
    public float positionPlayerI = -70;
    public float positionPlayerII = GameScreen.WIDTH - 150;

    public MuscleWorld(SpriteBatch batch) {
    	this.batch = batch;
        playerI = new Player(batch, positionPlayerI, muscleI);
        playerII = new Player(batch, positionPlayerII, muscleII);
    }
    
    public void update (float delta) {
    	
    }
    
    
}
