package com.anything.tacticool.view.scene;

import com.anything.tacticool.view.util.AudioController;
import com.anything.tacticool.view.util.SongPathsEnum;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
        prepareUI();
    }

    private void prepareUI() {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.skin.getFont("default-font").getData().setScale(3f);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float uiWidth = screenWidth/3f;
        float uiHeight = screenHeight/6f;
        float ui_xPosition = screenWidth/2 - uiWidth/2;

        TextButton mute_Button = new TextButton("Mute Game", skin);
        Label musicLabel = new Label("Music", skin);
        TextButton tenseMusic_Button = new TextButton("Tense", skin);
        TextButton orchestralMusic_Button = new TextButton("Orchestral", skin);
        TextButton closeSettings_Button = new TextButton("Close Settings", skin);


        mute_Button.setPosition(ui_xPosition, screenHeight * 4/5);
        musicLabel.setPosition(ui_xPosition, screenHeight * 3/5);
        tenseMusic_Button.setPosition(ui_xPosition - uiWidth * 1/2, screenHeight* 2/5);
        orchestralMusic_Button.setPosition(ui_xPosition + uiWidth * 1/2, screenHeight* 2/5);
        closeSettings_Button.setPosition(ui_xPosition, screenHeight * 1/5);

        mute_Button.setSize(uiWidth, uiHeight);
        musicLabel.setSize(uiWidth, uiHeight);
        tenseMusic_Button.setSize(uiWidth, uiHeight);
        orchestralMusic_Button.setSize(uiWidth, uiHeight);
        closeSettings_Button.setSize(uiWidth, uiHeight);

        mute_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mute_or_unmute_all();
            }
        });

        tenseMusic_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setGameSong(SongPathsEnum.TENSE);
            }
        });

        orchestralMusic_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setGameSong(SongPathsEnum.ORCHESTRAL);
            }
        });

        closeSettings_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeSettings();
            }
        });

        stage.addActor(mute_Button);
        stage.addActor(musicLabel);
        stage.addActor(tenseMusic_Button);
        stage.addActor(orchestralMusic_Button);
        stage.addActor(closeSettings_Button);

        Gdx.input.setInputProcessor(stage);
    }

    private void mute_or_unmute_all() {
        if (AudioController.getMusicVolume() == 0f && AudioController.getSoundVolume() == 0f) {
            AudioController.setSoundVolume(1f);
            AudioController.setMusicVolume(1f);
        } else {
            AudioController.setSoundVolume(0f);
            AudioController.setMusicVolume(0f);
        }
    }

    private void setGameSong(SongPathsEnum songPathsEnum) {
        AudioController.setGame_musicPath(songPathsEnum.getSongPath());
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
