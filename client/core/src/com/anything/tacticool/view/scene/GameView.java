package com.anything.tacticool.view.scene;

import com.anything.tacticool.view.util.ActionPointSingleton;
import com.anything.tacticool.view.util.GridElementIterator;
import com.anything.tacticool.view.util.TextureHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GameView extends Scene {

    private GridElementIterator tileIterator;
    private GridElementIterator actorIterator;
    private ActionPointSingleton ap;
    private int width;
    private int height;

    private Stage stage;
    private Texture apHUD;
    private Sprite apSprite;

    private TextureHandler textureHandler;
    private BitmapFont font;
    private Skin skin;

    private TextButton resetButton;
    private TextButton submitButton;
    private float uiWidth;
    private float uiHeight;

    public GameView(){
        super();
        textureHandler = new TextureHandler(width, height);
        ap = ActionPointSingleton.getInstance();
        apHUD = new Texture("aphud.png");
        apSprite = new Sprite(apHUD);
        font = new BitmapFont();
        uiWidth = Gdx.graphics.getWidth()/3f;
        uiHeight = Gdx.graphics.getHeight()/6f;
    }

    @Override
    public void prepareScene(){
        stage = new Stage(new ScreenViewport());
        buildButtons();
        while (tileIterator.hasNext()){
            try {
                stage.addActor(tileIterator.next().getActor());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tileIterator.reset();

        Gdx.input.setInputProcessor(stage);
    }

    private void buildButtons(){

        skin.getFont("default-font").getData().setScale(3f);
        resetButton = new TextButton("Reset Moves", skin);
        submitButton = new TextButton("Submit Moves", skin);
        resetButton.setSize(uiWidth, uiHeight);
        submitButton.setSize(uiWidth, uiHeight);
        resetButton.setPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()-10);
        submitButton.setPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()-30);
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ap.reset();
            }
        });

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: Submit moves to controller
            }
        });



        stage.addActor(resetButton);
    }

    private void drawHUD(SpriteBatch batch){
        batch.draw(apSprite, 0, Gdx.graphics.getHeight()-10);
        font.draw(batch, ""+ap.actionPoint, 10, Gdx.graphics.getHeight()-10);
    }

    @Override
    public void onRender(SpriteBatch batch){
        textureHandler.createBatch(tileIterator, batch);
        textureHandler.createBatch(actorIterator, batch);
        textureHandler.createBatch(ap.getInputIterator(), batch);
        drawHUD(batch);
    }
}
