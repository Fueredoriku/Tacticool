package com.anything.tacticool.view.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class GridElementIterator implements Iterator{

    private SpriteConnector[] spriteConnectors;
    private int index;

    public GridElementIterator(String[] args) {
        //TODO implement constructor
    }


    @Override
    public boolean hasNext() {
        if (index < spriteConnectors.length) {
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
        return spriteConnectors[index];
    }

    @Override
    public void remove() {
        // This needs to be overridden to avoid compilation errors, but we don't use it, so we don't need to define behaviour
    }

    public void reset() {
        this.index = 0;
    }

}
