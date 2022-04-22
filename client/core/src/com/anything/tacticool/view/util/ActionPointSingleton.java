package com.anything.tacticool.view.util;

import com.anything.tacticool.view.scene.SceneManager;

import java.util.Stack;

public class ActionPointSingleton {

    private GridElementIterator highlightElements;
    private int actionPoint;

    //Singleton boilerplate begins
    public static volatile ActionPointSingleton Singleton;

    private ActionPointSingleton(){
        if (Singleton != null){
            throw new RuntimeException("Singleton somehow already created, use ActionPointSingleton.getInstance() instead.");
        }
        highlightElements = new GridElementIterator();
        actionPoint = 10;
    }

    public static ActionPointSingleton getInstance() {
        if (Singleton == null) {
            synchronized (ActionPointSingleton.class) {
                if (Singleton == null) Singleton = new ActionPointSingleton();
            }
        }
        return Singleton;
    }
    //Singleton boilerplate ends

    public void addAction(SpriteConnector spriteConnector){
        highlightElements.add(spriteConnector);
        actionPoint--;
    }

    public void reset(){
        highlightElements.clear();
        actionPoint = 10;
    }

    public GridElementIterator getInputIterator(){
        return highlightElements;
    }


}
