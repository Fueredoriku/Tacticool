package com.anything.tacticool.view.scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MainView extends Scene {

    protected SceneManager sm;

    /**
     * textField gameID_input
     * button joinGame
     * button settings
     * button quit
     */

    private Stage stage;
    private String gameID;


    @Override
    public void create() {
        this.stage = new Stage(new ScreenViewport());

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();

        TextField gameID_Input = new TextField("Game ID", skin);
        TextButton joinGame_Button = new TextButton("Join Game", skin);
        TextButton settings_Button = new TextButton("Settings", skin);

        joinGame_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joinGame(gameID);
            }
        });

        settings_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openSettings();
            }
        });

        gameID_Input.setPosition(sw/2f, sh * 4/5, Align.center);
        joinGame_Button.setPosition(sw/2f, sh * 3/5, Align.center);
        settings_Button.setPosition(sw/2f, sh * 2/5, Align.center);

        stage.addActor(gameID_Input);
        stage.addActor(joinGame_Button);
        stage.addActor(settings_Button);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0,0,0,0);

        stage.act();
        stage.draw();
        this.gameID = ((TextField) stage.getActors().get(0)).getText();
    }


    //Method for joining game
    private void joinGame(String gameID) {
        //TODO make call to server
        sm.Push(new GameView());
    }

    //Method for opening settings
    private void openSettings() {
        sm.Push(new Settings());
    }

    //Method for closing app?


}

