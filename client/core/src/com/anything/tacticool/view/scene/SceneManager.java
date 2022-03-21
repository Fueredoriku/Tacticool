package com.anything.tacticool.view.scene;

import java.util.Stack;

public class SceneManager {

    private static Stack<Scene> scenes;

    //Singleton boilerplate begins
    public static volatile SceneManager Singleton;

    private SceneManager(){
        if (Singleton != null){
            throw new RuntimeException("Singleton somehow already created, use SceneManager.getInstance() instead.");
        }
        scenes = new Stack<>();
    }

    public static SceneManager getInstance() {
        if (Singleton == null) {
            synchronized (SceneManager.class) {
                if (Singleton == null) Singleton = new SceneManager();
            }
        }
        return Singleton;
    }
    //Singleton boilerplate ends

    //Stack handling functions
    public void Push(Scene scene){
        scenes.push(scene);
    }

    public Scene Pop(){
        if (scenes.isEmpty()){
            throw new IllegalStateException("Can't pop scene from stack when stack is empty.");
        }
        return scenes.pop();
    }

    public Scene Peek(){
        if (scenes.isEmpty()){
            throw new IllegalStateException("No Scenes to peek at in stack.");
        }
        return scenes.peek();
    }
}
