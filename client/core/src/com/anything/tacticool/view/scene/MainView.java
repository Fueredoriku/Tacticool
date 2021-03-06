package com.anything.tacticool.view.scene;


import com.anything.tacticool.view.util.ActorFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.anything.tacticool.view.util.AudioController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

import httpRequests.Request;


public class MainView extends Scene {

    private Stage stage;
    private Skin skin;

    private int gameID;
    private int playerID;

    private ActorFactory actorFactory;
    private Request request;


    public MainView(int playerID) {
        this.actorFactory = new ActorFactory();
        this.request = new Request();
        this.playerID = playerID;
    }

    @Override
    public void prepareScene() {
        prepareVariables();
        prepareUI();
        prepareSound();
    }


    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0.5f,0.2f,0.5f,1);

        batch.begin();

        stage.draw();
        checkState();
        String gameID_String = ((TextField) stage.getActors().get(0)).getText();
        this.gameID = gameID_String.isEmpty()?0:Integer.parseInt(gameID_String);
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
        try {
            Integer.parseInt(((TextField) stage.getActors().get(0)).getText());
        } catch (NumberFormatException e) {
            if (((TextField) stage.getActors().get(0)).getText().length() != 0) {
                throw new IllegalStateException("Textfield couldn't be cast to an int. It should only contain numbers.");
            }
        }
    }


    //Method for joining game
    private void joinGame() throws IOException {
        int gamePin = request.joinGame(gameID, playerID); //!\ UNCOMMENT FOR TESTING, DO NOT REMOVE /!\
        sm.Push(new WaitingRoomView(gamePin, playerID));
    }

    //Method for opening settings
    private void openSettings() {
        Gdx.input.setOnscreenKeyboardVisible(false);
        sm.Push(new Settings());
    }


    // Methods used by constructor
    private void prepareVariables() {
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
        float ui_yScale = screenHeight/5f;

        // Instantiates UI elements using the ActorFactory
        TextField gameID_Input = actorFactory.textField(
                new TextField("", skin),
                uiWidth, uiHeight, ui_xPosition, ui_yScale * 3,
                new TextField.TextFieldFilter.DigitsOnlyFilter()
        );

        TextButton joinGame_Button = actorFactory.textButton(
                new TextButton("Join Game", skin),
                uiWidth, uiHeight, ui_xPosition, ui_yScale * 2,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        try {
                            joinGame();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        TextButton settings_Button = actorFactory.textButton(
                new TextButton("Settings", skin),
                uiWidth, uiHeight, ui_xPosition, ui_yScale * 1,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        openSettings();
                    }
                }
        );

        actorFactory.stageActors(stage, new Actor[] {gameID_Input, joinGame_Button, settings_Button});

        // Allows UI elements to take inputs
        Gdx.input.setInputProcessor(stage);
    }


    private void prepareSound() {
        AudioController.setCurrent_musicPath("audio/main_menu.ogg");
        AudioController.playMusic();
    }
}

