package com.anything.tacticool.view.util;

public enum SpriteConnector {
    GRASS("terrain1.png", 0),
    PLAYER("player.png", 1),
    BULLET("", 2),
    EXPLOSION("", 2);

    private final String filePath;
    private final int layer;

    private SpriteConnector(final String filePath, final int layer) {
        this.filePath = filePath;
        this.layer = layer;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getLayer() {
        return layer;
    }

}
