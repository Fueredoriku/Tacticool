package com.anything.tacticool.view.scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


enum MenuState {
    MAIN,
    QEUED,
    START
}

public class MainView extends Scene {

    protected SceneManager sm;

    private Stage stage;
    private String gameID;
    private MenuState menuState;


    @Override
    public void create() {
        this.menuState = MenuState.MAIN;

        this.stage = new Stage(new ScreenViewport());

        // Skin to texture UI elements. Currently uses libgdx's internal skin
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();

        // Instantiates UI elements
        TextField gameID_Input = new TextField("Game ID", skin);
        TextButton joinGame_Button = new TextButton("Join Game", skin);
        TextButton settings_Button = new TextButton("Settings", skin);

        // Defines listeners for when the join game and settings buttons are pressed
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

        // Sets locations of UI elements
        gameID_Input.setPosition(sw/2f, sh * 4/5, Align.center);
        joinGame_Button.setPosition(sw/2f, sh * 3/5, Align.center);
        settings_Button.setPosition(sw/2f, sh * 2/5, Align.center);

        // Adds UI elements to stage
        stage.addActor(gameID_Input);
        stage.addActor(joinGame_Button);
        stage.addActor(settings_Button);

        // Allows UI elements to take inputs
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0,0,0,0);

        switch (menuState) {
            case MAIN:
                stage.act();
                stage.draw();

                checkState();

                this.gameID = ((TextField) stage.getActors().get(0)).getText();
                break;
            case QEUED:
                //TODO make call to server to check if game can start
                if (true) {
                    this.menuState = menuState.START;
                }
                break;
            case START:
                //TODO make call to server to start new game
                this.menuState = menuState.MAIN;
                sm.Push(new GameView());
                break;
        }
    }

    // Rudimentary exception handling
    private void checkState() {
        if (stage.getActors().size != 3) {
            throw new IllegalStateException("Expected 3 actors, instead of " + stage.getActors().size);
        }
        if (!(stage.getActors().get(0) instanceof TextField)) {
            throw new IllegalStateException("Expected TextField at index 0 in Actors, instead of " + stage.getActors().get(0).getName());
        }
    }


    //Method for joining game
    private void joinGame(String gameID) {
        this.menuState = menuState.QEUED;
    }

    //Method for opening settings
    private void openSettings() {
        sm.Push(new Settings());
    }
}

