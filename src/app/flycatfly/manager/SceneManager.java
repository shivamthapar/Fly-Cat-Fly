package app.flycatfly.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import app.flycatfly.base.BaseScene;
import app.flycatfly.scene.ShopScene;
import app.flycatfly.scene.GameScene;
import app.flycatfly.scene.LoadingScene;
import app.flycatfly.scene.MainMenuScene;
import app.flycatfly.scene.SplashScene;

public class SceneManager
{
	//---------------------------------------------
	// SCENES
	//---------------------------------------------
	
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameScene;
	private BaseScene loadingScene;
	private BaseScene shopScene;
	
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------
	
	private static final SceneManager INSTANCE = new SceneManager();
	
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	
	private BaseScene currentScene;
	
	private Engine engine = ResourcesManager.getInstance().engine;
	
	public enum SceneType
	{
		SCENE_SPLASH,
		SCENE_MENU,
		SCENE_GAME,
		SCENE_LOADING,
		SCENE_SHOP
	}
	
	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------
	
	public void setScene(BaseScene scene)
	{
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType)
	{
		switch (sceneType)
		{
			case SCENE_MENU:
				setScene(menuScene);
				break;
			case SCENE_GAME:
				setScene(gameScene);
				break;
			case SCENE_SPLASH:
				setScene(splashScene);
				break;
			case SCENE_LOADING:
				setScene(loadingScene);
				break;
			case SCENE_SHOP:
				setScene(shopScene);
			default:
				break;
		}
	}
	
	public void createMenuScene()
	{
		ResourcesManager.getInstance().loadMenuResources();
		menuScene = new MainMenuScene();
		loadingScene = new LoadingScene();
        SceneManager.getInstance().setScene(menuScene);
        disposeSplashScene();
	}
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
	{
		ResourcesManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}

	private void disposeSplashScene()
	{
		ResourcesManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}
	
	public void loadGameScene(final Engine mEngine)
	{
		setScene(loadingScene);
		ResourcesManager.getInstance().unloadMenuTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	mEngine.unregisterUpdateHandler(pTimerHandler);
            	ResourcesManager.getInstance().loadGameResources();
        		gameScene = new GameScene();
        		setScene(gameScene);
            }
		}));
	}
	
	public void loadMenuScene(final Engine mEngine)
	{
		setScene(loadingScene);
		gameScene.disposeScene();
		ResourcesManager.getInstance().unloadGameTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	mEngine.unregisterUpdateHandler(pTimerHandler);
            	ResourcesManager.getInstance().loadMenuTextures();
        		setScene(menuScene);
            }
		}));
	}
	
	public void loadShopScene(final Engine mEngine)
	{
		gameScene.disposeScene();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	mEngine.unregisterUpdateHandler(pTimerHandler);
            	ResourcesManager.getInstance().loadCompleteGraphics();
            	shopScene = new ShopScene();
        		setScene(shopScene);
            }
		}));
	}
	
	//---------------------------------------------
	// GETTERS AND SETTERS
	//---------------------------------------------
	
	public static SceneManager getInstance()
	{
		return INSTANCE;
	}
	
	public SceneType getCurrentSceneType()
	{
		return currentSceneType;
	}
	
	public BaseScene getCurrentScene()
	{
		return currentScene;
	}
}