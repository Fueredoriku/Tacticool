package com.anything.tacticool.view.scene;

import com.anything.tacticool.model.ActionType;
import com.anything.tacticool.model.Grid;
import com.anything.tacticool.model.InputAction;
import com.anything.tacticool.model.Player;
import com.anything.tacticool.view.util.ActionPointSingleton;
import com.anything.tacticool.view.util.ActorFactory;
import com.anything.tacticool.view.util.GridElementIterator;
import com.anything.tacticool.view.util.spriteConnectors.SimpleSprite;
import com.anything.tacticool.view.util.spriteConnectors.SpriteConnector;
import com.anything.tacticool.view.util.SpriteConnectorEnum;
import com.anything.tacticool.view.util.TextureHandler;
import com.anything.tacticool.view.util.spriteConnectors.SpriteConnectorFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import httpRequests.Request;


public class GameView extends Scene {

    private GridElementIterator tileIterator;
    private GridElementIterator actorIterator;
    private ActionPointSingleton ap;
    private int width;
    private int height;

    private Stage stage;
    private Texture apHUD;
    private Sprite apSprite;

    private TextureHandler textureHandler;
    private BitmapFont font;
    private Skin skin;

    private TextButton resetButton;
    private TextButton submitButton;
    private float uiWidth;
    private float uiHeight;

    private Request request;
    private ArrayList<InputAction> inputs;
    private List<Player> players;
    private Grid grid;
    private long gameID = 2;
    private Player mainPlayer;

    public GameView(){
        super();
        request = new Request();/*
        try{
            grid = request.getGameState(gameID);
        }
        catch (IOException e){
            System.out.println(e);
        }*/

        //temporary for test
        tileIterator = new GridElementIterator();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.getFont("default-font").getData().setScale(3f);
        grid = new Grid("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1",5,5);
        mainPlayer = new Player(513,3,4,4);
        Player enemy = new Player(543,3,2,2);
        players = new ArrayList<>();
        players.add(mainPlayer);
        players.add(enemy);
        mainPlayer.addAction(new InputAction(ActionType.MOVE, 3,3));
        grid.setPlayers(players);


        constructBoard(grid.getWidth(), grid.getHeigth());
        textureHandler = new TextureHandler(grid.getWidth(), grid.getHeigth());
        ap = ActionPointSingleton.getInstance();
        apHUD = new Texture("aphud.png");
        apSprite = new Sprite(apHUD);
        font = new BitmapFont();
        uiWidth = Gdx.graphics.getWidth()/3f;
        uiHeight = Gdx.graphics.getHeight()/6f;

        prepareScene();


    }

    public void constructBoard(int width, int height){
        for (int i = 0; i < grid.getBoard().length; i++){
            Actor newActor = new Actor();
            tileIterator.add(SpriteConnectorFactory.createActorSpriteWithHiglight(
                    SpriteConnectorEnum.GRASS, SpriteConnectorEnum.HIGHLIGHTTILE,
                    i%width, (int)Math.floor(i/width), newActor));
        }

        players = grid.getPlayers();
        for (int i = 0; i < players.size(); i++){
            SpriteConnector newPlayer = new SimpleSprite(SpriteConnectorEnum.PLAYER, players.get(i).getCurrentX(), players.get(i).getCurrentY());
            players.get(i).setTexture(newPlayer);
            tileIterator.add(newPlayer);

        }
    }

    @Override
    public void prepareScene(){
        stage = new Stage(new ScreenViewport());
        buildButtons();
        while (tileIterator.hasNext()){
            try {
                stage.addActor(tileIterator.next().getActor());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tileIterator.reset();

        Gdx.input.setInputProcessor(stage);
    }


    private void buildButtons(){

        resetButton = new TextButton("Reset Moves", skin);
        submitButton = new TextButton("Submit Moves", skin);
        resetButton.setSize(uiWidth, uiHeight);
        submitButton.setSize(uiWidth, uiHeight);
        resetButton.setPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()-10);
        submitButton.setPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()-30);
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ap.reset();
            }
        });

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: Submit moves to controller
                constructActionList();
                //request.poster(inputs);
            }
        });



        stage.addActor(resetButton);
    }

    private void drawHUD(SpriteBatch batch){
        batch.draw(apSprite, 0, Gdx.graphics.getHeight()-10);
        font.draw(batch, ""+ap.actionPoint, 10, Gdx.graphics.getHeight()-10);
    }

    private void updatePlayers(){
        for (Player player : players){
            if (player.getActions().size() > 0){
                updatePlayer(player);
            }
        }
    }

    private void constructActionList(){
        inputs.clear();
        while (ap.getInputIterator().hasNext()) {
            InputAction action = new InputAction(ActionType.MOVE, ap.getInputIterator().next().getX(), ap.getInputIterator().next().getY());
            inputs.add(action);
        }
    }

    public void updatePlayer(Player player){
        while (player.getActions().size() > 0){
            player.setCurrentPos(player.getCurrentX() + player.getActions().get(0).getTargetX(), player.getCurrentY() + player.getActions().get(0).getTargetY());
            player.getTexture().updatePos(player.getCurrentX(), player.getCurrentY());
            player.getActions().remove(0);
        }
    }

    @Override
    public void onRender(SpriteBatch batch){
        updatePlayers();
        textureHandler.createBatch(tileIterator, batch);
        //textureHandler.createBatch(actorIterator, batch);
        textureHandler.createBatch(ap.getInputIterator(), batch);
        drawHUD(batch);

    }
}
