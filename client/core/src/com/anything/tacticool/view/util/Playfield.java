package com.anything.tacticool.view.util;

import java.util.Iterator;
import java.util.List;

public class Playfield {

    /**
     *  Method renderBoard in TextureHandler assumes that spriteConnectors in SpriteConnector[] are sorted based on priority (lowest first)
     */

    private List<List<SpriteConnector[]>> grid;

    public Playfield(String[] args) {
        //TODO implement constructor
    }

    public List<List<SpriteConnector[]>> getGrid() {
        return grid;
    }

    /**
     * Proposed method to be called when updating the internal gamestate with new information from the server.
     * One call of this method should update the internal model with ONE step from the changeLog given by the server.
     * After each time this is called from the GameView we should call TextureHandler.renderBoard from the GameView.
     * Handling updates this way simplifies the process of rendering the actions step by step.
     *
     * @param changeLog is the log of changes given by the server. I propose that this is an iterator, allowing us to parse through it step by step, since we can't use loops
     * @return Should return a boolean to tell GameView when we have made the final change in the changeLog.
     */

    public boolean updateGrid(Iterator changeLog) {
        if (changeLog.hasNext()) {
            //TODO Make change to grid using changeLog.next()
            return true;
        }
        return false;
    }

}
