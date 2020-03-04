package com.mygdx.game.objects;


import java.io.Console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.MainMenuScreen;

public class Player extends Sprite
{
	public World world;
	public Body b2Body;
	private String name = "Default";
	private float X = 32f;
	private float Y = 32f;
	private float pastX = 0f;
	private float pastY = 0f;
	private Texture playerSprite;
	private Array<TextureRegion> sprites;
	public Animation<TextureRegion> playerAnim;
	private boolean isRunning = false;
	private int health = 25;
	private float walkSpeed = 4.25f;
	private float runSpeed = 5.25f;
	private float speedMulti = 1f;
	private float maxSpeed = 1f;
	
	public Player(String name, MainMenuScreen screen)
	{
		super();
		this.name = name;
		this.world = screen.getWorld();

		playerSprite = new Texture(Gdx.files.internal("player/knight.png"));

		sprites = new Array<TextureRegion>();

		//tamanho do sprite 34/82
		for(int i = 0; i <=3; i++)
		{
			sprites.add(new TextureRegion(playerSprite,24 +((34 * i) + (50 * i)),0,34,82));
		}
		playerAnim = new Animation(0.1f,sprites);

		definePlayer();
	}
	
	public void definePlayer()
	{
		BodyDef bdef = new BodyDef();
		bdef.position.set(X/MyGdxGame.PPM,Y/MyGdxGame.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.linearDamping = 5f;
		b2Body = world.createBody(bdef);
		FixtureDef fdef = new FixtureDef();
		fdef.friction = 1f;
		CircleShape shape = new CircleShape();
		shape.setRadius(5/MyGdxGame.PPM);
		
		fdef.shape = shape;
		b2Body.createFixture(fdef).setUserData(this);
	}
	
	/**
	 * @param direction 
	 * The keyCode pressed to validate the direction of the character 
	 */
	public void Move(int direction)
	{
		switch(direction)
		{
			case Input.Keys.D:
			{
				if(b2Body.getLinearVelocity().x <= maxSpeed)
				{

					b2Body.applyLinearImpulse(new Vector2((walkSpeed * speedMulti * (isRunning ? runSpeed : 1))/MyGdxGame.PPM,0), b2Body.getWorldCenter(), true);
				}
				break;
			}
			case Input.Keys.A:
			{
				if(b2Body.getLinearVelocity().x >= -maxSpeed)
				{
					b2Body.applyLinearImpulse(new Vector2(-(walkSpeed * speedMulti * (isRunning ? runSpeed : 1))/MyGdxGame.PPM,0), b2Body.getWorldCenter(), true);
				}
				break;
			}
			case Input.Keys.W:
			{
				if(b2Body.getLinearVelocity().y <= maxSpeed)
				{
					b2Body.applyLinearImpulse(new Vector2(0,(walkSpeed * speedMulti * (isRunning ? runSpeed : 1))/MyGdxGame.PPM), b2Body.getWorldCenter(), true);
				}
				break;
			}
			case Input.Keys.S:
			{
				if(b2Body.getLinearVelocity().y >= -maxSpeed)
				{
					pastY = Y;
					Y -= (walkSpeed * speedMulti * (isRunning ? runSpeed : 1))/MyGdxGame.PPM;
					b2Body.applyLinearImpulse(new Vector2(0,-(walkSpeed * speedMulti * (isRunning ? runSpeed : 1))/MyGdxGame.PPM), b2Body.getWorldCenter(), true);
				}
				break;
			}
			default:
		}
	}
	
	public float getX()
	{
		return X;
	}
	
	public float getY()
	{
		return Y;
	}
	
	public void setRunningState(boolean param)
	{
		isRunning = param;
	}

	public void update(SpriteBatch batch, float elapsedTime)
	{
		X = b2Body.getPosition().x;
		Y = b2Body.getPosition().y;
		batch.begin();
		batch.draw(playerAnim.getKeyFrame(elapsedTime,true), MyGdxGame.V_WIDTH/2 - 17, MyGdxGame.V_HEIGHT/2 - 10);
		batch.end();
	}
	
}
