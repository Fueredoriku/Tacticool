package com.anything.tacticool.view.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioController {

    private static Music music;
    private static float musicVolume = 1f;
    private static String current_musicPath;
    public static String game_musicPath = SongPathEnum.TENSE.getSongPath();

    private static float soundVolume = 1f;


    //Singleton boilerplate begins
    public static volatile AudioController Singleton;

    private AudioController() {
        if (Singleton != null) {
            throw new RuntimeException("Singleton somehow already created, use SettingChoices.getInstance instead");
        }
    }

    public static AudioController getInstance() {
        if (Singleton == null) {
            synchronized (AudioController.class) {
                if (Singleton == null) Singleton = new AudioController();
            }
        }
        return Singleton;
    }
    //Singleton boilerplate ends


     public static void setCurrent_musicPath(String path) {
        AudioController.current_musicPath = path;
     }

     public static void setGame_musicPath(String path) {
        AudioController.game_musicPath = path;
     }

     public static float getMusicVolume() {
        return AudioController.musicVolume;
     }

    public static float getSoundVolume() {
        return AudioController.soundVolume;
    }

    public static void setMusicVolume(float volume) {
        AudioController.musicVolume = volume;
    }

    public static void setSoundVolume(float volume) {
        AudioController.soundVolume = volume;
    }

     public static void playMusic() {
         music = Gdx.audio.newMusic(Gdx.files.internal(AudioController.current_musicPath));
         music.setLooping(true);
         music.setVolume(AudioController.musicVolume);
         music.play();
     }

     public static void endMusic() {
         music.stop();
         music.dispose();
     }

     public static Sound prepareSound(SoundPathEnum soundPathEnum) {
        return Gdx.audio.newSound(Gdx.files.internal(soundPathEnum.getSoundPath()));
     }

     public static void playSound(Sound sound) {
        sound.play(soundVolume);
     }

     public static void endSounds(Sound[] sounds) {
         for (Sound sound : sounds) {
             sound.stop();
             sound.dispose();
         }
     }
}
