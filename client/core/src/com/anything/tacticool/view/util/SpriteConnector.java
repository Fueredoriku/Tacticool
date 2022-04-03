package com.anything.tacticool.view.util;

public enum SpriteConnector {
    GRASS("G", "terrain1.png", 0),
    PLAYER("P", "player.png", 1),
    BULLET("B", "", 2),
    EXPLOSION("E", "", 2);

    private final String type;
    private final String path;
    private final int priority;

    private SpriteConnector(final String type, final String path, final int priority) {
        this.type = type;
        this.path = path;
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public int getPriority() {
        return priority;
    }

}
