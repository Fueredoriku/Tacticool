package com.anything.tacticool.view.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteConnector {

    private SpriteConnectorEnum spriteConnectorEnum;
    private int x;
    private int y;

    public SpriteConnector(SpriteConnectorEnum spriteConnectorEnum, int x, int y) {
        this.spriteConnectorEnum = spriteConnectorEnum;
        this.x = x;
        this.y = y;
    }

    public SpriteConnectorEnum getSpriteConnectorEnum() {
        return spriteConnectorEnum;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Currently loads each texture seperately from memory each time this is called.
     * Should be changed to use TextureRegion to avoid this.
     */

    public Sprite prepareSprite(float tileScale) {
        Texture texture = new Texture(spriteConnectorEnum.getFilePath());
        Sprite sprite = new Sprite(texture);
        sprite.setSize(tileScale, tileScale);
        return sprite;
    }
}
