package com.anything.tacticool.view.util;

import com.anything.tacticool.model.ActionType;
import com.anything.tacticool.model.InputAction;
import com.anything.tacticool.view.util.spriteConnectors.SpriteConnector;

import java.util.ArrayList;

public class ActionPointSingleton {

    private static SpriteConnectorEnum currentActionTypeEnum;
    private static ActionType currentActionType;
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
        currentActionTypeEnum = SpriteConnectorEnum.HIGHLIGHTTILE;
        currentActionType = ActionType.MOVE;
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
            System.out.println(currentActionType);
            inputs.add(new InputAction(currentActionType, spriteConnector.getX()-highlightElements.getLastMoveSprite().getX(), spriteConnector.getY()-highlightElements.getLastMoveSprite().getY()));
            System.out.println(spriteConnector.getSpriteConnectorEnum());
            spriteConnector.setHighlightEnum(ActionPointSingleton.currentActionTypeEnum);
            highlightElements.add(spriteConnector);
            actionPoint--;
            System.out.println(inputs);
            }
    }

    public static void setCurrentActionType(SpriteConnectorEnum actionTypeEnum, ActionType actionType) {
        ActionPointSingleton.currentActionTypeEnum = actionTypeEnum;
        ActionPointSingleton.currentActionType = actionType;
    }

    public SpriteConnectorEnum getCurrentActionType() {
        return currentActionTypeEnum;
    }

    private boolean checkDiff(SpriteConnector spriteConnector){
        if (highlightElements.getLastMoveSprite().getX()-spriteConnector.getX() == 1 || highlightElements.getLastMoveSprite().getX()-spriteConnector.getX() == -1){
            if (highlightElements.getLastMoveSprite().getY()-spriteConnector.getY() == 0) {
                return true;
            }
        }
        if (highlightElements.getLastMoveSprite().getY()-spriteConnector.getY() == 1 || highlightElements.getLastMoveSprite().getY()-spriteConnector.getY() == -1){
            if (highlightElements.getLastMoveSprite().getX()-spriteConnector.getX() == 0) {
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
