package com.anything.tacticool.view.scene;

import com.anything.tacticool.view.util.ActorFactory;
import com.anything.tacticool.view.util.AudioController;
import com.anything.tacticool.view.util.SongPathEnum;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Settings extends Scene {

    private Stage stage;
    private Skin skin;

    private ActorFactory actorFactory;


    public Settings() {
        this.actorFactory = new ActorFactory();
    }

    @Override
    public void prepareScene() {
        prepareVariables();
        prepareUI();
    }

    private void prepareVariables() {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.skin.getFont("default-font").getData().setScale(3f);
    }


    private void prepareUI() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float uiWidth = screenWidth/3f;
        float uiHeight = screenHeight/7f;
        float ui_xPosition = screenWidth/2 - uiWidth/2;
        float ui_yScale = screenHeight/6f;
        float ui_side_offset = uiWidth/1.8f;

        Slider musicSlider = actorFactory.slider(
                new Slider(0f, 10f, 1f, false, skin),
                uiWidth, uiHeight, ui_xPosition - ui_side_offset, ui_yScale * 4f, AudioController.getMusicVolume()
        );

        Slider soundSlider = actorFactory.slider(
                new Slider(0f, 10f, 1f, false, skin),
                uiWidth, uiHeight, ui_xPosition + ui_side_offset, ui_yScale * 4f, AudioController.getSoundVolume()
        );

        Label music_slider_label = (Label) actorFactory.label("Music Volume", skin,
                uiWidth, uiHeight,
                ui_xPosition - ui_side_offset + uiWidth/2, ui_yScale * 5f,
                true
        );

        Label sound_slider_label = (Label) actorFactory.label("Sound Volume", skin,
                uiWidth, uiHeight,
                ui_xPosition + ui_side_offset + uiWidth/2, ui_yScale * 5f,
                true
        );

        Label game_music_label = (Label) actorFactory.label("Game Music", skin,
                uiWidth, uiHeight,
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,
                true
        );

        TextButton tenseMusic_Button = actorFactory.textButton(
                new TextButton("Tense", skin),
                uiWidth, uiHeight, ui_xPosition - uiWidth/1.8f, ui_yScale * 2f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        setGameSong(SongPathEnum.TENSE);
                    }
                }
        );

        TextButton orchestralMusic_Button = actorFactory.textButton(
                new TextButton("Orchestral", skin),
                uiWidth, uiHeight, ui_xPosition + uiWidth/1.8f, ui_yScale * 2f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        setGameSong(SongPathEnum.ORCHESTRAL);
                    }
                }
        );

        TextButton closeSettings_Button = actorFactory.textButton(
                new TextButton("Return", skin),
                uiWidth, uiHeight, ui_xPosition, ui_yScale,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        closeSettings();
                    }
                }
        );

        actorFactory.stageActors(stage, new Actor[] {
                musicSlider, soundSlider, music_slider_label, sound_slider_label, game_music_label, tenseMusic_Button, orchestralMusic_Button, closeSettings_Button
        });

        Gdx.input.setInputProcessor(stage);
    }

    private void setGameSong(SongPathEnum songPathEnum) {
        AudioController.setGame_musicPath(songPathEnum.getSongPath());
    }


    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0.5f,0.2f,0.5f,1f);

        batch.begin();
        stage.draw();
        batch.end();

        checkState();
        AudioController.setMusicVolume(((Slider) stage.getActors().get(0)).getPercent());
        AudioController.setSoundVolume(((Slider) stage.getActors().get(1)).getPercent());
    }

    private void checkState() {
        if (stage.getActors().size != 8) {
            throw new IllegalStateException("Stage should have 8 elements, not " + stage.getActors().size);
        }
        if (!(stage.getActors().get(0) instanceof Slider) || !(stage.getActors().get(1) instanceof Slider)) {
            throw new IllegalStateException("Element in Stage at index 0 and 1 should be Sliders, not " + stage.getActors().get(0) + " and " + stage.getActors().get(1));
        }
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
