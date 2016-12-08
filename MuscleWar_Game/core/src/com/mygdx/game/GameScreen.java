
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gnu.io.CommPort;

import java.awt.RenderingHints;

public class GameScreen extends ScreenAdapter {
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int BAR_LENGHT = WIDTH/2;
    
    private final int UPSIZE = 7;
    
    float maxPower;
    float ratio;
    
    private MuscleWorld world;
    private PowerBar powerBarI;
    private PowerBar powerBarII;
    private Serial serial;
    private SpriteBatch batch;
    private double endPower1;
    private double endPower2;
    
    private float timer = 0;
    private float powerI = -BAR_LENGHT;
    private float powerII = BAR_LENGHT*2;
    
    private int state;
    private boolean resetGame;
    
    Texture backG = new Texture("gym.jpg");
    Texture barI = new Texture("red.png");
    Texture barII = new Texture("blue.jpg");
    Texture playerI = new Texture("player1win.png");
    Texture playerII = new Texture("player2win.png");
    
    Sound bgMusic;
    Sound soundPlayerI;
    Sound soundPlayerII;
    Sound win;


    public static int GAME_RUNNING = 1;
    public static int GAME_END = 2;
    
    public GameScreen(MuscleWarGame muscleWarGame, Serial serial) throws Exception {
        
        batch = new SpriteBatch();
        world = new MuscleWorld(batch);
        this.serial = serial;
        powerBarI = new PowerBar(batch,barI);
        powerBarII = new PowerBar(batch, barII);
        bgMusic = Gdx.audio.newSound(Gdx.files.internal("bg.mp3"));
        soundPlayerI = Gdx.audio.newSound(Gdx.files.internal("voice.mp3"));
        soundPlayerII = Gdx.audio.newSound(Gdx.files.internal("voice2.mp3"));
        win = Gdx.audio.newSound(Gdx.files.internal("win.mp3"));
        bgMusic.setVolume((long)1.0f, 500);
        bgMusic.play();
        
        state = GAME_RUNNING;
        resetGame = false;
        
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
        	if(!releasePower()) {
        		soundPlayerI.play();
        	}
        	
        }
        
        if (world.playerII.getIsRaise()){
        	powerII -= ratio;
        	world.playerII.setPosition -= ratio;
        	world.playerII.upSize += UPSIZE;
        	if(!releasePower()) {
        		soundPlayerII.play();
        	}
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
            bgMusic.stop();
            win.setVolume((long)1.0f, 100000);
            win.play();
        } else if (world.playerII.releasePower) {
            System.out.println("Player II Win !!");
            bgMusic.stop();
            win.setVolume((long)1.0f, 100000);
            win.play();
        }
        
    	endPower1 = world.playerI.getPower();
    	endPower2 = world.playerII.getPower();
    }
    
    public boolean releasePower() {
        if (world.playerI.releasePower) {
            return true;
        } else if (world.playerII.releasePower) {
            return true;
        }
        return false;
    }
    
    public void restart() {
    	if (state == GAME_END) {
    		powerUp();
    		
    		System.out.println(endPower1 + " | " + endPower2);
    		
    		if (world.playerI.getPower() - endPower1 >= 5 && world.playerII.getPower() - endPower2 >= 5) {
    			resetGame = true;
    		}
    	}
    }
    
    public void timerReset() {
        if(timer >= 10000){
            timer = 0;
        }

    }
    
    public boolean getReset() {
    	return resetGame;
    }
    
    @Override
    public void render(float delta){
        
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
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
        	
            if (world.playerI.releasePower) {
                batch.draw(playerI, WIDTH/2 - 320, 35);
                powerBarI.drawWin();
            } else if (world.playerII.releasePower) {
            	batch.draw(playerII, WIDTH/2 - 320, 35);
            	powerBarII.drawWin();
            }
            
            state = GAME_END;
        }
        restart();
        batch.end();
    }
    
    

}
