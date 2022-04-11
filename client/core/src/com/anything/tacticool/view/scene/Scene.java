package com.anything.tacticool.view.scene;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Scene extends ApplicationAdapter {
    protected SceneManager sm;

    protected Scene(){this.sm = SceneManager.getInstance();}

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.end();
    }

    public void dispose(SpriteBatch batch){
        batch.dispose();
    }
}
