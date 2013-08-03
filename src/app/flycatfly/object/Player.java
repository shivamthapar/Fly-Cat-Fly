package app.flycatfly.object;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import app.flycatfly.manager.ResourcesManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public abstract class Player extends AnimatedSprite
{
	// ---------------------------------------------
	// CONSTANTS
	// ---------------------------------------------
	
	public final double PIXEL_DISTANCE_MULTIPLIER = 0.1;
	public final double DISTANCE_MONEY_MULTIPLIER = 0.6;
	public final float RESISTANCE_MULTIPLIER = 0.6f;
	public final int INIT_VELOCITY_EXTRA = 7;
	
	
	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------
	
	private Body body;
	
	private SharedPreferences sharedPref;
	
	public int money;
	
	private boolean canRun = true;
	public boolean onRamp = true;
	public boolean onGround = false;
	
	private int footContacts = 0;
	
	public float speed = 7;
	
	public double distance = 0.000;
	
	public double maxAltitude = 0.000;
	public float maxSpeed = 0;
	
	private float initX, initY;
	
	public int rampHeight = 1; //1-10
	public int initVelocity = 1; //1-10
	public int resistance = 1; //1-10
	
	public PhysicsWorld physicsWorld;
	
	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------
	
	public Player(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld pWorld, Activity activity)
	{
		super(pX, pY, ResourcesManager.getInstance().player_region, vbo);
		initX=pX;
		initY=pY;
		sharedPref=activity.getPreferences(Context.MODE_PRIVATE);
		money = sharedPref.getInt("money", 0);
		physicsWorld=pWorld;
		createPhysics(camera);
		setRunning();
		camera.setChaseEntity(this);
	}
	
	// ---------------------------------------------
	// CLASS LOGIC
	// ---------------------------------------------
	
	private void createPhysics(final Camera camera)
	{		
		body = PhysicsFactory.createCircleBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
		body.setUserData("player");
		body.setLinearVelocity(initVelocity+INIT_VELOCITY_EXTRA, -12);
		body.setLinearDamping(resistance*RESISTANCE_MULTIPLIER);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, true)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				if (canRun)
				{
					double currentAltitude = getY()*PIXEL_DISTANCE_MULTIPLIER;
					if(currentAltitude>maxAltitude)
					{
						maxAltitude=currentAltitude;
					}
					
					speed=(float) Math.sqrt(Math.pow(body.getLinearVelocity().x, 2)+Math.pow(body.getLinearVelocity().y, 2));
					
					if(speed>maxSpeed){
						maxSpeed=speed;
					}
					if(speed<0.3&&speed>0){
						speed=0;
						stopAnimation(); //stopping the animation once the character is almost stopped
					}
					if (getY() <= 0)
					{					
						onDie();
					}
					calculateDistance();
					Log.d("mine", "xVel:"+body.getLinearVelocity().x+" yVel: "+body.getLinearVelocity().y);
					Log.d("mine", "speed:"+speed+" onGround:"+onGround);
				}
				if(speed==0&&onGround){
					Log.d("mine","speed == 0 called!!!!");
					speed=0;
					onDie();
				}
	        }
		});
	}
	
	public void setRunning()
	{
		canRun = true;
		
		final long[] PLAYER_ANIMATE = new long[] { 100, 100, 100 };
		
		animate(PLAYER_ANIMATE, 0, 2, true);
	}
	
	public void fly(float xDiff, float yDiff)
	{
		float angle = (float) Math.atan2(yDiff,xDiff);
		if(speed<0.3)
			return;
		if(Math.abs(angle)>Math.PI/2)
			return;
		float xVel = (float) (speed*Math.cos(angle));
		float yVel = (float) (speed*Math.sin(angle));
		body.setLinearVelocity(xVel,yVel);
		body.setTransform(body.getPosition(),angle);
	}
	
	public void increaseFootContacts()
	{
		footContacts++;
	}
	
	public void decreaseFootContacts()
	{
		footContacts--;
	}
	
	public void calculateDistance(){
		Log.d("mine", "getX:"+getX()+" initX:"+initX);
		distance= (getX()-initX)*PIXEL_DISTANCE_MULTIPLIER;
	}
	
	public int getNewMoney(){
		return money+(int)(DISTANCE_MONEY_MULTIPLIER*distance);
	}
	
	public abstract void onDie();
}