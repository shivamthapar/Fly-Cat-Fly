package com.example.flycatfly;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class Slope {
	public Sprite sprite;
	public static Slope instance;
	Camera mCamera;
	
	private final int width= 300;
	
	private int height;
	private int acceleration;
	private int resistance;
	
	public Slope(int h, int a, int r){
		height= 100;
		
		height=h;
		acceleration=a;
		resistance=r;
	
	}
}
