package app.flycatfly;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import app.flycatfly.SceneManager.SceneType;

public class LoadingScene extends BaseScene
{
	@Override
	public void createScene()
	{
	    setBackground(new Background(Color.WHITE));
	    attachChild(new Text(400, 240, resourceManager.font, "Loading...", resourceManager.vbom));
	}

    @Override
    public void onBackKeyPressed()
    {
        return;
    }

    @Override
    public SceneType getSceneType()
    {
        return SceneType.SCENE_LOADING;
    }

    @Override
    public void disposeScene()
    {

    }
}