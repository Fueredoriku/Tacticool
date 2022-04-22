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

    /**
     * The SceneManager uses a combination of a State and memento pattern.
     *
     * The State pattern is not implemented with an interface, but rather by delegating behaviour to the currently selected state object.
     * In this implementation a Scene represents a state object, and the scene at top of the stack is the selected state object.
     * This implementation allows for new states being created at runtime in the form of scenes with Push(new scene()).
     *
     * The memento pattern is implemented as the Stack of scenes named "scenes", which makes SceneManager act as a proxy for storing objects representing previous states.
     * As new scenes are pushed to the top of the Stack, the old scenes will not render or perform any actions.
     * As the client leaves the current scene by calling Pop(), the previous scene will automatically become active, thus achieving memento.
     */

    //Stack handling functions
    public void Push(Scene scene){
        scenes.push(scene);
        scenes.peek().prepareStage();
    }

    public Scene Pop(){
        if (scenes.isEmpty()){
            throw new IllegalStateException("Can't pop scene from stack when stack is empty.");
        }
        Scene poppedScene = scenes.pop();
        if (!scenes.isEmpty()) {
            scenes.peek().prepareStage();
        }
        return poppedScene;
    }

    public Scene Peek(){
        if (scenes.isEmpty()){
            throw new IllegalStateException("No Scenes to peek at in stack.");
        }
        return scenes.peek();
    }
}
