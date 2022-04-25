package please.tacticool.models.Actions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actors.Player;

@SpringBootTest
public class ActionHandlerTest {

    private static ActionHandler actionHandler;
    private static TerrainGrid dummyGrid;
    private static Player dummyPlayer;

    @BeforeAll
    public static void beforeAll(){
        actionHandler = new ActionHandler(42);
        dummyGrid = new TerrainGrid(10, 10);
        actionHandler.setGrid(dummyGrid);
        dummyPlayer = new Player(1, new Coordinate(1,1), 10);
    }

    @Test
    void addPlayerTest(){
        assertTrue(dummyGrid.getTile(dummyPlayer.getPosition()).isEmpty());
        actionHandler.addPlayer(dummyPlayer);
        assertTrue(dummyGrid.getTile(dummyPlayer.getPosition()).getActor().equals(dummyPlayer));

        /*
        assertTrue(actionHandler.getPlayerById(2) == null);
        actionHandler.addNewPlayer(2);
        assertTrue(actionHandler.getPlayerById(2) != null);
        */
    }


    
}
