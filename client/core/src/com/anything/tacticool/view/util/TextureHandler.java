package com.anything.tacticool.view.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class TextureHandler {

    float tileScale;

    /**
     *  Checks which direction will be the limiting direction that has to be used to size the grid to the screen.
     *  Finds the size each tile needs to be to fit the screen.
     */

    public TextureHandler(Playfield playfield) {
        if (Gdx.graphics.getWidth()/playfield.getGrid().get(0).size() < Gdx.graphics.getHeight()/playfield.getGrid().size()) {
            tileScale = Gdx.graphics.getWidth()/playfield.getGrid().get(0).size();
        } else {
            tileScale = Gdx.graphics.getHeight()/playfield.getGrid().size();
        }
    }

    /**
     * Currently loads each texture from memory each time this is called.
     * Should be changed to use TextureRegion to avoid this.
     */

    public void renderBoard(Playfield playfield, SpriteBatch batch) {
        float x = 0.0f;
        float y = Gdx.graphics.getHeight() - tileScale;

        for (List<SpriteConnector[]> list: playfield.getGrid()) {
            for (SpriteConnector[] spriteConnectors: list) {
                for (SpriteConnector spriteConnector : spriteConnectors) {
                    batch.draw(prepareSprite(spriteConnector), x, y);
                }
                x += tileScale;
            }
            x = 0.0f;
            y -= tileScale;
        }
    }

    private Sprite prepareSprite(SpriteConnector spriteConnector) {
        Texture texture = new Texture(spriteConnector.getPath());
        Sprite sprite = new Sprite(texture);
        sprite.setSize(tileScale, tileScale);
        return sprite;
    }

}
