package com.anything.tacticool.view.scene;


import com.anything.tacticool.view.util.Input;
import com.anything.tacticool.view.util.Playfield;
import com.anything.tacticool.view.util.TextureHandler;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

public class GameView extends Scene {
    private int actionPoints;
    private Stack chosenActions;
    //Maybe stack of inputs?:
    private Stack<Input> inputs;

    private TextureHandler textureHandler;
    private Playfield playfield;

    public GameView(){
        super();
        playfield = new Playfield(new String[2]);
        textureHandler = new TextureHandler(playfield);
    }

    @Override
    public void onRender(SpriteBatch batch){
        textureHandler.renderBoard(playfield, batch);
    }
}
