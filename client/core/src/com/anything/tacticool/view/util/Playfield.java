package com.anything.tacticool.view.util;

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

}
