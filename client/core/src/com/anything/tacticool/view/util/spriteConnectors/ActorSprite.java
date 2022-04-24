package com.anything.tacticool.view.util.spriteConnectors;

import com.anything.tacticool.view.util.ActionPointSingleton;
import com.anything.tacticool.view.util.SpriteConnectorEnum;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ActorSprite extends SpriteConnector {

    Actor actor;
    private ActionPointSingleton ap;

    public ActorSprite(SpriteConnectorEnum spriteConnectorEnum, int x, int y, Actor actor) {
        super(spriteConnectorEnum, x, y);
        ap = ActionPointSingleton.getInstance();
        this.actor = actor;

        actor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ap.addAction(getHighlight());
            }
        });
    }


    @Override
    public Sprite prepareSprite(float tileScale) {
        // do something with the actor
        Texture texture = new Texture(getSpriteConnectorEnum().getFilePath());
        Sprite sprite = new Sprite(texture);
        sprite.setPosition(this.getX()*tileScale,this.getY()*tileScale);
        sprite.setSize(tileScale, tileScale);
        return sprite;
    }
}
