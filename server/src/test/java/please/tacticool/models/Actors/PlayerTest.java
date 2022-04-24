package please.tacticool.models.Actors;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import please.tacticool.GameBalance;
import please.tacticool.models.Coordinate;


@SpringBootTest
public class PlayerTest {
    private Coordinate coord;
    private Player player;
    private Player deadPlayer;

    @BeforeAll
    public void BeforeAll() {
        coord = new Coordinate(0, 0);
        player = new Player(1, coord, 10);
        deadPlayer = new Player(2, coord, 0);
    }

    @Test
    void isAliveTest() {
        assertThat(!player.isDead());
        assertThat(deadPlayer.isDead());
    }

    @Test
    void takeDamageTest() {
        assertEquals(10, player.getHealthPoints());
        player.getHit(3);
        assertEquals(7, player.getHealthPoints());
    }

    @Test
    void actionPointsTest() {
        assertEquals(GameBalance.MaxActionPoints, player.getActionPoints());
        player.useActionPoints(2);
        assertEquals(GameBalance.MaxActionPoints - 2, player.getActionPoints());
        player.resetActionPoints();
        assertEquals(GameBalance.MaxActionPoints, player.getActionPoints());
    }
}
