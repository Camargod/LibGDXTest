package com.mygdx.game.Scenes;

import java.time.Instant;
import java.util.Date;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.game.MyGdxGame;

public class Hud 
{
	public Stage stage;
	private Viewport viewport;
	
	private Date worldTimer;
	
	private Label timeLabel;

	public Hud(SpriteBatch sb)
	{
		worldTimer = Date.from(Instant.now());
		
		viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport,sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		timeLabel = new Label(worldTimer.toString(), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		
		table.add(timeLabel).expandX().padTop(10).right();
		table.row();
		
		stage.addActor(table);
	}
	public void update(float dt)
	{
		worldTimer = Date.from(Instant.now());
		timeLabel.setText(worldTimer.toString());
	}
}
