package please.tacticool.models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CoordinateTest {
    private static Coordinate coord;
    private static Coordinate coord2;

    @BeforeAll
    public static void beforeAll(){
        coord = new Coordinate(1,1);
        coord2 = new Coordinate(2,2);
    }

    @Test
    void addTwoCoordinatesTest(){
        assertThat(coord.add(coord2).toString()).isEqualTo(new Coordinate(3,3).toString());
    }

    @Test
    void scaleCoordianteWith2(){
        assertThat(coord.scale(2).toString()).isEqualTo(new Coordinate(2,2).toString());
        assertThat(coord2.scale(2).toString()).isEqualTo(new Coordinate(4,4).toString());
    }

    @Test
    void distanceBetweenTwoPointsExpectedToBe2(){
        assertThat(coord.distance(coord2)).isEqualTo(2);
    }

    @Test
    void testStringConstructorForCoordinate() {
        assertThat(new Coordinate ("1,1").toString()).isEqualTo(new Coordinate(1,1).toString());
    }

    @Test
    void testConstructorByPassingCoordinateObject(){
        assertThat(new Coordinate(new Coordinate(4,4)).toString()).isEqualTo(new Coordinate(4,4).toString());
    }
}
