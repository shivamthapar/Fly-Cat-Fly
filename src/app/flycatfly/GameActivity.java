package app.flycatfly;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;


public class GameActivity extends BaseGameActivity
{
	private Camera camera;
	private ResourceManager resourceManager;
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) 
	{
	    return new LimitedFPSEngine(pEngineOptions, 60);
	}
	
    public EngineOptions onCreateEngineOptions()
    {
    	camera = new Camera(0, 0, 800, 480);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(800, 480), camera);
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
    {
    	ResourceManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
        resourceManager = ResourceManager.getInstance();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
    {
        SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
    }
    

    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
    {
        mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
        {
                public void onTimePassed(final TimerHandler pTimerHandler) 
                {
                	 mEngine.unregisterUpdateHandler(pTimerHandler);
                     SceneManager.getInstance().createMenuScene();
                }
        }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
    
    @Override
    protected void onDestroy()
    {
    	super.onDestroy();
            System.exit(0);	
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
        }
        return false; 
    }
}