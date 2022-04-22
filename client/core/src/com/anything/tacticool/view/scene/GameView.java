package com.anything.tacticool.view.scene;

import com.anything.tacticool.view.util.ActionPointSingleton;
import com.anything.tacticool.view.util.GridElementIterator;
import com.anything.tacticool.view.util.TextureHandler;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Stack;

public class GameView extends Scene {

    private GridElementIterator tileIterator;
    private GridElementIterator actorIterator;
    private ActionPointSingleton ap;
    private int width;
    private int height;

    private Stack chosenActions;
    //Maybe stack of inputs?:
    //private Stack<Input> inputs;
    private Stage stage;

    private TextureHandler textureHandler;

    public GameView(){
        super();
        textureHandler = new TextureHandler(width, height);
    }

    public void prepareStage(){
        stage = new Stage(new ScreenViewport());
        while (tileIterator.hasNext()){
            try {
                stage.addActor(tileIterator.next().getActor());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tileIterator.reset();
    }

    @Override
    public void onRender(SpriteBatch batch){
        textureHandler.createBatch(tileIterator, batch);
        textureHandler.createBatch(actorIterator, batch);
        textureHandler.createBatch(ap.getInputIterator(), batch);
    }
}
