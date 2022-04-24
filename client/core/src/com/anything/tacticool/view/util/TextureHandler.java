package com.anything.tacticool.view.util;


import com.anything.tacticool.view.util.spriteConnectors.SpriteConnector;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextureHandler {

    private float tileScale;

    /**
     *  Checks which direction will be the limiting direction that has to be used to size the grid to the screen.
     *  Finds the size each tile needs to be to fit the screen.
     */

    public TextureHandler(int width, int height) {
        if (Gdx.graphics.getWidth() / width < Gdx.graphics.getHeight() / height) {
            tileScale = (Gdx.graphics.getWidth() / width);
        } else {
            tileScale = (Gdx.graphics.getHeight() / height);
        }
    }


    /**
     * Method adds all elements in the client-side model of the gamestate to a SpriteBatch.
     * It should be called in GameView's render function.
     *
     * This method is also used to animate the results of a round.
     * To do so, we simply have to make sure that the changes made to the gamestate is made step by step with a few frames delay instead of all at the same frame
     *
     * Could in the future implement a way to save a batch of tileIterator, since it doesn't change
     * That way we can avoid preparing it each render call
     */


    public void createBatch(GridElementIterator iterator, SpriteBatch batch) {
        while (iterator.hasNext()) {
            SpriteConnector connector = iterator.next();
            connector.prepareSprite(tileScale).draw(batch);
        }
        iterator.reset();
    }
}
