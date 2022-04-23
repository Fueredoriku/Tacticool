package com.anything.tacticool.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int playerID;
    private int healthPoint;
    private List<Action> actions;

    public Player(int playerID, int healthPoint) {
        this.playerID = playerID;
        this.healthPoint = healthPoint;
        this.actions = new ArrayList<>();
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getHealthPoint() {
        return healthPoint;
    }
    
    public void addAction(Action action) {
        actions.add(action);
    }

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nHP: %d\nActions: %s\n", playerID, healthPoint, actions);
    }
}
