package please.tacticool.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> getBoard(@PathVariable String gameId) {
        // Initalize gameController as object with input gameId
        // Ask if gameController has changed
        // Return a map and moves that both players have made this turn

        return new ResponseEntity<>(gameId, HttpStatus.OK);
    }

    @GetMapping("/api/hasChanged{gameId}")
    public boolean hasChanged(@PathVariable String gameId) {
        // Check if map has changed, this needs to be done EFFICIENT!!
        // return true if map has changed
        // return false if map hasn't changed
        return true;
    }


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
            // Initalize game and input user id with moves
            // Game Controller = new GameController(gameId);
            // gameController.move(pId, utilclasse(body))
            // Check if player is alive before executing
            // Check if moves are legal
            // If legal, then add to list
            // If all players added moves to list, start simulating
            System.out.println(gameId);
            System.out.println(playerId);
            System.out.println(body);
            return new ResponseEntity<>(gameId + " " + playerId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Illegal Moves!!", HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
