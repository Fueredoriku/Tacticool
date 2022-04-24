package com.anything.tacticool.view.util;

import com.anything.tacticool.view.util.spriteConnectors.SpriteConnector;

public class ActionPointSingleton {

    private GridElementIterator highlightElements;
    public int actionPoint;

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
        if (highlightElements.isEmpty()) {
            highlightElements.add(spriteConnector);
            actionPoint--;
        }else if (checkDiff(spriteConnector)) {
                highlightElements.add(spriteConnector);
                actionPoint--;
            }
    }

    private boolean checkDiff(SpriteConnector spriteConnector){
        if (highlightElements.getLastSprite().getX()-spriteConnector.getX() <1 && highlightElements.getLastSprite().getX()-spriteConnector.getX() >-1){
            if (highlightElements.getLastSprite().getX()-spriteConnector.getX() <1 && highlightElements.getLastSprite().getX()-spriteConnector.getX() >-1){
                return true;
            }
        }
        return false;
    }

    public void reset(){
        highlightElements.clear();
        actionPoint = 10;
    }

    public GridElementIterator getInputIterator(){
        return highlightElements;
    }

}
