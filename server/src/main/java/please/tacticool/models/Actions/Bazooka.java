package please.tacticool.models.Actions;

import com.mysql.jdbc.BalanceStrategy;
import please.tacticool.enums.ActionType;
import please.tacticool.models.Actors.Actor;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

import java.util.ArrayList;
import java.util.List;

public class Bazooka extends Action2{

    public Bazooka(Coordinate coordinate) {
        super(coordinate, ActionType.BAZOOKA);
    }


    @Override
    public Action2 perform(Player player, TerrainGrid grid) {
        List<Coordinate> result = new ArrayList<>();

        if (player.getPosition().distance(getCoordinate()) > GameBalance.BazookaRange || player.getActionPoints() < GameBalance.BazookaActionCost || player.isDead()) {
            return null;
        }

        Coordinate target = player.getPosition().add(getCoordinate().scale(GameBalance.BazookaRange));

        player.useActionPoints(GameBalance.BazookaDamage);

        for (int i = 0; i < GameBalance.BazookaRadius * GameBalance.BazookaRadius; i++) {
            Coordinate newTarget = target.add(new Coordinate(-1 + i % GameBalance.BazookaRadius, -1 + i / GameBalance.BazookaRadius));
            if (grid.isValidCoordinate(newTarget)) {
                result.add(newTarget);
                Actor actor = grid.getTile(newTarget).getActor();
                if (actor != null) {
                    actor.getHit(GameBalance.BazookaDamage);
                }
            }
        }
        return new Bazooka(target);
    }
}
