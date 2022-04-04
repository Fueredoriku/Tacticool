package com.anything.tacticool.view.util;


import com.badlogic.gdx.Gdx;
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
     *
     * Method adds all elements in the client-side model of the gamestate to a SpriteBatch.
     * It should be called in GameView's render function.
     *
     * This method is also used to animate the results of a round.
     * To do so, we simply have to make sure that the changes made to the gamestate is made step by step with a few frames delay instead of all at the same frame
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
     * Adds all sprites from the inputs to the SpriteBatch, and makes them translucent
     */

    public void renderInput(Input[] inputs, SpriteBatch batch) {
        batch.setColor(1,1,1,0.5f);
        for (Input input : inputs) {
            batch.draw(prepareSprite(input.getSpriteConnector()), input.getX()*tileScale, Gdx.graphics.getHeight() - input.getY()*tileScale);
        }
        batch.setColor(1,1,1,1);
    }


    private Sprite prepareSprite(SpriteConnector spriteConnector) {
        Texture texture = new Texture(spriteConnector.getFilePath());
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
