package com.mygdx.game.Screens;

import java.awt.event.KeyEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.objects.Player;

public class MainMenuScreen implements Screen
{
	private MyGdxGame game;
	
	private Viewport gameViewPort;
	private Hud hud;
	private OrthographicCamera gameCamera;
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	private Player player;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	public MainMenuScreen(MyGdxGame game)
	{
		this.game = game;
		world = new World(new Vector2(0,0), true);
		maploader = new TmxMapLoader();
		this.player = new Player("Gabriel", this);
		gameCamera = new OrthographicCamera();
		gameViewPort = new FitViewport((MyGdxGame.V_WIDTH/2.8f)/MyGdxGame.PPM,(MyGdxGame.V_HEIGHT/2.8f)/MyGdxGame.PPM,gameCamera);
		hud = new Hud(game.batch);
		
		maploader = new TmxMapLoader();
		map = maploader.load("map002.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);
		
		gameCamera.position.set(gameViewPort.getWorldWidth()/2, gameViewPort.getWorldWidth()/4,0);
		
		b2dr = new Box2DDebugRenderer();
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtDef = new FixtureDef();
		Body body;
		
		//creates wall bodies/fixtures 
		for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() /2)/ MyGdxGame.PPM, (rect.getY() + rect.getHeight()/2)/MyGdxGame.PPM);
			
			body = world.createBody(bdef);
			shape.setAsBox((rect.getWidth()/2)/MyGdxGame.PPM, (rect.getHeight()/2)/MyGdxGame.PPM);
			
			fixtDef.shape = shape;
			body.createFixture(fixtDef);
		}
	}
	@Override
	public void show() 
	{
		// TODO Auto-generated method stub
	}
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
		player.update(game.batch,game.elapsedTime);
		renderer.setView(gameCamera);
	}

	@Override
	public void render(float delta) 
	{
		game.elapsedTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();
		b2dr.render(world, gameCamera.combined);
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		update(delta);
	}

	@Override
	public void resize(int width, int height) {
		gameViewPort.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stu
	}
	public World getWorld()
	{
		return world;
	}
	
}
