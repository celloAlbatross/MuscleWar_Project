
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.RenderingHints;

public class GameScreen extends ScreenAdapter {
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int BAR_LENGHT = WIDTH/2;
    
    float maxPower;
    float ratio;
    
    MuscleWorld world;
    PowerBar powerBarI;
    PowerBar powerBarII;
    Serial serial;
    
    
    
    SpriteBatch batch;
    Texture backG = new Texture("gym.jpg");
    Texture barI = new Texture("red.png");
    Texture barII = new Texture("blue.jpg");
    
    float timer = 0;
    float powerI = -BAR_LENGHT;
    float powerII = BAR_LENGHT*2;


    public GameScreen(MuscleWarGame muscleWarGame) throws Exception {
        
        batch = new SpriteBatch();
        world = new MuscleWorld(batch);
        serial = new Serial("/dev/ttyUSB0");
        powerBarI = new PowerBar(batch,barI);
        powerBarII = new PowerBar(batch, barII);
        
        Thread t = new Thread(serial);
        t.setDaemon(true);
        t.start();
        
        maxPower = world.playerI.getMaxPower();
        ratio = BAR_LENGHT/maxPower;
    }
    
    private void powerUp(){
        world.playerI.setPower(serial.getValue());
        world.playerII.setPower(serial.getValue2());
        
        if (world.playerI.getIsRaise()) {
        	powerI += ratio;
        }
        
        if (world.playerII.getIsRaise()){
        	powerII -= ratio;
        }
        
        System.out.println(world.playerI.getPower());
        System.out.println(world.playerII.getPower());
    }
    
    public void decreasePowerPerSec(){
        if (timer >= 2) {
            world.playerI.deCreasePowerPerSec();
            world.playerII.deCreasePowerPerSec();
            if (powerI > -BAR_LENGHT)
                powerI -= ratio;
            if (powerII < BAR_LENGHT*2)
                powerII += ratio;
            timer = 0;

        }

        timer += Gdx.graphics.getDeltaTime();
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

        //System.out.println(serial.getValue());
        //System.out.println(serial.getValue2());
        
        
        batch.begin();
        batch.draw(backG, 0, 0);
        if (!releasePower()) {  
        	System.out.println("-----------");
            System.out.println(serial.getValue() + " | " + serial.getValue2());
            powerUp();
            
            powerBarI.Draw(powerI);
            powerBarII.Draw(powerII);
            decreasePowerPerSec();
            whoWin();       
        } else {
            powerBarI.Draw(powerI);
            powerBarII.Draw(powerII);
        }
        batch.end();
    }
    
    

}
