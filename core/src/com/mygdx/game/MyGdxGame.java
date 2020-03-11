package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.MainMenuScreen;

public class MyGdxGame extends Game 
{	
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 480;
	//Pixeis por metro :)
	public static final float PPM = 100;
	public float elapsedTime;
	
	public SpriteBatch batch;
	
	@Override
	public void create () 
	{
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () 
	{
		super.render();
	}
	
	@Override
	public void dispose () 
	{
		batch.dispose();
	}
}
