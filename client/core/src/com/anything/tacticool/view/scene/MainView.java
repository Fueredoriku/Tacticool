package com.anything.tacticool.view.scene;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainView extends Scene {

    protected SceneManager sm;

    /**
     * textField gameID_input
     * button joinGame
     * button settings
     * button quit
     */

    private TextField gameID_TextField;
    private ImageButton joinGame_Button;
    private ImageButton settings_Button;


    @Override
    public void render(SpriteBatch batch) {
        //Temporary code to switch directly to gameView as main menu still is not defined.
        sm.Push(new GameView());
    }

    //constructor

    private void show() {
        joinGame_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                joinGame(gameID_TextField.getText());
            }
        });

        settings_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openSettings();
            }
        });
    }

    //Method for joining game


    private void joinGame(String gameID) {

    }

    //Method for opening settings
    private void openSettings() {

    }

    //Method for closing app?


}

