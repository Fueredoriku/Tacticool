package com.anything.tacticool.view.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class TextureHandler {

    private float tileScale;

    /**
     *  Checks which direction will be the limiting direction that has to be used to size the grid to the screen.
     *  Finds the size each tile needs to be to fit the screen.
     */

    public TextureHandler(Playfield playfield) {
        checkGridNotEmpty(playfield);

        if (Gdx.graphics.getWidth()/playfield.getGrid().get(0).size() < Gdx.graphics.getHeight()/playfield.getGrid().size()) {
            tileScale = Gdx.graphics.getWidth()/playfield.getGrid().get(0).size();
        } else {
            tileScale = Gdx.graphics.getHeight()/playfield.getGrid().size();
        }
    }


    /**
     * Currently loads each texture seperately from memory each time this is called.
     * Should be changed to use TextureRegion to avoid this.
     */

    public void renderBoard(Playfield playfield, SpriteBatch batch) {
        checkGridNotEmpty(playfield);

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

    /**
     * Adds all sprites from the inputs to the SpriteBatch, but makes them translucent
     */

    public void renderInputPreview(Input[] inputs, SpriteBatch batch) {
        batch.setColor(1,1,1,0.5f);
        for (Input input : inputs) {
            batch.draw(prepareSprite(input.getSpriteConnector()), input.getX(), input.getY());
        }
        batch.setColor(Color.WHITE);
    }

    /**
     *  Method for rendering the steps in the results given from the server.
     *  If the results are stored in an internal iterable, we can simply iteratevly make changes to Playfield, calling renderBoard after each change!
     *  Then we don't need this anymore
     */

    public void renderTurnResults() {

    }

    private Sprite prepareSprite(SpriteConnector spriteConnector) {
        Texture texture = new Texture(spriteConnector.getPath());
        Sprite sprite = new Sprite(texture);
        sprite.setSize(tileScale, tileScale);
        return sprite;
    }

    private void checkGridNotEmpty(Playfield playfield) {
        if (playfield.getGrid().size() == 0) {
            throw new ArrayIndexOutOfBoundsException("Playfield can't be empty");
        }
        if (playfield.getGrid().get(0).size() == 0) {
            throw new ArrayIndexOutOfBoundsException("Playfield can't be empty");
        }
    }

}
