package please.tacticool.models.Actions;

import please.tacticool.GameBalance;
import please.tacticool.enums.ActionType;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

public class Move extends Action {
    public Move(Coordinate coordinate){
        super(coordinate, ActionType.MOVE);
    }

    @Override
    public Action perform(Player player, TerrainGrid grid) {
        Coordinate target = player.getPosition().add(getCoordinate());
        if (player.getActionPoints() >= GameBalance.MoveActionCost) {
            player.useActionPoints(GameBalance.MoveActionCost);
        }
        if (grid.isValidCoordinate(target) && grid.isEmptyTile(target) && target.distance(player.getPosition()) == 1 && !player.isDead()) {
            grid.moveActor(player, target);
            player.setPosition(target);
            return new Move(target);
        }
        return null;
    }
}
