package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;

public class Box2DWorldCreator 
{
    public Box2DDebugRenderer b2dr = new Box2DDebugRenderer();
    
    BodyDef bdef = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fixtDef = new FixtureDef();
    Body body;
    
    public Box2DWorldCreator(World world, TiledMap map)
    {
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
}
