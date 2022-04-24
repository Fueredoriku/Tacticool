package com.anything.tacticool.view.scene;

import com.anything.tacticool.view.util.ActorFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

import httpRequests.Request;

public class LoginView extends Scene{

    private Stage stage;
    private Skin skin;
    private ActorFactory actorFactory;
    private Request request;

    private String username;


    public LoginView() {
        this.actorFactory = new ActorFactory();
        this.request = new Request();
    }

    @Override
    public void prepareScene() {
        prepareVariables();
        prepareUI();
    }

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

        Label username_Label = (Label) actorFactory.actor(
          new Label("Username", skin),
                uiWidth, uiHeight, ui_xPosition, ui_yScale * 3 + uiHeight
        );

        TextField username_TextField = (TextField) actorFactory.actor(
                new TextField("", skin),
                uiWidth, uiHeight, ui_xPosition, ui_yScale * 3
        );

        TextButton login_Button = actorFactory.textButton(
                new TextButton("Login", skin),
                uiWidth, uiHeight, ui_xPosition, ui_yScale * 2,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        login();
                    }
                }
        );

        actorFactory.stageActors(stage, new Actor[] {username_TextField, username_Label, login_Button});
        Gdx.input.setInputProcessor(stage);
    }

    private void login() {
        //TODO connect to server
        try {
            sm.Push(new MainView(1));
            sm.Push(new MainView(request.getPlayerIDFromLogin(username,"")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0.5f, 0.2f, 0.5f, 1);
        batch.begin();
        stage.draw();
        checkState();
        this.username = ((TextField) stage.getActors().get(0)).getText();
        batch.end();
    }

    private void checkState() {
        if (stage.getActors().size != 3) {
            throw new IllegalStateException("Expected 3 actors, instead of " + stage.getActors().size);
        }
        if (!(stage.getActors().get(0) instanceof TextField)) {
            throw new IllegalStateException("Expected TextField at index 0 in Actors, instead of " + stage.getActors().get(0).getName());
        }
    }

    @Override
    public void disposeEarly() {

    }

    @Override
    public void dispose(SpriteBatch batch) {
        batch.dispose();
    }

}
