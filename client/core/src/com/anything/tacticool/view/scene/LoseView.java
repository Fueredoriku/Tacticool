package com.anything.tacticool.view.scene;

import com.anything.tacticool.view.util.ActorFactory;
import com.anything.tacticool.view.util.AudioController;
import com.anything.tacticool.view.util.GridElementIterator;
import com.anything.tacticool.view.util.SongPathEnum;
import com.anything.tacticool.view.util.SpriteConnectorEnum;
import com.anything.tacticool.view.util.TextureHandler;
import com.anything.tacticool.view.util.spriteConnectors.SimpleSprite;
import com.anything.tacticool.view.util.spriteConnectors.SpriteConnector;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoseView extends Scene{
    private Stage stage;
    private Skin skin;

    private static final float textScale = 12f;
    Image textImage;

    private ActorFactory actorFactory;

    public LoseView(){
        this.actorFactory = new ActorFactory();
        textImage = new Image(new Texture(SpriteConnectorEnum.LOSE.getFilePath()));
    }

    @Override
    public void prepareScene() {
        super.prepareScene();
        prepareVariables();
    }

    private void prepareVariables() {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.skin.getFont("default-font").getData().setScale(3f);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float uiWidth = screenWidth/4f;
        float uiHeight = screenHeight/6f;
        float ui_xPosition = screenWidth/2 - uiWidth/2;

        textImage.setScale(textScale);
        textImage.setPosition(screenWidth/2 - (textImage.getWidth() * textScale) / 2,
                screenHeight/2 - screenHeight/3);



        TextButton quitButton = actorFactory.textButton(
                new TextButton("Quit", skin),
                uiWidth, uiHeight, ui_xPosition, uiHeight * 1.5f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        quit();
                    }
                }
        );

        actorFactory.stageActors(stage, new Actor[] {quitButton});
        Gdx.input.setInputProcessor(stage);


    }


    @Override
    public void onRender(SpriteBatch batch) {
        ScreenUtils.clear(0.5f,0.2f,0.5f,1f);
        textImage.draw(batch, 1);
        stage.draw();
    }

    private void quit(){
        sm.Pop();
        sm.Pop();
    }

}
