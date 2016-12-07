
package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    
    private final float MAX_POWER = 20;
    private final int INCREASE_POWERBAR = 1;
    
    private float ratio;
    private int currentState;
    private int previousState;
    
    boolean isRaise;
    boolean releasePower;
    int powerBar = 0;
    float setPosition;
    SpriteBatch batch;
    
    public static int DOWN_STATE = 1;
    public static int UP_STATE = 2;

    public Player(SpriteBatch batch,float x) {
        
    	isRaise = true;
    	setPosition = x;
    	this.batch = batch;
    	
    	currentState = DOWN_STATE;
    	previousState = DOWN_STATE;
    }
    
    
    public void increasePowerBar(){
        if (powerBar < MAX_POWER) {
            if (isRaise) {
                powerBar += INCREASE_POWERBAR;
                isReleasePower();
            }
        }
    }
    
    public void isReleasePower(){
        if (powerBar >= MAX_POWER){
            releasePower = true;
        }
    }
    
    public void deCreasePowerPerSec(){
        if (powerBar > 0) {
            powerBar -= 1;
        }
    }
    
    public void setPower(double power) {
    	
    	if (power > 1) {
    		currentState = UP_STATE;
    		previousState = DOWN_STATE;
    	} else {
    		currentState = DOWN_STATE;
    		
    		if (previousState != currentState) {
    			powerBar += INCREASE_POWERBAR;
    			previousState = DOWN_STATE;
    		}
    	}
    	
    	isReleasePower();
    }
    
    public float getMaxPower() {
        
        return MAX_POWER;
    }
    
    public double getPower() {
    	return powerBar;
    }
    
    public void draw() {
    	
    }
    
    
}
