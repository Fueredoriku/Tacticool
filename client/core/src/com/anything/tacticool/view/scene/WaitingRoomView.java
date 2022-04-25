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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

import httpRequests.Request;

public class WaitingRoomView extends Scene {

    private final int gameID;
    private final int playerID;
    private Stage stage;
    private Skin skin;

    private final Request request;
    private final ActorFactory actorFactory;

    public WaitingRoomView(int gamePin, int playerID){
        this.gameID = gamePin;
        this.request = new Request();
        this.actorFactory = new ActorFactory();
        this.playerID = playerID;
    }

    @Override
    public void prepareScene(){
        prepareVariables();
        prepareUI();
    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0.5f,0.2f,0.5f,1);

        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose(SpriteBatch batch) {
        stage.dispose();
        batch.dispose();
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

        Label game_pin_label = (Label) actorFactory.label(
                "Game Pin : " + this.gameID, skin,
                uiWidth, uiHeight,
                Gdx.graphics.getWidth()/2, ui_yScale * 3f,
                true
        );

        TextButton start_game_button = actorFactory.textButton(
                new TextButton("Start Game", skin),
                uiWidth, uiHeight, ui_xPosition, ui_yScale * 2f,
                new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        try{
                            startGame();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
        );

        TextButton leave_game_button = actorFactory.textButton(
                new TextButton("Leave Game", skin),
                uiWidth, uiHeight, ui_xPosition, ui_yScale * 1f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        try {
                            leaveGame();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        actorFactory.stageActors(stage, new Actor[] {game_pin_label, start_game_button, leave_game_button});
        Gdx.input.setInputProcessor(stage);
    }

    private void startGame() throws IOException {
        // TODO start the game
        request.getGameState(gameID);
        sm.Push(new GameView(playerID, gameID));
    }

    private void leaveGame() throws IOException {
        // TODO : Leave the game
        sm.Pop();
    }

}
