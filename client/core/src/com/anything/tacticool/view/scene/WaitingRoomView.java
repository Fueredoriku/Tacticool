package com.anything.tacticool.view.scene;

import com.anything.tacticool.view.util.ActorFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import httpRequests.Request;

public class WaitingRoomView extends Scene {

    private final int gamePin;

    private Stage stage;
    private Skin skin;

    private final Request request;
    private final ActorFactory actorFactory;

    public WaitingRoomView(int gamePin){
        this.gamePin = gamePin;
        this.request = new Request();
        this.actorFactory = new ActorFactory();
    }

    @Override
    public void prepareScene(){

    }

    private void prepareVariables(){
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.getFont("default-font").getData().setScale(3f);
    }

    private void prepareUI(){
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float uiWidth = screenWidth/3f;
        float uiHeight = screenHeight/6f;
        float ui_xPosition = screenWidth/2 - uiWidth/2;
        float ui_yScale = screenHeight/5f;

        Label game_ID_label = (Label) actorFactory.actor(new Label("Game pin : "+this.gamePin, skin),
                uiWidth, uiHeight, ui_xPosition - uiWidth/1.8f, ui_yScale * 3f
        );
    }
}
