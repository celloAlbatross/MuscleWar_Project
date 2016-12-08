
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PowerBar {
    Texture bar;
    SpriteBatch batch;

    public PowerBar(SpriteBatch batch,Texture bar) {
        this.batch = batch;
        this.bar = bar;
    }
    
    
    public void draw(double x){

        batch.draw(bar, (float) x, 20, 640, 30);
//        batch.draw(redBar, 20, 20);

    }
    
    public void drawWin(){
    	batch.draw( bar, 0, 20, 1280, 30);
    }
}
