package com.anything.tacticool;

import com.anything.tacticool.view.scene.LoginView;
import com.anything.tacticool.view.scene.SceneManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Tacticool extends ApplicationAdapter {
	SpriteBatch batch;
	private SceneManager sm;

	@Override
	public void create () {
		batch = new SpriteBatch();
		sm = SceneManager.getInstance();
		sm.Push(new LoginView());
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 0, 1);
		sm.Peek().render(batch);
	}
	
	@Override
	public void dispose () {
		sm.Peek().dispose(batch);
	}
}
