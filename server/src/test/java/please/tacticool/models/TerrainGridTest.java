package please.tacticool.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TerrainGridTest {

    @BeforeAll
    public static void beforeAll(){
        
    }

    @Test
    void isValidCoordinateTest(){
        TerrainGrid grid = new TerrainGrid(5, 5);
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
    
}
