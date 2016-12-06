
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import java.awt.RenderingHints;

public class GameScreen extends ScreenAdapter {
    
    MuscleWorld world;

    int timer = 1;
//    float time;

    public GameScreen(MuscleWarGame muscleWarGame) {
        world = new MuscleWorld();
        
    }
    
    private void testAL(){
        if (Gdx.input.isKeyJustPressed(Keys.A)) {
            world.playerI.increasePowerBar();
            System.out.println("Player I: " + world.playerI.powerBar);
        }
        if (Gdx.input.isKeyJustPressed(Keys.L)) {
            world.playerII.increasePowerBar();
            System.out.println("Player II: " + world.playerII.powerBar);
        }
        
    }
    
    public void decreasePowerPerSec(){
        if (timer % 50 == 0) {
            world.playerI.deCreasePowerPerSec();
            world.playerII.deCreasePowerPerSec();
            System.out.println(world.playerI.powerBar);
        }
        timer++;
        timerReset();
    }
    
    private void whoWin(){
        if (world.playerI.releasePower) {
            System.out.println("Player I Win !!");
        } else if (world.playerII.releasePower) {
            System.out.println("Player II Win !!");
        }
    }
    
    public boolean releasePower(){
        if (world.playerI.releasePower) {
            return true;
        } else if (world.playerII.releasePower) {
            return true;
        }
        return false;
    }
    
    public void timerReset(){
        if(timer >= 10000){
            timer = 0;
        }
    }
    
    @Override
    public void render(float delta){
        
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!releasePower()) {  
            testAL();
            decreasePowerPerSec();
            whoWin();       
        }
    }

}
