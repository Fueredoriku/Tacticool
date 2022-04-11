package com.anything.tacticool.view.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainView extends Scene {

    @Override
    public void onRender(SpriteBatch batch){
        //Temporary code to switch directly to gameView as main menu still is not defined.
        sm.Push(new GameView());
    }
}
