package com.anything.tacticool.view.util.spriteConnectors;

import com.anything.tacticool.view.util.SpriteConnectorEnum;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorSprite extends SpriteConnector {

    Actor actor;

    public ActorSprite(SpriteConnectorEnum spriteConnectorEnum, int x, int y, Actor actor) {
        super(spriteConnectorEnum, x, y);
        this.actor = actor;
    }


    @Override
    public Sprite prepareSprite(float tileScale) {
        // do something with the actor
        Texture texture = new Texture(getSpriteConnectorEnum().getFilePath());
        Sprite sprite = new Sprite(texture);
        sprite.setSize(tileScale, tileScale);
        return sprite;
    }
}
