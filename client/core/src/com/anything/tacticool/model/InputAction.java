package com.anything.tacticool.model;

public class InputAction {
    private ActionType actionType;
    private int targetX;
    private int targetY;

    public InputAction(ActionType actionType, int targetX, int targetY) {
        this.actionType = actionType;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    @Override
    public String toString() {
        return String.format("Type: %s, X: %d, Y: %d", actionType, targetX, targetY);
    }
}
