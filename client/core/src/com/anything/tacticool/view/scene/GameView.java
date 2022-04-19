package com.anything.tacticool.view.scene;

import com.anything.tacticool.view.util.GridElementIterator;
import com.anything.tacticool.view.util.TextureHandler;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameView extends Scene {

    private GridElementIterator tileIterator;
    private GridElementIterator actorIterator;
    private GridElementIterator inputIterator;
    private int actionPoints;
    private int width;
    private int height;

    private Stack chosenActions;
    //Maybe stack of inputs?:
    //private Stack<Input> inputs;

    private TextureHandler textureHandler;

    public GameView(){
        super();
        textureHandler = new TextureHandler(width, height);
    }

    @Override
    public void onRender(SpriteBatch batch){
        textureHandler.createBatch(tileIterator, batch);
        textureHandler.createBatch(actorIterator, batch);
        textureHandler.createBatch(inputIterator, batch);
    }
}
