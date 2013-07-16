package app.flycatfly;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.color.Color;

import app.flycatfly.SceneManager.SceneType;

import com.badlogic.gdx.math.Vector2;

public class GameScene extends BaseScene
{
	private Text distanceCount;
	private int distance = 0;
	private PhysicsWorld physicsWorld;
	private HUD gameHUD;

	private void createPhysics()
	{
	    physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -17), false); 
	    registerUpdateHandler(physicsWorld);
	}
	
	private void createBackground()
	{
	    setBackground(new Background(Color.BLUE));
	    // CREATE DISTANCE COUNTER
	    distanceCount = new Text(20, 420, resourceManager.font, "Score: 0123456789", vbom);
	    distanceCount.setSkewCenter(0, 0);    
	    distanceCount.setText("Score: 0");
	    gameHUD.attachChild(distanceCount);
	}
	
	private void addTodistance(int i)
	{
	    distance += i;
	    distanceCount.setText(distance + " feet");
	}
	
	private void createHUD()
	{
	    gameHUD = new HUD();
	    camera.setHUD(gameHUD);
	}
	
	@Override
	public void createScene()
	{
	    createBackground();
	    createHUD();
	    createPhysics();
	}

    @Override
    public void onBackKeyPressed()
    {
        
    }

    @Override
    public SceneType getSceneType()
    {
        return SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene()
    {
        
    }
}
