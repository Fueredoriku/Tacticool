package com.anything.tacticool.model;

import com.anything.tacticool.view.util.SpriteConnector;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int playerID;
    private int healthPoint;
    private List<InputAction> actions;
    private int posX;
    private int posY;
    private SpriteConnector texture;

    public Player(int playerID, int healthPoint, int posX, int posY) {
        this.playerID = playerID;
        this.healthPoint = healthPoint;
        this.posX = posX;
        this.posY = posY;
        this.actions = new ArrayList<>();
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getHealthPoint() {
        return healthPoint;
    }
    
    public void addAction(InputAction action) {
        actions.add(action);
    }

    public List<InputAction> getActions() {
        return actions;
    }

    public int getCurrentX(){
        return posX;
    }

    public int getCurrentY(){
        return posY;
    }

    //TODO: Remove this after factory has been made:


    public void setTexture(SpriteConnector texture) {
        this.texture = texture;
    }
    //TODO: Remove this after factory has been made:
    public SpriteConnector getTexture() {
        return texture;
    }

    public void setCurrentPos(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nHP: %d\nActions: %s\n", playerID, healthPoint, actions);
    }
}
