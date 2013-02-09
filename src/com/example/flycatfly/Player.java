package com.example.flycatfly;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Player extends GameObject {
	 
    // ===========================================================
    // Constants
    // ===========================================================
 
    // ===========================================================
    // Fields
    // ===========================================================
 
    // ===========================================================
    // Constructors
    // ===========================================================
 
    public Player(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }
 
    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
 
    @Override
    public void move() {
        this.mPhysicsHandler.setVelocityX(100);
    }
 
    // ===========================================================
    // Methods
    // ===========================================================
 
}