package com.anything.tacticool.view.scene;

import com.anything.tacticool.model.ActionType;
import com.anything.tacticool.model.Grid;
import com.anything.tacticool.model.InputAction;
import com.anything.tacticool.model.Player;
import com.anything.tacticool.view.util.ActionPointSingleton;
import com.anything.tacticool.view.util.ActorFactory;
import com.anything.tacticool.view.util.AudioController;
import com.anything.tacticool.view.util.GridElementIterator;
import com.anything.tacticool.view.util.spriteConnectors.ActorSprite;
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
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import httpRequests.Request;
import httpRequests.Serializer;


public class GameView extends Scene {

    private boolean isWaiting;

    private GridElementIterator tileIterator;
    private GridElementIterator playerIterator;
    private ActionPointSingleton ap;

    private Stage stage;
    private Texture apHUD;
    private Sprite apSprite;
    private int playerID;

    private TextureHandler textureHandler;
    private BitmapFont font;
    private Skin skin;

    private float uiWidth;
    private float uiHeight;

    private Request request;
    private ArrayList<InputAction> inputs;
    private List<Player> players;
    private Grid grid;
    private int gameID;
    private Player mainPlayer;

    private ActorFactory actorFactory;
    private Label waiting_Label;

    public GameView(int playerID, int gameID){
        super();
        this.isWaiting = false;

        this.playerID = playerID;
        this.gameID = gameID;
        request = new Request();

        // Instantiate grid
        try{
            grid = request.getGameState(gameID);
        }
        catch (IOException e){
            System.out.println(e);
        }

        tileIterator = new GridElementIterator();
        playerIterator = new GridElementIterator();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        //skin.getFont("default-font").getData().setScale(3f);

        this.inputs = new ArrayList<>();
        actorFactory = new ActorFactory();

        textureHandler = new TextureHandler(grid.getWidth(), grid.getHeigth());
        ap = ActionPointSingleton.getInstance();
        apHUD = new Texture("aphud.png");
        apSprite = new Sprite(apHUD);
        font = new BitmapFont();
        uiWidth = Gdx.graphics.getWidth()/6f;
        uiHeight = Gdx.graphics.getHeight()/12f;

        constructBoard(grid.getWidth(), grid.getHeigth());

        // Find the character the player controls
        for (Player player : grid.getPlayers()) {
            if (player.getPlayerID() == playerID) {
                mainPlayer = player;
            }
        }
    }

    public void constructBoard(int width, int height){
        float tileScale;
        if (Gdx.graphics.getWidth() / width < Gdx.graphics.getHeight() / height) {
            tileScale = (Gdx.graphics.getWidth() / width);
        } else {
            tileScale = (Gdx.graphics.getHeight() / height);
        }
        for (int i = 0; i < grid.getBoard().length; i++){
            Actor newActor = new Actor();
            tileIterator.add(SpriteConnectorFactory.createActorSpriteWithHiglight(
                    SpriteConnectorEnum.GRASS, ap.getCurrentActionType(),
                    i%width, height - 1 - (int)Math.floor(i/width), newActor, tileScale));
        }

        players = grid.getPlayers();
        for (int i = 0; i < players.size(); i++){
            SpriteConnector newPlayer = new SimpleSprite(SpriteConnectorEnum.PLAYER, players.get(i).getCurrentX(), players.get(i).getCurrentY());
            players.get(i).setTexture(newPlayer);
            playerIterator.add(newPlayer);
        }
    }

    @Override
    public void prepareScene(){
        stage = new Stage(new ScreenViewport());
        buildButtons();
        prepareSound();

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

    private void prepareSound() {
        AudioController.playGameMusic();
    }

    @Override
    public void disposeEarly() {
        AudioController.endMusic();
    }


    private void buildButtons(){
        TextButton submit_button = actorFactory.textButton(
                new TextButton("Submit", skin),
                uiWidth, uiHeight,Gdx.graphics.getWidth() - uiWidth*1.1f, Gdx.graphics.getHeight() - uiHeight*1.1f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(ap.inputs);
                        try {
                            post();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        TextButton reset_input_button = actorFactory.textButton(
                new TextButton("Undo", skin),
                uiWidth, uiHeight,Gdx.graphics.getWidth() - uiWidth*1.1f, Gdx.graphics.getHeight() - 2*uiHeight*1.1f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        undoInputs();
                    }
                }
        );

        TextButton settings_Button = actorFactory.textButton(
            new TextButton("Settings", skin),
                uiWidth, uiHeight,Gdx.graphics.getWidth() - uiWidth*1.1f, Gdx.graphics.getHeight() - 10*uiHeight*1.1f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        sm.Push(new Settings());
                    }
                }
        );

        TextButton move_Button = actorFactory.textButton(
                new TextButton("Move", skin),
                uiWidth/2, uiHeight, Gdx.graphics.getWidth() - uiWidth*1.1f, Gdx.graphics.getHeight() - 3*uiHeight*1.1f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        setActionType(SpriteConnectorEnum.HIGHLIGHTTILE, ActionType.MOVE);
                    }
                }
        );

        TextButton rifle_Button = actorFactory.textButton(
                new TextButton("Rifle", skin),
                uiWidth/2, uiHeight, Gdx.graphics.getWidth() - uiWidth*1.1f/2f, Gdx.graphics.getHeight() - 3*uiHeight*1.1f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        setActionType(SpriteConnectorEnum.HIGHLIGHTWEAPON, ActionType.RIFLE);
                    }
                }
        );

        waiting_Label = (Label) actorFactory.label(
                "Waiting for other players", skin, uiWidth, uiHeight, Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, true
        );

        waiting_Label.setVisible(false);

        actorFactory.stageActors(stage, new Actor[]{
                waiting_Label, submit_button, reset_input_button, settings_Button, move_Button, rifle_Button
        });
        Gdx.input.setInputProcessor(stage);
    }

    private void setActionType(SpriteConnectorEnum actionTypeEnum, ActionType actionType) {
        ActionPointSingleton.setCurrentActionType(actionTypeEnum, actionType);
    }

    private void post() throws IOException{
        request.postMoves(new Serializer().serializeActions(ap.inputs), gameID, playerID);
        this.isWaiting = true;
        for (Actor actor : stage.getActors()) {
            actor.setVisible(false);
        }
        waiting_Label.setVisible(true);
    }

    private void undoInputs() {
        ap.inputs.clear();
        ap.reset();
        inputs.clear();
        isWaiting = false;
    }

    private void addFirstInput() {
        if (ap.inputs.size() == 0) {
            ap.addAction(new SimpleSprite(SpriteConnectorEnum.HIGHLIGHTTILE, grid.getPlayer(playerID).getCurrentX(), grid.getPlayer(playerID).getCurrentY()));
        }
    }

    private void drawHUD(SpriteBatch batch){
        batch.draw(apSprite, 0, Gdx.graphics.getHeight()-10);
        font.draw(batch, ""+ap.actionPoint, 10, Gdx.graphics.getHeight()-10);
        font.draw(batch, "Health: " + grid.getPlayer(playerID).getHealthPoint(), Gdx.graphics.getWidth() - uiWidth*1.1f, Gdx.graphics.getHeight() - 4f*uiHeight*1.1f);
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
        inputs = ap.getInputs();
    }

    public void updatePlayer(Player player){
        Player oldPlayer = grid.getPlayer(player.getPlayerID());

        while (player.getActions().size() > 0){
            oldPlayer.addAction(player.getActions().get(0));

            oldPlayer.setCurrentPos(player.getActions().get(0).getTargetX(), player.getActions().get(0).getTargetY());
            oldPlayer.getTexture().updatePos(player.getCurrentX(), player.getCurrentY());
            player.getActions().remove(0);
            oldPlayer.getActions().remove(0);
        }
        undoInputs();
    }

    @Override
    public void onRender(SpriteBatch batch){
        if (!isWaiting) {
            addFirstInput();

            textureHandler.createBatch(tileIterator, batch);
            textureHandler.createBatch(ap.getInputIterator(), batch);
            textureHandler.createBatch(playerIterator, batch);
            stage.act();
            drawHUD(batch);
        } else {
            //waiting_Label.setVisible(true);
            pollForUpdates();
        }
        stage.draw();
    }

    private void pollForUpdates() {
        try {
            if (isWaiting && request.getHasChanged(gameID, grid.getTurn())) {
                Grid newGrid = request.getGameState(gameID);
                if (newGrid.isGameWon()){
                    gameOver(newGrid.getWinningPlayer().getPlayerID());
                }
                for (Player newPlayer : newGrid.getPlayers()) {
                    updatePlayer(newPlayer);
                }
                this.grid.setTurn(newGrid.getTurn());
                this.isWaiting = false;
                for (Actor actor : stage.getActors()) {
                    actor.setVisible(true);
                } waiting_Label.setVisible(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gameOver(int winnerID) {
        if (winnerID == playerID) {
            sm.Push(new WinView());
        } else {
            sm.Push(new LoseView());
        }
    }
}
