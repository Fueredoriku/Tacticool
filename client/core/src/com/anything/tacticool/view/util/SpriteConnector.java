package com.anything.tacticool.view.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SpriteConnector {

    private SpriteConnectorEnum spriteConnectorEnum;
    private SpriteConnectorEnum highlightEnum;
    private ActionPointSingleton ap;
    private int x;
    private int y;
    private Actor actor;

    public SpriteConnector(SpriteConnectorEnum spriteConnectorEnum, int x, int y) {
        this.spriteConnectorEnum = spriteConnectorEnum;
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

    private SpriteConnector getHighlight(){
        return new SpriteConnector(this.highlightEnum, this.x, this.y);
    }

    public SpriteConnectorEnum getSpriteConnectorEnum() {
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

    public Sprite prepareSprite(float tileScale) {
        Texture texture = new Texture(spriteConnectorEnum.getFilePath());
        Sprite sprite = new Sprite(texture);
        sprite.setSize(tileScale, tileScale);
        return sprite;
    }
}
