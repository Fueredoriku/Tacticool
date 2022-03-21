package com.anything.tacticool.view.scene;

import com.badlogic.gdx.ApplicationAdapter;

public abstract class Scene extends ApplicationAdapter {
    protected SceneManager sm;

    protected Scene(){this.sm = SceneManager.getInstance();}
}
