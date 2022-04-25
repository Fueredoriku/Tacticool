package please.tacticool.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import please.tacticool.models.Actions.ActionHandler;
import please.tacticool.persistance.DBController;

@RestController
@RequestMapping("/api")
public class Controller {

    @GetMapping("/")
    public String index() {
        return "Hello World from spring boot!";
    }

    /**
     * An endpoint for login in / registering a player. If the given username - password pair exists return their playerID,
     * otherwise create a new user and return the created ID.
     * @param name      of the user trying to login / register.
     * @param password  of the user trying to login / register.
     * @return          the id of the player + status code OK if login and code CREATED if registered.
     */
    @GetMapping("/getUser/{name}/{password}")
    public ResponseEntity<Integer> getPlayerId(@PathVariable String name,@PathVariable String password){
        int id = new DBController().getPlayerByLogin(name.toLowerCase(),password.toLowerCase());
        if (id < 0 ) {
            return new ResponseEntity<>(new DBController().registerPlayer(name.toLowerCase(), password.toLowerCase()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    /**
     *
     * @param gameId to initalize the correct gameController with gameId
     * @return responseEntity with json object
     * {moveset:{
     *
     * }, playerhp:{
     *     1 : 100, 2 : 100}}
     *
     */
    @GetMapping("/getBoard{gameId}")
    public ResponseEntity<String> getFinishedSimulation(@PathVariable String gameId) {
        // Initalize gameController as object with input gameId
        // Ask if gameController has changed
        // Return a map and moves that both players have made this turn
        ActionHandler ah = new DBController().getGame(Integer.parseInt(gameId));

        return new ResponseEntity<>(ah.getGameState(), HttpStatus.OK);
    }

    /**
     * Gets the value of the turn switch.
     * @param gameId    of game to check.
     * @return          the current turns value.
     */
    @GetMapping("/hasChanged/{gameId}/{currentTurn}")
    public ResponseEntity<Boolean> hasChanged(@PathVariable String gameId, @PathVariable String currentTurn) {
        return new ResponseEntity<>(new DBController().getTurnSwitch(Integer.parseInt(gameId)) != Boolean.parseBoolean(currentTurn), HttpStatus.OK);
    }

    /**
     * An endpoint for joining a game. A player tries to join a game by providing the ID. If the game exists, add the player
     * to the game, otherwise create a new game and add them to that game.
     * @param gameID    id of the game to join, can be a non-existing id.
     * @param playerID  id of the player trying to join a game.
     * @return          the id of the game joined. If joining an existing game the response code will be OK, otherwise CREATED.
     */
    @GetMapping("/joinGame/{gameID}/{playerID}")
    public ResponseEntity<String> joinGame(@PathVariable String gameID, @PathVariable  String playerID) {
        try {
            ActionHandler ac = new DBController().getGame(Integer.parseInt(gameID));
            ac.addNewPlayer(Integer.parseInt(playerID));
            return new ResponseEntity<>(String.valueOf(ac.getGameID()), HttpStatus.OK);
        }catch (Exception e){
            ActionHandler ac = ActionHandler.createGame();
            ac.addNewPlayer(Integer.parseInt(playerID));
            return new ResponseEntity<>(String.valueOf(ac.getGameID()), HttpStatus.CREATED);
        }
    }

    /**
     * Main request function for taking in moves from players
     * @param body Input body that will be the move list from the client
     * @param gameId The main way for the player to identify him/herself
     * @param playerId The way for the server to know which game the player is playing
     * @return Mainly a responsecode meant to signify OK if the input is seen as legal and I AM A TEAPOT if the move list contains illegal moves.
     */
    @PostMapping("/move/{gameId}/{playerId}")
    public ResponseEntity<String> inputMove(@RequestBody String body, @PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId) {
        try {
            DBController db = new DBController();
            ActionHandler actionHandler = db.getGame(Integer.parseInt(gameId));
            actionHandler.addActions(actionHandler.getPlayerById(Integer.parseInt(playerId)), body, true);

            // Simulate if all players have added.
            actionHandler.simulate();
            return new ResponseEntity<>(gameId + " " + playerId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Illegal Moves!!", HttpStatus.BAD_REQUEST);
        }
    }
}
