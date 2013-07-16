package app.flycatfly;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import app.flycatfly.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener
{
	private Sprite splash;
	
    @Override
    public void createScene()
    {
    	createBackground();
    	createMenuChildScene();
    }

    @Override
    public void onBackKeyPressed()
    {
    	System.exit(0);
    }

    @Override
    public SceneType getSceneType()
    {
    	return SceneType.SCENE_MENU;
    }

    @Override
    public void disposeScene()
    {
        splash.detachSelf();
        splash.dispose();
        this.detachSelf();
        this.dispose();
    }
    
    private void createBackground()
    {
        attachChild(new Sprite(0, 0, resourceManager.menu_background_region, vbom)
        {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
            {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        });
    }
    
    private MenuScene menuChildScene;
    private final int MENU_PLAY = 0;
    private final int MENU_OPTIONS = 1;

    private void createMenuChildScene()
    {
        menuChildScene = new MenuScene(camera);
        menuChildScene.setPosition(0, 0);
        
        final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourceManager.play_region, vbom), 1.2f, 1);
        final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourceManager.options_region, vbom), 1.2f, 1);
        
        menuChildScene.addMenuItem(playMenuItem);
        menuChildScene.addMenuItem(optionsMenuItem);
        
        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);
        
        playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY()+20);
        optionsMenuItem.setPosition(optionsMenuItem.getX(), optionsMenuItem.getY()+50);
        
        menuChildScene.setOnMenuItemClickListener(this);
        
        setChildScene(menuChildScene);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
    {
        switch(pMenuItem.getID())
        {
            case MENU_PLAY:
                SceneManager.getInstance().loadGameScene(engine);
                return true;
            case MENU_OPTIONS:
                return true;
            default:
                return false;
        }
    }

}