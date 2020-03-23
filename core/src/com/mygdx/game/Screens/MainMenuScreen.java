package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.InputProcessor.InputCore;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Tools.Box2DWorldCreator;
import com.mygdx.game.Tools.SoundPlayer;
import com.mygdx.game.objects.Player;

public class MainMenuScreen implements Screen 
{
	private InputProcessor InputCore;

	private MyGdxGame game;
	
	private Viewport gameViewPort;
	private Hud hud;
	public OrthographicCamera gameCamera;
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	private Player player;
	
	private World world;

	private Box2DWorldCreator worldColisions; 

	public MainMenuScreen(MyGdxGame game)
	{
		this.game = game;
		world = new World(new Vector2(0,0), true);

		maploader = new TmxMapLoader();
		this.player = new Player("Gabriel", this, game);
		gameCamera = new OrthographicCamera();
		gameViewPort = new FitViewport((MyGdxGame.V_WIDTH/2.8f)/MyGdxGame.PPM,(MyGdxGame.V_HEIGHT/2.8f)/MyGdxGame.PPM,gameCamera);
		hud = new Hud(game.batch);

		maploader = new TmxMapLoader();
		map = maploader.load("map002.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);
		SoundPlayer.rain();

		worldColisions = new Box2DWorldCreator(world,map);

		gameCamera.position.set(gameViewPort.getWorldWidth()/2, gameViewPort.getWorldWidth()/4,0);
		
		InputCore = new InputCore(gameCamera);
		Gdx.input.setInputProcessor(InputCore);
	}
	@Override
	public void show() {}

	public void handleInput(float dt)
	{
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			player.Move(Input.Keys.D);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			player.Move(Input.Keys.A);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			player.Move(Input.Keys.W);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			player.Move(Input.Keys.S);
		}
		if(!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D))
		{
			player.Move(0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
		{
			player.setRunningState(true);
		}
		if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
		{
			player.setRunningState(false);
		}
	}
	
	public void update(float dt)
	{
		handleInput(dt);
		world.step(1/60f, 6, 2);
		gameCamera.position.x = player.b2Body.getPosition().x;
		gameCamera.position.y = player.b2Body.getPosition().y;
		gameCamera.update();
		hud.update(dt);
		renderer.setView(gameCamera);
	}

	@Override
	public void render(float delta) 
	{
		game.elapsedTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		renderer.render();
		worldColisions.b2dr.render(world, gameCamera.combined);
		player.draw(game.batch,game.elapsedTime);
		hud.stage.draw();
		update(delta);
	}

	@Override
	public void resize(int width, int height) 
	{
		gameViewPort.update(width, height);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

	public World getWorld()
	{
		return world;
	}
	
}
