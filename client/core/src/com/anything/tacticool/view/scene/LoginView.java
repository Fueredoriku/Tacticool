package com.anything.tacticool.view.scene;

import com.anything.tacticool.view.util.ActorFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginView extends Scene{

    private Stage stage;
    private Skin skin;
    private ActorFactory actorFactory;

    private String username;


    public LoginView() {
        this.actorFactory = new ActorFactory();
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

        TextField username = (TextField) actorFactory.actor(
                new TextField("", skin),
                uiWidth, uiHeight, ui_xPosition, ui_yScale * 3
        );

        TextButton login = actorFactory.textButton(
                new TextButton("Login", skin),
                uiWidth, uiHeight, ui_xPosition, ui_yScale * 2,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //joinGame();
                    }
                }
        );
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void disposeEarly() {

    }

}
