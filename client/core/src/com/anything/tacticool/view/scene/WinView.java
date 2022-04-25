package com.anything.tacticool.view.scene;

import com.anything.tacticool.view.util.ActorFactory;
import com.anything.tacticool.view.util.SpriteConnectorEnum;
import com.anything.tacticool.view.util.spriteConnectors.SimpleSprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WinView extends Scene{
    private Stage stage;
    private Skin skin;

    private ActorFactory actorFactory;
    private SimpleSprite info;

    public WinView(){
        this.actorFactory = new ActorFactory();
        this.info = new SimpleSprite(SpriteConnectorEnum.WIN, Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
    }

    @Override
    public void prepareScene() {
        super.prepareScene();
        prepareVariables();
    }

    private void prepareVariables() {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.skin.getFont("default-font").getData().setScale(3f);


        TextButton quitButton = actorFactory.textButton(
                new TextButton("Quit", skin),
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        quit();
                    }
                }
        );
        Gdx.input.setInputProcessor(stage);
    }

    private void quit(){
        sm.Pop();
        sm.Pop();
    }

}
