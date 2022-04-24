package com.anything.tacticool.view.util.spriteConnectors;

import com.anything.tacticool.view.util.SpriteConnectorEnum;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpriteConnectorFactory {

    public static SpriteConnector createSimpleSprite(SpriteConnectorEnum spriteConnectorEnum, int x, int y){
        return new SimpleSprite(spriteConnectorEnum, x, y);
    }

    public static SpriteConnector createActorSprite(SpriteConnectorEnum spriteConnectorEnum, int x, int y, Actor actor){
        return new ActorSprite(spriteConnectorEnum, x, y, actor);
    }

    public static SpriteConnector createAnimatedSprite(SpriteConnectorEnum spriteConnectorEnum, int x, int y){
        return new AnimatedSprite(spriteConnectorEnum, x, y);
    }
}
