package com.anything.tacticool.view.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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

    public Slider slider(Slider slider, float width, float height, float x, float y, float value) {
        slider.setSize(width, height);
        slider.setPosition(x, y);
        slider.setValue(value*slider.getMaxValue());
        return slider;
    }


    public void stageActors(Stage stage, Actor[] actors) {
        for (Actor actor : actors) {
            stage.addActor(actor);
        }
    }
}
