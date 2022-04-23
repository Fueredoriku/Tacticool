package please.tacticool.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import please.tacticool.GameBalance;
import please.tacticool.models.Actions.Action;
import please.tacticool.models.Actions.ActionHandler;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.persistance.DBController;

import java.util.UUID;

@RestController
public class Controller {

    @GetMapping("/")
    public String index() {
        return "Hello World from spring boot!";
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
    @GetMapping("/api/getBoard{gameId}")
    public ResponseEntity<String> getFinishedSimulation(@PathVariable String gameId) {
        // Initalize gameController as object with input gameId
        // Ask if gameController has changed
        // Return a map and moves that both players have made this turn
        ActionHandler ah = new DBController().getGame(Integer.parseInt(gameId));

        return new ResponseEntity<>(ah.getGameState(), HttpStatus.OK);
    }

    @GetMapping("/api/hasChanged{gameId}")
    public boolean hasChanged(@PathVariable String gameId) {
        // Check if map has changed, this needs to be done EFFICIENT!!
        // return true if map has changed
        // return false if map hasn't changed
        return true;
    }


    @GetMapping("/api/joinGame/{gameID}/{playerID}")
    public ResponseEntity<String> joinGame(@PathVariable String gameID, @PathVariable  String playerID) {
        try {
            ActionHandler ac = new DBController().getGame(Integer.parseInt(gameID));
            ac.addNewPlayer(Integer.parseInt(playerID));
            System.out.println("GOT HERE MOFO");
            return new ResponseEntity<>(String.valueOf(ac.getGameID()), HttpStatus.OK);
        }catch (Exception e){
            ActionHandler ac = ActionHandler.createGame();
            ac.addNewPlayer(Integer.parseInt(playerID));
            System.out.println("NOPE; WE GOT HERE MILF");
            return new ResponseEntity<>(String.valueOf(ac.getGameID()), HttpStatus.CREATED);
        }
    }

    //TODO: Register player

    /**
     * Main request function for taking in moves from players
     * @param body Input body that will be the move list from the client
     * @param gameId The main way for the player to identify him/herself
     * @param playerId The way for the server to know which game the player is playing
     * @return Mainly a responsecode meant to signify OK if the input is seen as legal and I AM A TEAPOT if the move list contains illegal moves.
     */
    @PostMapping("/api/move/{gameId}/{playerId}")
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
