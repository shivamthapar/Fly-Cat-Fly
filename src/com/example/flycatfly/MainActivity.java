package com.example.flycatfly;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Typeface;

public class MainActivity extends SimpleBaseGameActivity {

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;

	public Font mFont;
	public Camera mCamera;
	public BitmapTextureAtlas mBitmapTextureAtlas;
	public TiledTextureRegion mTriangleFaceTextureRegion;

	//A reference to the current scene
	public Scene mCurrentScene;
	public static MainActivity instance;
	
    @Override
    public EngineOptions onCreateEngineOptions() {
    	instance = this;
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);

    }

    @Override
    protected void onCreateResources() {
    	loadGraphics();
        loadFonts();
        loadSounds();
    	

    }


	private void loadGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	
    	this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR);
    	this.mTriangleFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "facebox.png", 0, 64, 1, 1);
        
    	this.mBitmapTextureAtlas.load();
	}
	
	private void loadFonts()
	{
    	mFont = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
    	mFont.load();
	}
	
	private void loadSounds()
	{
	    
	}
    @Override
    protected Scene onCreateScene() {
        mEngine.registerUpdateHandler(new FPSLogger());
        mCurrentScene = new SplashScene();
        return mCurrentScene;
    }
    public static MainActivity getSharedInstance() {
        return instance;
    }

    // to change the current main scene
    public void setCurrentScene(Scene scene) {
        mCurrentScene = scene;
        getEngine().setScene(mCurrentScene);
    }
    

	
}
