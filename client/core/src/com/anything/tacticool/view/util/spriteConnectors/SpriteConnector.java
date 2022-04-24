package com.anything.tacticool.view.util.spriteConnectors;

import com.anything.tacticool.view.util.ActionPointSingleton;
import com.anything.tacticool.view.util.SpriteConnectorEnum;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public abstract class SpriteConnector {

    private SpriteConnectorEnum spriteConnectorEnum;
    private SpriteConnectorEnum highlightEnum;
    private ActionPointSingleton ap;
    private int x;
    private int y;
    private Actor actor;

    public SpriteConnector(SpriteConnectorEnum spriteConnectorEnum, int x, int y) {
        this.spriteConnectorEnum = spriteConnectorEnum;
        this.highlightEnum = null;
        this.x = x;
        this.y = y;
    }

    public SpriteConnector(SpriteConnectorEnum spriteConnectorEnum, SpriteConnectorEnum highlightSprite, int x, int y) {
        this.spriteConnectorEnum = spriteConnectorEnum;
        this.highlightEnum = highlightSprite;
        this.x = x;
        this.y = y;
        this.actor = new Actor();
        ap = ActionPointSingleton.getInstance();
        actor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ap.addAction(getHighlight());
            }
        });
    }

    public SpriteConnector getHighlight(){
        if(highlightEnum != null){
            return new SimpleSprite(this.highlightEnum, this.x, this.y);
        }  else {
            return null;
        }
    }

    public SpriteConnectorEnum getSpriteConnectorEnum(){
        return spriteConnectorEnum;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Actor getActor() throws Exception{
        if (actor == null){
            throw new Exception("The sprite has no actor tied to it!");
        }
        return actor;
    }

    /**
     * Currently loads each texture seperately from memory each time this is called.
     * Should be changed to use TextureRegion to avoid this.
     */
    public abstract Sprite prepareSprite(float tileScale);
}
