package com.anything.tacticool.view.util;

import com.anything.tacticool.model.ActionType;
import com.anything.tacticool.model.InputAction;
import com.anything.tacticool.view.util.spriteConnectors.SpriteConnector;

import java.util.ArrayList;

public class ActionPointSingleton {

    private GridElementIterator highlightElements;
    public int actionPoint;
    public ArrayList<InputAction> inputs;

    //Singleton boilerplate begins
    public static volatile ActionPointSingleton Singleton;

    private ActionPointSingleton(){
        if (Singleton != null){
            throw new RuntimeException("Singleton somehow already created, use ActionPointSingleton.getInstance() instead.");
        }
        highlightElements = new GridElementIterator();
        inputs = new ArrayList<>();
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
        }else if (checkDiff(spriteConnector)) {
            System.out.println(spriteConnector.getX() + " " + spriteConnector.getY());
            System.out.println(spriteConnector.getX()-highlightElements.getLastSprite().getX());
            inputs.add(new InputAction(ActionType.MOVE, spriteConnector.getX()-highlightElements.getLastSprite().getX(), spriteConnector.getY()-highlightElements.getLastSprite().getY()));
            highlightElements.add(spriteConnector);
            actionPoint--;
            System.out.println(inputs);
            }
    }

    private boolean checkDiff(SpriteConnector spriteConnector){
        if (highlightElements.getLastSprite().getX()-spriteConnector.getX() == 1 || highlightElements.getLastSprite().getX()-spriteConnector.getX() == -1){
            if (highlightElements.getLastSprite().getY()-spriteConnector.getY() == 0) {
                return true;
            }
        }
        if (highlightElements.getLastSprite().getY()-spriteConnector.getY() == 1 || highlightElements.getLastSprite().getY()-spriteConnector.getY() == -1){
            if (highlightElements.getLastSprite().getX()-spriteConnector.getX() == 0) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<InputAction> getInputs(){
        return inputs;
    }

    public void reset(){
        highlightElements.clear();
        actionPoint = 10;
    }

    public GridElementIterator getInputIterator(){
        return highlightElements;
    }

}
