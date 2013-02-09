package com.example.flycatfly;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;

public class Cat {
	public Rectangle sprite;
	public static Cat instance;
	public Camera mCamera;
	public Glider glider;
	
	private int money;
		
	public static Cat getInstance(){
		if(instance==null)
			instance=new Cat();
		return instance;
	}
	
	private Cat()
	{
		sprite= new Rectangle (0,0,70,30,MainActivity.getSharedInstance().getVertexBufferObjectManager());
		mCamera= MainActivity.getSharedInstance().mCamera;
		sprite.setPosition(10, 10);
		money=0;
	}
	

}
