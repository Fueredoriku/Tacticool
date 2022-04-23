package please.tacticool.models.Actions;

import please.tacticool.GameBalance;
import please.tacticool.enums.ActionType;
import please.tacticool.models.Actors.Actor;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

public class Bazooka extends Action {

    public Bazooka(Coordinate coordinate) {
        super(coordinate, ActionType.BAZOOKA);
    }


    @Override
    public Action perform(Player player, TerrainGrid grid) {
        if (player.getPosition().distance(getCoordinate()) > GameBalance.BazookaRange || player.getActionPoints() < GameBalance.BazookaActionCost || player.isDead()) {
            return null;
        }

        Coordinate target = player.getPosition().add(getCoordinate().scale(GameBalance.BazookaRange));

        player.useActionPoints(GameBalance.BazookaDamage);

        for (int i = 0; i < GameBalance.BazookaRadius * GameBalance.BazookaRadius; i++) {
            Coordinate newTarget = target.add(new Coordinate(-1 + i % GameBalance.BazookaRadius, -1 + i / GameBalance.BazookaRadius));
            if (grid.isValidCoordinate(newTarget)) {
                Actor actor = grid.getTile(newTarget).getActor();
                if (actor != null) {
                    actor.getHit(GameBalance.BazookaDamage);
                }
            }
        }
        return new Bazooka(target);
    }
}
