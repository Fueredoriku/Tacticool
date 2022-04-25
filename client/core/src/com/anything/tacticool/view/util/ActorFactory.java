package com.anything.tacticool.view.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

public class ActorFactory {

    public TextButton textButton(TextButton textButton, float width, float height, float x, float y, EventListener eventListener) {
        textButton.setSize(width, height);
        textButton.setPosition(x, y);
        textButton.addListener(eventListener);
        return textButton;
    }

    public Actor actor(Actor actor, float width, float height, float x, float y) {
        actor.setSize(width, height);
        actor.setPosition(x, y);
        return actor;
    }

    /**
     * Create a Label based on the given values
     * @param text of the label
     * @param skin for the text
     * @param width of the label
     * @param height of the label
     * @param x position of the label
     * @param y position of the label
     * @param center whether the given position is the center of the text
     * @return
     */
    public Actor label(String text, Skin skin, float width, float height, float x, float y, boolean center){
        Label label = new Label(text, skin);
        label.setPosition(x, y);
        if(center){
            label.setWrap(true);
            label.setPosition(x - label.getWidth()/2, y );
        }

        return label;
    }

    public Slider slider(Slider slider, float width, float height, float x, float y, float value) {
        slider.setSize(width, height);
        slider.setPosition(x, y);
        slider.setValue(value*slider.getMaxValue());
        return slider;
    }

    public TextField textField(TextField textField, float width, float height, float x, float y, TextField.TextFieldFilter textFieldFilter) {
        textField.setSize(width, height);
        textField.setPosition(x, y);
        textField.setTextFieldFilter(textFieldFilter);
        textField.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                super.keyboardFocusChanged(event, actor, focused);
                if (!focused) {
                    Gdx.input.setOnscreenKeyboardVisible(false);
                }
            }
        });
        return textField;
    }


    public void stageActors(Stage stage, Actor[] actors) {
        for (Actor actor : actors) {
            stage.addActor(actor);
        }
    }
}
