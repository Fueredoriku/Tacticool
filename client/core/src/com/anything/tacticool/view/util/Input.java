package com.anything.tacticool.view.util;

/**
 * A class for storing the players planned inputs while they plan their turn.
 * Is used by TextureHandler to render the planned inputs.
 * GameView should have a collection of these.
 * If the player undoes their actions, the collection is emptied.
 */

public class Input {

    private int x;
    private int y;
    private SpriteConnector spriteConnector;

    public Input(int x, int y, SpriteConnector spriteConnector) {
        this.x = x;
        this.y = y;
        this.spriteConnector = spriteConnector;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public SpriteConnector getSpriteConnector() {
        return spriteConnector;
    }
}
