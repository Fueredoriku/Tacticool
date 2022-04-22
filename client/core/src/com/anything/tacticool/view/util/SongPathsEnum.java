package com.anything.tacticool.view.util;

public enum SongPathsEnum {

    ORCHESTRAL("audio/game_orchestral.ogg"),
    TENSE("audio/game_tense.ogg");

    private final String songPath;

    private SongPathsEnum(final String songPath) {
        this.songPath = songPath;
    }

    public String getSongPath() {
        return songPath;
    }

}
