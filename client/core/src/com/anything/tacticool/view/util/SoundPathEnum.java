package com.anything.tacticool.view.util;

public enum SoundPathEnum {

    PISTOL("audio/pistol.mp3"),
    EXPLOSION("audio/explosion.mp3");

    private final String soundPath;

    private SoundPathEnum(final String soundPath) {
        this.soundPath = soundPath;
    }

    public String getSoundPath() {
        return soundPath;
    }

}
