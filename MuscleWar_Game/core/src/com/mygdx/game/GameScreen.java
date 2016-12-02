
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import java.awt.RenderingHints;

public class GameScreen extends ScreenAdapter {
    
    MuscleWorld world;

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
    
    private void whoWin(){
        if (world.playerI.releasePower) {
            System.out.println("Player I Win !!");
        } else if (world.playerII.releasePower) {
            System.out.println("Player II Win !!");
        }
    }
    
    private boolean releasePower(){
        if (world.playerI.releasePower) {
            return false;
        } else if (world.playerII.releasePower) {
            return false;
        }
        return true;
    }
    
    @Override
    public void render(float delta){
        
        Gdx.gl.glClearColor(5, 5, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (releasePower()) {  
            testAL();
        }
        whoWin();
        
    }

}
