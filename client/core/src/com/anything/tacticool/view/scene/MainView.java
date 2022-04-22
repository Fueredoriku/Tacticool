package com.anything.tacticool.view.scene;


import com.anything.tacticool.view.util.AudioController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


enum MenuState {
    MAIN,
    QEUED,
    START
}

public class MainView extends Scene {

    private Stage stage;
    private Skin skin;
    private MenuState menuState;

    private String gameID;


    @Override
    public void prepareScene() {
        prepareVariables();
        prepareUI();
        prepareSound();
    }


    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0,0,0,1);

        batch.begin();

        switch (menuState) {
            case MAIN:
                stage.draw();
                checkState();
                this.gameID = ((TextField) stage.getActors().get(0)).getText();
                break;

            case QEUED:
                //TODO make call to server to check if game can start
                //TODO change if statement to take the answer from the server as its condition
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

        batch.end();
    }

    @Override
    public void dispose(SpriteBatch batch) {
        stage.dispose();
        AudioController.endMusic();
        batch.dispose();
    }

    @Override
    public void disposeEarly() {
        AudioController.endMusic();
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
    private void joinGame() {
        this.menuState = menuState.QEUED;
    }

    //Method for opening settings
    private void openSettings() {
        sm.Push(new Settings());
    }


    // Two methods used by constructor
    private void prepareVariables() {
        this.menuState = MenuState.MAIN;
        this.stage = new Stage(new ScreenViewport());

        // Skin to texture UI elements. Currently uses libgdx's basic skin
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Upscales font
        skin.getFont("default-font").getData().setScale(3f);
    }
    private void prepareUI() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float uiWidth = screenWidth/3f;
        float uiHeight = screenHeight/6f;
        float ui_xPosition = screenWidth/2 - uiWidth/2;

        // Instantiates UI elements
        TextField gameID_Input = new TextField("Game ID", skin);
        TextButton joinGame_Button = new TextButton("Join Game", skin);
        TextButton settings_Button = new TextButton("Settings", skin);

        // Sets locations of UI elements
        gameID_Input.setPosition(ui_xPosition, screenHeight * 4/5);
        joinGame_Button.setPosition(ui_xPosition, screenHeight * 3/5);
        settings_Button.setPosition(ui_xPosition, screenHeight * 2/5);

        // Sets size of all UI elements
        gameID_Input.setSize(uiWidth, uiHeight);
        joinGame_Button.setSize(uiWidth, uiHeight);
        settings_Button.setSize(uiWidth, uiHeight);

        // Defines listeners for when the join game and settings buttons are pressed
        joinGame_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joinGame();
            }
        });

        settings_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openSettings();
            }
        });

        // Adds UI elements to stage
        stage.addActor(gameID_Input);
        stage.addActor(joinGame_Button);
        stage.addActor(settings_Button);

        // Allows UI elements to take inputs
        Gdx.input.setInputProcessor(stage);
    }

    private void prepareSound() {
        AudioController.setCurrent_musicPath("audio/main_menu.ogg");
        AudioController.playMusic();
    }
}

