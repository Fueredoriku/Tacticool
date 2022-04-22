package com.anything.tacticool.view.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioController {

    private Music music;

     public void setMusic(String filePath) {
         music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
         music.setLooping(true);
     }

     public void playMusic() {
         music.play();
     }

     public void endMusic() {
         music.stop();
         music.dispose();
     }

    public void setMusicVolume(float v) {
         music.setVolume(v);
    }
}
