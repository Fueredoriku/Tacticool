package please.tacticool.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import please.tacticool.models.Actors.Actor;
import please.tacticool.models.Actors.Obstacle;
import please.tacticool.models.Actors.Player;

@SpringBootTest
public class TerrainGridTest {

    private static TerrainGrid grid;
    private static Player dummyPlayer;
    private static Obstacle dummyObstacle;

    @BeforeAll
    public static void beforeAll(){
        grid = new TerrainGrid(5, 5);
        dummyPlayer = new Player(1, new Coordinate(1,1), 10);
        grid.setActor(dummyPlayer.getPosition(), dummyPlayer);

        dummyObstacle = new Obstacle(new Coordinate(3,3), true);
        grid.setActor(dummyObstacle.getPosition(), dummyObstacle);
    }

    @Test
    void isValidCoordinateTest(){
        for(int x = -1; x < 6; x++){
            for(int y = -1; y < 6; y ++){
                if(x < 0 || x > 4 || y < 0 || y > 4){
                    assertFalse(grid.isValidCoordinate(new Coordinate(x,y)));
                } else {
                    assertTrue(grid.isValidCoordinate(new Coordinate(x,y)));
                } 

            }
        }
    }

    @Test
    void moveActorTest(){
        Coordinate origin = dummyPlayer.getPosition();
        Coordinate target = new Coordinate(1,2);
        assertTrue(grid.moveActor(origin, target));
        assertTrue(grid.getTile(origin).isEmpty());
        assertFalse(grid.getTile(target).isEmpty());
        assertTrue(grid.getTile(target).getActor().equals(dummyPlayer));
        dummyPlayer.setPosition(target);

        origin = new Coordinate(target);
        target = new Coordinate(1, 3);
        assertTrue(grid.moveActor(dummyPlayer, target));
        assertTrue(grid.getTile(origin).isEmpty());
        assertFalse(grid.getTile(target).isEmpty());
        assertTrue(grid.getTile(target).getActor().equals(dummyPlayer));

        origin = new Coordinate(target);
        target = new Coordinate(1, 2);
        assertTrue(grid.moveActor(dummyPlayer.getPlayerID(), target));
        assertTrue(grid.getTile(origin).isEmpty());
        assertFalse(grid.getTile(target).isEmpty());
        assertTrue(grid.getTile(target).getActor().equals(dummyPlayer));
        dummyPlayer.setPosition(target);

        Coordinate illegalMove = new Coordinate(5, 1);
        assertThrows(IndexOutOfBoundsException.class, () -> grid.moveActor(dummyPlayer, illegalMove));
        assertTrue(grid.getTile(target).getActor().equals(dummyPlayer));
    }

    @Test
    void getFreeTilesTest(){
        List<Coordinate> freeTiles = grid.getFreeTiles();
        for(Coordinate c : freeTiles){
            assertFalse(c.equals(dummyObstacle.getPosition()) || c.equals(dummyPlayer.getPosition()));
        }
    }


    
}
