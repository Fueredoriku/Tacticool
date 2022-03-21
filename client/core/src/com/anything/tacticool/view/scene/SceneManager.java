package com.anything.tacticool.view.scene;

import java.util.Stack;

public class SceneManager {
    private Stack<Scene> scenes;

    public SceneManager(){
        scenes = new Stack<>();
    }

    public void push(Scene scene){
        scenes.pop();
        scenes.push(scene);
    }

}
