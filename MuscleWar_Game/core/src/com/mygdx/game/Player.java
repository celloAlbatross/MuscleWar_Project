
package com.mygdx.game;


public class Player {
    
    private final int MAX_POWER = 20;
    
    boolean isRaise;
    boolean releasePower;
    int powerBar = 0;

    public Player() {
        isRaise = true;
    }
    
    
    
    
    
    
    public void increasePowerBar(){
        if (powerBar < MAX_POWER) {
            if (isRaise) {
                powerBar++;
            }
        } else {
            releasePower = true;
        }
    }
}
