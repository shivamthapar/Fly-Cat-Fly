package app.flycatfly.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;

import app.flycatfly.base.BaseScene;
import app.flycatfly.manager.SceneManager;
import app.flycatfly.manager.SceneManager.SceneType;

public class FlightCompleteScene extends BaseScene implements IOnMenuItemClickListener
{
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------

	private MenuScene menuChildScene;

	private Text gameOverText;
	private Text scoreText;
	private Text distanceText;

	private final int MENU_PLAY = 0;
	private final int MENU_QUIT = 1;
	
	private void createText()
	{
		gameOverText = new Text(0, 0, resourcesManager.font, "Game Over!", vbom);
		scoreText = new Text(0, 0, resourcesManager.font, "Score: ", vbom);
		distanceText = new Text(0, 0, resourcesManager.font, "Distance: ", vbom);
	}

	@Override
	public void createScene()
	{
		createBackground();
		createText();
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
		return SceneType.SCENE_COMPLETE;
	}


	@Override
	public void disposeScene()
	{
		// TODO Auto-generated method stub
	}

	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
	{
		switch(pMenuItem.getID())
		{
		case MENU_PLAY:
			//Load Game Scene!
			SceneManager.getInstance().loadGameScene(engine);
			return true;
		case MENU_QUIT:
			return true;
		default:
			return false;
		}
	}

	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------

	private void createBackground()
	{
		attachChild(new Sprite (400, 240, resourcesManager.menu_background_region, vbom)
		{
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) 
			{
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});
	}

	private void createMenuChildScene()
	{
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);

		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
		final IMenuItem quitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_QUIT, resourcesManager.options_region, vbom), 1.2f, 1);

		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(quitMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() - 40);
		quitMenuItem.setPosition(quitMenuItem.getX(), quitMenuItem.getY() - 70);

		menuChildScene.setOnMenuItemClickListener(this);

		setChildScene(menuChildScene);
	}
}
/**
private TiledSprite star1;
private TiledSprite star2;
private TiledSprite star3;

public enum StarsCount
{
	ONE,
	TWO,
	THREE
}

private void attachStars(VertexBufferObjectManager pSpriteVertexBufferObject)
{
	star1 = new TiledSprite(150, 150, ResourcesManager.getInstance().complete_stars_region, pSpriteVertexBufferObject);
	star2 = new TiledSprite(325, 150, ResourcesManager.getInstance().complete_stars_region, pSpriteVertexBufferObject);
	star3 = new TiledSprite(500, 150, ResourcesManager.getInstance().complete_stars_region, pSpriteVertexBufferObject);

	attachChild(star1);
	attachChild(star2);					OLD CODE KEPT FOR REFERENCE: DELETE LATER
	attachChild(star3);
}

/**
 * Change star`s tile index, depends on stars count.
 * @param starsCount
 */