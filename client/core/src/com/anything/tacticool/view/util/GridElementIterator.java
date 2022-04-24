package com.anything.tacticool.view.util;

import com.anything.tacticool.view.util.spriteConnectors.SpriteConnector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GridElementIterator implements Iterator{

    private ArrayList<SpriteConnector> spriteConnectors;
    private int index;

    public GridElementIterator(String[] args) {
        //TODO implement constructor
    }

    public GridElementIterator() {
        spriteConnectors = new ArrayList<>();
        index = 0;
    }

    @Override
    public boolean hasNext() {
        if (index < spriteConnectors.size()-1) {
            return true;
        }
        return false;
    }

    @Override
    public SpriteConnector next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Iteration already over, can't call next()");
        }
        index ++;
        return spriteConnectors.get(index);
    }

    public SpriteConnector getLastSprite(){
        return spriteConnectors.get(spriteConnectors.size()-1);
    }

    public boolean isEmpty(){
        return spriteConnectors.size()==0;
    }

    public void add(SpriteConnector sprite){
        spriteConnectors.add(sprite);
    }

    @Override
    public void remove() {
        spriteConnectors.remove(index);
    }

    public void removeLast(){
        spriteConnectors.remove(spriteConnectors.size());
    }

    public void reset() {
        this.index = 0;
    }

    public void clear(){
        spriteConnectors.clear();
    }

}
