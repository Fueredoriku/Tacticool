package com.anything.tacticool.view.util;

public enum SpriteConnectorEnum {
    GRASS("terrain1.png"),
    PLAYER("player.png"),
    BULLET(""),
    EXPLOSION("");

    private final String filePath;

    private SpriteConnectorEnum(final String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
