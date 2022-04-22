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
        if (grid.isEmptyTile(getCoordinate()) && getCoordinate().distance(player.getPosition()) == 1 && player.getActionPoints() >= GameBalance.MoveActionCost && !player.isDead()) {
            grid.moveActor(player, getCoordinate());
            player.useActionPoints(GameBalance.MoveActionCost);
            return new Move(getCoordinate());
        }
        return null;
    }
}
