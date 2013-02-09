package com.example.flycatfly;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;

public class GameScene extends Scene implements IOnSceneTouchListener {
	public Cat cat;
	Camera mCamera;
	
	public GameScene() {
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		mCamera=MainActivity.getSharedInstance().mCamera;
		cat=Cat.getInstance();
		attachChild(cat.sprite);
		Player oPlayer = new Player(0, 0, MainActivity.getSharedInstance().mTriangleFaceTextureRegion, MainActivity.getSharedInstance().getVertexBufferObjectManager());
		this.attachChild(oPlayer);
	}	

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	

	
}