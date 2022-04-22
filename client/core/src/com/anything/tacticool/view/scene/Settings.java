package com.anything.tacticool.view.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Settings extends Scene {

    private Stage stage;
    private Skin skin;


    @Override
    public void prepareScene() {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.skin.getFont("default-font").getData().setScale(3f);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float uiWidth = screenWidth/3f;
        float uiHeight = screenHeight/6f;
        float ui_xPosition = screenWidth/2 - uiWidth/2;

        TextButton mute_Button = new TextButton("Mute Game", skin);
        TextButton closeSettings_Button = new TextButton("Close Settings", skin);

        mute_Button.setPosition(ui_xPosition, screenHeight * 4/5);
        closeSettings_Button.setPosition(ui_xPosition, screenHeight * 3/5);

        mute_Button.setSize(uiWidth, uiHeight);
        closeSettings_Button.setSize(uiWidth, uiHeight);

        mute_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO code to mute/unmute the game
            }
        });
        closeSettings_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeSettings();
            }
        });

        stage.addActor(mute_Button);
        stage.addActor(closeSettings_Button);

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0,0,0,1);

        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose(SpriteBatch batch) {
        stage.dispose();
        batch.dispose();
    }


    private void closeSettings() {
        sm.Pop();
    }
}
