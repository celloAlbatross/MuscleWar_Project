
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
    
    private final int UPSIZE = 7;
    
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
    Texture playerI = new Texture("player1win.png");
    Texture playerII = new Texture("player2win.png");
 
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
        	world.playerI.setPosition += ratio;
        	world.playerI.upSize += UPSIZE;
        	
        }
        
        if (world.playerII.getIsRaise()){
        	powerII -= ratio;
        	world.playerII.setPosition -= ratio;
        	world.playerII.upSize += UPSIZE;
        }
        
        System.out.println(world.playerI.getPower());
        System.out.println(world.playerII.getPower());
    }
    
    public void decreasePowerPerSec(){
        if (timer >= 2) {
            world.playerI.deCreasePowerPerSec();
            world.playerII.deCreasePowerPerSec();
            if (powerI > -BAR_LENGHT) {
            	powerI -= ratio;
            	world.playerI.setPosition -= ratio;
            	world.playerI.upSize -= UPSIZE;
            }
            	
            if (powerII < BAR_LENGHT*2) {
            	powerII += ratio;
            	world.playerII.setPosition += ratio;
            	world.playerII.upSize -= UPSIZE;
            }
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
            
            powerBarI.draw(powerI);
            powerBarII.draw(powerII);
            world.playerI.draw();
            world.playerII.draw();
            decreasePowerPerSec();
            whoWin();       
        } else {
//            powerBarI.draw(powerI);
//            powerBarII.draw(powerII);
            if (world.playerI.releasePower) {
                batch.draw(playerI, WIDTH/2 - 320, 35);
                powerBarI.drawWin();
            } else if (world.playerII.releasePower) {
            	batch.draw(playerII, WIDTH/2 - 320, 35);
            	powerBarII.drawWin();
            }
        }
        batch.end();
    }
    
    

}
