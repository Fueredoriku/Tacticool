package com.anything.tacticool.view.util;

public enum SongPathEnum {

    ORCHESTRAL("audio/game_orchestral.ogg"),
    TENSE("audio/game_tense.ogg");

    private final String songPath;

    private SongPathEnum(final String songPath) {
        this.songPath = songPath;
    }

    public String getSongPath() {
        return songPath;
    }

}
