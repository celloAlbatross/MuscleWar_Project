
package com.mygdx.game;


public class Player {
    
    private final int MAX_POWER = 40;
    private final int INCREASE_POWERBAR = 1;
    
    boolean isRaise;
    boolean releasePower;
    int powerBar = 0;

    public Player() {
        isRaise = true;
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
}
