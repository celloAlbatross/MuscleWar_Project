package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gnu.io.CommPort;

public class MuscleWarGame extends Game {
	SpriteBatch batch;
	GameScreen gameScreen;
	Serial serial;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		try {
			serial = new Serial("/dev/ttyUSB0");
			gameScreen = new GameScreen(this, serial);
			setScreen(gameScreen);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render () {
            super.render();

            try {
				restart();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public void restart () throws Exception {
		if (getScreen() == gameScreen) {
			if (gameScreen.getReset()) {
				System.out.println("yoo");
				gameScreen.dispose();
				gameScreen = new GameScreen(this, serial);
				setScreen(gameScreen);
			}
		}
	}
}
