package please.tacticool.models.Actions;

import please.tacticool.enums.ActionType;
import please.tacticool.models.Actors.Actor;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

import java.util.ArrayList;
import java.util.List;

public class Rifle extends Action2{

    public Rifle(Coordinate coordinate) {
        super(coordinate, ActionType.RIFLE);
    }

    @Override
    public Action2 perform(Player player, TerrainGrid grid) {
        if (player.getActionPoints() < GameBalance.RifleActionCost || player.isDead()) {
            return null;
        }

        player.useActionPoints(GameBalance.RifleActionCost);

        List<Coordinate> result = new ArrayList<Coordinate>();

        Coordinate newTarget = player.getPosition().add(getCoordinate());
        while (grid.isValidCoordinate(newTarget)) {
            Actor actor = grid.getTile(newTarget).getActor();
            if (actor != null) {
                actor.getHit(GameBalance.RifleDamage);
                return new Rifle(newTarget);
            }
            result.add(newTarget);
            newTarget = newTarget.add(getCoordinate());
        }
        return null;
    }
}
