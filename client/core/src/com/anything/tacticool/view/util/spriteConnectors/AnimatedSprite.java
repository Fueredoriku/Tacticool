package com.anything.tacticool.view.util.spriteConnectors;

import com.anything.tacticool.view.util.SpriteConnectorEnum;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimatedSprite extends SpriteConnector {

    public AnimatedSprite(SpriteConnectorEnum spriteConnectorEnum, int x, int y) {
        super(spriteConnectorEnum, x, y);
    }

    @Override
    public Sprite prepareSprite(float tileScale) {
        Texture texture = new Texture(getSpriteConnectorEnum().getFilePath());
        Sprite sprite = new Sprite(texture);
        sprite.setSize(tileScale, tileScale);
        return sprite;
    }
}
